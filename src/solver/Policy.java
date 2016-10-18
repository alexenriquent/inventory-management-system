package solver;

import java.util.ArrayList;
import java.util.List;

import problem.Matrix;
import problem.ProblemSpec;
import problem.Store;

public class Policy {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
    private List<Matrix> probabilities;
    
	private List<Integer> state;
	private List<List<Integer>> actions;
	private List<Integer> optimalAction;
	private double value;
	
	public Policy(ProblemSpec spec) {
		this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
		state = new ArrayList<Integer>();
		actions = new ArrayList<List<Integer>>();
		optimalAction = new ArrayList<Integer>();
		value = 0.0;
	}
	
	public Policy(ProblemSpec spec, List<Integer> state, List<List<Integer>> actions) {
		this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
		this.state = new ArrayList<Integer>(state);
		this.actions = getValidActions(actions);
		optimalAction = new ArrayList<Integer>();
		value = 0.0;
	}
	
	public List<Integer> getState() {
		return state;
	}
	
	public List<List<Integer>> getActions() {
		return actions;
	}
	
	public List<Integer> getOptimalAction() {
		return optimalAction;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	private List<List<Integer>> getValidActions(List<List<Integer>> actions) {
		List<List<Integer>> validActions = new ArrayList<List<Integer>>();
		for (List<Integer> action : actions) {
			List<Integer> nextState = nextState(action);
			if (nextState.stream().mapToInt(Integer::intValue).sum() <= store.getCapacity() &&
				validAction(action)) {
				List<Integer> validAction = new ArrayList<Integer>(action);
				validActions.add(validAction);
			}
		}
		return validActions;
	}
	
	private boolean validAction(List<Integer> action) {
		List<Integer> nextState = nextState(action);
		for (int items : nextState) {
			if (items < 0) {
				return false;
			}
		}
		return true;
	}
	
	public double transition(List<Integer> action) {
		double totalTransitionProbability = 1.0;
		List<Integer> nextState = nextState(action);
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double transitionProbability = 0.0;
			int totalItems = state.get(i) + action.get(i);
			if (nextState.get(i) > totalItems) {
				transitionProbability = 0.0;
			} else if (nextState.get(i) > 0 && nextState.get(i) <= totalItems) {
				transitionProbability = probabilities.get(i).get(totalItems, totalItems - nextState.get(i));
			} else if (nextState.get(i) == 0) {
				for (int j = totalItems; j < store.getCapacity(); j++) {
					transitionProbability += probabilities.get(i).get(totalItems, j);
				}
			}
			if (transitionProbability == 0.0) {
				transitionProbability = 1.0;
			}
			totalTransitionProbability *= transitionProbability;
		}
		
		return totalTransitionProbability;
	}
	
	public double reward() {
		double totalReward = 0.0;
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double reward = 0.0;
			for (int j = state.get(i) + 1; j < store.getCapacity(); j++) {
				reward = (j - state.get(i)) * spec.getPrices().get(i) * (probabilities.get(i).get(state.get(i), j));
			}
			totalReward += -1 * reward;
		}
		
		return totalReward;
	}
	
	public double reward(List<Integer> action) {
		double totalReward = 0.0;
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double reward = 0.0;
			for (int j = state.get(i) + action.get(i) + 1; j < store.getCapacity(); j++) {
				reward = (j - state.get(i) - action.get(i)) * spec.getPrices().get(i)
					   * (probabilities.get(i).get(state.get(i) + action.get(i), j));
			}
			totalReward += -1 * reward;
		}
		
		return totalReward;
	}
	
	private List<Integer> nextState(List<Integer> action) {
		List<Integer> nextState = new ArrayList<Integer>();
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			nextState.add(state.get(i) + action.get(i));
		}
		
		return nextState;
	}
}
