import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Writer implements WriterI{
	private File mainFile;
	private BufferedWriter metaWriter;
	public Writer() {
		this.createFiles();
		try {
			metaWriter = new BufferedWriter(new FileWriter(this.mainFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
   /**Write one line to the files created.
    * 
    * @param line The line being added.
    * @param file 
    * @param metaWriter
    * @throws IOException
    */
	@Override
	public final void writeToFile(String line, BufferedWriter metaWriter) throws IOException {
		metaWriter.write(line);
		metaWriter.newLine();
	}
	@Override
	public final void writeCreateCommand() {
		String createQuery = "";
		Map<String, TableI> tables =  DBDef.getTables();
		for(String s: tables.keySet()) {
			createQuery += "create " +  s + "(\n";
			for (int i = 0; i < tables.get(s).getSize(); i++) {
				createQuery += '\t' + tables.get(s).getAttributeName(i) + " " +  tables.get(s).getVariableType(i);
				if (tables.get(s).isAutoIncrement(i)) {
					createQuery += " not null auto_increment" ;
				}
				createQuery += ",\n";
			}
					//write all the primary key and foreign keys
			for (int i = 0; i < tables.get(s).getSize(); i++) {
				if (tables.get(s).isPrimaryKey(i)) {
					createQuery += "\tprimary key (" + tables.get(s).getAttributeName(i)+ ")";
				}
				//we assume that there is at least one primary key so that a comma
				if (tables.get(s).hasRef(i)) {
					createQuery += ",\n";
					createQuery += "\tforeign key(" + tables.get(s).getAttributeName(i)+ ") references " + tables.get(s).getRef(i);
				}
			}
			createQuery += "\n); \n";
		}
		System.out.println(createQuery);
		try {
			this.writeToFile(createQuery, metaWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public final void close() {
		try {
			this.metaWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createFiles() {
		this.mainFile 	= new File("main.sql");
		if (mainFile.exists()) {
			mainFile.delete();
		}
		try {
			mainFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error: Could not create file.");
			System.exit(0);
		}
	}
}
