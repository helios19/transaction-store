package com.abn.amro.transaction.repository;

import com.abn.amro.transaction.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>, TransactionRepositoryCustom {

    Optional<Transaction> findById(String id);

}

