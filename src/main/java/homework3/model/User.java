package homework3.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    private static final long DEFAULT_ID = 0L;
    private static final String DEFAULT_LOGIN = "";
    private static final String DEFAULT_PASSWORD = "";
    private static final Set<Role> DEFAULT_ROLES = new HashSet<>();

    private static long counter = 0L;

    @Id
    private final long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
        this.id = DEFAULT_ID;
        this.login = DEFAULT_LOGIN;
        this.password = DEFAULT_PASSWORD;
        this.roles = DEFAULT_ROLES;
    }

    public User(String login, String password, Set<Role> roles) {
        this.id = counter++;
        this.login = login;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.addAll(roles);
    }

    public User(String login, String password) {
        this(login, password, new HashSet<>());
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return String.format("User{login: %s, roles: %s}", login,
                roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(", ")));
    }
}
