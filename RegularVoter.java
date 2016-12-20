package votingSimulator;

public class RegularVoter extends Voter {
	protected int votingBoothTime;
	protected int leaveTime;
	protected int checkInTime;
	protected int name;
	protected int created;

	public RegularVoter() {
	}

	public void setName(int name) {
		this.name = name;
	}

	public void setTimeCreated(int tick) {
		this.created = tick;
	}

	@Override
	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	@Override
	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime;
	}

	@Override
	public void setVotingBoothTime(int votingBoothTime) {
		this.votingBoothTime = votingBoothTime;
	}

	@Override
	public int getVotingBoothTime() {
		return votingBoothTime;
	}

	@Override
	public int getCheckInTime() {
		return checkInTime;
	}

	@Override
	public int getName() {
		return name;
	}

	@Override
	public int getLeaveTime() {
		return leaveTime;
	}

	@Override
	public int getCreationTime() {
		return created;
	}

}
