import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner implements ScannerI{
	private String nullRegex = "'[\\s]*(null)[\\s]*'";
	private String trimRegex = "'(([\\s]+[\\w]+[\\s]+)|([\\s]*[\\w]+[\\s]+)|([\\s]+[\\w]+[\\s]*))'";
	private String oneToManyRegex = "'[\\s]*([\\w]|;|[\\s])+[\\s]*'";
	
	/** Constructor which initialize the table list.
	 * 
	 */
	public Scanner() {
	}
	
	private String replaceOneToMany(String query) {
		Pattern pattern = Pattern.compile(this.oneToManyRegex);
        Matcher matcher = pattern.matcher(query);
        String result ="";
       // System.out.println(query);
        while (matcher.find()) {
            String s = matcher.group();
            String trimmed = s.substring(1, s.length()-1).trim();
            //System.out.println(s);
        	String arr[] = trimmed.split(";");
	        for (int i = 0;i< arr.length;i++) {
	        	query = query.replaceFirst(this.oneToManyRegex, "'"+arr[i].trim()+"'");
	        	result += "\n" + query;
            }
            
        }
		return result;
	}
	private String replaceNulls(String query) {
			return query.replaceAll(nullRegex, "null");
	}
	/**to trim the fields when they contain lead whitespace. Other the query will 
	not be changed
	**/
	private String trimFields(String query) {
        Pattern pattern = Pattern.compile(this.trimRegex);
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()) {
            String s = matcher.group();
            String trimmed = "'" + s.substring(1, s.length()-1).trim() + "'";
            query = query.replaceFirst(trimRegex, trimmed);
        }
		return query;
	}
	@Override
	public String beautifyQuery(String query) {
		query = this.replaceNulls(query);
		query = this.trimFields(query);
		return this.replaceOneToMany(query);
	}
	/*public static void main(String[] args) {
		Scanner myScanner = new Scanner();
		//System.out.println(myScanner.beautifyQuery("insert '1  ','' ' 22', ', ' null',  3  ', '444', 'null'"));
		//System.out.println(myScanner.replaceOneToMany("insert into transcription values(default, 3,  'E2Fa  ; E2fb;   rrt   ;   piuy');"));
	}*/
	
}
