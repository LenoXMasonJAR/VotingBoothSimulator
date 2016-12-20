package votingSimulator;

public class LimitedTimeVoter extends Voter {

	protected int votingBoothTime;
	protected int leaveTime;
	protected int checkInTime;
	protected int name;
	protected int created;

	public LimitedTimeVoter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime;
	}

	@Override
	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime / 2;
	}

	@Override
	public int getLeaveTime() {
		return leaveTime;
	}

	@Override
	public void setVotingBoothTime(int votingBoothTime) {
		this.votingBoothTime = votingBoothTime/2;
	}

	@Override
	public int getVotingBoothTime() {
		return this.votingBoothTime;
	}

	@Override
	public int getCheckInTime() {
		// TODO Auto-generated method stub
		return this.checkInTime;
	}

	@Override
	public int getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public int getCreationTime() {
		// TODO Auto-generated method stub
		return this.created;
	}

	@Override
	public void setTimeCreated(int timeCreated) {
		this.created = timeCreated;
	}

	@Override
	public void setName(int name) {
		this.name = name;
	}

}
