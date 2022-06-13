package com.abn.amro.transaction.service;

import com.abn.amro.transaction.dto.TransactionSummary;
import com.abn.amro.transaction.model.Transaction;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.abn.amro.common.utils.ClassUtils.fromDate;
import static com.abn.amro.common.utils.ClassUtils.getClientInformation;
import static com.abn.amro.common.utils.ClassUtils.getProductInformation;
import static com.abn.amro.common.utils.ClassUtils.getTotalTransactionAmount;


/**
 * Service class implementing logic to build a summary report from a input list of transactions.
 *
 * @see TransactionSummaryService
 */
@Service
public class TransactionSummaryServiceImpl implements TransactionSummaryService {

    @Override
    public List<TransactionSummary> buildTransactionSummaryReport(List<Transaction> transactions) {

        if (CollectionUtils.isEmpty(transactions)) {
            return List.of();
        }

        final List<TransactionSummary> transactionSummaryList = Lists.newArrayList();

        Function<Transaction, String> keyFunction = t ->
                new StringJoiner("")
                        // client information
                        .add(Strings.nullToEmpty(t.getClientType()).trim())
                        .add(Strings.nullToEmpty(t.getClientNum()).trim())
                        .add(Strings.nullToEmpty(t.getAcctNum()).trim())
                        .add(Strings.nullToEmpty(t.getSubAcctNum()).trim())
                        .add(Strings.nullToEmpty(t.getExchangeCode()).trim())
                        // product information
                        .add(Strings.nullToEmpty(t.getProductGroupCode()).trim())
                        .add(Strings.nullToEmpty(t.getSymbol()).trim())
                        .add(Strings.nullToEmpty(t.getExpirationDate()).trim())
                        // date
                        .add(Strings.nullToEmpty(fromDate(t.getTransactionDate())).trim())
                        .toString();

        ImmutableMultimap<String, Transaction> multimapTransaction = Multimaps.index(transactions, keyFunction);

        multimapTransaction.asMap().forEach((s, transactionsList) ->
                transactionSummaryList.add(TransactionSummary
                        .builder()
                        .clientInformation(
                                getClientInformation(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0)))
                        .productInformation(
                                getProductInformation(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0)))
                        .date(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new)).get(0).getTransactionDate())
                        .totalTransactionAmount(
                                getTotalTransactionAmount(transactionsList.stream().collect(Collectors.toCollection(ArrayList::new))))
                        .build()));

        return transactionSummaryList;
    }
}
