package homework3.api;

import homework3.model.Issue;
import homework3.model.Reader;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderByID(@PathVariable long id) {
        Reader reader = readerRepository.getById(id);
        if (reader == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @GetMapping("/{id}/issues")
    public ResponseEntity<List<Issue>> getIssuesOfReader(@PathVariable long id) {
        List<Issue> issues = issueRepository.getByReaderId(id);
        if (issues.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @DeleteMapping("/{id}")
    public boolean deleteReaderByID(@PathVariable long id) {
        return readerRepository.remove(id);
    }

    @PostMapping
    public ResponseEntity<Reader> postReader(@RequestBody Reader reader) {
        if (readerRepository.add(reader)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reader);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
