package com.NetWorth.Transaction.Controller;

import com.NetWorth.Transaction.Service.ConvertFile;
import com.NetWorth.Transaction.Service.TransactionService;
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
@RequestMapping("/v1/tx-details")
public class TransactionDetails {
    @Autowired
    private  ConvertFile convertFile;

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/upload-statement")
    public ResponseEntity<?> handleFile(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID FILE");

        }
        try {
            // Convert PDF to Excel using the service directly
            String excelFile = convertFile.pdfToExcel(file);
            List<Map<String,Object>> transactionDetails= transactionService.extractDetails(excelFile);
//            return ResponseEntity.ok("File converted successfully. Download Link: " + excelFile);
              return ResponseEntity.ok(transactionDetails);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload or convert file: "+e.getMessage());
        }
////        String fileName= file.getOriginalFilename();
//
//        return ResponseEntity.ok("File Uploaded Successfully"+fileName);
    }



}
