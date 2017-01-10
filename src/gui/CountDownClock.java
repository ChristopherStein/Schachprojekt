package gui;

public class CountDownClock implements Runnable {
	private Thread thread;
	private int minuten;
	private int sekunden;
	private Schachbrett sb;

	public CountDownClock(Schachbrett sb) {
		minuten = 15;
		sekunden = 0;
		this.sb = sb;
		start(sb);

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
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if (sekunden != 0) {
				sekunden--;
			} else {		this.sb = sb;
				minuten--;
				sekunden = 59;
			}
			if (minuten == 0 && sekunden == 0) {
				//System.out.println("vorerst");
			}
			sb.setTime(minuten, sekunden);
			//System.out.println(minuten + ":" + sekunden);
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
