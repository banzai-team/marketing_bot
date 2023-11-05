package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "stop_topic")
@Getter
@Setter
public class StopTopic {

    @Id
    @Column(name = "stop_topic_id")
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "model_response_id")
    private ModelResponse modelResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopTopic stopTopic = (StopTopic) o;
        return Objects.equals(id, stopTopic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
