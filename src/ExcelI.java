public interface ExcelI {
	/** Analyzes tthe excel file and guess what DB Def to create.
	 * 
	 * 
	 */
	void createDB();

	void writeCreateCommand();

	void close();

}
