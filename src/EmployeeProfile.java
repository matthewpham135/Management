import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import java.awt.Color;

public class EmployeeProfile {
	JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTable tasksTable;
    private JScrollPane taskScroll;
	DefaultTableModel tasksModel = new DefaultTableModel();
	private String id;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();
	
	public EmployeeProfile(String eid) {
		id = eid;
	}
	
	
	public void loadList() {
		try
		{
			// connects to the database and creates a command to select the first record
			conn = EmployeeData.ConnectDB();
			String query = "select * from tasks where EmpID = "  + id;
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
				DLM.addElement(rs.getString("TaskID"));
				model.addRow(new Object[] {
						rs.getString("TaskID"),
						rs.getString("TaskName"),
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
	
	
	public void loadPerformance() {
        try {
                // connects to the database and creates a command to select the first record
                conn = EmployeeData.ConnectDB();
                String query = "select * from tasks";
                PreparedStatement pst = conn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                DefaultTableModel tasksModel = (DefaultTableModel) tasksTable.getModel();


                // resets the current table
                while(tasksModel.getRowCount() > 0)
                {
                    tasksModel.removeRow(0);
                }

                DefaultListModel DLM = new DefaultListModel();
                // iterates through each record and adds it to the table GUI shown to the user
                while(rs.next()) {
                    DLM.addElement(rs.getString("TaskID"));
                    tasksModel.addRow(new Object[] {
                            rs.getString("TaskID"),
                            rs.getString("EmpID"),
                            rs.getString("TaskName"),
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
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1400, 677);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		frame.getContentPane().add(scrollPane);
		table.setBounds(685, 122, 612, 601);
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		
		JLabel lblNewLabel = new JLabel("Tasks");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel.setBounds(685, 39, 106, 72);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadList();
			}
		});
		btnNewButton.setBounds(446, 30, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnTasks = new JButton("View Performance");
		 btnTasks.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 
				 try {
					 Reviews re = new Reviews();
					 re.reviewScreen(id);
				 }
				 catch(Exception ev)
				 {
					 System.err.format(ev.getMessage());
					 JOptionPane.showMessageDialog(null, "Error");
				 }
			 }
		 });
		 btnTasks.setFont(new Font("Tahoma", Font.BOLD, 18));
		 btnTasks.setBounds(33, 509, 214, 47);
		 frame.getContentPane().add(btnTasks);
		
		JButton btnRequests = new JButton("Requests");
		btnRequests.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						 
					try {
						Requests re = new Requests();
						re.requestScreen(id);
					}
					catch(Exception ev)
					{
						System.err.format(ev.getMessage());
						JOptionPane.showMessageDialog(null, "Error");
					}
				}
		});
		btnRequests.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRequests.setBounds(308, 509, 214, 47);
		frame.getContentPane().add(btnRequests);
		String fname = null;
		String lname= null;
		String email= null;
		String start= null;
		String end= null;
		String salary= null;
		String EmpID= null;
		String phone= null;
		String department= null;
		String position= null;
		String DOB= null;
		try {
			conn = EmployeeData.ConnectDB();
			String query = "select * from Employee where EmpID = " + id;
			PreparedStatement pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			fname = rs.getString("Firstname");
			lname = rs.getString("Lastname");
			email = rs.getString("Email");
			start = rs.getString("Start");
			end = rs.getString("End");
			salary = rs.getString("Salary");
			EmpID = rs.getString("EmpID");
			phone = rs.getString("PhoneNum");
			department = rs.getString("Department");
			position = rs.getString("Position");
			DOB = rs.getString("DOB");
			conn.close();
			pst.close();
			rs.close();
			
		}
		catch (SQLException ed) {
            System.out.println(ed.getMessage());
        }
		
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(45, 59, 106, 41);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(45, 99, 106, 41);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Email");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(45, 136, 106, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Start Date");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setBounds(45, 175, 106, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("End Date");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1.setBounds(45, 213, 106, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Salary");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1.setBounds(45, 253, 106, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Employee ID");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(45, 293, 128, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1_1 = new JLabel("Department");
		lblNewLabel_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1_1_1.setBounds(45, 332, 128, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1 = new JLabel("Position");
		lblNewLabel_1_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1_1_1_1.setBounds(45, 372, 128, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1_1_1_1 = new JLabel("Date of Birth");
		lblNewLabel_1_1_1_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1_1_1_1_1.setBounds(45, 413, 128, 41);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1_1_1_1);
		
		JLabel pfname = new JLabel(fname);
		pfname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pfname.setBounds(197, 59, 289, 41);
		frame.getContentPane().add(pfname);
		
		JLabel plname = new JLabel(lname);
		plname.setFont(new Font("Tahoma", Font.PLAIN, 18));
		plname.setBounds(197, 99, 289, 41);
		frame.getContentPane().add(plname);
		
		JLabel pmail = new JLabel(email);
		pmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pmail.setBounds(197, 136, 289, 41);
		frame.getContentPane().add(pmail);
		
		JLabel pstart = new JLabel(start);
		pstart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pstart.setBounds(197, 175, 289, 41);
		frame.getContentPane().add(pstart);
		
		JLabel pend = new JLabel(end);
		pend.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pend.setBounds(197, 213, 289, 41);
		frame.getContentPane().add(pend);
		
		JLabel psalary = new JLabel("$" + salary);
		psalary.setFont(new Font("Tahoma", Font.PLAIN, 18));
		psalary.setBounds(197, 253, 289, 41);
		frame.getContentPane().add(psalary);
		
		JLabel pid = new JLabel(EmpID);
		pid.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pid.setBounds(197, 293, 289, 41);
		frame.getContentPane().add(pid);
		
		JLabel pdepartment = new JLabel(department);
		pdepartment.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pdepartment.setBounds(197, 332, 289, 41);
		frame.getContentPane().add(pdepartment);
		
		JLabel pposition = new JLabel(position);
		pposition.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pposition.setBounds(197, 372, 289, 41);
		frame.getContentPane().add(pposition);
		
		JLabel pdob = new JLabel(DOB);
		pdob.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pdob.setBounds(197, 413, 289, 41);
		frame.getContentPane().add(pdob);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(33, 480, 489, 2);
		frame.getContentPane().add(separator);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(1143, 578, 214, 47);
		frame.getContentPane().add(btnExit);

		
		Object col[] = {"TaskID", "Task"};
		model.setColumnIdentifiers(col);
		loadList();
	}
}
