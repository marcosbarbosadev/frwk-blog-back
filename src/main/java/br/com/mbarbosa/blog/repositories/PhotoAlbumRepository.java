package br.com.mbarbosa.blog.repositories;

import br.com.mbarbosa.blog.models.PhotoAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoAlbumRepository extends JpaRepository<PhotoAlbum, Long> {

    @Query(value = "select distinct p from PhotoAlbum p left join fetch p.photos")
    List<PhotoAlbum> findAllFetchPhotos();

}
