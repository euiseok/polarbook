package com.polarbook.catalogservice.demo;

import com.polarbook.catalogservice.domain.Book;
import com.polarbook.catalogservice.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {

    // 생성자 주입방식에서만 final 사용가능, 안됨
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    //
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){

        bookRepository.deleteAll();

        var book1 = Book.of("12345678", "Northern Lights","Syrial", 9.9, null);
        var book2 = Book.of("22335678", "Polar Journey","Nathon", 6.8, "Random House");

        bookRepository.saveAll(List.of(book1, book2));
    }

    /*
    postgres db를 도커에서 실행
    --name : 컨테이너이름
    POSTGRES_DB=polardb_catalog : 디비서비스명

    docker run -d \
            --name polar-postgres \
            -e POSTGRES_USER=user \
            -e POSTGRES_PASSWORD=password \
            -e POSTGRES_DB=polardb_catalog \
            -p 5432:5432 \
    postgres:14.4

     */
}
