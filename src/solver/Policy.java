package solver;

import java.util.ArrayList;
import java.util.List;

public class Policy {
	
	private List<Integer> state;
	private List<Integer> action;
	private List<Integer> finalState;
	private double value;
	private int totalItems;
	
	public Policy() {
		state = new ArrayList<Integer>();
		action = new ArrayList<Integer>();
		finalState = new ArrayList<Integer>();
		value = 0.0;
		totalItems = 0;
	}
	
	public Policy(int items) {
		state = new ArrayList<Integer>();
		state.add(items);
		totalItems = items;
	}
	
	public Policy(Policy policy, int items) {
		state = new ArrayList<Integer>(policy.getState());
		state.add(items);
		totalItems = policy.getTotalItems() + items;
	}
	
	public List<Integer> getState() {
		return state;
	}
	
	public List<Integer> getAction() {
		return action;
	}
	
	public List<Integer> getFinalState() {
		return finalState;
	}
	
	public double getValue() {
		return value;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
}
