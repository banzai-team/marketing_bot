package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "model_request")
@Getter
@Setter
public class ModelRequest {

    @Id
    @Column(name = "model_request_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "is_operator")
    private boolean isOperator;

    @Column(name = "additional_text")
    private String text;

    @Column(name = "performed_at")
    private Date performedAt;

    @OneToOne(mappedBy = "modelRequest", cascade = CascadeType.ALL)
    private ModelResponse modelResponse;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @OneToMany(mappedBy = "modelRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ModelRequestMessage> messages = new ArrayList<>();

    public void setModelResponse(ModelResponse modelResponse) {
        this.modelResponse = modelResponse;
        modelResponse.setModelRequest(this);
    }

    public void addMessage(ModelRequestMessage message) {
        message.setModelRequest(this);
        messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelRequest that = (ModelRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
