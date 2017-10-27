/**
 * 
 */
package brainSim;

/**
 * @author Wojciech Litwinowicz
 *
 */
public class GreyCell {

	private int[] birth, keep;
	private boolean state, hidden;
	private GreyCell[] neighbours;
	
	public GreyCell() {
		state = false;
		lookAround();
	}
	
	public GreyCell(boolean fromMemory) {
		state = fromMemory;
		lookAround();
	}
	
	public void recalcState() {
		int totalNeighbours = 0;
		
		for(int i = 0; i < neighbours.length; i++) {
			if(neighbours[i] != null)
				totalNeighbours += (neighbours[i].getState()? 1 : 0);
		}
		
		if(state && rulesCheck(keep, totalNeighbours)) {
			hidden = true;
		} else 
			if(!state && rulesCheck(birth, totalNeighbours))
				hidden = true;
			else
				hidden = false;
		
	}	
	
	public void revealCalc() {
		state = hidden;
	}
	
	public void setNeighbours(GreyCell[] neighbours) {
		this.neighbours = neighbours;
	}
	
	public boolean getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(state);
	}

	private void lookAround() {
		neighbours = new GreyCell[8];
		birth = new int[] {3};
		keep = new int[] {2,3};
	}
	
	private boolean rulesCheck(int[] rulesSet, int foundTotal) {
		for(int rule: rulesSet) {
			if(rule == foundTotal)
				return true;
		}
		
		return false;
	}
}
