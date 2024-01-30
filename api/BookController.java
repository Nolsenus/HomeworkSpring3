package homework3.api;

import homework3.model.Book;
import homework3.services.BookService;
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

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Returns the book with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "Book with given id not found", content = @Content)
    })
    public ResponseEntity<Book> getBookByID(@PathVariable long id) {
        Book book = bookService.get(id);
        try {
            if (!book.failedToGetData()) {
                return ResponseEntity.status(HttpStatus.OK).body(book);
            }
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Removes book with given id from database")
    @ApiResponse(responseCode = "200", description = "Success, returns true if a book was deleted, false otherwise",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> deleteBookByID(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.delete(id));
    }

    @PostMapping
    @Operation(summary = "Add new book", description = "Creates given book and adds it to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "500", description = "Could not add given book to database", content = @Content)
    })
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        try {
            Book result = bookService.add(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
