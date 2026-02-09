# Übersicht häufig verwendeter Container-Klassen in Java

Java bietet mehrere Container-Klassen, die das `Collection`-Interface implementieren und verschiedene Möglichkeiten zum Speichern und Bearbeiten von Objektgruppen bieten. Hier erklären wir die am häufigsten verwendeten Container-Klassen, ihre APIs und typische Anwendungsfälle.

## 1. ArrayList
### API:
- **add(E e)**: Fügt das angegebene Element am Ende der Liste hinzu.
- **remove(Object o)**: Entfernt das erste Vorkommen des angegebenen Elements aus der Liste.
- **get(int index)**: Gibt das Element an der angegebenen Position in der Liste zurück.

### Anwendungsfälle:
- Wenn Sie ein dynamisches Array benötigen, das beim Hinzufügen von Elementen wächst.
- Am besten für Szenarien geeignet, bei denen häufiger Zugriff auf Elemente erforderlich ist, aber seltene Entfernungen oder Einfügungen (aufgrund der zugrunde liegenden Array-Struktur).

## 2. LinkedList
### API:
- **add(E e)**: Fügt das angegebene Element am Ende der Liste hinzu.
- **remove(Object o)**: Entfernt das erste Vorkommen des angegebenen Elements aus der Liste.
- **get(int index)**: Gibt das Element an der angegebenen Position in der Liste zurück.

### Anwendungsfälle:
- Nützlich für die Implementierung von Stacks oder Queues aufgrund effizienter Einfügungen und Löschungen an beiden Enden.
- Wenn der Speicherverbrauch wichtig ist und Sie eine Liste mit häufigen Hinzufüge-/Entfernungsoperationen benötigen.

## 3. HashSet
### API:
- **add(E e)**: Fügt das angegebene Element diesem Set hinzu, wenn es noch nicht vorhanden ist.
- **remove(Object o)**: Entfernt das angegebene Element aus diesem Set, wenn es vorhanden ist.
- **contains(Object o)**: Gibt true zurück, wenn dieses Set das angegebene Element enthält.

### Anwendungsfälle:
- Für hochperformante Sets, bei denen Sie sicherstellen müssen, dass keine Duplikate gespeichert werden.
- Nützlich für Zugehörigkeitstests, z.B. um zu prüfen, ob ein Element in einem Datensatz existiert.

## 4. TreeSet
### API:
- **add(E e)**: Ruft das angegebene Element ab und fügt es dem Set hinzu.
- **remove(Object o)**: Entfernt das angegebene Element aus diesem Set, wenn es vorhanden ist.
- **first()**: Gibt das erste (niedrigste) Element zurück.

### Anwendungsfälle:
- Wenn Sie ein sortiertes Set benötigen, das Elemente in natürlicher Reihenfolge oder nach einem angegebenen Comparator verwaltet.
- Ideal für Bereichsoperationen und wenn Sie in einer bestimmten Reihenfolge iterieren müssen.

## 5. HashMap
### API:
- **put(K key, V value)**: Verknüpft den angegebenen Wert mit dem angegebenen Schlüssel in dieser Map.
- **get(Object key)**: Gibt den Wert zurück, dem der angegebene Schlüssel zugeordnet ist, oder null, wenn diese Map keine Zuordnung für den Schlüssel enthält.
- **remove(Object key)**: Entfernt die Zuordnung für einen Schlüssel aus dieser Map, wenn sie vorhanden ist.

### Anwendungsfälle:
- Wenn Sie Schlüssel-Wert-Paare mit schnellen Suchfunktionen speichern müssen.
- Nützlich für Situationen, in denen häufige Lese- und Schreibvorgänge erwartet werden.

## 6. TreeMap
### API:
- **put(K key, V value)**: Verknüpft den angegebenen Wert mit dem angegebenen Schlüssel in dieser Map.
- **get(Object key)**: Gibt den Wert zurück, dem der angegebene Schlüssel zugeordnet ist.
- **firstKey()**: Gibt den ersten (niedrigsten) Schlüssel zurück, der sich derzeit in dieser Map befindet.

### Anwendungsfälle:
- Wenn Sie Schlüssel in natürlicher Reihenfolge speichern und eine geordnete Iteration benötigen.
- Nützlich für Szenarien, die Head-Maps, Tail-Maps und Bereichsansichten erfordern.

## Fazit
Das Verständnis dieser Container-Klassen ist entscheidend für effizientes Datenmanagement in Java, da sie je nach Anwendungsanforderungen unterschiedliche Leistungsmerkmale und Funktionsumfänge bieten.