package banz.ai.marketing.bot.modelbehavior.feedback.entity;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_feedback")
@Getter
@Setter
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "is_correct", nullable = false)
  private boolean isCorrect;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @Column(name = "applied_at")
  private Date appliedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private FeedbackStatus status;

  @ManyToOne
  @JoinColumn(name = "model_response_id")
  private ModelResponse modelResponse;

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
