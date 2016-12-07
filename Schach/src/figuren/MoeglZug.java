package figuren;

import java.util.ArrayList;

import spiel.Zug;

public class MoeglZug extends Figur {

	public MoeglZug(boolean weiss) {
		super(weiss);
	}
	
	public ArrayList<Zug> getMoeglZuege(int x, int y) {
		return null;
	}

	@Override
	public Figur copy() {
		return new MoeglZug(this.isWeiss());
	}

}
