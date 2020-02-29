package br.com.mbarbosa.blog.repositories;

import br.com.mbarbosa.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
