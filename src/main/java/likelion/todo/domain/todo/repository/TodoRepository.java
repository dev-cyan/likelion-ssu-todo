package likelion.todo.domain.todo.repository;

import likelion.todo.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMemberIdOrderByDateAsc(Long memberId);

    @Query("""
            select t
            from Todo t
            where t.member.id = :memberId
            and month(t.date) = :month
            and day(t.date) = :day
            order by t.date asc
            """)
    List<Todo> findAllByMemberIdAndMonthAndDay(
            @Param("memberId") Long memberId,
            @Param("month") Integer month,
            @Param("day") Integer day
    );

    Optional<Todo> findByIdAndMemberId(Long todoId, Long memberId);
}
