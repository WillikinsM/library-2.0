package com.library.bookservice.model;

import com.library.bookservice.dto.PublisherRequest;
import com.library.bookservice.dto.PublisherUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(value = "publisher")
public class Publisher {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotBlank
    private String description;

    public Publisher() {
    }

    public Publisher(String _id, String name, String description) {
        this._id = _id;
        this.name = name;
        this.description = description;
    }

    public static class PublisherBuilder {
        private String _id;
        private String name;
        private String description;

        PublisherBuilder() {
        }

        public PublisherBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public PublisherBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PublisherBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Publisher build() {
            return new Publisher(_id, name, description);
        }
    }

    public static PublisherBuilder builder() {
        return new PublisherBuilder();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(_id, publisher._id) && Objects.equals(name, publisher.name) && Objects.equals(description, publisher.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, description);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Publisher(@Valid PublisherRequest publisherRequest) {
        this.name = publisherRequest.name();
        this.description = publisherRequest.description();
    }

    public void update(@Valid PublisherUpdate publisherUpdate) {

        if (publisherUpdate.name() != null) {
            this.name = publisherUpdate.name();
        }

        if (publisherUpdate.description() != null) {
            this.description = publisherUpdate.description();
        }
    }
}
