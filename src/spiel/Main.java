package spiel;

import java.util.ArrayList;
import java.util.Arrays;

import figuren.Figur;
import figuren.MoeglZug;
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
		boolean wAmZug = true;
		Figur[][] copy = m.feld.getFeld();
		int[] zuletzt = new int[2];
		while (m.feld.istZuende(wAmZug) == 0) {
			
			int a[]=sb.setFiguren(m.feld);
			copy = m.feld.getFeld();
			
			// Es wurde eine eigene Figur angeklickt
			// Kommt ja nicht mehr vor wenn du das selber machst mit den möglichen Zügen
			if (copy[a[0]][a[1]] != null && copy[a[0]][a[1]].isWeiss() != wAmZug) {
				zuletzt[0] = a[0];
				zuletzt[1] = a[1];
				copy = copy2D(copy);
				for (Zug z : m.feld.moeglZuege(a[0], a[1])) {
					if (copy[z.getNeuX()][z.getNeuY()] == null) {
						copy[z.getNeuX()][z.getNeuY()] = new MoeglZug(wAmZug);
					} else {
						copy[z.getNeuX()][z.getNeuY()].setMoegl(true);
					}
				}
			} 
			
			// Es wurde keine eigene Figur angeklickt
			Zug z = new Zug(zuletzt[0], zuletzt[1], a[0], a[1]);
			if (m.feld.moeglZuege(zuletzt[0], zuletzt[1]).contains(z)) {
				m.feld.macheZug(z);
				copy = copy2D(m.feld.getFeld());
				wAmZug = !wAmZug;
			}
			
		}
		
		/*
		 *Lieber Tobias,
		 *
		 *dein Programm hat mir sehr gut gefallen. Trotz der <großes S>schwierigkeiten, die wir zusammen beim <großes I>integrieren in
		 *Eclipse hatten, funktioniert jetzt schon relativ viel.
		 *Ich habe die Klasse Schachbrett hochgeladen und eine Instanz davon in deiner Main erstellt.
		 *Mit der Methode "int[] setFiguren(Figur[][])" uebergibst du das aktuelle <großes A>array und bekommst, falls
		 *der <großes U>user das irgendwann macht, ein <großes A>array mit i,j, also der geklickten <großes S>stelle wieder.
		 *Wundere dich nicht, noch gibt es keine <kleines s>Schwarzen Figuren, ich ueberlege noch <dass mit 2 s>das man damit Schach evtl einem
		 *neuen Klientel zugaenglich machen kann, da es fuer manche schwierig ist, sich mit <großes S>schwarzen zu identifizieren oder
		 *einfach nur Schach zu spielen und dabei womoeglich zu verlieren, aber wem sag ich das.
		 *Ich hoffe du ergoetzt dich am sprudelnden <großes E>erguss der Zeit,die wir beide zu ehren des Prof. Dr.rer. 
		 *nat. Jobst Hoffmann auf uns nahmen, und den ersten <großes E>ergebnisse seiner allumfassenden <großes W>weisheit, die in uns lebt und waechst.
		 *
		 *Grue<zwei s>s deine Frau und meine Kinder
		 *Der Tod
		 * *ungewoehnlich seit 1995*
		 * 
		 * 
		 * Lieber Christopher,
		 * 
		 * ich habe mich über deine Nachricht sehr gefreut. Deinen Kindern geht es gut, dein Ältester schlägt allerdings in letzter
		 * Zeit ständig seinen Kopf gegen die Käfigwände, der wirkt irgendwie behindert.
		 * Naja.
		 * War ja nicht anders zu erwarten, wenn du mit einer Ziege Kinder zeugst.
		 * 
		 * Ich habe mir dein Schachbrett angeguckt.
		 * Das Design müssen wir am nächsten Montag in deinem Institut bei Kaffee und Kuchen nochmal besprechen.
		 * Du wirst neben den Figuren auch noch einen möglichen Zug bekommen, in diesem Array, dies sei dir gesagt. Ich fügte
		 * in deine Klasse Schachbrett ein paar Kommentare und Änderungen ein, wegen Gründen.
		 * 
		 * Der Spielablauf ist da oben in der while-Schleife ein bisschen simuliert, ist natürlich noch nicht komplett fertig das Programm
		 * aber läuft schon relativ süß vor sich her.
		 * Zum Beispiel müssen wir uns noch was für die Historie und so überlegen.
		 * 
		 * Außerdem hat bei mir der weiße linke Turm die Koordinaten 0/0 und der schwarze linke Turm 7/7. Vielleicht kannst du das noch anpassen? :)
		 * 
		 * Mit freundlichen Grüßen
		 * Xx_ThaChessKillah_xX@twitch
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
