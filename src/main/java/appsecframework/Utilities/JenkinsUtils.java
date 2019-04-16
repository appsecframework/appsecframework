package appsecframework.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.RequestStatus;
import com.cdancy.jenkins.rest.domain.job.JobInfo;

import appsecframework.FrameworkConfig;
import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.Scan;
import appsecframework.ScanEvent;
import appsecframework.ScanResult;
import appsecframework.TestingTool;

public class JenkinsUtils {
	private static JenkinsClient jenkinsClient;

	// Default, if not specified
	private String jenkinsURL = "http://127.0.0.1:8080";
	private String jenkinsUser = "admin";
	private String jenkinsPass = "password";

	public JenkinsUtils(String jenkinsURL, String jenkinsUser, String jenkinsPass) {
		this.jenkinsURL = jenkinsURL;
		this.jenkinsUser = jenkinsUser;
		this.jenkinsPass = jenkinsPass;
		connectJenkins();
	}

	public JenkinsUtils() {
		connectJenkins();
	}

	public void startJob(String jobName) {
		jenkinsClient.api().jobsApi().build(null, jobName);
	}

	public String getJobConfig(String jobName) {
		return jenkinsClient.api().jobsApi().config(null, jobName);
	}

	public String getJobConfig(String folderPath, String jobName) {
		return jenkinsClient.api().jobsApi().config(folderPath, jobName);
	}

	public boolean setJobConfig(String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().config(null, jobName, configXML);
	}

	public boolean setJobConfig(String folderPath, String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().config(folderPath, jobName, configXML);
	}

	public RequestStatus createJob(String folderPath, String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().create(folderPath, jobName, configXML);
	}

	public RequestStatus createJob(String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().create(null, jobName, configXML);
	}
	
	public RequestStatus deleteJob(String jobName) {
		return jenkinsClient.api().jobsApi().delete(null, jobName);
	}

	public JobInfo getJobInfo(String jobName) {
		return jenkinsClient.api().jobsApi().jobInfo(null, jobName);
	}

