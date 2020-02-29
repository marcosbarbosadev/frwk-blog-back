package br.com.mbarbosa.blog.controllers;

import br.com.mbarbosa.blog.models.PhotoAlbum;
import br.com.mbarbosa.blog.services.PhotoAlbumService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "photo-albuns")
public class PhotoAlbumsController {

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> index() {
        List<PhotoAlbum> photoAlbums = photoAlbumService.findAllFetchPhotos();
        return new ResponseEntity<>(photoAlbums, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody final PhotoAlbum photoAlbum) {
        PhotoAlbum photoAlbumCreated = photoAlbumService.create(photoAlbum);
        return new ResponseEntity<>(photoAlbumCreated, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable(required = true) Long id) {
        try {
            photoAlbumService.deleteById(id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
