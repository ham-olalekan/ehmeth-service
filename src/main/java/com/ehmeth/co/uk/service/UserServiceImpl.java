package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(final String userId){
        return userRepository.findById(userId).orElseThrow(()->new NotFoundException("User with Id was not found"));
    }
}
