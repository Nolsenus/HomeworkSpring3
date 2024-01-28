package homework3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "issues")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Issue {

    private static final long DEFAULT_ID = 0L;
    private static final long DEFAULT_BOOK_ID = 0L;
    private static final long DEFAULT_READER_ID = 0L;
    private static final LocalDateTime DEFAULT_ISSUED_AT = null;
    private static long sequence = 1L;

    @Id
    private final long id;

    @Column(name = "book_id")
    private final long bookID;

    @Column(name = "reader_id")
    private final long readerID;

    @Column(name = "issued_at")
    private final LocalDateTime issuedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    public Issue() {
        this.id = DEFAULT_ID;
        this.bookID = DEFAULT_BOOK_ID;
        this.readerID = DEFAULT_READER_ID;
        this.issuedAt = DEFAULT_ISSUED_AT;
    }

    public Issue(long bookID, long readerID) {
        this(sequence++, bookID, readerID, LocalDateTime.now(), null);
    }

    public boolean failedToGetData() {
        return this.id == DEFAULT_ID &&
                this.bookID == DEFAULT_BOOK_ID &&
                this.readerID == DEFAULT_READER_ID &&
                this.issuedAt == DEFAULT_ISSUED_AT;
    }

    public void close(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
