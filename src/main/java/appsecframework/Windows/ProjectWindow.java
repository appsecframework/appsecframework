package appsecframework.Windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import appsecframework.MainController;
import appsecframework.Project;
import appsecframework.Utilities.ConfigUtils;
import appsecframework.Utilities.JenkinsUtils;
import appsecframework.Utilities.SwingUtils;

public class ProjectWindow {
	// TODO: Add Start job when Scan Now is pressed
	private static JFrame frame;
	private DefaultTableModel tableModel;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel headingPanel;
	private JPanel projectPanel;
	private JScrollPane projectScrollPane;
	private JTable projectTable;
	private JTableHeader projectTableHeader;
	private Timer timer;

	private static final int WAIT_SECOND = 5000;
	private static ArrayList<Project> projectList;

	String[] columns = new String[] { "Name", "Application Type", "Last Scan Date", "Scan Status", "Scan",
			"Configuration", "Result" };

	public ProjectWindow() {
		projectList = MainController.getProjectList();
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Project");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel();

		// Detail for this window goes here
		headingPanel = new JPanel();
		JLabel lblProject = new JLabel("Project");
		lblProject.setFont(new Font("Tahoma", Font.PLAIN, 24));

		JButton btnNewProject = new JButton("New Project");
		btnNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new NewProjectWindow();
			}
		});
		btnNewProject.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
		btnNewProject.setFocusable(false);

		JSeparator headingSeparator = new JSeparator();
		headingSeparator.setBackground(Color.LIGHT_GRAY);

		// Fetch data for Table
		tableModel = new DefaultTableModel(columns, 0); // columns, row
		for (Project p : projectList) {
			String projectName = p.getName();
			String projectType = p.getPlatform();
			String lastScanDate = p.getLastScanDate();
			String scanStatus = p.getScanStatus();

			Object[] data = { projectName, projectType, lastScanDate, scanStatus, "Scan Now", "Configure", "View" };
			tableModel.addRow(data);
		}

		// Configure Table
		projectTable = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int col) {
				if (col == 4 || col == 5 || col == 6)
					return true;
				return false;
			}
		};
		projectTable.setFillsViewportHeight(true);
		projectTable.setShowVerticalLines(false);
		projectTable.setShowHorizontalLines(false);
		projectTable.setRowSelectionAllowed(false);
		projectTable.setBackground(SystemColor.menu);
		projectTable.setShowGrid(false);
		projectTable.setRowHeight(30);

		// Custom Table Header
		projectTableHeader = projectTable.getTableHeader();
		projectTableHeader.setReorderingAllowed(false);
		projectTableHeader.setFont(new Font("Tahoma", Font.PLAIN, 16));
		((DefaultTableCellRenderer) projectTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		// Custom Column "Status"
		projectTable.getColumn("Scan Status").setCellRenderer(new LabelRenderer());

		// Custom Column "Scan"
		projectTable.getColumn("Scan").setCellRenderer(new scanRenderer());
		projectTable.getColumn("Scan").setCellEditor(new scanEditor(new JCheckBox()));

		projectTable.getColumn("Configuration").setCellRenderer(new configureRenderer());
		projectTable.getColumn("Configuration").setCellEditor(new configureEditor(new JCheckBox()));

		// Custom Column "Download"
		projectTable.getColumn("Result").setCellRenderer(new viewRenderer());
		projectTable.getColumn("Result").setCellEditor(new viewEditor(new JCheckBox()));
		projectTable.setDefaultRenderer(Object.class, new TableRenderer());

		projectScrollPane = new JScrollPane(projectTable);
		projectScrollPane.setBorder(BorderFactory.createEmptyBorder());

		projectPanel = new JPanel();
		projectPanel.setLayout(new GridLayout(1, 0, 0, 0));
		projectPanel.add(projectScrollPane);

		GroupLayout gl_headingPanel = new GroupLayout(headingPanel);
		gl_headingPanel.setHorizontalGroup(gl_headingPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_headingPanel.createSequentialGroup().addContainerGap().addGroup(gl_headingPanel
						.createParallelGroup(Alignment.LEADING)
						.addComponent(headingSeparator, GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
						.addGroup(gl_headingPanel.createSequentialGroup().addComponent(lblProject)
								.addPreferredGap(ComponentPlacement.RELATED, 964, Short.MAX_VALUE).addComponent(
										btnNewProject, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_headingPanel.setVerticalGroup(gl_headingPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_headingPanel
				.createSequentialGroup().addGap(23)
				.addGroup(gl_headingPanel.createParallelGroup(Alignment.TRAILING).addComponent(lblProject)
						.addComponent(btnNewProject, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(headingSeparator,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(27, Short.MAX_VALUE)));
		headingPanel.setLayout(gl_headingPanel);

		GroupLayout groupLayout = new GroupLayout(panel);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(projectPanel, GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE).addGap(10))
						.addGroup(
								groupLayout.createSequentialGroup().addComponent(headingPanel, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(headingPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(projectPanel, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE).addGap(0)));

		panel.setLayout(groupLayout);
		// Create a timer.
		timer = new Timer(WAIT_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				fetchStatus();
			}
		});
		fetchStatus(); // First fetch when enter the screen
		timer.start(); // to refresh UI for every 5 seconds
	}

	/* Utilities classes for JTable */
	// For JTable
	static class TableRenderer extends DefaultTableCellRenderer {
		public TableRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setHorizontalAlignment(JLabel.CENTER);
			setBorder(noFocusBorder);
			return this;
		}
	}

	// For JLabel (Complete, Incomplete) in JTable
	static class LabelRenderer extends JLabel implements TableCellRenderer {

		public LabelRenderer() {
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value != null) {
				if (value.equals("Success")) {
					setForeground(Color.GREEN);
				} else if (value.equals("Not Started")) {
					setForeground(Color.ORANGE);
				} else if (value.equals("Scanning...")) {
					setForeground(Color.BLUE);
				} else { // Failed
					setForeground(Color.RED);
				}
				setHorizontalAlignment(JLabel.CENTER);
			} else {
				value = "-";
			}
			setText(value.toString());
			return this;
		}
	}

	// For JButton (Scan + Configure) in JTable
	static class scanRenderer extends JButton implements TableCellRenderer {

		public scanRenderer() {
			setOpaque(true);
			setIcon(new ImageIcon((getClass().getResource("/images/scan.png"))));
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());

			String scanStatus = projectList.get(row).getScanStatus();
			if (scanStatus.equals("Scanning...")) {
				setEnabled(false);
			} else {
				setEnabled(true);
			}
			return this;
		}
	}

	// For JButton (Scan + Configure) in JTable
	static class configureRenderer extends JButton implements TableCellRenderer {

		public configureRenderer() {
			setOpaque(true);
			setIcon(new ImageIcon(getClass().getResource("/images/configure.png")));
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	// For JButton (Download) in JTable
	static class viewRenderer extends JButton implements TableCellRenderer {

		public viewRenderer() {
			setOpaque(true);
			setIcon(new ImageIcon(getClass().getResource("/images/download.png")));
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	// For Scan Button action
	static class scanEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		//private boolean isPushed;
		private int row;

		public scanEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					MainController.getJenkins().startJob(projectList.get(row).getName());
					projectList.get(row).setScanStatus("Scanning...");
					projectList.get(row).setImported(false); // Set imported status to false when start new scan
					
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			//isPushed = true;
			
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			return button;
		}

		public Object getCellEditorValue() {
//			if (isPushed) {
//				JOptionPane.showMessageDialog(button, label + ": Done");
//			}
//			isPushed = false;
			return new String(label);
		}

//		public boolean stopCellEditing() {
//			isPushed = false;
//			return super.stopCellEditing();
//		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	// For Configure Button action
	static class configureEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		//private boolean isPushed;
		private int row;

		public configureEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					frame.dispose();
					new ProjectConfigurationWindow(projectList.get(row), row);
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			//isPushed = true;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			button.setIcon(new ImageIcon(getClass().getResource("/images/configure.png")));
			return button;
		}

		public Object getCellEditorValue() {
//			if (isPushed) {
//				JOptionPane.showMessageDialog(button, label + ": Done");
//			}
//			isPushed = false;
			return new String(label);
		}
		
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	// For View Button action
	static class viewEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		//private boolean isPushed;
		private int row;

		public viewEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						String uri = "http://localhost:8000/product/" + (row + 1) + "/finding/all";
						try {
							Desktop.getDesktop().browse(new URI(uri));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			//isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
//			if (isPushed) {
//				JOptionPane.showMessageDialog(button, label + ": Done");
//			}
//			isPushed = false;
			return new String(label);
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}
	
	private void fetchStatus() {
		// Fetch data for Table
		tableModel = new DefaultTableModel(columns, 0); // columns, row
		for (Project p : projectList) {
			ArrayList<String> jobStatus = (ArrayList<String>) MainController.getJenkins().fetchJobStatus(p);
			String scanStatus = jobStatus.get(0);
			p.setScanStatus(scanStatus);
			String lastScanDate = jobStatus.get(1);
			p.setLastScanDate(lastScanDate);

			Object[] data = { p.getName(), p.getPlatform(), p.getLastScanDate(), p.getScanStatus(), "Scan Now",
					"Configure", "View" };
			tableModel.addRow(data);
		}
		ConfigUtils.saveProjects("config/projects.yaml", projectList);

		projectTable.setModel(tableModel);
		projectTable.setFillsViewportHeight(true);
		projectTable.setShowVerticalLines(false);
		projectTable.setShowHorizontalLines(false);
		projectTable.setRowSelectionAllowed(false);
		projectTable.setBackground(SystemColor.menu);
		projectTable.setShowGrid(false);
		projectTable.setRowHeight(30);

		// Custom Table Header
		projectTableHeader = projectTable.getTableHeader();
		projectTableHeader.setReorderingAllowed(false);
		projectTableHeader.setFont(new Font("Tahoma", Font.PLAIN, 16));
		((DefaultTableCellRenderer) projectTableHeader.getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);

		// Custom Column "Status"
		projectTable.getColumn("Scan Status").setCellRenderer(new LabelRenderer());

		// Custom Column "Scan"
		projectTable.getColumn("Scan").setCellRenderer(new scanRenderer());
		projectTable.getColumn("Scan").setCellEditor(new scanEditor(new JCheckBox()));

		projectTable.getColumn("Configuration").setCellRenderer(new configureRenderer());
		projectTable.getColumn("Configuration").setCellEditor(new configureEditor(new JCheckBox()));

		// Custom Column "Download"
		projectTable.getColumn("Result").setCellRenderer(new viewRenderer());
		projectTable.getColumn("Result").setCellEditor(new viewEditor(new JCheckBox()));
		projectTable.setDefaultRenderer(Object.class, new TableRenderer());
	}

}
