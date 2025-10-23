

// Die Klasse MainWindow ist die grafische Benutzeroberfläche (View) des BMI-Rechners.// Die Klasse MainWindow ist die grafische Benutzeroberfläche (View) des BMI-Rechners.

// Sie zeigt Eingabefelder, Buttons und das Ergebnis an und verbindet sich mit dem Controller (BmiManager).// Sie zeigt Eingabefelder, Buttons und das Ergebnis an und verbindet sich mit dem Controller (BmiManager).

package start;package start;



import java.awt.EventQueue;import java.awt.EventQueue;

import java.awt.GridBagLayout;import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;import java.awt.GridBagConstraints;

import java.awt.Insets;import java.awt.Insets;

import javax.swing.JFrame;import javax.swing.JFrame;

import javax.swing.JPanel;import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;import javax.swing.JLabel;

import java.awt.Font;import java.awt.Font;

import javax.swing.ImageIcon;import javax.swing.ImageIcon;

import java.awt.Color;import java.awt.Color;

import javax.swing.JTextField;import javax.swing.JTextField;

import javax.swing.JButton;import javax.swing.JButton;

import javax.swing.JComboBox;<<<<<<< HEAD

import javax.swing.JRadioButton;=======

import javax.swing.ButtonGroup;import javax.swing.JComboBox;

import java.awt.event.ActionListener;import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;import javax.swing.ButtonGroup;

>>>>>>> version-2-methoden

import java.awt.event.ActionListener;

/**import java.awt.event.ActionEvent;

 * Die MainWindow-Klasse ist das Hauptfenster der Anwendung.

 * Sie nutzt das MVC-Prinzip: View (MainWindow) -> Controller (BmiManager) -> Model (Bmirechner)

 */package start;

public class MainWindow extends JFrame {

import java.awt.EventQueue;

    private static final long serialVersionUID = 8519171689153531668L;import java.awt.GridBagLayout;

import java.awt.GridBagConstraints;

    // Das Haupt-Panel für alle GUI-Komponentenimport java.awt.Insets;

    private JPanel contentPane;import javax.swing.JFrame;

    // Eingabefeld für das Gewichtimport javax.swing.JPanel;

    private JTextField tfGewicht;import javax.swing.border.EmptyBorder;

    // Eingabefeld für die Größeimport javax.swing.JLabel;

    private JTextField tfGroesse;import java.awt.Font;

    // Feld zur Anzeige des Ergebnissesimport javax.swing.ImageIcon;

    private JTextField tfErgebnis;import java.awt.Color;

    // ComboBox für das Alter (Version 2)import javax.swing.JTextField;

    private JComboBox<String> cbAlter;import javax.swing.JButton;

    // RadioButtons für das Geschlecht (Version 2)import javax.swing.JComboBox;

    private JRadioButton rbMann;import javax.swing.JRadioButton;

    private JRadioButton rbFrau;import javax.swing.ButtonGroup;

    private ButtonGroup bgGeschlecht;import java.awt.event.ActionListener;

    // Der Controller, der die Logik steuertimport java.awt.event.ActionEvent;

    private BmiManager manager;



