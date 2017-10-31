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
	
	public void connect2Right(GreyMatrix next) {
		List<GreyCell> input = getEdge(4), output = next.getEdge(3);
		GreyCell[] overwriteNeighbours;
		
		for(int y = 0; y < output.size(); y++) {
			overwriteNeighbours = new GreyCell[8];
			
			overwriteNeighbours[3] = input.get(y);
			if(y != 0)
				overwriteNeighbours[0] = input.get(y-1);
			if(y != output.size() - 1)
				overwriteNeighbours[5] = input.get(y+1);
			
			output.get(y).updateNeighbours(overwriteNeighbours);			
		}
	}
	
	public void connect(GreyMatrix next) {
		List<GreyCell> input = getEdge(4), output = next.getEdge(3);
		if(input.size() != output.size()) {
			throw new RuntimeException("smthing went haywire...");
		}
		
		GreyCell[] overwriteNeighbours;
		
		for(int y = 0; y < output.size(); y++) {
			overwriteNeighbours = new GreyCell[8];
			
			overwriteNeighbours[3] = input.get(y);
			if(y != 0)
				overwriteNeighbours[0] = input.get(y-1);
			if(y != output.size() - 1)
				overwriteNeighbours[5] = input.get(y+1);
			
			output.get(y).updateNeighbours(overwriteNeighbours);
			
			overwriteNeighbours = new GreyCell[8];
			
			overwriteNeighbours[4] = output.get(y);
			if(y != 0)
				overwriteNeighbours[2] = output.get(y-1);
			if(y != output.size() - 1)
				overwriteNeighbours[7] = output.get(y+1);
			
			input.get(y).updateNeighbours(overwriteNeighbours);
			
		}
	}
	
	public List<GreyCell> getEdge(int side){
		List<GreyCell> edge = new ArrayList<>();
		int cellID = 0, step = 1, overflow = fullMatrix.size();
		
		switch(side) {
		case 1: //up
			overflow = setSize;
			break;
		case 2: //down
			cellID = fullMatrix.size() - setSize;
			break;
		case 3: //left
			step = setSize;
			break;
		case 4: //right
			cellID = setSize - 1;
			step = setSize;
			break;
		default:
			throw new RuntimeException("smthing went haywire...");
		}
		
		while(cellID < overflow) {
			edge.add(fullMatrix.get(cellID));
			cellID += step;
		}
		
		return edge;
	}
	
	public List<GreyCell> getFullMatrix(){
		return fullMatrix;
	}
}
