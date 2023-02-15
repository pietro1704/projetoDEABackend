package br.com.dea.management.user.repository;

import br.com.dea.management.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Get User by id
    // Automatic Query
    public Optional<User> findById(Long id);

    // Get User by name
    @Query("SELECT u FROM User u WHERE name = :name")
    public Optional<User> findByName(String name);

    // Get User by email
    public Optional<User> findByEmail(String email);

    // Get User by password
    @Query("SELECT p FROM User p WHERE password = :password")
    public Optional<User> findByPassword(String password);

    public Optional<User> findByLinkedin(String linkedin);

    @Query("SELECT u FROM User u")
    public Page<User> findAllPaginated(Pageable pageable);
}
