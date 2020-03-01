package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.models.PhotoAlbum;
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
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoAlbumService {

    @Autowired
    private PhotoAlbumRepository photoAlbumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private Environment env;

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

    public Photo addPhoto(Photo photo, Long photoAlbumId) throws NotFoundException {

        Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(photoAlbumId);

        PhotoAlbum photoAlbum = photoAlbumOptional.orElseThrow(
                () -> new NotFoundException("Álbum com id " + photoAlbumId + "não foi encontrado."));


        String imageName = UUID.randomUUID().toString();
        photo.setName(imageName);
        byte[] imageContents = Base64.getDecoder().decode(photo.getImageContents());

        try {

            URL uploads = getClass().getClassLoader().getResource("uploads");
            URL uploads2 = getClass().getClassLoader().getResource("application.properties");

            String dirUpload = env.getProperty("blog.upload_dir");
            Path path = Paths.get(dirUpload, imageName + ".jpg");

            ByteArrayInputStream bis = new ByteArrayInputStream(imageContents);
            BufferedImage bufferedImage = ImageIO.read(bis);
            ImageIO.write(bufferedImage, "jpg", new File(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setPhotoAlbum(photoAlbum);

        return photoRepository.save(photo);
    }
}
