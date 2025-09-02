package com.example.BankDetails.Service;

import com.example.BankDetails.Entity.BankEntity;
import com.example.BankDetails.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public BankEntity create(BankEntity bankEntity)
    {
        return  bankRepository.save(bankEntity);
    }
    public List<BankEntity> getAll()
    {
        return bankRepository.findAll();
    }
    public Optional<BankEntity> getById(Long id)
    {
        return bankRepository.findById(id);
    }
    public String update(Long id,BankEntity bankEntity)
    {
        Optional<BankEntity> entity=bankRepository.findById(id);
         if(entity.isPresent()) {
             BankEntity update = entity.get();
             update.setCustomer_id(bankEntity.getCustomer_id());
             update.setBalance(bankEntity.getBalance());
             bankRepository.save(update);

             return "Bank details updated";
         }else
        {
            return "bank details not updated";
        }
    }
    public String deleteByID(Long id)
    {
         Optional<BankEntity> bankEntity=bankRepository.findById(id);
         if(bankEntity.isPresent())
         {
             bankRepository.deleteById(id);
             return "Deleted successfully";
         }
         else {
             return "No details found with id"+id;
         }
    }
    public BankEntity updateBalance(Long customer_id,Double amount,String type)
    {
         BankEntity bankEntity=bankRepository.findCustomerById(customer_id);
          Double currentbalance=bankEntity.getBalance();
          if(type.equalsIgnoreCase("Debit")) {
              if (currentbalance < amount) {
                  throw new RuntimeException("Insufficent balance to debit");
              }
              bankEntity.setBalance(currentbalance - amount);
          }
          else if(type.equalsIgnoreCase("credit"))
              {
                  bankEntity.setBalance(currentbalance+amount);
              }
          else
          {
              throw new RuntimeException("Invalid Transaction");
          }
          return bankRepository.save(bankEntity);
    }

}
