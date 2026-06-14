package likelion.todo.domain.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import likelion.todo.domain.member.entity.Member;
import likelion.todo.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean checked;

    @Column(length = 20)
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Todo(String content, LocalDateTime date, boolean checked, String emoji, Member member) {
        this.content = content;
        this.date = date;
        this.checked = checked;
        this.emoji = emoji;
        this.member = member;
    }

    public void update(String content, LocalDateTime date) {
        if (content != null) {
            this.content = content;
        }
        if (date != null) {
            this.date = date;
        }
    }

    public void complete() {
        this.checked = !this.checked;
    }

    public void updateEmoji(String emoji) {
        this.emoji = emoji;
    }
}
