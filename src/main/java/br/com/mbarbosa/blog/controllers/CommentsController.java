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
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.acl.NotOwnerException;
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
        List<Comment> posts = commentService.findAllOrderByCreatedAtDesc();
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
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") final String authorization,
                                    @PathVariable(required = true) final Long id) {

        User user = userService.getRequestUser(authorization);

        try {
            commentService.deleteById(id, user);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (NotOwnerException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
