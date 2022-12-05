import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ManagerRequests {
	private JFrame frmRequests;
	private JFrame frame;
	JTable table;
	JScrollPane scrollPane;
	Connection conn = null;
	PreparedStatement pst = null;
	
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
	
	public ManagerRequests() {
		initialize();
	}

	private void initialize() {
		frmRequests = new JFrame();
		frmRequests.setBounds(0, 0, 599, 428);
		frmRequests.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"TaskID", "Task"
				}
			));
		scrollPane = new JScrollPane();
		scrollPane.setBounds(545, 27, 812, 540);
		frmRequests.getContentPane().add(scrollPane);
		table.setBounds(685, 122, 612, 601);
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
	}
}
