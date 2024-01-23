package homework3.repository;

import homework3.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository() {
        books  = new ArrayList<>();
        fillWithData();
    }

    private void fillWithData() {
        books.addAll(List.of(
                new Book("Война и Мир"),
                new Book("Мёртвые Души"),
                new Book("Чистый Код"),
                new Book("Капитанская Дочка"),
                new Book("Отцы и Дети")));
    }

    public List<Book> getAll() {
        return List.copyOf(books);
    }

    public Book getById(long id) {
        return books.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean hasId(long id) {
        return books.stream()
                .anyMatch(it -> it.getId() == id);
    }

    public boolean remove(Book book) {
        return books.remove(book);
    }

    public boolean remove(long id) {
        Book found = getById(id);
        return found != null && books.remove(found);
    }

    public boolean add(Book book) {
        return books.add(book);
    }

}
