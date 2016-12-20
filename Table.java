package votingSimulator;

import java.util.ArrayList;

public class Table implements ClockListener {

	/**
	 * Queue before table.
	 */
	private ArrayList<Voter> Q = new ArrayList<Voter>();

	/**
	 * tick when the next thing happens
	 */
	private int timeOfNextEvent = 0;
	/**
	 * stat for maximum Queue Length
	 */
	private int maxqLength = 0;
	/**
	 * Person at the table currently
	 */
	private Voter person;
	/**
	 * stat for throughput
	 */
	private int completed = 0;
	/**
	 * place where the voter is going
	 */
	ArrayList<Voter> nextDestination = new ArrayList<Voter>();
	/**
	 * max Queue Length
	 */
	private int maxQLen = 0;
	/**
	 * number of people that left the line.
	 */
	private int numLeft = 0;
	/**
	 * number of each kind of voter that left the line.
	 */
	public int regLeft = 0, specialLeft = 0, superLeft = 0, limitedLeft = 0;

	public Table(ArrayList<Voter> queue) {
		nextDestination = queue;

	}

	/**
	 * adds a new voter to the line.
	 */
	public void add(Voter person) {
		Q.add(person);
		if (Q.size() > maxqLength)
			maxqLength = Q.size();
	}

	public void event(int tick) {
		if (tick >= timeOfNextEvent) {
			if (person != null) { // Notice the delay that takes place here
				nextDestination.add(person);
				// take this person to the next station.
				if (nextDestination.size() > maxQLen) {
					maxQLen = nextDestination.size();
				}
				person = null; // I have sent the person on.
			}

			if (Q.size() >= 1) {
				person = Q.remove(0); // do not send this person as of yet, make
										// them wait.
				timeOfNextEvent = tick + (person.getCheckInTime() + 1);
				completed++;
				for (int i = 0; i < Q.size(); i++) {
					Voter v = Q.get(i);
					if (tick >= v.getCreationTime() + v.getLeaveTime()) {
						Q.remove(i);
						numLeft++;
						if (v instanceof RegularVoter) {
							regLeft++;
						} else if (v instanceof SpecialNeedsVoter) {
							specialLeft++;
						} else if (v instanceof SuperSpecialNeedsVoter) {
							superLeft++;
						} else if (v instanceof LimitedTimeVoter) {
							limitedLeft++;
						}
					}
				}
			}
		}
	}

	// Getters and Setters.......
	public ArrayList<Voter> getQ() {
		return Q;
	}

	public int getLeft() {
		return Q.size();
	}

	public int getMaxqLength() {
		return maxqLength;
	}

	public int getMaxQLength() {
		return maxQLen;
	}

	public int getCheckedIn() {
		return completed;
	}

	/**
	 * @return the numLeft
	 */
	public int getNumLeft() {
		return numLeft;
	}

	public Voter getPerson() {
		return person;
	}

}