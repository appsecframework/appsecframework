package appsecframework.Utilities;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import appsecframework.Project;
import appsecframework.TestingTool;
import appsecframework.Windows.DashboardWindow;
import appsecframework.Windows.ProjectWindow;
import appsecframework.Windows.SettingWindow;
import appsecframework.Windows.ToolWindow;

public class SwingUtils {

	public static JFrame frame;
	public static JPanel menuPanel;
	public static ArrayList<Project> projectList;
	public static ArrayList<TestingTool> toolList;

	// Initialize JFrame for every pages
	public static JFrame createWindow(String name) {
		frame = new JFrame(name);

		frame.setBounds(0, 0, 1300, 900);
		frame.setMinimumSize(new Dimension(1300, 900));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		return frame;
	}

	// Create Menu Panel for JFrame
	public static JPanel getMenuPanel() {
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
		menuPanel.setLayout(new GridLayout(4, 0, 0, 0));
		return menuPanel;
	}

	// create JCheckBox list for Custom Scan function
	public static JPanel createCustomScanPanel(JPanel panel, ArrayList<JCheckBox> checkboxList) {
		panel.removeAll();
		panel.setLayout(new GridLayout(0, 1, 5, 5));
		
		for (JCheckBox c : checkboxList) {
			panel.add(c);
		}
		
		panel.revalidate();
		panel.repaint();
		
		return panel;
	}

}
