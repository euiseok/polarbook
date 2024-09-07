package com.polarbook.catalogservice;

// 서비스 전체 컨텍스트를 로드하는 경우보다 필요한 부분만 로드해서 통합 테스트 가능

import com.polarbook.catalogservice.domain.BookNotFoundException;
import com.polarbook.catalogservice.domain.BookService;
import com.polarbook.catalogservice.web.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)       // spring MVC 컴포넌트에 중점, 명시적으로 BookController 타깃
public class BookControllerTests {

    // 권장하지는 않지만 테스트시에는 사용
    // MockMvc 는 톰캣과 같은 서버를 로드하지 않고 웹 엔드포인트를 테스트
    @Autowired
    private MockMvc mockMvc;        // 모의 환경에서 웝 계층 테스트

    //
    @MockBean
    private BookService bookService;    // BookService 모의 객체


    @Test
    void whenGetBookNotExistingThenShouldReturn484() throws Exception{

        String isbn = "73737313940";
        // 모의 객체가 어떻게 작동할지 규정 -> 무엇을 리턴할지.
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);

        // mvc 는 http get 요청 수행
        // 결과는 404를 예상
        mockMvc
            .perform(get("/books/"+isbn))
            .andExpect(status().isNotFound());
    }
}
