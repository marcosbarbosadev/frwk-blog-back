package br.com.mbarbosa.blog.repositories;

import br.com.mbarbosa.blog.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
