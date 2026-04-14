package de.schule.elearning.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    public List<LearningModule> findAll() {
        return List.of(
            new LearningModule(
                "M-A",
                "OOP Basics: Klassen und Objekte",
                "basic",
                "Modellierung, Kapselung, saubere Attribute",
                "Eine Klasse beschreibt den Bauplan. Ein Objekt ist die konkrete Instanz. "
                    + "Gute Klassen sind klein, klar und testen sich leicht.",
                "Modelliere eine Klasse Student mit validierten Attributen "
                    + "und einer Methode zur Ausgabe eines Lernprofils.",
                List.of(
                    "Lege Attribute privat an (name, kurs, punktestand).",
                    "Erstelle einen Konstruktor mit Eingabepruefung.",
                    "Implementiere Getter statt direkter Feldzugriffe.",
                    "Schreibe mindestens drei Testfaelle (gueltig, leerer Name, negativer Punktestand)."
                ),
                "java",
                """
                public final class Student {
                  private final String name;
                  private final String kurs;
                  private int punktestand;

                  public Student(String name, String kurs, int punktestand) {
                    if (name == null || name.isBlank()) throw new IllegalArgumentException("name leer");
                    if (kurs == null || kurs.isBlank()) throw new IllegalArgumentException("kurs leer");
                    if (punktestand < 0) throw new IllegalArgumentException("punktestand negativ");
                    this.name = name.trim();
                    this.kurs = kurs.trim();
                    this.punktestand = punktestand;
                  }

                  public String profil() {
                    return name + " besucht " + kurs + " und hat " + punktestand + " Punkte.";
                  }
                }""",
                "Erweitere Student um eine Methode, die den Lernstatus "
                    + "(Starter, Fortgeschritten, Profi) aus dem Punktestand bestimmt."
            ),
            new LearningModule(
                "M-B",
                "MVC: Model, View, Controller",
                "basic",
                "Trennung der Verantwortlichkeiten, Event-Listener, saubere Schnittstellen",
                "MVC trennt Daten (Model), Darstellung (View) und Steuerung (Controller). "
                    + "Das macht Code testbar, wartbar und erweiterbar.",
                "Baue eine einfache Notenverwaltung nach MVC: "
                    + "Model haelt die Noten, View zeigt sie an, Controller verbindet beide.",
                List.of(
                    "Erstelle die Klasse Notenrechner (Model) mit List<Double> noten.",
                    "Implementiere durchschnitt(), beste() und schlechteste().",
                    "Baue eine View-Klasse, die Ergebnisse entgegennimmt und ausgibt.",
                    "Verbinde beides in einem Controller mit Event-Listener.",
                    "Schreibe Unit-Tests fuer das Model."
                ),
                "java",
                """
                // Model
                public class Notenrechner {
                  private final List<Double> noten = new ArrayList<>();

                  public void addNote(double note) {
                    if (note < 1 || note > 6) throw new IllegalArgumentException("Note 1-6 erwartet");
                    noten.add(note);
                  }

                  public double durchschnitt() {
                    return noten.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                  }
                }

                // Controller (Ausschnitt)
                berechnenButton.addActionListener(e -> {
                  try {
                    model.addNote(Double.parseDouble(noteField.getText()));
                    view.zeigeErgebnis(model.durchschnitt());
                  } catch (IllegalArgumentException ex) {
                    view.zeigeFehler(ex.getMessage());
                  }
                });""",
                "Ergaenze eine Persistenzschicht: Noten werden beim Beenden in einer JSON-Datei gespeichert."
            ),
            new LearningModule(
                "M-C",
                "Algorithmen und Kontrollstrukturen",
                "basic",
                "if/else, switch, for, while, Struktogramm",
                "Kontrollstrukturen bestimmen den Programmfluss. "
                    + "Gute Algorithmen sind nachvollziehbar, effizient und testbar.",
                "Implementiere einen Rabattrechner: Staffelrabatt je nach Bestellmenge, "
                    + "validiert, mit Unit-Tests.",
                List.of(
                    "Entwirf das Struktogramm auf Papier.",
                    "Implementiere die Fallunterscheidung (if-else oder switch).",
                    "Validiere Eingaben (Menge > 0, Preis > 0).",
                    "Teste alle Grenzbereiche (genau 10, 11, 49, 50 Stueck usw.)."
                ),
                "java",
                """
                public double berechneRabatt(int menge, double einzelpreis) {
                  if (menge <= 0 || einzelpreis <= 0)
                    throw new IllegalArgumentException("Menge und Preis muessen positiv sein");

                  double rabatt = switch (menge / 10) {
                    case 0 -> 0.0;
                    case 1, 2, 3, 4 -> 0.05;
                    default -> 0.10;
                  };
                  return menge * einzelpreis * (1 - rabatt);
                }""",
                "Erweitere um einen Treuerabatt: Stammkunden erhalten zusaetzlich 2 % auf jeden Kauf."
            ),
            new LearningModule(
                "M-D",
                "Vererbung und Polymorphie",
                "advanced",
                "extends, super, @Override, abstrakte Klassen, Polymorphie",
                "Vererbung ermoeglicht Wiederverwendung. Polymorphie erlaubt es, "
                    + "Objekte unterschiedlicher Unterklassen einheitlich anzusprechen.",
                "Modelliere ein Fuhrpark-System: Fahrzeug (abstrakt) -> PKW, LKW. "
                    + "Jede Unterklasse berechnet Mautkosten unterschiedlich.",
                List.of(
                    "Definiere abstrakte Klasse Fahrzeug mit gemeinsamen Attributen.",
                    "Erstelle PKW und LKW als Unterklassen.",
                    "Ueberschreibe berechneMaut() in jeder Unterklasse (@Override).",
                    "Verwalte alle Fahrzeuge in einer List<Fahrzeug> – teste Polymorphie."
                ),
                "java",
                """
                public abstract class Fahrzeug {
                  private final String kennzeichen;
                  private final int baujahr;

                  public Fahrzeug(String kennzeichen, int baujahr) {
                    this.kennzeichen = kennzeichen;
                    this.baujahr = baujahr;
                  }

                  public abstract double berechneMaut(int km);

                  public String kennzeichen() { return kennzeichen; }
                }

                public class PKW extends Fahrzeug {
                  @Override
                  public double berechneMaut(int km) { return km * 0.07; }
                }

                public class LKW extends Fahrzeug {
                  private final double nutzlastTonnen;
                  public LKW(String kz, int bj, double nutzlast) {
                    super(kz, bj);
                    this.nutzlastTonnen = nutzlast;
                  }
                  @Override
                  public double berechneMaut(int km) { return km * (0.17 + nutzlastTonnen * 0.01); }
                }""",
                "Ergaenze Elektrofahrzeug als weitere Unterklasse mit 0 Maut unter 100 km/Tag."
            ),
            new LearningModule(
                "M-E",
                "Assoziationen (1:N und M:N)",
                "advanced",
                "Hat-Beziehungen, ArrayList, Bridge-Klasse, UML",
                "Assoziationen verbinden Klassen. 1:N bedeutet: eine Person hat viele Messungen. "
                    + "M:N braucht eine Bridge-Klasse (Verbindungsobjekt).",
                "Modelliere Person (1) und Messung (N): Eine Person kann beliebig viele "
                    + "BMI-Messungen haben. Implementiere Hinzufuegen, Anzeigen und Loeschen.",
                List.of(
                    "Erstelle Klassen Person und Messung.",
                    "Person haelt eine List<Messung> als Attribut.",
                    "Implementiere addMessung(), getMessung() und removeMessung().",
                    "Validiere Eingaben (Groesse 100-250 cm, Gewicht 30-300 kg).",
                    "Schreibe Tests fuer 0, 1 und viele Messungen."
                ),
                "java",
                """
                public class Person {
                  private final String id;
                  private final String name;
                  private final List<Messung> messungen = new ArrayList<>();

                  public void addMessung(double groesseCm, double gewichtKg) {
                    if (groesseCm < 100 || groesseCm > 250)
                      throw new IllegalArgumentException("Groesse ausserhalb des gueltigen Bereichs");
                    if (gewichtKg < 30 || gewichtKg > 300)
                      throw new IllegalArgumentException("Gewicht ausserhalb des gueltigen Bereichs");
                    messungen.add(new Messung(groesseCm, gewichtKg));
                  }

                  public List<Messung> getMessungen() { return Collections.unmodifiableList(messungen); }
                }""",
                "Erweitere: Berechne den Durchschnitts-BMI ueber alle Messungen und visualisiere den Trend."
            ),
            new LearningModule(
                "M-G",
                "Sichere DB-Anbindung",
                "advanced",
                "Prepared Statements, Least Privilege, Audit-Logging",
                "Sicherheit ist kein Extra, sondern Teil der Funktionalitaet. "
                    + "Datenbankzugriffe werden nur parametrisiert und mit minimalen Rechten umgesetzt.",
                "Ersetze ein unsicheres SQL-Statement durch ein Prepared Statement "
                    + "und dokumentiere den Unterschied im Team.",
                List.of(
                    "Identifiziere String-Konkatenation im SQL.",
                    "Parametrisiere Query und setze Werte via setString/setInt.",
                    "Ergaenze Fehlerbehandlung mit interner Log-Meldung.",
                    "Schreibe einen Regressionstest mit Angriffsstring."
                ),
                "java",
                """
                String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ?";
                try (Connection conn = connector.verbinden();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                  ps.setString(1, inputVorname);
                  try (ResultSet rs = ps.executeQuery()) {
                    // mappe ResultSet -> Domain-Objekte
                  }
                }""",
                "Definiere ein Rollenmodell fuer Lehrkraft/Lernende/Admin und skizziere, "
                    + "welche Rechte jede Rolle in der DB wirklich braucht."
            ),
            new LearningModule(
                "M-H",
                "Qualitaet: Testing und Secure Coding",
                "advanced",
                "Unit-Tests, OWASP Top 10, Eingabevalidierung, Audit-Logging",
                "Qualitaet entsteht nicht am Ende, sondern waehrend der Entwicklung. "
                    + "Tests und Secure Coding verhindern Fehler systematisch.",
                "Schreibe fuer eine bestehende Klasse vollstaendige JUnit-Tests "
                    + "und sichere alle Eingaben mit Whitelist-Validierung ab.",
                List.of(
                    "Identifiziere alle public-Methoden als Testpunkte.",
                    "Schreibe je einen Test fuer: gueltige Eingabe, Grenzwert, ungueltige Eingabe.",
                    "Fuege Whitelist-Validierung fuer alle Eingaben hinzu.",
                    "Protokolliere Sicherheitsereignisse im Audit-Log (ohne Klartextdaten).",
                    "Pruefe: kein Stack-Trace sichtbar fuer Endnutzer."
                ),
                "java",
                """
                @Test
                void addMessung_gueltigeEingabe_wirdGespeichert() {
                  Person p = new Person("P1", "Test");
                  p.addMessung(175.0, 70.0);
                  assertEquals(1, p.getMessungen().size());
                }

                @Test
                void addMessung_groesseZuKlein_wurfIllegalArgument() {
                  Person p = new Person("P1", "Test");
                  assertThrows(IllegalArgumentException.class,
                    () -> p.addMessung(50.0, 70.0));
                }

                // Secure Coding: Whitelist-Validierung
                public static boolean isValidName(String name) {
                  if (name == null || name.trim().isEmpty()) return false;
                  if (name.length() > 50) return false;
                  return name.matches("^[a-zA-ZäöüßÄÖÜ\\\\-\\\\s]+$");
                }""",
                "Fuehre einen kompletten Security-Review fuer eine eurer Versionen durch: "
                    + "Liste alle Eingaben auf und pruefe, ob jede validiert ist."
            )
        );
    }
}
