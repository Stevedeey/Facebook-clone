package com.example.springtfacebook.controller;

import com.example.springtfacebook.dto.Login;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import com.example.springtfacebook.services.serviceImplementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
@MultipartConfig
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @RequestMapping(value = "/postProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("post") Post post,  HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        try {

            Part part = request.getPart("file");

            //set imageName
            String imageName = part.getSubmittedFileName();
            post.setImageName(imageName);

            //set person
            post.setPerson(person);

            //path to store image
            String path = "/Users/mac/IdeaProjects/SpringTFacebook/src/main/resources/static/image"+File.separator+post.getImageName();

            InputStream in = part.getInputStream();
            boolean success = uploadFile(in, path);

            if(success){
                if(postService.createPost(person.getId(), post))
                    session.setAttribute("message", "File uploaded successfully");
                else
                    session.setAttribute("message", "Error uploading image to database");
            }else{
                session.setAttribute("message", "error uploading file");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/edit/{post}", method = RequestMethod.GET)
    public ModelAndView editComment(@PathVariable("post") Long post_id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("person", new Person());
            mav.addObject("login", new Login());
            session.setAttribute("message", "!!!Please Login");
            return mav;
        }

        ModelAndView mav = new ModelAndView("edit");
        Long postId = post_id;

        List<Post> post = postService.getPostById(postId);

        mav.addObject("postData", post.get(0));
        mav.addObject("user", person);

        return mav;
    }

    @RequestMapping(value = "/editProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                          @ModelAttribute("post") Post post) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        if(postService.editPost(person,post.getPostId(),post.getTitle(),post.getBody())) {
            session.setAttribute("message", "Post edited successfully");
        }else{
            session.setAttribute("message", "Error editing post!");
        }

        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public String deleteComment(HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));

        if(postService.deletePost(postId,person.getId())){
            session.setAttribute("message", "Post deleted successfully");
        }else {
            session.setAttribute("message", "Error deleting post!");
        }

        return "redirect:/dashboard";
    }


    /**
     * method for reading images to a specific path
     * @param in
     * @param path
     * @return boolean
     */
    public boolean uploadFile(InputStream in, String path){
        boolean test = false;

        try{
            byte[] byt = new byte[in.available()];
            in.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            test = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return test;
    }
}
