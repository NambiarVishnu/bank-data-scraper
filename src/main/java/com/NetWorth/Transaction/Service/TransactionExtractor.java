package com.NetWorth.Transaction.Service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aspose.pdf.internal.l52p.l4n.e;

@Service
public class TransactionExtractor {

    public List<Map<String, Object>> extractDetails(String excel, String header1, String header2,int page,int size) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(excel);
             Workbook workbook = WorkbookFactory.create(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            int header1Index = -1;
            int header2Index = -1;
            int headerRowIndex = -1;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (Cell cell : row) {
                        if (cell.getCellType() == CellType.STRING) {
                            String cellValue = cell.getStringCellValue().trim();

                            // Check if this row contains both header1 and header2
                            if (cellValue.equalsIgnoreCase(header1)) {
                                header1Index = cell.getColumnIndex();
                            }
                            if (cellValue.equalsIgnoreCase(header2)) {
                                header2Index = cell.getColumnIndex();
                            }
                        }
                    }

                    if (header1Index != -1 && header2Index != -1) {
                        headerRowIndex = i;
                        break;
                    }
                }
            }

            if (header1Index == -1 || header2Index == -1) {
                Map<String, Object> error = new HashMap<>();
                if (header1Index == -1) {
                    error.put("Error", "Column '" + header1 + "' not found.");
                }
                if (header2Index == -1) {
                    error.put("Error", "Column '" + header2 + "' not found.");
                }
                result.add(error);
                return result;
            }

            // Calculate pagination
            int startRow = headerRowIndex + 1 + (page - 1) * size;
            int endRow = Math.min(startRow + size, sheet.getLastRowNum() + 1);

            // Extract data from the specific columns under header1 and header2
            for (int i = startRow; i < endRow; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, Object> rowMap = new HashMap<>();

                    // Extract data from header1 column
                    Cell header1Cell = row.getCell(header1Index);
                    String header1Value = (header1Cell != null && header1Cell.getCellType() == CellType.STRING)
                            ? header1Cell.getStringCellValue()
                            : "N/A";  // Fallback value if the cell is null or not a string
                    if ("N/A".equals(header1Value)||header1.equals(header1Value)||header1Value.isEmpty()) {
                        continue;
                    }
                    rowMap.put(header1, header1Value);



                    // Extract data from header2 column (assuming numeric data for demonstration)
                    Cell header2Cell = row.getCell(header2Index);
                    String header2Value = (header2Cell != null && header2Cell.getCellType() == CellType.NUMERIC)
                            ? String.valueOf((int) header2Cell.getNumericCellValue())  // Convert to integer if numeric
                            : "N/A";  // Fallback value if the cell is not numeric
                    if ("N/A".equals(header2Value)||header2Value.isEmpty()) {
                        continue; // Skip the rest of the loop for this row and move to the next row
                    }
                        rowMap.put(header2, header2Value);


                    result.add(rowMap);  // Add the extracted data to the result list
                }
            }

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("Error", "Error reading the Excel file: " + e.getMessage());
            result.add(error);
        }

        return result;
        }

    }






