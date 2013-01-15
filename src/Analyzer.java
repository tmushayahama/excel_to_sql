public class Analyzer implements AnalyzerI {
	public Analyzer() {
	}

	@Override
	public String rename(String attrName) {
		String result = attrName.trim();
		while (result.contains(" ")) {
			result = result.replace(" ", "_"); //will change the hard coding
		}
		return result.toLowerCase();
	}
	
}
