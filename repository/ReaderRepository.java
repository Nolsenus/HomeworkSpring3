package homework3.repository;

import homework3.model.Reader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReaderRepository {

    private final List<Reader> readers;

    public ReaderRepository() {
        readers  = new ArrayList<>();
        fillWithData();
    }

    private void fillWithData() {
        readers.addAll(List.of(
                new Reader("Игорь"),
                new Reader("Олег"),
                new Reader("Анна"),
                new Reader("Елизавета"),
                new Reader("Саша")));
    }

    public Reader getById(long id) {
        return readers.stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean hasId(long id) {
        return readers.stream().anyMatch(it -> it.getId() == id);
    }

    public boolean remove(Reader reader) {
        return readers.remove(reader);
    }

    public boolean remove(long id) {
        Reader found = getById(id);
        return found != null && readers.remove(found);
    }

    public boolean add(Reader reader) {
        return readers.add(reader);
    }

}
