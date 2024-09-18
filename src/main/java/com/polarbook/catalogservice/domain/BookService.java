package com.polarbook.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // 생성자를 통해서 오토와이어, 필요한 의존성이 언제나 초기회되고 널이 반환되지 않으므로
    // 스프링팀의 권고에 따라서 생성자 기반 사용
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList(){
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn){
        // 책이 존지하지 않으면 오류 발생
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book){
        // 동일한 내역을 추가하려고 하면 오류 발셍
        if(bookRepository.existsByIsbn(book.isbn())){
            throw new BookAlreadyExistsException(book.isbn());
        }

        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book){
        return bookRepository.findByIsbn(isbn)
                .map(existsBook -> {
                    // isbn을 제외한 모든 정보를 업데이트
                    var bookToUpdate = new Book(
                            existsBook.id(),
                            existsBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            book.publisher(),
                            book.createdDate(),
                            book.lastModifiedDate(),
                            existsBook.version());  // 업데이트 성공시 증가
                    return bookRepository.save(bookToUpdate);
                })
                // 카탈로그에 존재하지 않는 책의 경우 카탈로그에 저장
                .orElseGet(() -> addBookToCatalog(book));
    }
}
