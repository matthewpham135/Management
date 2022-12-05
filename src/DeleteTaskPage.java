

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class DeleteTaskPage
{
	public static int square;
	public static boolean trigger = false;
	//private JFrame frame;
	private JFrame frmDeleteTasks;
	private JTextField deletetaskField;
	Connection conn = null;
	PreparedStatement pst = null;
	/**
	 * Launch the application.
	 */
	public static void DeleteScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteTaskPage window = new DeleteTaskPage();
					window.frmDeleteTasks.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DeleteTaskPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeleteTasks = new JFrame();
		frmDeleteTasks.setBackground(UIManager.getColor("Button.background"));
		//frmDeleteTasks.setIconImage(Toolkit.getDefaultToolkit().getImage(TaskPage.class.getResource("/image/SnySys.png")));
		frmDeleteTasks.setTitle("Create Tasks");
		frmDeleteTasks.setBounds(100, 100, 748, 582);
		frmDeleteTasks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDeleteTasks.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 732, 543);
		frmDeleteTasks.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(55, 112, 639, 299);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane txtpnTaskName = new JTextPane();
		txtpnTaskName.setEditable(false);
		txtpnTaskName.setFont(new Font("Times New Roman", Font.BOLD, 22));
		txtpnTaskName.setText("\t\t        Enter Task ID to delete task.");
		txtpnTaskName.setBounds(0, 22, 639, 38);
		panel_1.add(txtpnTaskName);
		
		deletetaskField = new JTextField();
		deletetaskField.setBounds(218, 71, 211, 38);
		panel_1.add(deletetaskField);
		deletetaskField.setColumns(10);
		
		JButton btnNewButton = new JButton("Delete Task"); //deletes the task given the task ID #
		btnNewButton.setBounds(218, 149, 211, 107);
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// creates sql delete command
				conn = EmployeeData.ConnectDB();
				String sql = "DELETE FROM tasks WHERE TaskID = ?";
				
				
				try
				{
					// runs sql delete command 
					pst = conn.prepareStatement(sql);
					pst.setString(1, deletetaskField.getText());
					int i = pst.executeUpdate();
					// checks if given ID exists and if so deletes corresponding record
			        if (i > 0) 
			        {
			        	frmDeleteTasks.dispose();
			        	JOptionPane.showMessageDialog(null, "Task Successfully Deleted");
			        } 
			        else 
			        {
			        	JOptionPane.showMessageDialog(null, "Task does not exist");
			        }			    
				}
				catch (SQLException ed) {
		            System.out.println(ed.getMessage());
		        }
				finally
				{
					try
					{
						pst.close();
						conn.close();
					}
					catch(SQLException es)
					{
						es.printStackTrace();
					}
				}																
				
			}
				/*
				JOptionPane.showConfirmDialog(frmDeleteTasks,"Sure? You want to exit?", "Swing Tester", //shows confirmation
			               JOptionPane.YES_NO_OPTION,
			               JOptionPane.QUESTION_MESSAGE);
			}
			
			*/
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 35));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(139, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		panel_2.setBounds(0, 11, 732, 44);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JTextPane txtpnCreateTask = new JTextPane();
		txtpnCreateTask.setEditable(false);
		txtpnCreateTask.setBackground(new Color(192, 192, 192));
		txtpnCreateTask.setFont(new Font("Times New Roman", Font.BOLD, 27));
		txtpnCreateTask.setText("Delete Task");
		txtpnCreateTask.setBounds(292, 0, 150, 44);
		panel_2.add(txtpnCreateTask);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDeleteTasks.dispose(); //closes the Task window
			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setBounds(20, 11, 89, 23);
		panel_2.add(btnNewButton_1);
		
	}
	public static int getInt()
	{
		return square;
	}
	
}
