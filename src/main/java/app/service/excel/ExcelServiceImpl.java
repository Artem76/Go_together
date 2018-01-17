package app.service.excel;

import app.entity.*;
import app.entityXML.outgoingInvoiceTrafficDetails.OutgoingInvoiceTrafficDetailsRow;
import app.service.clientRatesHistroy.ClientRatesHistoryService;
import app.service.outgoingInvoice.OutgoingInvoiceTrafficDetailsService;
import app.service.refbook.RefbookService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by АРТЕМ on 28.08.2017.
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private RefbookService refbookService;

    @Autowired
    private ClientRatesHistoryService clientRatesHistoryService;

    @Autowired
    private OutgoingInvoiceTrafficDetailsService outgoingInvoiceTrafficDetailsService;

    @Override
    public Map<String, String> setExcelGetStringForEmail(EmailAttachment emailAttachment) throws IOException {
        StringBuilder tableString = new StringBuilder();

        File file = new File(emailAttachment.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(emailAttachment.getFileBody());
        fos.close();

        //Вычисление количества столбцов

        Map<String, Integer> countRowAndCell = countRowAndCell(file);
        int maxNumberCell = countRowAndCell.get("maxNumberCell");
        int maxNumberRow = countRowAndCell.get("maxNumberRow");

        //Формирование HTML таблицы

        if (file.getPath().endsWith(".xlsx")) {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> rows = myExcelSheet.rowIterator();

            int numTr = 0;
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                tableString.append("<tr>");
                for (int i = 0; i < maxNumberCell; i++) {
                    XSSFCell cell = row.getCell(i);
                    tableString.append("<td class='td_con'>");
                    tableString.append(viewXSSFCell(cell));
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
        }

        if (file.getPath().endsWith(".xls")) {
            HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> rows = myExcelSheet.rowIterator();

            int numTr = 0;
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                tableString.append("<tr>");
                for (int i = 0; i < maxNumberCell; i++) {
                    HSSFCell cell = row.getCell(i);
                    tableString.append("<td class='td_con'>");
                    tableString.append(viewHSSVCell(cell));
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
        }

        if (file.getPath().endsWith(".csv")) {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            String line;

            int numTr = 0;
            while ((line = dis.readLine()) != null) {
                tableString.append("<tr>");
                String[] arrayCell = line.split(";");
                for (int i = 0; i < maxNumberCell; i++) {
                    tableString.append("<td class='td_con'>");
                    try {
                        tableString.append(arrayCell[i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
            dis.close();
            fis.close();
        }

        file.delete();
        Map<String, String> map = new HashMap<>();
        map.put("table", tableString.toString());
        map.put("fileName", emailAttachment.getFileName());
        map.put("td_number", String.valueOf(maxNumberCell));
        map.put("tr_number", String.valueOf(maxNumberRow));
        return map;
    }

    @Override
    public Map<String, String> setExcelGetStringForPrice(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return getMapTableStringExcel(file);
    }

    @Override
    public Map<String, String> setAttachmentExcelGetStringForPrice(EmailAttachment emailAttachment) throws IOException {
        File file = new File(emailAttachment.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(emailAttachment.getFileBody());
        fos.close();
        return getMapTableStringExcel(file);
    }

    private Map<String,String> getMapTableStringExcel(File file) throws IOException {
        StringBuilder tableString = new StringBuilder();

        //Вычисление количества столбцов

        Map<String, Integer> countRowAndCell = countRowAndCell(file);
        int maxNumberCell = countRowAndCell.get("maxNumberCell");
        int maxNumberRow = countRowAndCell.get("maxNumberRow");


        //Формирование HTML таблицы

        //Шапка с Select

        tableString.append("<tr class='head'>");
        tableString.append("<td class='td_-1'></td>");
        for (int i = 0; i < maxNumberCell; i++) {
            tableString.append("<td id='td_" + i + "'>");
            tableString.append("<select>" +
                    "<option value='none'>None</option>" +
                    "<option value='mccmnc'>MCCMNC</option>" +
                    "<option value='mcc'>MCC</option>" +
                    "<option value='mnc'>MNC</option>" +
                    "<option value='price'>Price</option>" +
                    "<option value='date'>Date</option>");
            tableString.append("</td>");
        }
        tableString.append("</tr>");

        //Тело таблицы

        if (file.getPath().endsWith(".xlsx")) {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> rows = myExcelSheet.rowIterator();

            int numTr = 0;
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                tableString.append("<tr class='tr_" + (numTr) + "'>");
                tableString.append("<td id='tr_" + (numTr) + "' class='td_-1'>" +
                        "<select>" +
                        "<option value='none'>None</option>" +
                        "<option value='begin'>Begin</option>" +
                        "<option value='end'>End</option>" +
                        "</td>");
                for (int i = 0; i < maxNumberCell; i++) {
                    XSSFCell cell = row.getCell(i);
                    tableString.append("<td class='td_" + i + " td_con'>");
                    tableString.append(viewXSSFCell(cell));
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
        }

        if (file.getPath().endsWith(".xls")) {
            HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);

            Iterator<Row> rows = myExcelSheet.rowIterator();

            int numTr = 0;
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                tableString.append("<tr class='tr_" + (numTr) + "'>");
                tableString.append("<td id='tr_" + (numTr) + "' class='td_-1'>" +
                        "<select>" +
                        "<option value='none'>None</option>" +
                        "<option value='begin'>Begin</option>" +
                        "<option value='end'>End</option>" +
                        "</td>");
                for (int i = 0; i < maxNumberCell; i++) {
                    HSSFCell cell = row.getCell(i);
                    tableString.append("<td class='td_" + i + " td_con'>");
                    tableString.append(viewHSSVCell(cell));
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
        }

        if (file.getPath().endsWith(".csv")) {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            String line;

            int numTr = 0;
            while ((line = dis.readLine()) != null) {
                tableString.append("<tr class='tr_" + (numTr) + "'>");
                tableString.append("<td id='tr_" + (numTr) + "' class='td_-1'>" +
                        "<select>" +
                        "<option value='none'>None</option>" +
                        "<option value='begin'>Begin</option>" +
                        "<option value='end'>End</option>" +
                        "</td>");
                String[] arrayCell = line.replaceAll("^\"|\"$", "").split(";|\",\"");
                for (int i = 0; i < maxNumberCell; i++) {
                    tableString.append("<td class='td_" + i + " td_con'>");
                    try {
                        tableString.append(arrayCell[i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    tableString.append("</td>");
                }
                numTr++;
                tableString.append("</tr>");
            }
            dis.close();
            fis.close();
        }

        file.delete();
        Map<String, String> map = new HashMap<>();
        map.put("table", tableString.toString());
        map.put("td_number", String.valueOf(maxNumberCell));
        map.put("tr_number", String.valueOf(maxNumberRow));
        return map;
    }

    private String doubleToString(double d) {
        int i = (int) d;
        return d == i ? String.valueOf(i) : String.valueOf(d);
    }

    private Map<String, Integer> countRowAndCell(File file) throws IOException {
        int maxNumberRow = 0;
        int maxNumberCell = 0;
        if (file.getPath().endsWith(".xlsx")) {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            XSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
            Iterator<Row> rows = myExcelSheet.rowIterator();
            maxNumberRow = myExcelSheet.getLastRowNum();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                maxNumberCell = maxNumberCell < row.getLastCellNum() ? row.getLastCellNum() : maxNumberCell;
            }
        }
        if (file.getPath().endsWith(".xls")) {
            HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet myExcelSheet = myExcelBook.getSheetAt(0);
            Iterator<Row> rows = myExcelSheet.rowIterator();
            maxNumberRow = myExcelSheet.getLastRowNum();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                maxNumberCell = maxNumberCell < row.getLastCellNum() ? row.getLastCellNum() : maxNumberCell;
            }
        }
        if (file.getPath().endsWith(".csv")) {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            String line;
            while ((line = dis.readLine()) != null) {
                maxNumberRow++;
                maxNumberCell = maxNumberCell < line.replaceAll("^\"|\"$", "").split(";|\",\"").length ? line.replaceAll("^\"|\"$", "").split(";|\",\"").length : maxNumberCell;
            }
            dis.close();
            fis.close();
        }
        Map<String, Integer> countRowAndCell = new HashMap<>();
        countRowAndCell.put("maxNumberRow", maxNumberRow);
        countRowAndCell.put("maxNumberCell", maxNumberCell);
        return countRowAndCell;
    }

    private String viewXSSFCell(XSSFCell cell) {
        String tablePart = new String();
        try {
            if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                tablePart = cell.getStringCellValue();
            }
            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                System.out.println(cell.getCellStyle().getDataFormatString());
                if (cell.getDateCellValue().getTime() > 946684800000l && cell.getDateCellValue().getTime() < 4102444800000l) {
                    tablePart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(cell.getDateCellValue().getTime());
                } else {
                    tablePart = doubleToString(cell.getNumericCellValue());
                }
            }
        } catch (NullPointerException e) {
        }
        return tablePart;
    }

    private String viewHSSVCell(HSSFCell cell) {
        String tablePart = new String();
        try {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                tablePart = cell.getStringCellValue();
            }
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
//                short cdf = cell.getCellStyle().getDataFormat();
                if (cell.getDateCellValue().getTime() > 946684800000l && cell.getDateCellValue().getTime() < 4102444800000l) {
                    tablePart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(cell.getDateCellValue().getTime());
                } else {
                    tablePart = doubleToString(cell.getNumericCellValue());
                }
            }
        } catch (NullPointerException e) {
        }
        return tablePart;
    }

    @Override
    public List<EmailAttachment> setClientRnGetEmailAttachmentXlsxCsv(ClientRatesUpdate clientRatesUpdate, EmailContent emailContent){
        List<EmailAttachment> emailAttachmentList = new ArrayList<>();
        String fileName = "Volumes_WS_EUR_" + new SimpleDateFormat("dd-MM-yyyy").format(clientRatesUpdate.getDate()) + "_" + clientRatesUpdate.getId();
        Set<ClientRatesHistory> clientRatesHistoryList = clientRatesUpdate.getClientRatesHistories();

        //========= Creating xlsx ===============

        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        File file = new File(fileName + ".xlsx");
        File fileExample = new File("src/main/resources/excel/example_client_rn.xlsx");
        FileInputStream fileInputStreamExample = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileInputStreamExample = new FileInputStream(fileExample);
            XSSFWorkbook workXBookPOI = new XSSFWorkbook(fileInputStreamExample);
            XSSFSheet sheet = workXBookPOI.getSheetAt(0);

            Row rowNew = sheet.getRow(2);
            Row rowSame = sheet.getRow(3);
            Row rowDecrease = sheet.getRow(4);
            Row rowIncrease = sheet.getRow(5);

            for (ClientRatesHistory clientRatesHistory: clientRatesHistoryList) {
                Map<String, String> countryNetworkMap = refbookService.getRnCountryNetworkByMccMnc(clientRatesHistory.getMcc(), clientRatesHistory.getMnc());
                Float currentRate = clientRatesHistoryService.getCurrentRate(clientRatesHistory.getDate(), clientRatesUpdate.getSmppClientAccount(), clientRatesHistory.getMcc(), clientRatesHistory.getMnc(), true);
                Row row = rowNew;
                if (currentRate == null) {
                    row = rowNew;
                }else if (currentRate.equals(clientRatesHistory.getRate())){
                    row = rowSame;
                }else if (currentRate > clientRatesHistory.getRate()){
                    row = rowDecrease;
                }else if (currentRate < clientRatesHistory.getRate()){
                    row = rowIncrease;
                }
                Cell cell = row.getCell(0);
                cell.setCellValue(clientRatesHistory.getMcc());
                cell = row.getCell(1);
                cell.setCellValue((clientRatesHistory.getMnc() == null || clientRatesHistory.getMnc().equals("")) ? "" : clientRatesHistory.getMnc());
                cell = row.getCell(2);
                cell.setCellValue(countryNetworkMap.get("country"));
                cell = row.getCell(3);
                cell.setCellValue((clientRatesHistory.getMnc() == null || clientRatesHistory.getMnc().equals("")) ? "Default" : countryNetworkMap.get("network"));
                cell = row.getCell(4);
                cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(clientRatesHistory.getDate()));
                cell = row.getCell(5);
                cell.setCellValue(clientRatesHistory.getRate().toString());

                sheet.copyRows(row.getRowNum(), row.getRowNum(), sheet.getLastRowNum() + 1, new CellCopyPolicy());
            }

            sheet.removeRow(rowNew);
            sheet.removeRow(rowSame);
            sheet.removeRow(rowDecrease);
            sheet.removeRow(rowIncrease);
            if (clientRatesHistoryList.size() > 0){
                sheet.shiftRows(6, sheet.getLastRowNum(), -4, true, false);
            }

            workXBookPOI.write(fileOutputStream);
            fileInputStream = new FileInputStream(file);
            byte[] body =  IOUtils.toByteArray(fileInputStream);
            EmailAttachment emailAttachment = new EmailAttachment(fileName + ".xlsx", body, emailContent);
            emailAttachmentList.add(emailAttachment);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStreamExample.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete();
        }

        //======== Creating csv =================

        StringBuilder sb = new StringBuilder("MCC;MNC;Country;Network;Effective from;Rate;Change type" + System.lineSeparator());
        for (ClientRatesHistory clientRatesHistory: clientRatesHistoryList) {
            Map<String, String> countryNetworkMap = refbookService.getRnCountryNetworkByMccMnc(clientRatesHistory.getMcc(), clientRatesHistory.getMnc());
            sb.append(clientRatesHistory.getMcc() + ";");
            sb.append(((clientRatesHistory.getMnc() == null || clientRatesHistory.getMnc().equals("")) ? "" : clientRatesHistory.getMnc()) + ";");
            sb.append(countryNetworkMap.get("country") + ";");
            sb.append(((clientRatesHistory.getMnc() == null || clientRatesHistory.getMnc().equals("")) ? "Default" : countryNetworkMap.get("network")) + ";");
            sb.append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(clientRatesHistory.getDate()) + ";");
            sb.append(clientRatesHistory.getRate() + ";");
            Float currentRate = clientRatesHistoryService.getCurrentRate(clientRatesUpdate.getDate(), clientRatesUpdate.getSmppClientAccount(), clientRatesHistory.getMcc(), clientRatesHistory.getMnc(), true);
            if (currentRate == null){
                sb.append("New");
            }else if (currentRate.equals(clientRatesHistory.getRate())){
                sb.append("Same");
            }else if (currentRate > clientRatesHistory.getRate()){
                sb.append("Decrease");
            }else if (currentRate < clientRatesHistory.getRate()){
                sb.append("Increase");
            }
            sb.append(System.lineSeparator());
        }
        EmailAttachment emailAttachment = new EmailAttachment(fileName + ".csv", sb.toString().getBytes(), emailContent);
        emailAttachmentList.add(emailAttachment);

        return emailAttachmentList;
    }

    @Override
    public byte[] createCsvForOutgoingInvoice(OutgoingInvoice outgoingInvoice) {
        StringBuilder resultSB = new StringBuilder();
        resultSB.append("System-Id;Country;Network;MCC;MNC;Count;Sum" + System.lineSeparator());
        try{
            List<OutgoingInvoiceTrafficDetailsRow> outgoingInvoiceTrafficDetailsRowList = outgoingInvoiceTrafficDetailsService.getTrafficDetailsRowList(outgoingInvoice.getId());
            for (OutgoingInvoiceTrafficDetailsRow outgoingInvoiceTrafficDetailsRow: outgoingInvoiceTrafficDetailsRowList) {
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getUid() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getCountry() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getNetwork() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getMcc() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getMnc() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getCount() + ";");
                resultSB.append(outgoingInvoiceTrafficDetailsRow.getSum() + System.lineSeparator());
            }
        }catch (Exception e){
            System.out.println("Create csv invoice Exception");
            e.printStackTrace();
        }

        return resultSB.toString().getBytes();
    }
}