    public static void main(String[] args) {/**

        EventQueue.invokeLater(new Runnable() { * Die MainWindow-Klasse ist das Hauptfenster der Anwendung.

            public void run() { * Sie nutzt das MVC-Prinzip: View (MainWindow) -> Controller (BmiManager) -> Model (Bmirechner)

                try { */

                    MainWindow frame = new MainWindow();public class MainWindow extends JFrame {

                    frame.setVisible(true);

                } catch (Exception e) {    private static final long serialVersionUID = 8519171689153531668L;

                    e.printStackTrace();

                }    // Das Haupt-Panel für alle GUI-Komponenten

            }    private JPanel contentPane;

        });    // Eingabefeld für das Gewicht

    }    private JTextField tfGewicht;

    // Eingabefeld für die Größe

    private JTextField tfGroesse;

    // Feld zur Anzeige des Ergebnisses

    /**    private JTextField tfErgebnis;

     * Konstruktor: Initialisiert die GUI und verbindet sie mit dem Controller.    // ComboBox für das Alter (Version 2)

     */    private JComboBox<String> cbAlter;

    public MainWindow() {    // RadioButtons für das Geschlecht (Version 2)

        // Erzeuge den Controller (BmiManager)    private JRadioButton rbMann;

        manager = new BmiManager();    private JRadioButton rbFrau;

    private ButtonGroup bgGeschlecht;

        // Fenster-Einstellungen    // Der Controller, der die Logik steuert

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Schließen beendet die App    private BmiManager manager;

        setTitle("BMI-Rechner"); // Fenstertitel

        // Setze das Fenster-Icon (Java-Tasse oder eigenes Icon)    public static void main(String[] args) {

        setIconImage(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")).getImage());        EventQueue.invokeLater(new Runnable() {

            public void run() {

        // Haupt-Panel für die GUI                try {

        contentPane = new JPanel();                    MainWindow frame = new MainWindow();

        contentPane.setBackground(Color.WHITE); // Hintergrundfarbe                    frame.setVisible(true);

        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Abstand zum Rand                } catch (Exception e) {

        setContentPane(contentPane);                    e.printStackTrace();

                }

        // Layout-Manager: GridBagLayout für flexible Anordnung            }

        contentPane.setLayout(new GridBagLayout());        });

        GridBagConstraints gbc = new GridBagConstraints();    }

        gbc.insets = new Insets(8, 8, 8, 8); // Abstand zwischen Komponenten

        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponenten füllen horizontal

        gbc.weightx = 1.0; // Komponenten können sich ausdehnen

    /**

     * Konstruktor: Initialisiert die GUI und verbindet sie mit dem Controller.

        // Banner-Logo oben     */

        JLabel lbLogo = new JLabel();    public MainWindow() {

        lbLogo.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));        // Erzeuge den Controller (BmiManager)

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;        manager = new BmiManager();

        gbc.anchor = GridBagConstraints.CENTER;

        contentPane.add(lbLogo, gbc);        // Fenster-Einstellungen

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Schließen beendet die App

        setTitle("BMI-Rechner"); // Fenstertitel

        // Titel mit Icon        // Setze das Fenster-Icon (Java-Tasse oder eigenes Icon)

        JLabel lbTitel = new JLabel("BMI-Rechner");        setIconImage(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")).getImage());

        lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));

        lbTitel.setFont(new Font("Arial", Font.BOLD, 20));        // Haupt-Panel für die GUI

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;        contentPane = new JPanel();

        contentPane.add(lbTitel, gbc);        contentPane.setBackground(Color.WHITE); // Hintergrundfarbe

        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Abstand zum Rand

        setContentPane(contentPane);

        // Eingabefelder für Gewicht und Größe

        // Gewicht        // Layout-Manager: GridBagLayout für flexible Anordnung

        gbc.gridwidth = 1;        contentPane.setLayout(new GridBagLayout());

        gbc.gridx = 0; gbc.gridy = 2;        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.3; // Label nimmt weniger Platz        gbc.insets = new Insets(8, 8, 8, 8); // Abstand zwischen Komponenten

        gbc.anchor = GridBagConstraints.EAST;        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponenten füllen horizontal

        JLabel lbGewicht = new JLabel("Gewicht (kg):");        gbc.weightx = 1.0; // Komponenten können sich ausdehnen

        contentPane.add(lbGewicht, gbc);

        gbc.gridx = 1;

        gbc.weightx = 0.7; // TextField nimmt mehr Platz        // Banner-Logo oben

        gbc.anchor = GridBagConstraints.WEST;        JLabel lbLogo = new JLabel();

        tfGewicht = new JTextField();        lbLogo.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));

        tfGewicht.setColumns(15);        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        contentPane.add(tfGewicht, gbc);        gbc.anchor = GridBagConstraints.CENTER;

        contentPane.add(lbLogo, gbc);

        // Größe

        gbc.gridx = 0; gbc.gridy = 3;

        gbc.weightx = 0.3;        // Titel mit Icon

        gbc.anchor = GridBagConstraints.EAST;        JLabel lbTitel = new JLabel("BMI-Rechner");

        JLabel lbGroesse = new JLabel("Größe (m):");        lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));

        contentPane.add(lbGroesse, gbc);        lbTitel.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = 1;        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;

        gbc.weightx = 0.7;        contentPane.add(lbTitel, gbc);

        gbc.anchor = GridBagConstraints.WEST;

        tfGroesse = new JTextField();

        tfGroesse.setColumns(15);        // Eingabefelder für Gewicht und Größe

        contentPane.add(tfGroesse, gbc);        // Gewicht

        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2;

        // Alter (Version 2) - ComboBox        gbc.weightx = 0.3; // Label nimmt weniger Platz

        gbc.gridx = 0; gbc.gridy = 4;        gbc.anchor = GridBagConstraints.EAST;

        gbc.weightx = 0.3;        JLabel lbGewicht = new JLabel("Gewicht (kg):");

        gbc.anchor = GridBagConstraints.EAST;        contentPane.add(lbGewicht, gbc);

        JLabel lbAlter = new JLabel("Alter (Jahre):");        gbc.gridx = 1;

        contentPane.add(lbAlter, gbc);        gbc.weightx = 0.7; // TextField nimmt mehr Platz

        gbc.gridx = 1;        gbc.anchor = GridBagConstraints.WEST;

        gbc.weightx = 0.7;        tfGewicht = new JTextField();

        gbc.anchor = GridBagConstraints.WEST;        tfGewicht.setColumns(15);

                contentPane.add(tfGewicht, gbc);

        // ComboBox mit Altersgruppen

        String[] altersGruppen = {        // Größe

            "-- nicht angegeben --",        gbc.gridx = 0; gbc.gridy = 3;

            "18-24", "25-34", "35-44", "45-54",         gbc.weightx = 0.3;

            "55-64", "65-74", "75+"        gbc.anchor = GridBagConstraints.EAST;

        };        JLabel lbGroesse = new JLabel("Größe (m):");

        cbAlter = new JComboBox<>(altersGruppen);        contentPane.add(lbGroesse, gbc);

        contentPane.add(cbAlter, gbc);        gbc.gridx = 1;

        gbc.weightx = 0.7;

        // Geschlecht (Version 2) - RadioButtons        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 5;        tfGroesse = new JTextField();

        gbc.weightx = 0.3;        tfGroesse.setColumns(15);

        gbc.anchor = GridBagConstraints.EAST;        contentPane.add(tfGroesse, gbc);

        JLabel lbGeschlecht = new JLabel("Geschlecht:");

        contentPane.add(lbGeschlecht, gbc);

                // Alter (Version 2) - ComboBox

        gbc.gridx = 1;        gbc.gridx = 0; gbc.gridy = 4;

        gbc.weightx = 0.7;        gbc.weightx = 0.3;

        gbc.anchor = GridBagConstraints.WEST;        gbc.anchor = GridBagConstraints.EAST;

                JLabel lbAlter = new JLabel("Alter (Jahre):");

        // Panel für RadioButtons        contentPane.add(lbAlter, gbc);

        JPanel panelGeschlecht = new JPanel();        gbc.gridx = 1;

        panelGeschlecht.setBackground(Color.WHITE);        gbc.weightx = 0.7;

        rbMann = new JRadioButton("Männlich");        gbc.anchor = GridBagConstraints.WEST;

        rbMann.setBackground(Color.WHITE);        

        rbFrau = new JRadioButton("Weiblich");        // ComboBox mit Altersgruppen

        rbFrau.setBackground(Color.WHITE);        String[] altersGruppen = {

                    "-- nicht angegeben --",

        // ButtonGroup sorgt dafür, dass nur ein RadioButton ausgewählt werden kann            "18-24", "25-34", "35-44", "45-54", 

        bgGeschlecht = new ButtonGroup();            "55-64", "65-74", "75+"

        bgGeschlecht.add(rbMann);        };

        bgGeschlecht.add(rbFrau);        cbAlter = new JComboBox<>(altersGruppen);

                contentPane.add(cbAlter, gbc);

        panelGeschlecht.add(rbMann);

        panelGeschlecht.add(rbFrau);        // Geschlecht (Version 2) - RadioButtons

        contentPane.add(panelGeschlecht, gbc);        gbc.gridx = 0; gbc.gridy = 5;

        gbc.weightx = 0.3;

        // Ergebnisfeld (BMI/Kategorie)        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;        JLabel lbGeschlecht = new JLabel("Geschlecht:");

        gbc.weightx = 1.0;        contentPane.add(lbGeschlecht, gbc);

        gbc.anchor = GridBagConstraints.CENTER;        

        tfErgebnis = new JTextField();        gbc.gridx = 1;

        tfErgebnis.setEditable(false); // Nur Anzeige, keine Eingabe        gbc.weightx = 0.7;

        tfErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 14));        gbc.anchor = GridBagConstraints.WEST;

        tfErgebnis.setBackground(Color.LIGHT_GRAY);        

        tfErgebnis.setText("");        // Panel für RadioButtons

        contentPane.add(tfErgebnis, gbc);        JPanel panelGeschlecht = new JPanel();

        panelGeschlecht.setBackground(Color.WHITE);

        rbMann = new JRadioButton("Männlich");

        rbMann.setBackground(Color.WHITE);

        // Buttons für Aktionen        rbFrau = new JRadioButton("Weiblich");

        gbc.gridwidth = 1;        rbFrau.setBackground(Color.WHITE);

        gbc.gridy = 7;        

        gbc.gridx = 0;        // ButtonGroup sorgt dafür, dass nur ein RadioButton ausgewählt werden kann

        // Button: BMI berechnen        bgGeschlecht = new ButtonGroup();

        JButton btBerechne = new JButton("Berechne BMI");        bgGeschlecht.add(rbMann);

        btBerechne.addActionListener(new ActionListener() {        bgGeschlecht.add(rbFrau);

            public void actionPerformed(ActionEvent e) {        

                // Übergibt die Eingaben an den Controller und zeigt das Ergebnis        panelGeschlecht.add(rbMann);

                manager.berechneBMI(getGewichtValue(), getGroesseValue());        panelGeschlecht.add(rbFrau);

                schreibeErgebnis();        contentPane.add(panelGeschlecht, gbc);

            }

        });        // Ergebnisfeld (BMI/Kategorie)

        contentPane.add(btBerechne, gbc);        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;

        gbc.weightx = 1.0;

        gbc.gridx = 1;        gbc.anchor = GridBagConstraints.CENTER;

        // Button: BMI interpretieren        tfErgebnis = new JTextField();

        JButton btInterpretiere = new JButton("Interpretiere BMI");        tfErgebnis.setEditable(false); // Nur Anzeige, keine Eingabe

        btInterpretiere.addActionListener(new ActionListener() {        tfErgebnis.setFont(new Font("Tahoma", Font.PLAIN, 14));

            public void actionPerformed(ActionEvent e) {        tfErgebnis.setBackground(Color.LIGHT_GRAY);

                // Version 2: Nutzt intelligente Methodenwahl mit Alter & Geschlecht        tfErgebnis.setText("");

                manager.interpretiereIntelligent(        contentPane.add(tfErgebnis, gbc);

                    getGewichtValue(), 

                    getGroesseValue(), 

                    getAlterValue(), 

                    getGeschlechtValue()        // Buttons für Aktionen

                );        gbc.gridwidth = 1;

                manager.zeigeInterpretation();        gbc.gridy = 7;

                schreibeKategorie();        gbc.gridx = 0;

            }        // Button: BMI berechnen

        });        JButton btBerechne = new JButton("Berechne BMI");

        contentPane.add(btInterpretiere, gbc);        btBerechne.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                // Übergibt die Eingaben an den Controller und zeigt das Ergebnis

        // Button: Felder leeren                manager.berechneBMI(getGewichtValue(), getGroesseValue());

        gbc.gridy = 8;                schreibeErgebnis();

        gbc.gridx = 0;            }

        JButton btLeeren = new JButton("Leeren");        });

        btLeeren.addActionListener(new ActionListener() {        contentPane.add(btBerechne, gbc);

            public void actionPerformed(ActionEvent arg0) {

                // Löscht alle Eingabefelder und das Ergebnis        gbc.gridx = 1;

                clearFields();        // Button: BMI interpretieren

            }        JButton btInterpretiere = new JButton("Interpretiere BMI");

        });        btInterpretiere.addActionListener(new ActionListener() {

        contentPane.add(btLeeren, gbc);            public void actionPerformed(ActionEvent e) {

                // Version 2: Nutzt intelligente Methodenwahl mit Alter & Geschlecht

        // Button: Anwendung schließen                manager.interpretiereIntelligent(

        gbc.gridx = 1;                    getGewichtValue(), 

        JButton btSchliessen = new JButton("Schließen");                    getGroesseValue(), 

        btSchliessen.addActionListener(new ActionListener() {                    getAlterValue(), 

            public void actionPerformed(ActionEvent e) {                    getGeschlechtValue()

                // Beendet die Anwendung                );

                System.exit(0);                manager.zeigeInterpretation();

            }                schreibeKategorie();

        });            }

        contentPane.add(btSchliessen, gbc);        });

        contentPane.add(btInterpretiere, gbc);

        // Fenstergröße automatisch anpassen

        pack();

        // Minimale Fenstergröße festlegen        // Button: Felder leeren

        setMinimumSize(new java.awt.Dimension(400, 500));        gbc.gridy = 8;

        // Fenster zentriert auf dem Bildschirm anzeigen        gbc.gridx = 0;

        setLocationRelativeTo(null);        JButton btLeeren = new JButton("Leeren");

    }        btLeeren.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {

                // Löscht alle Eingabefelder und das Ergebnis

    /**                clearFields();

     * Gibt den Controller zurück (für andere Klassen).            }

     */        });

    public BmiManager getManager() {        contentPane.add(btLeeren, gbc);

        return manager;

    }        // Button: Anwendung schließen

        gbc.gridx = 1;

        JButton btSchliessen = new JButton("Schließen");

    /**        btSchliessen.addActionListener(new ActionListener() {

     * Liest das Gewicht aus dem Eingabefeld.            public void actionPerformed(ActionEvent e) {

     */                // Beendet die Anwendung

    public double getGewichtValue() {                System.exit(0);

        return Double.valueOf(tfGewicht.getText());            }

    }        });

        contentPane.add(btSchliessen, gbc);



    /**        // Fenstergröße automatisch anpassen

     * Liest die Größe aus dem Eingabefeld.        pack();

     */        // Minimale Fenstergröße festlegen

    public double getGroesseValue() {        setMinimumSize(new java.awt.Dimension(400, 500));

        return Double.valueOf(tfGroesse.getText());        // Fenster zentriert auf dem Bildschirm anzeigen

    }        setLocationRelativeTo(null);

    }



    /**

     * Liest das Alter aus dem ComboBox (Version 2).    /**

     * Berechnet den Mittelwert der ausgewählten Altersgruppe.     * Gibt den Controller zurück (für andere Klassen).

     * @return Alter als int (0 wenn keine Gruppe gewählt)     */

     */    public BmiManager getManager() {

    public int getAlterValue() {        return manager;

        String auswahl = (String) cbAlter.getSelectedItem();    }

        if (auswahl == null || auswahl.equals("-- nicht angegeben --")) {

            return 0;

        }    /**

             * Liest das Gewicht aus dem Eingabefeld.

        // Berechne Mittelwert der Altersgruppe     */

        switch (auswahl) {    public double getGewichtValue() {

            case "18-24": return 21;        return Double.valueOf(tfGewicht.getText());

            case "25-34": return 30;    }

            case "35-44": return 40;

            case "45-54": return 50;

            case "55-64": return 60;    /**

            case "65-74": return 70;     * Liest die Größe aus dem Eingabefeld.

            case "75+": return 75;     */

            default: return 0;    public double getGroesseValue() {

        }        return Double.valueOf(tfGroesse.getText());

    }    }





    /**    /**

     * Liest das Geschlecht aus den RadioButtons (Version 2).     * Liest das Alter aus dem ComboBox (Version 2).

     * @return "Mann", "Frau" oder null (wenn nichts ausgewählt)     * Berechnet den Mittelwert der ausgewählten Altersgruppe.

     */     * @return Alter als int (0 wenn keine Gruppe gewählt)

    public String getGeschlechtValue() {     */

        if (rbMann.isSelected()) {    public int getAlterValue() {

            return "Mann";        String auswahl = (String) cbAlter.getSelectedItem();

        } else if (rbFrau.isSelected()) {        if (auswahl == null || auswahl.equals("-- nicht angegeben --")) {

            return "Frau";            return 0;

        }        }

        return null;        

    }        // Berechne Mittelwert der Altersgruppe

        switch (auswahl) {

            case "18-24": return 21;

    /**            case "25-34": return 30;

     * Setzt den Text im Ergebnisfeld.            case "35-44": return 40;

     */            case "45-54": return 50;

    public void setErgebnisText(String text) {            case "55-64": return 60;

        tfErgebnis.setText(text);            case "65-74": return 70;

    }            case "75+": return 75;

            default: return 0;

        }

    }

    /**

     * Löscht alle Eingabefelder und das Ergebnis.

     */    /**

    public void clearFields() {     * Liest das Geschlecht aus den RadioButtons (Version 2).

        tfGewicht.setText("");     * @return "Mann", "Frau" oder null (wenn nichts ausgewählt)

        tfGroesse.setText("");     */

        tfErgebnis.setText("");    public String getGeschlechtValue() {

        cbAlter.setSelectedIndex(0); // Zurück auf "-- nicht angegeben --"        if (rbMann.isSelected()) {

        bgGeschlecht.clearSelection(); // Keine Auswahl bei RadioButtons            return "Mann";

    }        } else if (rbFrau.isSelected()) {

            return "Frau";

        }

    /**        return null;

     * Zeigt den berechneten BMI im Ergebnisfeld an.    }

     */

    private void schreibeErgebnis(){

        tfErgebnis.setText(String.format("BMI: %.2f", manager.getModel().getErgebnis()));    /**

    }     * Setzt den Text im Ergebnisfeld.

     */

    public void setErgebnisText(String text) {

    /**        tfErgebnis.setText(text);

     * Zeigt die Gewichtskategorie im Ergebnisfeld an.    }

     */

    private void schreibeKategorie(){

        tfErgebnis.setText(manager.getModel().getKategorie());

    }    /**

}     * Löscht alle Eingabefelder und das Ergebnis.

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
	private void schreibeKategorie(){
