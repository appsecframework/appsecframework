package appsecframework.Windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileSystemView;

import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.Scan;
import appsecframework.TestingTool;
import appsecframework.Utilities.ConfigUtils;
import appsecframework.Utilities.JenkinsUtils;
import appsecframework.Utilities.SwingUtils;

public class NewProjectWindow extends JFrame {

	private JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel configurationPanel;
	private JPanel customScanPanel;
	private JPanel customScanCheckboxPanel; // panel to contain JCheckBox choices for custom scan based on proejctType
	private JLabel lblUrl;
	private JTextField txtfProjectName;
	private JTextField txtfRespository;
	private JTextField txtfReportPath;
	private JTextField txtfUrl;
	private JScrollPane customScanScrollPane; // inside customScanPanel for being scroll able
	private JRadioButton rdbtnDocker;
	private JRadioButton rdbtnGit;
	private JRadioButton rdbtnLocalFiles;
	private JComboBox<Object> comboboxProjectType;
	private JCheckBox checkboxAutoScan;
	private JCheckBox checkboxCustomScan;
	private JButton btnBrowseRespository;

	private ArrayList<JCheckBox> customScanCheckBoxList; // keep tracking which JCheckBox is selected
	private String[] projectTypeList = new String[] { "", "Desktop", "Mobile Application", "Web Application" };
	private String projectType = "";
	private String sourceType = "Docker";
	private static ArrayList<Project> projectList;

	public NewProjectWindow() {
		projectList = MainController.getProjectList();
		initialize();
		frame.setVisible(true);
	}

	public void initialize() {
		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("New Project");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel();

		// Detail for this window goes here
		customScanPanel = new JPanel(new GridLayout(1, 0, 0, 0));
		customScanCheckboxPanel = new JPanel(new GridLayout(1, 0, 5, 5));
		customScanScrollPane = new JScrollPane(customScanCheckboxPanel);
		customScanPanel.add(customScanScrollPane);

		configurationPanel = new JPanel();
		JLabel lblNewProject = new JLabel("New Project");
		lblNewProject.setFont(new Font("Tahoma", Font.PLAIN, 24));

		JLabel lblRespository = new JLabel("Respository:");
		lblRespository.setForeground(Color.BLACK);
		lblRespository.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblProjectName = new JLabel("Project Name:");
		lblProjectName.setForeground(Color.BLACK);
		lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblType = new JLabel("Type :");
		lblType.setForeground(Color.BLACK);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblReportPath = new JLabel("Report Path: ");
		lblReportPath.setForeground(Color.BLACK);
		lblReportPath.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblUrl = new JLabel("URL: ");
		lblUrl.setForeground(Color.BLACK);
		lblUrl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUrl.setVisible(false);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.LIGHT_GRAY);

		RadioButtonActionListener rdbtnAction = new RadioButtonActionListener();
		rdbtnDocker = new JRadioButton("Docker");
		rdbtnDocker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnDocker.addActionListener(rdbtnAction);
		rdbtnDocker.setSelected(true);

		rdbtnGit = new JRadioButton("Git");
		rdbtnGit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnGit.addActionListener(rdbtnAction);
		//rdbtnGit.setVisible(false); //TODO: show me
		
