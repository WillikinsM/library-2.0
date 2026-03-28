package com.library.orderservice.model;

import com.library.orderservice.dto.AddressRequest;
import com.library.orderservice.dto.AddressUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(value = "address")
public class Address {

    @Id
    private String _id;
    @NotBlank
    private String userId;
    @NotBlank
    private String addressName;
    @NotBlank
    private String cep;
    @NotBlank
    private String street;
    private String adjunct;
    @NotBlank
    private String number;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String uf;

    public Address() {
    }

    public Address(String _id, String userId, String addressName, String cep, String street, String adjunct, String number, String district, String city, String uf) {
        this._id = _id;
        this.userId = userId;
        this.addressName = addressName;
        this.cep = cep;
        this.street = street;
        this.adjunct = adjunct;
        this.number = number;
        this.district = district;
        this.city = city;
        this.uf = uf;
    }

    public static class AddressBuilder {
        private String _id;
        private String userId;
        private String addressName;
        private String cep;
        private String street;
        private String adjunct;
        private String number;
        private String district;
        private String city;
        private String uf;

        AddressBuilder() {
        }

        public AddressBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public AddressBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public AddressBuilder addressName(String addressName) {
            this.addressName = addressName;
            return this;
        }

        public AddressBuilder cep(String cep) {
            this.cep = cep;
            return this;
        }

        public AddressBuilder street(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder adjunct(String adjunct) {
            this.adjunct = adjunct;
            return this;
        }

        public AddressBuilder number(String number) {
            this.number = number;
            return this;
        }

        public AddressBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder uf(String uf) {
            this.uf = uf;
            return this;
        }

        public Address build() {
            return new Address(_id, userId, addressName, cep, street, adjunct, number, district, city, uf);
        }
    }

    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdjunct() {
        return adjunct;
    }

    public void setAdjunct(String adjunct) {
        this.adjunct = adjunct;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(_id, address._id) && Objects.equals(userId, address.userId) && Objects.equals(addressName, address.addressName) && Objects.equals(cep, address.cep) && Objects.equals(street, address.street) && Objects.equals(adjunct, address.adjunct) && Objects.equals(number, address.number) && Objects.equals(district, address.district) && Objects.equals(city, address.city) && Objects.equals(uf, address.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, userId, addressName, cep, street, adjunct, number, district, city, uf);
    }

    @Override
    public String toString() {
        return "Address{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", addressName='" + addressName + '\'' +
                ", city='" + city + '\'' +
                ", city='" + city + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public Address(@Valid AddressRequest addressRequest) {
        this.addressName = addressRequest.addressName();
        this.userId = addressRequest.userId();
        this.cep = addressRequest.cep();
        this.street = addressRequest.street();
        this.adjunct = addressRequest.adjunct();
        this.number = addressRequest.number();
        this.district = addressRequest.district();
        this.city = addressRequest.city();
        this.uf = addressRequest.uf();
    }

    public void update(@Valid AddressUpdate addressUpdate) {

        if (addressUpdate.addressName() != null) {
            this.addressName = addressUpdate.addressName();
        }

        if (addressUpdate.cep() != null) {
            this.cep = addressUpdate.cep();
        }

        if (addressUpdate.street() != null) {
            this.street = addressUpdate.street();
        }

        if (addressUpdate.adjunct() != null) {
            this.adjunct = addressUpdate.adjunct();
        }

        if (addressUpdate.number() != null) {
            this.number = addressUpdate.number();
        }

        if (addressUpdate.district() != null) {
            this.district = addressUpdate.district();
        }

        if (addressUpdate.city() != null) {
            this.city = addressUpdate.city();
        }

        if (addressUpdate.uf() != null) {
            this.uf = addressUpdate.uf();
        }
    }
}
