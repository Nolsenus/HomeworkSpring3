package homework3.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class IssueRequest {
    private final long bookId;
    private final long readerId;

    @JsonCreator
    public IssueRequest(long bookId, long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
    }
}
