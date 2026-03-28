package com.library.orderservice.service;

import com.library.orderservice.dto.InventoryResponse;
import com.library.orderservice.dto.OrderDetails;
import com.library.orderservice.dto.OrderRequest;
import com.library.orderservice.dto.OrderResponse;
import com.library.orderservice.exception.NotFoundException;
import com.library.orderservice.model.Address;
import com.library.orderservice.model.Order;
import com.library.orderservice.model.OrderItems;
import com.library.orderservice.repository.AddressRepository;
import com.library.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemsService orderItemsService;
    private final RestClient.Builder restClientBuilder;

    public OrderService(OrderRepository orderRepository, AddressRepository addressRepository,
                        OrderItemsService orderItemsService, RestClient.Builder restClientBuilder) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.orderItemsService = orderItemsService;
        this.restClientBuilder = restClientBuilder;
    }

    public OrderDetails placeOrder(OrderRequest orderRequest){
        log.info("order, {}", orderRequest);
        Order order = new Order(orderRequest);
        Address adress = addressRepository.findById(orderRequest.addressId())
                .orElseThrow(() -> new NotFoundException("Address: " + orderRequest.addressId() + " Not found"));

        List<OrderItems> orderItemsList = orderItemsService.save(orderRequest.orderItemsList());

        order.setOrderItemsList(orderItemsList);
        order.setAddress(adress);
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> bookIds = order.getOrderItemsList()
                .stream()
                .map(OrderItems::getBookId)
                .toList();

            String bookIdsParam = String.join(",", bookIds);
            InventoryResponse[] inventoryResponsesArray = restClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/?bookIds={bookIds}",
                            bookIdsParam)
                    .retrieve()
                    .body(InventoryResponse[].class);

            boolean allProductsinStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
            System.out.println(Arrays.toString(inventoryResponsesArray));
            log.info("deu certo 1:{}",allProductsinStock);


        if(allProductsinStock) {
            return new OrderDetails(orderRepository.save(order));
        }else {
            throw new NotFoundException("product is not in stock");
        }

    }
    public OrderDetails getOrder(String ordernumber){
      return new OrderDetails(orderRepository.findByOrderNumber(ordernumber)
              .orElseThrow(
                      () -> new NotFoundException("Order: " + ordernumber + " Not found")));
    }
    public List<OrderResponse> findall(){
       List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponse::new).toList();
    }

    public List<OrderDetails> getOrderbyUserId(String userId){
        List<Order> orders = orderRepository.findOrdersByCustomerId(userId).orElseThrow(
                () -> new NotFoundException("Order: " + userId + " Not found"));
        return orders.stream().map(OrderDetails::new).toList();

    }
}
