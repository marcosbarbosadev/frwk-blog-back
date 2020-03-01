package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.PhotoAlbumRepository;
import br.com.mbarbosa.blog.repositories.PhotoRepository;
import br.com.mbarbosa.blog.util.FileUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private OwnerResourceService ownerResourceService;

    @Autowired
    private PropertyService propertyService;

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

    public void deleteById(Long id, User user) throws NotFoundException, NotOwnerException, IOException {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        Photo photo = photoOptional.orElseThrow(
                () -> new NotFoundException("Foto com id " + id + " não foi encontrado"));

        if(!isOwnerPhotoAlbum(photo.getPhotoAlbum(), user)) {
            throw new NotOwnerException();
        }

        if(!removeFile(photo)) {
            throw new IOException("Não foi possível excluir imagem.");
        }
        photoRepository.delete(photo);
    }

    private boolean removeFile(Photo photo) {
        return FileUtil.removeFile(photo, propertyService.getUploadDir());
    }

    public Photo addPhoto(Photo photo, Long photoAlbumId) throws NotFoundException, IOException, InvalidMediaTypeException {

        if(!FileUtil.isJpeg(photo.getImageContents())) {
            throw new InvalidMediaTypeException("não identificado", "Formato de arquivo não permitido.");
        }

        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(photoAlbumId);

        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(
                () -> new NotFoundException("Álbum com id " + photoAlbumId + "não foi encontrado."));

        photo.setName(generateFotoName(photo));
        saveImageFile(photo);

        photo.setPhotoAlbum(photoAlbum);

        return photoRepository.save(photo);
    }

    private void saveImageFile(Photo photo) throws IOException {
        FileUtil.saveImageFile(photo, propertyService.getUploadDir());
    }

    private String generateFotoName(Photo photo) {
        return FileUtil.generateFotoName(photo);
    }

    private boolean isOwnerPhotoAlbum(PhotoAlbum photoAlbum, User user) {
        return ownerResourceService.isOwner(photoAlbum, user);
    }
}
