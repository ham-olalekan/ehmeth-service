package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.Address;
import com.ehmeth.co.uk.db.models.PaginatedData;
import com.ehmeth.co.uk.db.models.User.*;
import com.ehmeth.co.uk.db.repository.AddressRepository;
import com.ehmeth.co.uk.db.repository.UserRepository;
import com.ehmeth.co.uk.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private OrderService orderService;
    private AddressRepository addressRepository;

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

    @Override
    public PaginatedData<UserAdminInfo> fetchAllBuyers(int page, int size) {
        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<User> buyersPage = userRepository.findByRole(UserRole.BUYER, request);
        return new PaginatedData<>(buyersPage.getTotalPages(), buyersPage.getTotalElements(), getUserAdminInfo(buyersPage.getContent()));
    }

    private List<UserAdminInfo> getUserAdminInfo(List<User> users) {
        List<UserAdminInfo> userAdminInfos = new ArrayList<>();
        users.forEach(u -> {
            UserAdminInfo userInfo = u.getUserAdminInfo();
            userInfo.setNumberOfOrders(countBuyerOrders(u.getId()));
            userInfo.setAddress(findUserAddress(u.getAddressId()).orElse(null));
            userAdminInfos.add(userInfo);
        });
        return userAdminInfos;
    }

    /**
     * counts the number of orders
     * <p>
     * of a buyer
     *
     * @param userId
     * @return long
     */
    private long countBuyerOrders(String userId) {
        return orderService.countUserOrders(userId);
    }

    /**
     * find a user-address by addressId
     *
     * @param addressId
     * @return Address
     */
    private Optional<Address> findUserAddress(final String addressId) {
        return addressRepository.findById(addressId);
    }

    @Override
    public User updateUserStatus(User user, UserStatus status) {
        user.setUserStatus(status);
        return userRepository.save(user);
    }
}
