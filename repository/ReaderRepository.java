package homework3.repository;

import homework3.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Reader getById(long id);

    boolean existsById(long id);

    @Transactional
    void removeById(long id);

}
