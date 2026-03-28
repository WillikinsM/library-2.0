package com.library.orderservice.model;

import com.library.orderservice.dto.OrderItemsDto;
import com.library.orderservice.dto.OrderItemsUpdate;
import jakarta.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

@Document(value = "order_book")
public class OrderItems {

    @Id
    private String _id;
    private String bookId;
    private String bookName;
    private BigDecimal price;
    private int quantity;

    public OrderItems() {
    }

    public OrderItems(String _id, String bookId, String bookName, BigDecimal price, int quantity) {
        this._id = _id;
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
    }

    public static class OrderItemsBuilder {
        private String _id;
        private String bookId;
        private String bookName;
        private BigDecimal price;
        private int quantity;

        OrderItemsBuilder() {
        }

        public OrderItemsBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public OrderItemsBuilder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public OrderItemsBuilder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public OrderItemsBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItemsBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderItems build() {
            return new OrderItems(_id, bookId, bookName, price, quantity);
        }
    }

    public static OrderItemsBuilder builder() {
        return new OrderItemsBuilder();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItems that = (OrderItems) o;
        return quantity == that.quantity && Objects.equals(_id, that._id) && Objects.equals(bookId, that.bookId) && Objects.equals(bookName, that.bookName) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, bookId, bookName, price, quantity);
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "_id='" + _id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }



    public OrderItems(@Valid OrderItemsDto orderItemsDto) {
        this.bookId = orderItemsDto.bookId();
        this.bookName = orderItemsDto.bookName();
        this.price = orderItemsDto.price();
        this.quantity = orderItemsDto.quantity();
    }

    public void update(@Valid OrderItemsUpdate orderItemsUpdate) {
        if (orderItemsUpdate.bookId() != null) {
            this.bookId = orderItemsUpdate.bookId();
        }

        if (orderItemsUpdate.bookName() != null) {
            this.bookName = orderItemsUpdate.bookName();
        }

        if (orderItemsUpdate.price() != null) {
            this.price = orderItemsUpdate.price();
        }

        if (orderItemsUpdate.quantity() != 0) {
            this.quantity = orderItemsUpdate.quantity();
        }
    }


}
