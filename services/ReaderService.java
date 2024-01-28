package homework3.services;

import homework3.model.Issue;
import homework3.model.Reader;
import homework3.repository.IssueRepository;
import homework3.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    public ReaderService(ReaderRepository rr, IssueRepository ir) {
        this.readerRepository = rr;
        this.issueRepository = ir;
        fillWithData();
    }

    public Reader get(long id) {
        return readerRepository.getById(id);
    }

    public List<Issue> getIssuesOfReader(long id) {
        return issueRepository.getByReaderID(id);
    }

    public boolean delete(long id) {
        if (readerRepository.existsById(id)) {
            readerRepository.removeById(id);
            return true;
        }
        return false;
    }

    public Reader add(Reader reader) {
        return readerRepository.save(reader);
    }

    private void fillWithData() {
        readerRepository.saveAll(List.of(
                new Reader("Игорь"),
                new Reader("Олег"),
                new Reader("Анна"),
                new Reader("Елизавета"),
                new Reader("Саша")));
    }
}
