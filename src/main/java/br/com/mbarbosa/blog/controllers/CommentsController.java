package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.Comment;
import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.repositories.CommentRepository;
import br.com.mbarbosa.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  index() {
        List<Comment> posts = commentRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping(value = "post_id/{postId}" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable(required = true) Long postId, @Valid @RequestBody final Comment comment) {

        Optional<Post> optionalPost = postRepository.findById(postId);

        Post post = optionalPost.orElse(null);

        if(post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        comment.setPost(post);
        Comment commentCreated = commentRepository.save(comment);
        return new ResponseEntity<>(commentCreated, HttpStatus.CREATED);

    }

    @DeleteMapping( value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) Long id) {
        commentRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
