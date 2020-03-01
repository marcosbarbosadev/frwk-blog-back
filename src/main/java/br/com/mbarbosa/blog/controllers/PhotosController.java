package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.Photo;
import br.com.mbarbosa.blog.services.PhotoAlbumService;
import br.com.mbarbosa.blog.services.PhotoService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "photos")
public class PhotosController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        List<Photo> photoAlbums = photoService.findAll();
        return new ResponseEntity<>(photoAlbums, HttpStatus.OK);
    }

    @PostMapping(value = "{photoAlbumId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable(required = true) Long photoAlbumId, @Valid @RequestBody final Photo photo) {

        Photo photoCreated = null;
        try {
            photoCreated = photoAlbumService.addPhoto(photo, photoAlbumId);
        } catch (NotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(photoCreated, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) final Long id) {
        try {
            photoService.deleteById(id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
