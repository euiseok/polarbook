package com.polarbook.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

public record Book (

        // record = 불가변 객체
        // spring-boot-starter-validation 를 사용해서 유효성 제약 추가
        // jpa 에서는 entity 사용

        @Id
        Long id,                // primary key

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
            regexp = "^([0-9]{10}|[0-9]{13})$",
            message = "The ISBN format must be valid."
        )
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,
        @NotBlank(message = "The book author must be defined.")
        String author,

        // 숫자 : NotNull, String/CharSequence : NotBlank / Collection 등 : NotEmpty
        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        // 낙관적 잠금?
        @Version
        int version

){
        public static Book of(String isbn, String title, String author, Double price){
                return new Book(
                        // id가 null 이고, version 이 0 이면 새로운 데이터
                        null, isbn, title, author, price, null, null, 0
                );
        }

}
