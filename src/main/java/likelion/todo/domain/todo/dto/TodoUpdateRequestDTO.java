package likelion.todo.domain.todo.dto;

import java.time.LocalDateTime;

public record TodoUpdateRequestDTO(
        String content,
        LocalDateTime date,
        String emoji
) {
}
