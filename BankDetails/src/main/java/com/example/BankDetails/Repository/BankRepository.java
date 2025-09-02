package com.example.BankDetails.Repository;

import com.example.BankDetails.Entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankEntity,Long> {
    BankEntity findCustomerById(Long id);
}
