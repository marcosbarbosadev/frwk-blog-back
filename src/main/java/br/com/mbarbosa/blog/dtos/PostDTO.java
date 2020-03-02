package br.com.mbarbosa.blog.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class PostDTO implements Serializable {

    @NotNull(message = "O título é obrigatório.")
    @NotEmpty(message = "O título não pode estar vazio.")
    private String title;

    @NotNull(message = "A descrição é obrigatória.")
    @NotEmpty(message = "A descrição não pode estar vazio.")
    private String description;

}
