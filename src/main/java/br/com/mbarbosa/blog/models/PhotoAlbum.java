package br.com.mbarbosa.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "photo_albums", schema = "frwk_blog")
public class PhotoAlbum implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "photoAlbum", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    private List<Photo> photos;

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty
    public User getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
    }

}
