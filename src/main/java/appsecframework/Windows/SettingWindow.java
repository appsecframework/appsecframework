package appsecframework.Windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import appsecframework.FrameworkConfig;
import appsecframework.MainController;
import appsecframework.Utilities.SwingUtils;

public class SettingWindow {

	private JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel headingPanel;
	private JPanel settingPanel;
	private JPanel dockerPanel;
	private JPanel gitPanel;
	private JPanel jenkinsPanel;
	private JPanel defectdojoPanel;
	private JTextField txtfDockerUsername;
	private JPasswordField txtfDockerPassword;
	private JTextField txtfDockerSocket;
	private JTextField txtfJenkinsUsername;
	private JPasswordField txtfJenkinsPassword;
	private JTextField txtfJenkinsURL;
	private JTextField txtfGitUsername;
	private JPasswordField txtfGitPassword;
	private JTextField txtfDefectdojoUsername;
	private JPasswordField txtfDefectdojoPassword;
	private JTextField txtfDefectdojoURL;
	private FrameworkConfig frameworkConfig;
	private JCheckBox dockerChecker;

	public SettingWindow() {
		initialize();
		frame.setVisible(true);
	}

	private void actionPerformed(java.awt.event.ActionEvent evt, JCheckBox checker, JPasswordField password) {
		if (checker.isSelected()) {
			password.setEchoChar((char) 0); // password = JPasswordField
		} else {
			password.setEchoChar('*');
		}
	}

	private void initialize() {
		frameworkConfig = MainController.getFrameworkConfig();
		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Setting");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel(frame);

		headingPanel = new JPanel();
		JLabel lblSetting = new JLabel("Setting");
		lblSetting.setFont(new Font("Tahoma", Font.PLAIN, 24));

		JSeparator headingSeparator = new JSeparator();
		headingSeparator.setBackground(Color.LIGHT_GRAY);

		settingPanel = new JPanel(new GridLayout(4, 0));
		dockerPanel = new JPanel();
		gitPanel = new JPanel();
		jenkinsPanel = new JPanel();
		defectdojoPanel = new JPanel();

		// Docker
		settingPanel.add(dockerPanel);
		JLabel lblDocker = new JLabel("Docker");
		lblDocker.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblDockerUsername = new JLabel("Username:");
		lblDockerUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDockerUsername = new JTextField();
		// txtfDockerUsername.setText(txtfDockerUsername.getText() +
		// frameworkConfig.getDockerUsername());
		txtfDockerUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDockerUsername.setColumns(10);

		JLabel lblDockerPassword = new JLabel("Password: ");
		lblDockerPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDockerPassword = new JPasswordField();
		// txtfDockerPassword.setText(txtfDockerPassword.getText() +
		// frameworkConfig.getDockerPassword());
		txtfDockerPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDockerPassword.setColumns(10);

		// dockerChecker = new JCheckBox("Show Password");
		// actionPerformed( e, dockerChecker, txtfDockerPassword);

		JLabel lblDockerSocket = new JLabel("Socket: ");
		lblDockerSocket.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDockerSocket = new JTextField();
		txtfDockerSocket.setText(txtfDockerSocket.getText() + frameworkConfig.getDockerSocket());
		txtfDockerSocket.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDockerSocket.setColumns(10);

		// Git
		settingPanel.add(gitPanel);
		JLabel lblGit = new JLabel("Git");
		lblGit.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblGitUsername = new JLabel("Username:");
		lblGitUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfGitUsername = new JTextField();
		// txtfGitUsername.setText(txtfGitUsername.getText() +
		// frameworkConfig.getGitUsername());
		txtfGitUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfGitUsername.setColumns(10);

		JLabel lblGitPassword = new JLabel("Password: ");
		lblGitPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfGitPassword = new JPasswordField();
		// txtfGitPassword.setText(txtfGitPassword.getText() +
		// frameworkConfig.getGitPassword());
		txtfGitPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfGitPassword.setColumns(10);

		// Jenkins
		settingPanel.add(jenkinsPanel);
		JLabel lblJenkins = new JLabel("Jenkins");
		lblJenkins.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGit.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblJenkinsUsername = new JLabel("Username:");
		lblJenkinsUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGitUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfJenkinsUsername = new JTextField();
		txtfJenkinsUsername.setText(txtfJenkinsUsername.getText() + frameworkConfig.getJenkinsUsername());
		txtfJenkinsUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfJenkinsUsername.setColumns(10);

		JLabel lblJenkinsPassword = new JLabel("Password: ");
		lblJenkinsPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfJenkinsPassword = new JPasswordField();
		txtfJenkinsPassword.setText(txtfJenkinsPassword.getText() + frameworkConfig.getJenkinsPassword());
		txtfJenkinsPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfJenkinsPassword.setColumns(10);

		JLabel lblJenkinsURL = new JLabel("URL: ");
		lblJenkinsURL.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfJenkinsURL = new JTextField();
		txtfJenkinsURL.setText(txtfJenkinsURL.getText() + frameworkConfig.getJenkinsURL());
		txtfJenkinsURL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfJenkinsURL.setColumns(10);

		// Defect Dojo
		settingPanel.add(defectdojoPanel);

		JLabel lblDefectdojo = new JLabel("DefectDojo");
		lblDefectdojo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblDefectdojoUsername = new JLabel("Username:");
		lblDefectdojoUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDefectdojoUsername = new JTextField();
		txtfDefectdojoUsername.setText(frameworkConfig.getDojoUsername());
		txtfDefectdojoUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDefectdojoUsername.setColumns(10);

		JLabel lblDefectdojoPassword = new JLabel("Password: ");
		lblDefectdojoPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDefectdojoPassword = new JPasswordField();
		txtfDefectdojoPassword.setText(frameworkConfig.getDojoPassword());
		txtfDefectdojoPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDefectdojoPassword.setColumns(10);

		JLabel lblDefectDojoURL = new JLabel("URL: ");
		lblDefectDojoURL.setFont(new Font("Tahoma", Font.PLAIN, 16));

		txtfDefectdojoURL = new JTextField();
		txtfDefectdojoURL.setText(frameworkConfig.getDojoURL());
		txtfDefectdojoURL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtfDefectdojoURL.setColumns(10);

		// Set all layouts
		GroupLayout gl_headingPanel = new GroupLayout(headingPanel);
		gl_headingPanel
				.setHorizontalGroup(
						gl_headingPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_headingPanel.createSequentialGroup().addContainerGap()
												.addGroup(gl_headingPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(headingSeparator, GroupLayout.DEFAULT_SIZE, 1063,
																Short.MAX_VALUE)
														.addComponent(lblSetting))
												.addContainerGap()));
		gl_headingPanel.setVerticalGroup(gl_headingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_headingPanel.createSequentialGroup().addGap(33).addComponent(lblSetting)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(headingSeparator,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(27, Short.MAX_VALUE)));
		headingPanel.setLayout(gl_headingPanel);

