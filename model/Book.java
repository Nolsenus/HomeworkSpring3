package homework3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

    private static final long DEFAULT_ID = 0L;
    private static final String DEFAULT_NAME = null;
    private static long sequence = 1L;

    @Id
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private final long id;

    @Column(name = "name")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @JsonCreator
    public Book(String name) {
        this(sequence++, name);
    }

    public Book() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
    }

    public boolean failedToGetData() {
        return this.id == DEFAULT_ID && this.name.equals(DEFAULT_NAME);
    }
}
