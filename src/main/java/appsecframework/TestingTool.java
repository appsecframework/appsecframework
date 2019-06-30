package appsecframework;

import java.util.HashMap;

public class TestingTool {
	private String toolName;
	private String toolReportExtension;
	private String toolType;
	private String toolScanType;
	private String toolSource;
	private String toolDockerRepo;
	private String dockerOptions;
	private String toolGitRepo;
	private String toolPath;
	private String startScript;
	private HashMap<String, String> scanScript;
	private String stopScript;
	
	public TestingTool() {
		
	}
	
	public TestingTool(String name) {
		this.toolName = name;
		this.toolReportExtension = "";
		this.toolType = "";
		this.toolSource = "";
		this.toolDockerRepo = "";
		this.dockerOptions = "";
		this.toolGitRepo = "";
		this.toolPath = "";
		this.startScript = "";
		this.scanScript = new HashMap<String, String>();
		this.stopScript = "";
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	public String getToolReportExtension() {
		return toolReportExtension;
	}

	public void setToolReportExtension(String toolReportExtension) {
		this.toolReportExtension = toolReportExtension;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

	public String getToolScanType() {
		return toolScanType;
	}

	public void setToolScanType(String toolScanType) {
		this.toolScanType = toolScanType;
	}

	public String getToolSource() {
		return toolSource;
	}

	public void setToolSource(String toolSource) {
		this.toolSource = toolSource;
	}

	public String getToolDockerRepo() {
		return toolDockerRepo;
	}

	public void setToolDockerRepo(String toolDockerRepo) {
		this.toolDockerRepo = toolDockerRepo;
	}

	public String getDockerOptions() {
		return dockerOptions;
	}

	public void setDockerOptions(String dockerOptions) {
		this.dockerOptions = dockerOptions;
	}

	public String getToolGitRepo() {
		return toolGitRepo;
	}

	public void setToolGitRepo(String toolGitRepo) {
		this.toolGitRepo = toolGitRepo;
	}

	public String getToolPath() {
		return toolPath;
	}

	public void setToolPath(String toolPath) {
		this.toolPath = toolPath;
	}

	public String getStartScript() {
		return startScript;
	}

	public void setStartScript(String startScript) {
		this.startScript = startScript;
	}

	public HashMap<String, String> getScanScript() {
		return scanScript;
	}

	public void setScanScript(HashMap<String, String> scanScript) {
		this.scanScript = scanScript;
	}

	public String getStopScript() {
		return stopScript;
	}

	public void setStopScript(String stopScript) {
		this.stopScript = stopScript;
	}
	
}
