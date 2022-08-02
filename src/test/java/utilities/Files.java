package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;

public class Files {
    private static Workbook workbook;
    private static Row row;
    private static Sheet sheet;
    private static Cell cell;

    @Test
    public void write_File() throws Exception {

        File file = new File("C:\\Users\\Oleksanda\\Desktop\\DevxRemoteServer\\GroupProjectFramework\\src\\test\\resources\\data\\summary.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fw);

        writer.write("Hello This is Taras Dykun");
        writer.newLine();
        writer.write("The event is pending at this moment");
        writer.close();

    }

    @Test
    public void readFile2() throws Exception {

        File file = new File("C:\\Users\\Oleksanda\\Desktop\\DevxRemoteServer\\GroupProjectFramework\\src\\test\\resources\\data\\summary.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String v;
        // save the read-line int he v variable and keep rotating until the last line in null
        while ((v = br.readLine()) != null) {
            System.out.println(v);
        }


    }

    @Test
    public void write_to_excel() throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("write_test");

        sheet.createRow(0);
        sheet.getRow(0).createCell(0).setCellValue("Hello");
        sheet.getRow(0).createCell(1).setCellValue("World");

        sheet.createRow(1);
        sheet.getRow(1).createCell(0).setCellValue("Fail");
        sheet.getRow(1).createCell(1).setCellValue("Manager A");

        File file = new File("src/test/resources/data/write_test.xlsx");
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);

        workbook.close();
    }

    @Test
    public void read_from_excel() throws Exception {
        File src = new File("src/test/resources/data/write_test.xlsx");
        FileInputStream fis = new FileInputStream(src);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        String data1 = sheet.getRow(0).getCell(0).getStringCellValue();
        String data2 = sheet.getRow(0).getCell(1).getStringCellValue();
        String data3 = sheet.getRow(1).getCell(0).getStringCellValue();
        String data4 = sheet.getRow(1).getCell(1).getStringCellValue();

        System.out.println(data1 + " " + data2 + " " + data3 + " " + data4);

        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            XSSFCell cell = sheet.getRow(i).getCell(1);
            String cellData = cell.getStringCellValue();
            if (cellData.equals("Manager A")) {
                cell = sheet.getRow(i).getCell(0);
                String getEmployeesList = cell.getStringCellValue();
                System.out.println(getEmployeesList);
            }


        }

    }

    @Test
    public void readUserNamePasswordFromExcel() throws IOException {
        File file = new File("src/test/resources/data/Book1.xlsx");
        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            XSSFCell cell = sheet.getRow(i).getCell(0);
            String username = cell.getStringCellValue();
            cell = sheet.getRow(i).getCell(1);
            DataFormatter format = new DataFormatter();
            String password = format.formatCellValue(cell);
            System.out.println(username + " " + password);


        }

    }


}

