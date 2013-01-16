import java.io.BufferedWriter;
import java.io.IOException;


public interface AnalyzerI {

	String rename(String attrName);

	void writeToFile(String line, BufferedWriter metaWriter) throws IOException;

	void close();

}
