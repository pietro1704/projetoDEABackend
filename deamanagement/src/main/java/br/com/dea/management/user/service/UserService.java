package br.com.dea.management.user.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<User> findAllUsersPaginated(Integer page, Integer pageSize) {
        return this.userRepository.findAllPaginated(PageRequest.of(page, pageSize, Sort.by("name").ascending()));
    }

    // Get User by id
    public User findUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NotFoundException(User.class, id));
    }

    // Get Users by email
    public User findUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NotFoundException(User.class,
                email));
    }

}
