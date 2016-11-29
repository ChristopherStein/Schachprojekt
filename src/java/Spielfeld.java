package spiel;

import java.util.ArrayList;

import figuren.*;

public class Spielfeld {
	
	private Figur[][] feld;
	private ArrayList<Zug> zuegeBisher;
	
	public Spielfeld() {
		this.init();
	}
	
	public void init() {
		feld = new Figur[8][8];
		feld[0][0] = new Turm(true);
		feld[0][1] = new Springer(true);
		feld[0][2] = new Laeufer(true);
		feld[0][3] = new Dame(true);
		feld[0][4] = new Koenig(true);
		feld[0][5] = new Laeufer(true);
		feld[0][6] = new Springer(true);
		feld[0][7] = new Turm(true);
		for (int i = 0; i < 8; ++i) {
			feld[1][i] = new Bauer(true);
			feld[6][i] = new Bauer(false);
		}
		for (int i = 2; i < 6; ++i) {
			for (int j = 0; j < 8; ++j) {
				feld[i][j] = null;
			}
		}
		feld[7][0] = new Turm(false);
		feld[7][1] = new Springer(false);
		feld[7][2] = new Laeufer(false);
		feld[7][3] = new Dame(false);
		feld[7][4] = new Koenig(false);
		feld[7][5] = new Laeufer(false);
		feld[7][6] = new Springer(false);
		feld[7][7] = new Turm(false);
		this.zuegeBisher = new ArrayList<Zug>();
	}
	
	public ArrayList<Zug> moeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		//System.out.println(feld[x][y].getMoeglZuege(x, y));
		for (Zug z : feld[x][y].getMoeglZuege(x, y)) {
			// Wenn die Figur ein Bauer ist, darf ich nur diagonal ziehen, wenn ich schlage, 
			// und nur geradeaus ziehen, wenn ich nicht schlage.
			if (feld[z.getAltX()][z.getNeuX()] instanceof Bauer) {
				if (z.getAltY() != z.getNeuY() && 
						feld[z.getNeuX()][z.getNeuY()] != null &&
						feld[z.getNeuX()][z.getNeuY()].isWeiss() != feld[z.getAltX()][z.getAltY()].isWeiss()) {
					moegl.add(z);
				}
				if (z.getAltY() == z.getNeuY() && 
						feld[z.getNeuX()][z.getNeuY()] == null) {
					moegl.add(z);
				}
				continue;
			}
			// Wenn die Figur kein Bauer ist, darf Sie alles schlagen, bis auf eigene Figuren,
			// aber nicht über Figuren hinwegziehen.
			if (feld[z.getNeuX()][z.getNeuY()] == null ||
					feld[z.getAltX()][z.getAltY()].isWeiss() != feld[z.getNeuX()][z.getNeuY()].isWeiss()) {
				if (isWegFrei(z)) {
					moegl.add(z);
				}
				
			}
		}
		return moegl;
	}
	
	private boolean isWegFrei(Zug z) {
		int dx = z.getNeuX() - z.getAltX();
		int dy = z.getNeuY() - z.getAltY();
		if (dx == 0) {
			for (int i = 1; i < Math.abs(dy); ++i) {
				if (feld[z.getAltX()]
						[z.getAltY() + i * (int) (dy / Math.abs(dy))] != null) {
					return false;
				}
			}
			return true;
		}
		if (dy == 0) {
			for (int i = 1; i < Math.abs(dx); ++i) {
				if (feld[z.getAltX() + i * (int) (dx / Math.abs(dx))]
						[z.getAltY()] != null) {
					return false;
				}
			}
			return true;
		}
		for (int i = 1; i < Math.abs(dx); ++i) {
			for (int j = 1; j < Math.abs(dy); ++j) {
				if (feld[z.getAltX() + i * (int) (dx / Math.abs(dx))]
						[z.getAltY() + j * (int) (dy / Math.abs(dy))] != null) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Figur[][] getFeld() {
		return this.feld;
	}
}
