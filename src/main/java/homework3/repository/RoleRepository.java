package homework3.repository;

import homework3.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getByName(String name);

    Optional<Role> getById(long id);
}
