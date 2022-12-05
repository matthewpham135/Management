import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeePerformanceReview {

	private JFrame frmEmployeePerformanceReview;
	private JTextField jtxtEmployeeID;
	private JTextField jtxtRating;
	private JTextField jtxtComments;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnReset;
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	
	DefaultTableModel model = new DefaultTableModel();
	protected JFrame frame;

	/**
	 * Launch the application.
	 */
	
	public void updatemyTable() {
		
		conn = EmployeeData.ConnectDB();
		
		if (conn != null) {
			
			String sql = "Select * from reviews";
		
			
		try {
			conn = EmployeeData.ConnectDB();
			String query = "select * from reviews";
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
						rs.getString("EmpID"),
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
		catch (Exception e){
			
			JOptionPane.showMessageDialog(null,e);
		}
		
	  }
			
	}
		
	
	public static void prefRev() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeePerformanceReview window = new EmployeePerformanceReview();
					window.frmEmployeePerformanceReview.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// * Create the application.

	public EmployeePerformanceReview() {
		initialize();
	}

	
	 //* Initialize the contents of the frame.
	 
	void initialize() {
		frmEmployeePerformanceReview = new JFrame();
		frmEmployeePerformanceReview.setTitle("Employee Performance Review");
		frmEmployeePerformanceReview.setBounds(100, 100, 1218, 672);
		frmEmployeePerformanceReview.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(95, 117, 118, 31);
		frmEmployeePerformanceReview.getContentPane().add(lblNewLabel);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setBounds(247, 127, 187, 19);
		frmEmployeePerformanceReview.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Rating");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(95, 188, 102, 61);
		frmEmployeePerformanceReview.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Comments");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(94, 274, 129, 55);
		frmEmployeePerformanceReview.getContentPane().add(lblNewLabel_2);
		
		jtxtRating = new JTextField();
		jtxtRating.setBounds(247, 213, 187, 19);
		frmEmployeePerformanceReview.getContentPane().add(jtxtRating);
		jtxtRating.setColumns(10);
		
		jtxtComments = new JTextField();
		jtxtComments.setBounds(247, 293, 187, 19);
		frmEmployeePerformanceReview.getContentPane().add(jtxtComments);
		jtxtComments.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Employee Performance Review");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel_3.setBounds(399, 11, 531, 47);
		frmEmployeePerformanceReview.getContentPane().add(lblNewLabel_3);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(554, 117, 613, 345);
		frmEmployeePerformanceReview.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmpID", "Rating", "Comments"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		JButton btnAddNew = new JButton("Add ");
		btnAddNew.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			String sql = "INSERT INTO reviews(EmpID, Rating, Comments)VALUES(?,?,?)";
			
			try {
				conn = EmployeeData.ConnectDB();
				pst = conn.prepareStatement(sql);
				pst.setString(1, jtxtEmployeeID.getText());
				pst.setString(2, jtxtRating.getText());
				pst.setString(3, jtxtComments.getText());
				
				pst.execute();
				
				pst.close();
				conn.close();
				JOptionPane.showMessageDialog(null,"Database Updated");
			}
			catch (Exception ev) {
				
				JOptionPane.showMessageDialog(null,"Error!");
			}
	
			
			}
		});
		btnAddNew.setBounds(89, 472, 167, 58);
		frmEmployeePerformanceReview.getContentPane().add(btnAddNew);
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jtxtEmployeeID.setText(null);
				jtxtRating.setText(null);
				jtxtComments.setText(null);
			}
		});
		btnReset.setBounds(338, 472, 167, 58);
		frmEmployeePerformanceReview.getContentPane().add(btnReset);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatemyTable();
			}
		});
		btnNewButton.setBounds(455, 117, 89, 23);
		frmEmployeePerformanceReview.getContentPane().add(btnNewButton);
		
		Object col[] = {"EmpID", "Rating", "Comments"};
		model.setColumnIdentifiers(col);
		updatemyTable();
	}
}