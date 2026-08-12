package com.example.cursos_backend.infra;

import com.example.cursos_backend.enums.Role;
import com.example.cursos_backend.exceptions.InvalidAccessException;
import com.example.cursos_backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHelper {

    public void checkOwnerOrAdmin(Long ownerId, User user) {
        boolean isOwner = ownerId.equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new InvalidAccessException();
        }
    }
}
