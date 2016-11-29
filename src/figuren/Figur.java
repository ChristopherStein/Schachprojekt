package figuren;

import java.util.ArrayList;

import spiel.Zug;

public abstract class Figur {
	
	private boolean weiss;
	private boolean moegl;
	
	public Figur(boolean weiss) {
		this.weiss = weiss;
		this.moegl = false;
	}
	
	public abstract Figur copy();
	
	public void setMoegl(boolean m) {
		this.moegl = true;
	}
	
	public boolean isWeiss() {
		return this.weiss;
	}
	
	public boolean isMoegl() {
		return this.moegl;
	}
	
	protected boolean imBrett(int x, int y) {
		if (x < 0 || y < 0 || x > 7 || y > 7) {
			return false;
		}
		return true;
	}
	
	public abstract ArrayList<Zug> getMoeglZuege(int x, int y);
}