		GroupLayout gl_dockerPanel = new GroupLayout(dockerPanel);
		gl_dockerPanel.setHorizontalGroup(gl_dockerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dockerPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_dockerPanel.createParallelGroup(Alignment.LEADING, false).addComponent(lblDocker)
								.addGroup(gl_dockerPanel.createSequentialGroup().addComponent(lblDockerUsername)
										.addGap(18).addComponent(txtfDockerUsername, GroupLayout.PREFERRED_SIZE, 148,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_dockerPanel.createSequentialGroup()
										.addGroup(gl_dockerPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDockerPassword).addComponent(lblDockerSocket))
										.addGap(18)
										.addGroup(gl_dockerPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(txtfDockerSocket, GroupLayout.DEFAULT_SIZE, 138,
														Short.MAX_VALUE)
												.addComponent(txtfDockerPassword))))
						.addContainerGap(830, Short.MAX_VALUE)));
		gl_dockerPanel.setVerticalGroup(gl_dockerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dockerPanel.createSequentialGroup().addContainerGap().addComponent(lblDocker).addGap(18)
						.addGroup(gl_dockerPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblDockerUsername)
								.addComponent(txtfDockerUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_dockerPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblDockerPassword)
								.addComponent(txtfDockerPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_dockerPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblDockerSocket)
								.addComponent(txtfDockerSocket, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(89, Short.MAX_VALUE)));
		dockerPanel.setLayout(gl_dockerPanel);

		GroupLayout gl_gitPanel = new GroupLayout(gitPanel);
		gl_gitPanel.setHorizontalGroup(gl_gitPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gitPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_gitPanel.createParallelGroup(Alignment.LEADING, false).addComponent(lblGit)
								.addGroup(gl_gitPanel.createSequentialGroup().addComponent(lblGitUsername).addGap(18)
										.addComponent(txtfGitUsername, GroupLayout.PREFERRED_SIZE, 148,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_gitPanel.createSequentialGroup().addComponent(lblGitPassword).addGap(18)
										.addComponent(txtfGitPassword)))
						.addContainerGap(830, Short.MAX_VALUE)));
		gl_gitPanel.setVerticalGroup(gl_gitPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_gitPanel
				.createSequentialGroup().addContainerGap().addComponent(lblGit).addGap(18)
				.addGroup(gl_gitPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblGitUsername).addComponent(
						txtfGitUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_gitPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblGitPassword).addComponent(
						txtfGitPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addContainerGap(69, Short.MAX_VALUE)));
		gitPanel.setLayout(gl_gitPanel);

		GroupLayout gl_JenkinsPanel = new GroupLayout(jenkinsPanel);
		gl_JenkinsPanel.setHorizontalGroup(gl_JenkinsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_JenkinsPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.LEADING, false).addComponent(lblJenkins)
								.addGroup(gl_JenkinsPanel.createSequentialGroup().addComponent(lblJenkinsUsername)
										.addGap(18).addComponent(txtfJenkinsUsername, GroupLayout.PREFERRED_SIZE, 148,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_JenkinsPanel.createSequentialGroup()
										.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblJenkinsPassword).addComponent(lblJenkinsURL))
										.addGap(18)
										.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(txtfJenkinsURL, GroupLayout.DEFAULT_SIZE, 138,
														Short.MAX_VALUE)
												.addComponent(txtfJenkinsPassword))))
						.addContainerGap(830, Short.MAX_VALUE)));
		gl_JenkinsPanel.setVerticalGroup(gl_JenkinsPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_JenkinsPanel
				.createSequentialGroup().addContainerGap().addComponent(lblJenkins).addGap(18)
				.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblJenkinsUsername)
						.addComponent(txtfJenkinsUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblJenkinsPassword)
						.addComponent(txtfJenkinsPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_JenkinsPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblJenkinsURL)
						.addComponent(txtfJenkinsURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addContainerGap(89, Short.MAX_VALUE)));
		jenkinsPanel.setLayout(gl_JenkinsPanel);

		GroupLayout gl_defectdojoPanel = new GroupLayout(defectdojoPanel);
		gl_defectdojoPanel.setHorizontalGroup(gl_defectdojoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_defectdojoPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_defectdojoPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblDefectdojo)
								.addGroup(gl_defectdojoPanel.createSequentialGroup().addComponent(lblDefectdojoUsername)
										.addGap(18).addComponent(txtfDefectdojoUsername, GroupLayout.PREFERRED_SIZE,
												148, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_defectdojoPanel.createSequentialGroup().addComponent(lblDefectdojoPassword)
										.addGap(18).addComponent(txtfDefectdojoPassword))
								.addGroup(gl_defectdojoPanel.createSequentialGroup().addComponent(lblDefectDojoURL)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(txtfDefectdojoURL, GroupLayout.PREFERRED_SIZE, 147,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(830, Short.MAX_VALUE)));
		gl_defectdojoPanel
				.setVerticalGroup(
						gl_defectdojoPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_defectdojoPanel.createSequentialGroup().addContainerGap()
										.addComponent(lblDefectdojo).addGap(18)
										.addGroup(gl_defectdojoPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblDefectdojoUsername)
												.addComponent(txtfDefectdojoUsername, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_defectdojoPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblDefectdojoPassword)
												.addComponent(txtfDefectdojoPassword, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_defectdojoPanel.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblDefectDojoURL).addComponent(txtfDefectdojoURL,
														GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(37, Short.MAX_VALUE)));
		defectdojoPanel.setLayout(gl_defectdojoPanel);

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(settingPanel, GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE).addGap(10))
						.addGroup(
								groupLayout.createSequentialGroup().addComponent(headingPanel, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(headingPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(settingPanel, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE).addGap(0)));

		panel.setLayout(groupLayout);
	}
}
