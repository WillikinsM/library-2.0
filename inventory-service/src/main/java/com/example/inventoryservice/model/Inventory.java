package com.example.inventoryservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(value = "inventory")
public class Inventory {

    @Id
    private String _id;
    private String bookId;
    private int quantity;

    public Inventory() {
    }

    public Inventory(String _id, String bookId, int quantity) {
        this._id = _id;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public static class InventoryBuilder {
        private String _id;
        private String bookId;
        private int quantity;

        InventoryBuilder() {
        }

        public InventoryBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public InventoryBuilder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public InventoryBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Inventory build() {
            return new Inventory(_id, bookId, quantity);
        }
    }

    public static InventoryBuilder builder() {
        return new InventoryBuilder();
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
        Inventory inventory = (Inventory) o;
        return quantity == inventory.quantity && Objects.equals(_id, inventory._id) && Objects.equals(bookId, inventory.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, bookId, quantity);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "_id='" + _id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

