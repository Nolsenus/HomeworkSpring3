package homework3.services;

import homework3.model.Book;
import homework3.model.Issue;
import homework3.model.Reader;
import homework3.repository.BookRepository;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UIService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public List<String> getBookStrings() {
        return bookRepository.getAll().stream()
                .map(Book::toString)
                .toList();
    }

    public List<String> getReaderStrings() {
        return readerRepository.getAll().stream()
                .map(Reader::toString)
                .toList();
    }

    public List<List<String>> getIssues() {
        return issueRepository.getAll().stream()
                .map(it -> {
                    List<String> res = new ArrayList<>();
                    res.add(bookRepository.getById(it.getBookID()).toString());
                    res.add(readerRepository.getById(it.getReaderID()).toString());
                    res.add(it.getIssuedAt().toString());
                    LocalDateTime returnedAt = it.getReturnedAt();
                    res.add(returnedAt == null ? "" : returnedAt.toString());
                    return res;
                }).toList();
    }

    public String getReaderName(long id) {
        Reader reader = readerRepository.getById(id);
        if (reader != null) {
            return reader.getName();
        }
        return "";
    }

    public List<String> getBookStringsOfReader(long id) {
        return issueRepository.getByReaderId(id).stream()
                .filter(it -> it.getReturnedAt() == null)
                .map(it -> bookRepository.getById(it.getBookID()).toString())
                .toList();
    }
}
