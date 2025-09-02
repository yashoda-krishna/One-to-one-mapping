package com.example.BankDetails.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;
      private long Customer_id;
      private double balance;



}
