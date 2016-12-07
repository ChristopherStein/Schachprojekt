package figuren;

import java.util.ArrayList;

import spiel.Zug;

public class Springer extends Figur {

	public Springer(boolean weiss) {
		super(weiss);
	}

	public ArrayList<Zug> getMoeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		for (int i = -2; i <= 2; i += 4) {
			for (int j = -1; j <= 1; j += 2) {
				if (imBrett(x + i, y + j)) {
					moegl.add(new Zug(x, y, x + i, y + j));
				}
				if (imBrett(x + j, y + i)) {
					moegl.add(new Zug(x, y, x + j, y + i));
				}
			}
		}
		return moegl;
	}

	@Override
	public Figur copy() {
		return new Springer(this.isWeiss());
	}

}
