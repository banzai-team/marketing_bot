package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
