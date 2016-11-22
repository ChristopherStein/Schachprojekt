package spiel;

import java.util.ArrayList;

public class Main {

	private Spielfeld feld;
	
	public void init() {
		this.feld = new Spielfeld();
	}
	
	public static void main(String args[]) {
		Main m = new Main();
		m.init();
		/*
		 * An Stein:
		 * Einfach nur ein bisschen zum Testen. Ich erzeuge ein Spielfeld und lasse mir alle möglichen Züge
		 * anzeigen.
		 * Das funktioniert.
		 * Es wäre cool wenn du mir irgenein GUI-Objekt hochlädst, dann kann ich machen was passiert wenn 
		 * geklickt wird und so. 
		 * Ich habe das Projekt im Eclipse erzeugt und für meinen Kram die Packages figuren und spiel gemacht.
		 * In spiel sind bisher Main.java, Spielfeld.java und Zug.java und in figuren der ganze andere Kram.
		 * Vielleicht machst du deins dann alles in eine Package gui rein?
		 * Achso und du bekommst dann einen Array, den ich bekomme, wenn ich m.feld.getFeld() mache. Also der,
		 * den ich auch ausgebe.
		 */
		testAusgabe2(m);
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (m.feld.getFeld()[i][j] != null) {
					ArrayList<Zug> zts = m.feld.moeglZuege(i, j);
					for (Zug z : zts) {
						testAusgabe(z, m);
					}
				}
			}
		}
	}
	
	public static void testAusgabe2(Main m) {
		for (int i = 7; i >= 0; --i) {
			for (int j = 0; j < 8; ++j) {
				if (m.feld.getFeld()[i][j] == null) {
					System.out.print("leer \t\t");
					continue;
				}
				if (m.feld.getFeld()[i][j] instanceof figuren.Springer || m.feld.getFeld()[i][j] instanceof figuren.MoeglZug) {
					System.out.print(m.feld.getFeld()[i][j].getClass().getSimpleName() + "\t");
					continue;
				}
				System.out.print(m.feld.getFeld()[i][j].getClass().getSimpleName() + "\t\t");
			}
			System.out.println();
		}
	}
	
	public static void testAusgabe(Zug z, Main m) {
		System.out.println("Zug: " + m.feld.getFeld()[z.getAltX()][z.getAltY()].getClass().getSimpleName() + " von "
				+ z.getAltX() + "/" + z.getAltY() + " nach " + z.getNeuX() + "/" + z.getNeuY());
	}
	
}
