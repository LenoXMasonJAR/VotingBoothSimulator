package votingSimulator;

import java.util.ArrayList;

public class VotingBooth implements ClockListener {

	/**
	 * Where it gets the voters.
	 */
	private ArrayList<Voter> source;
	/**
	 * time of next event
	 */
	private int nextEventTick = 0;
	/**
	 * voter in the booth
	 */
	private Voter currentVoter = null;
	/**
	 * number of completed voters
	 */
	private int completed = 0;
	/**
	 * how long the voters took
	 */
	private int totalCompletionTime = 0;
	/**
	 * how many left the Queue
	 */
	private int numLeft = 0;
	/**
	 * types that left.
	 */
	public int regLeft = 0, specialLeft = 0, superLeft = 0, limitedLeft = 0;

	public VotingBooth(ArrayList<Voter> queue) {
		source = queue;
	}

	@Override
	public void event(int tick) {

		if (tick >= nextEventTick) {

			currentVoter = null;

			if (source.size() > 0) {
				// get a voter and set time for that voter's vote time.
				currentVoter = source.remove(0);
				nextEventTick = tick + (currentVoter.getVotingBoothTime() + 1);
				totalCompletionTime += tick - currentVoter.getCreationTime() + currentVoter.getVotingBoothTime();
				// +1 to completed
				setCompleted(getCompleted() + 1);

				for (int i = 0; i < source.size(); i++) {
					Voter v = source.get(i);
					if (tick >= v.getCreationTime() + v.getLeaveTime()) {
						source.remove(i);
						numLeft++;
						// look through and see if any should leave.
						// remove them.
						// +1 to counters
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

	/**
	 * @return the number of voters that have been through this booth.
	 */
	public int getCompleted() {
		return completed;
	}

	private void setCompleted(int completed) {
		this.completed = completed;
	}

	public int getTimeElapsed() {
		return totalCompletionTime;
	}

	/**
	 * @return the numLeft
	 */
	public int getNumLeft() {
		return numLeft;
	}

	public Voter getCurrentVoter() {
		return currentVoter;
	}

}
