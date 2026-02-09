// Die Klasse MainWindow ist die grafische Benutzeroberfläche (View) des BMI-Rechners.
// Version 4: Erweit um Person- und Messung-Verwaltung
package start;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;


/**
 * MainWindow Version 4: Erweitert um Person- und Messung-Verwaltung.
 * MVC-Prinzip: View (MainWindow) -> Controller (BmiManager) -> Model (Bmirechner, Person, Messung)
 * 
 * @version 4.0
 * @since 2026-02-09
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 8519171689153531670L;

	private JPanel contentPane;
	
	// Personen-Felder
	private JTextField tfVorname;
	private JTextField tfNachname;
	private JTextField tfAlter;
	private JTextField tfEmail;
	private JComboBox<String> cbGeschlecht;
	private JComboBox<String> cbPersonAuswahl;
	
	// BMI-Berechnung-Felder
	private JTextField tfGewicht;
	private JTextField tfGroesse;
	private JTextField tfErgebnis;
	
	// Messungs-Historie und Statistik
	private JTextArea taMessungsHistorie;
	private JTextArea taStatistik;
	
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
	 * Konstruktor: Initialisiert die GUI Version 4.
	 */
	public MainWindow() {
		manager = new BmiManager();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BMI-Rechner Version 4 - mit Personen & Messungen");
		try {
			setIconImage(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")).getImage());
		} catch (Exception e) {
			// Icon nicht gefunden, kein Problem
		}

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		// Haupt-Panel mit GridBagLayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.WHITE);
		contentPane.add(mainPanel, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		int row = 0;

		// ========== Logo & Titel ==========
		try {
			JLabel lbLogo = new JLabel();
			lbLogo.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));
			gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.CENTER;
			mainPanel.add(lbLogo, gbc);
		} catch (Exception e) {
			// Logo nicht gefunden
		}

		JLabel lbTitel = new JLabel("BMI-Rechner Version 4");
		try {
			lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));
		} catch (Exception e) {
			// Icon nicht gefunden
		}
		lbTitel.setFont(new Font("Arial", Font.BOLD, 20));
		gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
		mainPanel.add(lbTitel, gbc);

		// ========== Person-Bereich ==========
		JPanel personPanel = new JPanel();
		personPanel.setBorder(new TitledBorder("1. Personen-Verwaltung"));
		personPanel.setLayout(new GridBagLayout());
		personPanel.setBackground(Color.WHITE);
		gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		mainPanel.add(personPanel, gbc);

		GridBagConstraints gbcPerson = new GridBagConstraints();
		gbcPerson.insets = new Insets(3, 3, 3, 3);
		gbcPerson.fill = GridBagConstraints.HORIZONTAL;
		
		// Vorname
		gbcPerson.gridx = 0; gbcPerson.gridy = 0; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("Vorname:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		tfVorname = new JTextField(15);
		personPanel.add(tfVorname, gbcPerson);
		
		// Nachname
		gbcPerson.gridx = 0; gbcPerson.gridy = 1; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("Nachname:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		tfNachname = new JTextField(15);
		personPanel.add(tfNachname, gbcPerson);
		
		// Alter
		gbcPerson.gridx = 0; gbcPerson.gridy = 2; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("Alter:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		tfAlter = new JTextField(15);
		personPanel.add(tfAlter, gbcPerson);
		
		// Geschlecht
		gbcPerson.gridx = 0; gbcPerson.gridy = 3; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("Geschlecht:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		cbGeschlecht = new JComboBox<>(new String[]{"Mann", "Frau"});
		personPanel.add(cbGeschlecht, gbcPerson);
		
		// Email
		gbcPerson.gridx = 0; gbcPerson.gridy = 4; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("E-Mail:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		tfEmail = new JTextField(15);
		personPanel.add(tfEmail, gbcPerson);
		
		// Button-Zeile für Person
		JPanel personButtonPanel = new JPanel();
		personButtonPanel.setBackground(Color.WHITE);
		JButton btPersonErstellen = new JButton("Person erstellen");
		btPersonErstellen.addActionListener(e -> erstellePerson());
		personButtonPanel.add(btPersonErstellen);
		
		JButton btPersonFelderLeeren = new JButton("Felder leeren");
		btPersonFelderLeeren.addActionListener(e -> {
			tfVorname.setText("");
			tfNachname.setText("");
			tfAlter.setText("");
			tfEmail.setText("");
		});
		personButtonPanel.add(btPersonFelderLeeren);
		
		gbcPerson.gridx = 0; gbcPerson.gridy = 5; gbcPerson.gridwidth = 2;
		personPanel.add(personButtonPanel, gbcPerson);
		
		// Person Auswahl
		gbcPerson.gridx = 0; gbcPerson.gridy = 6; gbcPerson.gridwidth = 1; gbcPerson.weightx = 0.3;
		personPanel.add(new JLabel("Person auswählen:"), gbcPerson);
		gbcPerson.gridx = 1; gbcPerson.weightx = 0.7;
		cbPersonAuswahl = new JComboBox<>();
		cbPersonAuswahl.addActionListener(e -> personAuswaehlen());
		personPanel.add(cbPersonAuswahl, gbcPerson);

		// ========== BMI-Bereich ==========
		JPanel bmiPanel = new JPanel();
		bmiPanel.setBorder(new TitledBorder("2. BMI-Berechnung & Messung"));
		bmiPanel.setLayout(new GridBagLayout());
		bmiPanel.setBackground(Color.WHITE);
		gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
		mainPanel.add(bmiPanel, gbc);

		GridBagConstraints gbcBmi = new GridBagConstraints();
		gbcBmi.insets = new Insets(3, 3, 3, 3);
		gbcBmi.fill = GridBagConstraints.HORIZONTAL;
		
		// Gewicht
		gbcBmi.gridx = 0; gbcBmi.gridy = 0; gbcBmi.weightx = 0.3;
		bmiPanel.add(new JLabel("Gewicht (kg):"), gbcBmi);
		gbcBmi.gridx = 1; gbcBmi.weightx = 0.7;
		tfGewicht = new JTextField(15);
		bmiPanel.add(tfGewicht, gbcBmi);
		
		// Größe
		gbcBmi.gridx = 0; gbcBmi.gridy = 1; gbcBmi.weightx = 0.3;
		bmiPanel.add(new JLabel("Größe (m):"), gbcBmi);
		gbcBmi.gridx = 1; gbcBmi.weightx = 0.7;
		tfGroesse = new JTextField(15);
		bmiPanel.add(tfGroesse, gbcBmi);
		
		// Ergebnis
		gbcBmi.gridx = 0; gbcBmi.gridy = 2; gbcBmi.gridwidth = 2;
		tfErgebnis = new JTextField();
		tfErgebnis.setEditable(false);
		tfErgebnis.setFont(new Font("Tahoma", Font.BOLD, 14));
		tfErgebnis.setBackground(Color.LIGHT_GRAY);
		bmiPanel.add(tfErgebnis, gbcBmi);
		
		// Buttons für BMI
		JPanel bmiButtonPanel = new JPanel();
		bmiButtonPanel.setBackground(Color.WHITE);
		
		JButton btMessungErstellen = new JButton("Messung erstellen");
		btMessungErstellen.addActionListener(e -> erstelleMessung());
		bmiButtonPanel.add(btMessungErstellen);
		
		JButton btBerechne = new JButton("Nur berechnen");
		btBerechne.addActionListener(e -> nurBerechnen());
		bmiButtonPanel.add(btBerechne);
		
		JButton btLeeren = new JButton("Leeren");
		btLeeren.addActionListener(e -> {
			tfGewicht.setText("");
			tfGroesse.setText("");
			tfErgebnis.setText("");
		});
		bmiButtonPanel.add(btLeeren);
		
		gbcBmi.gridx = 0; gbcBmi.gridy = 3; gbcBmi.gridwidth = 2;
		bmiPanel.add(bmiButtonPanel, gbcBmi);

		// ========== Messungs-Historie & Statistik ==========
		JPanel historiePanelContainer = new JPanel();
		historiePanelContainer.setLayout(new GridBagLayout());
		historiePanelContainer.setBackground(Color.WHITE);
		gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 0.5;
		mainPanel.add(historiePanelContainer, gbc);
		
		GridBagConstraints gbcHist = new GridBagConstraints();
		gbcHist.insets = new Insets(3, 3, 3, 3);
		gbcHist.fill = GridBagConstraints.BOTH;
		gbcHist.weightx = 0.6;
		gbcHist.weighty = 1.0;
		
		// Messungs-Historie
		JPanel historiePanel = new JPanel();
		historiePanel.setBorder(new TitledBorder("3. Messungs-Historie"));
		historiePanel.setLayout(new BorderLayout());
		historiePanel.setBackground(Color.WHITE);
		gbcHist.gridx = 0; gbcHist.gridy = 0;
		historiePanelContainer.add(historiePanel, gbcHist);
		
		taMessungsHistorie = new JTextArea(8, 30);
		taMessungsHistorie.setEditable(false);
		taMessungsHistorie.setFont(new Font("Monospaced", Font.PLAIN, 11));
		JScrollPane scrollHistorie = new JScrollPane(taMessungsHistorie);
		historiePanel.add(scrollHistorie, BorderLayout.CENTER);
		
		// Statistik
		JPanel statistikPanel = new JPanel();
		statistikPanel.setBorder(new TitledBorder("4. Statistik"));
		statistikPanel.setLayout(new BorderLayout());
		statistikPanel.setBackground(Color.WHITE);
		gbcHist.gridx = 1; gbcHist.gridy = 0;
		gbcHist.weightx = 0.4;
		historiePanelContainer.add(statistikPanel, gbcHist);
		
		taStatistik = new JTextArea(8, 20);
		taStatistik.setEditable(false);
		taStatistik.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JScrollPane scrollStatistik = new JScrollPane(taStatistik);
		statistikPanel.add(scrollStatistik, BorderLayout.CENTER);
		
		// Button unter Historie
		JPanel historieButtonPanel = new JPanel();
		historieButtonPanel.setBackground(Color.WHITE);
		JButton btAktualisieren = new JButton("Aktualisieren");
		btAktualisieren.addActionListener(e -> aktualisiereAnzeige());
		historieButtonPanel.add(btAktualisieren);
		
		JButton btSchliessen = new JButton("Schließen");
		btSchliessen.addActionListener(e -> System.exit(0));
		historieButtonPanel.add(btSchliessen);
		
		gbc.gridx = 0; gbc.gridy = row++; gbc.gridwidth = 2;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		mainPanel.add(historieButtonPanel, gbc);

		// Fenster-Einstellungen
		pack();
		setMinimumSize(new Dimension(900, 800));
		setLocationRelativeTo(null);
		
		// Initial-Aktualisierung
		aktualisierePersonenAuswahl();
		aktualisiereAnzeige();
	}

	// ========== Methoden für Version 4 ==========
	
	/**
	 * Erstellt eine neue Person basierend auf den Eingabefeldern.
	 */
	private void erstellePerson() {
		try {
			String vorname = tfVorname.getText().trim();
			String nachname = tfNachname.getText().trim();
			int alter = Integer.parseInt(tfAlter.getText().trim());
			String geschlecht = (String) cbGeschlecht.getSelectedItem();
			String email = tfEmail.getText().trim();
			
			Person person = manager.erstellePerson(vorname, nachname, alter, geschlecht, email);
			manager.setAktuellePerson(person);
			
			JOptionPane.showMessageDialog(this, 
				"Person erfolgreich erstellt:\n" + person.getFullName(),
				"Erfolg", JOptionPane.INFORMATION_MESSAGE);
			
			// Felder leeren und Auswahl aktualisieren
			tfVorname.setText("");
			tfNachname.setText("");
			tfAlter.setText("");
			tfEmail.setText("");
			
			aktualisierePersonenAuswahl();
			aktualisiereAnzeige();
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, 
				"Fehler: Alter muss eine Zahl sein!",
				"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, 
				"Fehler: " + e.getMessage(),
				"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Wählt eine Person aus der Combo Box aus.
	 */
	private void personAuswaehlen() {
		int index = cbPersonAuswahl.getSelectedIndex();
		if (index >= 0 && index < manager.getAllePersonen().size()) {
			Person person = manager.getAllePersonen().get(index);
			manager.setAktuellePerson(person);
			aktualisiereAnzeige();
		}
	}
	
	/**
	 * Erstellt eine neue Messung für die aktuelle Person.
	 */
	private void erstelleMessung() {
		try {
			if (manager.getAktuellePerson() == null) {
				JOptionPane.showMessageDialog(this, 
					"Bitte zuerst eine Person erstellen oder auswählen!",
					"Keine Person", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			double gewicht = Double.parseDouble(tfGewicht.getText().trim());
			double groesse = Double.parseDouble(tfGroesse.getText().trim());
			
			Messung messung = manager.erstelleMessung(gewicht, groesse);
			
			tfErgebnis.setText(String.format("BMI: %.2f - %s", 
				messung.getBmi(), messung.getKategorie()));
			
			aktualisiereAnzeige();
			
			JOptionPane.showMessageDialog(this, 
				"Messung erfolgreich gespeichert!\nBMI: " + messung.getFormatierteBMI(),
				"Erfolg", JOptionPane.INFORMATION_MESSAGE);
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, 
				"Fehler: Gewicht und Größe müssen Zahlen sein!",
				"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, 
				"Fehler: " + e.getMessage(),
				"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Berechnet BMI ohne Person (Version 3 Kompatibilität).
	 */
	private void nurBerechnen() {
		try {
			double gewicht = Double.parseDouble(tfGewicht.getText().trim());
			double groesse = Double.parseDouble(tfGroesse.getText().trim());
			
			double bmi = manager.berechneBMI(gewicht, groesse);
			manager.interpretiereBMI();
			
			tfErgebnis.setText(String.format("BMI: %.2f - %s", 
				bmi, manager.getModel().getKategorie()));
				
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, 
				"Fehler: Gewicht und Größe müssen Zahlen sein!",
				"Eingabefehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Aktualisiert die Personen-Auswahl ComboBox.
	 */
	private void aktualisierePersonenAuswahl() {
		cbPersonAuswahl.removeAllItems();
		List<Person> personen = manager.getAllePersonen();
		
		if (personen.isEmpty()) {
			cbPersonAuswahl.addItem("Keine Personen vorhanden");
		} else {
			for (Person p : personen) {
				cbPersonAuswahl.addItem(p.getFullName() + " (" + p.getAlter() + " Jahre)");
			}
			// Letzte Person auswählen
			cbPersonAuswahl.setSelectedIndex(personen.size() - 1);
		}
	}
	
	/**
	 * Aktualisiert Historie und Statistik.
	 */
	private void aktualisiereAnzeige() {
		Person aktPerson = manager.getAktuellePerson();
		
		if (aktPerson == null) {
			taMessungsHistorie.setText("Keine Person ausgewählt.\n\nBitte zuerst eine Person erstellen!");
			taStatistik.setText("Keine Statistik verfügbar.");
			return;
		}
		
		// Messungs-Historie
		StringBuilder historie = new StringBuilder();
		historie.append("Messungen für: ").append(aktPerson.getFullName()).append("\n");
		historie.append("=============================================\n\n");
		
		List<Messung> messungen = aktPerson.getMessungen();
		if (messungen.isEmpty()) {
			historie.append("Noch keine Messungen vorhanden.\n");
			historie.append("Bitte erfassen Sie eine neue Messung!");
		} else {
			int nr = 1;
			for (Messung m : messungen) {
				historie.append(String.format("%d. %s\n", nr++, m.getFormatiertesDatum()));
				historie.append(String.format("   Gewicht: %.1f kg | Größe: %.2f m\n", 
					m.getGewicht(), m.getGroesse()));
				historie.append(String.format("   BMI: %.2f (%s)\n", 
					m.getBmi(), m.getKategorie()));
				historie.append("\n");
			}
		}
		taMessungsHistorie.setText(historie.toString());
		taMessungsHistorie.setCaretPosition(0);
		
		// Statistik
		StringBuilder statistik = new StringBuilder();
		statistik.append("STATISTIK\n");
		statistik.append("===================\n\n");
		statistik.append("Person:\n");
		statistik.append(aktPerson.getFullName()).append("\n");
		statistik.append(aktPerson.getAlter()).append(" Jahre, ");
		statistik.append(aktPerson.getGeschlecht()).append("\n\n");
		
		if (messungen.isEmpty()) {
			statistik.append("Keine Messungen\nvorhanden.");
		} else {
			statistik.append("Anzahl Messungen:\n");
			statistik.append(messungen.size()).append("\n\n");
			
			statistik.append("Durchschnitts-BMI:\n");
			statistik.append(String.format("%.2f\n\n", aktPerson.getDurchschnittsBmi()));
			
			if (aktPerson.getLetzteMessung() != null) {
				statistik.append("Letzte Messung:\n");
				statistik.append(String.format("BMI: %.2f\n", aktPerson.getLetzteMessung().getBmi()));
				statistik.append(aktPerson.getLetzteMessung().getKategorie()).append("\n\n");
			}
			
			if (messungen.size() >= 2) {
				Messung beste = aktPerson.getBesteMessung();
				Messung schlechteste = aktPerson.getSchlechtesteMessung();
				
				statistik.append("Bester BMI:\n");
				statistik.append(String.format("%.2f\n\n", beste.getBmi()));
				
				statistik.append("Schlechtester BMI:\n");
				statistik.append(String.format("%.2f\n\n", schlechteste.getBmi()));
				
				statistik.append("Differenz:\n");
				statistik.append(String.format("%.2f\n", aktPerson.getBmiDifferenz()));
			}
		}
		
		taStatistik.setText(statistik.toString());
		taStatistik.setCaretPosition(0);
	}
	
	// ========== Getter für Tests ==========
	
	public BmiManager getManager() {
		return manager;
	}
	
	public double getGewichtValue() {
		return Double.valueOf(tfGewicht.getText());
	}
	
	public double getGroesseValue() {
		return Double.valueOf(tfGroesse.getText());
	}
	
	public void setErgebnisText(String text) {
		tfErgebnis.setText(text);
	}
	
	public void clearFields() {
		tfGewicht.setText("");
		tfGroesse.setText("");
		tfErgebnis.setText("");
	}
}
