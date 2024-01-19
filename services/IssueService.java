package homework3.services;

import homework3.api.IssueRequest;
import homework3.model.Issue;
import homework3.repository.BookRepository;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;
    @Value("${application.issue.max-allowed-books:1}")
    private int MAX_BOOKS;

    public Issue issue(IssueRequest request) {
        long bookId = request.getBookId();
        long readerId = request.getReaderId();
        if (!bookRepository.hasId(bookId)) {
            throw new NoSuchElementException(String.format("Не найдена книга с Id %d", bookId));
        }
        if (!readerRepository.hasId(readerId)) {
            throw new NoSuchElementException(String.format("Не найден читатель с Id %d", readerId));
        }
        if (issueRepository.numberOfBooksOnReaderId(readerId) >= MAX_BOOKS) {
            throw new RuntimeException(String.format("У читателя с Id %d слишком много невозвращённых книг", readerId));
        }
        Issue issue = new Issue(bookId, readerId);
        issueRepository.add(issue);
        return issue;
    }

    public Issue get(long id) {
        return issueRepository.getById(id);
    }

    public Issue close(long id) {
        if (!issueRepository.hasId(id)) {
            throw new NoSuchElementException(String.format("Не найдена выдача с Id %d", id));
        }
        Issue issue = issueRepository.getById(id);
        issue.close(LocalDateTime.now());
        return issue;
    }
}