	public String getDefaultJobConfig() {
		try {
			byte[] encoded;
			encoded = Files.readAllBytes(Paths.get("config/defaultconfig.xml"));
			return new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Return the Jenkins pipeline script from the XML string given
	 * 
	 * @param configXML The string of config.xml
	 * @return Jenkins pipeline script within the XML
	 */
	public static String extractJenkinsPipelineScript(String configXML) {
		String patternString = "(?s)<script>(.*)</script>";
		Pattern matcherPattern = Pattern.compile(patternString, Pattern.DOTALL);
		Matcher matcher = matcherPattern.matcher(configXML);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * Return the config.xml string with the Jenkins pipeline script replaced with
	 * the input script
	 * 
	 * @param configXML         The string of config.xml
	 * @param newPipelineScript The new pipeline script to be used
	 * @return New config.xml job configuration
	 */
	public static String replaceJenkinsPipelineScript(String configXML, String newPipelineScript) {
		String patternString = "(?s)<script>(.*)</script>";
		Pattern matcherPattern = Pattern.compile(patternString, Pattern.DOTALL);
		Matcher matcher = matcherPattern.matcher(configXML);
		return matcher.replaceFirst("<script>\n" + newPipelineScript + "\n</script>");
	}

	public static String formatPlainToXML(String pipelineScript) {
		String apostrophe = "\'";
		String quote = "\"";
		Pattern apostrophePattern = Pattern.compile(apostrophe);
		Pattern quotePattern = Pattern.compile(quote);

		Matcher matcher = apostrophePattern.matcher(pipelineScript);
		pipelineScript = matcher.replaceAll("&apos;");

		matcher = quotePattern.matcher(pipelineScript);
		pipelineScript = matcher.replaceAll("&quot;");

		return pipelineScript;
	}

	public static String formatXMLToPlain(String pipelineScript) {
		String apostrophe = "&apos;";
		String quote = "&quot;";
		Pattern apostrophePattern = Pattern.compile(apostrophe);
		Pattern quotePattern = Pattern.compile(quote);

		Matcher matcher = apostrophePattern.matcher(pipelineScript);
		pipelineScript = matcher.replaceAll("\\'");

		matcher = quotePattern.matcher(pipelineScript);
		pipelineScript = matcher.replaceAll("\\\"");

		return pipelineScript;
	}

	public boolean connectJenkins() {
		// TODO: Fix reconnect problem when Jenkins is not already running
		try {
			jenkinsClient = JenkinsClient.builder().endPoint(jenkinsURL).credentials(jenkinsUser + ":" + jenkinsPass)
					.build();
			if (jenkinsClient.api().systemApi().systemInfo().jenkinsVersion() == "-1") {
				System.out.println("Can't connect to Jenkins server, retrying in 10 seconds...");
				TimeUnit.SECONDS.sleep(10);
				jenkinsClient = null;
				return connectJenkins();
			}
		} catch (Exception e) {
			System.out.println("Can't connect to Jenkins server");
			System.out.println("Retrying...");
			try {
				TimeUnit.SECONDS.sleep(10);
				return connectJenkins();
			} catch (Exception e1) {
			}
			return false;
		}
		System.out.println("Connected to Jenkins");
		return true;
	}

	public String createScript(Project project) {
		/*
		 * TODO: 1. Separate into multiple methods for specific stages to make it easier
		 * to read 2. Check if the syntax is valid 3. convertUserVariables() 3. ****
		 * Scan now -> createScript and run Configure -> createScript only
		 */
		//ArrayList<String> imageList = new ArrayList<String>();
		ArrayList<TestingTool> toolList = new ArrayList<TestingTool>();
		
		String projectSource = project.getSource();
		String projectType = project.getPlatform();

		// Common start and end string
		String startBracket = "\t\t\tsteps {\n\t\t\t\tscript{\n\t\t\t\t\tdocker.withRegistry('https://registry.hub.docker.com', registryCredential) {\n";
		String endBracket = "\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n";
		String startBracketTest = "" + "\t\t\tsteps {\n" + "\t\t\t\tscript {\n" + "\t\t\t\t\ttry {\n";
		String endBracketTest = "" + "\t\t\t\t\t}\n" + "\t\t\t\t\tcatch(Exception e) {\n"
				+ "\t\t\t\t\t\tcurrentBuild.result = \'SUCCESS\'\n" + "\t\t\t\t\t\techo \"RESULT: SUCCESS\"\n"
				+ "\t\t\t\t\t}\n" + "\t\t\t\t}\n" + "\t\t\t}\n" + "\t\t}\n";
		String startBracketResult = "" + "\t\t\tsteps {\n" + "\t\t\t\tscript{\n";
		String endBracketResult = "" + "\t\t\t\t}\n" + "\t\t\t}\n" + "\t\t}\n";

		/// Prepare Stages in JenkinsFile
		String environment = "\tenvironment {\n" + "\t\tregistryCredential = 'dockerhub'\n\t}\n";
		String stagePrepareProject = "\t\tstage('Prepare Project') {\n" + startBracket + "\t\t\t\t\t\tprojectImage = ";
		String stagePrepareTestingTools = "\t\tstage('Prepare TestingTools') {\n" + startBracket;
		String stageTest = "\t\tstage('Test') {\n" + startBracketTest;
		String stageResult = "\t\tstage('Result') {\n" + startBracketResult;
		
		// Prepare Project stage
		if (projectSource.equals("Docker")) { //TODO: fix me hardcode only web app
			stagePrepareProject += "docker.image('" + project.getDockerRepo() + "')\n"
					+ "\t\t\t\t\t\tprojectImage.pull()\n" + "\t\t\t\t\t\tprojectImage.run('-p "
					+ project.getUrlPort().split(":")[1] + ":80 --rm --name " + project.getName() + "')\n" + endBracket;
		} else if (projectSource.equals("Git")) {
			stagePrepareProject += "sh git pull"; // To be updated
		} else if (projectSource.equals("Local Files")) {

		}

		// Prepare Testing tools stage
		if (project.isCustomScan()) {
			for (Scan s : project.getScanList()) {
				toolList.add(s.getTool());
			}
		}
		else {
			if (projectType.equals("Desktop")) {
				toolList = MainController.getDesktopToolList();
			} else if (projectType.equals("Mobile")) {
				toolList = MainController.getMobileToolList();
			} else if (projectType.equals("Web Application")) {
				toolList = MainController.getWebAppToolList();
			}
		}
		String prevImageName = "";
		for (TestingTool t : toolList) {
			String imageName = String.join("", t.getToolName().split(" ")) + "Image";
			if(imageName.equals(prevImageName)) continue;
			//imageList.add(imageName);
			String image = "\t\t\t\t\t\t" + imageName + " = docker.image('" + t.getToolDockerRepo() + "')\n";
			String pull = "\t\t\t\t\t\t" + imageName + ".pull()\n";
			stagePrepareTestingTools += image + pull;
			prevImageName = imageName;
		}
		stagePrepareTestingTools += endBracket;

		// Test stage
		List<String> scanScripts = getTestingScripts(project, toolList);
		// TODO: counter to close all containers after executed
		for (String s : scanScripts) {
			stageTest += "\t\t\t\t\t\tsh '" + s + "'\n";
		}
		stageTest += endBracketTest;

		// Result Stage
		stageResult += "\t\t\t\t\t\t" + "sh 'docker container stop " + project.getName() + "'\n";
		stageResult += endBracketResult;

		String script = "pipeline {\n" + environment + "\tagent any\n" + "\tstages{\n" + stagePrepareProject
				+ stagePrepareTestingTools + stageTest + stageResult + "\t}\n}";
		return script;
	}

	public boolean saveProjectToJob(Project project) {
		JobInfo jobInfo = getJobInfo(project.getName());
		// Check if job available
		if (jobInfo == null) {
			// Create new Jenkins Job using default job config
			String config = getDefaultJobConfig();
			if (!config.equals("")) {
				createJob(project.getName(), getDefaultJobConfig());
			} else
				return false;
		}
		String newScript = createScript(project);
		String newConfig = getJobConfig(project.getName());
		newConfig = replaceJenkinsPipelineScript(newConfig, newScript);
		setJobConfig(project.getName(), newConfig);
		return true;
	}

	/**
	 * Get Jenkins "latest" job status using job name (For displaying statuses)
	 * 
	 * @param project
	 * @return
	 */
	public List<String> fetchJobStatus(Project project) {
		List<String> statusList = new ArrayList<String>();
		try {
			URL url = new URL(jenkinsURL + "job/" + project.getName() + "/lastBuild/api/json?tree=result,timestamp");
			String auth = jenkinsUser + ":" + jenkinsPass;
			String encoding = DatatypeConverter.printBase64Binary(auth.getBytes("utf-8"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			if (connection.getResponseCode() != 200) {
				statusList.add("Not Started");
				statusList.add("-");
				return statusList;
			}
			InputStream content = connection.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(content));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				if (line.isEmpty()) {
					break;
				}
				result = result + line;
			}
			String[] split = result.split(",");

			String scanDate = split[2].split(":")[1].split("}")[0];
			long seconds = Long.parseLong(scanDate);
			Date temp = new Date(seconds);
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy");
			SimpleDateFormat dojosdf = new SimpleDateFormat("YYYY-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
			dojosdf.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
			String formattedDate = sdf.format(temp);
			String dojoDate = dojosdf.format(temp);
			String scanStatus = "";
			String modStatus = "";
			if (split[1].split(":")[1].equals("null")) {
				modStatus = "Scanning...";
			} else {
				scanStatus = split[1].split(":")[1].split("\"")[1];
				if (scanStatus.equals("SUCCESS")) {
					modStatus = "Success";
					// ScanEvent goes here
					if (!project.isImported()) {
						ScanEvent scanEvent = new ScanEvent();
						scanEvent.setProjectName(project.getName());
						scanEvent.setProjectPlatform(project.getPlatform());
						scanEvent.setScanStartDate(dojoDate);
						scanEvent.setScanFinishDate(dojoDate);
						scanEvent.setScanResultList(new ArrayList<ScanResult>());
						for (Scan s : project.getScanList()) {
							ScanResult scanResult = new ScanResult();
							scanResult.setScanName(s.getScanName());
							scanResult.setScanResultDirectory(project.getReportPath());
							String fileName = project.getName() + "_" + s.getTool().getToolName().replaceAll(" ", "")
									+ "." + s.getTool().getToolReportExtension();
							scanResult.setScanResultFileName(fileName);
							scanResult.setScanType(s.getScanName());
							scanEvent.getScanResultList().add(scanResult);
						}
						project.getScanEventList().add(scanEvent);
						MainController.getDojo().importScanResult(scanEvent);
						project.setImported(true);
					}
				} else if (scanStatus.equals("FAILURE")) {
					modStatus = "Failed";
				} else if (scanStatus.equals("ABORTED")) {
					modStatus = "Aborted";
				}
			}

			statusList.add(modStatus); // status
			statusList.add(formattedDate);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
		return statusList;
	}

	public String convertUserVariables(String script, Project project, TestingTool tool,
			FrameworkConfig frameworkConfig) {

		String urlPort = "\\{\\{SCAN_TARGET_URLPORT\\}\\}";
		String url = "\\{\\{SCAN_TARGET_URL\\}\\}";
		String port = "\\{\\{SCAN_TARGET_PORT\\}\\}";
		String path = "\\{\\{SCAN_TARGET_PATH\\}\\}";
		String file = "\\{\\{SCAN_RESULT_FILE\\}\\}";
		String filePath = "\\{\\{SCAN_RESULT_FILEPATH\\}\\}";
		String directory = "\\{\\{SCAN_RESULT_DIRECTORY\\}\\}";
		String username = "\\{\\{AUTH_APP_USERNAME\\}\\}";
		String password = "\\{\\{AUTH_APP_PASSWORD\\}\\}";
		// TODO: Sanitize input pattern, '/' after URL and file path
		String editedScript = script;
		String fileName = project.getName() + "_" + tool.getToolName().replace(" ", "") + "."
				+ tool.getToolReportExtension();

		editedScript = editedScript.replaceAll(file, fileName);
		editedScript = editedScript.replaceAll(path, project.getSourcePath());
		editedScript = editedScript.replaceAll(directory, project.getReportPath());
		editedScript = editedScript.replaceAll(filePath, project.getReportPath() + fileName);

		editedScript = editedScript.replaceAll(url, "http://" + project.getUrlPort().split(":")[0]);
		editedScript = editedScript.replaceAll(port, project.getUrlPort().split(":")[1]);
		editedScript = editedScript.replaceAll(urlPort, project.getUrlPort());

		editedScript = editedScript.replaceAll(username, "username");
		editedScript = editedScript.replaceAll(password, "password");
		return editedScript.trim();
	}

	/**
	 * Return scanning scripts list for this project
	 * 
	 * @param project
	 * @param toolList
	 * @return List of scanning script strings
	 */
	public List<String> getTestingScripts(Project project, ArrayList<TestingTool> toolList) {
		// TODO: Improve performance, remove redundant double loop
		List<String> testingScripts = new ArrayList<String>();
		// Custom Scan - Pick from list
		if (project.isCustomScan()) {
			for (Scan scan : project.getScanList()) {
				for (TestingTool tool : toolList) {
					if (scan.getTool().equals(tool)) {
						String script = tool.getScanScript().get(scan.getScanName());
						testingScripts.add(convertUserVariables(script, project, tool, MainController.getFrameworkConfig()));
						break;
					}
				}
			}
		}
		// Full scan
		else {
			for (TestingTool t : toolList) {
				for (String script : t.getScanScript().values()) {
					testingScripts.add(script);
				}
			}
		}
		return testingScripts;
	}

}
