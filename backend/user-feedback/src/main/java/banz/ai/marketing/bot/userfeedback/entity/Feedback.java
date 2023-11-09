package banz.ai.marketing.bot.userfeedback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private Long id;

  @Column(name = "model_request_id")
  private Long modelRequestId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "is_correct")
  private boolean isCorrect;

  @Column(name = "created_at")
  private Date createdAt;

  @Column(name = "applied_at", nullable = true)
  private Date appliedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private FeedbackStatus status;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Feedback feedback = (Feedback) o;
    return Objects.equals(id, feedback.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
