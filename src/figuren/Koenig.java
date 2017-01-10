package figuren;

import java.util.ArrayList;

import spiel.Sonderzug;
import spiel.Zug;

public class Koenig extends Figur {

	public Koenig(boolean weiss) {
		super(weiss);
	}

	public ArrayList<Zug> getMoeglZuege(int x, int y) {
		ArrayList<Zug> moegl = new ArrayList<Zug>();
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (imBrett(x + i, y + j)) {
					if (i == 0 && j == 0) {
						continue;
					}
					moegl.add(new Zug(x, y, x + i, y + j));
				}
			}
		}
		if (imBrett(x, y - 2))
			moegl.add(new Sonderzug(x, y, x, y - 2, "Rochade"));
		if (imBrett(x, y + 2))
			moegl.add(new Sonderzug(x, y, x, y + 2, "Rochade"));
		return moegl;
	}

	@Override
	public Figur copy() {
		return new Koenig(this.isWeiss());
	}

}
