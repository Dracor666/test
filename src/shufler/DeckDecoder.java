/**
 * 
 */
package shufler;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;

/**
 * @author Wojciech Litwinowicz
 *
 */
public class DeckDecoder {
	
	int top;
	Stack<Integer> deck;
	
	public DeckDecoder(int x){
		top = x;
		deck = new Stack<>();
		
		for(int i = 0; i < x + 2; i++) 
			deck.add(0,i);
		
		Random rand = new Random();		
		int c, tmp;
		
		for(int i = 0; i < x; i++) {
			c = Math.abs(rand.nextInt(x));
			
			tmp = deck.get(c);			
			deck.remove(c);
			deck.push(tmp);
		}
	}
	
	public int getNewNumber() {
		Stack<Integer> left = new Stack<>();
		
		int currentAId = moveDetector(top);
		moveDetector(top + 1);
		int currentBId = moveDetector(top + 1);		
		
		if(currentBId > currentAId) 
			currentBId = (currentAId + currentBId) - (currentAId = currentBId);
		
		left.addAll(deck.subList(currentAId + 1, deck.size()));
		left.addAll(deck.subList(currentBId, currentAId + 1));
		left.addAll(deck.subList(0, currentBId));
		
		deck = new Stack<>();
		
		deck.push(left.get(0));
		left.remove(0);
		
		for(int i = 0; i < (deck.get(0) - (deck.get(0) > top?1:0)); i++) {
			deck.push(left.pop());
		}
		
		deck.addAll(left);
		
		return deck.get(deck.size() - 1 - (deck.peek() - (deck.peek() > top?1:0)));
	}
	
	private int moveDetector(Integer detectorValue) {
		int index = deck.indexOf(detectorValue);
		
		if(index > -1) {
			deck.remove(index);
			
			index--;
			if(index < 0)
				index = top;
			
			deck.add(index, detectorValue);
			
		} else throw new RuntimeException("Detector error");
		
		return index;
	}
}
