package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "model_response")
@Getter
@Setter
public class ModelResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_response_id")
    private Long id;

    @Column(name = "dialog_evaluation")
    private int dialogEvaluation;

    @Column(name = "offer_purchase")
    private boolean offerPurchase;

    @OneToOne
    @JoinColumn(name = "model_request_id")
    private ModelRequest modelRequest;

    @OneToMany(mappedBy = "modelResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StopTopic> stopTopics = new ArrayList<>();

    public void addStopTopic(StopTopic stopTopic) {
        stopTopic.setModelResponse(this);
        stopTopics.add(stopTopic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelResponse that = (ModelResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
