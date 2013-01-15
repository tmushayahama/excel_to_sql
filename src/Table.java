import java.util.ArrayList;
import java.util.List;

public class Table implements TableI {
	private String tableName;
	
	private List<AttributeData> table;
	public Table() {
		table = new ArrayList<AttributeData>();
	}
	public void getAttrName(String attrName) {
		//this.attrName = attrName;
	}
	@Override
	public int getSize() {
		return table.size();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setName(String dbTableName) {
		this.tableName = dbTableName;
	}
	@Override
	public void addAttribute(AttributeData attrData) {
		table.add(attrData);
	}
	@Override
	public String getAttributeName(int index) {
		return table.get(index).attrName;
	}
	@Override
	public boolean isAutoIncrement(int i) {
		return table.get(i).isAutoIncrement;
	}
	@Override
	public String getVariableType(int i) {
		return table.get(i).variableType;
	}
	@Override
	public boolean isPrimaryKey(int index) {
		return table.get(index).isPrimaryKey;
	}
	@Override
	public String getRef(int index) {
		return table.get(index).ref;
	}
	@Override
	public boolean hasRef(int index) {
		return table.get(index).hasRef();
	}

}
