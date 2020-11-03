package model;

import enums.DisciplineType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "discipline_table")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discipline_id")
    private int id;

    @OneToOne
    @JoinColumn(name="HeadOfDiscipline",referencedColumnName = "user_id")
    private User headOfDiscipline;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private DisciplineType disciplineType;

    @OneToMany(mappedBy = "discipline",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;

    public Discipline() {

    }

    public Discipline(DisciplineType am) {
        this.disciplineType = am;
    }

    public void setUser(User user) {
        this.headOfDiscipline = user;
    }

    @Override
    public String toString() {
        return "Discipline{" +
                ", disciplineType=" + disciplineType +
                ", disciplineHead=" + headOfDiscipline +
                '}';
    }
}
