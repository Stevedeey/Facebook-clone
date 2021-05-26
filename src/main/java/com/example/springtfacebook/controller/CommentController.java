package com.example.springtfacebook.controller;

import com.example.springtfacebook.dto.CommentMapper;
import com.example.springtfacebook.dto.Login;
import com.example.springtfacebook.model.Comment;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.services.serviceImplementation.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @RequestMapping(value = "/processComment", method = RequestMethod.POST)
    public String makeComment(HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute("commentData") Comment comment, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        //get the post id that comment was made on
        Long postId =  Long.parseLong(request.getParameter("postId"));

        //set the person
        comment.setPerson(person);

        if(commentService.createComment(person.getId(), postId, comment)){
            session.setAttribute("message", "Comment made successfully");
        }else{
            session.setAttribute("message", "Failed to comment");
        }

        return "redirect:/dashboard";
    }

//    @RequestMapping(value = "/comment/{post}", method = RequestMethod.GET)
//    public ModelAndView showComment(@PathVariable("post") Long post_id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//
//        Person person = (Person) session.getAttribute("user");
//
//        if(person == null) {
//            ModelAndView mav = new ModelAndView("index");
//            mav.addObject("person", new Person());
//            mav.addObject("login", new Login());
//            session.setAttribute("message", "!!!Please Login");
//            return mav;
//        }
//
//        ModelAndView mav = new ModelAndView("comment");
//
//        System.out.println(post_id);
//        Long postId = post_id;
//        List<CommentMapper> commentList = commentService.getComments(postId);
//
//        mav.addObject("user", person);
//        mav.addObject("commentData", commentList);
//
//        return mav;
//    }

    @RequestMapping(value = "/comment/{post}", method = RequestMethod.GET)
    public String showComment(@PathVariable("post") Long post_id, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Person person = (Person) session.getAttribute("user");
        if(person == null) return "redirect:/";
        Long postId = post_id;
        List<CommentMapper> commentList = commentService.getComments(postId);
        if (commentList.size() == 0){
            return "redirect:/dashboard";
        }
        model.addAttribute("commentData", commentList);
        model.addAttribute("user", person);
        return "comment";
    }


    @RequestMapping(value = "/editComment", method = RequestMethod.POST)
    public String editComment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Long postId = Long.parseLong(request.getParameter("postId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        String comment = request.getParameter("editedComment");

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        if(commentService.editComment(commentId, person, postId, comment)){
            session.setAttribute("message", "Comment edited successfully");
        }else {
            session.setAttribute("message", "Failed to edit comment");
        }

        return "redirect:/comment/"+postId;
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    public String deleteComment(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));


        if(commentService.deleteComment(commentId)){
            session.setAttribute("message", "Comment edited successfully");
        }else {
            session.setAttribute("message", "Failed to edit comment");
        }

        return "redirect:/comment/"+postId;
    }

}
