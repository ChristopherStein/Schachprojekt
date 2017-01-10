
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
		m.brett.setFiguren(m.feld);
		m.brett.setSpielZuEnde(resultat);
	}
	
	private static int spieleSpiel(Main m) {
		boolean wAmZug = true;
		int[] geklickt = new int[2];
		int[] davor = new int[2];
		
		while (m.feld.istZuende(wAmZug) == 0) {
			//System.out.println(m.feld.getNotationFuerMenschen() + "\n\n\n");
			davor = Arrays.copyOf(geklickt, geklickt.length);
			geklickt = m.brett.setFiguren(m.feld);
			if (m.feld.getFeld()[geklickt[0]][geklickt[1]] == null) {
				// Leeres Feld wurde angeklickt
				
				if (m.feld.getFeld()[davor[0]][davor[1]] != null && 
						m.feld.getFeld()[davor[0]][davor[1]].isWeiss() == wAmZug) {					
					// Davor wurde eine eigene Figur angeklickt: Zug moeglich?
					Zug z = null;
					for (Zug tz : m.feld.moeglZuege(davor[0], davor[1])) { // Pr�fen auf Sonderzug
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
						
						// Der andere Spieler ist jetzt am Zug
						wAmZug = !wAmZug;
					}
				}
			}
		}
		return m.feld.istZuende(wAmZug);
	}
	
}
