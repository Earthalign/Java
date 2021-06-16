package Wit_app.Wit_app.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Tasks description must be not empty ")
    private String description; //opis
    private boolean done; //zrobione/nie
    private LocalDateTime deadline;

    @Embedded
            private Audit audit = new Audit();


    public Task() {

    }

   public int getId() {
        return id;
    }

        void setId(final int id) {
        this.id = id;
    }

   public String getDescription() {
        return description;
    }

    void setDescription(final String description) {
        this.description = description;
    }

   public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(final Task source)
    {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }


}
