package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.config.JwtTokenUtil;
import br.com.mbarbosa.blog.exceptions.ResourceAlreadyExists;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws ResourceAlreadyExists {

        User exists = userRepository.findByEmail(user.getEmail());
        if(exists != null) {
            throw new ResourceAlreadyExists("Já existe uma usuário com o e-mail informado.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

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

    public User getRequestUser(String authorization) {
        String token = authorization.split(" ")[1];
        String userEmail = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByEmail(userEmail);
        return user;
    }
}
