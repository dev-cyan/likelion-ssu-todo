package likelion.todo.domain.todo.service;

import likelion.todo.domain.member.entity.Member;
import likelion.todo.domain.member.repository.MemberRepository;
import likelion.todo.domain.todo.dto.TodoCreateRequestDTO;
import likelion.todo.domain.todo.dto.TodoResponseDTO;
import likelion.todo.domain.todo.dto.TodoReviewRequestDTO;
import likelion.todo.domain.todo.dto.TodoUpdateRequestDTO;
import likelion.todo.domain.todo.entity.Todo;
import likelion.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.time.ZoneOffset;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoResponseDTO createTodo(Long memberId, TodoCreateRequestDTO req) {
        Member member = getMember(memberId);

        Todo todo = Todo.builder()
                .content(req.content())
                .date(req.date().withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime())
                .checked(false)
                .emoji(defaultEmoji(req.emoji()))
                .member(member)
                .build();

        todoRepository.save(todo);

        return toResponse(todo);
    }

    public List<TodoResponseDTO> getTodos(Long memberId, Integer month, Integer day) {
        getMember(memberId);

        List<Todo> todos = (month != null && day != null)
                ? todoRepository.findAllByMemberIdAndMonthAndDay(memberId, month, day)
                : todoRepository.findAllByMemberIdOrderByDateAsc(memberId);

        return todos.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TodoResponseDTO updateTodo(Long memberId, Long todoId, TodoUpdateRequestDTO req) {
        Todo todo = getTodo(memberId, todoId);
        todo.update(req.content(), req.date().withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime());
        todo.updateEmoji(defaultEmoji(req.emoji()));

        return toResponse(todo);
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) {
        Todo todo = getTodo(memberId, todoId);
        todoRepository.delete(todo);
    }

    @Transactional
    public TodoResponseDTO completeTodo(Long memberId, Long todoId) {
        Todo todo = getTodo(memberId, todoId);
        todo.complete();

        return toResponse(todo);
    }

    @Transactional
    public TodoResponseDTO reviewTodo(Long memberId, Long todoId, TodoReviewRequestDTO req) {
        Todo todo = getTodo(memberId, todoId);
        todo.updateEmoji(defaultEmoji(req.emoji()));

        return toResponse(todo);
    }

    private TodoResponseDTO toResponse(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getMember().getUsername(),
                todo.getDate().atOffset(ZoneOffset.UTC),
                todo.getContent(),
                todo.isChecked(),
                defaultEmoji(todo.getEmoji())
        );
    }

    private String defaultEmoji(String emoji) {
        return emoji == null ? "" : emoji;
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
    }

    private Todo getTodo(Long memberId, Long todoId) {
        return todoRepository.findByIdAndMemberId(todoId, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "투두를 찾을 수 없습니다."));
    }
}
