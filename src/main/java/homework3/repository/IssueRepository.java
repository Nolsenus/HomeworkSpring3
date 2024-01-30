package homework3.repository;

import homework3.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    Issue getById(long id);

    boolean existsById(long id);

    List<Issue> getByReaderID(long readerId);

    long countByReaderID(long readerId);
}
