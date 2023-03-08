package br.com.dea.management.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateStudentRequestDto {

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;

    private String linkedin;

    private String university;

    private String graduation;

    private LocalDate finishDate;

    @NotNull(message = "Password cannot be null")
//    @Min(value = 3, message = "Password must be at least 3 characters")
    @Size(min = 3, message = "Password must be at least 3 characters")
    private String password;

}

