package appsecframework.Windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class HelpWindow extends JFrame {

	private JFrame frame;
	private JPanel panel;
	private JTable table;
	private DefaultTableModel tableModel;
	
	String columns[] = { "Variable", "Description", "Example" };
	String rows[][] = { {"{{SCAN_TARGET_URLPORT}}", "URL with port of scanningtarget", "http://localhost:8080"},
						{"{{SCAN_TARGET_URL}}", "URL of scanning target", "http://localhost"},
						{"{{SCAN_TARGET_PORT}}", "Port of scanning target", "8080"},
						{"{{SCAN_TARGET_PATH}}", "Path of scanning target", "/home/user/Project"},
						{"{{SCAN_RESULT_FILE}}",	  "Name of scanning result file", "tool_result.json"},
						{"{{SCAN_RESULT_FILEPATH}}", "Path with name of scanning result file", "/home/user/scan_result/tool_result.json"},
						{"{{SCAN_RESULT_DIRECTORY}}", "Directory that contains all result", "/home/user/scan_result"},
						{"{{APP_AUTH_USERNAME}}", "Username" ,"test_user"},
						{"{{APP_AUTH_PASSWORD}}", "Password" ,"test_password"} };
			
	public HelpWindow() {
		initialize();
		frame.setVisible(true);
	}

	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		frame.setBounds(500, 500, 800, 300);
		frame.setMinimumSize(new Dimension(800,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(SystemColor.menu);
		panel = new JPanel(new GridLayout(1, 0, 0, 0));
	
		JTable table = new JTable(rows, columns);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
