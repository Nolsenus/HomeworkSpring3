package homework3.api;

import homework3.model.Issue;
import homework3.model.Reader;
import homework3.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderByID(@PathVariable long id) {
        Reader reader = readerService.get(id);
        if (reader == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @GetMapping("/{id}/issues")
    public ResponseEntity<List<Issue>> getIssuesOfReader(@PathVariable long id) {
        List<Issue> issues = readerService.getIssuesOfReader(id);
        if (issues.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @DeleteMapping("/{id}")
    public boolean deleteReaderByID(@PathVariable long id) {
        return readerService.delete(id);
    }

    @PostMapping
    public ResponseEntity<Reader> postReader(@RequestBody Reader reader) {
        if (readerService.add(reader)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reader);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
