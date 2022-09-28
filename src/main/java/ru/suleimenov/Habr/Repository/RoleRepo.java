package ru.suleimenov.Habr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
}
