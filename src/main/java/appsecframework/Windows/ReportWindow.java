package appsecframework.Windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import appsecframework.Utilities.SwingUtils;

import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;

public class ReportWindow {

	private JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel categoriesPanel;
	private JPanel exploitListPanel;
	private JPanel summaryPanel;

	public ReportWindow() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		// *************************************** DELETE this section
		// ************************************
		panel = new JPanel(); // panel for all components

		frame = new JFrame("Report");
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(0, 0, 1300, 900);
		frame.setMinimumSize(new Dimension(1300, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);

		menuPanel = new JPanel();

		// menuPanel's Components and add them to the panel
		// Dashboard
		JButton menuBtnDashboard = new JButton("Dashboard");
		menuBtnDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new DashboardWindow();
			}
		});
		menuBtnDashboard.setIcon(new ImageIcon(SwingUtils.class.getResource("/images/dashboard.png")));
		menuBtnDashboard.setFocusPainted(false);
		menuBtnDashboard.setFocusable(false);
		menuPanel.add(menuBtnDashboard);

		// Project
		JButton menuBtnProject = new JButton("Project");
		menuBtnProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ProjectWindow();
			}
		});
		menuBtnProject.setIcon(new ImageIcon(SwingUtils.class.getResource("/images/project.png")));
		menuBtnProject.setFocusPainted(false);
		menuBtnProject.setFocusable(false);
		menuPanel.add(menuBtnProject);

		// Report
		JButton menuBtnReport = new JButton("Report");
		menuBtnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ReportWindow();
			}
		});
		menuBtnReport.setIcon(new ImageIcon(SwingUtils.class.getResource("/images/report.png")));
		menuBtnReport.setFocusPainted(false);
		menuBtnReport.setFocusable(false);
		menuPanel.add(menuBtnReport);

		// Tools
		JButton menuBtnTools = new JButton("Tool");
		menuBtnTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ToolWindow();
			}
		});
		menuBtnTools.setIcon(new ImageIcon(SwingUtils.class.getResource("/images/tools.png")));
		menuBtnTools.setFocusPainted(false);
		menuBtnTools.setFocusable(false);
		menuPanel.add(menuBtnTools);

		// Setting
		JButton menuBtnSetting = new JButton("Setting");
		menuBtnSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new SettingWindow();
			}
		});
		menuBtnSetting.setIcon(new ImageIcon(SwingUtils.class.getResource("/images/setting.png")));
		menuBtnSetting.setFocusPainted(false);
		menuBtnSetting.setFocusable(false);
		menuPanel.add(menuBtnSetting);

		// set Layout to menuPanel
		menuPanel.setLayout(new GridLayout(5, 0, 0, 0));

		// *************************************** DELETE this section
		// ************************************

		// ******************** Summary Panel ********************
		summaryPanel = new JPanel();
		JLabel lblSummary = new JLabel("Summary ");
		lblSummary.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JSeparator sumSeparator = new JSeparator();

		JLabel lblAppName = new JLabel("<App Name>");
		lblAppName.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblVersion = new JLabel("<App Version>");
		lblVersion.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblAppSeverity = new JLabel("<App Serivity>");
		lblAppSeverity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAppSeverity.setBackground(Color.RED);
		lblAppSeverity.setOpaque(true);

		JLabel lblLastTime = new JLabel("Last time: ");
		lblLastTime.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblDateTime = new JLabel("<Date Time>");
		lblDateTime.setFont(new Font("Tahoma", Font.PLAIN, 16));

		GroupLayout gl_summaryPanel = new GroupLayout(summaryPanel);
		gl_summaryPanel.setHorizontalGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_summaryPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING).addComponent(lblSummary)
								.addGroup(gl_summaryPanel.createSequentialGroup().addComponent(lblAppName).addGap(35)
										.addComponent(lblVersion).addGap(18).addComponent(lblAppSeverity).addGap(165)
										.addComponent(lblLastTime).addGap(18).addComponent(lblDateTime))
								.addComponent(sumSeparator, GroupLayout.PREFERRED_SIZE, 1016,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(67, Short.MAX_VALUE)));
		gl_summaryPanel.setVerticalGroup(gl_summaryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_summaryPanel.createSequentialGroup().addContainerGap().addComponent(lblSummary)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(sumSeparator, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_summaryPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblAppName)
								.addComponent(lblLastTime)
								.addComponent(lblVersion, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAppSeverity).addComponent(lblDateTime))
						.addContainerGap(360, Short.MAX_VALUE)));
		summaryPanel.setLayout(gl_summaryPanel);
		// ******************** End of Summary Panel ********************

		// ******************** Categories Panel ********************
		categoriesPanel = new JPanel();
		JLabel findLblFinding = new JLabel("Categories");
		findLblFinding.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JSeparator categoriesSeparator = new JSeparator();

		// TODO: To insert more exploit list, insert here
		exploitListPanel = new JPanel();

		/** Change to Dynamic **/
		JPanel testPanel = new JPanel();

		JLabel lblExploitName = new JLabel("<ExploitName>");
		lblExploitName.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel = new JLabel("Decription");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblPages = new JLabel("<Pages> : ");
		lblPages.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblTotalVul = new JLabel("<Total Vul>");
		lblTotalVul.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblDescriptionDetail = new JLabel("Detail");
		lblDescriptionDetail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_testPanel = new GroupLayout(testPanel);
		gl_testPanel.setHorizontalGroup(gl_testPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_testPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_testPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_testPanel.createSequentialGroup()
										.addComponent(lblDescriptionDetail, GroupLayout.DEFAULT_SIZE, 1056,
												Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(gl_testPanel.createSequentialGroup().addComponent(lblExploitName)
										.addPreferredGap(ComponentPlacement.RELATED, 639, Short.MAX_VALUE)
										.addComponent(lblPages).addGap(18).addComponent(lblTotalVul).addGap(204))
								.addGroup(gl_testPanel.createSequentialGroup().addComponent(lblNewLabel)
										.addContainerGap(994, Short.MAX_VALUE)))));
		gl_testPanel.setVerticalGroup(gl_testPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_testPanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_testPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblExploitName)
								.addComponent(lblPages).addComponent(lblTotalVul))
						.addGap(27).addComponent(lblNewLabel).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblDescriptionDetail, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
						.addContainerGap()));
		testPanel.setLayout(gl_testPanel);
		/** Change to Dynamic **/

		GroupLayout gl_exploitListPanel = new GroupLayout(exploitListPanel);
		gl_exploitListPanel.setHorizontalGroup(gl_exploitListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_exploitListPanel.createSequentialGroup()
						.addComponent(testPanel, GroupLayout.PREFERRED_SIZE, 1076, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_exploitListPanel.setVerticalGroup(gl_exploitListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_exploitListPanel.createSequentialGroup()
						.addComponent(testPanel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(147, Short.MAX_VALUE)));
		exploitListPanel.setLayout(gl_exploitListPanel);

		GroupLayout gl_categoriesPanel = new GroupLayout(categoriesPanel);
		gl_categoriesPanel.setHorizontalGroup(gl_categoriesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_categoriesPanel.createSequentialGroup().addGroup(gl_categoriesPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_categoriesPanel.createSequentialGroup().addContainerGap()
								.addComponent(findLblFinding))
						.addComponent(exploitListPanel, GroupLayout.PREFERRED_SIZE, 1076, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_categoriesPanel.createSequentialGroup().addContainerGap().addComponent(
								categoriesSeparator, GroupLayout.PREFERRED_SIZE, 1019, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(34, Short.MAX_VALUE)));
		gl_categoriesPanel.setVerticalGroup(gl_categoriesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_categoriesPanel.createSequentialGroup().addContainerGap().addComponent(findLblFinding)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(categoriesSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(exploitListPanel, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		categoriesPanel.setLayout(gl_categoriesPanel);
		// ******************** End of Categories Panel ********************

		// Create containers for both summaryPanel and findingPanel
		Container cont = new Container();
		GroupLayout gl_scrollPane = new GroupLayout(cont);
		gl_scrollPane.setHorizontalGroup(gl_scrollPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_scrollPane.createSequentialGroup()
						.addGroup(gl_scrollPane.createParallelGroup(Alignment.LEADING)
								.addComponent(summaryPanel, GroupLayout.PREFERRED_SIZE, 1093,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(categoriesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));
		gl_scrollPane
				.setVerticalGroup(gl_scrollPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_scrollPane.createSequentialGroup()
								.addComponent(summaryPanel, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(categoriesPanel,
										GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		cont.setLayout(gl_scrollPane);

		// Put Container into scrollPane
		JScrollPane contScrollPane = new JScrollPane(cont);
		contScrollPane.setBorder(BorderFactory.createEmptyBorder());
		contScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// Set GroupLayout for Frame
		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(contScrollPane, GroupLayout.PREFERRED_SIZE, 1091, GroupLayout.PREFERRED_SIZE)
						.addGap(19)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 861, GroupLayout.PREFERRED_SIZE)
				.addComponent(contScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE));
		panel.setLayout(groupLayout);

	}
}
