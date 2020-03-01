package br.com.mbarbosa.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private Environment env;

    public String getUploadDir() {
        return env.getProperty("blog.upload_dir");
    }

}
