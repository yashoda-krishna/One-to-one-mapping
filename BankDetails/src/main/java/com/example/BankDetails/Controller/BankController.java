package com.example.BankDetails.Controller;

import com.example.BankDetails.Entity.BankEntity;
import com.example.BankDetails.Repository.BankRepository;
import com.example.BankDetails.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;
    @GetMapping
    public List<BankEntity> findall() {
        return bankService.getAll();
    }
    @GetMapping("/{id}")
    public Optional<BankEntity> findByID(@PathVariable Long id)
    {
        return bankService.getById(id);
    }
    @PutMapping("/{id}")
    public String update(@PathVariable Long id,@RequestBody BankEntity bankEntity)
    {
        return bankService.update(id, bankEntity);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id)
    {
        return bankService.deleteByID(id);
    }
    @PutMapping("/{id}")
    public BankEntity updateBalance(@PathVariable Long customer_id,
                                     @RequestBody Double amount,
                                    @RequestBody String type)
    {
        return bankService.updateBalance(customer_id,amount,type);
    }


}
