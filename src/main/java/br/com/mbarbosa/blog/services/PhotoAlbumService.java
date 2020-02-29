package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.repositories.PhotoAlbumRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoAlbumService {

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    public PhotoAlbum create(PhotoAlbum photoAlbum) {
        return photoAlbumRepository.save(photoAlbum);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(id);
        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(() -> new NotFoundException("Post com id " + id + " não foi encontrado."));
        photoAlbumRepository.delete(photoAlbum);
    }

    public List<PhotoAlbum> findAllFetchPhotos() {
        return photoAlbumRepository.findAllFetchPhotos();
    }
}
