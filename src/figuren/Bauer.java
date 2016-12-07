package figuren;

import java.util.ArrayList;

import spiel.Zug;

public class Bauer extends Figur {

	public Bauer(boolean weiss) {
		super(weiss);
	}

	public ArrayList<Zug> getMoeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		int r = (this.isWeiss()) ? 1 : -1;
		if (imBrett(x + r, y - 1)) {
			moegl.add(new Zug(x, y, x + r, y - 1));
		}
		if (imBrett(x + r, y)) {
			moegl.add(new Zug(x, y, x + r, y));
		}
		if (imBrett(x + r, y + 1)) {
			moegl.add(new Zug(x, y, x + r, y + 1));
		}
		if (x == 6 && (! this.isWeiss())) {
			moegl.add(new Zug(x, y, x - 2, y));
		}
		if (x == 1 && (this.isWeiss())) {
			moegl.add(new Zug(x, y, x + 2, y));
		}
		return moegl;
	}

	@Override
	public Figur copy() {
		return new Bauer(this.isWeiss());
	}

}
