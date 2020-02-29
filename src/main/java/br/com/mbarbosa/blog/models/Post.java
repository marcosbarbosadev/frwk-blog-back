package br.com.mbarbosa.blog.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts", schema = "frwk_blog")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "post_has_photos", joinColumns = {@JoinColumn(name = "post_id")},
    inverseJoinColumns = {@JoinColumn(name = "photo_id")}, schema = "frwk_blog")
    private List<Photo> fotos;

    @ManyToOne
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
