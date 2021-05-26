package com.example.springtfacebook.controller;

import com.example.springtfacebook.dto.Login;
import com.example.springtfacebook.dto.PostMapper;
import com.example.springtfacebook.model.Comment;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import com.example.springtfacebook.services.serviceImplementation.PersonServiceImpl;
import com.example.springtfacebook.services.serviceImplementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonServiceImpl userService;
    @Autowired
    private PostServiceImpl postService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("message");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("person", new Person());
        mav.addObject("login", new Login());

        return mav;
    }

    @RequestMapping(value = "/processLogout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }

    /**
     * This handles the redirect to dashboard after succesful login.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView showHome(HttpServletRequest request, HttpServletResponse response) {

        HttpSession httpSession = request.getSession();
        Person person = (Person) httpSession.getAttribute("user");

        if(person == null) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("person", new Person());
            mav.addObject("login", new Login());
            httpSession.setAttribute("message", "!!!Please Login");
            return mav;
        }

        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("post", new Post());
        mav.addObject("commentData", new Comment());

        List<PostMapper> post = postService.getPost(person);

        mav.addObject("user", person);
        mav.addObject("posts", post);

        return mav;
    }

    /**
     * This handles the registration process
     * @param request
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("person") Person user) {

        HttpSession httpSession = request.getSession();

        if(userService.createUser(user)){
            httpSession.setAttribute("message", "Successfully registered!!!");
        }else{
            httpSession.setAttribute("message", "Failed to register or email already exist");
        }

        return "redirect:/";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("login", new Login());
//        System.out.println("First ");
//
//        return mav;
//    }

    /**
     * Handling the login request from the browser
     * @param request
     * @param response
     * @param login
     * @return redirect to the homepage
     */
    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("login") Login login) {

        Person user = userService.getUser(login.getEmail(), login.getPassword());
        System.out.println("I got in login process");

        HttpSession httpSession = request.getSession();

        if (user != null) {
            httpSession.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            httpSession.setAttribute("message", "Email or Password is wrong!!!");
            return "redirect:/";
        }
    }
}
