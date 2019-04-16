package appsecframework;

public class Scan {
	private TestingTool tool;
	private String scanName;
	
	// Constructors
	public Scan() {
		tool = new TestingTool();
		scanName = "";
	}
	
	public Scan(TestingTool _tool, String _scanName) {
		tool = _tool;
		scanName = _scanName;
	}
	
	// Getters & Setters
	public TestingTool getTool() {
		return tool;
	}

	public void setTool(TestingTool tool) {
		this.tool = tool;
	}

	public String getScanName() {
		return scanName;
	}

	public void setScanName(String scanName) {
		this.scanName = scanName;
	}
}
