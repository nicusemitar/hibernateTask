package model;

import enums.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "task_table")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Task() {
    }

    public Task(String name, String description, LocalDate startDate, LocalDate dueDate, Status status) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public String toString() {
        return "[ " + "name:" + name + "dueDate: " + dueDate + "status: " + status + "]";
    }
}