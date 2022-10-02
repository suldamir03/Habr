package ru.suleimenov.Habr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.suleimenov.Habr.Repository.RoleRepo;
import ru.suleimenov.Habr.Repository.UserConfigurationRepo;
import ru.suleimenov.Habr.Repository.UserRepo;
import ru.suleimenov.Habr.entity.Role;
import ru.suleimenov.Habr.entity.User;
import ru.suleimenov.Habr.entity.UserConfiguration;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    RoleRepo roleRepo;

   /* @Autowired
    PasswordEncoder passwordEncoder;*/
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserConfigurationRepo userConfigurationRepo;
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return userRepo.findUserByLogin(username).orElse(null);
    }
    @Transactional
    public boolean registerDefaultUser(User user) {
        if (loadUserByUsername(user.getUsername()) != null){
            return false;
        }
//        user.setPass(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Collections.singletonList(roleRepo.findById(1L).get()));
        UserConfiguration userConfiguration = new UserConfiguration();
        userConfiguration.setUser(user);
        user.setUserConfiguration(userConfiguration);
        userRepo.save(user);
        return true;
    }

    public User findById(Long id){
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()){
            return user.get();
        }else return null;
    }

    public void save(User user){
        userRepo.save(user);
    }

    public UserConfiguration getUserConfig(Long id){
        Optional<UserConfiguration> userConfiguration = userConfigurationRepo.findById(id);
        if (userConfiguration.isPresent()) {
            return userConfiguration.get();
        }else return new UserConfiguration();
    }

    public void saveUserConfig(UserConfiguration userConfiguration){
        userConfigurationRepo.save(userConfiguration);
    }
}
