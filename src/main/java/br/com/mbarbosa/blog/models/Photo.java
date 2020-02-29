package br.com.mbarbosa.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "photos", schema = "frwk_blog")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private PhotoAlbum photoAlbum;

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
