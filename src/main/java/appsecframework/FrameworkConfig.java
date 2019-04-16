package appsecframework;

public class FrameworkConfig {
	// Jenkins Configs
	private String jenkinsURL = "http://localhost:8080";			
	private String jenkinsUsername = "admin";
	private String jenkinsPassword = "password";
	
	// Docker Configs
	private String dockerSocket = "unix:///var/run/docker.sock";			// For connecting to Docker Daemon
	private String dockerUsername = "";										// For pulling from private repository
	private String dockerPassword = "";										// For pulling from private repository
	
	// Dojo Configs
	private String dojoURL = "http://localhost:8000";
	private String dojoUsername = "admin";
	private String dojoPassword = "password";
	
	// Git Configs
	private String gitEmail = "";												// For pulling from private repository
	private String gitUsername = "";											// For pulling from private repository
	private String gitPassword = "";											// For pulling from private repository
	
	// Report Directory Configs
	private String reportDirectory;
	
	// Getters & Setters
	public String getJenkinsURL() {
		return jenkinsURL;
	}

	public void setJenkinsURL(String jenkinsURL) {
		this.jenkinsURL = jenkinsURL;
	}

	public String getJenkinsUsername() {
		return jenkinsUsername;
	}

	public void setJenkinsUsername(String jenkinsUsername) {
		this.jenkinsUsername = jenkinsUsername;
	}

	public String getJenkinsPassword() {
		return jenkinsPassword;
	}

	public void setJenkinsPassword(String jenkinsPassword) {
		this.jenkinsPassword = jenkinsPassword;
	}

	public String getDockerSocket() {
		return dockerSocket;
	}

	public void setDockerSocket(String dockerSocket) {
		this.dockerSocket = dockerSocket;
	}

	public String getDockerUsername() {
		return dockerUsername;
	}

	public void setDockerUsername(String dockerUsername) {
		this.dockerUsername = dockerUsername;
	}

	public String getDockerPassword() {
		return dockerPassword;
	}

	public void setDockerPassword(String dockerPassword) {
		this.dockerPassword = dockerPassword;
	}

	public String getDojoURL() {
		return dojoURL;
	}

	public void setDojoURL(String dojoURL) {
		this.dojoURL = dojoURL;
	}

	public String getDojoUsername() {
		return dojoUsername;
	}

	public void setDojoUsername(String dojoUsername) {
		this.dojoUsername = dojoUsername;
	}

	public String getDojoPassword() {
		return dojoPassword;
	}

	public void setDojoPassword(String dojoPassword) {
		this.dojoPassword = dojoPassword;
	}

	public String getGitEmail() {
		return gitEmail;
	}

	public void setGitEmail(String gitEmail) {
		this.gitEmail = gitEmail;
	}

	public String getGitUsername() {
		return gitUsername;
	}

	public void setGitUsername(String gitUsername) {
		this.gitUsername = gitUsername;
	}

	public String getGitPassword() {
		return gitPassword;
	}

	public void setGitPassword(String gitPassword) {
		this.gitPassword = gitPassword;
	}

	public String getReportDirectory() {
		return reportDirectory;
	}

	public void setReportDirectory(String reportDirectory) {
		this.reportDirectory = reportDirectory;
	}
}
