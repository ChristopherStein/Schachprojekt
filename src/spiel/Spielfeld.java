package spiel;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	public void macheZug(Zug z) {
		if (this.moeglZuege(z.getAltX(), z.getAltY()).contains(z)) {
			// Ich frage das zwar mehrfach ab, aber wegen konsistenter Programmierung und so ist das richtig
			this.zuegeBisher.add(z);
			feld[z.getNeuX()][z.getNeuY()] = feld[z.getAltX()][z.getAltY()];
			feld[z.getAltX()][z.getAltY()] = null;
		}
	}
	
	public ArrayList<Zug> moeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		if (feld[x][y] == null) {
			return moegl;
		}
		for (Zug z : feld[x][y].getMoeglZuege(x, y)) {
			// Wenn die Figur ein Bauer ist, darf ich nur diagonal ziehen, wenn ich schlage, 
			// und nur geradeaus ziehen, wenn ich nicht schlage.
			if (feld[z.getAltX()][z.getAltY()] instanceof Bauer) {
				
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
			//System.out.println(z);
			// Wenn die Figur kein Bauer ist, darf Sie alles schlagen, bis auf eigene Figuren,
			// aber nicht Ã¼ber Figuren hinwegziehen.
			if (feld[z.getNeuX()][z.getNeuY()] == null ||
					feld[z.getAltX()][z.getAltY()].isWeiss() != feld[z.getNeuX()][z.getNeuY()].isWeiss()) {
				if (isWegFrei(z) || feld[x][y] instanceof Springer) {
					moegl.add(z);
				}
				
			}
		}
		return moegl;
	}
	
	private boolean isWegFrei(Zug z) {
		int dx = z.getNeuX() - z.getAltX();
		int dy = z.getNeuY() - z.getAltY();
		// Figur bewegt sich auf der Y-Achse
		if (dx == 0) {
			for (int i = 1; i < Math.abs(dy); ++i) {
				if (feld[z.getAltX()]
						[z.getAltY() + i * (int) (dy / Math.abs(dy))] != null) {
					return false;
				}
			}
			return true;
		}
		// Figur bewegt sich auf der X-Achse
		if (dy == 0) {
			for (int i = 1; i < Math.abs(dx); ++i) {
				if (feld[z.getAltX() + i * (int) (dx / Math.abs(dx))]
						[z.getAltY()] != null) {
					return false;
				}
			}
			return true;
		}
		// Figur bewegt sich diagonal
		//for (int i = 1; i < Math.abs(dx); ++i) {
			for (int j = 1; j < Math.abs(dy); ++j) {
				if (feld[z.getAltX() + j * (int) (dx / Math.abs(dx))]
						[z.getAltY() + j * (int) (dy / Math.abs(dy))] != null) {
					return false;
				}
			}
		//}
		return true;
	}
	
	/**
	 * Ist das Spiel zu Ende?
	 * @param weissAmZug Ist Weiß am Zug?
	 * @return 0 = Nicht zu Ende
	 * 1 = Weiss gewinnt
	 * 2 = Schwarz gewinnt
	 */
	public int istZuende(boolean weissAmZug) {
		/* Das Spiel ist zu Ende, wenn der Spieler, der am Zug ist, entweder keinen Zug mehr hat
		 * (Patt) oder er im Schach steht und keinen Zug hat, nach dem er nicht im Schach steht.
		*/
		
		return 0;
	}
	
	public boolean imSchach(boolean weiss) {
		// Finde raus wo der Koenig ist
		int kX = -1, kY = -1;
		outter:
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (feld[i][j] instanceof Koenig) {
					if (feld[i][j].isWeiss() == weiss) {
						kX = i;
						kY = j;
						break outter;
					}
				}
			}
		}
		
		// greift eine gegnerische Figur den König an? 
		// Also, hat eine gegnerische Figur als möglichen Zug das Feld vom König?
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (feld[i][j] != null && feld[i][j].isWeiss() != weiss) {
					for (Zug z : moeglZuege(i, j)) {
						if (z.getNeuX() == kX && z.getNeuY() == kY) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public Figur[][] getFeld() {
		return this.feld;
	}

	public String getZuegeBisher() {
		StringBuilder sb = new StringBuilder();
		for (Zug z : this.zuegeBisher) {
			sb.append(z.getAltX() + "/" + z.getAltY() + " -> " + z.getNeuX() + "/" + z.getNeuY() + "\n");
		}
		return sb.toString();
	}
	
	public static Spielfeld buildSpielfeldFromString(String history) throws NumberFormatException {
		Spielfeld neu = new Spielfeld();
		if (history.equals("")) {
			return neu;
		}
		String[] zuege = history.split("\n");
		try {
			for (String s : zuege) {
				String[] temp = s.split(" -> ");
				String[] a = temp[0].split("/");
				String[] n = temp[1].split("/");
				neu.macheZug(new Zug(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(n[0]), Integer.parseInt(n[1])));
			}
		} catch (Exception e) {
			throw new NumberFormatException("Ungueltiges Format!");
		}
		return neu;
	}
}