		rdbtnLocalFiles = new JRadioButton("Local Files");
		rdbtnLocalFiles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLocalFiles.addActionListener(rdbtnAction);
		//rdbtnLocalFiles.setVisible(false); //TODO: show me

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnDocker);
		radioGroup.add(rdbtnGit);
		radioGroup.add(rdbtnLocalFiles);

		JFileChooser jfcRes = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfcRes.setDialogTitle("Choose a directory to open your files");
		jfcRes.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		btnBrowseRespository = new JButton("Browse...");
		btnBrowseRespository.setVisible(false);
		btnBrowseRespository.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = jfcRes.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfcRes.getSelectedFile();
					txtfRespository.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		
		JButton btnClear = new JButton("Clear");
		
		// Customization goes here
		txtfRespository = new JTextField();
		txtfProjectName = new JTextField();
		txtfReportPath = new JTextField();
		txtfUrl = new JTextField();
		txtfUrl.setVisible(false);

		checkboxAutoScan = new JCheckBox("Auto Scan");
		checkboxAutoScan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		checkboxAutoScan.setSelected(true);
		checkboxAutoScan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				customScanCheckboxPanel.removeAll();
				customScanCheckboxPanel.revalidate();
				customScanCheckboxPanel.repaint();
			}
		});

		checkboxCustomScan = new JCheckBox("Custom Scan");
		checkboxCustomScan.setFont(new Font("Tahoma", Font.PLAIN, 18));
		checkboxCustomScan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				customScanCheckBoxList = getCustomScanList(projectType);
				customScanCheckboxPanel = SwingUtils.createCustomScanPanel(customScanCheckboxPanel,
						customScanCheckBoxList);
			}
		});

		ButtonGroup checkboxGroup = new ButtonGroup();
		checkboxGroup.add(checkboxAutoScan);
		checkboxGroup.add(checkboxCustomScan);

		comboboxProjectType = new JComboBox<Object>(projectTypeList);
		comboboxProjectType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				projectType = comboboxProjectType.getSelectedItem().toString();
				if (projectType.equals("Web Application")) {
					lblUrl.setVisible(true);
					txtfUrl.setVisible(true);
				}
				else {
					lblUrl.setVisible(false);
					txtfUrl.setVisible(false);
				}
				if (checkboxCustomScan.isSelected()) {
					customScanCheckBoxList = getCustomScanList(projectType);
					customScanCheckboxPanel = SwingUtils.createCustomScanPanel(customScanCheckboxPanel,
							customScanCheckBoxList);
				}
			}
		});

		JButton btnCreateProject = new JButton("Create Project");
		btnCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Project project = new Project(txtfProjectName.getText());
				switch (sourceType) {
				case "Docker":
					project.setDockerRepo(txtfRespository.getText());
					break;
				case "Git":
					project.setGitRepo(txtfRespository.getText());
					break;
				case "Local Files":
					project.setSourcePath(txtfRespository.getText());
					break;
				}
				project.setSource(sourceType);
				project.setPlatform(projectType);
				project.setUrlPort(txtfUrl.getText());
				project.setReportPath(txtfReportPath.getText());
				project.setLastScanDate("-");
				project.setScanStatus("Not Start");
				if (checkboxCustomScan.isSelected()) {
					project.setCustomScan(true);
					for (JCheckBox c : customScanCheckBoxList) {
						if (c.isSelected()) {
							// TODO: fix Testing tool(text) -> UI -> String opt. me
							String[] text = c.getText().split(":: ");
							Scan scan = new Scan();
							for(TestingTool t: MainController.getToolList()) {
								if(text[0].trim().equals(t.getToolName())) {
									scan.setTool(t);
									break;
								}
							}
							scan.setScanName(text[1].trim());
							project.getScanList().add(scan);
						}
					}
				}
				projectList.add(project);
				ConfigUtils.saveProjects("config/projects.yaml", projectList);
				MainController.getJenkins().saveProjectToJob(project);
				frame.dispose();
				new ProjectWindow();
			}
		});

		GroupLayout gl_newProjectPanel = new GroupLayout(configurationPanel);
		gl_newProjectPanel.setHorizontalGroup(
			gl_newProjectPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newProjectPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addComponent(rdbtnDocker)
							.addGap(47)
							.addComponent(rdbtnGit)
							.addGap(47)
							.addComponent(rdbtnLocalFiles)
							.addGap(865))
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(separator, GroupLayout.DEFAULT_SIZE, 1474, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_newProjectPanel.createSequentialGroup()
									.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblReportPath, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblRespository)
										.addComponent(lblProjectName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_newProjectPanel.createSequentialGroup()
											.addComponent(txtfRespository, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
											.addGap(30)
											.addComponent(btnBrowseRespository, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
										.addComponent(txtfProjectName, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtfReportPath, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE))
									.addGap(882)))
							.addGap(31))
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addComponent(lblType)
							.addGap(26)
							.addComponent(comboboxProjectType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(1004))
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addComponent(btnCreateProject, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addComponent(lblUrl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtfUrl, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addComponent(lblNewProject)
							.addContainerGap(954, Short.MAX_VALUE))
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_newProjectPanel.createSequentialGroup()
									.addComponent(checkboxAutoScan, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(checkboxCustomScan, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
								.addComponent(customScanPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(647, Short.MAX_VALUE))))
		);
		gl_newProjectPanel.setVerticalGroup(
			gl_newProjectPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newProjectPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewProject)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtnDocker)
							.addComponent(rdbtnGit))
						.addComponent(rdbtnLocalFiles))
					.addGap(10, 10, Short.MAX_VALUE)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRespository)
						.addComponent(txtfRespository, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowseRespository, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProjectName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtfProjectName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReportPath, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtfReportPath, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_newProjectPanel.createSequentialGroup()
							.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboboxProjectType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(checkboxAutoScan))
						.addComponent(checkboxCustomScan))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(customScanPanel, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtfUrl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUrl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(173)
					.addGroup(gl_newProjectPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreateProject, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		configurationPanel.setLayout(gl_newProjectPanel);

		// Adjust the overall layout of this window
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(configurationPanel, GroupLayout.PREFERRED_SIZE, 1093, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(108, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE)
				.addComponent(configurationPanel, GroupLayout.PREFERRED_SIZE, 861, GroupLayout.PREFERRED_SIZE)
		);
		panel.setLayout(groupLayout);

	}

	// create JCheckBox list for Custom Scan function
	private ArrayList<JCheckBox> getCustomScanList(String type) {
		ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
		ArrayList<TestingTool> toolList = new ArrayList<TestingTool>();
		if (type == "Desktop") {
			toolList = MainController.getDesktopToolList();
		} else if (type == "Mobile Application") {
			toolList = MainController.getMobileToolList();
		} else if (type == "Web Application") {
			toolList = MainController.getWebAppToolList();
		} else {

		}

		if (!toolList.isEmpty()) {
			for (TestingTool t : toolList) {
				for (Map.Entry<String, String> entry : t.getScanScript().entrySet()) {
					JCheckBox checkbox = new JCheckBox(t.getToolName() + ":: " + entry.getKey() + "\n");
					checkboxList.add(checkbox);
				}
			}
		}
		return checkboxList;
	}

	class RadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			JRadioButton button = (JRadioButton) event.getSource();
			sourceType = button.getText();
			if (button == rdbtnLocalFiles) {
				btnBrowseRespository.setVisible(true);
			} else {
				btnBrowseRespository.setVisible(false);
			}
		}
	}
}
