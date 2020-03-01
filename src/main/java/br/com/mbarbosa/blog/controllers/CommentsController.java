package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.Comment;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.services.CommentService;
import br.com.mbarbosa.blog.services.PostService;
import br.com.mbarbosa.blog.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  index() {
        List<Comment> posts = commentService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping(value = "post-id/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") final String authorization,
           @PathVariable(required = true) Long postId, @Valid @RequestBody final Comment comment) {

        User user = userService.getRequestUser(authorization);

        Comment commentCreated = null;
        try {
            commentCreated = commentService.createComment(comment, postId, user);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commentCreated, HttpStatus.CREATED);
    }

    @DeleteMapping( value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) final Long id) {
        try {
            commentService.deleteById(id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
