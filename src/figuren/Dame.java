package figuren;

import java.util.ArrayList;

import spiel.Zug;

public class Dame extends Figur {

	public Dame(boolean weiss) {
		super(weiss);
	}

	public ArrayList<Zug> getMoeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		for (int i = -7; i < 8; i++) {
			if (i == 0) {
				continue;
			}
			if (imBrett(x + i, y)) {
				moegl.add(new Zug(x, y, x + i, y));
			}
			if (imBrett(x, y + i)) {
				moegl.add(new Zug(x, y, x, y + i));
			}
			if (imBrett(x + i, y + i)) {
				moegl.add(new Zug(x, y, x + i, y + i));
			}
			if (imBrett(x + i, y - i)) {
				moegl.add(new Zug(x, y, x + i, y - i));
			}
		}
		return moegl;
	}

	@Override
	public Figur copy() {
		return new Dame(this.isWeiss());
	}

}
