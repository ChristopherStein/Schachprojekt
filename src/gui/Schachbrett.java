package gui;

import javax.swing.*;

import figuren.*;
import spiel.Zug;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Schachbrett extends JFrame {
	ArrayList<spiel.Zug> moegl = new ArrayList<>();
	int[] click = { -1, -1 };
	JButton feld[][] = new JButton[8][8];
	CountDownClock c1;
	CountDownClock c2;
	JLabel clock1;
	JLabel clock2;
	JMenu menu1;
	Dimension screenSize;
	JLabel spielLabel;
	private boolean wAmZug;

	public void initialize() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setTitle("Schach");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setBounds(0, 0, screenSize.height * 2 / 3, screenSize.height * 2 / 3);
		menu1 = new JMenu("File");
		JMenuItem laden = new JMenuItem("laden");
		laden.addActionListener(new Laden());
		menu1.add(laden);

	}

	public Schachbrett() {
		initialize();
		JMenuItem save = new JMenuItem("speichern");
		save.addActionListener(new Speichern());

		menu1.addSeparator();
		menu1.add(save);

		JMenuBar menubar = new JMenuBar();
		menubar.add(menu1);

		this.setJMenuBar(menubar);
		spielLabel = new JLabel("");
		spielLabel.setBounds(screenSize.height * 1 / 9, screenSize.height * 7 / 9, screenSize.height,
				screenSize.height * 2 / 10);
		this.add(spielLabel);
		c1 = new CountDownClock(this, true);
		c2 = new CountDownClock(this, false);

		clock1 = new JLabel(c1 + "");
		clock2 = new JLabel(c2 + "");
		clock1.setFont(new Font("bl", Font.PLAIN, 50));
		clock2.setFont(new Font("bl", Font.PLAIN, 50));
		clock1.setBounds(screenSize.height * 8 / 10, screenSize.height / 10, screenSize.height / 8,
				screenSize.height / 8);
		clock2.setBounds(screenSize.height * 8 / 10, screenSize.height * 6 / 10, screenSize.height / 8,
				screenSize.height / 8);
		this.add(clock1);
		this.add(clock2);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ImageIcon icon = null;

				feld[i][j] = new JButton("", icon);

				feld[i][j].addActionListener(new FeldListener(i, j));
				feld[i][j].setBounds(screenSize.height / 10 * j, screenSize.height / 10 * i, screenSize.height / 11,
						screenSize.height / 11);
				if ((i + j) % 2 == 1) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				}

				this.add(feld[i][j]);

			}
		}

		this.setVisible(true);

	}

	public ImageIcon getIconFromMain(Figur f) {
		String iconpath = null;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (f == null) {
			return null;
		}
		switch (f.getClass().getSimpleName()) {
		case "Bauer":
			iconpath = "SchachBauerWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachBauerSchwarz.img";
			}
			// Mit f.isWeiss() kannst du zwischen Weiss und Schwarz untescheiden
			break;
		case "Dame":
			iconpath = "SchachDameWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachDameSchwarz.img";
			}
			break;
		case "Koenig":
			iconpath = "SchachKoenigWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachKoenigSchwarz.img";
			}
			break;
		case "Laeufer":
			iconpath = "SchachLaeuferWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachLaeuferSchwarz.img";
			}
			break;
		case "Turm":
			iconpath = "SchachTurmWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachTurmSchwarz.img";
			}
			break;
		case "Springer":
			iconpath = "SchachSpringerWeiss.img";
			if (!f.isWeiss()) {
				iconpath = "SchachSpringerSchwarz.img";
			}
			break;
		case "null":
			return null;
		}

		ImageIcon icon = new ImageIcon("./Bilder/" + iconpath);
		icon.setImage(
				icon.getImage().getScaledInstance(screenSize.height / 11, screenSize.height / 11, Image.SCALE_DEFAULT));

		return icon;

	}

	public int[] setMoeglZuege(ArrayList<spiel.Zug> moegl) {
		for (Zug zug : moegl) {
			feld[zug.getNeuX()][zug.getNeuY()].setBackground(Color.BLUE);
		}
		return click;
	}

	public int[] setFiguren(spiel.Spielfeld f) {
		// Diese beiden Methoden bringen dir was, später kannst du dann noch die
		// bisher gespielten Züge abrufen
		// ****************************************************//
		Figur[][] fig = f.getFeld();
		wAmZug = f.getWAmZug();
		// ****************************************************//
		/*
		 * Also du machst die möglichen Züge dann selber? Wäre cool wenn du dass
		 * dann mal machen könntest, dann änder ich die Main um, dass das
		 * vernünftig läuft (sollte es jetzt schon, aber ich habe da jetzt
		 * unnötigen Code). Schach und so implementiere ich danach. Also
		 * nächster Schritt mögliche Züge machen und mir dann Bescheid sagen! <3
		 */
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if ((i + j) % 2 == 1) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				} else {
					feld[i][j].setBackground(null);
				}
				feld[i][j].setIcon(getIconFromMain(fig[i][j]));

			}
		}
		this.setMoeglZuege(moegl);
		this.spielLabel.setText(f.getNotationFuerMenschen());
		

		click[0] = -1;
		while (click[0] == -1)

		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}

		if (f.getFeld()[click[0]][click[1]] != null && f.getFeld()[click[0]][click[1]].isWeiss() == f.getWAmZug()) {

			moegl = f.moeglZuege(click[0], click[1]);
		} else {
			moegl.clear();
		}

		return click;

	}

	public void setSpielZuEnde(int wasistpassiert) {
		// So das Spiel beenden? TODO @Stein
	}

	public boolean getWAmZug() {
		return wAmZug;
	}

	public void setTime(int minuten, int sekunden, boolean farbe) {
		if (farbe == true) {
			if (sekunden > 9) {
				clock1.setText(minuten + ":" + sekunden);
			} else {
				clock1.setText(minuten + ":0" + sekunden);
			}
		} else {
			if (sekunden > 9) {
				clock2.setText(minuten + ":" + sekunden);
			} else {
				clock2.setText(minuten + ":0" + sekunden);
			}
		}
		
	}

	private class Speichern implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			JFileChooser saver = new JFileChooser("/home/christopher/HA/Schach/data");
			saver.showSaveDialog(null);
			String file = saver.getSelectedFile().toString();
			System.out.println(file);
		}
	}

	private class Laden implements ActionListener {

		public void actionPerformed(ActionEvent a) {
			JFileChooser loader = new JFileChooser("/home/christopher/HA/Schach/data");
			loader.showOpenDialog(null);
			try {
				String file = loader.getSelectedFile().toString();
				System.out.println(file);
			} catch (NullPointerException e) {
				return;
			}

		}
	}

	private class FeldListener implements ActionListener {
		int i;
		int j;

		public FeldListener(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public void actionPerformed(ActionEvent a) {
			click[0] = i;
			click[1] = j;

		}
	}

}
