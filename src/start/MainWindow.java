package start;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame
{

	private JPanel contentPane;
	private JLabel lbBanner;
	private JTextField tfGewicht;
	private JTextField tfGroesse;
	private final ButtonGroup bgGeschlecht = new ButtonGroup();

	// Neu: als Felder, damit Action-Handler darauf zugreifen können
	private JComboBox<String> cbAlter;
	private JRadioButton rbMaennlich;
	private JRadioButton rbWeiblich;
	private JTextArea taErgebnis;

	private BmiManager manager = new BmiManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BMI-Rechner");
		setIconImage(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")).getImage());
		setBounds(100, 100, 422, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel paImages = new JPanel();
		paImages.setBounds(10, 11, 386, 156);
		contentPane.add(paImages);
		paImages.setLayout(new GridLayout(2, 1, 5, 5));
		
		lbBanner = new JLabel("");
		paImages.add(lbBanner);
		lbBanner.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/logo_final.png")));
		
		JLabel lbTitel = new JLabel("BMI-Rechner");
		paImages.add(lbTitel);
		lbTitel.setIcon(new ImageIcon(MainWindow.class.getResource("/start/images/ic_launcher.png")));
		lbTitel.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JPanel paInput = new JPanel();
		paInput.setBounds(10, 189, 386, 128);
		contentPane.add(paInput);
		paInput.setLayout(new GridLayout(0, 2, 2, 2));
		
		JLabel lbGewicht = new JLabel("Gewicht (kg):");
		paInput.add(lbGewicht);
		
		tfGewicht = new JTextField();
		paInput.add(tfGewicht);
		tfGewicht.setText("");
		tfGewicht.setColumns(10);
		
		JLabel lbGroesse = new JLabel("Gr\u00F6\u00DFe (m):");
		paInput.add(lbGroesse);
		
		tfGroesse = new JTextField();
		paInput.add(tfGroesse);
		tfGroesse.setText("");
		tfGroesse.setColumns(10);
		
		JLabel lbAlter = new JLabel("Alter (Jahre):");
		paInput.add(lbAlter);
		
		String[] altersgruppen =new String[] {"-- nicht angegeben --", "18-25", "25-34", "35-44", "45-54", "55-64", "65-74", "75+"};
		cbAlter = new JComboBox<>(altersgruppen);
		paInput.add(cbAlter);

		
		JLabel lbGeschlecht = new JLabel("Geschlecht:");
		paInput.add(lbGeschlecht);
		
		JPanel paGeschlechter = new JPanel();
		paInput.add(paGeschlechter);
		
		rbMaennlich = new JRadioButton("M\u00E4nnlich");
		paGeschlechter.add(rbMaennlich);
		bgGeschlecht.add(rbMaennlich);
		
		rbWeiblich = new JRadioButton("Weiblich");
		paGeschlechter.add(rbWeiblich);
		bgGeschlecht.add(rbWeiblich);
		
		JScrollPane spErgebnis = new JScrollPane();
		spErgebnis.setBounds(10, 328, 386, 51);
		contentPane.add(spErgebnis);
		
		JPanel paOutput = new JPanel();
		spErgebnis.setViewportView(paOutput);
		paOutput.setLayout(new GridLayout(1, 1, 5, 5));
		
		taErgebnis = new JTextArea();
		taErgebnis.setColumns(4);
		taErgebnis.setText("");
		paOutput.add(taErgebnis);
		
		JPanel paActions = new JPanel();
		paActions.setBounds(10, 449, 386, 62);
		contentPane.add(paActions);
		paActions.setLayout(new GridLayout(2, 2, 5, 5));
		
		JButton btBerechneBmi = new JButton("Berechne BMI");
		paActions.add(btBerechneBmi);
		
		JButton btInterpretiereBmi = new JButton("Interpretiere BMI");
		paActions.add(btInterpretiereBmi);
		
		JButton btnBtleeren = new JButton("Leeren");
		paActions.add(btnBtleeren);
		
		JButton btSchliessen = new JButton("Schlie\u00DFen");
		paActions.add(btSchliessen);

		// Action-Handler
		btBerechneBmi.addActionListener(e -> {
			try {
				double gewicht = Double.parseDouble(tfGewicht.getText().trim());
				double groesse = Double.parseDouble(tfGroesse.getText().trim());
				double bmi = manager.berechneBMI(gewicht, groesse);
				taErgebnis.setText(String.format("BMI: %.2f", bmi));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Bitte gültige Zahlen für Gewicht und Größe eingeben.", "Eingabefehler", JOptionPane.WARNING_MESSAGE);
			}
		});

		btInterpretiereBmi.addActionListener(e -> {
			try {
				double gewicht = Double.parseDouble(tfGewicht.getText().trim());
				double groesse = Double.parseDouble(tfGroesse.getText().trim());
				int alter = mapAlterToMittelwert((String) cbAlter.getSelectedItem());
				String geschlecht = rbMaennlich.isSelected() ? "männlich" : (rbWeiblich.isSelected() ? "weiblich" : null);

				manager.interpretiereIntelligent(gewicht, groesse, alter, geschlecht);
				manager.zeigeInterpretation();
				taErgebnis.setText(manager.getModel().getKategorie());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Bitte gültige Zahlen für Gewicht und Größe eingeben.", "Eingabefehler", JOptionPane.WARNING_MESSAGE);
			}
		});

		btnBtleeren.addActionListener(e -> {
			tfGewicht.setText("");
			tfGroesse.setText("");
			cbAlter.setSelectedIndex(0);
			bgGeschlecht.clearSelection();
			taErgebnis.setText("");
		});

		btSchliessen.addActionListener(e -> System.exit(0));

		setLocationRelativeTo(null);
	}

	private int mapAlterToMittelwert(String auswahl) {
		if (auswahl == null || auswahl.startsWith("--")) return 0;
		int value = 0;
		switch (auswahl) {
			case "18-25": value = 22; break;
			case "25-34": value = 30; break;
			case "35-44": value = 40; break;
			case "45-54": value = 50; break;
			case "55-64": value = 60; break;
			case "65-74": value = 70; break;
			case "75+": value = 75; break;
			default: value = 0; break;
		}
		return value;
	}
}
