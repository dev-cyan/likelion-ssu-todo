package likelion.todo.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record TodoResponseDTO(
        @JsonProperty("todo_id")
        Long todoId,
        String user,
        OffsetDateTime date,
        String content,
        @JsonProperty("is_checked")
        boolean isChecked,
        String emoji
) {
}
