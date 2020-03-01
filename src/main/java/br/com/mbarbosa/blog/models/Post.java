package br.com.mbarbosa.blog.models;

import br.com.mbarbosa.blog.interfaces.OwnerResource;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts", schema = "frwk_blog")
public class Post implements Serializable, OwnerResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "O título é obrigatório.")
    @NotEmpty(message = "O título não pode estar vazio.")
    @Column(length = 150)
    private String title;

    @NotNull(message = "O título é obrigatório.")
    @NotEmpty(message = "O título não pode estar vazio.")
    @Column(columnDefinition = "text")
    private String description;

    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "post_has_photos", joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "photo_id")}, schema = "frwk_blog")
    private List<Photo> photos;

    @ManyToOne
    private User user;

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
