package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Comment;
import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.CommentRepository;
import br.com.mbarbosa.blog.repositories.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private OwnerResourceService ownerResourceService;

    public List<Comment> findAllOrderByCreatedAtDesc() {
        return commentRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Long id, User user) throws NotFoundException, NotOwnerException {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        Comment comment = commentOptional.orElseThrow(
                () -> new NotFoundException("Comentário com id " + id + " não foi encontrado."));

        if(!isOwnerComment(comment, user)) {
            throw new NotOwnerException();
        }

        commentRepository.delete(comment);
    }

    public Comment createComment(Comment comment, Long postId, User user) throws NotFoundException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(
                () -> new NotFoundException("Post com id "+ postId + " não foi encontrado."));

        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public boolean isOwnerComment(Comment comment, User user) {
        return ownerResourceService.isOwner(comment, user);
    }

}
