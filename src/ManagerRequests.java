import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagerRequests {
	private JFrame frmRequests;
	private JFrame frame;
	JTable table;
	JScrollPane scrollPane;
	Connection conn = null;
	PreparedStatement pst = null;
	DefaultTableModel model = new DefaultTableModel();
	private JButton btnNewButton;
	
	public static void requestScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerRequests window = new ManagerRequests();
					window.frmRequests.setVisible(true);
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
			String query = "select * from requests";
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
						rs.getString("Message"),
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
	
	
	
	public ManagerRequests() {
		initialize();
	}

	private void initialize() {
		frmRequests = new JFrame();
		frmRequests.setBounds(0, 0, 787, 670);
		frmRequests.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"EmpID", "Message"
				}
			));
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 751, 570);
		frmRequests.getContentPane().add(scrollPane);
		table.setBounds(685, 122, 612, 601);
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadList();
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		frmRequests.getContentPane().add(btnNewButton);
		Object col[] = {"EmpID", "Message"};
		model.setColumnIdentifiers(col);
		loadList();
	}
}
