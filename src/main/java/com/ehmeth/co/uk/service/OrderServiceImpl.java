package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.db.models.order.OrderItem;
import com.ehmeth.co.uk.db.models.order.OrderItemStatus;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.repository.OrderItemRepository;
import com.ehmeth.co.uk.db.repository.OrderRepository;
import com.ehmeth.co.uk.events.OrderCreationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                            OrderItemRepository orderItemRepository) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
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
        orderItem.setStatus(OrderItemStatus.PENDING);
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
     * Generates a unique identifier for
     * a particular order
     *
     * @return
     */
    public String generateOrderId() {
        String orderId;
        Order existingOrder;
        do {
            orderId = String.valueOf(Math.random());
            existingOrder = orderRepository.findByOrderId(orderId);
        } while (Objects.nonNull(existingOrder));

        return orderId;
    }
}
