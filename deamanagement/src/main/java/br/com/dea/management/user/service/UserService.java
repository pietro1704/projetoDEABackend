package br.com.dea.management.user.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get All Users
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    // Automatic Custom Query
    // Get User by id
    public User findUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NotFoundException(User.class, id));
    }

    // Get Users email
    public String findUserEmail(Long id) {
        User user = this.findUserById(id);
        return user.getEmail();
    }

    // Get Users by email
    @Query("SELECT u FROM User u WHERE email := email")
    public User findUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NotFoundException(User.class,
                email));
    }

}
