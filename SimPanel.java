package votingSimulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimPanel extends JPanel implements ActionListener {
	/**
	 * labels indicating what each input does.
	 */
	JLabel[] promptArr = new JLabel[11];
	/**
	 * Input fields to change sim.
	 */
	JTextField[] inputArr = new JTextField[11];

	/**
	 * JLabel array to display stats.
	 */
	JLabel[][] outArr = new JLabel[][] { new JLabel[] { new JLabel("Number of Voters Served:"), new JLabel("0") },
			new JLabel[] { new JLabel("Average Voter Completion Time:"), new JLabel("0") },
			new JLabel[] { new JLabel("Number of People that left the line:"), new JLabel("0") },
			new JLabel[] { new JLabel("Average maximum Q length at tables:"), new JLabel("0") },
			new JLabel[] { new JLabel("Maximum Voting Booth Queue Length:"), new JLabel("0") },

			new JLabel[] { new JLabel("Voters still at tables:"), new JLabel("0") },
			new JLabel[] { new JLabel("Voters still in main Queue:"), new JLabel("0") },

			new JLabel[] { new JLabel("Current Tick / Number of Steps Completed:"), new JLabel("0") },

			new JLabel[] { new JLabel("Regular Voters Produced:"), new JLabel("0") },
			new JLabel[] { new JLabel("Special Needs Voters Produced:"), new JLabel("0") },
			new JLabel[] { new JLabel("Super Special Needs Voters Produced:"), new JLabel("0") },
			new JLabel[] { new JLabel("Limited Time Voters Produced:"), new JLabel("0") },

			new JLabel[] { new JLabel("Regular Voters Left:"), new JLabel("0") },
			new JLabel[] { new JLabel("Special Needs Voters Left:"), new JLabel("0") },
			new JLabel[] { new JLabel("Super Special Needs Voters Left:"), new JLabel("0") },
			new JLabel[] { new JLabel("Limited Time Voters Left:"), new JLabel("0") }, };

	private int stepDistance = 1;
	/**
	 * graphical display
	 */
	private DisplayPanel display;
	/**
	 * buttons for basic functionality.
	 */
	private JButton start;
	private JButton init;
	private JButton quit;
	private JButton step;

	public SimPanel() {
		// borderLayout is the best.:)
		this.setLayout(new BorderLayout());

		// various JPanels for organization.
		JPanel inputPanel = new JPanel(new GridLayout(promptArr.length, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JPanel statPanel = new JPanel(new GridLayout(outArr.length / 2, 4));
		// graphical display panel.
		display = new DisplayPanel();

		// dimensions of Panels
		Dimension in = new Dimension(400, 700);
		Dimension out = new Dimension(900, 200);

		// set sizes and colors of panels.
		inputPanel.setPreferredSize(in);
		inputPanel.setBackground(new Color(209, 165, 190));
		buttonPanel.setBackground(new Color(144, 212, 173));
		buttonPanel.setPreferredSize(new Dimension(300, 40));
		statPanel.setPreferredSize(out);
		statPanel.setBackground(new Color(165, 173, 209));

		for (int i = 0; i < promptArr.length; i++) {
			promptArr[i] = new JLabel();
			inputArr[i] = new JTextField();
			inputPanel.add(promptArr[i]);
			inputPanel.add(inputArr[i]);
		}

		for (int r = 0; r < outArr.length; r++) {
			for (int c = 0; c < outArr[0].length; c++) {
				statPanel.add(outArr[r][c]);
			}
		}

		// set the text of all the prompts
		promptArr[0].setText("Seconds until next person:");
		promptArr[0].setToolTipText("frequency of voter arrival.");
		promptArr[1].setText("Average seconds for check-in");
		promptArr[2].setText("Total time in seconds");
		promptArr[2].setToolTipText("How many seconds the simulation will run.");
		promptArr[3].setText("Average seconds for voting");
		promptArr[4].setText("Seconds before person leaves");
		promptArr[5].setText("Number of booths");
		promptArr[6].setText("Number of tables");
		promptArr[7].setText("% Super Special Needs Voters:");
		promptArr[7].setToolTipText("% out of 100 where the regular voter percentage is whatever is leftover.");
		promptArr[8].setText("% Special Needs Voters:");
		promptArr[8].setToolTipText("% out of 100 where the regular voter percentage is whatever is leftover.");
		promptArr[9].setText("% Limited Time Voters:");
		promptArr[9].setToolTipText("% out of 100 where the regular voter percentage is whatever is leftover.");
		promptArr[10].setText("Step Distance:");
		promptArr[10].setToolTipText("How many steps are executed for each click of step button.");

		// default values for input.
		inputArr[0].setText("20");
		inputArr[1].setText("15");
		inputArr[2].setText("10000");
		inputArr[3].setText("60");
		inputArr[4].setText("90");
		inputArr[5].setText("3");
		inputArr[6].setText("4");
		inputArr[7].setText("3");
		inputArr[8].setText("7");
		inputArr[9].setText("20");
		inputArr[10].setText("1");

		// button instantiation
		start = new JButton("Start Simulation");
		quit = new JButton("Quit Simulation");
		step = new JButton("Do One Step");
		step.setToolTipText("does " + inputArr[10].getText() + " steps only after initialization");

		init = new JButton("Initialize");

		step.setEnabled(false);

		// action listeners.
		start.addActionListener(this);
		quit.addActionListener(this);
		step.addActionListener(this);
		init.addActionListener(this);

		// add them.
		buttonPanel.add(init);
		buttonPanel.add(start);
		buttonPanel.add(step);
		buttonPanel.add(quit);

		// add the panels
		this.add(inputPanel, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(display, BorderLayout.CENTER);
		this.add(statPanel, BorderLayout.SOUTH);
		display.setTheSize(display.getSize());

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Voting Simulator");
		SimPanel p = new SimPanel();
		frame.add(p);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	// instance variables for sim.
	ArrayList<Voter> Q;
	ArrayList<VotingBooth> booths;
	int boothNum;
	Clock clk;
	int tableNum;
	Table[] tables;
	Producer producer;

	/**
	 * instantiates all of the variables and sets up the sim.
	 */
	private void initialize() {

		Q = new ArrayList<Voter>(0);

		booths = new ArrayList<VotingBooth>();
		boothNum = Integer.parseInt(inputArr[5].getText());
		for (int i = 0; i < boothNum; i++)
			booths.add(new VotingBooth(Q));

		clk = new Clock();
		tableNum = Integer.parseInt(inputArr[6].getText());
		tables = new Table[tableNum];
		for (int i = 0; i < tableNum; i++)
			tables[i] = new Table(Q);

		producer = new Producer(tables, Integer.parseInt(inputArr[0].getText()),
				Integer.parseInt(inputArr[3].getText()), Integer.parseInt(inputArr[1].getText()),
				Integer.parseInt(inputArr[4].getText()), Double.parseDouble(inputArr[7].getText()),
				Double.parseDouble(inputArr[8].getText()), Double.parseDouble(inputArr[9].getText()));

		clk.add(producer);
		for (int i = 0; i < tableNum; i++)
			clk.add(tables[i]);

		for (VotingBooth b : booths) {
			clk.add(b);
		}
		clk.step(0);
	}

	/**
	 * basically updates all of the stats.
	 */
	private void updateStuff() {
		int total = 0;
		for (VotingBooth b : booths) {
			total += b.getCompleted();
		}
		outArr[0][1].setText("Maximum = " + producer.getProduced() + " , Throughput = " + total);
		outArr[1][1].setText("" + booths.get(0).getTimeElapsed() / (booths.get(0).getCompleted() + 1));

		int totalLeft = 0;
		for (int i = 0; i < booths.size(); i++) {
			totalLeft += booths.get(i).getNumLeft();
		}

		for (int i = 0; i < tables.length; i++) {
			totalLeft += tables[i].getNumLeft();
		}

		outArr[2][1].setText("" + totalLeft);

		int c = 0;
		// int checkedTotal = 0;
		int totalmaxlength = 0;
		int leftOver = 0;
		for (Table t : tables) {

			if (t.getPerson() != null) {
				leftOver++;
			}
			// checkedTotal += t.getCheckedIn();
			totalmaxlength += t.getMaxqLength();
			leftOver += t.getLeft();

			c++;
		}
		outArr[3][1].setText("" + totalmaxlength / tables.length);

		int maxQLen = 0;
		for (Table t : tables) {
			if (t.getMaxQLength() > maxQLen) {
				maxQLen = t.getMaxQLength();
			}
		}

		outArr[4][1].setText(maxQLen + "");
		outArr[5][1].setText(leftOver + "");
		outArr[6][1].setText(Q.size() + "");

		outArr[8][1].setText("" + producer.regulars);
		outArr[9][1].setText("" + producer.specials);
		outArr[10][1].setText("" + producer.supers);
		outArr[11][1].setText("" + producer.limiteds);

		int tRegs = 0, tSpec = 0, tSup = 0, tLim = 0;
		for (Table t : tables) {
			tRegs += t.regLeft;
			tSpec += t.specialLeft;
			tSup += t.superLeft;
			tLim += t.limitedLeft;
		}
		outArr[12][1].setText("" + tRegs);
		outArr[13][1].setText("" + tSpec);
		outArr[14][1].setText("" + tSup);
		outArr[15][1].setText("" + tLim);

		display.updateDisplay(tables, booths, Q);
	}

	int tickNum = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == init) {
			initialize();
			stepDistance = Integer.parseInt(inputArr[10].getText());
			step.setEnabled(true);
			step.setToolTipText("does " + inputArr[10].getText() + " steps.");
			updateStuff();
		}
		if (src == start) {
			initialize();
			clk.run(Integer.parseInt(inputArr[2].getText()));
			updateStuff();
			outArr[7][1].setText("" + inputArr[2].getText());
		}
		if (src == step) {
			stepDistance = Integer.parseInt(inputArr[10].getText());
			clk.stepXTicks(tickNum, stepDistance);
			tickNum += stepDistance;
			if (tickNum >= Integer.parseInt(inputArr[2].getText())) {
				step.setEnabled(false);
				step.setToolTipText("You have reached the maximum tick count. Press initialize to reset.");
			}
			updateStuff();
			outArr[7][1].setText("" + tickNum);

		}
		if (src == quit) {
			System.exit(0);
		}

	}

}
