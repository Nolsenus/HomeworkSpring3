package homework3.api;

import homework3.model.Book;
import homework3.services.BookService;
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
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
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
    public boolean deleteBookByID(@PathVariable long id) {
        return bookService.delete(id);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        try {
            Book result = bookService.add(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
