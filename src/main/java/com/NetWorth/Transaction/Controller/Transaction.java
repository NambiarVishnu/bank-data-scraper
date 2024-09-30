package com.NetWorth.Transaction.Controller;

import com.NetWorth.Transaction.Service.ConvertFileService;
import com.NetWorth.Transaction.Service.TransactionExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v2/tx-details")
public class Transaction {
    @Autowired
    private TransactionExtractor transactionExtractor;
    @Autowired
    private ConvertFileService convertFileService;

    @PostMapping("/upload-statement")
    public ResponseEntity<?> handleFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam(value = "nameColumn", required = false, defaultValue = "Particulars") String header1,
                                        @RequestParam(value = "ageColumn", required = false, defaultValue = "Deposits") String header2) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID FILE");

        }
        try {
            // Convert PDF to Excel using the service directly
            String excelFile = convertFileService.pdfToExcel(file);
            List<Map<String, Object>> transactionDetails = transactionExtractor.extractDeatils(excelFile, header1, header2,1,10);
            // return ResponseEntity.ok("File converted successfully. Download Link: " + excelFile);
            return ResponseEntity.ok(transactionDetails);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload or convert file: " + e.getMessage());
        }
    }
}
