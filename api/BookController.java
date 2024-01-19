package homework3.api;

import homework3.model.Book;
import homework3.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable long id) {
        Book book = bookRepository.getById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBookByID(@PathVariable long id) {
        return bookRepository.remove(id);
    }

    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody Book book) {
        if (bookRepository.add(book)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
