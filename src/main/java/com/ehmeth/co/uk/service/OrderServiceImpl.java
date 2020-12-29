package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.db.models.order.OrderItem;
import com.ehmeth.co.uk.db.models.order.OrderItemStatus;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.db.repository.OrderItemRepository;
import com.ehmeth.co.uk.db.repository.OrderRepository;
import com.ehmeth.co.uk.events.OrderCreationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private ProductService productService;
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;
    private ApplicationEventPublisher publisher;

    @Autowired
    public OrderServiceImpl(ProductService productService,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            ApplicationEventPublisher publisher) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.publisher = publisher;
    }

    @Override
    public Order createOrder(User user, List<CartItemModel> cartItems) {
        log.info("order creation req");
        Order order = new Order(user.getId());
        order.setOrderId(generateOrderId());
        order = orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemModel c : cartItems) {
            orderItems.add(createOrderItem(order.getId(), c));
        }
        order.setOrderItems(orderItems);
        order = orderRepository.save(order);
        publisher.publishEvent(new OrderCreationEvent(this, order));
        return order;
    }

    /**
     * fetches a product by it ID;
     *
     * @param productId
     * @return
     */
    private Product getProduct(final String productId) {
        return productService.getById(productId).orElseThrow(() -> new NotFoundException("Invalid product Id [" + productId + "]"));
    }

    /**
     * creates an orderItem for an order
     *
     * @param orderId
     * @param cartItemModel
     * @return
     */
    private OrderItem createOrderItem(final String orderId,
                                      CartItemModel cartItemModel) {
        Product product = getProduct(cartItemModel.getProductId());
        //out of stock goods
        if (product.getQuantity() == 0) {
            throw new BadRequestException(String.format("%s is out of stock in store %s", cartItemModel.getProductName(), cartItemModel.getStoreName()));
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setCreatedAt(new Date());
        orderItem.setUpdatedAt(new Date());
        orderItem.setPricingType(cartItemModel.getPricingType());
        orderItem.setProductImage(cartItemModel.getProductImage());
        orderItem.setProductName(cartItemModel.getProductName());
        orderItem.setStatus(OrderItemStatus.AWAITING_APPROVAL);
        orderItem.setUnitPrice(cartItemModel.getUnitPrice());
        orderItem.setQuantity(cartItemModel.getQuantity());
        orderItem.setSubTotal(cartItemModel.getUnitPrice().multiply(BigDecimal.valueOf(cartItemModel.getQuantity())));
        orderItem.setStoreId(product.getStoreId());
        orderItem.setStoreName(cartItemModel.getStoreName());
        orderItem.setOrderId(orderId);
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    /**
     * updates an order object
     *
     * @param order
     * @return
     */
    public Order update(Order order) {
        order.setUpdatedAt(new Date());
        return orderRepository.save(order);
    }

    /**
     * updates an order Item
     *
     * @param orderItem
     * @return
     */
    public OrderItem updateOrderItem(OrderItem orderItem) {
        orderItem.setUpdatedAt(new Date());
        return orderItemRepository.save(orderItem);
    }

    /**
     * Generates a unique identifier for
     * a particular order
     *
     * @return
     */
    public String generateOrderId() {
        String orderId;
        Order existingOrder;
        do {
            orderId = generateAlphaNumericString();
            existingOrder = orderRepository.findByOrderId(orderId);
        } while (Objects.nonNull(existingOrder));

        return orderId;
    }


    /**
     * Generates an alphanumeric string
     *
     * @return
     */
    private String generateAlphaNumericString() {
        String alphaString = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
        String numericString = "123456789";

        StringBuilder alphaNumericString = new StringBuilder(6);

        for (int i = 0; i < 3; i++) {
            int index = (int) (alphaString.length()
                    * Math.random());
            alphaNumericString.append(alphaString
                    .charAt(index));
        }
        for (int i = 0; i < 3; i++) {
            int index = (int) (numericString.length()
                    * Math.random());
            alphaNumericString.append(numericString
                    .charAt(index));
        }
        return alphaNumericString.toString();
    }

    @Override
    public Map<Object, Object> fetchStoreOrders(Store store,
                                                int page,
                                                int size,
                                                String direction,
                                                OrderItemStatus status) {

        Sort.Direction dir;
        if ("asc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.ASC;
        } else {
            dir = Sort.Direction.DESC;
        }

        PageRequest request = PageRequest.of(page, size, Sort.by(dir, "updatedAt"));
        Page<OrderItem> storeOrdersPage;
        if (status == null) {
            storeOrdersPage = orderItemRepository.findByStoreId(store.getStoreId(), request);
        } else {
            storeOrdersPage = orderItemRepository.findByStoreIdAndStatus(store.getStoreId(), status, request);
        }
        Map<Object, Object> storeProductPage = new HashMap<>();
        storeProductPage.put("totalStoreOrders", storeOrdersPage.getTotalElements());
        storeProductPage.put("totalOrdersOnPage", storeOrdersPage.getNumberOfElements());
        storeProductPage.put("totalPages", storeOrdersPage.getTotalPages());
        storeProductPage.put("orders", storeOrdersPage.getContent());
        return storeProductPage;
    }

    @Override
    public Map<Object, Object> fetchBuyerOrders(User user, int page, int size, String direction) {
        Sort.Direction dir;
        if ("asc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.ASC;
        } else {
            dir = Sort.Direction.DESC;
        }
        PageRequest request = PageRequest.of(page, size, Sort.by(dir, "updatedAt"));
        Page<Order> userOrder = orderRepository.findByBuyerId(user.getId(), request);
        Map<Object, Object> buyerOrderPage = new HashMap<>();
        buyerOrderPage.put("totalBuyerOrders", userOrder.getTotalElements());
        buyerOrderPage.put("totalBuyerOnPage", userOrder.getNumberOfElements());
        buyerOrderPage.put("totalPages", userOrder.getTotalPages());
        buyerOrderPage.put("orders", userOrder.getContent());
        return buyerOrderPage;
    }

    @Override
    public OrderItem OrderItemById(String orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(() -> new NotFoundException("orderItem with ID:[" + orderItemId + "] was not found"));
    }

    @Override
    public long countUserOrders(String userId) {
        return 0;
    }
}
