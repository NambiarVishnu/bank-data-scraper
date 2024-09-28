package com.NetWorth.Transaction.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    public List<Map<String,Object>> extractDetails(String excel) throws IOException{
        List<Map<String, Object>> transactionDetails = new ArrayList<>();

        //load
        FileInputStream excelFile =new FileInputStream(new File(excel));
        Workbook workbook=new XSSFWorkbook(excelFile);
        Sheet sheet =workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(17);
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValue(cell).toString());
        }
        for(int i=19;i<=sheet.getLastRowNum();i++){
            Row row= sheet.getRow(i);
            Map<String,Object> rowDetails=new HashMap<>();
            for(int j=0;j< headers.size();j++){
                Cell cell=row.getCell(j);
                rowDetails.put(headers.get(j),getCellValue(cell) );
            }
            transactionDetails.add(rowDetails);
        }
        workbook.close();
        return transactionDetails;

    }
    private Object getCellValue(Cell cell){
        if (cell == null) {
            return null; // Or return a default value, such as an empty string
        }
        switch (cell.getCellType()){
            case STRING -> {
                return cell.getStringCellValue();
            }
            case NUMERIC ->{
                if(DateUtil.isCellDateFormatted(cell)){
                    return cell.getDateCellValue();
                }else{
                    return cell.getNumericCellValue();
                }
            }
            default -> {
                return null;
            }
        }
    }

}
