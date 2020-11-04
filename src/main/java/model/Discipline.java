package model;

import enums.DisciplineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "discipline_table")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discipline_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "HeadOfDiscipline", referencedColumnName = "user_id")
    private User headOfDiscipline;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private DisciplineType disciplineType;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<User> users;

    public Discipline(DisciplineType disciplineType) {
        this.disciplineType = disciplineType;
    }
}
