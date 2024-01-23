package homework3.api;

import homework3.model.Book;
import homework3.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable long id) {
        Book book = bookService.get(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBookByID(@PathVariable long id) {
        return bookService.delete(id);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        if (bookService.add(book)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
