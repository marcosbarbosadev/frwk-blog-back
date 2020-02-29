package br.com.mbarbosa.blog.repositories;

import br.com.mbarbosa.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select distinct p from Post p left join fetch p.comments c")
    List<Post> findAllFetchComments();

}
