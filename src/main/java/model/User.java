package model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user_t")
@SQLDelete(sql = "update user_t set name='DELETE', last_name='DELETE', e_mail='DELETE', enabled=false where user_id =?")
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
    private Date createdAt;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Discipline discipline;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "r_id")
    )
    @ToString.Exclude
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Task> taskList;

    public User(String name, String lname, String email, String userName, Date createdAt, boolean enabled, Discipline discipline, List<Task> taskList, Set<Role> roleSet) {
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

}