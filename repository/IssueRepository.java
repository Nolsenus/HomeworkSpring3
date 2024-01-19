package homework3.repository;

import homework3.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueRepository {

    private final List<Issue> issues = new ArrayList<>();


    public Issue getById(long id) {
        return issues.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean hasId(long id) {
        return issues.stream()
                .anyMatch(it -> it.getId() == id);
    }

    public List<Issue> getByReaderId(long readerId) {
        return issues.stream()
                .filter(it -> it.getReaderID() == readerId)
                .toList();
    }

    public long numberOfBooksOnReaderId(long readerId) {
        return issues.stream()
                .filter(it -> it.getReaderID() == readerId)
                .filter(it -> it.getReturnedAt() == null)
                .count();
    }

    public boolean add(Issue issue) {
        return issues.add(issue);
    }

}
