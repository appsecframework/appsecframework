package appsecframework.Utilities;

import java.util.List;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.ContainerNotFoundException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.exceptions.ImageNotFoundException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;

public class DockerUtils {
	private static DockerClient dockerClient;
	private static List<Container> dockerContainerList;

	public boolean connectDocker() {
		try {
			dockerClient = DefaultDockerClient.builder().uri("unix:///var/run/docker.sock").build();
			dockerClient.ping();
			dockerContainerList = listContainers();
			System.out.println("Connected to Docker");
		} catch (Exception e) {
			System.out.println("Can't connect to Docker");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void startDockerContainer(String containerName) {
		try {
			if (!dockerClient.inspectContainer(containerName).state().running()) {
				System.out.println("Starting container: \"" + containerName + "\"");
				dockerClient.startContainer(containerName);
			} else {
				System.out.println("Container \"" + containerName + "\" is already running");
			}
		} catch (ContainerNotFoundException notFoundException) {
			// This place should not be reached
			System.out.println("Container not found.");
			notFoundException.printStackTrace();
		} catch (DockerException | InterruptedException systemException) {
			// This place should also not be reached
			System.out.println("System error.");
			systemException.printStackTrace();
		}
	}

	public boolean createDockerContainer(String containerName, String imageName) {
		ContainerConfig config = ContainerConfig.builder().image(imageName).build();
		try {
			System.out.println("Creating container \"" + containerName + "\" from image \"" + imageName + "\"");
			dockerClient.createContainer(config, containerName);
			return true;
		} catch (DockerException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public void runDockerContainer(String containerName, String imageName) {
		boolean containerExists = false;

		try {
			if (dockerClient.inspectContainer(containerName).state().running()) {
				containerExists = true;
				System.out.println("Container \"" + containerName + "\" is already running.");
				return;
			} else {
				containerExists = true;
				startDockerContainer(containerName);
			}

		} catch (ContainerNotFoundException notFoundException) {
			containerExists = false;
		} catch (DockerException | InterruptedException systemException) {
			System.out.println("System error.");
			systemException.printStackTrace();
		}

		if (!containerExists) {
			System.out.println("Container not found, building container...");
			try {
				if (dockerClient.inspectImage(imageName) != null) {
					createDockerContainer(containerName, imageName);
					startDockerContainer(containerName);
				}
			} catch (ImageNotFoundException notFoundExcpetion) {
				if (pullNewImage(imageName)) {
					createDockerContainer(imageName, containerName);
					startDockerContainer(containerName);
				} else {
					System.out.println("Invalid image.");
				}
			} catch (Exception systemException) {
				System.out.println("System Error: ");
				systemException.printStackTrace();
			}
		}
	}

	public boolean pullNewImage(String imageName) {
		try {
			System.out.println("Pulling image \"" + imageName + "\"....");
			dockerClient.pull(imageName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private List<Container> listContainers() {
		try {
			return dockerClient.listContainers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	public List<Container> getContainers() {
		return dockerContainerList;
	}
}
