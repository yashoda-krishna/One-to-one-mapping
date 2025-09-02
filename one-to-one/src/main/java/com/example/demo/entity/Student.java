package com.example.demo.entity;



import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private Long studentId;

    private String studentName;
    private Integer age;

    // ✅ One-to-One mapping with Aadhar
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_aadhar_id", referencedColumnName = "aadhar_id")
    private Aadhar aadhar;
}