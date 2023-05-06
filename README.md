# Sudoku

## ALLGEMEIN

Sudoku ist ein Java multi-module-Maven CLI Projekt.
Neben dem Lösen von Sudokus stehen auch Anpassungen im Schwierigkeitsgrad
und das Anfordern von Tipps, als auch die Korrektur von Sudokus zur Verfügung.

## INSTALLATION

Vorausgesetzt wird eine Java und eine Maven installation.

```sh
    git clone https://github.com/leartpro/Sudoku.git
```

Anschließend kann die Jar wie folgt gebaut werden:
```sh
    mvn package
```
Die Jar sollte nun im Verzeichnis `ConsoleApp/target` zu finden sein.
Ausgeführt werden kann diese wie folgt:
```sh
    java -jar ConsoleApp-1.0.0.jar
```

## VERWENDUNG

Am Anfang gelangt man in ein Menü, in dem man zwischen verschiedenen Aktionen wählen kann.
Mit `.commands` kann man sich alle möglichen Befehle jederzeit anzeigen lassen.
Mit `.hint` kann man sich in einem Spiel Tipps geben lassen (ein zufälliges Feld wird gelöst)
und mit `.difficulty` kann zwischen verschiedenen Schwierigkeitsgraden gewechselt werden.
Der Schwierigkeitsgrad Noob lässt sich mit nur Sole-Candidates lösen.
Für Easy werden auch Unique-Candidates benötigt und für Medium zusätzlich Removing-Candidates.
Ein weiterer Unterschied zwischen den Schwierigkeitsgraden ist, 
dass die Anzahl der gegebenen Felder mit steigendem Schwierigkeitsgrad wie folgt abnimmt:

| Schwierigkeitsgrad | min. Anzahl gegebener Felder |
|--------------------|------------------------------|
| Noob               | 34                           |
| Easy               | 24                           |
| Medium             | 17                           |

## AUFBAU

Das Projekt beinhaltet innerhalb des Hauptmoduls die zwei Untermodule `Logic` und `ConsoleApp`.

`Logic` beinhaltet dabei die Spiellogik im package `utils` und eine Domain Klasse im package `domain`.
Zusätzlich beinhaltet das Modul `Logic` das package `controller`, welches mit der Verwaltungsklasse `Controller.java`kommt.

Das Modul `ConsoleApp` ist für den Programmablauf und die Nutzer-Interaktion zuständig.
So finden sich im package `utils` Konsolen spezifische Utility-Klassen und im package `app`,
neben der Hauptklasse `App.java` weitere verwaltende Klassen.

## QUELLCODE

Der Quellcode ist grob dokumentiert und objektorientiert strukturiert.
Im Modul `Logic` im package `solver` in der Klasse `Solver.java` kommt es,
aufgrund fehlender Implementation von Methoden vermehrt zu Warnungen.
Die Lauffähigkeit wird dadurch nicht beeinträchtigt.
Es handelt sich dabei um Methoden, um Removing-Candidates zu ermitteln.
Die Implementation dieser steht noch offen.
