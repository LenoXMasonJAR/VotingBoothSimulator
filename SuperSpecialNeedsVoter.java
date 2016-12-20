package votingSimulator;

public class SuperSpecialNeedsVoter extends SpecialNeedsVoter {

	protected int votingBoothTime;
	protected int leaveTime;
	protected int checkInTime;
	protected int name;
	protected int created;

	public SuperSpecialNeedsVoter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setCheckInTime(int checkInTime) {
		this.checkInTime = checkInTime*3;
	}

	@Override
	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime * 4;
	}

	@Override
	public int getLeaveTime() {
		return leaveTime;
	}

	@Override
	public void setVotingBoothTime(int votingBoothTime) {
		this.votingBoothTime = votingBoothTime * 6;
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
