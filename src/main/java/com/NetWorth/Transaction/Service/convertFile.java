//package com.NetWorth.Transaction.Service;
//
//import com.convertapi.client.*;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.xml.transform.Result;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class convertFile {
//    @Value("${convertapi.secret}")
//    private  String convertApiSecret;
//
//    @Value("${file.upload}")
//    private String uploadDir;
//
//    public void initializeConvertApi() {
//        Config.setDefaultApiCredentials(convertApiSecret);
//    }
//
//    public String pdfToExcel(MultipartFile file) throws Exception {
//        initializeConvertApi();
//        Files.createDirectories(Paths.get(uploadDir));
////        try (InputStream inputStream = file.getInputStream()) {
//         File pdfFile = File.createTempFile("upload-", ".pdf");
//        file.transferTo(pdfFile);
//        if (!pdfFile.exists() || !pdfFile.canRead()) {
//            throw new IOException("Temporary PDF file was not created or is not accessible.");
//        }
//
//       String excelFilePath = uploadDir + File.separator + file.getName().replace(".pdf", ".xlsx");
//
//        CompletableFuture <ConversionResult> result=ConvertApi.convert("pdf", "xlsx", new Param("file", pdfFile.getAbsolutePath()));
//
//        result.get().saveFile(Paths.get(excelFilePath)).get();
//
//        return excelFilePath;
//
//
//////        CompletableFuture<Result> result=ConvertApi.convert("pdf","xlsx",new Param("file",pdfFile) );
//////        Result convertResult=result.get();
//////        ConversionResultFile file = result.get().getFile(0);
//////        return convertResult.getU;
////
////
//    }
////
//}
