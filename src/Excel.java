import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;


public class Excel implements ExcelI{
	//BufferedWriter exptWriter = new BufferedWriter(new FileWriter(this.expt));
	
	private String fileName ;
	private String mainTable = "main"; //hard coded
	/**The specified start column and end column, start row and end row.
	 * x0 = start column index value
	 * x1 = end column index value
	 * y0 = start row index value
	 * y1 = start row index value
	 * For example
	 * 		__A	__|__B___|__C___|
	 * 1		|_____|______|______|
	 * 2		|_____|______|______|
	 * 3		|_____|______|______|
	 * 
	 */
	private int x0 = 0, x1 = 10, y0, y1;
	private FileInputStream fis;
	private  HSSFWorkbook workbook;
    private HSSFSheet sheet; //i.e first worksheet hardcoded
    private ScannerI scanner = new Scanner();
	private WriterI writer = new Writer();
	private AnalyzerI analyzer = new Analyzer();
	private File mainFile;
	private File renameFile;
    
	/**A Constructor that will initialize the file input stream, workbook of the excel and the sheet 
	 * array. Sheets are stored in an array starting from 0.
     * @param fileName The Excel xls file name
     */
    public Excel(String fileName) {
		try {
			this.fileName = fileName;
			this.fis = new FileInputStream(this.fileName);
			this.workbook = new HSSFWorkbook(fis);
			this.sheet = workbook.getSheetAt(0); //the first sheet.
		} catch (FileNotFoundException e) {
			System.out.println("Excel File not found " + fileName);
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public final void createDB() {
    	DBDef.addTable(DBDef.DBName, this.mainTable, new AttributeData("id", true));
        for (int j = x0; j < x1; j++) {
            HSSFCell cell = sheet.getRow(0).getCell(j);
            switch (cell.getCellType()) {
            	case Cell.CELL_TYPE_NUMERIC:
            		System.out.print("Error -  A heading must only contain A-Za-z and _");
            		System.exit(0);
            		break;
            	case Cell.CELL_TYPE_STRING:
            	case Cell.CELL_TYPE_FORMULA:
            	//	scanner.analyzeAttributeName(cell.getStringCellValue(), );
            		String attrName = analyzer.rename(cell.getStringCellValue());
            		this.addTable(attrName);
            		break;
			default:
				System.out.print("Error");
				break;
            }
        }
        System.out.print("\n");
    }
    @Override
	public void writeCreateCommand() {
		this.writer.writeCreateCommand();
	}
    @Override
	public void close() {
		try {
			this.fis.close();
			this.analyzer.close();
		    this.writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	private void addTable (String attrName) {
		if (attrName.endsWith("(s)")) {
			System.out.println("One to many relationship identified... " + attrName + "\n");
			attrName = attrName.replace("(s)", "");
			DBDef.addTable(DBDef.DBName, attrName, new AttributeData("id", true));
			DBDef.addTable(DBDef.DBName, attrName, new AttributeData(attrName+"_id", "int", this.mainTable + ".id"));
			DBDef.addTable(DBDef.DBName, attrName, new AttributeData(attrName, "varchar(255)"));
		} else {
			DBDef.addTable(DBDef.DBName, "main", new AttributeData(attrName, "varchar(255)"));
		//System.out.println(cell.getStringCellValue() + " attribute has been added");
		}
	}
    /**Creates the main table file and rename file.
     * Rename File is used to map the database values to Excel
     * 
     */
    
    
   
    
     public static void analyzeExcelFile(String fileName){
        try{
           
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
