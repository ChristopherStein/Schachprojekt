package spiel;

import java.util.ArrayList;

import figuren.Figur;
import gui.*;
public class Main {

	private Spielfeld feld;
	
	public void init() {
		this.feld = new Spielfeld();
	}
	
	public static void main(String args[]) {
		Main m = new Main();
		m.init();
		Schachbrett sb=new Schachbrett();
		int a[]=sb.setFiguren(m.feld.getFeld());
		System.out.println(a[0]+" "+a[1]);
		/*
		 * An Stein:
		 * HDF
		 * Einfach nur ein bisschen zum Testen. Ich erzeuge ein Spielfeld und lasse mir alle m�glichen Z�ge
		 * anzeigen.
		 * Das funktioniert.
		 * Es w�re cool wenn du mir irgenein GUI-Objekt hochl�dst, dann kann ich machen was passiert wenn 
		 * geklickt wird und so. 
		 * Ich habe das Projekt im Eclipse erzeugt und f�r meinen Kram die Packages figuren und spiel gemacht.
		 * In spiel sind bisher Main.java, Spielfeld.java und Zug.java und in figuren der ganze andere Kram.
		 * Vielleicht machst du deins dann alles in eine Package gui rein?
		 * Achso und du bekommst dann einen Array, den ich bekomme, wenn ich m.feld.getFeld() mache. Also der,
		 * den ich auch ausgebe.
		 
		 *Lieber Tobias,
		 *
		 *dein Programm hat mir sehr gut gefallen. Trotz der schwierigkeiten, die wir zusammen beim integrieren in
		 *Eclipse hatten, funktioniert jetzt schon relativ viel.
		 *Ich habe die Klasse Schachbrett hochgeladen und eine Instanz davon in deiner Main erstellt.
		 *Mit der Methode "int[] setFiguren(Figur[][])" uebergibst du das aktuelle array und bekommst, falls
		 *der user das irgendwann macht, ein array mit i,j, also der geklickten stelle wieder.
		 *Wundere dich nicht, noch gibt es keine Schwarzen Figuren, ich ueberlege noch das man damit Schach evtl einem
		 *neuen Klientel zugaenglich machen kann, da es fuer manche schwierig ist, sich mit schwarzen zu identifizieren oder
		 *einfach nur Schach zu spielen und dabei womoeglich zu verlieren, aber wem sag ich das.
		 *Ich hoffe du ergoetzt dich am sprudelnden erguss der Zeit,die wir beide zu ehren des Prof. Dr.rer. 
		 *nat. Jobst Hoffmann auf uns nahmen, und den ersten ergebnisse seiner allumfassenden weisheit, die in uns lebt und waechst.
		 *
		 *Grues deine Frau und meine Kinder
		 *Der Tod
		 * *ungewoehnlich seit 1995*
		 */
		//testAusgabe2(m);
		/*for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (m.feld.getFeld()[i][j] != null) {
					ArrayList<Zug> zts = m.feld.moeglZuege(i, j);
					for (Zug z : zts) {
						testAusgabe(z, m);
					}
				}
			}
		}
		*/
		
		
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
