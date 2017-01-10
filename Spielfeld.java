package spiel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import figuren.*;

public class Spielfeld {
	
	private Figur[][] feld;
	private ArrayList<Zug> zuegeBisher;
	private boolean rochadeWeissKurz;
	private boolean rochadeSchwarzKurz;
	private boolean rochadeWeissLang;
	private boolean rochadeSchwarzLang;
	private ArrayList<Zug> enPassant;
	
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
		this.rochadeWeissKurz = true;
		this.rochadeSchwarzKurz = true;
		this.rochadeSchwarzLang = true;
		this.rochadeWeissLang = true;
		this.enPassant = new ArrayList<Zug>();
	}
	
	public void macheZug(Zug z) {
		if (this.moeglZuege(z.getAltX(), z.getAltY(), false).contains(z)) {
			if (feld[z.getAltX()][z.getAltY()] instanceof Koenig) {
				if (feld[z.getAltX()][z.getAltY()].isWeiss()) {
					this.rochadeWeissKurz = false;
					this.rochadeWeissLang = false;
				} else {
					this.rochadeSchwarzKurz = false;
					this.rochadeSchwarzLang = false;
				}
			}
			if (feld[z.getAltX()][z.getAltY()] instanceof Turm) {
				if (feld[z.getAltX()][z.getAltY()].isWeiss()) {
					if (z.getAltY() == 0) {
						this.rochadeWeissLang = false;
					} else if (z.getAltY() == 7) {
						this.rochadeWeissKurz = false;
					}
					
				} else {
					if (z.getAltY() == 0) {
						this.rochadeSchwarzLang = false;
					} else if (z.getAltY() == 7) {
						this.rochadeSchwarzKurz = false;
					}
				}
			}
			if (z instanceof Sonderzug) {
				if (this.feld[z.getAltX()][z.getAltY()] instanceof Koenig) {
					// Rochade
					
				}
			}
			this.zuegeBisher.add(z);
			feld[z.getNeuX()][z.getNeuY()] = feld[z.getAltX()][z.getAltY()];
			feld[z.getAltX()][z.getAltY()] = null;
		}
	}
	
	public ArrayList<Zug> moeglZuege(int x, int y) {
		return moeglZuege(x, y, true);
	}
	
	public ArrayList<Zug> moeglZuege(int x, int y, boolean pruefeSchach) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		if (feld[x][y] == null) {
			return moegl;
		}
		for (Zug z : feld[x][y].getMoeglZuege(x, y)) {
			// Bei Koenigen muss ich auf Rochade pruefen
			if (z instanceof Sonderzug) {
				
			}
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
			// aber nicht über Figuren hinwegziehen.
			if (feld[z.getNeuX()][z.getNeuY()] == null ||
					feld[z.getAltX()][z.getAltY()].isWeiss() != feld[z.getNeuX()][z.getNeuY()].isWeiss()) {
				if (isWegFrei(z) || feld[x][y] instanceof Springer) {
					moegl.add(z);
				}
				
			}
		}
		if (pruefeSchach) {
			Iterator<Zug> i = moegl.iterator();
			while (i.hasNext()) {
				Spielfeld temp = Spielfeld.buildSpielfeldFromString(this.getZuegeBisher());
				temp.macheZug(i.next());
				if (temp.imSchach(feld[x][y].isWeiss())) {
					i.remove();
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
	 * @param weissAmZug Ist Wei� am Zug?
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
		
		// greift eine gegnerische Figur den K�nig an? 
		// Also, hat eine gegnerische Figur als m�glichen Zug das Feld vom K�nig?
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (feld[i][j] != null && feld[i][j].isWeiss() != weiss) {
					for (Zug z : moeglZuege(i, j, false)) {
						if (z.getNeuX() == kX && z.getNeuY() == kY) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Gibt das Spielfeld zurueck. An den Stellen [0][0] und  [0][7] stehen zu 
	 * Spielbeginn die wei�en T�rme (die Schwarzen stehen gegen�ber).
	 * @return ein 2d-Array von der Klasse Figur. 
	 */
	public Figur[][] getFeld() {
		return this.feld;
	}
	
	/**
	 * Gibt einen String in der f�r Menschen lesbaren Schachnotation zurueck.
	 * Ich kann aus dieser Notation schwerer ein Spiel bauen, deshalb habe ich
	 * noch die Andere drinnen. Diese hier ist nur zum Anzeigen da.
	 * @return
	 */
	public String getNotationFuerMenschen() {
		StringBuilder sb = new StringBuilder();
		Spielfeld tmp = new Spielfeld();
		boolean wamzug = true;
		int i = 1;
		for (Zug z : this.zuegeBisher) {
			if (wamzug) { 
				sb.append("\n" + i + ": ");
				++i;
			} else {
				sb.append("  ");
			}
			wamzug = ! wamzug;
			String zug = "-";
			if (tmp.feld[z.getNeuX()][z.getNeuY()] != null) {
				zug = "x";
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Bauer) {
				sb.append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Turm) {
				sb.append("T").append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Springer) {
				sb.append("S").append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Laeufer) {
				sb.append("L").append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Dame) {
				sb.append("D").append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			if (tmp.feld[z.getAltX()][z.getAltY()] instanceof Koenig) {
				sb.append("K").append((char)((int)'a' + z.getAltY())).append(z.getAltX() + 1);
				sb.append(zug).append((char)((int)'a' + z.getNeuY())).append(z.getNeuX() + 1);
			}
			tmp.macheZug(z);
			if (tmp.imSchach(wamzug)) 
				sb.append("+");
		}
		return sb.toString();
	}
	
	/**
	 * Gibt den Spielstand zurueck, wie die Spiele gespeichert werden.
	 * @return ein String.
	 */
	public String getZuegeBisher() {
		StringBuilder sb = new StringBuilder();
		for (Zug z : this.zuegeBisher) {
			sb.append(z.getAltX() + "/" + z.getAltY() + " -> " + z.getNeuX() + "/" + z.getNeuY() + "\n");
		}
		return sb.toString();
	}
	
	/** 
	 * Baut ein Spielfeld aus einem String, wie er von getZuegeBisher() 
	 * zur�ckgegeben wird. Also diese Funktion benutzt man zum Laden.
	 * Die Methode ist statisch. Sie gibt ein Spielfeld zur�ck, und man
	 * kann sie nicht auf ein Objekt aufrufen.
	 * @param history String, wo das bisherige Spiel kodiert ist
	 * @return Gibt ein fertiges Spielfeld zur�ck.
	 * @throws NumberFormatException Wird geworfen, wenn der String fehlerhaft ist.
	 */
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