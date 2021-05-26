package com.example.springtfacebook.services.serviceImplementation;

import com.example.springtfacebook.dto.CommentMapper;
import com.example.springtfacebook.model.Comment;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import com.example.springtfacebook.repositories.CommentRepository;
import com.example.springtfacebook.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    /**
     * CREATE operation on Comment
     * @param comment
     * @param postId
     * @param userId
     * @return boolean(true for successful creation and false on failure on create)
     * */

    public boolean createComment(Long userId, Long postId, Comment comment){
        boolean result = false;

        try{
            Post post = postRepository.findById(postId).get();
            //set the post
            comment.setPost(post);

            commentRepository.save(comment);
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public List<CommentMapper> getComments(Long postId){
        List<CommentMapper> comments = new ArrayList();

        try{

            List<Comment> commentsData = commentRepository.findAllByPostPostId(postId);

            for (Comment commentEach:commentsData) {
                CommentMapper comment = new CommentMapper();
                comment.setId(commentEach.getId());
                comment.setPostId(commentEach.getPost().getPostId());
                comment.setComment(commentEach.getComment());
                comment.setUsername(commentEach.getPerson().getLastname()+" "+commentEach.getPerson().getFirstname());
                comment.setTitle(commentEach.getPost().getTitle());
                comment.setImageName("/images/"+commentEach.getPost().getImageName());
                comment.setUserId(commentEach.getPerson().getId());

                comments.add(comment);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return comments;
    }

    public boolean editComment(Long commentId, Person person, Long postId, String comment) {
        boolean status = false;

        try {
            Post post = postRepository.findById(postId).get();

            Comment data = commentRepository.findCommentById(commentId);

            data.setComment(comment);
            data.setPerson(person);
            data.setPost(post);
            commentRepository.save(data);

            status = true;

        }catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }


    /**
     * DELETE operation on Comment
     * @param
     * @param
     * @return boolean(true for successful deletion and false on failure to delete)
     * */
    public boolean deleteComment(Long commentId){
        boolean status =  false;

        try {

            commentRepository.deleteCommentById(commentId);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}

