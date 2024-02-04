package homework3.services;

import homework3.model.Role;
import homework3.model.User;
import homework3.repository.RoleRepository;
import homework3.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        fillWithData();
    }

    private void fillWithData() {
        clearAllData();
        fillWithRoles();
        fillWithUsers();
    }

    private void clearAllData() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private void fillWithRoles() {
        Role reader = new Role("reader");
        Role admin =  new Role("admin");
        roleRepository.saveAll(List.of(reader, admin));
    }

    private void fillWithUsers() {
        Role reader = roleRepository.getByName("reader").orElseThrow();
        Role admin = roleRepository.getByName("admin").orElseThrow();
        User regular = new User("regular", passwordEncoder.encode("regular"));
        User readerUser = new User("reader", passwordEncoder.encode("reader"));
        User adminUser = new User("admin", passwordEncoder.encode("admin"));
        User superUser = new User("super", passwordEncoder.encode("super"));
        reader.addUser(readerUser);
        readerUser.addRole(reader);
        admin.addUser(adminUser);
        adminUser.addRole(admin);
        reader.addUser(superUser);
        superUser.addRole(reader);
        admin.addUser(superUser);
        superUser.addRole(admin);
        userRepository.save(regular);
        userRepository.save(readerUser);
        userRepository.save(adminUser);
        userRepository.save(superUser);
    }
}
