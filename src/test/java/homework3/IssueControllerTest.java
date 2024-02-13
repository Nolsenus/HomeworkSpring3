package homework3;

import homework3.api.IssueRequest;
import homework3.model.Issue;
import homework3.repository.IssueRepository;
import homework3.services.BookService;
import homework3.services.ReaderService;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IssueControllerTest extends ApplicationTestBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;


    @Data
    static class TestIssueResponse {
        private final long id;
        private final long bookID;
        private final long readerID;
        private final LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @BeforeEach
    void clearDB() {
        issueRepository.deleteAll();
    }

    @Test
    void testGetByIdSuccess() {
        Issue expected = new Issue(1, 1);
        issueRepository.save(expected);
        TestIssueResponse response = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestIssueResponse.class)
                .returnResult().getResponseBody();
        compareIssues(expected, response);
    }

    @Test
    @Transactional
    void testPostSuccess() {
        IssueRequest request = new IssueRequest(1, 1);
        TestIssueResponse response = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TestIssueResponse.class)
                .returnResult().getResponseBody();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(request.getBookId(), response.getBookID());
        Assertions.assertEquals(request.getReaderId(), response.getReaderID());
        Issue created = issueRepository.getById(response.getId());
        compareIssues(created, response);
    }

    @Test
    void testPostNotFound() {
        IssueRequest request = new IssueRequest(-1, -1);
        webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testPostConflict() {
        List<Issue> issues = List.of(
                new Issue(1, 1),
                new Issue(2, 1),
                new Issue(3, 1),
                new Issue(4, 1),
                new Issue(5, 1)
        );
        issueRepository.saveAll(issues);
        IssueRequest request = new IssueRequest(1, 1);
        webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testPutSuccess() {
        Issue expected = new Issue(1, 1);
        issueRepository.save(expected);
        TestIssueResponse response = webTestClient.put()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestIssueResponse.class)
                .returnResult().getResponseBody();
        compareIssuesNoReturnedAt(expected, response);
        Assertions.assertNotNull(response.getReturnedAt());
    }

    @Test
    void testPutNotFound() {
        Assumptions.assumeFalse(issueRepository.existsById(1));
        webTestClient.put()
                .uri("/issue/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    void compareIssuesNoReturnedAt(Issue expected, TestIssueResponse response) {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getBookID(), response.getBookID());
        Assertions.assertEquals(expected.getReaderID(), response.getReaderID());
        compareLDT(expected.getIssuedAt(), response.getIssuedAt());
    }

    void compareIssues(Issue expected, Issue response) {
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expected.getId(), response.getId());
        Assertions.assertEquals(expected.getBookID(), response.getBookID());
        Assertions.assertEquals(expected.getReaderID(), response.getReaderID());
        compareLDT(expected.getIssuedAt(), response.getIssuedAt());
        compareLDT(expected.getReturnedAt(), response.getReturnedAt());
    }

    void compareIssues(Issue expected, TestIssueResponse response) {
        compareIssuesNoReturnedAt(expected, response);
        compareLDT(expected.getReturnedAt(), response.getReturnedAt());
    }

    void compareLDT(LocalDateTime first, LocalDateTime second) {
        if (first == null) {
            Assertions.assertNull(second);
            return;
        }
        Assertions.assertNotNull(second);
        Assertions.assertEquals(first.getYear(), second.getYear());
        Assertions.assertEquals(first.getMonth(), second.getMonth());
        Assertions.assertEquals(first.getDayOfMonth(), second.getDayOfMonth());
        Assertions.assertEquals(first.getHour(), second.getHour());
        Assertions.assertEquals(first.getMinute(), second.getMinute());
        Assertions.assertEquals(first.getSecond(), second.getSecond());
    }

}
