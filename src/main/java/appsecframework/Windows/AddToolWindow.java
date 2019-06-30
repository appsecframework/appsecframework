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
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileSystemView;

import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.TestingTool;
import appsecframework.Utilities.ConfigUtils;
import appsecframework.Utilities.SwingUtils;

public class AddToolWindow extends JFrame {

	private JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel newToolsPanel;
	private JTextField txtfName;
	private JTextField txtfReportExtension;
	private JTextField txtfRespository;
	private JTextField txtfScanType;
	private JTextPane commandPane;
	private JRadioButton rdbtnDocker;
	private JRadioButton rdbtnGit;
	private JRadioButton rdbtnLocalFiles;
	private JButton btnBrowseRespository;

	private static ArrayList<TestingTool> toolList;

	private String[] toolTypeList = new String[] { "", "Desktop", "Mobile Application", "Web Application" };
	private String toolType = "";
	private String sourceType = "Docker";

	public AddToolWindow() {
		toolList = MainController.getToolList();
		initialize();
		frame.setVisible(true);
	}

	public void initialize() {
		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Add Tool");
		frame.getContentPane().add(panel); //
		menuPanel = SwingUtils.getMenuPanel(frame);	
		
		// Detail for this window goes here
		newToolsPanel = new JPanel();
		JLabel lblNewTools = new JLabel("Add Tool");
		lblNewTools.setFont(new Font("Tahoma", Font.PLAIN, 24));

		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.BLACK);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblReportExtension = new JLabel("Report Extension: ");
		lblName.setForeground(Color.BLACK);
		lblReportExtension.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblRespository = new JLabel("Respository: ");
		lblRespository.setForeground(Color.BLACK);
		lblRespository.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblScanType = new JLabel("DefectDojo Scan Type: ");
		lblScanType.setForeground(Color.BLACK);
		lblScanType.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblType = new JLabel("Type: ");
		lblType.setForeground(Color.BLACK);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblCommand = new JLabel("Command: ");
		lblCommand.setForeground(Color.BLACK);
		lblCommand.setFont(new Font("Tahoma", Font.PLAIN, 18));

		txtfName = new JTextField();
		txtfReportExtension = new JTextField();
		txtfRespository = new JTextField();
		txtfScanType = new JTextField();

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
		rdbtnGit.setVisible(false);

		rdbtnLocalFiles = new JRadioButton("Local Files");
		rdbtnLocalFiles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnLocalFiles.addActionListener(rdbtnAction);
		rdbtnLocalFiles.setVisible(false);

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(rdbtnDocker);
		radioGroup.add(rdbtnGit);
		radioGroup.add(rdbtnLocalFiles);

		JComboBox comboboxType = new JComboBox(toolTypeList);
		comboboxType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				toolType = comboboxType.getSelectedItem().toString();
			}
		});

		JButton btnAddTool = new JButton("Add Tool");
		btnAddTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TestingTool tool = new TestingTool();
				HashMap<String, String> scanScript = new HashMap<String, String>();

				String[] token = commandPane.getText().trim().split("\n");
				for (String s : token) {
					String[] temp = s.split(":", 2);
					scanScript.put(temp[0].trim(), temp[1].trim());
				}
				switch (sourceType) {
				case "Docker":
					tool.setToolDockerRepo(txtfRespository.getText());
					break;
				case "Git":
					tool.setToolGitRepo(txtfRespository.getText());
					break;
				case "Local Files":
					tool.setToolPath(txtfRespository.getText());
					break;
				}
				tool.setToolName(txtfName.getText());

				tool.setToolReportExtension(txtfReportExtension.getText());
				if (txtfReportExtension.getText().charAt(0) == '.') {
					tool.setToolReportExtension(txtfReportExtension.getText().substring(1));
				}
				tool.setToolScanType(txtfScanType.getText());
				tool.setToolSource(sourceType);
				tool.setToolType(toolType);
				tool.setScanScript(scanScript);
				toolList.add(tool);
				ConfigUtils.saveTools("config/tools.yaml", toolList);
				frame.dispose();
				new ToolWindow();
			}
		});
		
		JButton btnClear = new JButton("Clear");
		
		JButton btnHelp = new JButton("?");
		btnHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpWindow();
			}
		});
		
		
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
					System.out.println(selectedFile.getAbsolutePath());
				}
			}
		});

		JPanel commandPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		JScrollPane commandScrollPane = new JScrollPane(commandPanel);

		commandPane = new JTextPane();
		commandPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		commandPanel.add(commandPane);

		// Set all layouts
		GroupLayout gl_newToolsPanel = new GroupLayout(newToolsPanel);
		gl_newToolsPanel.setHorizontalGroup(
			gl_newToolsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newToolsPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(rdbtnDocker)
							.addGap(47)
							.addComponent(rdbtnGit)
							.addGap(47)
							.addComponent(rdbtnLocalFiles)
							.addGap(865))
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(separator, GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE)
								.addComponent(lblNewTools)
								.addGroup(gl_newToolsPanel.createSequentialGroup()
									.addComponent(lblRespository)
									.addGap(18)
									.addComponent(txtfRespository, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnBrowseRespository, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_newToolsPanel.createSequentialGroup()
									.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(txtfName, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)))
							.addGap(31))
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(btnAddTool, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(lblReportExtension)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtfReportExtension, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(lblScanType, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtfScanType, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(commandScrollPane, GroupLayout.PREFERRED_SIZE, 1071, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(83, Short.MAX_VALUE))
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_newToolsPanel.createSequentialGroup()
									.addComponent(lblCommand)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnHelp))
								.addGroup(Alignment.LEADING, gl_newToolsPanel.createSequentialGroup()
									.addComponent(lblType)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboboxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(989))))
		);
		gl_newToolsPanel.setVerticalGroup(
			gl_newToolsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_newToolsPanel.createSequentialGroup()
					.addGap(21)
					.addComponent(lblNewTools)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(rdbtnDocker)
									.addComponent(rdbtnGit))
								.addComponent(rdbtnLocalFiles))
							.addGap(7)
							.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblRespository)
								.addComponent(txtfRespository, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnBrowseRespository, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(txtfName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(14))
						.addGroup(gl_newToolsPanel.createSequentialGroup()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblReportExtension)
						.addComponent(txtfReportExtension, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtfScanType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblScanType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboboxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCommand)
						.addComponent(btnHelp))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(commandScrollPane, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_newToolsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddTool, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
					.addGap(31))
		);
		newToolsPanel.setLayout(gl_newToolsPanel);

		// Adjust the overall layout of this window
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(newToolsPanel, GroupLayout.DEFAULT_SIZE, 1201, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(newToolsPanel, GroupLayout.PREFERRED_SIZE, 858, Short.MAX_VALUE)
						.addContainerGap()));
		panel.setLayout(groupLayout);

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
