package appsecframework.Windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import appsecframework.CustomLabelClickListener;
import appsecframework.DojoFinding;
import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.Utilities.DojoUtils;
import appsecframework.Utilities.SwingUtils;

public class DashboardWindow {

	private JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel dashboardPanel;
	private JPanel findingPanel;
	private JPanel summaryPanel;
	private JLabel lblRiskLevel;
	private JLabel lblHigh;
	private JLabel lblMedium;
	private JLabel lblLow;
	private JLabel lblStartTime;
	private JLabel lblFinishTime;
	private JLabel lblScanDuration;
	private JLabel lblScanStatus;
	private JTextField txtfProject;
	private JTextField txtfFinding;
	private JTextField txtfThreat;
	private JTextField txtfFix;

	private static int size;
	private static ArrayList<Project> projectList;
	private static DojoUtils dojo;
	private static String findingSource;
	private static Pattern pattern;
	static Matcher match;

	public DashboardWindow() {
		projectList = MainController.getProjectList();
		dojo = MainController.getDojo();
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {

		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Dashboard");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel();

		txtfProject = new JTextField("-");
		txtfProject.setHorizontalAlignment(JTextField.CENTER);
		txtfProject.setBackground(Color.WHITE);
		txtfProject.setEditable(false);
		txtfProject.setFocusable(false);

		txtfFinding = new JTextField("-");
		txtfFinding.setHorizontalAlignment(JTextField.CENTER);
		txtfFinding.setBackground(Color.WHITE);
		txtfFinding.setEditable(false);
		txtfFinding.setFocusable(false);

		txtfThreat = new JTextField("-");
		txtfThreat.setHorizontalAlignment(JTextField.CENTER);
		txtfThreat.setBackground(Color.WHITE);
		txtfThreat.setEditable(false);
		txtfThreat.setFocusable(false);

		txtfFix = new JTextField("-");
		txtfFix.setHorizontalAlignment(JTextField.CENTER);
		txtfFix.setBackground(Color.WHITE);
		txtfFix.setEditable(false);
		txtfFix.setFocusable(false);

		lblHigh = new JLabel("-");
		lblHigh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMedium = new JLabel("-");
		lblMedium.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLow = new JLabel("-");
		lblLow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JPanel highRiskFound = new JPanel();
		
		if (projectList.size() > 0) {
			List<DojoFinding> findings = dojo.getDojoFindingList(projectList.get(projectList.size() - 1).getName());
			List<DojoFinding> highrisk = dojo.getHighRisk(findings);
			List<DojoFinding> mediumrisk = dojo.getMediumRisk(findings);
			List<DojoFinding> lowrisk = dojo.getLowRisk(findings);
			size = highrisk.size() > 20 ? 20 : highrisk.size();

			txtfProject.setText(projectList.get(projectList.size() - 1).getName());
			txtfFinding.setText(String.valueOf(findings.size()));
			txtfThreat.setText("" + highrisk.size());
			txtfFix.setText("0");

			lblHigh.setText(String.valueOf(highrisk.size()));
			lblMedium.setText(String.valueOf(mediumrisk.size()));
			lblLow.setText(String.valueOf(lowrisk.size()));
			
			highRiskFound = getHighRiskPanel(findings, highrisk); // TODO: to be edited
		}

		// Detail for this window goes here
		dashboardPanel = new JPanel();
		JButton dashboardProject = new JButton("Project");
		dashboardProject.setIcon(new ImageIcon(getClass().getResource("/images/projectBanner.png")));
		dashboardProject.setFocusable(false);
		JButton dashboardFinding = new JButton("Finding");
		dashboardFinding.setIcon(new ImageIcon(getClass().getResource("/images/findingBanner.png")));
		dashboardFinding.setFocusable(false);
		JButton dashboardThreat = new JButton("Threat");
		dashboardThreat.setIcon(new ImageIcon(getClass().getResource("/images/threat.png")));
		dashboardThreat.setFocusable(false);
		JButton dashboardFix = new JButton("Fix");
		dashboardFix.setIcon(new ImageIcon(getClass().getResource("/images/fix.png")));
		dashboardFix.setFocusable(false);

		// Fetch Recent Project

		summaryPanel = new JPanel();
		JLabel sumLblSummary = new JLabel("Summary ");
		sumLblSummary.setFont(new Font("Tahoma", Font.BOLD, 18));

		JSeparator sumSeparator = new JSeparator();

		JLabel sumLblRiskLevel = new JLabel("Overall Risk Level ");
		sumLblRiskLevel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblRiskRating = new JLabel("Risk Rating ");
		sumLblRiskRating.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblScanInfo = new JLabel("Scan Information ");
		sumLblScanInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblStartTime = new JLabel("Start time: ");
		sumLblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblFinishTime = new JLabel("Finish time: ");
		sumLblFinishTime.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblScanDuration = new JLabel("Scan duration: ");
		sumLblScanDuration.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblScanStatus = new JLabel("Scan Status: ");
		sumLblScanStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel sumLblHigh = new JLabel("High: ");
		sumLblHigh.setFont(new Font("Tahoma", Font.PLAIN, 16));
		;
		sumLblHigh.setOpaque(true);

		JLabel sumLblMeidum = new JLabel("Medium: ");
		sumLblMeidum.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLblMeidum.setOpaque(true);

		JLabel sumLblLow = new JLabel("Low: ");
		sumLblLow.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLblLow.setOpaque(true);

		// Fetch Summary for Recent Project
		lblRiskLevel = new JLabel();
		lblRiskLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRiskLevel.setText("High");

		lblStartTime = new JLabel();
		lblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStartTime.setText("14-2-2019");

		lblFinishTime = new JLabel();
		lblFinishTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFinishTime.setText("14-2-2019");

		lblScanDuration = new JLabel();
		lblScanDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblScanDuration.setText("01:13:06");

		lblScanStatus = new JLabel();
		lblScanStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblScanStatus.setText("SUCCESS");

		findingPanel = new JPanel();
		JLabel findLblFinding = new JLabel("Finding");
		findLblFinding.setFont(new Font("Tahoma", Font.BOLD, 18));
		JSeparator findSeparator = new JSeparator();

		JLabel findLblHighRiskFound = new JLabel("Potentially High Risk Found");
		findLblHighRiskFound.setIcon(new ImageIcon(getClass().getResource("/images/flag.png")));
		findLblHighRiskFound.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JPanel foundPanel = new JPanel();
		foundPanel.setLayout(new GridLayout(1, 0, 0, 0));
		foundPanel.add(highRiskFound);

		// Set all layouts
		GroupLayout gl_dashboardPanel = new GroupLayout(dashboardPanel);
		gl_dashboardPanel.setHorizontalGroup(gl_dashboardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dashboardPanel.createSequentialGroup().addGap(50)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardProject, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfProject, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
						.addGap(70)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardFinding, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfFinding, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
						.addGap(70)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardThreat, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfThreat, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
						.addGap(70)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardFix, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfFix, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
						.addGap(50)));
		gl_dashboardPanel.setVerticalGroup(gl_dashboardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dashboardPanel.createSequentialGroup().addGap(13).addGroup(gl_dashboardPanel
						.createParallelGroup(Alignment.BASELINE)
						.addComponent(dashboardFix, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardThreat, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardFinding, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardProject, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGap(0)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.LEADING, false).addComponent(txtfFix)
								.addComponent(txtfThreat).addComponent(txtfFinding)
								.addComponent(txtfProject, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
						.addContainerGap(30, Short.MAX_VALUE)));
		dashboardPanel.setLayout(gl_dashboardPanel);

		GroupLayout gl_summaryPanel = new GroupLayout(summaryPanel);
		gl_summaryPanel
				.setHorizontalGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_summaryPanel.createSequentialGroup().addContainerGap()
								.addGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(sumSeparator, GroupLayout.PREFERRED_SIZE, 1016,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(sumLblSummary)
										.addGroup(gl_summaryPanel
												.createSequentialGroup().addGroup(gl_summaryPanel
														.createParallelGroup(Alignment.LEADING).addComponent(
																sumLblRiskLevel)
														.addComponent(
																lblRiskLevel, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(110)
												.addGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
														.addComponent(sumLblRiskRating)
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblHigh).addGap(18)
																.addComponent(lblHigh, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblMeidum).addGap(18)
																.addComponent(lblMedium, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblLow)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(
																		lblLow, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
												.addGap(125)
												.addGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblScanDuration)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(
																		lblScanDuration, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblFinishTime)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblFinishTime, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblStartTime)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblStartTime, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(sumLblScanInfo)
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblScanStatus)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblScanStatus, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))))
								.addContainerGap(67, Short.MAX_VALUE)));
		gl_summaryPanel.setVerticalGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_summaryPanel.createSequentialGroup().addContainerGap().addComponent(sumLblSummary)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(sumSeparator, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE).addComponent(sumLblRiskLevel)
								.addComponent(sumLblRiskRating, GroupLayout.PREFERRED_SIZE, 20,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(sumLblScanInfo))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_summaryPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblRiskLevel, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(sumLblHigh)
										.addComponent(lblHigh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(sumLblStartTime).addComponent(
												lblStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_summaryPanel
								.createSequentialGroup()
								.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(sumLblMeidum).addComponent(lblMedium, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(sumLblLow)
										.addComponent(lblLow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(sumLblScanDuration).addComponent(lblScanDuration,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(sumLblFinishTime).addComponent(lblFinishTime,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE).addComponent(sumLblScanStatus)
								.addComponent(lblScanStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(30, Short.MAX_VALUE)));
		summaryPanel.setLayout(gl_summaryPanel);

		GroupLayout gl_findingPanel = new GroupLayout(findingPanel);
		gl_findingPanel.setHorizontalGroup(gl_findingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_findingPanel.createSequentialGroup()
						.addGroup(gl_findingPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_findingPanel.createSequentialGroup().addContainerGap()
										.addGroup(gl_findingPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(findSeparator, GroupLayout.PREFERRED_SIZE, 1019,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(findLblFinding).addComponent(findLblHighRiskFound)))
								.addComponent(foundPanel, GroupLayout.PREFERRED_SIZE, 1057, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(18, Short.MAX_VALUE)));
		gl_findingPanel.setVerticalGroup(gl_findingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_findingPanel.createSequentialGroup().addContainerGap().addComponent(findLblFinding)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(findSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(findLblHighRiskFound)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(foundPanel, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)));
		findingPanel.setLayout(gl_findingPanel);

		// Create containers for both summaryPanel and findingPanel
		Container cont = new Container();
		GroupLayout gl_scrollPane = new GroupLayout(cont);
		gl_scrollPane.setHorizontalGroup(gl_scrollPane.createParallelGroup(Alignment.LEADING).addGroup(gl_scrollPane
				.createSequentialGroup()
				.addGroup(gl_scrollPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(findingPanel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(summaryPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE))
				.addContainerGap(26, Short.MAX_VALUE)));
		gl_scrollPane.setVerticalGroup(gl_scrollPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_scrollPane.createSequentialGroup()
						.addComponent(summaryPanel, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(findingPanel,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		cont.setLayout(gl_scrollPane);

		// Put Container into scrollPane
		JScrollPane contScrollPane = new JScrollPane(cont);
		contScrollPane.setBorder(BorderFactory.createEmptyBorder());
		contScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Color myColor = Color.decode("#f0f0f0");
		contScrollPane.getViewport().setBackground(myColor);
		contScrollPane.setLocation(0, 0);

		// Set GroupLayout for Frame
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE).addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(dashboardPanel, 0, 0, Short.MAX_VALUE)
								.addComponent(contScrollPane, GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE))
						.addGap(19)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 861, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(dashboardPanel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(contScrollPane, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)));
		panel.setLayout(groupLayout);

	}

	public static JPanel getHighRiskPanel(List<DojoFinding> findings, List<DojoFinding> highrisk) {
		ArrayList<JPanel> panelList = new ArrayList<JPanel>();
		String text = "more";
		for (int i = 0; i < size; i++) { // i < severity
			JButton button = new JButton(text);
			button.setForeground(Color.BLUE.darker());
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			findingSource = highrisk.get(i).getUrl();
			pattern = Pattern.compile(".+\\/([0-9]+)\\/");
			if (findingSource == null) continue;
			match = pattern.matcher(findingSource);
			String URL = null;
			if (match.find()) {
				URL = "http://localhost:8000/finding/" + match.group(1);
			}

			button.addMouseListener(new CustomLabelClickListener(URL));

			JPanel tempPanel = new JPanel();
			String trimmedDesc = highrisk.get(i).getDescription().replaceAll("\n", " ").replaceAll("\r", " ");
			if (trimmedDesc.length() > 500) {
				trimmedDesc = trimmedDesc.substring(0, 500) + "...";
			}
			// tempPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
			tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 10, 10));
			JLabel lblRiskName = new JLabel("Name: ");
			lblRiskName.setFont(new Font("Tahoma", Font.PLAIN, 16));
			JLabel lblRiskLocation = new JLabel("Location: ");
			lblRiskLocation.setFont(new Font("Tahoma", Font.PLAIN, 16));
			JLabel lblRiskDescription = new JLabel("Description: ");
			lblRiskDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));

			JLabel riskName = new JLabel(highrisk.get(i).getTitle());
			riskName.setFont(new Font("Tahoma", Font.PLAIN, 15));

			JLabel location = new JLabel(highrisk.get(i).getFinding_url());
			location.setFont(new Font("Tahoma", Font.PLAIN, 15));

			JTextArea description = new JTextArea(1, 200);
			description.setText(trimmedDesc);
			description.setLineWrap(true);
			description.setWrapStyleWord(true);

			GroupLayout groupLayout = new GroupLayout(tempPanel);
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
					.createSequentialGroup().addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblRiskName).addComponent(lblRiskLocation))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(location)
											.addComponent(riskName).addComponent(button)))
							.addGroup(groupLayout.createSequentialGroup().addComponent(lblRiskDescription)
									.addPreferredGap(ComponentPlacement.RELATED).addComponent(description)))
					.addContainerGap(937, Short.MAX_VALUE)));
			groupLayout
					.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup().addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblRiskName).addComponent(riskName))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblRiskLocation).addComponent(location))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblRiskDescription).addComponent(description))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(button))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addContainerGap(304, Short.MAX_VALUE)));
			tempPanel.setLayout(groupLayout);
			panelList.add(tempPanel);
		}
		JPanel panel = new JPanel(new GridLayout(panelList.size(), 0, 0, 0));
		for (JPanel tempPanel : panelList) {
			panel.add(tempPanel);
		}
		return panel;
	}

}
