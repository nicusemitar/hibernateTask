package model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_t")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e_mail",unique = true)
    private String email;

    @Column(name = "username",unique = true)
    private String userName;

    @Column(name = "created")
    private String createdAt;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(mappedBy = "headOfDiscipline")
    private Discipline headOfDiscipline;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discipline discipline;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "r_id")
    )
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Task> taskList;

    public User(String name, String lname, String email, String userName, String createdAt, boolean enabled, Discipline discipline, List<Task> taskList, Set<Role> roleSet) {
        this.name = name;
        this.lastName = lname;
        this.email = email;
        this.userName = userName;
        this.createdAt = createdAt;
        this.enabled = enabled;
        this.discipline = discipline;
        this.roles = roleSet;
        this.taskList = taskList;
    }

    public User() {

    }

    public User(String name, String lastName, String eMail, String username) {
        this.name = name;
        this.lastName = lastName;
        this.email = eMail;
        this.userName = username;
    }

    @Override
    public String toString() {
        return "User[ " + " fName: " + name + " lName: " + lastName +
                " e-mail: " + email + " username:" + userName + " discipline: " + discipline.getDisciplineType() +
                " roles: " + roles + " tasks: " + taskList;
    }
}