package appsecframework.Windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
	private JLabel lblInfo;
	private JLabel lblStartTime;
	private JLabel lblFinishTime;
	private JLabel lblScanDuration;
	private JLabel lblScanStatus;
	private JTextField txtfProject;
	private JTextField txtfLastScan;
	private JTextField txtfFinding;
	private JTextField txtfHighRisk;

	private static ArrayList<Project> projectList;
	private static DojoUtils dojo;
	private static String findingSource;
	private static Pattern pattern;
	static Matcher match;

	public DashboardWindow() {
		projectList = MainController.getProjectList();
		dojo = MainController.getDojo();

		if (!MainController.isDbFetched()) {
			initialize();
		} else {
			frame = MainController.getDbFrame();
		}
		frame.setVisible(true);
	}

	private void initialize() {

		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Dashboard");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel(frame);

		txtfProject = new JTextField("-");
		txtfProject.setHorizontalAlignment(JTextField.CENTER);
		txtfProject.setBackground(Color.WHITE);
		txtfProject.setEditable(false);
		txtfProject.setFocusable(false);

		txtfLastScan = new JTextField("-");
		txtfLastScan.setHorizontalAlignment(JTextField.CENTER);
		txtfLastScan.setBackground(Color.WHITE);
		txtfLastScan.setEditable(false);
		txtfLastScan.setFocusable(false);

		txtfFinding = new JTextField("-");
		txtfFinding.setHorizontalAlignment(JTextField.CENTER);
		txtfFinding.setBackground(Color.WHITE);
		txtfFinding.setEditable(false);
		txtfFinding.setFocusable(false);

		txtfHighRisk = new JTextField("-");
		txtfHighRisk.setHorizontalAlignment(JTextField.CENTER);
		txtfHighRisk.setBackground(Color.WHITE);
		txtfHighRisk.setEditable(false);
		txtfHighRisk.setFocusable(false);

		lblHigh = new JLabel();
		lblHigh.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMedium = new JLabel();
		lblMedium.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLow = new JLabel();
		lblLow.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInfo = new JLabel();
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));

		// Fetch Summary for Recent Project
		lblRiskLevel = new JLabel("-");
		lblRiskLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStartTime = new JLabel("-");
		lblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFinishTime = new JLabel("-");
		lblFinishTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblScanDuration = new JLabel("-");
		lblScanDuration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblScanStatus = new JLabel("-");
		lblScanStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JPanel highRiskFound = new JPanel();

		if (projectList.size() != 0) {
			Project project = projectList.get(projectList.size() - 1);
			for (Project p : projectList) {
				if (p.isRecent()) {
					project = p;
				}
			}
			int lastInd = project.getScanEventList().size() - 1;

			if (project.getScanEventList().size() != 0 && !project.getScanEventList().get(lastInd).isImported()) {
				List<DojoFinding> findings = dojo.getDojoFindingList(project.getName());
				List<DojoFinding> highrisk = DojoUtils.getHighRisk(findings);
				List<DojoFinding> mediumrisk = DojoUtils.getMediumRisk(findings);
				List<DojoFinding> lowrisk = DojoUtils.getLowRisk(findings);
				List<DojoFinding> inforisk = DojoUtils.getInfoRisk(findings);
				int max = Math.max(Math.max(highrisk.size(), mediumrisk.size()), lowrisk.size());
				if (max == highrisk.size()) {
					lblRiskLevel.setText("High");
				} else if (max == mediumrisk.size()) {
					lblRiskLevel.setText("Medium");
				} else {
					lblRiskLevel.setText("Low");
				}
				lblStartTime.setText(project.getStartScanDate());
				lblFinishTime.setText(project.getLastScanDate());
				SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy, HH:mm:ss");
				formatter.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
				try {
					Date startDate = formatter.parse(project.getStartScanDate());
					Date stopDate = formatter.parse(project.getLastScanDate());
					int hour = Math.abs(stopDate.getHours() - startDate.getHours());
					int min = Math.abs(stopDate.getMinutes() - startDate.getMinutes());
					int sec = Math.abs(stopDate.getSeconds() - startDate.getSeconds());

					lblScanDuration.setText(hour + ":" + min + ":" + sec);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				lblScanStatus.setText(project.getScanStatus());

				txtfProject.setText(project.getName());
				txtfLastScan.setText(project.getLastScanDate().split(",")[0]);
				txtfFinding.setText(String.valueOf(findings.size()));
				txtfHighRisk.setText("" + highrisk.size());

				lblHigh.setText(String.valueOf(highrisk.size()));
				lblMedium.setText(String.valueOf(mediumrisk.size()));
				lblLow.setText(String.valueOf(lowrisk.size()));
				lblInfo.setText(String.valueOf(inforisk.size()));
				highRiskFound = getHighRiskPanel(findings, highrisk);
				project.getScanEventList().get(lastInd).setImported(true);
				MainController.setDbFetched(true);
			}
		}

		// Detail for this window goes here
		dashboardPanel = new JPanel();
		JButton dashboardProject = new JButton("Project");
		dashboardProject.setIcon(new ImageIcon(getClass().getResource("/images/projectBanner.png")));
		dashboardProject.setFocusable(false);
		JButton dashboardLastScan = new JButton("Last Scan");
		dashboardLastScan.setIcon(new ImageIcon(getClass().getResource("/images/scheduleBanner.png")));
		dashboardLastScan.setFocusable(false);
		JButton dashboardFinding = new JButton("Finding");
		dashboardFinding.setIcon(new ImageIcon(getClass().getResource("/images/findingBanner.png")));
		dashboardFinding.setFocusable(false);
		JButton dashboardHighRisk = new JButton("High Risk");
		dashboardHighRisk.setIcon(new ImageIcon(getClass().getResource("/images/threatBanner.png")));
		dashboardHighRisk.setFocusable(false);

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
		sumLblHigh.setOpaque(true);

		JLabel sumLblMedium = new JLabel("Medium: ");
		sumLblMedium.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLblMedium.setOpaque(true);

		JLabel sumLblLow = new JLabel("Low: ");
		sumLblLow.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLblLow.setOpaque(true);
		
		JLabel sumLblInfo = new JLabel("Info: ");
		sumLblInfo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLblInfo.setOpaque(true);

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
								.addComponent(dashboardLastScan, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfLastScan, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE))
						.addGap(70)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardFinding, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfFinding, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
						.addGap(70)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(dashboardHighRisk, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(txtfHighRisk, GroupLayout.PREFERRED_SIZE, 200,
										GroupLayout.PREFERRED_SIZE))
						.addGap(50)));
		gl_dashboardPanel.setVerticalGroup(gl_dashboardPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dashboardPanel.createSequentialGroup().addGap(13).addGroup(gl_dashboardPanel
						.createParallelGroup(Alignment.BASELINE)
						.addComponent(dashboardHighRisk, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardFinding, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardLastScan, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(dashboardProject, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGap(0)
						.addGroup(gl_dashboardPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtfHighRisk).addComponent(txtfFinding).addComponent(txtfLastScan)
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
																.addComponent(sumLblMedium).addGap(18)
																.addComponent(lblMedium, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblLow).addGap(18)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(
																		lblLow, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(gl_summaryPanel.createSequentialGroup()
																.addComponent(sumLblInfo)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(
																		lblInfo, GroupLayout.PREFERRED_SIZE,
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
										.addComponent(sumLblMedium).addComponent(lblMedium, GroupLayout.PREFERRED_SIZE,
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
								.addComponent(sumLblInfo)
								.addComponent(lblInfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
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
		MainController.setDbFrame(frame);
	}

	public static JPanel getHighRiskPanel(List<DojoFinding> findings, List<DojoFinding> highrisk) {
		ArrayList<JPanel> panelList = new ArrayList<JPanel>();
		int size = highrisk.size() > 20 ? 20 : highrisk.size();
		String text = "more";

		for (int i = 0; i < size; i++) { // i < severity
			JButton button = new JButton(text);
			button.setForeground(Color.BLUE.darker());
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			String URL = "http://localhost:8000/finding/" + highrisk.get(i).getId();

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

			JLabel location = new JLabel(highrisk.get(i).getUrl());
			location.setFont(new Font("Tahoma", Font.PLAIN, 15));

			JTextArea description = new JTextArea(1, 200);
			description.setText(trimmedDesc);
			description.setLineWrap(true);
			description.setWrapStyleWord(true);
			description.setEditable(false);

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
		JPanel panel = new JPanel(new GridLayout(panelList.size() > 0 ? panelList.size() : 1, 0, 0, 0));
		for (JPanel tempPanel : panelList) {
			panel.add(tempPanel);
		}
		return panel;
	}

}
