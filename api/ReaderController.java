package homework3.api;

import homework3.model.Issue;
import homework3.model.Reader;
import homework3.services.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reader")
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping("/{id}")
    @Operation(summary = "Get reader by id", description = "Gets reader with provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned reader",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reader.class))),
            @ApiResponse(responseCode = "404", description = "Reader with given id not found", content = @Content)
    })
    public ResponseEntity<Reader> getReaderByID(@PathVariable long id) {
        try {
            Reader reader = readerService.get(id);
            if (!reader.failedToGetData()) {
                return ResponseEntity.status(HttpStatus.OK).body(reader);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/issues")
    @Operation(summary = "Get all issues of reader by id",
            description = "Returns a list of all issues ever given to reader with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned a list of issues", content =
                    {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Issue.class) ))}),
            @ApiResponse(responseCode = "204", description = "Reader exists, no issues found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reader with given id not found", content = @Content)
    })
    public ResponseEntity<List<Issue>> getIssuesOfReader(@PathVariable long id) {
        List<Issue> issues = readerService.getIssuesOfReader(id);
        if (issues == null) {
            return ResponseEntity.notFound().build();
        }
        if (issues.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reader by id", description = "Removes reader with given id from database")
    @ApiResponse(responseCode = "200", description = "Success, returns true if a reader was deleted, false otherwise",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> deleteReaderByID(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.delete(id));
    }

    @PostMapping
    @Operation(summary = "Create new reader", description = "Add provided reader to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reader.class))),
            @ApiResponse(responseCode = "500", description = "Could not create given reader", content = @Content)
    })
    public ResponseEntity<Reader> postReader(@RequestBody Reader reader) {
        try {
            Reader result = readerService.add(reader);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
