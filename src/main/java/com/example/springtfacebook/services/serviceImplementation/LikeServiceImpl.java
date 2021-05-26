package com.example.springtfacebook.services.serviceImplementation;


import com.example.springtfacebook.model.Likes;
import com.example.springtfacebook.model.Person;
import com.example.springtfacebook.model.Post;
import com.example.springtfacebook.repositories.LikesRepository;
import com.example.springtfacebook.repositories.PostRepository;
import com.example.springtfacebook.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private PostRepository postRepository;

    public boolean likePost(Person person, Long postId, String action){
        boolean result = false;

        Post post = postRepository.findById(postId).get();

        try{
            Likes like = new Likes();
            like.setPerson(person);
            like.setPost(post);

            if(action.equals("1")){
                likesRepository.save(like);
                System.out.println("save");
            }else{
                likesRepository.deleteLikesByPostAndPerson(post,person);
                System.out.println("delete");
            }

            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
