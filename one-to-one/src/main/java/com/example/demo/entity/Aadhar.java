package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "aadhar")
public class Aadhar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "aadhar_id")
    private Long aadharId;

    private String aadharNumber;
    private String state;

    // ✅ Bidirectional One-to-One
    @OneToOne(mappedBy = "aadhar")
    @JsonBackReference
    private Student student;
}