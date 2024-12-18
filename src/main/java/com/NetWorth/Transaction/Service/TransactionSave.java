package com.NetWorth.Transaction.Service;

import com.NetWorth.Transaction.model.BankData;
import com.NetWorth.Transaction.repository.BankDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TransactionSave {

    @Autowired
    private BankDataRepository bankDataRepository;

    @Transactional
    public void saveExtractedData(List<Map<String, Object>> transactionDetails) throws IllegalAccessException {
        if(transactionDetails==null||transactionDetails.isEmpty()){
            throw new IllegalAccessException("No transaction details provided");
        }

        List<BankData> transactions =new ArrayList<>();
        for (Map<String, Object> data : transactionDetails) {
            System.out.println("Transaction Map: " + data); // Print the entire map

            try{
            // Extract transaction fields from the map
            String transactionId = (String) data.get("Particulars");
            Double amount = Double.parseDouble((String) data.get("Balance")) ;
            String description = (String) data.get("description");
            LocalDateTime transactionDate = (LocalDateTime) data.get("transactionDate");
                System.out.println(transactionId + "-----"+amount);

            // Create a new BankData object
            BankData transaction = new BankData();
            transaction.setTxndetails(transactionId);
            transaction.setAmount(amount);
//            transaction.setDate(LocalDateTime.now());
//            transaction.setTransactionDate(transactionDate);
//            transaction.setScrapedTimestamp(); // Timestamp when the data was scraped

            // Save the transaction data to the database
            transactions.add(transaction);
            }catch(Exception e){
                throw new RuntimeException("Failed to process transaction: "+e.getMessage());
            }

        }
        bankDataRepository.saveAll(transactions);
    }
}
