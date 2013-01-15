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

/**@author Tremayne
 *
 */
public class Excel {
	private static File expt, trans, tissue;
	//BufferedWriter exptWriter = new BufferedWriter(new FileWriter(this.expt));
	private FileInputStream fis;
   private  HSSFWorkbook workbook;
    private HSSFSheet sheet; //i.e first worksheet hardcoded
	private ScannerI scanner;
	private WriterI writer;
	AnalyzerI analyzer;
    
	
	
    public Excel (String fileName) {
		try {
			this.fis = new FileInputStream(fileName);
			this.workbook = new HSSFWorkbook(fis);
			this.sheet = workbook.getSheetAt(0);
			scanner = new Scanner();
			writer = new Writer();
			analyzer = new Analyzer();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        //i.e first worksheet hardcoded
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
   /**We will write later
    * 
    * @param line
    * @param file
    * @param metaWriter
    * @throws IOException
    */
    private void writeToFile(String line, File file, BufferedWriter metaWriter) throws IOException {
		metaWriter.write(line);
		metaWriter.newLine();
	}
  
    
    public void createDB(){
    	DBDef.addTable("Genome", "main", new AttributeData("id", true));
        for (int j = 0; j < 10; j++) {
            HSSFCell cell = sheet.getRow(0).getCell(j);
            switch (cell.getCellType()) {
            	case Cell.CELL_TYPE_NUMERIC:
            		System.out.print("Error");
            		break;
            	case Cell.CELL_TYPE_STRING:
            	case Cell.CELL_TYPE_FORMULA:
            	//	scanner.analyzeAttributeName(cell.getStringCellValue(), );
            		String attrName = analyzer.rename(cell.getStringCellValue());
            		if (attrName.endsWith("(s)")) {
            			System.out.println("One to many relationship identified... " + cell.getStringCellValue() + "\n");
            			attrName = attrName.replace("(s)", "");
            			DBDef.addTable("Genome", attrName, new AttributeData("id", true));
            			DBDef.addTable("Genome", attrName, new AttributeData(attrName+"_id", "int", "main.id"));
            			DBDef.addTable("Genome", attrName, new AttributeData(attrName, "varchar(255)"));
            		} else {
            			DBDef.addTable("Genome", "main", new AttributeData(attrName, "varchar(255)"));
            		//System.out.println(cell.getStringCellValue() + " attribute has been added");
            		}
            		break;
            }
        }
        System.out.print("\n");
         try {
			this. fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /*   public static void analyzeExcelFile(String fileName){
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
            
            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }*/
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
    public static void main(String[] args) {
    	Excel excel = new Excel(args[0]);
    	DBDef.initializeDB();
    	excel.createDB();
    	excel.writer.writeCreateCommand();
    }
}
