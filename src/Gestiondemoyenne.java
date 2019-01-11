import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gestiondemoyenne extends JFrame {

	public JPanel contentPane;
	public JTextField txtGestionDesMoyennes;
	public JTextField textField_1;
	private JTable table;
	
	private static Connection cnx;
	private static Statement st,st1;
	private static ResultSet rst,rst1;
	private static String query;
	static String title1[] = {"ID","NOM","PRENOM","CIN","MOYENNE","MENTION","VALIDE"};
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
	

//	========================================================== Gestion des Moyennes =============================================================
	
	public Object[][] rechercheMoyennes() throws Exception
	{
		try
		{
			cnx=connecterDB();

			query =	"SELECT etudiant.idE,etudiant.nom,etudiant.prenom,etudiant.cne,ROUND(AVG(note.note),2) as moyenne "
					+ "FROM etudiant,note,module WHERE module.idM = note.codeM AND note.codeE = etudiant.cne GROUP BY note.codeE ORDER BY etudiant.idE ASC";

			st = cnx.createStatement();
			st1 = cnx.createStatement();
			rst = st.executeQuery(query);
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant ");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][7];
			int i = 0;
			while(rst.next())
			{
			    System.out.println();
			    data[i][0] = rst.getInt("idE");
			    data[i][1] = rst.getString("nom");
			    data[i][2] = rst.getString("prenom");
			    data[i][3] = rst.getString("cne");
			    if (rst.getDouble("moyenne") == -1) {
			    	 data[i][4] = "Non affecter";  
					 data[i][5] = "Non affecter";
					 data[i][6] = "Non affecter";
				} else {
					 data[i][4] = rst.getDouble("moyenne");	
					 Double valMoyenne = (Double) data[i][4];
					 data[i][5] = getMention(valMoyenne);
					 data[i][6] = getValidation(valMoyenne);
				}	
			    i++;
			}
			JTable table1 = new JTable(data,title1);
			table.setModel(table1.getModel());
		    return data;
		}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
				return null;
		}
	}
	
	
	public Object[][] rechercheMoyennesEtudiant(String cneE) throws Exception
	{
		try
		{
			cnx=connecterDB();

			query =	"SELECT etudiant.idE,etudiant.nom,etudiant.prenom,etudiant.cne,ROUND(AVG(note.note),2) as moyenne "
					+ "FROM etudiant,note,module WHERE module.idM = note.codeM AND note.codeE = etudiant.cne AND etudiant.cne = '"+cneE+"' GROUP BY note.codeE";

			st = cnx.createStatement();
			st1 = cnx.createStatement();
			rst = st.executeQuery(query);
			rst1 = st1.executeQuery("SELECT count(*) AS nbLigne FROM etudiant where cne = '"+cneE+"' ");
			int nbLignes = 0;
			rst1.next();
			nbLignes = rst1.getInt("nbLigne");
			Object[][] data = new Object[nbLignes][7];
			int i = 0;
			if (nbLignes>0) {
				while(rst.next())
				{
				    System.out.println();
				    data[i][0] = rst.getInt("idE");
				    data[i][1] = rst.getString("nom");
				    data[i][2] = rst.getString("prenom");
				    data[i][3] = rst.getString("cne");
				    
				    
				    if (rst.getDouble("moyenne") == -1) {
				    	 data[i][4] = "Non affecter";  
						 data[i][5] = "Non affecter";
						 data[i][6] = "Non affecter";
					} else {
						 data[i][4] = rst.getDouble("moyenne");	
						 Double valMoyenne = (Double) data[i][4];
						 data[i][5] = getMention(valMoyenne);
						 data[i][6] = getValidation(valMoyenne);
					}	
				    i++;
				}
				JTable table1 = new JTable(data,title1);
				table.setModel(table1.getModel());
			} else {
				 JOptionPane.showMessageDialog(null, "Etudiant pas trouvé.");
			}

		    return data;
		}catch(SQLException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
				return null;
		}
	}
	
	
	public String getMention(Double moyenne) throws Exception{
		if (moyenne>=10 && moyenne<12) {
			return "passable";
		}
		else if (moyenne>=12 && moyenne<14) {
			return "Assez bien";
		}
		else if (moyenne>=14 && moyenne<16) {
			return "bien";
		}
		else if (moyenne>=16 && moyenne<20) {
			return "trés bien";
		}
		else if (moyenne == 20) {
			return "parfait";
		}else
			return "insuffisant";
	}
	
	public String getValidation(Double moyenne) throws Exception{
		if (moyenne>=10) {
			return "Validé";
		}
		else if (moyenne>=6 && moyenne<10) {
			return "Rattrapage";
		}else
			return"redoublent";
	}
	
	/**
	 * Create the frame.
	 */
	public Gestiondemoyenne() {
		setTitle("Gestion Etudiant SIGL 2018/2019");
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 601, 456);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[594.00px]", "[33px][264.00px][75.00px]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[602.00px]", "[20px]"));
		
		txtGestionDesMoyennes = new JTextField();
		txtGestionDesMoyennes.setForeground(Color.WHITE);
		txtGestionDesMoyennes.setBackground(Color.BLUE);
		txtGestionDesMoyennes.setEditable(false);
		txtGestionDesMoyennes.setHorizontalAlignment(SwingConstants.CENTER);
		txtGestionDesMoyennes.setText("GESTION DES MOYENNES");
		panel_1.add(txtGestionDesMoyennes, "cell 0 0,growx,aligny top");
		txtGestionDesMoyennes.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new TitledBorder(null, "Table des moyennes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2, "cell 0 1,grow");
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		table = new JTable();
		//panel_2.add(table);
		
		try {
			rechercheMoyennes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel_2.add(scrollPane);

		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBorder(new TitledBorder(null, "chercher un etudiant", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_3, "cell 0 2,grow");
		panel_3.setLayout(new MigLayout("", "[][90.00][86px][89px][][][]", "[23px]"));
		
		textField_1 = new JTextField();
		panel_3.add(textField_1, "cell 2 0,grow");
		textField_1.setColumns(15);
		
		JButton btnNewButton = new JButton("Chercher");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String CINETu = textField_1.getText().toString().toUpperCase();
				
				try {
					rechercheMoyennesEtudiant(CINETu);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel_3.add(btnNewButton, "cell 4 0,alignx left,aligny top");
		
		JButton btnAnnuler = new JButton("Rafra\u00EEchir");
		btnAnnuler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					rechercheMoyennes();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_3.add(btnAnnuler, "cell 6 0");
		table.setDefaultRenderer(Object.class, new MonCellRenderer());
	}
	
}
