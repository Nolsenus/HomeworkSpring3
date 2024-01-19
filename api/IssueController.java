package homework3.api;


import homework3.model.Issue;
import homework3.services.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<Issue> issue(@RequestBody IssueRequest request) {
        Issue issue;
        try {
           issue = issueService.issue(request);
           return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> closeIssue(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.close(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssue(@PathVariable long id) {
        Issue issue = issueService.get(id);
        if (issue== null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }
}
