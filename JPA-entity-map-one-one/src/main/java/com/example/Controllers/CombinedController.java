package com.example.Controllers;



import com.example.Entity.Employee;
import com.example.Entity.Address;
import com.example.repository.EmployeeRepository;
import com.example.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CombinedController {

    @Autowired
    private EmployeeRepository empRepository;

    @Autowired
    private AddressRepository addRepository;

    // ───────────── EMPLOYEE CRUD ─────────────

    @PostMapping("/employee")
    public ResponseEntity<String> saveEmployee(@RequestBody Employee empData) {
        // Set both sides of the bidirectional relationship
        if (empData.getAddress() != null) {
            empData.getAddress().setEmployee(empData);
        }

        empRepository.save(empData);
        return ResponseEntity.ok("Employee and Address saved successfully");
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return empRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> emp = empRepository.findById(id);
        return emp.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmp) {
        Optional<Employee> empOpt = empRepository.findById(id);

        if (empOpt.isPresent()) {
            Employee existingEmp = empOpt.get();
            existingEmp.setEmpName((updatedEmp.getEmpName()));

            // Update address if present
            if (updatedEmp.getAddress() != null) {
                Address addr = updatedEmp.getAddress();
                addr.setEmployee(existingEmp); // maintain bidirectional link
                existingEmp.setAddress(addr);
            }

            empRepository.save(existingEmp);
            return ResponseEntity.ok("Employee updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (empRepository.existsById(id)) {
            empRepository.deleteById(id); // address will also be deleted (cascade)
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // ───────────── ADDRESS CRUD (optional) ─────────────

    @GetMapping("/addresses")
    public List<Address> getAddresses() {
        return addRepository.findAll();
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addRepository.findById(id);
        return address.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        if (addRepository.existsById(id)) {
            addRepository.deleteById(id);
            return ResponseEntity.ok("Address deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
