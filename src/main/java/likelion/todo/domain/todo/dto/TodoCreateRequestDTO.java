package likelion.todo.domain.todo.dto;

import java.time.LocalDateTime;

public record TodoCreateRequestDTO(
        String content,
        LocalDateTime date
) {
}
