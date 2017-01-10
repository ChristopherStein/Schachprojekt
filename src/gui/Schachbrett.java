package gui;

import javax.swing.*;

import com.sun.xml.internal.stream.util.BufferAllocator;

import figuren.*;
import spiel.Zug;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Schachbrett extends JFrame {
	ArrayList<spiel.Zug> moegl = new ArrayList<>();
	int[] click = { -1, -1 };
	JButton feld[][] = new JButton[8][8];
	JButton friedhof_weis[][] = new JButton[5][3];
	JButton friedhof_schwarz[][] = new JButton[5][3];
	CountDownClock c1;
	CountDownClock c2;
	JLabel clock1;
	JLabel clock2;
	JMenu menu1;
	Dimension screenSize;
	JTextArea spielLabel;
	private boolean wAmZug;
	private JComboBox auswahlFrame;
	protected boolean unbestaetigt;
	private JLabel sieger;
	private boolean mit_Uhr = true;

	public void initialize() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setTitle("Schach");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setBounds(0, 0, screenSize.height * 2 / 3, screenSize.height * 2 / 3);
		menu1 = new JMenu("File");

		JMenuItem laden = new JMenuItem("Laden");
		laden.addActionListener(new Laden());
		menu1.add(laden);
		JMenuItem save = new JMenuItem("Speichern");
		save.addActionListener(new Speichern(this));
		menu1.addSeparator();
		menu1.add(save);
		JMenuItem neu = new JMenuItem("Neues Spiel");

	}

	public void init_Uhren() {

		c1 = new CountDownClock(this, true);
		c2 = new CountDownClock(this, false);

		clock1 = new JLabel(c1 + "");
		clock2 = new JLabel(c2 + "");
		clock1.setFont(new Font("bl", Font.PLAIN, screenSize.height * 50 / 1000));
		clock2.setFont(new Font("bl", Font.PLAIN, screenSize.height * 50 / 1000));
		clock1.setBounds(screenSize.height * 8 / 10, screenSize.height / 10, screenSize.height / 8,
				screenSize.height / 8);
		clock2.setBounds(screenSize.height * 8 / 10, screenSize.height * 6 / 10, screenSize.height / 8,
				screenSize.height / 8);
		this.add(clock1);
		this.add(clock2);
	}

	public Schachbrett() {
		initialize();
		if (mit_Uhr) {
			init_Uhren();
		}
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu1);

		this.setJMenuBar(menubar);
		spielLabel = new JTextArea("");
		spielLabel.setBounds(screenSize.height * 1 / 20, screenSize.height * 15 / 18, screenSize.height,
				screenSize.height * 2 / 10);

		this.add(spielLabel);
		spielLabel.setFont(new Font("Serif", Font.ITALIC, screenSize.height * 15 / 1032));
		spielLabel.setLineWrap(true);
		spielLabel.setWrapStyleWord(true);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ImageIcon icon = null;

				feld[i][j] = new JButton("", icon);

				feld[i][j].addActionListener(new FeldListener(i, j));
				feld[i][j].setBounds(screenSize.height / 10 * j, screenSize.height / 10 * i, screenSize.height / 11,
						screenSize.height / 11);
				if ((i + j) % 2 == 0) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				}

				this.add(feld[i][j]);

			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				ImageIcon icon = null;

				friedhof_weis[i][j] = new JButton("", icon);
				friedhof_schwarz[i][j] = new JButton("", icon);

				friedhof_weis[i][j].setBounds(screenSize.height * 8 / 10 + screenSize.height * i / 25,
						screenSize.height * j / 25, screenSize.height / 25, screenSize.height / 25);
				friedhof_schwarz[i][j].setBounds(screenSize.height * 8 / 10 + screenSize.height * i / 25,
						screenSize.height * 7 / 10 + screenSize.height * j / 25, screenSize.height / 25,
						screenSize.height / 25);

				friedhof_weis[i][j].setFont(new Font("Serif", Font.ITALIC, 8));
				friedhof_schwarz[i][j].setFont(new Font("Serif", Font.ITALIC, 8));
				friedhof_schwarz[i][j].setVisible(false);
				friedhof_weis[i][j].setVisible(false);
				this.add(friedhof_weis[i][j]);
				this.add(friedhof_schwarz[i][j]);

			}
		}

		this.setVisible(true);
		// System.out.println(bauernWahl());

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

				if ((i + j) % 2 == 0) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				} else {
					feld[i][j].setBackground(null);
				}
				feld[i][j].setIcon(getIconFromMain(fig[i][j]));

			}
		}
		if (f.imSchach(true)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (fig[i][j] instanceof Koenig) {
						if (fig[i][j].isWeiss()) {
							feld[i][j].setBackground(Color.MAGENTA);
						}
					}

				}
			}
		}
		if (f.imSchach(false)) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (fig[i][j] instanceof Koenig) {
						if (!fig[i][j].isWeiss()) {
							feld[i][j].setBackground(Color.MAGENTA);
						}
					}

				}
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {

			}
		}

		this.setMoeglZuege(moegl);
		this.setFriedhof(f.getGeschlagen());
		this.spielLabel.setText(f.getNotationFuerMenschen().replace("\n", "  "));

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

	private void setFriedhof(ArrayList<Figur> geschlagen) {
		int weis_anzahl = 0;
		int schwarz_anzahl = 0;

		for (int i = 0; i < geschlagen.size(); i++) {
			if (geschlagen.get(i).isWeiss()) {
				String ret;
				switch (geschlagen.get(i).getClass().toString().replace("class figuren.", "")) {
				case "Bauer":
					ret = "B";
					break;
				case "Turm":
					ret = "T";
					break;
				case "Laeufer":
					ret = "L";
					break;
				case "Dame":
					ret = "D";
					break;
				default:
					ret = "S";
				}
				friedhof_weis[weis_anzahl / 3][weis_anzahl % 3].setText(ret);
				friedhof_weis[weis_anzahl/ 3][weis_anzahl % 3].setVisible(true);
				weis_anzahl++;
			}
			if (!geschlagen.get(i).isWeiss()) {
				String ret;

				switch (geschlagen.get(i).getClass().toString().replace("class figuren.", "")) {
				case "Bauer":
					ret = "B";
					break;
				case "Turm":
					ret = "T";
					break;
				case "Laeufer":
					ret = "L";
					break;
				case "Dame":
					ret = "D";
					break;
				default:
					ret = "S";
				}
				friedhof_schwarz[schwarz_anzahl / 3][schwarz_anzahl % 3].setText(ret);
				friedhof_schwarz[schwarz_anzahl / 3][schwarz_anzahl % 3].setVisible(true);
				schwarz_anzahl++;
			}
		}

	}

	public void setSpielZuEnde(int wasistpassiert, spiel.Spielfeld f) {
		Figur fig[][] = f.getFeld();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				}
				if (f.imSchach(false)) {

					if (fig[i][j] instanceof Koenig) {
						if (!fig[i][j].isWeiss()) {
							feld[i][j].setBackground(Color.RED);

						}
					}
				}
				if (f.imSchach(true)) {
					if (fig[i][j] instanceof Koenig) {
						if (fig[i][j].isWeiss()) {
							feld[i][j].setBackground(Color.RED);

						}
					}
				} else {
					feld[i][j].setBackground(null);
				}
				feld[i][j].setIcon(getIconFromMain(f.getFeld()[i][j]));

			}
		}
		sieger = new JLabel();
		sieger.setBounds(screenSize.height * 8 / 10, screenSize.height * 4 / 10, screenSize.height / 3,
				screenSize.height / 16);
		sieger.setFont(new Font("Serif", Font.ITALIC, screenSize.height * 20 / 1032));
		if (wasistpassiert == 2) {
			sieger.setText("Schwarz gewinnt");
		}
		if (wasistpassiert == 1) {
			sieger.setText("Weiss gewinnt");
		}
		this.add(sieger);
		if (mit_Uhr) {
			c1.stop();
			c2.stop();
		}
		this.setVisible(true);
		System.out.println(wasistpassiert);
		revalidate();
		repaint();
		// System.out.println("jo ende");
	}

	public boolean getWAmZug() {
		return wAmZug;
	}

	public String bauernWahl() {
		String comboBoxListe[] = { "Springer", "Laeufer", "Turm", "Dame" };
		auswahlFrame = new JComboBox(comboBoxListe);
		auswahlFrame.setSelectedIndex(3);
		auswahlFrame.setBounds(screenSize.height * 8 / 10, screenSize.height * 3 / 10, screenSize.height / 8,
				screenSize.height / 16);

		JButton best = new JButton("Bestaetigen");
		best.setBounds(screenSize.height * 8 / 10, screenSize.height * 4 / 10, screenSize.height / 8,
				screenSize.height / 16);
		best.addActionListener(new Bauernwahl(this));
		this.add(auswahlFrame);
		this.add(best);
		revalidate();
		repaint();

		unbestaetigt = true;
		while (unbestaetigt) {
			// System.out.println(unbestaetigt);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				System.out.println(e);
			}
		}

		return auswahlFrame.getSelectedItem().toString();
	}

	public String getNotation() {
		return spielLabel.getText();
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
		revalidate();
		repaint();

	}

	private class Bauernwahl implements ActionListener {
		private Schachbrett sb;

		public Bauernwahl(Schachbrett sb) {
			super();
			this.sb = sb;
		}

		public void actionPerformed(ActionEvent e) {

			sb.unbestaetigt = false;
		}
	}

	private class Speichern implements ActionListener {
		private Schachbrett sb;

		public Speichern(Schachbrett sb) {
			super();
			this.sb = sb;
		}

		public void actionPerformed(ActionEvent a) {
			JFileChooser saver = new JFileChooser("/home/christopher/HA/Schach/data");
			saver.showSaveDialog(null);
			String file = saver.getSelectedFile().toString();
			try {
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(sb.getNotation().replace("  ", "\n"));
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	private class NeuesSpiel_ohne implements ActionListener {
		private Schachbrett sb;

		public NeuesSpiel_ohne(Schachbrett sb) {
			super();
			this.sb = sb;
		}

		public void actionPerformed(ActionEvent a) {
			sb.mit_Uhr = false;
		}

	}

	private class NeuesSpiel_mit implements ActionListener {
		private Schachbrett sb;

		public NeuesSpiel_mit(Schachbrett sb) {
			super();
			this.sb = sb;
		}

		public void actionPerformed(ActionEvent a) {
			sb.mit_Uhr = false;
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
