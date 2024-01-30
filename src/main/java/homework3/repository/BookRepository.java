package homework3.repository;

import homework3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book getById(long id);

    @Transactional
    void removeById(long id);

    boolean existsById(long id);
}
