package froggerGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class FroggerFrame extends JFrame implements ActionListener{
	FroggerPanel panel;
	JMenuBar menu;
	JMenu quit;
	JMenuItem exit;
	JMenu help;
	JMenuItem about;
	JMenuItem howToPlay;
	FroggerFrame(){
		panel = new FroggerPanel();
		this.add(panel);
		menu = new JMenuBar();
		quit = new JMenu("Quit");
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		help = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		howToPlay = new JMenuItem("How to play");
		howToPlay.addActionListener(this);
		quit.add(exit);
		menu.add(quit);
		help.add(about);
		help.add(howToPlay);
		menu.add(help);
		this.setTitle("FROGGER");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(menu);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit) {
			System.exit(0);
		}else if (e.getSource() == about) {
			JOptionPane.showMessageDialog(null, "FROGGER game by Ömer ATİLLA", "Information Message", JOptionPane.INFORMATION_MESSAGE);
		}else if (e.getSource() == howToPlay) {
			JFrame howToPlayFrame = new JFrame("How to play");
			JTextArea area = new JTextArea();
			howToPlayFrame.add(area);
			howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			area.setText("1- Your goal is to guide 5 frogs to their homes in the green zone\n"+
			"2- Safely cross the road by avoid hitting cars\n"+
					"3- Cross the water by jumping on logs\n"+
			"4- Do all of this in time");
			area.setEditable(false);
			//howToPlayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			howToPlayFrame.setSize(400,500);
			howToPlayFrame.setLocationRelativeTo(null);
			howToPlayFrame.setResizable(false);
			howToPlayFrame.setVisible(true);
		}
	}
}
