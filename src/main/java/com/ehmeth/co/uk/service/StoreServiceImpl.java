package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.db.models.Address;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.User.UserRole;
import com.ehmeth.co.uk.db.models.product.SellerSignupRequest;
import com.ehmeth.co.uk.db.models.store.AccountType;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.db.models.store.StoreStatus;
import com.ehmeth.co.uk.db.repository.AddressRepository;
import com.ehmeth.co.uk.db.repository.StoreRepository;
import com.ehmeth.co.uk.db.repository.UserRepository;
import com.ehmeth.co.uk.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreServiceImpl implements StoreService {

    StoreRepository storeRepository;
    AddressRepository addressRepository;
    UserRepository userRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository,
                            AddressRepository addressRepository,
                            UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Store createStore(SellerSignupRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new BadRequestException("Account with this email already exists. please try another email");
        }

        Address address = Address
                .builder()
                .address(request.getAddress())
                .country(request.getCountry())
                .borough(request.getBorough())
                .postCode(request.getPostCode())
                .build();

        //create store address
        address = saveAddress(address);

        //create store
        Store store = Store.builder()
                .accountType(AccountType.STORE_HQ)
                .createdAt(new Date())
                .updatedAt(new Date())
                .mobileNumber(request.getMobileNumber())
                .addressId(address.getId())
                .registrationNumber(request.getRegistrationNumber())
                .storeName(request.getStoreName())
                .storeStatus(StoreStatus.PENDING_APPROVAL)
                .storeId(UUID.randomUUID().toString())
                .registrationName(request.getRegistrationName())
                .build();
        save(store);

        User user = User
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getMobileNumber())
                .storeId(store.getStoreId())
                .role(UserRole.STORE_ADMIN)
                .hashedPassword(SecurityUtil.hashPassword(request.getPassword()))
                .build();
        saveUser(user);
        return store;
    }

    public Store update(Store store) {
        store.setUpdatedAt(new Date());
        return storeRepository.save(store);
    }

    private Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Store save(Store store) {
        return storeRepository.save(store);
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<Store> findByStoreId(String id) {
        return storeRepository.findByStoreId(id);
    }
}
