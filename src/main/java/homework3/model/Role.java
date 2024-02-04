package homework3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    private static final long DEFAULT_ID = 0L;
    private static final String DEFAULT_NAME = "";
    private static final Set<User> DEFAULT_USERS = null;

    private static long counter = 0L;

    @Id
    private final long id;

    @Column(name = "name")
    private final String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private final Set<User> users;

    public Role() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
        this.users = DEFAULT_USERS;
    }

    public Role(String name) {
        this.id = counter++;
        this.name = name;
        this.users = new HashSet<>();
    }

    public void addUser(User user) {
        users.add(user);
    }
}
