import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;


import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gestionnotes {

	public JFrame frmGestionEtudiantSigl;
	public JTextField txtGestionDesNotes;
	private JTable table__2_note;

	private static Connection cnx;
	private static Statement st,st1;
	private static ResultSet rst,rst1;
	private static String query;
	
	static String title1[] = {"ID","NOM","PRENOM","CIN","NOTE"};
	static Object[][] ObjetRc = {} ;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox_1 = new JComboBox();
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

//	========================================================== Gestion des Notes ==============================================================

	public Object[][] rechercheNoteModule(String ModuleN) throws Exception
	{
		try
		{
			cnx=connecterDB();
			query ="SELECT etudiant.idE,etudiant.nom,etudiant.prenom,etudiant.cne,note.note FROM etudiant,note,module "
					+ "WHERE module.nom = '"+ModuleN+"' AND module.idM = note.codeM AND note.codeE = etudiant.cne ORDER BY etudiant.idE ASC";
			st = cnx.createStatement();
			st1 = cnx.createStatement();
			rst = st.executeQuery(query);
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant ");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][5];
			int i = 0;
			while(rst.next())
			{
			    System.out.println();
			    data[i][0] = rst.getInt("idE");
			    data[i][1] = rst.getString("nom");
			    data[i][2] = rst.getString("prenom");
			    data[i][3] = rst.getString("cne");
			    if (rst.getDouble("note") == -1) {
			    	data[i][4] = "non affecter";	
				}else {
					data[i][4] = rst.getDouble("note");	
				}
			    		
			    i++;
			}
			JTable table1 = new JTable(data,title1);
			table__2_note.setModel(table1.getModel());
		    return data;
		}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
				return null;
		}
	}
	
	public void modifierNoteModule(String CodeENote,String ModuleN,double NoteEMod) throws Exception
	{
		if (NoteEMod<=20 && NoteEMod>=0) {
			try{			
		        query = "UPDATE note,module SET note.note="+NoteEMod+" WHERE note.codeE = '"+CodeENote+"' AND note.codeM = module.idM AND module.nom = '"+ModuleN+"'";
				cnx=connecterDB();
				st = cnx.createStatement();
		        st.executeUpdate(query);
		        String CodeENote2 = textField.getText();
		        String ModuleN2 = comboBox_1.getSelectedItem().toString();
		        JOptionPane.showMessageDialog(null, "la note d\'etudiant "+CodeENote2+" au module "+ModuleN2+" est modifier .");
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
			}
		} else {
			 JOptionPane.showMessageDialog(null, "donner une note entre 0 et 20.");
		}

	}


	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public gestionnotes() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGestionEtudiantSigl = new JFrame();
		frmGestionEtudiantSigl.setTitle("Gestion Etudiant SIGL 2018/2019");
		frmGestionEtudiantSigl.setResizable(false);
		frmGestionEtudiantSigl.getContentPane().setBackground(Color.WHITE);
		frmGestionEtudiantSigl.setBounds(100, 100, 540, 468);
		frmGestionEtudiantSigl.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel__2 = new JPanel();
		panel__2.setBackground(Color.WHITE);
		frmGestionEtudiantSigl.getContentPane().add(panel__2, BorderLayout.NORTH);
		panel__2.setLayout(new MigLayout("", "[524px]", "[33px][33px][41.00px][206.00px]"));
		
		JPanel panel__2_2 = new JPanel();
		panel__2_2.setBackground(Color.WHITE);
		panel__2.add(panel__2_2, "cell 0 0,grow");
		panel__2_2.setLayout(new MigLayout("", "[513.00px]", "[20px]"));
		
		txtGestionDesNotes = new JTextField();
		txtGestionDesNotes.setForeground(Color.WHITE);
		txtGestionDesNotes.setEditable(false);
		txtGestionDesNotes.setBackground(Color.BLUE);
		txtGestionDesNotes.setHorizontalAlignment(SwingConstants.CENTER);
		txtGestionDesNotes.setText("GESTION DES NOTES");
		panel__2_2.add(txtGestionDesNotes, "cell 0 0,growx,aligny top");
		txtGestionDesNotes.setColumns(10);
		
		JPanel panel_1__2 = new JPanel();
		panel_1__2.setBackground(Color.WHITE);
		panel_1__2.setBorder(new TitledBorder(null, "liste module et fili\u00E8re", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel__2.add(panel_1__2, "cell 0 1,grow");
		panel_1__2.setLayout(new MigLayout("", "[13.00px][72.00][131.00px][33.00px][106.00px][grow]", "[41.00px][]"));
		
		JLabel lblNewLabel_1 = new JLabel("Fili\u00E8re :");
		panel_1__2.add(lblNewLabel_1, "cell 1 0,alignx left,aligny center");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"LP SIGL"}));
		panel_1__2.add(comboBox, "cell 2 0,growx,aligny top");
		
		JLabel lblCin = new JLabel("CIN :");
		panel_1__2.add(lblCin, "cell 4 0");
		
		textField = new JTextField();
		textField.setEditable(false);
		panel_1__2.add(textField, "cell 5 0,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Module :");
		panel_1__2.add(lblNewLabel, "cell 1 1,alignx left,aligny center");
		
		comboBox_1 = new JComboBox();
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String selectValue = comboBox_1.getSelectedItem().toString();
				textField_1.setText("");
				textField.setText("");
				
				try {
					rechercheNoteModule(selectValue);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"JAVA", "TEC et GP", "oracle", "DotNet", "Modelisation", "web Dynamique"}));
		panel_1__2.add(comboBox_1, "cell 2 1,growx,aligny top");
		
		JLabel lblNote = new JLabel("NOTE :");
		panel_1__2.add(lblNote, "cell 4 1");
		
		textField_1 = new JTextField();
		panel_1__2.add(textField_1, "cell 5 1,growx");
		textField_1.setColumns(10);
		//panel_2__2.add(table__2_note);
		

		
		JPanel panel_3__2 = new JPanel();
		panel_3__2.setBackground(Color.WHITE);
		panel__2.add(panel_3__2, "cell 0 2,grow");
		panel_3__2.setLayout(new MigLayout("", "[216.00px][173.00][267.00px]", "[23px]"));
		
		JButton btnsauvegardernote = new JButton("Sauvegarde");
		btnsauvegardernote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {							
					try {
						if (textField.getText().equals("") || textField_1.getText().equals("")) {
							//System.out.println("hello");
							//System.out.println(CodeENote);
							JOptionPane.showMessageDialog(null, "Selectionner votre etudiant.");
						} else {
							String  NoteEMod1= textField_1.getText();
							String CodeENote = textField.getText();
							String ModuleN = comboBox_1.getSelectedItem().toString();
							Double NoteEMod = Double.parseDouble(NoteEMod1);
							modifierNoteModule(CodeENote, ModuleN, NoteEMod);
							rechercheNoteModule(ModuleN);
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}			
			}
		});
		panel_3__2.add(btnsauvegardernote, "cell 1 0,growx,aligny top");
		
		JPanel panel_2__2 = new JPanel();
		panel_2__2.setBackground(Color.WHITE);
		panel_2__2.setBorder(new TitledBorder(null, "liste des etudiant", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel__2.add(panel_2__2, "cell 0 3,grow");
		

		
		table__2_note = new JTable();
		
		try {
			rechercheNoteModule("JAVA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel_2__2.setLayout(new GridLayout(0, 1, 0, 0));
		JScrollPane scrollPane = new JScrollPane(table__2_note);
		table__2_note.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int tableRow = table__2_note.getSelectedRow();
				textField.setText(table__2_note.getValueAt(tableRow, 3).toString());
				textField_1.setText(table__2_note.getValueAt(tableRow, 4).toString());
			}
		});
		panel_2__2.add(scrollPane);
	}

}
