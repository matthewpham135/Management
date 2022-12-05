
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import javax.swing.*;
import java.awt.BorderLayout;
public class Reviews {
	private JFrame frmReviews;
	private JFrame frame;
	Connection conn = null;
	PreparedStatement pst = null;
	private JTable table;
	private final JPanel panel = new JPanel();
	private JScrollPane scrollPane;
	private JTable tasksTable;
    private JScrollPane taskScroll;
	DefaultTableModel tasksModel = new DefaultTableModel();
	private static String id;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();
	private JTable table_1;
	private JTable table_2;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void reviewScreen(String eid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					id = eid;
					Reviews window = new Reviews();
					window.frmReviews.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void loadList() {
		try
		{
			// connects to the database and creates a command to select the first record
			conn = EmployeeData.ConnectDB();
			String query = "select * from reviews where EmpID = "  + id;
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			// resets the current table
			while(model.getRowCount() > 0)
			{
			    model.removeRow(0);
			}
			
			DefaultListModel DLM = new DefaultListModel();
			// iterates through each record and adds it to the table GUI shown to the user
			while(rs.next()) {
				DLM.addElement(rs.getString("EmpID"));
				model.addRow(new Object[] {
						rs.getString("Rating"),
						rs.getString("Comments"),
				});
			}
			// closes all connections
			pst.close();
			conn.close();
			if (rs != null) 
				rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * Create the application.
	 */
	public Reviews() {
		initialize();
	}
	
	private void initialize() {
		frmReviews = new JFrame();
		frmReviews.setBounds(0, 0, 599, 677);
		frmReviews.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Rating", "Comments"
				}
			));
		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 11, 523, 599);
		frmReviews.getContentPane().add(scrollPane);
		table.setBounds(685, 122, 612, 601);
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		Object col[] = {"Ratings", "Comments"};
		model.setColumnIdentifiers(col);
		loadList();
	}
}