/**
 * 
 */
package brainSim;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Litwinowicz
 *
 */
public class GreyMatrix {

	private List<GreyCell> fullMatrix;
	private int setSize;
	
	public GreyMatrix(boolean[][] memoryMatrix) {
		fullMatrix = new ArrayList<>();
		setSize = memoryMatrix[0].length;
		
		GreyCell[][] holdingMatrix = new GreyCell[memoryMatrix.length][setSize];
		
		for(int y = 0; y < memoryMatrix.length; y++) {
			for(int x = 0; x < memoryMatrix[y].length; x++) {
				holdingMatrix[y][x] = new GreyCell(memoryMatrix[y][x]);
			}
		}
		
		GreyCell[] neighbours;
		for(int y = 0; y < holdingMatrix.length; y++) {
			for(int x = 0; x < holdingMatrix[y].length; x++) {
				neighbours = new GreyCell[8];
				
				if(x != 0) 
					neighbours[3] = holdingMatrix[y][x-1];
				if(x != holdingMatrix[y].length - 1)
					neighbours[4] = holdingMatrix[y][x+1];
				
				if(y != 0) {
					neighbours[1] = holdingMatrix[y-1][x];
					
					if(x != 0) 
						neighbours[0] = holdingMatrix[y-1][x-1];
					if(x != holdingMatrix[y].length - 1)
						neighbours[2] = holdingMatrix[y-1][x+1];
				}				
				
				if(y < holdingMatrix.length - 1 ) {
					neighbours[6] = holdingMatrix[y+1][x];
					
					if(x != 0) 
						neighbours[5] = holdingMatrix[y+1][x-1];
					if(x != holdingMatrix[y].length - 1)
						neighbours[7] = holdingMatrix[y+1][x+1];
				}
				
				holdingMatrix[y][x].setNeighbours(neighbours);				
				fullMatrix.add(holdingMatrix[y][x]);
			}
		}
	}
	
	public void update() {
		for(GreyCell cell: fullMatrix) {
			cell.recalcState();
		}
		for(GreyCell cell: fullMatrix) {
			cell.revealCalc();
		}
	}
	
	public String[] project(){
		String[] canvas = new String[fullMatrix.size()/setSize];
		
		for(int y = 0; y < canvas.length; y++) {
			canvas[y] = (fullMatrix.get(y*setSize).getState()?"XX":"[]");
			
			for(int x = 1; x < setSize; x++) {
				canvas[y] += (fullMatrix.get(y*setSize + x).getState()?"XX":"[]");
			}			
		}
		
		return canvas;
	}
}
