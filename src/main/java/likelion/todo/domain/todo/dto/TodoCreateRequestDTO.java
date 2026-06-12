package likelion.todo.domain.todo.dto;

import java.time.OffsetDateTime;

public record TodoCreateRequestDTO(
        String content,
        OffsetDateTime date,
        String emoji
) {
}
