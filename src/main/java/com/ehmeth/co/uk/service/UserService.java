package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.PaginatedData;
import com.ehmeth.co.uk.db.models.User.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findByEmail(String email);
    User getUserById(final String userId);
    User createBuyerUser(BuyerCreationRequest request);
    PaginatedData<UserAdminInfo> fetchAllBuyers(int page, int size);
    User updateUserStatus(User user, UserStatus status);
}
