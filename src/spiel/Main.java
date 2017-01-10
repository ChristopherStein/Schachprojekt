
package spiel;

import gui.Schachbrett;

import java.util.Arrays;

public class Main {

	private Spielfeld feld;
	private Schachbrett brett;
	
	public void init() {
		this.feld = new Spielfeld();
		this.brett = new Schachbrett();
	}
	
	public static void main(String args[]) {
		Main m = new Main();
		m.init();
		int resultat = spieleSpiel(m);
		//m.brett.setFiguren(m.feld);
		m.brett.setSpielZuEnde(resultat, m.feld);
	}
	
	private static int spieleSpiel(Main m) {
		boolean wAmZug = true;
		int[] geklickt = new int[2];
		int[] davor = new int[2];
		
		while (m.feld.istZuende(wAmZug) == 0) {
			davor = Arrays.copyOf(geklickt, geklickt.length);
			geklickt = m.brett.setFiguren(m.feld);
			if (m.feld.getFeld()[geklickt[0]][geklickt[1]] == null) {
				// Leeres Feld wurde angeklickt
				
				if (m.feld.getFeld()[davor[0]][davor[1]] != null && 
						m.feld.getFeld()[davor[0]][davor[1]].isWeiss() == wAmZug) {					
					// Davor wurde eine eigene Figur angeklickt: Zug moeglich?
					Zug z = null;
					for (Zug tz : m.feld.moeglZuege(davor[0], davor[1])) { // Prüfen auf Sonderzug
						if (tz.getAltX() == davor[0] && tz.getAltY() == davor[1] &&
								 tz.getNeuX() == geklickt[0] && tz.getNeuY() == geklickt[1]) {
							if (tz instanceof Sonderzug) {
								z = tz;
							}
						}
					}
					if (z == null) {
						z = new Zug(davor[0], davor[1], geklickt[0], geklickt[1]);
					}
					
					if (m.feld.moeglZuege(davor[0], davor[1]).contains(z)) {
						Spielfeld copy = Spielfeld.buildSpielfeldFromString(m.feld.getZuegeBisher());
						copy.macheZug(z);
						if (copy.imSchach(wAmZug)) {
							// Steht der Spieler im Schach, wenn er den Zug macht?
							continue;
						}
						
						// Wenn der Zug moeglich ist, wird er ausgefuehrt.
						m.feld.macheZug(z);
						repBauer(m, z);
						
						// Der andere Spieler ist jetzt am Zug
						wAmZug = !wAmZug;
					}
				}
				// Wenn davor keine eigen Figur angeklickt wurde passiert nichts.
				continue;
			}
			if (m.feld.getFeld()[geklickt[0]][geklickt[1]].isWeiss() == wAmZug) {
				// Eigene Figur wurde angeklickt
				// Moegliche Zuege werden von Stein angezeigt -> ich mache nichts.
			} else {
				// Gegnerische Figur wurde angeklickt.
				
				if ((m.feld.getFeld()[davor[0]][davor[1]] != null) && 
						(m.feld.getFeld()[davor[0]][davor[1]].isWeiss() == wAmZug)) {					
					// Davor wurde eine eigene Figur angeklickt: Zug moeglich?
					
					Zug z = new Zug(davor[0], davor[1], geklickt[0], geklickt[1]);
					
					if (m.feld.moeglZuege(davor[0], davor[1]).contains(z)) {
						Spielfeld copy = Spielfeld.buildSpielfeldFromString(m.feld.getZuegeBisher());
						copy.macheZug(z);
						if (copy.imSchach(wAmZug)) {
							// Steht der Spieler im Schach, wenn er den Zug macht?
							continue;
						}
						
						// Wenn der Zug moeglich ist, wird er ausgefuehrt.
						m.feld.macheZug(z);
						repBauer(m, z);
						
						// Der andere Spieler ist jetzt am Zug
						wAmZug = !wAmZug;
					}
				}
			}
		}
		return m.feld.istZuende(wAmZug);
	}
	
	private static void repBauer(Main m, Zug zuletzt) {
		if (! (m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()] instanceof figuren.Bauer)) 
			return;
		if (zuletzt.getNeuX() != 0 && zuletzt.getNeuX() != 7)
			return;
		String auswahl = m.brett.bauernWahl();
		if (auswahl.equals("Dame")) {
			m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()] = 
					new figuren.Dame(m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()].isWeiss());
		}
		if (auswahl.equals("Turm")) {
			m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()] = 
					new figuren.Turm(m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()].isWeiss());
		}
		if (auswahl.equals("Springer")) {
			m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()] = 
					new figuren.Springer(m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()].isWeiss());
		}
		if (auswahl.equals("Laeufer")) {
			m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()] = 
					new figuren.Laeufer(m.feld.getFeld()[zuletzt.getNeuX()][zuletzt.getNeuY()].isWeiss());
		}
	}
	
}
