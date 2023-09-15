package com.wex.transaction.service;

import com.wex.transaction.dto.TransactionSummary;
import com.wex.transaction.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class implementing logic to build a summary report from a input list of transactions.
 *
 * @see TransactionSummaryService
 */
@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

    @Override
    public List<TransactionSummary> buildTransactionSummaryReport(List<Transaction> transactions) {

//        if (CollectionUtils.isEmpty(transactions)) {
            return List.of();
//        }
//
//        final List<TransactionSummary> transactionSummaryList = Lists.newArrayList();
//
//        Function<Transaction, String> keyFunction = t ->
//                new StringJoiner("")
//                        // client information
//                        .add(Strings.nullToEmpty(t.getClientType()).trim())
//                        .add(Strings.nullToEmpty(t.getClientNum()).trim())
//                        .add(Strings.nullToEmpty(t.getAcctNum()).trim())
//                        .add(Strings.nullToEmpty(t.getSubAcctNum()).trim())
//                        .add(Strings.nullToEmpty(t.getExchangeCode()).trim())
//                        // product information
//                        .add(Strings.nullToEmpty(t.getProductGroupCode()).trim())
//                        .add(Strings.nullToEmpty(t.getSymbol()).trim())
//                        .add(Strings.nullToEmpty(t.getExpirationDate()).trim())
//                        // date
//                        .add(Strings.nullToEmpty(ClassUtils.fromDate(t.getTransactionDate())).trim())
//                        .toString();
//
//        ImmutableMultimap<String, Transaction> multimapTransaction = Multimaps.index(transactions, keyFunction);
//
//        multimapTransaction.asMap().forEach((s, transactionsList) ->
//                transactionSummaryList.add(TransactionSummary
//                        .builder()
//                        .clientInformation(
//                                ClassUtils.getClientInformation(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0)))
//                        .productInformation(
//                                ClassUtils.getProductInformation(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0)))
//                        .date(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0).getTransactionDate())
//                        .totalTransactionAmount(
//                                ClassUtils.getTotalTransactionAmount(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new))))
//                        .build()));
//
//        return transactionSummaryList;
    }
}
