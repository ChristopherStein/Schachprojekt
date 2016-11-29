package spiel;

public class Zug {

	private int altx, alty, neux, neuy;
	
	public Zug(int altx, int alty, int neux, int neuy) {
		this.altx = altx;
		this.alty = alty;
		this.neux = neux;
		this.neuy = neuy;
	}
	
	public int getAltX() {
		return this.altx;
	}

	public int getAltY() {
		return this.alty;
	}

	public int getNeuX() {
		return this.neux;
	}

	public int getNeuY() {
		return this.neuy;
	}
	
	public String toString() {
		return this.altx + "/" + this.alty + " nach " + this.neux + "/" + this.neuy;
	}
	
	public boolean equals(Object o) {
		Zug other = (Zug) o;
		if ((this.altx == other.altx) &&
				(this.alty == other.alty) &&
				(this.neux == other.neux) &&
				(this.neuy == other.neuy)) {
			return true;
		}
		return false;
		
	}
}
