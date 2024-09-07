package com.polarbook.catalogservice;

import com.polarbook.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

// 완전한 스프링 웹 컨텍스트 + 임의 포트로 리슨하는 서블릿 컨테이너
// 서버를 기동하는 테스트
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

    // REST end point
    @Autowired
    private WebTestClient webTestClient;

    // POST 호출
    @Test
    void whenPostRequestThenBookCreated(){

        var expectedBook = new Book("1231231231", "Title", "Author", 9.9);

        // post http방식
        // uri  서비스 경로
        // bodyValue  POST 방식의 값
        // exchange 전송
        // isCreated()  HTTP 201
        // expectBody 응답본문

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

}
