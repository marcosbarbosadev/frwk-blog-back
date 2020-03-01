package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllFetchComments() {
        return postRepository.findAllFetchComments();
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.orElseThrow(() -> new NotFoundException("Post com id " + id + " não foi encontrado."));
        postRepository.delete(post);
    }

    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }
}
