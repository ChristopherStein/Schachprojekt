
package spiel;

import java.util.ArrayList;
import java.util.Arrays;

import figuren.Figur;
import figuren.MoeglZug;
import gui.*;
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
		
		boolean wAmZug = true;
		int[] geklickt = new int[2];
		int[] davor = new int[2];
		
		while (m.feld.istZuende(wAmZug) == 0) {
			//System.out.println(m.feld.getNotationFuerMenschen() + "\n\n\n");
			davor = Arrays.copyOf(geklickt, geklickt.length);
			geklickt = m.brett.setFiguren(m.feld);
			if (m.feld.getFeld()[geklickt[0]][geklickt[1]] == null) {
				// Leeres Feld wurde angeklickt
				
				if (m.feld.getFeld()[davor[0]][davor[1]].isWeiss() == wAmZug) {					
					// Davor wurde eine eigene Figur angeklickt: Zug möglich?
					
					Zug z = new Zug(davor[0], davor[1], geklickt[0], geklickt[1]);
					
					if (m.feld.moeglZuege(davor[0], davor[1]).contains(z)) {
						Spielfeld copy = Spielfeld.buildSpielfeldFromString(m.feld.getZuegeBisher());
						copy.macheZug(z);
						if (copy.imSchach(wAmZug)) {
							// Steht der Spieler im Schach, wenn er den Zug macht?
							continue;
						}
						
						// Wenn der Zug möglich ist, wird er ausgeführt.
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
				// Mögliche Züge werden von Stein angezeigt -> ich mache nichts.
			} else {
				// Gegnerische Figur wurde angeklickt.
				
				if ((m.feld.getFeld()[davor[0]][davor[1]] != null) && 
						(m.feld.getFeld()[davor[0]][davor[1]].isWeiss() == wAmZug)) {					
					// Davor wurde eine eigene Figur angeklickt: Zug möglich?
					
					Zug z = new Zug(davor[0], davor[1], geklickt[0], geklickt[1]);
					
					if (m.feld.moeglZuege(davor[0], davor[1]).contains(z)) {
						Spielfeld copy = Spielfeld.buildSpielfeldFromString(m.feld.getZuegeBisher());
						copy.macheZug(z);
						if (copy.imSchach(wAmZug)) {
							// Steht der Spieler im Schach, wenn er den Zug macht?
							continue;
						}
						
						// Wenn der Zug möglich ist, wird er ausgeführt.
						m.feld.macheZug(z);
						
						// Der andere Spieler ist jetzt am Zug
						wAmZug = !wAmZug;
					}
				}
			}
			
			
		}
		
	}
	
	private static Figur[][] copy2D(Figur[][] alt) {
		Figur[][] neu = new Figur[alt.length][alt[0].length];
		for (int i = 0; i < alt.length; ++i) {
			for (int j = 0; j < alt[0].length; ++j) {
				if (alt[i][j] == null) {
					neu[i][j] = null;
				} else {
					neu[i][j] = alt[i][j].copy();
				}
			}
		}
		return neu;
	}
	
}
