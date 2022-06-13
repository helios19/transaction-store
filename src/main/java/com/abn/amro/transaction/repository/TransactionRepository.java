package com.abn.amro.transaction.repository;

import com.abn.amro.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>, TransactionRepositoryCustom {

    Optional<Transaction> findById(String id);

}

