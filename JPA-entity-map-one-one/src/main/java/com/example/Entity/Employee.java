package com.example.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "employee_details")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "emp_id")
    private Long empId;

    private String empName;
    private Integer empAge;

    // ✅ One-to-One mapping with Address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_add_id", referencedColumnName = "add_id") // foreign key column in employee_details table
    private Address address;

}
