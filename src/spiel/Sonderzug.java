package spiel;

public class Sonderzug extends Zug {
	
	private boolean rochadeKlein;
	private int geschlagenx;
	private int geschlageny;
	
	public Sonderzug(int altx, int alty, int neux, int neuy, String typ) {
		super(altx, alty, neux, neuy);
		if (typ.equals("Rochade")) {
			if (neuy == 6) {
				this.rochadeKlein = true;
			} else if (neuy == 2) {
				this.rochadeKlein = false;
			} else {
				typ = "Ungueltig";
			}
		}
		if (typ.equals("enPassant")) {
			this.geschlagenx = altx;
			geschlageny = neuy;
		}
	}

	public boolean isRochadeKlein() {
		return this.rochadeKlein;
	}

	public int getGeschlagenx() {
		return this.geschlagenx;
	}
	
	public int getGeschlageny() {
		return this.geschlageny;
	}
}
