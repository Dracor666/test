/**
 * 
 */
package brainSim;

/**
 * @author Wojciech Litwinowicz
 *
 */
public class GreyCell {

	private int life;
	private int[] birth, keep;
	private boolean state, hidden;
	private GreyCell[] neighbours;
	
	public GreyCell() {
		state = false;
		coreSetup();
	}
	
	public GreyCell(boolean fromMemory) {
		state = fromMemory;
		coreSetup();
	}
	
	public void recalcState() {
		int totalNeighbours = 0;
		
		for(int i = 0; i < neighbours.length; i++) {
			if(neighbours[i] != null)
				totalNeighbours += (neighbours[i].getState()? 1 : 0);
		}
		
		if(state && rulesCheck(keep, totalNeighbours)) {
			hidden = true;
			life++;
		} else if(!state && rulesCheck(birth, totalNeighbours)) {
			hidden = true;
			life = 1;
		} else {
			hidden = false;
			life = 0;
		}
		
	}	
	
	public void revealCalc() {
		state = hidden;
	}
	
	public void setNeighbours(GreyCell[] neighbours) {
		if(neighbours.length != 8)
			return;
		
		this.neighbours = neighbours;
	}
	
	public void updateNeighbours(GreyCell[] neighbours) {
		if(neighbours.length != 8)
			return;
		
		for(int i = 0; i < this.neighbours.length; i++)
			if(neighbours[i] != null)
				this.neighbours[i] = neighbours[i];
	}
	
	public boolean getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(state);
	}

	private void coreSetup() {
		neighbours = new GreyCell[8];
		birth = new int[] {3};
		keep = new int[] {2,3};
		life = 0;
	}
	
	private boolean rulesCheck(int[] rulesSet, int foundTotal) {
		for(int rule: rulesSet) {
			if(rule == foundTotal)
				return true;
		}
		
		return false;
	}
}
