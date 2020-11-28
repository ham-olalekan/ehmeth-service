package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.User.BuyerCreationRequest;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.User.UserRole;
import com.ehmeth.co.uk.db.repository.UserRepository;
import com.ehmeth.co.uk.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(final String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with Id was not found"));
    }

    @Override
    public User createBuyerUser(BuyerCreationRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            throw new BadRequestException("User with this email already exists. please sign-up with another email");
        }

        User buyer = User
                .builder()
                .role(UserRole.BUYER)
                .hashedPassword(SecurityUtil.hashPassword(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .lastName(request.getLastName())
                .createdAt(new Date())
                .updatedAt(new Date())
                .firstName(request.getFirstName())
                .build();

        return userRepository.save(buyer);
    }
}
