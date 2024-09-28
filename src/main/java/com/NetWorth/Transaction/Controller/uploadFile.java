package com.NetWorth.Transaction.Controller;

import com.NetWorth.Transaction.Service.ConvertFileService;
import com.NetWorth.Transaction.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class uploadFile {
    @Autowired
    private  ConvertFileService convertFileService;

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/uploadStatement")
    public ResponseEntity<?> handleFile(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("NO FILE HAS BEEN UPLOADED");
        }
        try {
            // Convert PDF to Excel using the service directly
            String excelFile = convertFileService.pdfToExcel(file);
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
