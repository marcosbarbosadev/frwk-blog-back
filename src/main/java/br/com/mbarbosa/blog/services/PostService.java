package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private OwnerResourceService ownerResourceService;

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllFetchComments() {
        return postRepository.findAllFetchComments();
    }

    public void deleteById(Long id, User user) throws NotFoundException, NotOwnerException {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.orElseThrow(
                () -> new NotFoundException("Post com id " + id + " não foi encontrado."));

        if(!isOwnerPost(post, user)) {
            throw new NotOwnerException();
        }

        postRepository.delete(post);
    }

    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }

    private boolean isOwnerPost(Post post, User user) {
        return ownerResourceService.isOwner(post, user);
    }

}
