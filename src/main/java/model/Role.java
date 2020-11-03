package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@Table(name = "role_table")
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> userSet = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

