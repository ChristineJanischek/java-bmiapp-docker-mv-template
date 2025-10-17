package start;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8519171689153531668L;
	private JPanel contentPane;
	private JTextField tfGewicht;
	private JTextField tfGroesse;
	private JTextField tfErgebnis;
	private Bmirechner derRechner = new Bmirechner();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 353);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitel = new JLabel("BMI-Rechner");
		lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));
		lbTitel.setFont(new Font("Arial", Font.PLAIN, 18));
		lbTitel.setBounds(0, 11, 235, 80);
		contentPane.add(lbTitel);
		
		JLabel lbGewicht = new JLabel("Gewicht:");
		lbGewicht.setBounds(34, 122, 60, 14);
		contentPane.add(lbGewicht);
		
		JLabel lbGroesse = new JLabel("Gr\u00F6\u00DFe:");
		lbGroesse.setBounds(34, 157, 46, 14);
		contentPane.add(lbGroesse);
		
		tfGewicht = new JTextField();
		tfGewicht.setText("tfGewicht");
		tfGewicht.setBounds(104, 102, 200, 29);
		contentPane.add(tfGewicht);
		tfGewicht.setColumns(10);
		
		tfGroesse = new JTextField();
		tfGroesse.setText("tfGroesse");
		tfGroesse.setBounds(104, 145, 200, 29);
		contentPane.add(tfGroesse);
		tfGroesse.setColumns(10);
		
		JButton lbLeeren = new JButton("Leeren");
		lbLeeren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfGewicht.setText("");
				tfGroesse.setText("");
				tfErgebnis.setText("");
			}
		});
		lbLeeren.setBounds(33, 234, 89, 23);
		contentPane.add(lbLeeren);
		
		JButton btBerechne = new JButton("Berechne BMI");
		btBerechne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				double gewicht = Double.valueOf(tfGewicht.getText());
				double groesse = Double.valueOf(tfGroesse.getText());
				
				//Verarbeitung
				derRechner.berechne(gewicht, groesse);
				
				//Ausgabe
				schreibeErgebnis();
			}
		});
		btBerechne.setBounds(132, 234, 122, 23);
		contentPane.add(btBerechne);
		
		JButton lbInterpretiere = new JButton("Interpretiere BMI");
		lbInterpretiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Eingabe
				double gewicht = Double.valueOf(tfGewicht.getText());
				double groesse = Double.valueOf(tfGroesse.getText());
				
				//Verarbeitung
				derRechner.berechne(gewicht, groesse);
				derRechner.interpretiere();
				
				//Ausgabe
				schreibeKategorie();
			}
		});
		lbInterpretiere.setBounds(264, 234, 133, 23);
		contentPane.add(lbInterpretiere);
		
		JButton btSchliessen = new JButton("Schlie\u00DFen");
		btSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btSchliessen.setBounds(407, 234, 95, 23);
		contentPane.add(btSchliessen);
		
		tfErgebnis = new JTextField();
		tfErgebnis.setEditable(false);
		tfErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfErgebnis.setBackground(Color.LIGHT_GRAY);
		tfErgebnis.setText("tfErgebnis");
		tfErgebnis.setBounds(34, 190, 270, 33);
		contentPane.add(tfErgebnis);
		tfErgebnis.setColumns(10);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));
		lbLogo.setBounds(365, 268, 174, 35);
		contentPane.add(lbLogo);
	}
	
	
	//Hilfsmethoden und Sonstige Methoden
	private void leseGewicht(){
		derRechner.setGewicht(Double.valueOf(tfGewicht.getText()));
	}
	
	private void leseGroesse(){
		derRechner.setGroesse(Double.valueOf(tfGroesse.getText()));
	}
	
	private void schreibeErgebnis(){
		tfErgebnis.setText(String.valueOf(derRechner.getErgebnis()));
	}
	
	private void schreibeKategorie(){
		tfErgebnis.setText(derRechner.getKategorie());
	}
}
