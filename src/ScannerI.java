import java.io.File;
import java.util.Map;
public interface ScannerI {

	Map<String, TableI> getTables();
	void analyzeAttributeName(String attrName, String variableType);
	void analyzeAttributeName(String attrName, String variableType, Boolean primaryKey);
	
}
