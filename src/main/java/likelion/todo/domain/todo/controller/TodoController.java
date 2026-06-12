package likelion.todo.domain.todo.controller;

import likelion.todo.domain.todo.dto.TodoCreateRequestDTO;
import likelion.todo.domain.todo.dto.TodoResponseDTO;
import likelion.todo.domain.todo.dto.TodoReviewRequestDTO;
import likelion.todo.domain.todo.dto.TodoUpdateRequestDTO;
import likelion.todo.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/{userId}")
    public ResponseEntity<TodoResponseDTO> createTodo(
            @PathVariable Long userId,
            @RequestBody TodoCreateRequestDTO req
    ) {
        return ResponseEntity.ok(todoService.createTodo(userId, req));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TodoResponseDTO>> getTodos(
            @PathVariable Long userId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day
    ) {
        return ResponseEntity.ok(todoService.getTodos(userId, month, day));
    }

    @PatchMapping("/{userId}/{todoId}")
    public ResponseEntity<TodoResponseDTO> updateTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequestDTO req
    ) {
        return ResponseEntity.ok(todoService.updateTodo(userId, todoId, req));
    }

    @DeleteMapping("/{userId}/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId
    ) {
        todoService.deleteTodo(userId, todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/{todoId}/check")
    public ResponseEntity<TodoResponseDTO> completeTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId
    ) {
        return ResponseEntity.ok(todoService.completeTodo(userId, todoId));
    }

    @PatchMapping("/{userId}/{todoId}/reviews")
    public ResponseEntity<TodoResponseDTO> reviewTodo(
            @PathVariable Long userId,
            @PathVariable Long todoId,
            @RequestBody TodoReviewRequestDTO req
    ) {
        return ResponseEntity.ok(todoService.reviewTodo(userId, todoId, req));
    }
}
