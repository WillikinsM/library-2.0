package com.library.orderservice.model;

import com.library.orderservice.dto.OrderRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(value = "order")
public class Order {

    @Id
    private String _id;
    @NotBlank
    private String customerId;
    @NotBlank
    private String customerName;
    @NotBlank
    private String status;
    @NotBlank
    @Indexed
    private String orderNumber;
    @NotBlank
    private String phone;
    @DBRef
    private Address address;
    @NotNull
    @DBRef
    private List<OrderItems> orderItemsList;

    public Order() {
    }

    public Order(String _id, String customerId, String customerName, String status, String orderNumber, String phone, Address address, List<OrderItems> orderItemsList) {
        this._id = _id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
        this.orderNumber = orderNumber;
        this.phone = phone;
        this.address = address;
        this.orderItemsList = orderItemsList;
    }

    public static class OrderBuilder {
        private String _id;
        private String customerId;
        private String customerName;
        private String status;
        private String orderNumber;
        private String phone;
        private Address address;
        private List<OrderItems> orderItemsList;

        OrderBuilder() {
        }

        public OrderBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public OrderBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderBuilder status(String status) {
            this.status = status;
            return this;
        }

        public OrderBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public OrderBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public OrderBuilder orderItemsList(List<OrderItems> orderItemsList) {
            this.orderItemsList = orderItemsList;
            return this;
        }

        public Order build() {
            return new Order(_id, customerId, customerName, status, orderNumber, phone, address, orderItemsList);
        }
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItems> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItems> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(_id, order._id) && Objects.equals(customerId, order.customerId) && Objects.equals(customerName, order.customerName) && Objects.equals(status, order.status) && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(phone, order.phone) && Objects.equals(address, order.address) && Objects.equals(orderItemsList, order.orderItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, customerId, customerName, status, orderNumber, phone, address, orderItemsList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "_id='" + _id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Order(OrderRequest orderRequest) {
        this.customerId = orderRequest.customerId();
        this.customerName = orderRequest.customerName();
        this.phone = orderRequest.phone();
        this.status = orderRequest.status();
    }

    public void update(){

    }
}
