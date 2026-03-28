package com.library.bookservice.model;

import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(value = "book")
public class Book {

    @Id
    private String _id;
    @NotBlank
    @Indexed(unique = true)
    private String name;
    @DBRef
    private List<Author> authors = new ArrayList<>();
    @NotNull
    private int pages;
    @DBRef
    private List<Genre> genres = new ArrayList<>();
    @NotNull
    private int publicationYear;
    @NotBlank
    private String asin;
    @NotBlank
    private String summary;
    @DBRef
    private Publisher publisher;
    @NotBlank
    private String bookCover;
    @NotNull
    private BigDecimal price;

    public Book() {
    }

    public Book(String _id, String name, List<Author> authors, int pages, List<Genre> genres, int publicationYear, String asin, String summary, Publisher publisher, String bookCover, BigDecimal price) {
        this._id = _id;
        this.name = name;
        this.authors = authors != null ? authors : new ArrayList<>();
        this.pages = pages;
        this.genres = genres != null ? genres : new ArrayList<>();
        this.publicationYear = publicationYear;
        this.asin = asin;
        this.summary = summary;
        this.publisher = publisher;
        this.bookCover = bookCover;
        this.price = price;
    }

    public static class BookBuilder {
        private String _id;
        private String name;
        private List<Author> authors = new ArrayList<>();
        private int pages;
        private List<Genre> genres = new ArrayList<>();
        private int publicationYear;
        private String asin;
        private String summary;
        private Publisher publisher;
        private String bookCover;
        private BigDecimal price;

        BookBuilder() {
        }

        public BookBuilder _id(String _id) {
            this._id = _id;
            return this;
        }

        public BookBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BookBuilder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public BookBuilder pages(int pages) {
            this.pages = pages;
            return this;
        }

        public BookBuilder genres(List<Genre> genres) {
            this.genres = genres;
            return this;
        }

        public BookBuilder publicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public BookBuilder asin(String asin) {
            this.asin = asin;
            return this;
        }

        public BookBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public BookBuilder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder bookCover(String bookCover) {
            this.bookCover = bookCover;
            return this;
        }

        public BookBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Book build() {
            return new Book(_id, name, authors, pages, genres, publicationYear, asin, summary, publisher, bookCover, price);
        }
    }

    public static BookBuilder builder() {
        return new BookBuilder();
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages && publicationYear == book.publicationYear && Objects.equals(_id, book._id) && Objects.equals(name, book.name) && Objects.equals(authors, book.authors) && Objects.equals(genres, book.genres) && Objects.equals(asin, book.asin) && Objects.equals(summary, book.summary) && Objects.equals(publisher, book.publisher) && Objects.equals(bookCover, book.bookCover) && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, authors, pages, genres, publicationYear, asin, summary, publisher, bookCover, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", pages=" + pages +
                ", publicationYear=" + publicationYear +
                ", asin='" + asin + '\'' +
                ", summary='" + summary + '\'' +
                ", bookCover='" + bookCover + '\'' +
                ", price=" + price +
                '}';
    }


    public Book(BookRequest bookRequest) {
        this.name = bookRequest.name();
        this.authors = bookRequest.authors();
        this.pages = bookRequest.pages();
        this.genres = bookRequest.genres();
        this.publicationYear = bookRequest.publicationYear();
        this.asin = bookRequest.asin();
        this.summary = bookRequest.summary();
        this.publisher = bookRequest.publisher();
        this.bookCover = bookRequest.bookCover();
        this.price = bookRequest.price();
    }

    public void update(@Valid BookUpdate bookUpdate) {

        if (bookUpdate.name() != null) {
            this.name = bookUpdate.name();
        }

        if (bookUpdate.authors() != null) {
            this.authors = bookUpdate.authors();
        }

        if (bookUpdate.pages() != 0) {
            this.pages = bookUpdate.pages();
        }

        if (bookUpdate.genres() != null) {
            this.genres = bookUpdate.genres();
        }

        if (bookUpdate.publicationYear() != 0) {
            this.publicationYear = bookUpdate.publicationYear();
        }

        if (bookUpdate.asin() != null) {
            this.asin = bookUpdate.asin();
        }

        if (bookUpdate.summary() != null) {
            this.summary = bookUpdate.summary();
        }

        if (bookUpdate.publisher() != null) {
            this.publisher = bookUpdate.publisher();
        }

        if (bookUpdate.bookCover() != null) {
            this.bookCover = bookUpdate.bookCover();
        }

        if (bookUpdate.price() != null) {
            this.price = bookUpdate.price();
        }
    }
}
