package com.example.cursos_backend.infra;

import com.example.cursos_backend.enums.Role;
import com.example.cursos_backend.exceptions.InvalidAccessException;
import com.example.cursos_backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHelper {

    public boolean isOwnerOrAdmin(Long ownerId, User user) {
        boolean isOwner = ownerId.equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        return isOwner || isAdmin;
    }

    public void checkOwnerOrAdmin(Long ownerId, User user) {
        if (!isOwnerOrAdmin(ownerId, user)) {
            throw new InvalidAccessException();
        }
    }

    public void checkNotOwner(Long ownerId, User user) {
        boolean isOwner = ownerId.equals(user.getId());

        if (isOwner) {
            throw new InvalidAccessException();
        }
    }

    public void checkOwner(Long ownerId, User user) {
        if (!ownerId.equals(user.getId())) {
            throw new InvalidAccessException();
        }
    }
}
