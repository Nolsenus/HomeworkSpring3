package homework3;


import homework3.model.Issue;
import homework3.model.Reader;
import homework3.repository.BookRepository;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReaderControllerTest  extends ApplicationTestBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    static class TestResponseReader {
        private final long id;
        private final String name;
    }

    @BeforeEach
    void clearDB() {
        readerRepository.deleteAll();
    }

    @Test
    void testGetByIdSuccess() {
        Reader expected = new Reader("Name");
        readerRepository.save(expected);
        long id = expected.getId();
        TestResponseReader response = webTestClient.get()
                .uri("/reader/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestResponseReader.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(expected.getName(), response.getName());
    }

    @Test
    void testGetByIdNotFound() {
        Assumptions.assumeFalse(readerRepository.existsById(1));
        webTestClient.get()
                .uri("/reader/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdSuccess() {
        Reader reader = new Reader("Name");
        readerRepository.save(reader);
        Boolean response = webTestClient.delete()
                .uri("/reader/" + reader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response);
    }

    @Test
    void testDeleteByIdNoId() {
        Assumptions.assumeFalse(readerRepository.existsById(1));
        Boolean response = webTestClient.delete()
                .uri("/reader/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response);
    }

    @Test
    @Transactional
    void testPost() {
        Reader expected = new Reader("ReaderName");
        TestResponseReader response = webTestClient.post()
                .uri("/reader")
                .bodyValue(expected)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TestResponseReader.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getName(), response.getName());
        Reader created = readerRepository.getById(expected.getId());
        Assertions.assertNotNull(created);
        Assertions.assertEquals(expected.getName(), created.getName());
    }

}
