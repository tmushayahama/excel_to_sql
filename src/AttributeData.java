/**This class is not yet ready as the attributes are accessed publicly
 * 
 * @author Tremayne
 *
 */
public class AttributeData {
	public String attrName;
	public String ref;
	public boolean isAutoIncrement = false;
	public boolean isPrimaryKey = false;
	public String variableType;
	public AttributeData(String attrName, String variableType) {
		this.attrName = attrName;
		this.variableType = variableType;
	}
	public AttributeData(String attrName, String variableType,
			Boolean primaryKey) {
		this.attrName = attrName;
		this.variableType = variableType;
		this.isPrimaryKey = primaryKey;
	}
	public AttributeData(String attrName, Boolean autoIncrement) {
		this.attrName = attrName;
		this.variableType = "int";
		this.isPrimaryKey = true;
		this.isAutoIncrement = autoIncrement;
	}
}
