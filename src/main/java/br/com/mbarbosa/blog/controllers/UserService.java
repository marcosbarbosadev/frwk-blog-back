package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {

        Example<User> userExample = Example.of(user);
        if(!userRepository.exists(userExample)) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        return userRepository.save(user);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<User> postOptional = userRepository.findById(id);
        User user = postOptional.orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não foi encontrado."));
        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
