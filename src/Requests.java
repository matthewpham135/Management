import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Requests {
	private JFrame frmRequests;
	private JFrame frame;
	private static String id;
	Connection conn = null;
	PreparedStatement pst = null;
	
	
	public static void requestScreen(String eid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					id = eid;
					Requests window = new Requests();
					window.frmRequests.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Requests() {
		initialize();
	}
	
	private void initialize() {
		frmRequests = new JFrame();
		frmRequests.setBounds(0, 0, 599, 428);
		frmRequests.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Request");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(23, 11, 157, 64);
		frmRequests.getContentPane().add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(23, 86, 527, 200);
		frmRequests.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Send Request");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String message = textArea.getText();
				String sql = "INSERT INTO requests(EmpID,Message)VALUES(?,?)";
				conn = EmployeeData.ConnectDB();
				pst = conn.prepareStatement(sql);
				pst.setString(1, id);
				pst.setString(2, message);
				pst.execute();
				pst.close();
				conn.close();
				JOptionPane.showMessageDialog(null, "Employee Record Successfully Added");
				}
				catch (SQLException ed) {
		            System.out.println(ed.getMessage());
		        }
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(345, 308, 205, 49);
		frmRequests.getContentPane().add(btnNewButton);
		
	}
}
