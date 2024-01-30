package homework3.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IssueRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private final long bookId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private final long readerId;

    @JsonCreator
    public IssueRequest(long bookId, long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
    }
}
