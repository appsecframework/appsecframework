package appsecframework;

import java.util.ArrayList;
import java.util.List;

public class ScanEvent {
	private String projectName;
	private String projectPlatform; // Desktop, Web, Mobile
	private List<ScanResult> scanResultList;
	private String scanStartDate;
	private String scanFinishDate;
	private boolean imported = false;
	private int engagementId;

	public ScanEvent() {
		scanResultList = new ArrayList<ScanResult>();
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectPlatform() {
		return projectPlatform;
	}
	public void setProjectPlatform(String projectType) {
		this.projectPlatform = projectType;
	}
	public List<ScanResult> getScanResultList() {
		return scanResultList;
	}
	public void setScanResultList(List<ScanResult> scanResultList) {
		this.scanResultList = scanResultList;
	}
	public String getScanStartDate() {
		return scanStartDate;
	}
	public void setScanStartDate(String scanDate) {
		this.scanStartDate = scanDate;
	}
	public String getScanFinishDate() {
		return scanFinishDate;
	}

	public void setScanFinishDate(String scanFinishDate) {
		this.scanFinishDate = scanFinishDate;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public int getEngagementId() {
		return engagementId;
	}
	public void setEngagementId(int engagementId) {
		this.engagementId = engagementId;
	}
}
