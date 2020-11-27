package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
}
