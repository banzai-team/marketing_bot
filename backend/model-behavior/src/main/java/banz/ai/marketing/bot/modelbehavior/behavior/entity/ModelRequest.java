package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "model_request")
@Getter
@Setter
public class ModelRequest {

    @Id
    @Column(name = "model_request_id")
    private Long id;

    @Column(name = "is_operator")
    private boolean isOperator;

    @Column(name = "additional_text")
    private String text;

    @OneToOne(mappedBy = "modelRequest")
    private ModelResponse modelResponse;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @OneToMany(mappedBy = "modelRequest", orphanRemoval = true)
    private List<ModelRequestMessage> messages = new ArrayList<>();

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
