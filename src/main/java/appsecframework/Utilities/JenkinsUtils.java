package appsecframework.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.RequestStatus;
import com.cdancy.jenkins.rest.domain.job.BuildInfo;
import com.cdancy.jenkins.rest.domain.job.JobInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import appsecframework.DojoProduct;
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
	private boolean delay = false;

	public JenkinsUtils(String jenkinsURL, String jenkinsUser, String jenkinsPass) {
		this.jenkinsURL = jenkinsURL;
		this.jenkinsUser = jenkinsUser;
		this.jenkinsPass = jenkinsPass;
		connectJenkins();
	}

	public JenkinsUtils() {
		connectJenkins();
	}
	
	public boolean connectJenkins() {
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
	
	public int startJob(String folderPath, String jobName) {
		return jenkinsClient.api().jobsApi().build(folderPath, jobName).value();
	}
	
	public int startJob(String jobName) {
		return startJob(null, jobName);
	}

	public String getJobConfig(String folderPath, String jobName) {
		return jenkinsClient.api().jobsApi().config(folderPath, jobName);
	}
	
	public String getJobConfig(String jobName) {
		return getJobConfig(null, jobName);
	}

	public boolean setJobConfig(String folderPath, String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().config(folderPath, jobName, configXML);
	}
	
	public boolean setJobConfig(String jobName, String configXML) {
		return setJobConfig(null, jobName, configXML);
	}
	
	public RequestStatus createJob(String folderPath, String jobName, String configXML) {
		return jenkinsClient.api().jobsApi().create(folderPath, jobName, configXML);
	}
	
	public RequestStatus createJob(String jobName, String configXML) {
		return createJob(null, jobName, configXML);
	}
	
	public RequestStatus deleteJob(String folderPath, String jobName) {
		return jenkinsClient.api().jobsApi().delete(folderPath, jobName);
	}
	
	public RequestStatus deleteJob(String jobName) {
		return deleteJob(null, jobName);
	}
	
	public JobInfo getJobInfo(String folderPath, String jobName) {
		return jenkinsClient.api().jobsApi().jobInfo(folderPath, jobName);
	}
	
	public JobInfo getJobInfo(String jobName) {
		return getJobInfo(null, jobName);
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
		return matcher.replaceFirst("<script>\n" + Matcher.quoteReplacement(newPipelineScript) + "\n</script>");
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

	public String createScript(Project project) {
		long startTime = System.currentTimeMillis();

		ArrayList<TestingTool> toolList = new ArrayList<TestingTool>();

		String projectSource = project.getSource();
		String projectType = project.getPlatform();
		String failStage = "\t\t\t\t\tFAILED_STAGE=env.STAGE_NAME\n";

		// Common start and end string
		String startBracket = "\t\t\tsteps {\n\t\t\t\tscript{\n" + failStage + "\t\t\t\t\tdocker.withRegistry('https://registry.hub.docker.com', registryCredential) {\n";
		String endBracket = "\t\t\t\t\t}\n\t\t\t\t}\n\t\t\t}\n\t\t}\n";

		/// Prepare Stages in JenkinsFile
		String environment = "\tenvironment {\n" + "\t\tregistryCredential = 'dockerhub'\n\t}\n";
		String stagePrepareProject = "\t\tstage('Prepare Project') {\n" + startBracket;
		String stagePrepareTestingTools = "\t\tstage('Prepare TestingTools') {\n" + startBracket;
		String stageTest = "\t\tstage('Test') {\n" + "\t\t\tsteps {\n" + "\t\t\t\tscript {\n" + failStage;
		String stageResult = "\t\tstage('Result') {\n" + "\t\t\tsteps {\n";
		String postStage = "\tpost{\n" + "\t\tfailure{\n" + "\t\t\techo \"!!!Failed stage name: ${FAILED_STAGE}!!!\"\n" + 
				"\t\t}\n" + "\t}\n";
		
		// Project stage
		if (projectSource.equals("Docker") && !project.getDockerRepo().equals("")) {
			stagePrepareProject += "\t\t\t\t\t\tprojectImage = docker.image('" + project.getDockerRepo() + "')\n"
					+ "\t\t\t\t\t\tprojectImage.pull()\n" + "\t\t\t\t\t\tprojectImage.run('-p "
					+ project.getUrlPort().split(":")[1] + ":80 --rm --name " + project.getName() + "')\n";
		} else if (projectSource.equals("Git")) { // To be updated
			stagePrepareProject += "sh git pull"; 
		} else if (projectSource.equals("Local Files")) { // To be updated
			stagePrepareProject += "cd ";
		}
		stagePrepareProject += endBracket;

		// TestingTools stage
		if (project.isCustomScan()) {
			for (Scan s : project.getScanList()) {
				toolList.add(s.getTool());
			}
		} else {
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
			if (imageName.equals(prevImageName))
				continue;
			String image = "\t\t\t\t\t\t" + imageName + " = docker.image('" + t.getToolDockerRepo() + "')\n";
			String pull = "\t\t\t\t\t\t" + imageName + ".pull()\n";
			stagePrepareTestingTools += image + pull;
			prevImageName = imageName;
		}
		stagePrepareTestingTools += endBracket;

		// Test stage
		stageTest += "\t\t\t\t\tdef warn = false\n";
		List<String> scanScripts = getTestingScripts(project, toolList);
		for (String s : scanScripts) {
			stageTest += "\t\t\t\t\tdef cstatus = sh script:'" + s + "', returnStatus: true\n";
			stageTest += "\t\t\t\t\tif (!warn &amp;&amp; cstatus != 0) warn = true\n";
		}
		stageTest += "\t\t\t\t\tif (warn) {\n\t\t\t\t\t\techo '[WARN] Error in Test'\n\t\t\t\t\t\tcurrentBuild.result = 'SUCCESS'\n\t\t\t\t\t}\n";
		stageTest += "\t\t\t\t}\n" + "\t\t\t}\n" + "\t\t}\n";

		// Result Stage TODO://fixme for other options
		
		stageResult += "\t\t\t\tscript{\n" + failStage + "\t\t\t\t\t";
		if (!project.getDockerRepo().equals("")) stageResult +=  "sh 'docker container stop " + project.getName() + "'\n";
		stageResult += "\t\t\t\t}\n" + "\t\t\t}\n" + "\t\t}\n" + "\t}\n";

		String script = "def FAILED_STAGE\npipeline {\n" + environment + "\tagent any\n" + "\tstages{\n" + stagePrepareProject
				+ stagePrepareTestingTools + stageTest + stageResult + postStage + "}\n";
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Create Jenkins Script: " + elapsedTime + " ms");
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
		String newConfig = getJobConfig(project.getName());
		String newScript = createScript(project);
		newConfig = replaceJenkinsPipelineScript(newConfig, newScript);
		setJobConfig(project.getName(), newConfig);
		return true;
	}

	public int getLatestBuildId(String projectName) {
		return jenkinsClient.api().jobsApi().lastBuildNumber(null, projectName);
	}
	
	/**
	 * Get Jenkins "latest" job status using job name (For displaying statuses)
	 * 
	 * @param project
	 * @return
	 */
	public List<String> fetchJobStatus(Project project) {
		List<String> statusList = new ArrayList<String>();
		String modStatus = "Scanning...";
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
			String scanDate = split[2].split(":")[1].split("}")[0]; // unix time

			long seconds = Long.parseLong(scanDate);
			Date date = new Date(seconds);
			SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy, HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
			String formattedDate = formatter.format(date);

			if (split[1].split(":")[1].equals("null")) {
				delay = true;
			} else {
				String scanStatus = split[1].split(":")[1].split("\"")[1];
				if (scanStatus.equals("SUCCESS") && delay) {
					modStatus = "Success";

					SimpleDateFormat dojoFormatter = new SimpleDateFormat("YYYY-MM-dd");
					dojoFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
					String dojoFormattedDate = dojoFormatter.format(date);

					// ScanEvent goes here
					ScanEvent scanEvent = project.getScanEventList().get(project.getScanEventList().size() - 1);
					if (!scanEvent.isImported()) {

						scanEvent.setScanFinishDate(formattedDate);
						scanEvent.setDojoFinishDate(dojoFormattedDate);
						scanEvent.setScanResultList(new ArrayList<ScanResult>());

						for (Scan s : project.getScanList()) {
							ScanResult scanResult = new ScanResult();
							TestingTool t = s.getTool();
							scanResult.setScanName(s.getScanName());
							scanResult.setScanResultDirectory(project.getReportPath());
							String fileName = project.getName() + "_" + t.getToolName().replaceAll(" ", "") + "."
									+ s.getTool().getToolReportExtension();
							scanResult.setScanResultFileName(fileName);
							scanResult.setScanType(t.getToolScanType());
							scanEvent.getScanResultList().add(scanResult);
						}
						scanEvent.setImported(false);
						delay = false; // set delay for next project
						MainController.getDojo().importScanResult(project, scanEvent);
					}
				} else if (scanStatus.equals("FAILURE")) {
					modStatus = "Failed";
					project.setCheckError(true);
				} else if (scanStatus.equals("ABORTED")) {
					modStatus = "Aborted";
				} else if (scanStatus.equals("UNSTABLE")) {
					modStatus = "Unstable";
				}
			}
			statusList.add(modStatus);
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
	
	public String getErrorResponse(Project project) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(jenkinsURL + "job/" + project.getName() + "/" + getLatestBuildId(project.getName()) +"/consoleText");
		httpGet.setHeader("Authorization", jenkinsClient.authValue());
		CloseableHttpResponse response;
		String error = null;
		try {
			response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				error = EntityUtils.toString(response.getEntity());
			}
			else {
				String responseBody = EntityUtils.toString(response.getEntity());
				System.out.println("Failed to get Error log. Status code: " + statusCode);
				System.out.println(responseBody);
			}
			client.close();
			//split string to get stage
			String stageError = error.split("!!!")[1];
			return stageError ;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
						testingScripts
								.add(convertUserVariables(script, project, tool, MainController.getFrameworkConfig()));
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

