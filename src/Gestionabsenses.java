import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.CellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gestionabsenses extends JFrame {

	public JPanel contentPane;
	public JTextField txtGestionDabsenses;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTextField textField;
	public JTextField textField_4;
	public JComboBox comboBox_5;
	public JTable table_dabsenses;
	

	private static Connection cnx;
	private static Statement st,st1,st2;
	private static ResultSet rst,rst1,rst2;
	private static String query,query2,query3;
	
	static String title1[] = {"ID","NOM","PRENOM","Seance1","Seance2","Seance3","Seance4","Seance5"};
	static Object[][] ObjetRc = {} ;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

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

//	========================================================== Gestion des Absences =============================================================
	
	public void ajouterAbsence(String cneE,String ModuleNom,int numSeance,int action) throws Exception
	{
		try
		{
			int nbLignes = 0;
			cnx=connecterDB();
			query2 = "SELECT COUNT(*) as nbLigne FROM etudiant WHERE cne = '"+cneE+"'";
			st1 = cnx.createStatement();
			rst1 = st1.executeQuery(query2);
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			
			if(nbLignes>0) {
				query = "UPDATE absense,module SET action= "+action+"  WHERE absense.cne = '"+cneE+"' AND absense.codeM = module.idM AND module.nom = '"+ModuleNom+"' AND absense.Seance = "+numSeance+"";
				
				st = cnx.createStatement();
				st.executeUpdate(query);
				System.out.println(query);
				JOptionPane.showMessageDialog(null, "Operation bien effectué.");
			}else
				JOptionPane.showMessageDialog(null, "etudiant introuvable ajouter votre etudiant sur la table etudiant d'abord.");
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
	@SuppressWarnings("null")
	public int rechercheAbsenceEtudiant(String codeE,String ModuleNom) throws Exception
	{
		try {
			cnx=connecterDB();
			query ="SELECT COUNT(*) as nbLingne FROM absense,module WHERE absense.cne='"+codeE+"' AND absense.codeM = module.idM AND module.nom = '"+ModuleNom+"' AND absense.action = 1";
			st = cnx.createStatement();
			rst = st.executeQuery(query);
			int nbLignes = 0;
			rst.next();
			nbLignes = rst.getInt("nbLingne");
		    return nbLignes;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
			return (Integer) null;
		}
	}

	public Object[][] rechercheEtudiantAbsence(String ModuleNom) throws Exception
	{
		try {
			cnx=connecterDB();		
			query ="SELECT etudiant.idE,etudiant.cne,etudiant.nom,etudiant.prenom FROM etudiant,absense,module "
					+ "WHERE etudiant.cne = absense.cne AND absense.codeM = module.idM AND module.nom = '"+ModuleNom+"' GROUP BY etudiant.cne ORDER BY etudiant.idE ASC";
			st = cnx.createStatement();
			rst = st.executeQuery(query);
						
			st1 = cnx.createStatement();
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][8];
			int i = 0;
			while(rst.next())
			{					
			    System.out.println();
			    data[i][0] = rst.getInt("idE");
			    data[i][1] = rst.getString("nom");
			    data[i][2] = rst.getString("prenom");
			    
			    String cneEtudiant = rst.getString("cne");
			   
				query3 ="SELECT action FROM absense,module WHERE absense.cne = '"+cneEtudiant+"' AND absense.codeM = module.idM AND module.nom = '"+ModuleNom+"'";
				st2 = cnx.createStatement();
				rst2 = st2.executeQuery(query3);

				for (int j = 3; j < 8; j++){
					rst2.next();
					if (rst2.getInt("action")== 0) {
						data[i][j] = "Présent";
					} else if (rst2.getInt("action")== 1) {
						data[i][j] = "absent";
					}else if (rst2.getInt("action")== -1) {
						data[i][j] = "Pas précisé";
					}
				}
				
			    i++;
			}
			JTable table1 = new JTable(data,title1);
			table_dabsenses.setModel(table1.getModel());
		    return data;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
			return null;
		}
	}

	/**
	 * Create the frame.
	 */
	public Gestionabsenses() {
		setTitle("Gestion Etudiant SIGL 2018/2019");
		setResizable(false);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 900, 541);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[874px]", "[30px][224.00px][199.00px]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[868.00px]", "[20px]"));
		
		txtGestionDabsenses = new JTextField();
		txtGestionDabsenses.setForeground(Color.WHITE);
		txtGestionDabsenses.setBackground(Color.BLUE);
		txtGestionDabsenses.setEditable(false);
		txtGestionDabsenses.setHorizontalAlignment(SwingConstants.CENTER);
		txtGestionDabsenses.setText("GESTION D'ABSENSES");
		panel_1.add(txtGestionDabsenses, "cell 0 0,growx,aligny top");
		txtGestionDabsenses.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel.add(panel_2, "cell 0 1,grow");
		panel_2.setLayout(new MigLayout("", "[427.00px][431.00px]", "[241.00px]"));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBorder(new TitledBorder(null, "Marquer les absenses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_4, "cell 0 0,alignx left,aligny top");
		panel_4.setLayout(new MigLayout("", "[133.00px,grow][][67.00,grow][grow][89.00]", "[][][][][][][]"));
		
		JLabel lblNom = new JLabel("Nom :");
		panel_4.add(lblNom, "cell 0 0,alignx left");
		
		textField_1 = new JTextField();
		panel_4.add(textField_1, "cell 2 0 3 1,growx");
		textField_1.setColumns(10);
		
		JLabel lblPrenom = new JLabel("Prenom :");
		panel_4.add(lblPrenom, "cell 0 1,alignx left");
		
		textField_2 = new JTextField();
		panel_4.add(textField_2, "cell 2 1 3 1,growx");
		textField_2.setColumns(10);
		
		JLabel lblCne = new JLabel("CIN : (*)");
		panel_4.add(lblCne, "cell 0 2,alignx left");
		
		textField_3 = new JTextField();
		panel_4.add(textField_3, "cell 2 2 3 1,growx");
		textField_3.setColumns(10);
		
		JLabel lblModule = new JLabel("Module :");
		panel_4.add(lblModule, "cell 0 3,alignx left");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"JAVA", "TEC et GP", "oracle", "DotNet", "Modelisation", "web Dynamique"}));
		comboBox_1.setBackground(SystemColor.controlHighlight);
		panel_4.add(comboBox_1, "cell 2 3 3 1,growx");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(SystemColor.controlHighlight);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"seance 1", "seance 2", "seance 3", "seance 4", "seance 5"}));
		panel_4.add(comboBox, "cell 0 4,growx");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBackground(SystemColor.controlHighlight);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"present", "absent"}));
		panel_4.add(comboBox_2, "cell 2 4 3 1,growx");
		
		JButton btnNewButton = new JButton("Sauvegarder");

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cneE = textField_3.getText().toString().toUpperCase();
				String ModuleNom = comboBox_1.getSelectedItem().toString();
				int numSeance = comboBox.getSelectedIndex()+1;
				String actiontest = comboBox_2.getSelectedItem().toString();
				int action;
				if(actiontest =="absent") {
					action = 1;
				}
				else
					action = 0;
				
				try {
					System.out.println(numSeance);
					ajouterAbsence(cneE, ModuleNom, numSeance, action);
					rechercheEtudiantAbsence(ModuleNom);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(SystemColor.controlHighlight);
		panel_4.add(btnNewButton, "cell 1 6");
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setBorder(new TitledBorder(null, "Statistiques", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_5, "cell 1 0,alignx left,aligny top");
		panel_5.setLayout(new MigLayout("", "[126.00px][90.00px][93px,grow][][65.00]", "[14px][][][102.00]"));
		
		JLabel lblNewLabel = new JLabel("Chercher par CIN : (*)");
		panel_5.add(lblNewLabel, "cell 0 0,alignx left,aligny top");
		
		textField = new JTextField();
		panel_5.add(textField, "cell 2 0 2 1,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Module");
		panel_5.add(lblNewLabel_1, "cell 0 1,alignx left,aligny top");
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String ModuleNom = comboBox_3.getSelectedItem().toString();
				String codeE = textField.getText().toUpperCase();
				try {
				 int t = rechercheAbsenceEtudiant(codeE, ModuleNom);
					
				 String t1 =String.valueOf(t);
				 textField_4.setText(t1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"JAVA", "TEC et GP", "oracle", "DotNet", "Modelisation", "web Dynamique"}));
		comboBox_3.setBackground(SystemColor.controlHighlight);
		panel_5.add(comboBox_3, "cell 2 1 2 1,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Nombre d'absneses");
		panel_5.add(lblNewLabel_2, "cell 0 2,alignx left,aligny top");
		
		textField_4 = new JTextField();
		panel_5.add(textField_4, "cell 2 2 2 1,growx");
		textField_4.setColumns(10);
		
		JButton btnAfficherStatistique = new JButton("Afficher Statistique");
		btnAfficherStatistique.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ModuleNom = comboBox_3.getSelectedItem().toString();
				String codeE = textField.getText().toUpperCase();
				try {
				 int t = rechercheAbsenceEtudiant(codeE, ModuleNom);
					
				 String t1 =String.valueOf(t);
				 textField_4.setText(t1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		panel_5.add(btnAfficherStatistique, "cell 1 3");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBorder(new TitledBorder(null, "table d'absenses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, "cell 0 2,grow");
		panel_3.setLayout(new MigLayout("", "[658.00px][217.00px]", "[176px]"));
		

		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_3.add(panel_6, "cell 1 0,grow");
		panel_6.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JLabel lblAfficherLesAbsenses = new JLabel("Afficher les absenses d'un module");
		panel_6.add(lblAfficherLesAbsenses, "cell 0 0");
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBackground(SystemColor.controlHighlight);
		comboBox_4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String ModuleNom = comboBox_4.getSelectedItem().toString();	
				try {
					rechercheEtudiantAbsence(ModuleNom);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				
//			
//				try {	
//					Object[][] donnee =rechercheEtudiantAbsence(ModuleNom);
//				
//					for (int i = 0; i < donnee.length; i++) {
//						for (int j = 3; j < 8; j++){
//							if (donnee[i][j] == "Présent") {
//								int column = i;
//								int row = j;
//								cel = table_dabsenses.changeSelection(row, column, false, false);
//								.setBackground(Color.red)
//								table_dabsenses.setCellEditor((TableCellEditor) Color.red);
//								table_dabsenses.setBackground(0,0,255);
//							} else {
//
//							}		    	
//						}
//					}
//					
//				} catch (Exception e) {
//					 TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"JAVA", "TEC et GP", "oracle", "DotNet", "Modelisation", "web Dynamique"}));
		panel_6.add(comboBox_4, "cell 0 1,growx");
		
		table_dabsenses = new JTable();
		table_dabsenses.setEnabled(false);
		String ModuleNom = comboBox_4.getSelectedItem().toString();
		try {
			Object[][] donnee = rechercheEtudiantAbsence(ModuleNom);
			
			
			
			for (int i = 0; i < donnee.length; i++) {
				for (int j = 3; j < 8; j++){
					if (donnee[i][j] == "Présent") {
						int column = i;
						int row = j;
				
					//	table_dabsenses.setBackground(0,0,255);
					} else {

					}		    	
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//panel_3.add(table_dabsenses);
		
		JScrollPane scrollPane = new JScrollPane(table_dabsenses);
		scrollPane.setEnabled(false);
		panel_3.add(scrollPane, "cell 0 0,grow");
		
		table_dabsenses.setDefaultRenderer(Object.class, new MonCellRenderer());

	}

}
