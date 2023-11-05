package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class DialogMessage {

    @Id
    @Column(name = "message_id")
    private Long id;

    @Column
    private int ordinalNumber;

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogMessage that = (DialogMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
