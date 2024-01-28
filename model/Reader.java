package homework3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "readers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reader {

    private static final long DEFAULT_ID = 0L;
    private static final String DEFAULT_NAME = null;

    private static long sequence = 1L;

    @Id
    private final long id;

    @Column(name = "name")
    private final String name;

    public Reader() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
    }

    @JsonCreator
    public Reader(String name) {
        this(sequence++, name);
    }

    public boolean failedToGetData() {
        return this.id == DEFAULT_ID && this.name.equals(DEFAULT_NAME);
    }
}
