package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.dtos.PostDTO;
import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.services.PostService;
import br.com.mbarbosa.blog.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.acl.NotOwnerException;
import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostsController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        List<Post> posts = postService.findAllFetchComments();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") final String authorization,
        @Valid @RequestBody final PostDTO postDto) {

        User user = userService.getRequestUser(authorization);

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());

        Post postCreated = postService.createPost(post, user);
        return new ResponseEntity<>(postCreated, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") final String authorization,
                                    @PathVariable(required = true) final Long id) {

        User user = userService.getRequestUser(authorization);

        try {
            postService.deleteById(id, user);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (NotOwnerException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
