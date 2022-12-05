import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class Employee {
	// Used to build the GUI
	private JFrame frame;
	private JTextField jtxtfname;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblNiNumber;
	private JTextField jtxtlname;
	private JLabel lblFirstname;
	private JTextField jtxtemail;
	private JLabel lblSurname;
	private JLabel lblGender;
	private JLabel lblDob;
	private JTextField jtxtsalary;
	private JLabel lblAge;
	private JTextField jtxtemployeeid;
	private JLabel lblSalray;
	private JTextField jtxtphonenumber;
	private JButton btnPrint;
	private JButton btnReset;
	private JButton btnExit;
	private JLabel lblNewLabel;
	private JTextField jtxtTaskID;
    private JTable tasksTable;
    private JScrollPane taskScroll;
    private static JTextField txtFileName;
    private static JTextField textField;
    
    private JTextField jtxtReqID;
    private JTable reqTable;
    private JScrollPane reqScroll;
    
    DefaultTableModel reqModel = new DefaultTableModel();
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel tasksModel = new DefaultTableModel();
	private JLabel lblNewLabel_1;
	private JTextField jtxtdepartment;
	private JTextField jtxtposition;
	
	private JComboBox jtxtsmonth;
	private JComboBox jtxtsday;
	private JComboBox jtxtsyear;
	
	private JComboBox jtxtemonth;
	private JComboBox jtxteday;
	private JComboBox jtxteyear;
	
	private JRadioButton currButton;
	
	private String dates[]
	        = { "", "1", "2", "3", "4", "5",
	            "6", "7", "8", "9", "10",
	            "11", "12", "13", "14", "15",
	            "16", "17", "18", "19", "20",
	            "21", "22", "23", "24", "25",
	            "26", "27", "28", "29", "30",
	            "31" };
    private String months[]
        = { "", "01", "02", "03", "04",
            "04", "05", "06", "07",
            "08", "09", "10", "11", "12" };
    private String years[]
        = { "", "1998",
            "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010",
            "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018",
            "2019", "2020", "2021", "2022" };
    private JComboBox jtxtbmonth;
    private JComboBox jtxtbday;
    private JComboBox jtxtbyear;
    private JLabel lblEx;
	
    
    // Functions used for input validation
    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
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
    
    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
          .matcher(emailAddress)
          .matches();
    }
	    
	// This class creates the login page 
    static class CreateLoginForm extends JFrame implements ActionListener
	{
	    // create username, password,, submit, and create account fields
	    JButton sub, ca;
	    JPanel newPanel;
	    JLabel userLabel, passLabel;
	    final JTextField  textField1, textField2;

	    //calling constructor
	    CreateLoginForm()
	    {

	        //create label for username
	        userLabel = new JLabel();
	        userLabel.setText("Username");      //set label value for textField1

	        //create text field to get username from the user
	        textField1 = new JTextField(15);    //set length of the text

	        //create label for password
	        passLabel = new JLabel();
	        passLabel.setText("Password");      //set label value for textField2

	        //create text field to get password from the user
	        textField2 = new JPasswordField(15);    //set length for the password

	        //create submit button
	        sub = new JButton("SUBMIT"); //set label to submit
	        ca = new JButton("CREATE ACCOUNT "); //set label to ca

	        //create panel to put form elements
	        newPanel = new JPanel(new GridLayout(3, 1));
	        newPanel.add(userLabel);    //set username label to panel
	        newPanel.add(textField1);   //set text field to panel
	        newPanel.add(passLabel);    //set password label to panel
	        newPanel.add(textField2);   //set text field to panel
	        newPanel.add(ca);           //set ca to panel
	        newPanel.add(sub);           //set submit to panel

	        //set border to panel
	        add(newPanel, BorderLayout.CENTER);

	        //perform action on button click
	        sub.addActionListener(this);     //add action listener to submit
	        ca.addActionListener(this);     //add action listener to create account
	        setTitle("LOGIN FORM");         //set title to the login form
	    }

	    // button checks if username and password are correct
	    // Cesar Saenz created login via a database
	    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
	    {
	        if (ae.getSource() == sub) {
	        	String userValue = textField1.getText();        //get user entered username from the textField1
		        String passValue = textField2.getText();        //get user entered password from the textField2
		        
		        //check if username and passwords match
		        try {
		        	// connects to the database
					Connection connect = EmployeeData.ConnectDB();
					String query = "select * from EmployeeUser";
					PreparedStatement pst = connect.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					String usern, passw, id;
					boolean userPassExists = false;
					
					// searches every user for a matching username and password
					while (rs.next()) {
						//gets username
						usern = rs.getString("username");
						//gets password
						passw = rs.getString("passw");
						//gets id
						id = rs.getString("id");
						
						if (userValue.equals(usern) && passValue.equals(passw)) {  
				            // if it matches it sends the user to the interface
							userPassExists = true;
				        	JOptionPane.showMessageDialog(null, "Login Successful");
				        	try {		
		    					query = "select Position from Employee where EmpID = " + id;
		    					pst = connect.prepareStatement(query);
		    					rs = pst.executeQuery();
		    					
		    					String pos = rs.getString("Position");
		    					
		    					//closes page
		    					dispose();
		    					
		    					// closes all connections
		    					pst.close();
		    					connect.close();
		    					if (rs != null) 
		    						rs.close();
		    					
		    					if (pos.equals("Manager")) {
		    						//manager page
		    						Employee window = new Employee();
			    					window.frame.setVisible(true);
		    					} else {
									EmployeeProfile window = new EmployeeProfile(id);
									window.initialize();
									window.frame.setVisible(true);
		    					}
		    				} catch (Exception e) {
		    					e.printStackTrace();
		    				}
				        }
						else {
		
						}
					}
					
					if (!userPassExists) {
						JOptionPane.showMessageDialog(null, "Please enter valid username and password");
					}
					
					// closes all connections
					pst.close();
					connect.close();
					if (rs != null) 
						rs.close();
			        
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
	        } else {
	        	//closes page
	        	dispose();
	        	// Opens the create account form
				Employee.CreateAccountForm account = new Employee.CreateAccountForm();
				account.setSize(900,200);  //set size of the frame
				account.setVisible(true);  //make form visible to the user		
	        }
	    }
	}
 // This class creates the create account page 
  	// This was created by Cesar Saenz
  	static class CreateAccountForm extends JFrame implements ActionListener
  	{
  		// create username, password,, submit, and create account fields
  		JButton sub, exit;
  		JPanel newPanel;
  		JLabel userLabel, passLabel1, passLabel2, empId, fName, lName;
  		final JTextField  textField1, textField2, textField3, textField4, textField5, textField6;

  		//calling constructor
  		CreateAccountForm()
  		{
  			//create label for username
  		    userLabel = new JLabel();
  		    userLabel.setText("Username");      //set label value for textField1
  	        //create text field to get username from the user
  	        textField1 = new JTextField(15);    //set length of the text

  	        //create label for password
  	        passLabel1 = new JLabel();
  	        passLabel1.setText("Password");      //set label value for textField2
  	        //create text field to get password from the user
  	        textField2 = new JPasswordField(15);    //set length for the password
  		        
  	        //create label for confirm password
  	        passLabel2 = new JLabel();
  	        passLabel2.setText("Confirm Password");      //set label value for textField3
  	        //create text field to get the confirmation from the user
  	        textField3 = new JPasswordField(15);    //set length for the confirmation
  	        
  	        //create label for employee id
  	        empId = new JLabel();
  	        empId.setText("Employee ID");      //set label value for textField4
  	        //create text field to get the id from the user
  	        textField4 = new JTextField(3);    //set length of the id
  	        
  	        //create label for employee first name
  	        fName = new JLabel();
  	        fName.setText("First Name");      //set label value for textField5
  	        //create text field to get the first name from the user
  	        textField5 = new JTextField(25);    //set length of the first name
  		        
  	        //create label for employee last name
  	        lName = new JLabel();
  	        lName.setText("Last Name");      //set label value for textField6
  	        //create text field to get the last name from the user
  	        textField6 = new JTextField(25);    //set length of the last name

  	        //create submit button
  	        sub = new JButton("SUBMIT"); //set label to submit
  	        exit = new JButton("EXIT"); //set label to exit

  	        //create panel to put form elements
  	        newPanel = new JPanel(new GridLayout(7, 1));
  	        newPanel.add(userLabel);    //set username label to panel
  	        newPanel.add(textField1);   //set text field to panel
  	        newPanel.add(passLabel1);    //set password label to panel
  	        newPanel.add(textField2);   //set text field to panel
  		        
  	        newPanel.add(passLabel2);    //set confirmation label to panel
  	        newPanel.add(textField3);   //set text field to panel
  	        newPanel.add(empId);    	//set id label to panel
  	        newPanel.add(textField4);   //set text field to panel
  		        
  	        newPanel.add(fName);    	//set first name label to panel
  	        newPanel.add(textField5);   //set text field to panel
  	        newPanel.add(lName);    	//set last name label to panel
  	        newPanel.add(textField6);   //set text field to panel

  	        newPanel.add(exit);           //set exit to panel
  	        newPanel.add(sub);           //set submit to panel

  	        //set border to panel
  	        add(newPanel, BorderLayout.CENTER);

  	        //perform action on button click
  	        sub.addActionListener(this);     //add action listener to submit
  	        exit.addActionListener(this);     //add action listener to exit
  	        setTitle("CREATE ACCOUNT FORM");         //set title to the create account form
  	    }

  	    // button checks if the inputted information matches
  	    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
  	    {
  	        if (ae.getSource() == sub) {
  	        	String userValue = textField1.getText();	//get user entered username from the textField1
  		        String passValue = textField2.getText();	//get user entered password from the textField2
  		        String conValue = textField3.getText();		//get user entered confirmation from the textField3
  		        String idValue = textField4.getText();		//get user entered id from the textField4
  		        String fnValue = textField5.getText();		//get user entered first name from the textField5
  		        String lnValue = textField6.getText();		//get user entered last name from the textField6
  			    
  		        //checks database for any important
  		        try {
  					
  		        	// connects to the database
  					Connection connect = EmployeeData.ConnectDB();
  					String query = "SELECT EmpID, Firstname, Lastname, username, passw\r\n"
 							+ "FROM employee\r\n"
 							+ "LEFT  JOIN employeeUser ON EmpID = id";
  					PreparedStatement pst = connect.prepareStatement(query);
  					ResultSet rs = pst.executeQuery();
 					
  					//Creates strings to compare
 					String usern, passw, conPass, id, firstN, lastN;
 					
 					//Creates boolean to print error if the id doesn't exist
 					boolean idExists = false;
 						
 					// checks every employee record for the right information
 					while (rs.next()) {
 						usern = rs.getString("username");
 						passw = rs.getString("passw");
 						id = rs.getString("EmpID");
 						firstN = rs.getString("Firstname");
 						lastN = rs.getString("Lastname");
 						
 						//checks if the inputted id matches
 						if (idValue.equals(id)) {
 							//id does exist
 							idExists = true;
 							//checks if the inputted first and last name match
 							if (fnValue.equals(firstN) && lnValue.equals(lastN)) {
 								//checks if there the employee already has a user
 								if (usern == null && passw == null) {
 									query = "SELECT username FROM employeeUser";
 									pst = connect.prepareStatement(query);
 									ResultSet rs2 = pst.executeQuery();
 									//Creates string to compare
 									String userN;
 									//Creates boolean to print error if the username exists
 				 					boolean usernameExists = false;
 									
 				 					// checks every user record for username
 									while (rs2.next()) {
 										userN = rs2.getString("username");
 				 						if (userN.equals(userValue)) {
 				 							//username does exist
 				 							usernameExists = true;
 				 							break;
 				 						}
 									}
 									//checks if the username is already used
 									if (!usernameExists) {
 										//checks if the confirm password is the same as the password
 										if (conValue.equals(passValue)) {
 											//creates new user
 											Statement statement = connect.createStatement();
 											statement.executeUpdate("INSERT INTO employeeUser VALUES ('" + id + "', '"
 											+ userValue + "', '" + passValue + "')");
 											//Shows success message
 											JOptionPane.showMessageDialog(null, "User Successfully Created");
 												
 											//closes page
 								        	dispose();
 									        	
 								        	// Opens the login form
 											Employee.CreateLoginForm form = new Employee.CreateLoginForm();
 											form.setSize(900,200);  //set size of the frame
 									        form.setVisible(true);  //make form visible to the user
 										} else {
 											//Shows confirmation password error message
 											JOptionPane.showMessageDialog(null, "Comfirm Password is different");
 										}
 									} else {
 										//Shows username exists error message
 										JOptionPane.showMessageDialog(null, "Username already used");
 									}
 								} else {
 									//Shows user exists error message
 									JOptionPane.showMessageDialog(null, "User already created");
 								}
 							} else {
 								//Shows wrong name message
 								JOptionPane.showMessageDialog(null, "Wrong first or last name");
 							}
 							break;
 						}
 					}
 					//shows id error message
 					if (!idExists) {
 						JOptionPane.showMessageDialog(null, "ID doesn't exist");
 					}
 					
 					// closes all connections
 					pst.close();
 					connect.close();
 					if (rs != null) 
 						rs.close();
 				        
 		        } catch (Exception e) {
 		        	e.printStackTrace();
 		        }
  	        } else {
  	        	//closes page
  	        	dispose();
  	        	// Opens the login form
  				Employee.CreateLoginForm form = new Employee.CreateLoginForm();
  				form.setSize(900,200);  //set size of the frame
  		        form.setVisible(true);  //make form visible to the user	
  	        }
  	    }
  	}
    
 	 public void loadReq() {
         try {
                 // connects to the database and creates a command to select the first record
                 conn = EmployeeData.ConnectDB();
                 String query = "select * from requests";
                 PreparedStatement pst = conn.prepareStatement(query);
                 ResultSet rs = pst.executeQuery();
                 DefaultTableModel reqModel = (DefaultTableModel) reqTable.getModel();


                 // resets the current table
                 while(reqModel.getRowCount() > 0)
                 {
                     reqModel.removeRow(0);
                 }

                 DefaultListModel DLM = new DefaultListModel();
                 // iterates through each record and adds it to the table GUI shown to the user
                 while(rs.next()) {
                     DLM.addElement(rs.getString("ReqID"));
                     reqModel.addRow(new Object[] {
                             rs.getString("ReqID"),
                             rs.getString("EmpID"),
                             rs.getString("ReqName"),
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Opens the login form
					
					Employee.CreateLoginForm form = new Employee.CreateLoginForm();
					form.setSize(900,200);  //set size of the frame
			        form.setVisible(true);  //make form visible to the user			
			        
					
					/*
					EmployeeProfile window = new EmployeeProfile();
					window.initialize();
					window.frame.setVisible(true);
					*/
			        /*
			        Employee window = new Employee();
					window.frame.setVisible(true);
					*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/*
	 loadList updates the table shown to the user based on the records currently stored in the sqlite database.
	 This function is called at the start of the program and after every add, edit, and delete command 
	 so that the table is always showing the most up to date database. 
	 */
	public void loadList() {
		try
		{
			// connects to the database and creates a command to select the first record
			conn = EmployeeData.ConnectDB();
			String query = "select * from Employee";
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
						rs.getString("Firstname"),
						rs.getString("Lastname"),
						rs.getString("Email"),
						rs.getString("Start"),
						rs.getString("End"),
						rs.getString("Salary"),
						rs.getString("EmpID"),
						rs.getString("PhoneNum"),
						rs.getString("Department"),
						rs.getString("Position"),
						rs.getString("DOB"),
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
	
	public void loadTasks() {
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
	
	public Employee() {
		// displays connection made when successfully logging in
		JOptionPane.showMessageDialog(null, "Connection Made");
		initialize();
		Object col[] = {"Firstname", "Lastname", "Email", "Start", "End", "Salary", "EmpID", "PhoneNum", "Department", "Position", "DOB"};
		model.setColumnIdentifiers(col);
		// loads current contents of the database
		loadList();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1400, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		jtxtfname = new JTextField();
		jtxtfname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtfname.setBounds(272, 59, 209, 28);
		frame.getContentPane().add(jtxtfname);
		jtxtfname.setColumns(10);
		
		
		
		// Add button 
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create sql command to add new record 
				String sql = "INSERT INTO employee(Firstname, Lastname, Email, Start, End, Salary, EmpID, PhoneNum, Department, Position, DOB)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				
				try
				{
					// connects to database
					conn = EmployeeData.ConnectDB();
					
					int flag = 0;
					
					String firstname = jtxtfname.getText();
					String lastname = jtxtlname.getText();
					String email = jtxtemail.getText();
					String start = (String)jtxtsmonth.getSelectedItem() + "/" + (String)jtxtsday.getSelectedItem() + "/" + (String)jtxtsyear.getSelectedItem();
					String end = (String)jtxtemonth.getSelectedItem() + "/" + (String)jtxteday.getSelectedItem() + "/" + (String)jtxteyear.getSelectedItem();
					String salary = jtxtsalary.getText();
					String ID = jtxtemployeeid.getText();
					String phone = jtxtphonenumber.getText();
					String department = jtxtdepartment.getText();
					String position = jtxtposition.getText();
					String DOB = (String)jtxtbmonth.getSelectedItem() + "/" + (String)jtxtbday.getSelectedItem() + "/" + (String)jtxtbyear.getSelectedItem();
					
								
					// input validation 
					if(jtxtfname.getText().isEmpty() || jtxtlname.getText().isEmpty() || jtxtemail.getText().isEmpty() || jtxtsalary.getText().isEmpty() ||jtxtemployeeid.getText().isEmpty() || jtxtphonenumber.getText().isEmpty() || jtxtdepartment.getText().isEmpty() || jtxtposition.getText().isEmpty()){
						flag = 1;
						System.out.print("empty");
					}/*
					else if(!isAlpha(firstname) || !isAlpha(lastname) || !isAlpha(department) || !isAlpha(position)) {
						flag = 1;
						System.out.print("names");
					}
					*/
					else if(!patternMatches(email)) {
						flag = 1;
						System.out.print("email");
					}
					else if((String)jtxtsmonth.getSelectedItem() == "" || (String)jtxtsday.getSelectedItem() == "" || (String)jtxtsyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("start");
					}
					else if((String)jtxtemonth.getSelectedItem() == "" || (String)jtxteday.getSelectedItem() == "" || (String)jtxteyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("end");
						if (currButton.isSelected()) {
							end = "CURR";
							flag = 0;
						}
					}
					else if(!isNumeric(salary)) {
						flag = 1;
						System.out.print("salary");
					}
					else if(!isNumeric(ID) || ID.length() != 3) {
						flag = 1;
						System.out.print("ID");
					}
					else if(!isNumeric(phone) || phone.length() != 10) {
						flag = 1;
						System.out.print("phone");
					}
					else if((String)jtxtbmonth.getSelectedItem() == "" || (String)jtxtbday.getSelectedItem() == "" || (String)jtxtbyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("DOB");
					}
					
					
					// if input is invalid it outputs incorrect format
					if (flag == 1) {
						JOptionPane.showMessageDialog(null, "Incorrect Format");
						return;
					}
					// used if current button is selected
					if (currButton.isSelected()) {
						end = "CURR";
					}
					
					// accesses database and adds new record 
					pst = conn.prepareStatement(sql);
					pst.setString(1,  jtxtfname.getText());
					pst.setString(2,  jtxtlname.getText());
					pst.setString(3,  jtxtemail.getText());
					pst.setString(4, start);
					pst.setString(5,  end);
					pst.setString(6,  jtxtsalary.getText());
					pst.setString(7,  jtxtemployeeid.getText());
					pst.setString(8,  jtxtphonenumber.getText());
					pst.setString(9,  jtxtdepartment.getText());
					pst.setString(10,  jtxtposition.getText());
					pst.setString(11,  DOB);
					pst.execute();
					
					pst.close();
					conn.close();
					JOptionPane.showMessageDialog(null, "Employee Record Successfully Added");
				}
				catch(Exception ev)
				{
					System.err.format(ev.getMessage());
					JOptionPane.showMessageDialog(null, "Error");
				}
				// re updates the table 
				loadList();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(272, 460, 214, 47);
		frame.getContentPane().add(btnNewButton);
		
		
		
		
		
		
		// allows the table to scroll
		scrollPane = new JScrollPane();
		scrollPane.setBounds(531, 53, 819, 639);
		frame.getContentPane().add(scrollPane);
		
		// creates table with all column headers
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Firstname", "Lastname", "Email", "Start", "End", "Salary", "EmpID", "PhoneNum","Department", "Position", "DOB"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		
		// print button
		btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try 
				{
					// accesses printer and prints contents of the table 
					table.print();
				}
				catch(java.awt.print.PrinterException ev)
				{
					System.err.format("No Printer Found", ev.getMessage());
				}
				
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnPrint.setBounds(272, 518, 214, 47);
		frame.getContentPane().add(btnPrint);
		
		
		
		
		// Reset button
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// clears every input field 
				jtxtfname.setText(null);
				jtxtlname.setText(null);
				jtxtemail.setText(null);
				jtxtsalary.setText(null);
				jtxtemployeeid.setText(null);
				jtxtphonenumber.setText(null);
				jtxtdepartment.setText(null);
				jtxtposition.setText(null);
				JOptionPane.showMessageDialog(null, "Form Data Reset");
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(272, 428, 209, 21);
		frame.getContentPane().add(btnReset);
		
		
		
		
		
		// exit button 
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(1160, 703, 214, 47);
		frame.getContentPane().add(btnExit);
		
		
		
		
		// delete button
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// creates sql delete command
				conn = EmployeeData.ConnectDB();
				String sql = "DELETE FROM employee WHERE EmpID = ?";
				
				
				// asks user to input Employee ID 
				String value = JOptionPane.showInputDialog(null, "Enter Employee ID to Delete Record");
				
				
				// input validation 
				if (value.length() != 3) {
					JOptionPane.showMessageDialog(null, "Incorrect Format");
					return;
				}
				
				try
				{
					// runs sql delete command 
					pst = conn.prepareStatement(sql);
					pst.setString(1, value);
					int i = pst.executeUpdate();
					// checks if given ID exists and if so deletes corresponding record
			        if (i > 0) {
			        	JOptionPane.showMessageDialog(null, "Employee Record Successfully Deleted");
			        } else {
			        	JOptionPane.showMessageDialog(null, "Employee does not exist");
			        }
			        
				}
				catch (SQLException ed) {
		            System.out.println(ed.getMessage());
		        }
				finally
				{
					try
					{
						loadList();
						pst.close();
						conn.close();
					}
					catch(SQLException es)
					{
						es.printStackTrace();
					}
				}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnDelete.setBounds(20, 518, 214, 47);
		frame.getContentPane().add(btnDelete);
		
		
		
		
		// edit button
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
                try
                {
                	conn = EmployeeData.ConnectDB();
    		
    				int flag = 0;
					
					String firstname = jtxtfname.getText();
					String lastname = jtxtlname.getText();
					String email = jtxtemail.getText();
					String start = (String)jtxtsmonth.getSelectedItem() + "/" + (String)jtxtsday.getSelectedItem() + "/" + (String)jtxtsyear.getSelectedItem();
					String end = (String)jtxtemonth.getSelectedItem() + "/" + (String)jtxteday.getSelectedItem() + "/" + (String)jtxteyear.getSelectedItem();
					String salary = jtxtsalary.getText();
					String ID = jtxtemployeeid.getText();
					String phone = jtxtphonenumber.getText();
					String department = jtxtdepartment.getText();
					String position = jtxtposition.getText();
					String DOB = (String)jtxtbmonth.getSelectedItem() + "/" + (String)jtxtbday.getSelectedItem() + "/" + (String)jtxtbyear.getSelectedItem();
					
								
					// input validation
					if(jtxtfname.getText().isEmpty() || jtxtlname.getText().isEmpty() || jtxtemail.getText().isEmpty() || jtxtsalary.getText().isEmpty() ||jtxtemployeeid.getText().isEmpty() || jtxtphonenumber.getText().isEmpty() || jtxtdepartment.getText().isEmpty() || jtxtposition.getText().isEmpty()){
						flag = 1;
					}
					/*
					else if(!isAlpha(firstname) || !isAlpha(lastname) || !isAlpha(department) || !isAlpha(position)) {
						flag = 1;
					}
					*/
					else if(!patternMatches(email)) {
						flag = 1;
						System.out.print("email");
					}
					else if((String)jtxtsmonth.getSelectedItem() == "" || (String)jtxtsday.getSelectedItem() == "" || (String)jtxtsyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("start");
					}
					else if((String)jtxtemonth.getSelectedItem() == "" || (String)jtxteday.getSelectedItem() == "" || (String)jtxteyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("end");
						if (currButton.isSelected()) {
							end = "CURR";
							flag = 0;
						}
					}
					else if(!isNumeric(salary)) {
						flag = 1;
						System.out.print("salary");
					}
					else if(!isNumeric(ID) || ID.length() != 3) {
						flag = 1;
						System.out.print("id");
					}
					else if(!isNumeric(phone) || phone.length() != 10) {
						flag = 1;
						System.out.print("phone");
					}
					else if((String)jtxtbmonth.getSelectedItem() == "" || (String)jtxtbday.getSelectedItem() == "" || (String)jtxtbyear.getSelectedItem() == "") {
						flag = 1;
						System.out.print("dob");
					}
					
					// if input is invalid
					if (flag == 1) {
						JOptionPane.showMessageDialog(null, "Incorrect Format");
						return;
					}
					// if radio curr button is selected, updates end date field
					if (currButton.isSelected()) {
						end = "CURR";
					}
					
					// creates sql edit command based on given input
					String sql = "update employee set Firstname='"+ firstname +
							"' , Lastname='"+ lastname +
							"', Email='"+ email +
							"', Start='"+ start +
							"', End='"+ end +
							"', Salary='"+ salary +
							"', EmpID='"+ ID +
							"', PhoneNum='"+ phone +
							"', Department='"+ department +
							"', Position='"+ position +
							"', DOB='"+ DOB +
							"' WHERE EmpID='"+ ID +"'  ";
    				
					
                    pst = conn.prepareStatement(sql);
                    
                    // if record exists it updates it, if not it tells user employee does not exist
                    int i = pst.executeUpdate();
                    
                    if (i > 0) {
                    	JOptionPane.showMessageDialog(null, "Record Updated");
                    } else {
                    	JOptionPane.showMessageDialog(null, "Employee does not exist");
                    }
                    
                    pst.close();
                    conn.close();
                } 
                catch(Exception ev)
                {
                    System.err.format(ev.getMessage());
                    JOptionPane.showMessageDialog(null, "Error");
                } 
                loadList();
			}
		});
		editButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		editButton.setBounds(20, 460, 214, 47);
		frame.getContentPane().add(editButton);
		
		
		// generates labels and text fields
		lblNiNumber = new JLabel("Last Name");
		lblNiNumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNiNumber.setBounds(94, 98, 164, 14);
		frame.getContentPane().add(lblNiNumber);
		
		jtxtlname = new JTextField();
		jtxtlname.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtlname.setColumns(10);
		jtxtlname.setBounds(272, 91, 209, 28);
		frame.getContentPane().add(jtxtlname);
		
		lblFirstname = new JLabel("Email");
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFirstname.setBounds(94, 130, 164, 14);
		frame.getContentPane().add(lblFirstname);
		
		jtxtemail = new JTextField();
		jtxtemail.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtemail.setColumns(10);
		jtxtemail.setBounds(272, 123, 209, 28);
		frame.getContentPane().add(jtxtemail);
		
		lblSurname = new JLabel("Start Date");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSurname.setBounds(94, 162, 164, 14);
		frame.getContentPane().add(lblSurname);
		
		lblGender = new JLabel("End Date");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblGender.setBounds(94, 194, 164, 14);
		frame.getContentPane().add(lblGender);
		
		lblDob = new JLabel("Salary");
		lblDob.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDob.setBounds(94, 223, 164, 21);
		frame.getContentPane().add(lblDob);
		
		jtxtsalary = new JTextField();
		jtxtsalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtsalary.setColumns(10);
		jtxtsalary.setBounds(272, 219, 209, 28);
		frame.getContentPane().add(jtxtsalary);
		
		lblAge = new JLabel("Employee ID");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAge.setBounds(94, 255, 164, 21);
		frame.getContentPane().add(lblAge);
		
		jtxtemployeeid = new JTextField();
		jtxtemployeeid.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtemployeeid.setColumns(10);
		jtxtemployeeid.setBounds(272, 251, 108, 28);
		frame.getContentPane().add(jtxtemployeeid);
		
		lblSalray = new JLabel("Phone Number");
		lblSalray.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSalray.setBounds(94, 284, 164, 28);
		frame.getContentPane().add(lblSalray);
		
		jtxtphonenumber = new JTextField();
		jtxtphonenumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtphonenumber.setColumns(10);
		jtxtphonenumber.setBounds(272, 284, 209, 28);
		frame.getContentPane().add(jtxtphonenumber);
		
		lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(94, 66, 164, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Employee Database Management System");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(388, 11, 748, 31);
		frame.getContentPane().add(lblNewLabel_1);
		

		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDepartment.setBounds(94, 316, 164, 28);
		frame.getContentPane().add(lblDepartment);
		
		JLabel lblPosition = new JLabel("Position");
		lblPosition.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPosition.setBounds(94, 352, 164, 28);
		frame.getContentPane().add(lblPosition);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDateOfBirth.setBounds(94, 391, 164, 28);
		frame.getContentPane().add(lblDateOfBirth);
		
		jtxtdepartment = new JTextField();
		jtxtdepartment.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtdepartment.setColumns(10);
		jtxtdepartment.setBounds(272, 316, 209, 28);
		frame.getContentPane().add(jtxtdepartment);
		
		jtxtposition = new JTextField();
		jtxtposition.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtxtposition.setColumns(10);
		jtxtposition.setBounds(272, 352, 209, 28);
		frame.getContentPane().add(jtxtposition);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 53, 511, 2);
		frame.getContentPane().add(separator_1);
		
		jtxtsmonth = new JComboBox(months);
		jtxtsmonth.setBounds(272, 161, 58, 22);
		frame.getContentPane().add(jtxtsmonth);
		
		jtxtsday = new JComboBox(dates);
		jtxtsday.setBounds(340, 161, 40, 22);
		frame.getContentPane().add(jtxtsday);
		
		jtxtsyear = new JComboBox(years);
		jtxtsyear.setBounds(388, 162, 58, 22);
		frame.getContentPane().add(jtxtsyear);
		
		jtxtemonth = new JComboBox(months);
		jtxtemonth.setBounds(272, 193, 58, 22);
		frame.getContentPane().add(jtxtemonth);
		
		jtxteday = new JComboBox(dates);
		jtxteday.setBounds(340, 193, 40, 22);
		frame.getContentPane().add(jtxteday);
		
		jtxteyear = new JComboBox(years);
		jtxteyear.setBounds(388, 193, 58, 22);
		frame.getContentPane().add(jtxteyear);
		
		currButton = new JRadioButton("CURRENT");
		currButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		currButton.setBounds(452, 193, 73, 23);
		frame.getContentPane().add(currButton);
		
		jtxtbmonth = new JComboBox(months);
		jtxtbmonth.setBounds(272, 391, 58, 22);
		frame.getContentPane().add(jtxtbmonth);
		
		jtxtbday = new JComboBox(dates);
		jtxtbday.setBounds(340, 391, 40, 22);
		frame.getContentPane().add(jtxtbday);
		
		jtxtbyear = new JComboBox(years);
		jtxtbyear.setBounds(388, 392, 58, 22);
		frame.getContentPane().add(jtxtbyear);
		
		lblEx = new JLabel("*3 digits");
		lblEx.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEx.setBounds(391, 251, 90, 28);
		frame.getContentPane().add(lblEx);
		
       JButton btnTasks = new JButton("Tasks");
		 btnTasks.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 
				 try {
					 JFrame tasksFrame = new JFrame("Tasks");
					 tasksFrame.setVisible(true);
					 tasksFrame.setSize(1400,800);
					 tasksFrame.getContentPane().setLayout(null);
					 
					 
					 Object col[] = {"TaskID", "EmpID", "TaskName"};
					 tasksModel.setColumnIdentifiers(col);
						// loads current contents of the database

					// allows the table to scroll
					taskScroll = new JScrollPane();
					taskScroll.setBounds(531, 53, 819, 639);
					tasksFrame.getContentPane().add(taskScroll);
					
					// creates table with all column headers
					tasksTable = new JTable();
					tasksTable.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"TaskID", "EmpID", "TaskName"
						}
					));
					tasksTable.setFont(new Font("Tahoma", Font.BOLD, 14));
					taskScroll.setViewportView(tasksTable);
					 
					 // Edit Tasks Button
					 JButton editTasks = new JButton("Edit Tasks");
					 editTasks.addActionListener(new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							 
							 try {
								 EditTask et = new EditTask();
								 et.editScreen();
							 }
							 catch(Exception ev)
							 {
								 System.err.format(ev.getMessage());
								 JOptionPane.showMessageDialog(null, "Error");
							 }
						 }
					 });
					 
					 editTasks.setFont(new Font("Tahoma", Font.BOLD, 18));
					 editTasks.setBounds(50, 50, 150, 100);
					 tasksFrame.getContentPane().add(editTasks);
					 
					 // Create Tasks Button
					 JButton createTasks = new JButton("Create Tasks");
					 createTasks.addActionListener(new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							 
							 try {
								TaskPage nw = new TaskPage(); //goes to TaskPage.java
								nw.TaskScreen();
							 }
							 catch(Exception ev)
							 {
								 System.err.format(ev.getMessage());
								 JOptionPane.showMessageDialog(null, "Error");
							 }
						 }
					 });

					 
					 createTasks.setFont(new Font("Tahoma", Font.BOLD, 18));
					 createTasks.setBounds(50, 160, 150, 100);
					 tasksFrame.getContentPane().add(createTasks);
					 
					 
					// Delete Tasks Button
					 JButton deleteTasks = new JButton("Delete Tasks");
					 deleteTasks.addActionListener(new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							 
							 try {
								DeleteTaskPage dtp = new DeleteTaskPage(); //goes to DeleteTaskPage.java
								dtp.DeleteScreen();
							 }
							 catch(Exception ev)
							 {
								 System.err.format(ev.getMessage());
								 JOptionPane.showMessageDialog(null, "Error");
							 }
						 }
					 });

					 
					 deleteTasks.setFont(new Font("Tahoma", Font.BOLD, 18));
					 deleteTasks.setBounds(50, 270, 150, 100);
					 tasksFrame.getContentPane().add(deleteTasks);
					 
					//refresh button
					 JButton refreshButton = new JButton("Refresh");
					 refreshButton.addActionListener(new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							 loadTasks();
						 }
					 });

					 
					 refreshButton.setFont(new Font("Tahoma", Font.BOLD, 18));
					 refreshButton.setBounds(350, 50, 150, 100);
					 tasksFrame.getContentPane().add(refreshButton);

					 
					 
					 
					 loadTasks();
				 }
				 catch(Exception ev)
				 {
					 System.err.format(ev.getMessage());
					 JOptionPane.showMessageDialog(null, "Error");
				 }
			 }
		 });
		 btnTasks.setFont(new Font("Tahoma", Font.BOLD, 18));
		 btnTasks.setBounds(20, 576, 214, 47);
		 frame.getContentPane().add(btnTasks);
       
		 JButton btnRequests = new JButton("Requests");
			btnRequests.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							 
						try {
							ManagerRequests m = new ManagerRequests();
							m.requestScreen();
						}
						catch(Exception ev)
						{
							System.err.format(ev.getMessage());
							JOptionPane.showMessageDialog(null, "Error");
						}
					}
			});
			btnRequests.setFont(new Font("Tahoma", Font.BOLD, 18));
			btnRequests.setBounds(272, 576, 214, 47);
			frame.getContentPane().add(btnRequests);
			
			
			JButton documents = new JButton("Documents");
			documents.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			                try
			                {
			                    docs();
			                } 
			                catch(Exception ev)
			                {
			                    System.err.format(ev.getMessage());
			                    JOptionPane.showMessageDialog(null, "Documents Page Couldn't be Opened.");
			                } 
			    }
			});
			documents.setFont(new Font("Tahoma", Font.BOLD, 18));
			documents.setBounds(20, 634, 214, 47);
			frame.getContentPane().add(documents);
			
			JButton btnPerformanceReviews = new JButton("Performances");
			btnPerformanceReviews.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
                    try {
                        EmployeePerformanceReview p = new EmployeePerformanceReview();
                        p.prefRev();
                    }
                    catch(Exception ev){
                        System.err.format(ev.getMessage());
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }
			});
			btnPerformanceReviews.setFont(new Font("Tahoma", Font.BOLD, 18));
			btnPerformanceReviews.setBounds(272, 634, 214, 47);
			frame.getContentPane().add(btnPerformanceReviews);
			
		
	}


