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

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;

    @Column(length = 20) //유니코드로 들어갈 수도 있기 때문에 varchar(1)은 위험
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Todo(String content, LocalDateTime date, boolean isChecked, String emoji, Member member) {
        this.content = content;
        this.date = date;
        this.isChecked = isChecked;
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
        this.isChecked = !this.isChecked;
    }

    public void updateEmoji(String emoji) {
        this.emoji = emoji;
    }
}
