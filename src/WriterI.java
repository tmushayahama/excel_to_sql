import java.io.BufferedWriter;
import java.io.IOException;



public interface WriterI {
	void writeCreateCommand();
	void writeToFile(String line, BufferedWriter metaWriter) throws IOException;
	void close();

}