private static void docs()
{
	JFrame jFrame = new JFrame("documents");
    jFrame.getContentPane().setLayout(null);//new FlowLayout());
    jFrame.setBounds(0, 0, 589, 400);
    jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    //////////////////////////////////////////////////////////
    ///////////////////// Buttons Below  /////////////////////
    //////////////////////////////////////////////////////////
    
    
	/////////////////////// Page Label ///////////////////////
	JLabel pageLabel = new JLabel("DOCUMENTS");
	pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	pageLabel.setBounds(57, 30, 115, 34);
	jFrame.getContentPane().add(pageLabel);
	
	
	//File name label
	JLabel fileLabel = new JLabel("File Name");
	fileLabel.setHorizontalAlignment(SwingConstants.LEFT);
	fileLabel.setBounds(271, 53, 73, 20);
	jFrame.getContentPane().add(fileLabel);
			
	//Small Text Field
	txtFileName = new JTextField("Lorem Generated");
	txtFileName.setBounds(271, 72, 275, 20);
	jFrame.getContentPane().add(txtFileName);
	txtFileName.setColumns(10);
			
	
	//File content label
	JLabel contLabel = new JLabel("File Content");
	contLabel.setHorizontalAlignment(SwingConstants.LEFT);
	contLabel.setBounds(271, 103, 73, 20);
	jFrame.getContentPane().add(contLabel);
			
	//Big Text Field
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(271, 120, 275, 168);
	JTextArea textArea = new JTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
	textArea.setLineWrap(true);
	textArea.setBounds(271, 120, 275, 168);
	jFrame.getContentPane().add(textArea);		
	jFrame.getContentPane().add(scrollPane);
	scrollPane.setViewportView(textArea);

	
	//Status label
	JLabel stat = new JLabel("Operation Status");
	stat.setHorizontalAlignment(SwingConstants.LEFT);
	stat.setBounds(271, 291, 115, 20);
	jFrame.getContentPane().add(stat);
	
	//Status text field
	textField = new JTextField("...");
	textField.setColumns(10);
	textField.setBounds(271, 309, 275, 20);
	jFrame.getContentPane().add(textField);
    
    
    /////////////////// Create New Button ///////////////////
    JButton newDoc = new JButton("Add/Edit Document");
	newDoc.addActionListener(
    new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		
    		String status = "Operation \"Create New\" failed.";        		
    		
    		try {
    			//Retrieve file name from text
    			String name = txtFileName.getText();
    			int count = 0;
    			
    			for(int i = 0; i <name.length(); i++)
    			{
    				count++;
    			}        			
    			if(count == 0)
    			{
    				status = "File name cannot be null!";
    			}
    			else
    			{
    				String filename;
        			
        			if (name.contains(".txt"))
        			{
        				filename = name;
        			}
        			else
        			{
        				filename = name + ".txt";
        			}
        			        			
        			//Create new file using given file name
        			FileWriter dir = new FileWriter(filename);
                    BufferedWriter buffer = new BufferedWriter(dir);
                    
                    //Write to the file from what's in the textbox
                    buffer.write(textArea.getText());
                    buffer.newLine();
                    buffer.close();
                    
                    //Success message
                    status = filename + " successfully created.";
                    
                    buffer.close();
    			}
    			
    			//Reflect status message
                textField.setText(status);
 
            } catch (Exception z) {
            	
                System.err.format(status);
            }

        }
    });
	newDoc.setFont(new Font("Tahoma", Font.BOLD, 18));
	newDoc.setBounds(10, 90, 214, 47);
	jFrame.getContentPane().add(newDoc);

	
	/////////////////// Open Doc Button ///////////////////
	JButton openDoc = new JButton("Open Document");
	openDoc.addActionListener(
	  new ActionListener(){
	  	public void actionPerformed(ActionEvent e){
	  		
	  		String status = "Operation \"Edit\" failed.";
	  		
	  		try
	  		{     	  			
	  			//Retrieve file name from text
    			String name = txtFileName.getText();
    			int count = 0;
    			
    			for(int i = 0; i <name.length(); i++)
    			{
    				count++;
    			}        			
    			if(count == 0)
    			{
    				status = "File name cannot be null!";
    			}
    			else
    			{

    				String filename;
        			if (name.contains(".txt"))
        			{
        				filename = name;
        			}
        			else
        			{
        				filename = name + ".txt";
        			}
        			        			
        			String dir = System.getProperty("user.dir");
        			String dirFull = dir + "/" + filename;      	
        			
        			//Create new file using given file name
        			BufferedReader buffer = new BufferedReader(new FileReader(dirFull));
                    
                    //Read in text from file
                    String st;
                    String all = "";
                    
                    while ((st = buffer.readLine()) != null)
                    {
                    	all = all + "\n" + st;
                    }         
                    
                    //Show text from file in textbox
                    textArea.setText(all);
                    
                    //Update status
                    status = filename + " successfully opened.";
                    textField.setText(status);
                    buffer.close();
    			}
    			
    			textField.setText(status);
                
            } catch (Exception z) {
            	
                System.err.format(status);
            }
	
	      }
	  });
	openDoc.setFont(new Font("Tahoma", Font.BOLD, 18));
	openDoc.setBounds(10, 148, 214, 47);
	jFrame.getContentPane().add(openDoc);
	
	
	/////////////////// Delete Doc Button ///////////////////
	JButton delDoc = new JButton("Delete Document");
	delDoc.addActionListener(
        new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		
        		String status = "Operation \"Delete\" failed.";
        		String empty = "";
        		
        		try
        		{
        			//Retrieve file name from text
        			String name = txtFileName.getText();
        			int count = 0;
        			
        			for(int i = 0; i <name.length(); i++)
        			{
        				count++;
        			}        			
        			if(count == 0)
        			{
        				status = "File name cannot be null!";
        			}
        			else
        			{
        				String filename;
	        			if (name.contains(".txt"))
	        			{
	        				filename = name;
	        			}
	        			else
	        			{
	        				filename = name + ".txt";
	        			}
	        			
	        			//Delete file
	        			File file = new File(filename);
	        			
	        			if (file.delete())
	        			{
	        				status = filename + " successfully deleted.";
	        			}
	        			else
	        			{
	        				file.delete();
	        				status = filename + " successfully deleted.  NOT!";
	        			}
	        			
	        			//Empty textarea just because
	        			textArea.setText(empty);
	        			
	        			//Reflect status message
	                    textField.setText(status);
        			}
        				        			
        			//Reflect status message
                    textField.setText(status);
        			
                } catch (Exception z) {
                    // TODO Auto-generated catch block
                    System.err.println(status);
                }

            }
        });
	delDoc.setFont(new Font("Tahoma", Font.BOLD, 18));
	delDoc.setBounds(10, 206, 214, 47);
	jFrame.getContentPane().add(delDoc);
	
	
	/////////////////// Show Directory Button ///////////////////
	JButton showDir = new JButton("Show Files");
	showDir.addActionListener(
        new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		
        		String status = "Operation \"Show Files\" failed.";
        		
        		try
        		{
        			
        			Path curr = Paths.get("");
        			String directory = curr.toAbsolutePath().toString();
        			File dirPath = new File(directory);
        			
        			//List files in directory
        			String allFiles[] = dirPath.list();
        			String listed = "";
        			for (int i = 0; i < allFiles.length; i++)
        			{
        				if (allFiles[i].contains(".txt"))
        				{
        					listed = listed + allFiles[i] + "\n";
        				}
        				
        			}
        			if(listed == "")
        			{
        				textArea.setText("No text files in directory.");
        			}
        			else
        			{
        				textArea.setText(listed);
        			}
        			
        			//Set file name to empty just because
        			txtFileName.setText("");
        			
        			//Have status reflect success
        			status = "Showing text files in directory...";
        			
        			textField.setText(status);
        			
                } catch (Exception z) {
                    System.err.println(status);
                }

            }
        });
	showDir.setFont(new Font("Tahoma", Font.BOLD, 18));
	showDir.setBounds(10, 264, 214, 47);
	jFrame.getContentPane().add(showDir);
	
	JLabel lblNewLabel = new JLabel("Fill in the text fields");
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setBounds(10, 56, 214, 20);
	jFrame.getContentPane().add(lblNewLabel);
	
	JLabel lblNewLabel_1 = new JLabel("before clicking a button");
	lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel_1.setBounds(10, 75, 214, 14);
	jFrame.getContentPane().add(lblNewLabel_1);
	
	JLabel pageLabel_1 = new JLabel("______________");
	pageLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	pageLabel_1.setBounds(57, 35, 115, 34);
	jFrame.getContentPane().add(pageLabel_1);
	
	
	
	
	//
    jFrame.setVisible(true);
}
}
