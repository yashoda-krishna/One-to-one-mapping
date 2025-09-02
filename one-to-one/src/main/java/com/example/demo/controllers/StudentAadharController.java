package com.example.demo.controllers;


import com.example.demo.entity.Student;
import com.example.demo.entity.Aadhar;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.AadharRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentAadharController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AadharRepository aadharRepository;

    // ───────────── STUDENT CRUD ─────────────

    @PostMapping("/student")
    public ResponseEntity<String> saveStudent(@RequestBody Student studentData) {
        if (studentData.getAadhar() != null) {
            studentData.getAadhar().setStudent(studentData);
        }

        studentRepository.save(studentData);
        return ResponseEntity.ok("Student and Aadhar saved successfully");
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> studentOpt = studentRepository.findById(id);

        if (studentOpt.isPresent()) {
            Student existingStudent = studentOpt.get();
            existingStudent.setStudentName(updatedStudent.getStudentName());
            existingStudent.setAge(updatedStudent.getAge());

            if (updatedStudent.getAadhar() != null) {
                Aadhar aadhar = updatedStudent.getAadhar();
                aadhar.setStudent(existingStudent);
                existingStudent.setAadhar(aadhar);
            }

            studentRepository.save(existingStudent);
            return ResponseEntity.ok("Student updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Student deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // ───────────── AADHAR CRUD (optional) ─────────────

    @GetMapping("/aadhars")
    public List<Aadhar> getAllAadhar() {
        return aadharRepository.findAll();
    }

    @GetMapping("/aadhar/{id}")
    public ResponseEntity<Aadhar> getAadharById(@PathVariable Long id) {
        Optional<Aadhar> aadhar = aadharRepository.findById(id);
        return aadhar.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/aadhar/{id}")
    public ResponseEntity<String> deleteAadhar(@PathVariable Long id) {
        if (aadharRepository.existsById(id)) {
            aadharRepository.deleteById(id);
            return ResponseEntity.ok("Aadhar deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}