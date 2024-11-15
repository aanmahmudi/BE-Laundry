package com.laundry.BE_Laundry.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundry.BE_Laundry.Entity.Transaction;

public interface TransactionRepository extends JpaRepository <Transaction, Long>{

}
