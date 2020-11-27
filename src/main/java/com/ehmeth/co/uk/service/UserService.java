package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
