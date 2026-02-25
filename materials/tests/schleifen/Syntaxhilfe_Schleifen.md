# Syntaxhilfe: Schleifen

**Ziel:** Wiederholungen korrekt umsetzen.

---

## for-Schleife

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

---

## while-Schleife

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}
```

---

## do-while

```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

---

## for-each

```java
for (String s : liste) {
    System.out.println(s);
}
```

---

## break / continue

```java
for (int i = 0; i < 10; i++) {
    if (i == 3) continue;
    if (i == 8) break;
}
```
