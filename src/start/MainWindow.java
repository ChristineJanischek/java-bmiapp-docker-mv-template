
// Die Klasse MainWindow ist die grafische Benutzeroberfläche (View) des BMI-Rechners.
// Sie zeigt Eingabefelder, Buttons und das Ergebnis an und verbindet sich mit dem Controller (BmiManager).
package start;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Die MainWindow-Klasse ist das Hauptfenster der Anwendung.
 * Sie nutzt das MVC-Prinzip: View (MainWindow) -> Controller (BmiManager) -> Model (Bmirechner)
 */
public class MainWindow extends JFrame {

private static final long serialVersionUID = 8519171689153531668L;

	// Das Haupt-Panel für alle GUI-Komponenten
	private JPanel contentPane;
	// Eingabefeld für das Gewicht
	private JTextField tfGewicht;
	// Eingabefeld für die Größe
	private JTextField tfGroesse;
	// Feld zur Anzeige des Ergebnisses
	private JTextField tfErgebnis;
	// ComboBox für das Alter (Version 2)
	private JComboBox<String> cbAlter;
	// RadioButtons für das Geschlecht (Version 2)
	private JRadioButton rbMann;
	private JRadioButton rbFrau;
	private ButtonGroup bgGeschlecht;
	// Der Controller, der die Logik steuert
	private BmiManager manager;

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
	 * Konstruktor: Initialisiert die GUI und verbindet sie mit dem Controller.
	 */
	public MainWindow() {
		// Erzeuge den Controller (BmiManager)
		manager = new BmiManager();

		// Fenster-Einstellungen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Schließen beendet die App
		setTitle("BMI-Rechner"); // Fenstertitel
		// Setze das Fenster-Icon (Java-Tasse oder eigenes Icon)
		setIconImage(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")).getImage());

		// Haupt-Panel für die GUI
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE); // Hintergrundfarbe
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Abstand zum Rand
		setContentPane(contentPane);

		// Layout-Manager: GridBagLayout für flexible Anordnung
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8); // Abstand zwischen Komponenten
		gbc.fill = GridBagConstraints.HORIZONTAL; // Komponenten füllen horizontal
		gbc.weightx = 1.0; // Komponenten können sich ausdehnen


		// Banner-Logo oben
		JLabel lbLogo = new JLabel();
		lbLogo.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		contentPane.add(lbLogo, gbc);


