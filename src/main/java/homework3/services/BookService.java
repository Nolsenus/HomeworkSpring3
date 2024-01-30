package homework3.services;


import homework3.model.Book;
import homework3.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        fillWithData();
    }

    public Book get(long id) {
        return bookRepository.getById(id);
    }

    public boolean delete(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.removeById(id);
            return true;
        }
        return false;
    }

    public Book add(Book book) {
        return bookRepository.save(book);
    }

    private void fillWithData() {
        bookRepository.saveAll(List.of(
                new Book("Война и Мир"),
                new Book("Мёртвые Души"),
                new Book("Чистый Код"),
                new Book("Капитанская Дочка"),
                new Book("Отцы и Дети")));
    }
}
