package com.library.bookservice.controller;

import com.library.bookservice.dto.BookDetails;
import com.library.bookservice.dto.BookRequest;
import com.library.bookservice.dto.BookResponse;
import com.library.bookservice.dto.BookUpdate;
import com.library.bookservice.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDetails> create(@RequestBody @Valid BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        BookDetails bookDetails = bookService.save(bookRequest);
        URI uri = uriComponentsBuilder.path("/api/book/{id}").buildAndExpand(bookDetails._id()).toUri();
        return ResponseEntity.created(uri).body(bookDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDetails> delete(@PathVariable String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetails> findById(@PathVariable String id) {
        BookDetails bookDetails = bookService.findById(id);
        return ResponseEntity.ok().body(bookDetails);
    }

    @PutMapping
    public ResponseEntity<BookDetails> update(@RequestBody @Valid BookUpdate bookUpdate) {
        BookDetails bookDetails = bookService.update(bookUpdate);
        return ResponseEntity.ok().body(bookDetails);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<BookResponse>>findAll(){
       return ResponseEntity.ok().body(bookService.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> findByFilters(@RequestParam(required = false) List<String> authorIds,
                                                            @RequestParam(required = false) List<String> genreIds,
                                                            @RequestParam(required = false) List<String> publisherIds,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "name") String sortBy,
                                                            @RequestParam(defaultValue = "desc") String sortDirection){

        Page<BookResponse> books = bookService.findByFilters(authorIds, genreIds, publisherIds, page, size,sortBy,sortDirection);
        return ResponseEntity.ok().body(books);
    }

}
