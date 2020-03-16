package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.dtos.PhotoAlbumDTO;
import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.models.User;
import br.com.mbarbosa.blog.services.PhotoAlbumService;
import br.com.mbarbosa.blog.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.acl.NotOwnerException;
import java.util.List;

@RestController
@RequestMapping(value = "photo-albums")
public class PhotoAlbumsController {

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        List<PhotoAlbum> photoAlbums = photoAlbumService.findAllFetchPhotos();
        return new ResponseEntity<>(photoAlbums, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestHeader(value = "Authorization") final String authorization,
        @Valid @RequestBody final PhotoAlbumDTO photoAlbumDto) {

        User user = userService.getRequestUser(authorization);

        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setName(photoAlbumDto.getName());
        photoAlbum.setDescription(photoAlbumDto.getDescription());

        PhotoAlbum photoAlbumCreated = photoAlbumService.createPhotoAlbum(photoAlbum, user);
        return new ResponseEntity<>(photoAlbumCreated, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") final String authorization,
                                    @PathVariable(required = true) final Long id) {

        User user = userService.getRequestUser(authorization);

        try {
            photoAlbumService.deleteById(id, user);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (NotOwnerException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
