package com.polarbook.catalogservice.web;

import com.polarbook.catalogservice.domain.Book;
import com.polarbook.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")        // /books
public class BookController {

    private final BookService bookService;

    // 생정자 기반
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    // GET
    @GetMapping
    public Iterable<Book> get(){
        return bookService.viewBookList();
    }

    // GET  book/{isbn}
    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn){
        return bookService.viewBookDetails(isbn);
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)     // 성공적이면 201 상태코드
    public Book post(@Valid @RequestBody Book book){
        return bookService.addBookToCatalog(book);
    }

    // DELETE  /book/{isbn}
    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 성공적이면 204 상태코드
    public void delete(@PathVariable String isbn){
        bookService.removeBookFromCatalog(isbn);
    }

    // PUT /book/{isbn}
    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book){
        return bookService.editBookDetails(isbn, book);
    }
}
