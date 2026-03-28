package com.library.bookservice.model;

import com.library.bookservice.dto.GenreRequest;
import com.library.bookservice.dto.GenreUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(value = "genre")
public class Genre {

    @Id
    private String _id;

    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotBlank
    private String description;

    public Genre() {
    }

    public Genre(String _id, String name, String description) {
        this._id = _id;
        this.name = name;
        this.description = description;
    }

    // Builder manual
    public static class GenreBuilder {
        private String _id;
        private String name;
        private String description;

        GenreBuilder() {
        }

        public GenreBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public GenreBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GenreBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Genre build() {
            return new Genre(_id, name, description);
        }
    }

    public static GenreBuilder builder() {
        return new GenreBuilder();
    }

    // Getters e Setters
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
        Genre genre = (Genre) o;
        return Objects.equals(_id, genre._id) && Objects.equals(name, genre.name) && Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, description);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Genre(GenreRequest genreRequest) {
        this.name = genreRequest.name();
        this.description = genreRequest.description();
    }

    public void update(@Valid GenreUpdate genreUpdate) {

        if (genreUpdate.name() != null) {
            this.name = genreUpdate.name();
        }

        if (genreUpdate.description() != null) {
            this.description = genreUpdate.description();
        }
    }
}
