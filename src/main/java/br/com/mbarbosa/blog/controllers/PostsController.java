package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  index() {
        List<Post> posts = postRepository.findAllFetchComments();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody final Post post) {
        Post postCreated = postRepository.save(post);
        return new ResponseEntity<>(postCreated, HttpStatus.CREATED);
    }

    @DeleteMapping( value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) Long id) {
        postRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
