package gui;

public class CountDownClock implements Runnable {
	private Thread thread;
	private int minuten;
	private int sekunden;
	private Schachbrett sb;
	private boolean farbe;

	public CountDownClock(Schachbrett sb, boolean farbe) {
		minuten = 15;
		sekunden = 0;
		this.sb = sb;
		this.farbe = farbe;
		start(sb);
		System.out.println(this.farbe);

	}

	public void start(Schachbrett sb) {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(1000);
				if (this.farbe == sb.getWAmZug()) {
					if (sekunden != 0) {
						sekunden--;
					} else {
						minuten--;
						sekunden = 59;
					}
					if (minuten == 0 && sekunden == 0) {
						sb.setTime(minuten, sekunden, farbe);
						return;
					}
				}
				sb.setTime(minuten, sekunden, farbe);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public String toString() {
		if (sekunden > 9) {
			return minuten + ":" + sekunden;
		} else {
			return minuten + ":0" + sekunden;
		}
	}

	public int getSekunden() {
		return sekunden;
	}

	public int getMinuten() {
		return minuten;
	}

}
