import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class MiniProjet {

	private JFrame frmGestionEtudiantSigl;
	private JTextField txtGestionDesEtudiant;
	private JTextField txtUniversiteSidiMohamed;
	private JTextField txtFacultesDesScience;
	private JPanel panel;


	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MiniProjet window = new MiniProjet();
					window.frmGestionEtudiantSigl.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MiniProjet() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGestionEtudiantSigl = new JFrame();
		frmGestionEtudiantSigl.setResizable(false);
		frmGestionEtudiantSigl.setIconImage(Toolkit.getDefaultToolkit().getImage("principal.jpg"));
		frmGestionEtudiantSigl.getContentPane().setBackground(Color.WHITE);
		frmGestionEtudiantSigl.setBackground(Color.WHITE);
		frmGestionEtudiantSigl.setTitle("Gestion Etudiant SIGL 2018/2019");
		frmGestionEtudiantSigl.setBounds(100, 100, 730, 463);
		frmGestionEtudiantSigl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmGestionEtudiantSigl.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[728.00px]", "[207.00px][68.00px][94.00px][57.00px]"));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[165.00px][323.00px][226px]", "[207px]"));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setLayout(null);
		JLabel lab = new JLabel(new ImageIcon("logo.png"));
		lab.setBounds(10, 11, 119, 105);
		panel_5.add(lab);
		panel_1.add(panel_5, "cell 0 0,grow");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		panel_1.add(panel_6, "cell 1 0,grow");
		panel_6.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		panel_6.add(panel_12);
		panel_12.setLayout(new MigLayout("", "[307.00px]", "[39px]"));
		
		txtUniversiteSidiMohamed = new JTextField();
		txtUniversiteSidiMohamed.setForeground(Color.WHITE);
		txtUniversiteSidiMohamed.setBackground(Color.BLUE);
		txtUniversiteSidiMohamed.setHorizontalAlignment(SwingConstants.CENTER);
		txtUniversiteSidiMohamed.setEditable(false);
		txtUniversiteSidiMohamed.setText("   UNIVERSITE SIDI MOHAMED BEN ABDELAH");
		panel_12.add(txtUniversiteSidiMohamed, "cell 0 0,grow");
		txtUniversiteSidiMohamed.setColumns(10);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.WHITE);
		panel_6.add(panel_13);
		panel_13.setLayout(new MigLayout("", "[299.00px]", "[41.00px]"));
		
		txtFacultesDesScience = new JTextField();
		txtFacultesDesScience.setBackground(Color.BLUE);
		txtFacultesDesScience.setForeground(Color.WHITE);
		txtFacultesDesScience.setEditable(false);
		txtFacultesDesScience.setHorizontalAlignment(SwingConstants.CENTER);
		txtFacultesDesScience.setText("FACULTES DES SCIENCE DHAR ELMEHRAZ ");
		panel_13.add(txtFacultesDesScience, "cell 0 0,grow");
		txtFacultesDesScience.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setLayout(null);
		JLabel lab2 = new JLabel(new ImageIcon("LOGO-fsdm.jpg"));
		lab2.setBounds(106, 11, 98, 102);
		panel_7.add(lab2);
		panel_1.add(panel_7, "cell 2 0,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel.add(panel_2, "cell 0 1,grow");
		panel_2.setLayout(new MigLayout("", "[221px][][][][][92.00][261.00]", "[57.00px]"));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.WHITE);
		panel_2.add(panel_8, "cell 0 0 7 1,grow");
		panel_8.setLayout(new MigLayout("", "[720.00px]", "[36.00px]"));
		
		txtGestionDesEtudiant = new JTextField();
		txtGestionDesEtudiant.setForeground(Color.WHITE);
		txtGestionDesEtudiant.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtGestionDesEtudiant.setEditable(false);
		txtGestionDesEtudiant.setBackground(Color.BLUE);
		txtGestionDesEtudiant.setHorizontalAlignment(SwingConstants.CENTER);
		txtGestionDesEtudiant.setText("GESTION DES ETUDIANTS LP-SIGL 2018/2019");
		panel_8.add(txtGestionDesEtudiant, "cell 0 0,grow");
		txtGestionDesEtudiant.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, "cell 0 2,grow");
		panel_3.setLayout(new MigLayout("", "[502.00px][531.00px]", "[91px]"));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.WHITE);
		panel_3.add(panel_9, "cell 0 0,alignx center,growy");
		
		JButton btnNewButton = new JButton("informations Etudiant");
		btnNewButton.setBackground(SystemColor.controlHighlight);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							gestionetudiant window = new gestionetudiant();
							window.frmGestionEtudiantSigl.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel_9.setLayout(new MigLayout("", "[145px]", "[13.00px][14.00px][14.00][16.00]"));
		btnNewButton.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_9.add(btnNewButton, "cell 0 0 1 2,grow");
		
		JButton btnNewButton_1 = new JButton("Gestions des moyennes");
		btnNewButton_1.setBackground(SystemColor.controlHighlight);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Gestiondemoyenne frame = new Gestiondemoyenne();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel_9.add(btnNewButton_1, "cell 0 2 1 2,grow");
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.WHITE);
		panel_3.add(panel_10, "cell 1 0,alignx center,growy");
		panel_10.setLayout(new MigLayout("", "[141px]", "[13][14][14][16]"));
		
		JButton btnNewButton_2 = new JButton("Gestion des notes");
		btnNewButton_2.setBackground(SystemColor.controlHighlight);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							gestionnotes window = new gestionnotes();
							window.frmGestionEtudiantSigl.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel_10.add(btnNewButton_2, "cell 0 0 1 2,grow");
		
		JButton btnNewButton_3 = new JButton("Gestion des absenses");
		btnNewButton_3.setBackground(SystemColor.controlHighlight);
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Gestionabsenses frame = new Gestionabsenses();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel_10.add(btnNewButton_3, "cell 0 2 1 2,grow");
	}

}
