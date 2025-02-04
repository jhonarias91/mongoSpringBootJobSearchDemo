package com.faridroid.Joblisting.controller;

import com.faridroid.Joblisting.data.PostRepository;
import com.faridroid.Joblisting.data.PostSearchRepository;
import com.faridroid.Joblisting.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostController {


    private PostRepository postRepository;
    private PostSearchRepository postSearch;

    public PostController(PostRepository postRepository, PostSearchRepository postSearch) {
        this.postRepository = postRepository;
        this.postSearch = postSearch;
    }

    @GetMapping("/")
    public ResponseEntity<Void> redirect() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/swagger-ui.html"))
                .build();
    }

    @GetMapping("/posts")
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }


    @GetMapping("/posts/{query}")
    public ResponseEntity<List<Post>> search(@PathVariable String query, @RequestParam(required = false) Integer limit){
        return ResponseEntity.ok(postSearch.findByText(query, limit == null ? 1000: limit));
    }

    @PostMapping("/post")
    public Post insertPost(@RequestBody Post post){
        return postRepository.save(post);
    }
}
