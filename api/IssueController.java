package homework3.api;


import homework3.model.Issue;
import homework3.services.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Issue")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @Operation(summary = "Issue a book to a reader",
            description = "Creates a new issue based on the issue request and adds it to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404",
                    description = "Either the book or the reader with the specified id was not found", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Reader with given id already has maximum allowed number of books", content = @Content)
    })
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
    @Operation(summary = "Return a book", description = "Adds a return date to the given issue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", description = "Issue with given id not found", content = @Content)
    })
    public ResponseEntity<Issue> closeIssue(@PathVariable long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.close(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by id", description = "Returns the issue with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Issue.class))),
            @ApiResponse(responseCode = "404", description = "Issue with given id not found", content = @Content)
    })
    public ResponseEntity<Issue> getIssue(@PathVariable long id) {
        try {
            Issue issue = issueService.get(id);
            if (!issue.failedToGetData()) {
                return ResponseEntity.status(HttpStatus.OK).body(issue);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }
}
