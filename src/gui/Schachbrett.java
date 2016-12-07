package gui;

import javax.swing.*;

import figuren.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Schachbrett extends JFrame {
	int[] click = { -1, -1 };
	JButton feld[][] = new JButton[8][8];

	public Schachbrett() {
		this.setTitle("Schach");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setExtendedState(MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println(screenSize);
		this.setBounds(0, 0, screenSize.width, screenSize.height);

		JMenu menu1 = new JMenu("File");
		JMenuItem save = new JMenuItem("speichern");
		JMenuItem laden = new JMenuItem("laden");
		save.addActionListener(new Speichern());
		laden.addActionListener(new Laden());
		menu1.add(save);
		menu1.addSeparator();
		menu1.add(laden);

		JMenu menu2 = new JMenu("Menu2");

		JMenu menu3 = new JMenu("Menu3");

		JMenuBar menubar = new JMenuBar();
		menubar.add(menu1);
		menubar.add(menu2);
		menubar.add(menu3);

		this.setJMenuBar(menubar);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ImageIcon icon = null;
				
				feld[i][j] = new JButton("", icon);
				menu1.add(laden);
				
				feld[i][j].addActionListener(new FeldListener(i, j));
				feld[i][j].setBounds(screenSize.width / 9 * j, screenSize.height / 9 * i, screenSize.width / 10,
						screenSize.height / 10);
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
			if(!f.isWeiss()){
				iconpath="SchachBauerSchwarz.img";
			}
			// Mit f.isWeiss() kannst du zwischen Weiss und Schwarz untescheiden
			break;
		case "Dame":
			iconpath = "SchachDameWeiss.img";
			if(!f.isWeiss()){
				iconpath = "SchachDameSchwarz.img";
			}
			break;
		case "Koenig":
			iconpath = "SchachKoenigWeiss.img";
			if(!f.isWeiss()){
				iconpath = "SchachKoenigSchwarz.img";
			}
			break;
		case "Laeufer":
			iconpath = "SchachLaeuferWeiss.img";
			if(!f.isWeiss()){
				iconpath = "SchachLaeuferSchwarz.img";
			}
			break;
		case "Turm":
			iconpath = "SchachTurmWeiss.img";
			if(!f.isWeiss()){
				iconpath = "SchachTurmSchwarz.img";
			}
			break;
		case "Springer":
			iconpath = "SchachSpringerWeiss.img";
			if(!f.isWeiss()){
				iconpath = "SchachSpringerSchwarz.img";
			}
			break;
		case "null":
			return null;
		}

		ImageIcon icon = new ImageIcon("./Bilder/" + iconpath);
		icon.setImage(
				icon.getImage().getScaledInstance(screenSize.width / 11, screenSize.height / 11, Image.SCALE_DEFAULT));

		return icon;

	}

	public int[] setFiguren(spiel.Spielfeld f) {
		// Diese beiden Methoden bringen dir was, später kannst du dann noch die bisher gespielten Züge abrufen
		//****************************************************//
		Figur[][] fig = f.getFeld();
		ArrayList<spiel.Zug> moegl = f.moeglZuege(0, 0);
		//****************************************************//
		/*
		 * Also du machst die möglichen Züge dann selber?
		 * Wäre cool wenn du dass dann mal machen könntest, dann änder ich die Main um, dass das vernünftig läuft 
		 * (sollte es jetzt schon, aber ich habe da jetzt unnötigen Code). Schach und so implementiere ich danach.
		 * Also nächster Schritt mögliche Züge machen und mir dann Bescheid sagen!  <3
		 */
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					feld[i][j].setBackground(Color.DARK_GRAY);
				}
				else{
					
						feld[i][j].setBackground(null);
					
				}
				feld[i][j].setIcon(getIconFromMain(fig[i][j]));
				if (fig[i][j] instanceof MoeglZug) {
					feld[i][j].setBackground(Color.BLUE);
				}
				if (fig[i][j] != null) {
					if (fig[i][j].isMoegl()) {
						feld[i][j].setBackground(Color.BLUE);
					}
				}
			}
		}
		/*
		 * if(fig[i][j].isMoegl()==true){ feld[i][j].setBackground(Color.BLUE);
		 * }
		 */

		click[0] = -1;
		while (click[0] == -1)

		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
		return click;

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