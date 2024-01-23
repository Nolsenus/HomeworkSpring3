package homework3.services;

import homework3.model.Issue;
import homework3.model.Reader;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public Reader get(long id) {
        return readerRepository.getById(id);
    }

    public List<Issue> getIssuesOfReader(long id) {
        return issueRepository.getByReaderId(id);
    }

    public boolean delete(long id) {
        return readerRepository.remove(id);
    }

    public boolean add(Reader reader) {
        return readerRepository.add(reader);
    }
}
