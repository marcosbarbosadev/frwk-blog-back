package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.repositories.PhotoAlbumRepository;
import br.com.mbarbosa.blog.repositories.PhotoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public Photo save(Photo photo, Long photoAlbumId) throws NotFoundException {

        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(photoAlbumId);

        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(
                () -> new NotFoundException("Álbum com id " + photoAlbumId + "não foi encontrado."));

        photo.setPhotoAlbum(photoAlbum);

        return photoRepository.save(photo);
    }

    public void deleteById(Long id) throws NotFoundException {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        Photo photo = photoOptional.orElseThrow(() -> new NotFoundException("Foto com id " + id + " não foi encontrado"));
        photoRepository.delete(photo);
    }
}
