import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

/**
 * This is a simple class to read BUTOC excel file to construct a 
 * MySQL DB create and insert commands. Some of the functions are
 * hard coded according to the BUTOC database
 */
public class Excel {
	private static int queryCell[];
	private static File expt, trans, tissue;
	//BufferedWriter exptWriter = new BufferedWriter(new FileWriter(this.expt));
	
	private static ExcelToSQLScanner xlsToSql;
    public static void main(String[] args) {
    	Excel.queryCell = new int[args.length -1];
    	//populate the queryCell array 
    	for (int i = 0; i < Excel.queryCell.length; i++) {
    		Excel.queryCell[i] = args[i+1].toLowerCase().charAt(0) - 'a';
    	}
    	xlsToSql = new ExcelToSQLScanner();
        //writeDataToExcelFile(fileName);
    	
        analyzeExcelFile(args[0]);
    }
    /**This function creates the insert files used to generate the database.
     * These files have hard coded file since we are interested in only 3 tables
     * for the database.
     * 
     */
    private void createFiles() {
		expt 	= new File("expt_inserts.txt");
		trans 	= new File("trans_inserts.txt");
		tissue 	= new File("tissue_inserts.txt");
		if(expt.exists()) {
			expt.delete();
		}
		if(trans.exists()) {
			trans.delete();
		}
		if(tissue.exists()) {
			tissue.delete();
		}
		try {
			expt.createNewFile();
			tissue.createNewFile();
			trans.createNewFile();
		} catch (IOException e) {
			System.out.println("Error: Could not create file.");
			System.exit(0);
		}
	}
   
    private void writeToFile(String line, File file, BufferedWriter metaWriter) throws IOException {
		metaWriter.write(line);
		metaWriter.newLine();
	}
    public static void analyzeExcelFile(String fileName){
        try{
            FileInputStream fis = new FileInputStream(fileName);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            String line= "";
            for (int i = 1; i< 23; i++) {
                for (int j = 0; j < Excel.queryCell.length; j++) {
                    HSSFCell cell = sheet.getRow(i).getCell(Excel.queryCell[j]);
                    switch (cell.getCellType()) {
                    	case Cell.CELL_TYPE_NUMERIC:
                    		System.out.print((int) cell.getNumericCellValue() + "\t");
                    		break;
                    	case Cell.CELL_TYPE_STRING:
                    		System.out.print(cell.getStringCellValue() + "\t");
                    		break;
                    	case Cell.CELL_TYPE_FORMULA:
                    		line = Excel.xlsToSql.beautifyQuery(cell.getStringCellValue());
                    		System.out.print(line);
                    		break;
                    }
                }
                //System.out.print("\n");
            }
            
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public static void writeDataToExcelFile(String fileName) {
        try {

            HSSFWorkbook myWorkBook = new HSSFWorkbook();
            HSSFSheet mySheet = myWorkBook.createSheet();
            HSSFRow myRow;
            HSSFCell myCell;

            for (int rowNum = 0; rowNum < 10; rowNum++) {
                myRow = mySheet.createRow(rowNum);
                for (int cellNum = 0; cellNum < 5; cellNum++) {
                    myCell = myRow.createCell(cellNum);
                    myCell.setCellValue(new HSSFRichTextString(rowNum + "," + cellNum));
                }
            }


            FileOutputStream out = new FileOutputStream(fileName);
            myWorkBook.write(out);
            out.flush();
            out.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
