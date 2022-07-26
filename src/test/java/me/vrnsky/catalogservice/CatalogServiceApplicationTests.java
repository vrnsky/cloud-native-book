package me.vrnsky.catalogservice;

import me.vrnsky.catalogservice.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestCreateANewBook() {
        var book = Book.build("1231231230", "Title", "Author", 9.90);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                            assertThat(actualBook).isNotNull();
                            assertThat(actualBook.isbn()).isEqualTo(book.isbn());
                        }
                );
    }

}
