package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<User> postOptional = userRepository.findById(id);
        User user = postOptional.orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não foi encontrado."));
        userRepository.delete(user);
    }

}
