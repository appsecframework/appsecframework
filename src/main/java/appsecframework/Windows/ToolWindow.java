package appsecframework.Windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import appsecframework.MainController;
import appsecframework.TestingTool;
import appsecframework.Utilities.SwingUtils;

public class ToolWindow {

	private static JFrame frame;
	private JPanel panel;
	private JPanel menuPanel;
	private JPanel headingPanel;
	private JScrollPane toolsScrollPane;
	private JTable toolsTable;
	private JTableHeader toolsTableHeader;

	private static ArrayList<TestingTool> toolList;

	// Define columns (attributes) for the table
	String[] columns = new String[] { "Name", "Type", "Configuration" };

	public ToolWindow() {
		toolList = MainController.getToolList();
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		panel = new JPanel(); // panel for all components
		frame = SwingUtils.createWindow("Tool");
		frame.getContentPane().add(panel);
		menuPanel = SwingUtils.getMenuPanel();

		// ******************** Heading Panel ********************
		headingPanel = new JPanel();

		JLabel lblTool = new JLabel("Tool");
		lblTool.setFont(new Font("Tahoma", Font.PLAIN, 24));

		JButton btnNewTools = new JButton("Add Tool");
		btnNewTools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new AddToolWindow();
			}
		});
		btnNewTools.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
		btnNewTools.setFocusable(false);

		JSeparator headingSeparator = new JSeparator();
		headingSeparator.setBackground(Color.LIGHT_GRAY);

		DefaultTableModel tableModel = new DefaultTableModel(columns, 0); // columns, row
		for (TestingTool t : toolList) {
			String toolName = t.getToolName();
			String toolType = t.getToolType();

			Object[] data = { toolName, toolType, "Configure" };
			tableModel.addRow(data);
		}
		toolsTable = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int row, int col) {
				if (col == 2)
					return true;
				return false;
			}
		};
		toolsTable.setFillsViewportHeight(true);
		toolsTable.setShowVerticalLines(false);
		toolsTable.setShowHorizontalLines(false);
		toolsTable.setRowSelectionAllowed(false);
		toolsTable.setBackground(SystemColor.menu);
		toolsTable.setShowGrid(false);
		toolsTable.setRowHeight(30);

		// Custom Table Header
		toolsTableHeader = toolsTable.getTableHeader();
		toolsTableHeader.setReorderingAllowed(false);
		toolsTableHeader.setFont(new Font("Tahoma", Font.PLAIN, 16));
		((DefaultTableCellRenderer) toolsTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

		// Custom Column "Download"
		toolsTable.getColumn("Configuration").setCellRenderer(new ConfigureRenderer());
		toolsTable.getColumn("Configuration").setCellEditor(new ConfigureEditor(new JCheckBox()));
		toolsTable.setDefaultRenderer(Object.class, new TableRenderer());

		toolsScrollPane = new JScrollPane(toolsTable);
		toolsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new GridLayout(1, 0, 0, 0));
		toolPanel.add(toolsScrollPane);

		// Set all layouts
		GroupLayout gl_headingPanel = new GroupLayout(headingPanel);
		gl_headingPanel.setHorizontalGroup(gl_headingPanel.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_headingPanel.createSequentialGroup().addContainerGap().addGroup(gl_headingPanel
						.createParallelGroup(Alignment.LEADING)
						.addComponent(headingSeparator, GroupLayout.DEFAULT_SIZE, 1173, Short.MAX_VALUE)
						.addGroup(gl_headingPanel.createSequentialGroup().addComponent(lblTool)
								.addPreferredGap(ComponentPlacement.RELATED, 964, Short.MAX_VALUE).addComponent(
										btnNewTools, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_headingPanel.setVerticalGroup(gl_headingPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_headingPanel.createSequentialGroup().addGap(23)
						.addGroup(gl_headingPanel.createParallelGroup(Alignment.TRAILING).addComponent(lblTool)
								.addComponent(btnNewTools, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
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
								.addComponent(toolPanel, GroupLayout.DEFAULT_SIZE, 1193, Short.MAX_VALUE).addGap(10))
						.addGroup(
								groupLayout.createSequentialGroup().addComponent(headingPanel, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(headingPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(toolPanel, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE).addGap(0)));

		panel.setLayout(groupLayout);
	}

	/* Utilities class for JTable */
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
			setHorizontalAlignment(JLabel.CENTER);
			setText((value == null) ? "" : value.toString());

			if (value == "Completed") {
				setForeground(Color.GREEN);
			} else {
				setForeground(Color.RED);
			}
			return this;
		}
	}

	// For JButton (Downlooad) in JTable
	static class ConfigureRenderer extends JButton implements TableCellRenderer {

		public ConfigureRenderer() {
			// setContentAreaFilled(false); // uncomment this if you want to fill JButton
			// color
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

	// For JButton dialog popup
	static class ConfigureEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;
		private int row;

		public ConfigureEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new ToolConfigurationWindow(toolList.get(row), row);
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.row = row;
			isPushed = true;
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			button.setIcon(new ImageIcon(getClass().getResource("/images/configure.png")));
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				JOptionPane.showMessageDialog(button, label + ": Done");
			}
			isPushed = false;
			return new String(label);
		}
	}

}
