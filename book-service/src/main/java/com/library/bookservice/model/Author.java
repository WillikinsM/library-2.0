package com.library.bookservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.bookservice.dto.AuthorRequest;
import com.library.bookservice.dto.AuthorUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document(value = "author")
public class Author {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull
    private String nationality;
    @NotBlank
    private String description;

    public Author() {
    }

    public Author(String _id, String name, LocalDate birthDate, String nationality, String description) {
        this._id = _id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.description = description;
    }

    public static class AuthorBuilder {
        private String _id;
        private String name;
        private LocalDate birthDate;
        private String nationality;
        private String description;

        AuthorBuilder() {
        }

        public AuthorBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public AuthorBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthorBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public AuthorBuilder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public AuthorBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Author build() {
            return new Author(_id, name, birthDate, nationality, description);
        }
    }

    public static AuthorBuilder builder() {
        return new AuthorBuilder();
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
        Author author = (Author) o;
        return Objects.equals(_id, author._id) && Objects.equals(name, author.name) && Objects.equals(birthDate, author.birthDate) && Objects.equals(nationality, author.nationality) && Objects.equals(description, author.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, birthDate, nationality, description);
    }

    @Override
    public String toString() {
        return "Author{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", nationality='" + nationality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Author(AuthorRequest authorRequest) {
        this.name = authorRequest.name();
        this.birthDate = authorRequest.birthDate();
        this.nationality = authorRequest.nationality();
        this.description = authorRequest.description();
    }

    public void update(@Valid AuthorUpdate authorUpdate) {

        if (authorUpdate.name() != null) {
            this.name = authorUpdate.name();
        }

        if (authorUpdate.birthDate() != null) {
            this.birthDate = authorUpdate.birthDate();
        }

        if (authorUpdate.nationality() != null) {
            this.nationality = authorUpdate.nationality();
        }

        if (authorUpdate.description() != null) {
            this.description = authorUpdate.description();
        }
    }
}
