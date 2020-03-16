package br.com.mbarbosa.blog.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String s) {
        super(s);
    }
}
