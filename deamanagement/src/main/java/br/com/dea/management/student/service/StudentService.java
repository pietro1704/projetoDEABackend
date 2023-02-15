package br.com.dea.management.student.service;

import br.com.dea.management.exceptions.NotFoundException;
import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get All Students
    public List<Student> findAllStudents() { return this.studentRepository.findAll();}

    // Automatic Custom Query
    // Get Student by id
    public Student findStudentById(Long id) {
        Optional<Student> Student = this.studentRepository.findById(id);
        return Student.orElseThrow(() -> new NotFoundException(Student.class, id));
    }

    public Page<Student> findAllStudentsPaginated(Integer page, Integer pageSize) {
        return this.studentRepository.findAllPaginated(PageRequest.of(page, pageSize));
    }

}
