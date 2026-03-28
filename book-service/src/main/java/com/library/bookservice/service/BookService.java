package com.library.bookservice.service;


import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.exceptions.NotFoundException;
import com.library.bookservice.model.Author;
import com.library.bookservice.model.Book;
import com.library.bookservice.model.Genre;
import com.library.bookservice.model.Publisher;
import com.library.bookservice.repository.AuthorRepository;
import com.library.bookservice.repository.BookRepository;
import com.library.bookservice.repository.GenreRepository;
import com.library.bookservice.repository.PublisherRepository;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"books"})
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final MongoTemplate mongoTemplate;
    private final int DUPLICATE_ERROR_CODE = 11000;

    public BookService(PublisherRepository publisherRepository, BookRepository bookRepository,
                       GenreRepository genreRepository, AuthorRepository authorRepository,
                       MongoTemplate mongoTemplate) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @CacheEvict(cacheNames = "books", allEntries = true)
    public BookDetails save(BookRequest bookRequest) {
        Book book = new Book(bookRequest);
        BookDetails bookDetails = null;

        try {
            bookDetails = new BookDetails(bookRepository.save(book));
            log.info("Book saved: {} - {}", bookDetails._id(), bookDetails.name());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return bookDetails;
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        log.info("Book's list returned");
        return books.stream().map(BookResponse::new).toList();
    }


    @CacheEvict(cacheNames = "books", allEntries = true)
    public void delete(String id) {
        findById(id);
        bookRepository.deleteById(id);
        log.warn("Book deleted, id: " + id);
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    public BookDetails findById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not Found: " + id + " , type: " + Book.class.getName()));
        log.info("Book returned: {} - {}", book.get_id(), book.getName());
        return new BookDetails(book);
    }

    @CacheEvict(cacheNames = "books", allEntries = true)
    public BookDetails update(BookUpdate bookUpdate) {
        Book book = bookRepository.findById(bookUpdate._id()).orElseThrow(() -> new NotFoundException("Object not Found: " + bookUpdate._id() + " , type: " + Book.class.getName()));
        book.update(bookUpdate);
        try {
            bookRepository.save(book);
            log.info("Book updated, id: " + book.get_id());
        } catch (MongoWriteException exception) {
            if (exception.getError().getCode() == DUPLICATE_ERROR_CODE) {
                throw new DuplicateKeyException("Duplicate Field: " + exception.getMessage(), exception);
            }
        }
        return new BookDetails(book);
    }


    @Cacheable(keyGenerator = "customKeyGenerator")
    public Page<BookResponse> findByFilters(List<String> authorIds, List<String> genreIds, List<String> publisherIds, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        try {
            Criteria criteria = new Criteria();

            if (authorIds != null && !authorIds.isEmpty()) {
                List<Author> authors = authorRepository.findAllById(authorIds);
                criteria.and("authors").in(authors);
            }

            if (genreIds != null && !genreIds.isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(genreIds);
                criteria.and("genres").in(genres);
            }

            if (publisherIds != null && !publisherIds.isEmpty()) {
                List<Publisher> publishers = publisherRepository.findAllById(publisherIds);
                criteria.and("publisher").in(publishers);
            }

            Query query = new Query(criteria);
            long total = mongoTemplate.count(query, Book.class);

            Sort sort = Sort.by(sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);

            query.with(PageRequest.of(pageNumber, pageSize).withSort(sort));

            List<Book> books = mongoTemplate.find(query, Book.class);

            return new PageImpl<>(books.stream().map(BookResponse::new).toList(),
                    PageRequest.of(pageNumber, pageSize), total);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }


}
