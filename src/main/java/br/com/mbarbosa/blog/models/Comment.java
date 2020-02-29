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
@Table(name = "comments", schema = "frwk_blog")
public class Comment implements Serializable {

    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String description;

    @ApiModelProperty(hidden = true)
    @ManyToOne
    private User user;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @ManyToOne
    private Post post;

    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
