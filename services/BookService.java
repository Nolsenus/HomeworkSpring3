package homework3.services;


import homework3.model.Book;
import homework3.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book get(long id) {
        return bookRepository.getById(id);
    }

    public boolean delete(long id) {
        return bookRepository.remove(id);
    }

    public boolean add(Book book) {
        return bookRepository.add(book);
    }
}
