package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.repositories.PhotoAlbumRepository;
import br.com.mbarbosa.blog.repositories.PhotoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.acl.NotOwnerException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private OwnerResourceService ownerResourceService;

    @Autowired
    private Environment env;

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

    public boolean removeFile(Photo photo) {

        if(photo.getName() == null) {
            return false;
        }

        String uploadDir = getUploadDir();
        Path absolutePath = Paths.get(uploadDir, photo.getName());
        return new File(absolutePath.toString()).delete();
    }

    public Photo addPhoto(Photo photo, Long photoAlbumId) throws NotFoundException, IOException {

        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(photoAlbumId);

        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(
                () -> new NotFoundException("Álbum com id " + photoAlbumId + "não foi encontrado."));

        photo.setName(generateFotoName(photo));
        saveImageFile(photo);

        photo.setPhotoAlbum(photoAlbum);

        return photoRepository.save(photo);
    }

    private void saveImageFile(Photo photo) throws IOException {
        byte[] imageContents = Base64.getDecoder().decode(photo.getImageContents());
        Path absolutePath = Paths.get(getUploadDir(), photo.getName());

        ByteArrayInputStream bis = new ByteArrayInputStream(imageContents);
        BufferedImage bufferedImage = ImageIO.read(bis);
        ImageIO.write(bufferedImage, "jpg", new File(absolutePath.toString()));
    }

    private String generateFotoName(Photo photo) {
        String imageName = UUID.randomUUID().toString() + ".jpg";
        photo.setName(imageName);
        return imageName;
    }

    private String getUploadDir() {
        return env.getProperty("blog.upload_dir");
    }

    private boolean isOwnerPhotoAlbum(PhotoAlbum photoAlbum, User user) {
        return ownerResourceService.isOwner(photoAlbum, user);
    }
}
