package com.polarbook.catalogservice;

import com.polarbook.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTests {

    private static Validator validator;

    // Test 전 가장 먼저 수행할 코드 블록
    @BeforeAll
    static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // 테스트 케이스
    @Test
    void whenAllFieldCorrectThenValidationSucceeds(){
        // 유효한 정보로 생성한다.
        var book = Book.of("1234567890", "Title", "Author", 9.8, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails(){
        // ISBN 값이 포맷에 유효하지 않음을 테스트
        // new Book 과 Book.of 차이
        var book = Book.of("a12345678", "Title", "Author", 9.2, null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}
