import java.util.List;
import java.util.Map;


public class Writer implements WriterI{
	public Writer() {
		
	} 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void writeCreateCommand() {
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
					createQuery += "primary key (" + tables.get(s).getAttributeName(i)+ ")";
				}
			}
			createQuery += "\n) \n\n";
		}
		System.out.println(createQuery);
		
	}

}
