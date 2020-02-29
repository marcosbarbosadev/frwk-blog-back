package br.com.mbarbosa.blog.repositories;

import br.com.mbarbosa.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
