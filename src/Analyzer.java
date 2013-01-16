import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Analyzer implements AnalyzerI {
	private File renameFile;
	private BufferedWriter metaWriter;
	public Analyzer() {
		this.createFiles();
		try {
			metaWriter = new BufferedWriter(new FileWriter(this.renameFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public final String rename(String attrName) {
		String result = attrName.trim();
		while (result.contains(" ")) {
			result = result.replace(" ", "_"); //will change the hard coding
		}
		result = result.toLowerCase();
		try {
			this.writeToFile(result + "=" + attrName, metaWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public final void writeToFile(String line, BufferedWriter metaWriter) throws IOException {
		metaWriter.write(line);
		metaWriter.newLine();
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
		this.renameFile 	= new File("rename.txt");
		if (renameFile.exists()) {
			renameFile.delete();
		}
		try {
			renameFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Error: Could not create file.");
			System.exit(0);
		}
	}
}
