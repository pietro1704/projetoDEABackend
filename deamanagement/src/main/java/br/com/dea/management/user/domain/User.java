package br.com.dea.management.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "dea_user")
@NamedQuery(name = "myQuery", query = "SELECT u FROM User u where u.name = :name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    @NotNull(message = "Name cannot be null")
    private String name;

    @Column
    private String email;

    @Column
    @Min(value = 3, message = "Password must be at least 3 characters")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column
    private String linkedin;

}
