package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.User.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(final String email);

    Page<User> findByRole(final UserRole userRole, Pageable pageable);
}
