import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gestionetudiant {

	public JFrame frmGestionEtudiantSigl;
	public JTextField textField__1;
	public JTextField textField_1__1;
	public JTextField textField_2__1;
	public JTextField textField_3__1;
	public JTextField textField_4__1;
	public JTable table_1;
	public JTextField textField_5__1;
	public JTextField txtGestionDesEtudiant;
	public JButton btnajouteretudant;
	JButton btnSauvegarder;
	JButton btnSauvegarder_1;
	
	private Connection cnx;
	private Statement st,st1,st3,st4,st5,st6,st7,st8,st11,st12,st13,st14,st15;
	private ResultSet rst,rst1;
	private String query,query3,query4,query5,query6,query7,query8,query11,query12,query13,query14,query15;
	static String title1[] = {"ID","NOM","PRENOM","CIN","DIPLOME","EMAIL"};
	static Object[][] ObjetRc = {} ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * Connection to DataBase 
	 * ajouterE
	 * rechercheE
	 * rechercheEtudiant
	 * modifierE
	 * supprimerE
	 */
//	========================================================== Connection to DATA BASE ========================================================
	public static  Connection connecterDB() throws Exception
	{
			try
			{
			    Class.forName("com.mysql.jdbc.Driver");
			    String url = "jdbc:mysql://localhost/miniJava";
			    String user = "root";
			    String passwd = "";
			    Connection cnx = DriverManager.getConnection(url, user, passwd);
			    return cnx;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return null;
			}
	}
	
	
//	========================================================== Informations des etudiants ======================================================
	public void ajouterE(String nomE,String prenomE,String CodeE,String Diplome,String Email) throws Exception
	{
		try
		{
	        query = "INSERT INTO etudiant(nom,prenom,cne,diplome,email)VALUES('"+nomE+"','"+prenomE+"','"+CodeE+"','"+Diplome+"','"+Email+"')";
	        
	        query3 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '1')";
	        query4 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '2')";
	        query5 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '3')";
	        query6 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '4')";
	        query7 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '5')";
	        query8 = "INSERT INTO note (codeE,codeM) VALUES ('"+CodeE+"', '6')";
	        
	        for (int i = 1; i < 7; i++) {
	        	query11 = "INSERT INTO absense(cne,codeM,Seance) VALUES ('"+CodeE+"',"+i+",1)";
	        	query12 = "INSERT INTO absense(cne,codeM,Seance) VALUES ('"+CodeE+"',"+i+",2)";
	        	query13 = "INSERT INTO absense(cne,codeM,Seance) VALUES ('"+CodeE+"',"+i+",3)";
	        	query14 = "INSERT INTO absense(cne,codeM,Seance) VALUES ('"+CodeE+"',"+i+",4)";
	        	query15 = "INSERT INTO absense(cne,codeM,Seance) VALUES ('"+CodeE+"',"+i+",5)";
	        	
				st11 = cnx.createStatement();
				st12 = cnx.createStatement();
				st13 = cnx.createStatement();
				st14 = cnx.createStatement();
				st15 = cnx.createStatement();
				
				st11.executeUpdate(query11);
				st12.executeUpdate(query12);
				st13.executeUpdate(query13);
				st14.executeUpdate(query14);
				st15.executeUpdate(query15);
			}
	        
			cnx=connecterDB();
			st = cnx.createStatement();
			st3 = cnx.createStatement();
			st4 = cnx.createStatement();
			st5 = cnx.createStatement();
			st6 = cnx.createStatement();
			st7 = cnx.createStatement();
			st8 = cnx.createStatement();
			
			st.executeUpdate(query);
			st3.executeUpdate(query3);
			st4.executeUpdate(query4);
			st5.executeUpdate(query5);
			st6.executeUpdate(query6);
			st7.executeUpdate(query7);
			st8.executeUpdate(query8);
			
			JOptionPane.showMessageDialog(null, "votre etudiant est bien ajouter.");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	public Object[][] rechercheE() throws Exception
	{
		try
		{
			cnx=connecterDB();
			query ="select * from etudiant ORDER BY idE ASC";
			st = cnx.createStatement();
			st1 = cnx.createStatement();
			rst = st.executeQuery(query);
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant ");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][6];
			int i = 0;
			while(rst.next())
			{
			    System.out.println();
			    data[i][0] = rst.getInt("idE");
			    data[i][1] = rst.getString("nom");
			    data[i][2] = rst.getString("prenom");
			    data[i][3] = rst.getString("cne");
			    data[i][4] = rst.getString("diplome");
			    data[i][5] = rst.getString("email");
			    i++;
			}
			JTable table1 = new JTable(data,title1);
			table_1.setModel(table1.getModel());
		    return data;
		}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
				return null;
		}
	}

	public Object[][] rechercheEtudiant(String codeE) throws Exception
	{
		try {
			cnx=connecterDB();
			query ="select * from etudiant where cne = '"+ codeE+"'";
			st = cnx.createStatement();
			st1 = cnx.createStatement();
			rst = st.executeQuery(query);
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant where cne = '"+codeE+"'");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][6];
			int i = 0;
			
			if (nbLignes>0) {
				while(rst.next())
				{
				    System.out.println();
				    data[i][0] = rst.getInt("idE");
				    data[i][1] = rst.getString("nom");
				    data[i][2] = rst.getString("prenom");
				    data[i][3] = rst.getString("cne");
				    data[i][4] = rst.getString("diplome");
				    data[i][5] = rst.getString("email");
				    i++;
				}
				JTable table1 = new JTable(data,title1);
				table_1.setModel(table1.getModel());
			} else {
				JOptionPane.showMessageDialog(null, "Aucun étudiant trouver.");
			}

		    return data;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
			return null;
		}
	}

	public void modifierE(String nomE,String prenomE,String CodeE,String Diplome,String Email) throws Exception
	{
		try{
	        query = "UPDATE etudiant SET nom='"+nomE+"',prenom='"+prenomE+"',email = '"+Email+"',diplome = '"+Diplome+"' where cne='"+CodeE+"'";
			cnx=connecterDB();
			st = cnx.createStatement();
	        st.executeUpdate(query);
	        JOptionPane.showMessageDialog(null, "votre etudiant est bien modifier.");
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	public void supprimerE(String CodeE) throws Exception
	{
		try{
			query = "DELETE FROM etudiant where cne ='"+CodeE+"'";
			cnx=connecterDB();
			st = cnx.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "votre etudiant est bien supprimer.");
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public gestionetudiant() {
		table_1 = new JTable(ObjetRc,title1);
		
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		frmGestionEtudiantSigl = new JFrame();
		frmGestionEtudiantSigl.setTitle("Gestion Etudiant SIGL 2018/2019");
		frmGestionEtudiantSigl.setResizable(false);
		frmGestionEtudiantSigl.setBackground(Color.WHITE);
		frmGestionEtudiantSigl.setBounds(100, 100, 863, 482);
		frmGestionEtudiantSigl.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmGestionEtudiantSigl.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[784px]", "[47.00px][292.00px][86.00px]"));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(Color.WHITE);
		panel.add(panel_1_1, "cell 0 0,grow");
		panel_1_1.setLayout(new MigLayout("", "[784.00px]", "[20px]"));
		
		txtGestionDesEtudiant = new JTextField();
		txtGestionDesEtudiant.setForeground(Color.WHITE);
		txtGestionDesEtudiant.setHorizontalAlignment(SwingConstants.CENTER);
		txtGestionDesEtudiant.setBackground(Color.BLUE);
		txtGestionDesEtudiant.setEditable(false);
		txtGestionDesEtudiant.setText("GESTION DES ETUDIANTS");
		panel_1_1.add(txtGestionDesEtudiant, "cell 0 0,growx,aligny top");
		txtGestionDesEtudiant.setColumns(10);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(Color.WHITE);
		panel.add(panel_2_1, "cell 0 1,grow");
		panel_2_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_2_1.add(panel_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tableRow = table_1.getSelectedRow();
				textField_1__1.setText(table_1.getValueAt(tableRow, 1).toString());
				textField_2__1.setText(table_1.getValueAt(tableRow, 2).toString());
				textField__1.setText(table_1.getValueAt(tableRow, 3).toString());
				textField_3__1.setText(table_1.getValueAt(tableRow, 4).toString());
				textField_4__1.setText(table_1.getValueAt(tableRow, 5).toString());
			
			}
		});
		//panel_5_1.add(table_1);
		try {
			rechercheE();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		

		
		JScrollPane scrollPane = new JScrollPane(table_1);
		panel_1.add(scrollPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(Color.WHITE);
		panel_2.add(panel_4_1);
		panel_4_1.setLayout(new MigLayout("", "[][46px][81.00][78.00][][-16.00][61px][-22.00]", "[35.00px][35.00px][34.00px][37.00px][40.00px][53.00]"));
		
		JLabel lblNewLabel = new JLabel("CIN : (*)");
		panel_4_1.add(lblNewLabel, "cell 0 0,alignx left,aligny top");
		
		textField__1 = new JTextField();
		textField__1.setEditable(false);
		panel_4_1.add(textField__1, "cell 1 0 4 1,growx,aligny top");
		textField__1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nom :");
		lblNewLabel_1.setBackground(Color.WHITE);
		panel_4_1.add(lblNewLabel_1, "cell 0 1,alignx left,aligny top");
		
		textField_1__1 = new JTextField();
		panel_4_1.add(textField_1__1, "cell 1 1 4 1,growx,aligny top");
		textField_1__1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Prenom :");
		panel_4_1.add(lblNewLabel_2, "cell 0 2,alignx left,aligny top");
		
		textField_2__1 = new JTextField();
		panel_4_1.add(textField_2__1, "cell 1 2 4 1,growx,aligny top");
		textField_2__1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("DIPLOME :");
		panel_4_1.add(lblNewLabel_3, "cell 0 3,alignx left,aligny top");
		
		textField_3__1 = new JTextField();
		panel_4_1.add(textField_3__1, "cell 1 3 4 1,growx,aligny top");
		textField_3__1.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Email :");
		panel_4_1.add(lblNewLabel_4, "cell 0 4,alignx left,aligny top");
		
		textField_4__1 = new JTextField();
		panel_4_1.add(textField_4__1, "cell 1 4 4 1,growx,aligny top");
		textField_4__1.setColumns(10);
		
		JButton btnmodifieretudiant = new JButton("Modifier");
		btnmodifieretudiant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nomE = textField_1__1.getText().toString();
				String prenomE = textField_2__1.getText().toString();
				String CodeE = textField__1.getText().toString();
				String Diplome = textField_3__1.getText().toString();
				String Email  = textField_4__1.getText().toString();
				try {
					modifierE(nomE, prenomE, CodeE, Diplome, Email);
					rechercheE();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_4_1.add(btnmodifieretudiant, "cell 0 5,growx");
		
		JButton btnsupprimeretudiant = new JButton("Supprimer");
		btnsupprimeretudiant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String CodeE = textField__1.getText().toString();
				try {
					supprimerE(CodeE);
					rechercheE();
					String t = "";
					textField__1.setText(t);
					textField_1__1.setText(t);
					textField_2__1.setText(t);
					textField_3__1.setText(t);
					textField_4__1.setText(t);
					textField_5__1.setText(t);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_4_1.add(btnsupprimeretudiant, "cell 1 5");
		
		JButton btnSauvegarder_1 = new JButton("Ajouter");
		btnSauvegarder_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField__1.setEditable(true);
				btnajouteretudant.setVisible(true);
				btnSauvegarder_1.setVisible(false);
				String t = "";
				textField__1.setText(t);
				textField_1__1.setText(t);
				textField_2__1.setText(t);
				textField_3__1.setText(t);
				textField_4__1.setText(t);
				textField_5__1.setText(t);
			}
		});
		

		panel_4_1.add(btnSauvegarder_1, "cell 2 5");
		
		
		btnajouteretudant = new JButton("Sauvegarder");
		btnajouteretudant.setVisible(false);
		btnajouteretudant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nomE = textField_1__1.getText().toString();
				String prenomE = textField_2__1.getText().toString();
				String CodeE = textField__1.getText().toString().toUpperCase();
				String Diplome = textField_3__1.getText().toString();
				String Email  = textField_4__1.getText().toString();
				try {
					if (CodeE.equals("")) {
						JOptionPane.showMessageDialog(null, "Remplir le champ CIN.");
					} else {
						ajouterE(nomE, prenomE, CodeE, Diplome, Email);
						rechercheE();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				btnajouteretudant.setVisible(false);
				btnSauvegarder_1.setVisible(true);
				textField__1.setEditable(false);
			}
		});
		panel_4_1.add(btnajouteretudant, "cell 3 5");
		


		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBackground(Color.WHITE);
		panel.add(panel_3_1, "cell 0 2,grow");
		panel_3_1.setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][grow]", "[][]"));
		
		JLabel lblNewLabel_5 = new JLabel("Enter votre CNE :");
		panel_3_1.add(lblNewLabel_5, "cell 14 0");
		
		textField_5__1 = new JTextField();
		panel_3_1.add(textField_5__1, "cell 17 0,growx");
		textField_5__1.setColumns(10);
		
		JButton btnchercheretudiant = new JButton("Rechercher");
		btnchercheretudiant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			String CodeE = textField_5__1.getText().toString().toUpperCase();
			System.out.println(CodeE);
			try {
				rechercheEtudiant(CodeE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		});
		panel_3_1.add(btnchercheretudiant, "cell 15 1");
		
		JButton btnAnnuler = new JButton("Rafra\u00EEchir");
		btnAnnuler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					rechercheE();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3_1.add(btnAnnuler, "cell 17 1");
	}

}
