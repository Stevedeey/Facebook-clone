package com.example.springtfacebook.services.serviceImplementation;

import com.example.springtfacebook.POJO.PostMapper;
import com.example.springtfacebook.model.Comment;
import com.example.springtfacebook.model.Likes;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import com.example.springtfacebook.repositories.CommentRepository;
import com.example.springtfacebook.repositories.LikesRepository;
import com.example.springtfacebook.repositories.PersonRepository;
import com.example.springtfacebook.repositories.PostRepository;
import com.example.springtfacebook.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PersonRepository personRepository;

    /**
     * CREATE operation on Post
     * @param userId
     * @param post
     * @return boolean(true for successful creation and false on failure to create)
     * */
    public boolean createPost(Long userId, Post post) {
        boolean result = false;

        try {
            Person user = personRepository.findById(userId).get();

            if(user != null){
                postRepository.save(post);
                result = true;
            }else result = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Post> getPostById(Long postId){
        List<Post> postList = new ArrayList<>();

        try{
            postList= postRepository.findPostByPostId(postId);
        }catch(Exception e){
            System.out.println("Something went wrong1 "+e.getMessage());
        }

        return postList;
    }

    /**
     * GET by id operation on Post
     * @params postId
     * @return post object
     * */
    public List<PostMapper> getPost(Person currentUser) {
        List<PostMapper> posts = new ArrayList<>();

        try {
            //get all posts
            List<Post> postData = postRepository.findAll();

            for (Post postEach:postData) {

                PostMapper post = new PostMapper();
                post.setId(postEach.getPostId());
                post.setTitle(postEach.getTitle());
                post.setBody(postEach.getBody());
                post.setImageName("/image/"+postEach.getImageName());
                post.setName(postEach.getPerson().getLastname()+ " "+ postEach.getPerson().getFirstname());

                //the total number of likes on this particular post
                List<Likes> numberOfLikes = likesRepository.findAllByPostPostId(postEach.getPostId());
                int likeCount = numberOfLikes.size();
                post.setNoLikes(likeCount);

                List<Comment> noOfComment = commentRepository.findAllByPostPostId(postEach.getPostId());
                int commentCount = noOfComment.size();
                post.setNoComments(commentCount);

                //return true if current user liked this post, else false
                List<Likes> postLiked = likesRepository.findAllByPostPostIdAndPersonId(postEach.getPostId(), currentUser.getId());
                if(postLiked.size() > 0){
                    post.setLikedPost(true);
                }

                posts.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse(posts);
        return posts;
    }

    public boolean editPost(Person person, Long postId, String title, String body) {
        boolean status = false;

        try {
            Post post = postRepository.findById(postId).get();
            post.setTitle(title);
            post.setBody(body);
            postRepository.save(post);

            status = true;

        }catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public boolean deletePost(Long postId, Long personId){
        boolean status =  false;

        try {

            postRepository.deletePostByPostIdAndPersonId(postId, personId);

            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