		// Titel mit Icon
		JLabel lbTitel = new JLabel("BMI-Rechner");
		lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));
		lbTitel.setFont(new Font("Arial", Font.BOLD, 20));
		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
		contentPane.add(lbTitel, gbc);


		// Eingabefelder für Gewicht und Größe
		// Gewicht
		gbc.gridwidth = 1;
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.weightx = 0.3; // Label nimmt weniger Platz
		gbc.anchor = GridBagConstraints.EAST;
		JLabel lbGewicht = new JLabel("Gewicht (kg):");
		contentPane.add(lbGewicht, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7; // TextField nimmt mehr Platz
		gbc.anchor = GridBagConstraints.WEST;
		tfGewicht = new JTextField();
		tfGewicht.setColumns(15);
		contentPane.add(tfGewicht, gbc);

		// Größe
		gbc.gridx = 0; gbc.gridy = 3;
		gbc.weightx = 0.3;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel lbGroesse = new JLabel("Größe (m):");
		contentPane.add(lbGroesse, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		gbc.anchor = GridBagConstraints.WEST;
		tfGroesse = new JTextField();
		tfGroesse.setColumns(15);
		contentPane.add(tfGroesse, gbc);

		// Alter (Version 2) - ComboBox
		gbc.gridx = 0; gbc.gridy = 4;
		gbc.weightx = 0.3;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel lbAlter = new JLabel("Alter (Jahre):");
		contentPane.add(lbAlter, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		gbc.anchor = GridBagConstraints.WEST;
		
		// ComboBox mit Altersgruppen
		String[] altersGruppen = {
			"-- nicht angegeben --",
			"18-24", "25-34", "35-44", "45-54", 
			"55-64", "65-74", "75+"
		};
		cbAlter = new JComboBox<>(altersGruppen);
		contentPane.add(cbAlter, gbc);

		// Geschlecht (Version 2) - RadioButtons
		gbc.gridx = 0; gbc.gridy = 5;
		gbc.weightx = 0.3;
		gbc.anchor = GridBagConstraints.EAST;
		JLabel lbGeschlecht = new JLabel("Geschlecht:");
		contentPane.add(lbGeschlecht, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 0.7;
		gbc.anchor = GridBagConstraints.WEST;
		
		// Panel für RadioButtons
		JPanel panelGeschlecht = new JPanel();
		panelGeschlecht.setBackground(Color.WHITE);
		rbMann = new JRadioButton("Männlich");
		rbMann.setBackground(Color.WHITE);
		rbFrau = new JRadioButton("Weiblich");
		rbFrau.setBackground(Color.WHITE);
		
		// ButtonGroup sorgt dafür, dass nur ein RadioButton ausgewählt werden kann
		bgGeschlecht = new ButtonGroup();
		bgGeschlecht.add(rbMann);
		bgGeschlecht.add(rbFrau);
		
		panelGeschlecht.add(rbMann);
		panelGeschlecht.add(rbFrau);
		contentPane.add(panelGeschlecht, gbc);

		// Ergebnisfeld (BMI/Kategorie)
		gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		tfErgebnis = new JTextField();
		tfErgebnis.setEditable(false); // Nur Anzeige, keine Eingabe
		tfErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfErgebnis.setBackground(Color.LIGHT_GRAY);
		tfErgebnis.setText("");
		contentPane.add(tfErgebnis, gbc);


		// Buttons für Aktionen
		gbc.gridwidth = 1;
		gbc.gridy = 7;
		gbc.gridx = 0;
		// Button: BMI berechnen
		JButton btBerechne = new JButton("Berechne BMI");
		btBerechne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Übergibt die Eingaben an den Controller und zeigt das Ergebnis
				manager.berechneBMI(getGewichtValue(), getGroesseValue());
				schreibeErgebnis();
			}
		});
		contentPane.add(btBerechne, gbc);

		gbc.gridx = 1;
		// Button: BMI interpretieren
		JButton btInterpretiere = new JButton("Interpretiere BMI");
		btInterpretiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Version 2: Nutzt intelligente Methodenwahl mit Alter & Geschlecht
				manager.interpretiereIntelligent(
					getGewichtValue(), 
					getGroesseValue(), 
					getAlterValue(), 
					getGeschlechtValue()
				);
				manager.zeigeInterpretation();
				schreibeKategorie();
			}
		});
		contentPane.add(btInterpretiere, gbc);


		// Button: Felder leeren
		gbc.gridy = 8;
		gbc.gridx = 0;
		JButton btLeeren = new JButton("Leeren");
		btLeeren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Löscht alle Eingabefelder und das Ergebnis
				clearFields();
			}
		});
		contentPane.add(btLeeren, gbc);

		// Button: Anwendung schließen
		gbc.gridx = 1;
		JButton btSchliessen = new JButton("Schließen");
		btSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Beendet die Anwendung
				System.exit(0);
			}
		});
		contentPane.add(btSchliessen, gbc);

		// Fenstergröße automatisch anpassen
		pack();
		// Minimale Fenstergröße festlegen
		setMinimumSize(new java.awt.Dimension(400, 500));
		// Fenster zentriert auf dem Bildschirm anzeigen
		setLocationRelativeTo(null);
	}


	/**
	 * Gibt den Controller zurück (für andere Klassen).
	 */
	public BmiManager getManager() {
		return manager;
	}


	/**
	 * Liest das Gewicht aus dem Eingabefeld.
	 */
	public double getGewichtValue() {
		return Double.valueOf(tfGewicht.getText());
	}


	/**
	 * Liest die Größe aus dem Eingabefeld.
	 */
	public double getGroesseValue() {
		return Double.valueOf(tfGroesse.getText());
	}


	/**
	 * Liest das Alter aus dem ComboBox (Version 2).
	 * Berechnet den Mittelwert der ausgewählten Altersgruppe.
	 * @return Alter als int (0 wenn keine Gruppe gewählt)
	 */
	public int getAlterValue() {
		String auswahl = (String) cbAlter.getSelectedItem();
		if (auswahl == null || auswahl.equals("-- nicht angegeben --")) {
			return 0;
		}
		
		// Berechne Mittelwert der Altersgruppe
		switch (auswahl) {
			case "18-24": return 21;
			case "25-34": return 30;
			case "35-44": return 40;
			case "45-54": return 50;
			case "55-64": return 60;
			case "65-74": return 70;
			case "75+": return 75;
			default: return 0;
		}
	}


	/**
	 * Liest das Geschlecht aus den RadioButtons (Version 2).
	 * @return "Mann", "Frau" oder null (wenn nichts ausgewählt)
	 */
	public String getGeschlechtValue() {
		if (rbMann.isSelected()) {
			return "Mann";
		} else if (rbFrau.isSelected()) {
			return "Frau";
		}
		return null;
	}


	/**
	 * Setzt den Text im Ergebnisfeld.
	 */
	public void setErgebnisText(String text) {
		tfErgebnis.setText(text);
	}



	/**
	 * Löscht alle Eingabefelder und das Ergebnis.
	 */
	public void clearFields() {
		tfGewicht.setText("");
		tfGroesse.setText("");
		tfErgebnis.setText("");
		cbAlter.setSelectedIndex(0); // Zurück auf "-- nicht angegeben --"
		bgGeschlecht.clearSelection(); // Keine Auswahl bei RadioButtons
	}


	/**
	 * Zeigt den berechneten BMI im Ergebnisfeld an.
	 */
	private void schreibeErgebnis(){
		tfErgebnis.setText(String.format("BMI: %.2f", manager.getModel().getErgebnis()));
	}


	/**
	 * Zeigt die Gewichtskategorie im Ergebnisfeld an.
	 */
	private void schreibeKategorie(){
		tfErgebnis.setText(manager.getModel().getKategorie());
	}
}
