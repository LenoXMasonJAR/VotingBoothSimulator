package votingSimulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	private Table[] tables = new Table[0];
	private ArrayList<VotingBooth> booths = new ArrayList<VotingBooth>();
	private ArrayList<Voter> Queue = new ArrayList<Voter>();
	private Dimension size;
	private int[] xs = new int[0];
	private int[] ys = new int[0];
	private int divisionx = 3;// how many vertical lines.
	private int divisiony = 15;// horizontal lines.

	public DisplayPanel() {
		this.size = this.getSize();
		getDivisions(size, divisionx, divisiony);
		setBackground(new Color(218, 218, 218));

	}

	public DisplayPanel(Table[] t, ArrayList<VotingBooth> b, ArrayList<Voter> q, Dimension size) {
		setBackground(new Color(218, 218, 218));
		tables = t;
		booths = b;
		Queue = q;
		this.size = this.getSize();
		getDivisions(size, divisionx, divisiony);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		size = this.getSize();
		getDivisions(size, divisionx, divisiony);
		// for (int i = 0; i < xs.length; i++) {
		// g2d.drawLine(xs[i], 0, xs[i], size.height);
		// }
		// for (int i = 0; i < ys.length; i++) {
		// g2d.drawLine(0, ys[i], size.width, ys[i]);
		// }

		int lineWid = 0;
		int lineHei = 0;
		int lineX = 0;
		int lineY = 0;

		int tableW = 0;
		int tableH = 0;
		int tableX = 0;
		int tableY = 0;

		Color lineColor = new Color(77, 196, 218);
		for (int i = 0; i < tables.length; i++) {
			g2d.setColor(lineColor);
			lineWid = xs[1] - xs[0];
			lineWid -= lineWid / 4;
			lineHei = ys[1] - ys[0];
			lineHei /= 3;
			lineX = xs[0];
			lineX += lineWid / 8;
			lineY = ys[i];
			lineY += lineHei;
			g2d.fillRect(lineX, lineY, lineWid, lineHei);// line rectangle

			tableW = lineHei;
			tableH = ys[1] - ys[0];
			tableH -= tableH / 8;
			tableX = lineWid + lineX;
			tableY = ys[i];
			tableY += lineHei / 8;

			g2d.setColor(new Color(135, 68, 48).brighter());
			g2d.fillRect(tableX, tableY, tableW, tableH);// table rectangle

			int j = 0;
			int queueLength = tables[i].getQ().size();
			int vWid = lineWid / (queueLength + 1);
			if (queueLength <= 3) {
				vWid = lineWid / 5;
			}
			for (Voter v : tables[i].getQ()) {
				g2d.setColor(getVoterCol(v));
				g2d.fillOval((lineWid - vWid * j) - lineWid / 10, lineY, vWid, lineHei - (lineHei / 10));
				j++;
			}
			Voter v = tables[i].getPerson();

			if (v != null) {
				g2d.setColor(getVoterCol(v));
				g2d.fillOval(tableX, lineY, tableW, lineHei);

			}

		}

		g2d.setColor(new Color(202, 21, 21));

		int qx = xs[1];
		int qy = (size.height / 2) - (size.height / 5) / 2;
		int qw = xs[2] - xs[1];
		int qh = size.height / 4;

		g2d.fillRect(qx, qy, qw, qh);

		int vX = 0;
		int vY = 0;
		int vW = 0;
		int vH = 0;
		vX = qx - (qh / 6);
		vY = qy;
		vH = qh / 6;
		vW = qw / 10;
		for (Voter v : Queue) {
			g2d.setColor(getVoterCol(v));

			vX += vW;
			if (vX > qx + qw - vW) {
				vX = qx;
				vY += vH;
			}

			g2d.fillOval(vX, vY, vW, vH);
		}
		int q = 0;
		int boothw, boothh, boothx, boothy;

		for (VotingBooth b : booths) {
			boothw = ys[1] - ys[0];
			boothw -= boothw / 8;
			boothh = boothw;
			boothx = xs[2];
			boothx += (xs[1] - xs[0]) / 2;
			boothx -= boothw / 2;
			boothy = ys[q];
			boothy += boothw / 16;

			g2d.setColor(new Color(47, 86, 42));
			g2d.fillRect(boothx, boothy, boothw, boothh);

			Voter v = b.getCurrentVoter();
			if (v != null) {
				vX = boothx;
				vY = boothy;
				vH = boothh;
				vH -= vH / 4;
				vW = boothw;
				vW -= vW / 4;
				vX += boothw / 8;
				vY += boothh / 8;

				g2d.setColor(getVoterCol(v));
				g2d.fillOval(vX, vY, vW, vH);
			}

			q++;
		}

	}

	private Color getVoterCol(Voter v) {
		if (v instanceof RegularVoter)
			return Color.WHITE;
		else if (v instanceof LimitedTimeVoter)
			return new Color(213, 226, 59);
		else if (v instanceof SpecialNeedsVoter)
			return new Color(154, 9, 180);
		else if (v instanceof SuperSpecialNeedsVoter)
			return new Color(0, 49, 218);

		return Color.BLACK;
	}

	public void updateDisplay(Table[] t, ArrayList<VotingBooth> b, ArrayList<Voter> q) {
		tables = t;
		booths = b;
		Queue = q;
		if (t.length > b.size())
			divisiony = t.length;
		else
			divisiony = b.size();

		repaint();
	}

	private void getDivisions(Dimension s, int xDivisions, int yDivisions) {
		int wid = s.width;
		int hei = s.height;
		int div = xDivisions;
		int divy = yDivisions;

		xs = new int[div];
		ys = new int[divy];

		xs[0] = 0;
		ys[0] = 0;

		for (int i = 1; i < div; i++) {
			xs[i] = (wid / div) * i;
		}
		for (int i = 1; i < divy; i++) {
			ys[i] = (hei / divy) * i;
		}

	}

	public void setTheSize(Dimension newSize) {
		this.size = newSize;
		repaint();
	}

}
