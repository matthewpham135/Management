import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.sql.*;
import javax.swing.*;
public class EditTask {
	private JFrame frmeditTasks;
	private JFrame frame;
	Connection conn = null;
	PreparedStatement pst = null;
	/**
	 * Launch the application.
	 */
	public static void editScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditTask window = new EditTask();
					window.frmeditTasks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditTask() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmeditTasks = new JFrame();
		//frmeditTasks.setIconImage(Toolkit.getDefaultToolkit().getImage(TaskPage.class.getResource("/image/SnySys.png")));
		frmeditTasks.setTitle("Edit Tasks");
		frmeditTasks.setBounds(100, 100, 748, 582);
		frmeditTasks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmeditTasks.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 732, 543);
		frmeditTasks.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(148, 81, 405, 451);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane txtpnTaskId = new JTextPane();
		txtpnTaskId.setBounds(85, 23, 107, 42);
		panel_1.add(txtpnTaskId);
		txtpnTaskId.setText("Task ID");
		txtpnTaskId.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnTaskId.setEditable(false);
		
		JTextPane txtpneditTaskName = new JTextPane();
		txtpneditTaskName.setEditable(false);
		txtpneditTaskName.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpneditTaskName.setText("Task Name");
		txtpneditTaskName.setBounds(44, 110, 124, 33);
		panel_1.add(txtpneditTaskName);
		
		JTextField tasknameField = new JTextField();
		tasknameField.setBounds(199, 110, 196, 96);
		panel_1.add(tasknameField);
		tasknameField.setColumns(10);
		
		JTextPane txtpnEmpId = new JTextPane();
		txtpnEmpId.setText("Emp ID");
		txtpnEmpId.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnEmpId.setEditable(false);
		txtpnEmpId.setBounds(44, 288, 124, 33);
		panel_1.add(txtpnEmpId);
		
		JTextField empidField = new JTextField();
		empidField.setToolTipText("3 digit Emp ID");
		empidField.setColumns(10);
		empidField.setBounds(199, 288, 50, 33);
		panel_1.add(empidField);
		
		JTextField taskidField = new JTextField();
		taskidField.setBounds(220, 23, 50, 42);
		panel_1.add(taskidField);
		taskidField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		panel_2.setBounds(0, 11, 732, 44);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JTextPane txtpnCreateTask = new JTextPane();
		txtpnCreateTask.setEditable(false);
		txtpnCreateTask.setBackground(new Color(192, 192, 192));
		txtpnCreateTask.setFont(new Font("Times New Roman", Font.BOLD, 27));
		txtpnCreateTask.setText("Insert Task ID to edit.");
		txtpnCreateTask.setBounds(200, 0, 346, 44);
		panel_2.add(txtpnCreateTask);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setBounds(32, 11, 89, 23);
		panel_2.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmeditTasks.dispose(); //closes the Task window
			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		
		JButton btnNewButton = new JButton("Edit Task");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

                try
                {
                	conn = EmployeeData.ConnectDB();
    		
    				int flag = 0;
					
					String taskID = taskidField.getText();
					String empID = empidField.getText();
					String taskName = tasknameField.getText();
					
								
					// input validation 
					
					//if(jtxtfname.getText().isEmpty() || jtxtlname.getText().isEmpty() || jtxtemail.getText().isEmpty() || jtxtsalary.getText().isEmpty() ||jtxtemployeeid.getText().isEmpty() || jtxtphonenumber.getText().isEmpty() || jtxtdepartment.getText().isEmpty() || jtxtposition.getText().isEmpty()){
					//	flag = 1;
					//}

					if(!isNumeric(empID) || empID.length() != 3) {
						flag = 1;
					}
					
					// if input is invalid
					if (flag == 1) {
						JOptionPane.showMessageDialog(null, "Incorrect Format");
						return;
					}
					
					// if radio curr button is selected, updates end date field
					//if (currButton.isSelected()) {
						//end = "CURR";
					//}
					
					// creates sql edit command based on given input
					String sql = "update tasks set EmpID='"+ empID +
							"', TaskName='"+ taskName +
							"' WHERE TaskID='"+ taskID +"'  ";
    				
					
                    pst = conn.prepareStatement(sql);
                    
                    // if record exists it updates it, if not it tells user employee does not exist
                    int i = pst.executeUpdate();
                    
                    if (i > 0) {
                    	frmeditTasks.dispose();
                    	JOptionPane.showMessageDialog(null, "Task Updated");
                    } else {
                    	JOptionPane.showMessageDialog(null, "Task does not exist");
                    }
                    
                    pst.close();
                    conn.close();
                } 
                catch(Exception ev)
                {
                    System.err.format(ev.getMessage());
                    JOptionPane.showMessageDialog(null, "Error");
                } 
                //loadList();
			}
		});
		btnNewButton.setBounds(158, 359, 89, 61);
		panel_1.add(btnNewButton);
		
	}
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
