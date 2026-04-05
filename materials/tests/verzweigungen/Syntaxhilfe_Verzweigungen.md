# Syntaxhilfe: Verzweigungen

**Ziel:** Entscheidungen mit if/else und switch.

---

## if / else

```java
if (punkte >= 50) {
    System.out.println("bestanden");
} else {
    System.out.println("nicht bestanden");
}
```

---

## else-if Kette

```java
if (note == 1) {
    System.out.println("sehr gut");
} else if (note == 2) {
    System.out.println("gut");
} else {
    System.out.println("andere");
}
```

---

## switch

```java
switch (tag) {
    case "MO":
        System.out.println("Montag");
        break;
    case "DI":
        System.out.println("Dienstag");
        break;
    default:
        System.out.println("unbekannt");
}
```

---

## ternary

```java
String status = (alter >= 18) ? "volljaehrig" : "minderjaehrig";
```

---

## Tipps

- Bedingungen mit && und || kombinieren.
- Guard Clauses: frueh abbrechen.
