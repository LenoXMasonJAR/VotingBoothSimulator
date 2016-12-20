package votingSimulator;

import java.util.ArrayList;

/**
 * @author Brendan Cronan
 */
public class Clock {
	/**
	 * ArrayList of Classes that implement clock listener. these are the things
	 * to tick.
	 */
	private ArrayList<ClockListener> listeners;

	public Clock() {
		listeners = new ArrayList<ClockListener>(0);
	}

	/**
	 * steps numTicks times.
	 * 
	 * @param numTicks
	 *            how many ticks to run for.
	 */
	public void run(int numTicks) {
		for (int tick = 0; tick <= numTicks; tick++) {
			step(tick);
		}
	}

	/**
	 * does one tick in every listener.
	 * 
	 * @param tick
	 *            what is the current tick.
	 */
	public void step(int tick) {
		for (int j = 0; j < listeners.size(); j++)
			listeners.get(j).event(tick);
	}

	/**
	 * Steps x ticks from initial tick.
	 * 
	 * @param tick
	 *            what tick you are on
	 * @param x
	 *            how many ticks to do.
	 */
	public void stepXTicks(int tick, int x) {
		for (int i = tick; i < x + tick; i++) {
			step(i);
		}

	}

	/**
	 * adds a listener to the list.
	 * 
	 * @param cl
	 *            listener to add.
	 */
	public void add(ClockListener cl) {
		listeners.add(cl);
	}

	/**
	 * returns listeners.
	 * 
	 * @return array of listeners.
	 */
	public ArrayList<ClockListener> getMyListeners() {
		return listeners;
	}

	public void setMyListeners(ArrayList<ClockListener> newListeners) {
		this.listeners = newListeners;
	}

	/**
	 * returns number of listeners in the list.
	 * 
	 * @return number of listeners.
	 */
	public int getNumListeners() {
		return listeners.size();
	}

}