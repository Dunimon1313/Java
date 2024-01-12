/*
Name: Zachary Taylor
  Course: CNT 4714 Summer 2022 
  Assignment title: Project 2 – A Two-tier Client-Server Application 
  Date:  June 26, 2022 
 
  Class:  Enterprise Computing
*/

package banana;

//imports

import java.awt.BorderLayout;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.EventQueue;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.PopupFactory;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.sql.RowSet;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;


//application window
public class clientsideGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	
	Connection connection = null;
	Connection opOut = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientsideGUI frame = new clientsideGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public clientsideGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Connection Details");
		lblNewLabel.setBounds(15, 16, 311, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPropertiesFile = new JLabel("Properties File");
		lblPropertiesFile.setBounds(15, 43, 84, 15);
		lblPropertiesFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(15, 74, 56, 15);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(15, 98, 56, 15);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		
		textField = new JTextField();
		textField.setBounds(75, 72, 226, 20);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(75, 98, 226, 20);
		textField_1.setColumns(10);
		
		table = new JTable();
		table.setBounds(15, 238, 828, 247);
		
		JLabel lblEnterAnSql = new JLabel("Enter an SQL Command");
		lblEnterAnSql.setBounds(388, 16, 455, 19);
		lblEnterAnSql.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		textField_2 = new JTextField();
		textField_2.setBounds(388, 41, 449, 108);
		textField_2.setColumns(10);
		
		//button clears text in the command area
		JButton btnNewButton = new JButton("Clear SQL Command");
		btnNewButton.setBounds(547, 170, 133, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2.setText("");
			}
		});
		btnNewButton.setForeground(Color.RED);
		
		
		
		//this button is supposed to take the text from the command area, decide if the command is an update or a query,
		//use the correct statement to enact the query/update, and then return the results to the results area
		JButton btnNewButton_1 = new JButton("Execute SQL Command");
		btnNewButton_1.setBounds(698, 170, 145, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getRowCount() > 0) {
					while(table.getRowCount()>0) {
						((DefaultTableModel) table.getModel()).removeRow(0);
					}
				}

				String command = textField_2.getText();
				
				try {
					ResultSetTableModel myResult = new ResultSetTableModel(connection, command);
					if(!(command.startsWith("s") || command.startsWith("S"))) {
						myResult.setUpdate(command);
					}else {
							myResult.setQuery(command);
							table.setModel(myResult);
						}
				} catch (SQLException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		btnNewButton_1.setForeground(new Color(46, 139, 87));
		
		JTextPane txtpnNoConnectionNow = new JTextPane();
		txtpnNoConnectionNow.setBounds(15, 173, 373, 20);
		txtpnNoConnectionNow.setText("No Connection Now");
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(103, 41, 198, 20);
		
		
		//this button takes the state of a comboBox to determine what properties file to open, collects the text from the log in area,
		//opens the file, and checks the login text against the text expected in the properties files. if the credentials match, a
		//connection is attempted, and returns either a success or a failure to the connection status area
		JButton btnNewButton_2 = new JButton("Connect to Database");
		btnNewButton_2.setBounds(157, 129, 144, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			Properties properties = new Properties();
			FileInputStream filein = null;
			MysqlDataSource dataSource = null;
			
			public void actionPerformed(ActionEvent e) {
				String userName = textField.getText();
				String passWord = textField_1.getText();
				
				try {
					filein = new FileInputStream((String)comboBox.getSelectedItem());
					//load client user
					properties.load(filein);
					dataSource = new MysqlDataSource();
					if((userName.equals((String)properties.getProperty("MYSQL_DB_USERNAME"))  && (passWord.equals((String)properties.getProperty("MYSQL_DB_PASSWORD"))))) {
						dataSource.setUrl(properties.getProperty("MYSQL_DB_URL"));
						dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
						dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
						connection = dataSource.getConnection();
						if((dataSource.getURL())==null){
							txtpnNoConnectionNow.setText("No Connection Now");
						}else{
							txtpnNoConnectionNow.setText("Connected to "+ dataSource.getURL());
						}
					}else {
						txtpnNoConnectionNow.setText("Invalid User. Please try again.");
					}				
				}catch( SQLException el ) {
					el.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.setForeground(Color.BLUE);
		
		JLabel lblSqlExecutionResult = new JLabel("SQL Execution Result Window");
		lblSqlExecutionResult.setBounds(15, 213, 828, 19);
		lblSqlExecutionResult.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		
		
		
		JButton btnNewButton_3 = new JButton("Clear Result Window");
		btnNewButton_3.setBounds(15, 496, 133, 23);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
			}
		});
		btnNewButton_3.setForeground(new Color(238, 130, 238));
		
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"root.properties", "client.properties", "db3.properties", "db4.properties"}));
		
		
		txtpnNoConnectionNow.setForeground(new Color(255, 0, 0));
		txtpnNoConnectionNow.setBackground(new Color(0, 0, 0));
		contentPane.setLayout(null);
		contentPane.add(lblSqlExecutionResult);
		contentPane.add(btnNewButton_3);
		contentPane.add(lblNewLabel);
		contentPane.add(txtpnNoConnectionNow);
		contentPane.add(lblPropertiesFile);
		contentPane.add(comboBox);
		contentPane.add(lblUsername);
		contentPane.add(lblPassword);
		contentPane.add(textField);
		contentPane.add(textField_1);
		contentPane.add(btnNewButton_2);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_1);
		contentPane.add(textField_2);
		contentPane.add(lblEnterAnSql);
		contentPane.add(table);
	}
}
