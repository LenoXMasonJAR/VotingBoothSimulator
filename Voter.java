package votingSimulator;

public abstract class Voter {

	/**
	 * Method to set the check in time.
	 * @param checkInTime time for voter to check in.
	 */
	public abstract void setCheckInTime(int checkInTime);

	/**
	 * Method that sets the leave time.
	 * @param leaveTime time before voter leaves.
	 */
	public abstract void setLeaveTime(int leaveTime);

	/**
	 * returns leave time.
	 * @return leave time
	 */
	public abstract int getLeaveTime();
	
	/**
	 * sets the voting booth time.
	 * @param votingBoothTime set time for voter to vote in the booth.
	 */
	public abstract void setVotingBoothTime(int votingBoothTime);

	/**
	 * returns voting time.
	 * @return voting booth time
	 */
	public abstract int getVotingBoothTime();

	/**
	 * returns check in time
	 * @return check in time.
	 */
	public abstract int getCheckInTime();

	/**
	 * returns the "name" of the voter
	 * @return which table it will be sent to.
	 */
	public abstract int getName();

	/**
	 * returns the creation time.
	 * @return the time that the voter was created.
	 */
	public abstract int getCreationTime();
	
	/**
	 * set the creation time.
	 * @param timeCreated time that the voter was produced.
	 */
	public abstract void setTimeCreated(int timeCreated);
	
	/**
	 * set the "name" of the voter
	 * @param name which table the voter will go to.
	 */
	public abstract void setName(int name);

}
