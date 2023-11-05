package banz.ai.marketing.bot.modelbehavior.behavior.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dialog")
public class Dialog {

    @Id
    @Column(name = "dialog_id")
    private Long id;

    @Column
    private Date createdAt;

    @OneToMany(mappedBy = "dialog", orphanRemoval = true)
    private List<DialogMessage> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dialog dialog = (Dialog) o;
        return Objects.equals(id, dialog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
