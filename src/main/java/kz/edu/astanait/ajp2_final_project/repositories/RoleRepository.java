package kz.edu.astanait.ajp2_final_project.repositories;

import kz.edu.astanait.ajp2_final_project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
