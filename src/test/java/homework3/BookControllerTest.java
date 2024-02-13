package homework3;

import homework3.model.Book;
import homework3.repository.BookRepository;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

public class BookControllerTest extends ApplicationTestBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;

    @Data
    static class TestBookResponse {
        private final long id;
        private final String name;
    }

    @Data
    static class TestBooleanResponse {
        private final boolean value;
    }

    @BeforeEach
    void prepareDB() {
        bookRepository.deleteAll();
    }

    @Test
    void testGetByIdSuccess() {
        Book expected = new Book("Name of Book");
        bookRepository.save(expected);
        long id = expected.getId();
        TestBookResponse response = webTestClient.get()
                .uri("/book/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestBookResponse.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(expected.getName(), response.getName());
    }

    @Test
    void testGetByIdNoId() {
        //Перед каждым тестом БД чистится, поэтому ожидаем, что нет первого элемента
        Assumptions.assumeFalse(bookRepository.existsById(1));
        webTestClient.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdDeleted() {
        Book book = new Book("Book Name");
        bookRepository.save(book);
        long id = book.getId();
        TestBooleanResponse didDelete = webTestClient.delete()
                .uri("/book/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestBooleanResponse.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(didDelete);
        Assertions.assertTrue(didDelete.isValue());
        Assertions.assertFalse(bookRepository.existsById(id));
    }

    @Test
    void testDeleteByIdNotDeleted() {
        Assumptions.assumeFalse(bookRepository.existsById(1));
        TestBooleanResponse response = webTestClient.delete()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestBooleanResponse.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isValue());
    }

    @Test
    @Transactional
    void testAddBook() {
        Book expected = new Book("BookName");
        TestBookResponse response = webTestClient.post()
                .uri("/book")
                .bodyValue(expected)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TestBookResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getName(), response.getName());
        Assertions.assertTrue(bookRepository.existsById(expected.getId()));
        Book created = bookRepository.getById(expected.getId());
        Assertions.assertEquals(created.getName(), expected.getName());
    }


}
