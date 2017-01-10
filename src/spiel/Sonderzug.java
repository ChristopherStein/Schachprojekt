package spiel;

public class Sonderzug extends Zug {
	
	private boolean rochadeKlein;
	private int geschlagenx;
	private int geschlageny;
	
	public Sonderzug(int altx, int alty, int neux, int neuy, String typ) {
		super(altx, alty, neux, neuy);
		if (typ.equals("Rochade")) {
			if (neux == 6) {
				this.rochadeKlein = true;
			} else if (neux == 2) {
				this.rochadeKlein = false;
			} else {
				System.out.println("Ungueltige Rochade uebergeben!");
			}
		}
		if (typ.equals("enPassant")) {
			this.geschlagenx = neux;
			geschlageny = alty;
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
