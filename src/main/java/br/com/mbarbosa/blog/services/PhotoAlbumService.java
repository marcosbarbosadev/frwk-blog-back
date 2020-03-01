package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.PhotoAlbumRepository;
import br.com.mbarbosa.blog.repositories.PhotoRepository;
import br.com.mbarbosa.blog.util.FileUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.acl.NotOwnerException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoAlbumService {

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private OwnerResourceService ownerResourceService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PropertyService propertyService;

    public PhotoAlbum createPhotoAlbum(PhotoAlbum photoAlbum, User user) {
        photoAlbum.setUser(user);
        return photoAlbumRepository.save(photoAlbum);
    }

    public void deleteById(Long id, User user) throws NotFoundException, NotOwnerException, IOException {
        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(id);
        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(
                () -> new NotFoundException("Post com id " + id + " não foi encontrado."));

        if(!isOwnerPhotoAlbum(photoAlbum, user)) {
            throw new NotOwnerException();
        }

        if(!removeAllPhotos(photoAlbum.getPhotos())) {
            throw new IOException("Uma ou mais fotos não puderam ser excluidas.");
        }

        photoAlbumRepository.delete(photoAlbum);
    }

    private boolean removeAllPhotos(List<Photo> photos) {
        Optional<Photo> naoRemovido = photos.stream()
                .filter(f -> !removeFile(f))
                .findFirst();
        return !naoRemovido.isPresent();
    }

    private boolean removeFile(Photo photo) {
        return FileUtil.removeFile(photo, propertyService.getUploadDir());
    }


    public List<PhotoAlbum> findAllFetchPhotos() {
        return photoAlbumRepository.findAllFetchPhotos();
    }

    public boolean isOwnerPhotoAlbum(PhotoAlbum photoAlbum, User user) {
        return ownerResourceService.isOwner(photoAlbum, user);
    }

}
