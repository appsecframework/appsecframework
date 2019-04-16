package appsecframework;

public class ScanResult {
	private String scanType; // Tool name according to DefectDojo
	private String scanName; // Scan name according to tool setting
	private String scanResultFileName;
	private String scanResultDirectory; // Absolute Path, ends with /
	
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getScanName() {
		return scanName;
	}
	public void setScanName(String scanName) {
		this.scanName = scanName;
	}
	public String getScanResultFileName() {
		return scanResultFileName;
	}
	public void setScanResultFileName(String scanResultFileName) {
		this.scanResultFileName = scanResultFileName;
	}
	public String getScanResultDirectory() {
		return scanResultDirectory;
	}
	public void setScanResultDirectory(String scanResultDirectory) {
		this.scanResultDirectory = scanResultDirectory;
	}

}
