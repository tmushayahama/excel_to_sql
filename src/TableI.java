public interface TableI {
	int getSize();
	String getName();
	void setName(String dbTableName);
	void addAttribute(AttributeData attrData);
	String getAttributeName(int index);
	boolean isAutoIncrement(int index);
	String getVariableType(int index);
	boolean isPrimaryKey(int index);
	String getRef(int i);
	boolean hasRef(int i);
	
}
