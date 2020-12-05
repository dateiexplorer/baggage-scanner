# Wichtiger Hinweis zum Testen
Dass die Tests erfolgreich ausgeführt werden können, kann es möglich sein, dass zunächst die Daten für die Gepäckstücke generiert
werden müssen.
Die Daten können mit Hilfe der DataGenerator-Klasse generiert werden, indem deren Main-Methode ausgeführt wird.

# Liste von Annahmen, die für die Simulation getroffen wurden

* Das Starten und Herunterfahren des Gepäckscanners ist nur dem Supervisor erlaubt.
* Für Testzwecke gibt es noch (mindestens) einen weiteren Typ von Mitarbeitern: HouseKeeping, sodass Test (4) getestet werden kann.
* Der Ausweis hat ein Datum, nach dessen Ablauf der Ausweis ungültig ist. Das Ablaufdatum kann in der Konfigurationsdatei (Configuration) angepasst werden.
* Das Entsperren des Gepäckscanners nach einem Alarm (LOCKED) muss nicht am Lesegerät durchgeführt werden.
* Nur der Supervisor kann mit seinem Ausweis einen Gepäckscanner im Status LOCKED entsperren.
