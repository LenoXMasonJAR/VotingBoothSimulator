package votingSimulator;

import java.util.Random;

public class Producer implements ClockListener {
	/**
	 * Voter being produced
	 */
	private Voter voter;
	/**
	 * duh
	 */
	private int ticksUntilNextVoter = 0;
	/**
	 * random number generator
	 */
	private Random r = new Random();
	/**
	 * array of tables. allows for dynamic number of tables.
	 */
	private Table[] tables;
	/**
	 * booth time
	 */
	private int avgBoothTime;
	/**
	 * leave time
	 */
	private int avgLeaveTime;
	/**
	 * check in time
	 */
	private int avgCheckInTime;
	/**
	 * how often voters are produced
	 */
	private int frequency;
	/**
	 * number produced
	 */
	private int produced = 0;
	/**
	 * number produced of each type.
	 */
	public int regulars, specials, supers, limiteds;
	/**
	 * percent chance for each one.
	 */
	private double superChance, specialChance, limitedChance;

	public Producer(Table[] tables, int frequency, int averageBoothTime, int averageCheckInTime, int averageLeaveTime,
			double percentChanceSuperSpecial, double percentChanceSpecial, double percentChanceLimited) {
		// set all the instance variables
		this.frequency = frequency;
		avgBoothTime = averageBoothTime;
		avgCheckInTime = averageCheckInTime;
		avgLeaveTime = averageLeaveTime;

		// percent chance for each type
		superChance = percentChanceSuperSpecial;
		specialChance = percentChanceSpecial;
		limitedChance = percentChanceLimited;

		//counters
		regulars = 0;
		specials = 0;
		supers = 0;
		limiteds = 0;

		this.tables = tables;
	}

	@Override
	public void event(int tick) {
		if (ticksUntilNextVoter <= tick) {
			int temp = (int) (frequency * 0.5 * r.nextGaussian() + frequency + .5);

			ticksUntilNextVoter = tick + temp;

			voter = createVoter(tick);
			tables[voter.getName()].add(voter);

		}

	}

	/**
	 * Creates a voter
	 */
	private Voter createVoter(int tick) {
		Voter out;

		//random numbers based on averages.
		int boothTime = (int) (avgBoothTime * 0.5 * r.nextGaussian() + avgBoothTime + .5);
		int leaveTime = (int) (avgLeaveTime * 0.5 * r.nextGaussian() + avgLeaveTime + .5);
		int checkInTime = (int) (avgCheckInTime * 0.5 * r.nextGaussian() + avgCheckInTime + .5);

		//percent chances for each type.
		int rand = r.nextInt(100);
		if (rand <= superChance) {
			out = new SuperSpecialNeedsVoter();
			supers++;
		} else if (rand <= superChance + specialChance) {
			out = new SpecialNeedsVoter();
			specials++;
		} else if (rand <= superChance + specialChance + limitedChance) {
			out = new LimitedTimeVoter();
			limiteds++;
		} else {
			out = new RegularVoter();
			regulars++;
		}

		//set all the variables in voter.
		out.setLeaveTime(leaveTime);
		out.setCheckInTime(checkInTime);
		out.setVotingBoothTime(boothTime);
		out.setName(r.nextInt(tables.length));
		out.setTimeCreated(tick);

		produced++;
		return out;
	}

	/**
	 * @return the number of voters created
	 */
	public int getProduced() {
		return produced;
	}

}
