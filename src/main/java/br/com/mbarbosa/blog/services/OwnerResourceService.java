package br.com.mbarbosa.blog.services;

import br.com.mbarbosa.blog.interfaces.OwnerResource;
import br.com.mbarbosa.blog.models.User;
import org.springframework.stereotype.Service;

@Service
public class OwnerResourceService {

    public boolean isOwner(OwnerResource resource, User user) {
        return resource.getUser().getId().equals(user.getId());
    }

}
