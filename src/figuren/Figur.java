package figuren;

import java.util.ArrayList;

import spiel.Zug;

public abstract class Figur {
	
	private boolean weiss;
	
	public Figur(boolean weiss) {
		this.weiss = weiss;
	}
	
	public boolean isWeiss() {
		return this.weiss;
	}
	
	protected boolean imBrett(int x, int y) {
		if (x < 0 || y < 0 || x > 7 || y > 7) {
			return false;
		}
		return true;
	}
	
	public abstract ArrayList<Zug> getMoeglZuege(int x, int y);
}
