package homework3.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Issue {

    private static long sequence = 1L;

    private final long id;
    private final long bookID;
    private final long readerID;
    private final LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public Issue(long bookID, long readerID) {
        this(sequence++, bookID, readerID, LocalDateTime.now(), null);
    }

    public void close(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
