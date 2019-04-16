package appsecframework.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import appsecframework.FrameworkConfig;
import appsecframework.Project;
import appsecframework.TestingTool;

public class ConfigUtils {

	public static ArrayList<TestingTool> importTools(String path) {
		Yaml yaml = new Yaml();
		ArrayList<TestingTool> toolList = new ArrayList<TestingTool>();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			for (Object t : yaml.loadAll(inputStream)) {
				toolList.add((TestingTool)t);
			}
			return toolList;
		} catch (FileNotFoundException e) {
			System.out.println("Tools config file not found.");
			return toolList;
		}
	}
	
	public static boolean saveTools(String path, ArrayList<TestingTool> toolList) {
		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setPrettyFlow(true);
		Yaml yaml = new Yaml(dumpOptions);
		File file = new File(path);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			yaml.dumpAll(toolList.iterator(), writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Project> importProjects(String path) {
		Yaml yaml = new Yaml();
		ArrayList<Project> projectList = new ArrayList<Project>();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			for (Object p : yaml.loadAll(inputStream)) {
				projectList.add((Project)p);
			}
			return projectList;
		} catch (FileNotFoundException e) {
			System.out.println("Projects config file not found.");
			return projectList;
		}
	}
	
	public static boolean saveProjects(String path, ArrayList<Project> projectList) {
		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setPrettyFlow(true);
		dumpOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
		Yaml yaml = new Yaml(dumpOptions);
		File file = new File(path);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			yaml.dumpAll(projectList.iterator(), writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static FrameworkConfig importFrameworkConfig(String path) {
		Yaml yaml = new Yaml();
		InputStream inputStream;
		FrameworkConfig frameworkConfig;
		try {
			inputStream = new FileInputStream(path);
			frameworkConfig = (FrameworkConfig) yaml.load(inputStream);
			return frameworkConfig;
		} catch (FileNotFoundException e) {
			System.out.println("Framework config file not found.");
			frameworkConfig = new FrameworkConfig();
			return frameworkConfig;
		}
	}
	
	// TODO: Use this
	public static boolean saveFrameworkConfig(String path, FrameworkConfig frameworkConfig) {
		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setPrettyFlow(true);
		Yaml yaml = new Yaml(dumpOptions);
		File file = new File(path);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			yaml.dump(frameworkConfig, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
