package com.NetWorth.Transaction.Controller;

import org.apache.commons.lang3.tuple.Pair;

import com.NetWorth.Transaction.Service.ConvertFileService;
import com.NetWorth.Transaction.Service.TransactionExtractor;
import com.NetWorth.Transaction.Service.TransactionSave;
import com.NetWorth.Transaction.model.BankData;
import com.NetWorth.Transaction.repository.BankDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v2/tx-details")
public class Transaction {
    @Autowired
    private TransactionExtractor transactionExtractor;
    @Autowired
    private ConvertFileService convertFileService;
    @Autowired
    private BankDataRepository bankDataRepository;
    @Autowired
    private TransactionSave transactionSave;

    @PostMapping("/upload-statement")
    public ResponseEntity<?> handleFile(@RequestParam("file") List<MultipartFile> files,
                                        @RequestParam(value = "h1",  defaultValue = "Particulars") String header1,
                                        @RequestParam(value = "h2",  defaultValue = "Deposits") String header2) {

        MultipartFile file1 = files.get(0);
        MultipartFile file2 = files.get(1);



        if (file1.isEmpty()||file2.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID FILE");

        }
        try {
            // Convert PDF to Excel
            String excelFile1 = convertFileService.pdfToExcel(file1);
            List<Map<String, Object>> transactionDetails = transactionExtractor.extractDetails(excelFile1, header1, header2,1,100);
            transactionSave.saveExtractedData(transactionDetails);

            String excelFile2 = convertFileService.pdfToExcel(file2);
            List<Map<String,Object>>  transactionDetails2 = transactionExtractor.extractDetails(excelFile2,header1,header2,1,150);
//            return ResponseEntity.ok(transactionDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("transactionDetails", transactionDetails);
            response.put("transactionDetails2", transactionDetails2);
//            Pair<List<Map<String, Object>>,List<Map<String, Object>>> response = Pair.of(transactionDetails, transactionDetails2);
            return ResponseEntity.ok(response);

//            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload or convert file: " + e.getMessage());
        }
    }
}
