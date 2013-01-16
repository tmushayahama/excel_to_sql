/**The main class of the project implements the ExcelI class. For more documentation, 
 * see the interface ExcelI
 * To run - Excel [your excelpath]
 * 
 * @author Tremayne
 */
public class Main {
	/**The entry point of the program 
     * 
     * @param args
     */
    
    public static void main(String[] args) {
    	Excel excel = new Excel(args[0]);
    	DBDef.DBName = args[0];
    	DBDef.initializeDB();
    	excel.createDB();
    	excel.writeCreateCommand();
    	excel.close();
    	
    }
}
