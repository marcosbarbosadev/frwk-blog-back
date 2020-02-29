package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Comment;
import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.repositories.CommentRepository;
import br.com.mbarbosa.blog.repositories.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment createComment(Comment comment, Long postId) throws NotFoundException {
        Optional<Post> optionalPost = postRepository.findById(postId);

        Post post = optionalPost.orElseThrow(
                () -> new NotFoundException("Post com id "+ postId + " não foi encontrado."));

        comment.setPost(post);
        return commentRepository.save(comment);
    }

}
