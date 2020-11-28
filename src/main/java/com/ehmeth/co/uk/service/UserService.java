package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.BuyerCreationRequest;
import com.ehmeth.co.uk.db.models.User.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User getUserById(final String userId);
    User createBuyerUser(BuyerCreationRequest request);
}
