package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.Comment;
import br.com.mbarbosa.blog.models.Post;
import br.com.mbarbosa.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @RequestMapping
    public String index() {
        return "posts";
    }

}
