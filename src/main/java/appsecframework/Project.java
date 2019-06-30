package appsecframework;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private String name;
	private String source;
	private String dockerRepo;
	private String gitRepo;
	private String sourcePath;
	private String platform;
	private String urlPort;
	private String reportPath;
	private boolean customScan;
	private List<Scan> scanList;
	private List<ScanEvent> scanEventList;
	private String pastScanResultPath;
	private String startScanDate;
	private String lastScanDate;
	private String scanStatus;
	private boolean recent;
	private boolean checkError;
	
	/*
	 * TODO:
	 * 1. Save selected testing tools list into project object
	 */

	public Project() {
	}
	
	public Project(String _name) {
		name = _name;
		source = "";
		dockerRepo = "";
		gitRepo = "";
		sourcePath = "";
		platform = "";
		urlPort = "";
		reportPath = "";
		customScan = false;
		scanList = new ArrayList<Scan>();
		scanEventList = new ArrayList<ScanEvent>();
		pastScanResultPath = "";
		startScanDate = "";
		lastScanDate = "";
		scanStatus = "";
		recent = false;
		checkError = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDockerRepo() {
		return dockerRepo;
	}

	public void setDockerRepo(String dockerRepo) {
		this.dockerRepo = dockerRepo;
	}

	public String getGitRepo() {
		return gitRepo;
	}

	public void setGitRepo(String gitRepo) {
		this.gitRepo = gitRepo;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String type) {
		this.platform = type;
	}

	public String getUrlPort() {
		return urlPort;
	}

	public void setUrlPort(String urlPort) {
		this.urlPort = urlPort;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public boolean isCustomScan() {
		return customScan;
	}

	public void setCustomScan(boolean customScan) {
		this.customScan = customScan;
	}

	public List<Scan> getScanList() {
		return scanList;
	}

	public List<ScanEvent> getScanEventList() {
		return scanEventList;
	}

	public void setScanEventList(List<ScanEvent> scanEventList) {
		this.scanEventList = scanEventList;
	}

	public void setScanList(List<Scan> scanList) {
		this.scanList = scanList;
	}

	public String getPastScanResultPath() {
		return pastScanResultPath;
	}

	public void setPastScanResultPath(String pastScanResultPath) {
		this.pastScanResultPath = pastScanResultPath;
	}

	public String getStartScanDate() {
		return startScanDate;
	}

	public void setStartScanDate(String startScanDate) {
		this.startScanDate = startScanDate;
	}

	public String getLastScanDate() {
		return lastScanDate;
	}

	public void setLastScanDate(String lastScanDate) {
		this.lastScanDate = lastScanDate;
	}

	public String getScanStatus() {
		return scanStatus;
	}

	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}

	public boolean isRecent() {
		return recent;
	}

	public void setRecent(boolean recent) {
		this.recent = recent;
	}

	public boolean isCheckError() {
		return checkError;
	}

	public void setCheckError(boolean checkError) {
		this.checkError = checkError;
	}
	
}
