package appsecframework;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.UIManager;

import appsecframework.Utilities.ConfigUtils;
import appsecframework.Utilities.DockerUtils;
import appsecframework.Utilities.DojoUtils;
import appsecframework.Utilities.JenkinsUtils;
import appsecframework.Windows.DashboardWindow;

public class MainController {
	private static JenkinsUtils jenkins;
	private static DockerUtils docker;
	private static DojoUtils dojo;
	private static FrameworkConfig frameworkConfig;
	
	private static ArrayList<Project> projectList;
	private static ArrayList<TestingTool> toolList;
	private static ArrayList<TestingTool> desktopToolList;
	private static ArrayList<TestingTool> mobileToolList;
	private static ArrayList<TestingTool> webAppToolList;
	
	
	public static boolean initialize() {
		System.out.println("----------INITIALIZING----------");
		
		// Load config
		frameworkConfig = ConfigUtils.importFrameworkConfig("config/configs.yaml");
		projectList = ConfigUtils.importProjects("config/projects.yaml");
		toolList = ConfigUtils.importTools("config/tools.yaml");
		
		/*
		 * TODO:
		 * 1. Populate Setting Page with values from frameworkConfig (Show passwords as bullet)
		 * 2. Fetch Job statuses and show on project page
		 * 3. Add formal Javadoc style method comments
		 * 4. Edit methods to use values from frameworkConfig
		 * 5. Save settings from Setting page to frameworkConfig
		 */
		categorizeToolByType();
		
		docker = new DockerUtils();	
		// Connect to Docker and start Jenkins and DefectDojo
		if (docker.connectDocker()) {
			//docker.startDockerContainer("jenkins");
			//docker.startDockerContainer("dojo");
			docker.startDockerContainer("appsecframework_jenkins_1");
			docker.startDockerContainer("appsecframework_dojo_1");

		}
		else return false;
		
		// Connect to Jenkins
		jenkins = new JenkinsUtils(frameworkConfig.getJenkinsURL(), frameworkConfig.getJenkinsUsername(), frameworkConfig.getJenkinsPassword());
		
		// Connect to Dojo
		dojo = new DojoUtils(frameworkConfig.getDojoURL());
		dojo.authenticate(frameworkConfig.getDojoUsername(), frameworkConfig.getDojoPassword());
		
		System.out.println("--------------------------------");
		return true;
	}
	
	public static JenkinsUtils getJenkins() {
		return jenkins;
	}
	
	public static DojoUtils getDojo()
	{
		return dojo;
	}
	public static void setJenkins(JenkinsUtils jenkins) {
		MainController.jenkins = jenkins;
	}

	public static FrameworkConfig getFrameworkConfig() {
		return frameworkConfig;
	}

	public static void setFrameworkConfig(FrameworkConfig frameworkConfig) {
		MainController.frameworkConfig = frameworkConfig;
	}

	public static ArrayList<Project> getProjectList() {
		return projectList;
	}

	public static void setProjectList(ArrayList<Project> projectList) {
		MainController.projectList = projectList;
	}

	public static ArrayList<TestingTool> getToolList() {
		return toolList;
	}

	public static void setToolList(ArrayList<TestingTool> toolList) {
		MainController.toolList = toolList;
	}

	public static ArrayList<TestingTool> getDesktopToolList() {
		return desktopToolList;
	}

	public static void setDesktopToolList(ArrayList<TestingTool> desktopToolList) {
		MainController.desktopToolList = desktopToolList;
	}

	public static ArrayList<TestingTool> getMobileToolList() {
		return mobileToolList;
	}

	public static void setMobileToolList(ArrayList<TestingTool> mobileToolList) {
		MainController.mobileToolList = mobileToolList;
	}

	public static ArrayList<TestingTool> getWebAppToolList() {
		return webAppToolList;
	}

	public static void setWebAppToolList(ArrayList<TestingTool> webToolList) {
		MainController.webAppToolList = webToolList;
	}
	
	public static void categorizeToolByType() {
		ArrayList<TestingTool> desktopList = new ArrayList<TestingTool>();
		ArrayList<TestingTool> mobileList = new ArrayList<TestingTool>();
		ArrayList<TestingTool> webList = new ArrayList<TestingTool>();
		
		for(TestingTool t : toolList) {
			switch(t.getToolType()) {
			case "Desktop":
				desktopList.add(t);
				break;
			case "Mobile Application":
				mobileList.add(t);
				break;
			case "Web Application":
				webList.add(t);
				break;
			}
		}
		setDesktopToolList(desktopList);
		setMobileToolList(mobileList);
		setWebAppToolList(webList);
	}

	public static void main(String[] args){
		initialize();
		// Start UI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new DashboardWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
