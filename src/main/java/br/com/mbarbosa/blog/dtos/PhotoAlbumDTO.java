package br.com.mbarbosa.blog.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PhotoAlbumDTO implements Serializable {

    private String name;
    private String description;

}
