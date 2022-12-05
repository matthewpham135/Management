import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class TaskPage
{

	private JFrame frmCreateTasks;
	private JTextField tasknameField;
	private JTextField empidField;
	private JTextField taskidField;
	Connection conn = null;
	PreparedStatement pst = null;
	/**
	 * Launch the application.
	 */
	public static void TaskScreen() { //shows the tasks screen
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskPage window = new TaskPage();
					window.frmCreateTasks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TaskPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreateTasks = new JFrame();
		//frmCreateTasks.setIconImage(Toolkit.getDefaultToolkit().getImage(TaskPage.class.getResource("/image/SnySys.png")));
		frmCreateTasks.setTitle("Create Tasks");
		frmCreateTasks.setBounds(100, 100, 748, 582);
		frmCreateTasks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCreateTasks.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 732, 543);
		frmCreateTasks.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(157, 81, 405, 451);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane txtpnTaskName = new JTextPane();
		txtpnTaskName.setEditable(false);
		txtpnTaskName.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnTaskName.setText("Task");
		txtpnTaskName.setBounds(45, 54, 124, 33);
		panel_1.add(txtpnTaskName);
		
		tasknameField = new JTextField();
		tasknameField.setBounds(199, 54, 196, 103);
		panel_1.add(tasknameField);
		tasknameField.setColumns(10);
		
		JButton btnNewButton = new JButton("Create Task"); //creates a task and puts it into the database
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create sql command to add new record 
				String sql = "INSERT INTO tasks(TaskID, EmpID, TaskName)VALUES(?,?,?)";
				
				try
				{
					// connects to database
					conn = EmployeeData.ConnectDB(); //connects to the database
					
					//int flag = 0;
					
					String taskID = taskidField.getText();
					String empID = empidField.getText();
					String taskName = tasknameField.getText();

					// accesses database and adds new record 
					pst = conn.prepareStatement(sql);
					pst.setString(1,  taskidField.getText());
					pst.setString(2,  empidField.getText());
					pst.setString(3,  tasknameField.getText());
					pst.execute();
					
					pst.close();
					conn.close();
					
					
					frmCreateTasks.dispose();
					JOptionPane.showMessageDialog(null, "Task Successfully Added");
				}
				catch(Exception ev)
				{
					System.err.format(ev.getMessage());
					JOptionPane.showMessageDialog(null, "Error");
				}
				// re updates the table 
				
				//EmployeeTEST employee = new EmployeeTEST();
				//employee.loadTasks();
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setBounds(130, 407, 139, 33);
		panel_1.add(btnNewButton);
		
		JTextPane txtpnEmpId = new JTextPane();
		txtpnEmpId.setText("Emp ID");
		txtpnEmpId.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnEmpId.setEditable(false);
		txtpnEmpId.setBounds(45, 168, 124, 33);
		panel_1.add(txtpnEmpId);
		
		empidField = new JTextField();
		empidField.setToolTipText("3 digit Emp ID");
		empidField.setColumns(10);
		empidField.setBounds(199, 168, 86, 33);
		panel_1.add(empidField);
		
		JTextPane txtpnTaskId = new JTextPane();
		txtpnTaskId.setText("Task ID");
		txtpnTaskId.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnTaskId.setEditable(false);
		txtpnTaskId.setBounds(45, 212, 124, 33);
		panel_1.add(txtpnTaskId);
		
		taskidField = new JTextField();
		taskidField.setToolTipText("3 digit Emp ID");
		taskidField.setColumns(10);
		taskidField.setBounds(199, 212, 86, 33);
		panel_1.add(taskidField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		panel_2.setBounds(0, 11, 732, 44);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JTextPane txtpnCreateTask = new JTextPane();
		txtpnCreateTask.setEditable(false);
		txtpnCreateTask.setBackground(new Color(192, 192, 192));
		txtpnCreateTask.setFont(new Font("Times New Roman", Font.BOLD, 27));
		txtpnCreateTask.setText("Create Task");
		txtpnCreateTask.setBounds(273, 0, 209, 44);
		panel_2.add(txtpnCreateTask);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCreateTasks.dispose(); //closes the Task window
			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(10, 11, 89, 23);
		panel_2.add(btnNewButton_1);
		
		
		
		
	}
}
