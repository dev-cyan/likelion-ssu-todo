package likelion.todo.domain.todo.dto;

import java.time.OffsetDateTime;

public record TodoUpdateRequestDTO(
        String content,
        OffsetDateTime date,
        String emoji
) {
}
