import java.util.HashMap;
import java.util.Map;
public class DBDef {
	private static Map<String, TableI> tables;
	/** Constructor which initialize the table list.
	 * 
	 */
	public static void  initializeDB() {
		tables = new HashMap<String, TableI>();
	}
	public static  void addTable(String dbName, String dbTableName, AttributeData attrData) {
		if (!tables.containsKey(dbTableName)) {
			TableI table = new Table();
			tables.put(dbTableName, table);
			table.setName(dbTableName);
		}
		tables.get(dbTableName).addAttribute(attrData);
	}
	public static Map<String, TableI> getTables() {
		return new HashMap<String, TableI>(DBDef.tables);
	}
}
