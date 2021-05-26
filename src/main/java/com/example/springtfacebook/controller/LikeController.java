package com.example.springtfacebook.controller;


import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.services.serviceImplementation.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LikeController {
    @Autowired
    LikeServiceImpl likeService;

    @RequestMapping(value = "/processLike", method = RequestMethod.POST)
    public String likePost(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        System.out.println("Got here");

        Person person = (Person) session.getAttribute("user");

        if(person == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));
        String action = request.getParameter("action");


        if(likeService.likePost(person, postId, action)){
            System.out.println("Got here 2");
            session.setAttribute("message", "like made successfully");
        }else{
            session.setAttribute("message", "server error");
        }

        response.getWriter().write("Success liking/disliking post");
        return "redirect:/dashboard";
    }
}
