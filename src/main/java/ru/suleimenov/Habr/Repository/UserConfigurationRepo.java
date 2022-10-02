package ru.suleimenov.Habr.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.UserConfiguration;
@Repository
public interface UserConfigurationRepo extends CrudRepository<UserConfiguration, Long> {
}
