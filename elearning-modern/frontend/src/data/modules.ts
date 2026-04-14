export type LearningModule = {
  id: string;
  title: string;
  level: "basic" | "advanced";
  focus: string;
  info: string;
  task: string;
  steps: string[];
  codeLang: string;
  code: string;
  transfer: string;
};

export const modules: LearningModule[] = [
  {
    id: "M-A",
    title: "OOP Basics: Klassen und Objekte",
    level: "basic",
    focus: "Modellierung, Kapselung, saubere Attribute",
    info: "Eine Klasse beschreibt den Bauplan. Ein Objekt ist die konkrete Instanz. Gute Klassen sind klein, klar und testen sich leicht.",
    task: "Modelliere eine Klasse Student mit validierten Attributen und einer Methode zur Ausgabe eines Lernprofils.",
    steps: [
      "Lege Attribute privat an (name, kurs, punktestand).",
      "Erstelle einen Konstruktor mit Eingabepruefung.",
      "Implementiere Getter statt direkter Feldzugriffe.",
      "Schreibe mindestens drei Testfaelle (gueltig, leerer Name, negativer Punktestand)."
    ],
    codeLang: "java",
    code: `public final class Student {
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
}`,
    transfer: "Erweitere Student um eine Methode, die den Lernstatus (Starter, Fortgeschritten, Profi) aus dem Punktestand bestimmt."
  },
  {
    id: "M-G",
    title: "Sichere DB-Anbindung",
    level: "advanced",
    focus: "Prepared Statements, Least Privilege, Logging",
    info: "Sicherheit ist kein Extra, sondern Teil der Funktionalitaet. Datenbankzugriffe werden nur parametrisiert und mit minimalen Rechten umgesetzt.",
    task: "Ersetze ein unsicheres SQL-Statement durch ein Prepared Statement und dokumentiere den Unterschied im Team.",
    steps: [
      "Identifiziere String-Konkatenation im SQL.",
      "Parametrisiere Query und setze Werte via setString/setInt.",
      "Ergaenze Fehlerbehandlung mit interner Log-Meldung.",
      "Schreibe einen Regressionstest mit Angriffsstring."
    ],
    codeLang: "java",
    code: `String sql = "SELECT id, vorname, nachname FROM person WHERE vorname = ?";
try (Connection conn = connector.verbinden();
     PreparedStatement ps = conn.prepareStatement(sql)) {
  ps.setString(1, inputVorname);
  try (ResultSet rs = ps.executeQuery()) {
    // mappe ResultSet -> Domain-Objekte
  }
}`,
    transfer: "Definiere ein Rollenmodell fuer Lehrkraft/Lernende/Admin und skizziere, welche Rechte jede Rolle in der DB wirklich braucht."
  }
];
