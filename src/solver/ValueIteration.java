package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import problem.ProblemSpec;
import problem.Store;

public class ValueIteration {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
	private List<Policy> policies;
	
	private static final double EPSILON = 1e-7;
	
	public ValueIteration(ProblemSpec spec) {
		this.spec = spec;
		store = spec.getStore();
		policies = new ArrayList<Policy>();
	}
	
	public void valueIteration() {
		for (Policy policy : policies) {
			policy.setValue(policy.reward());
		}	
		for (Policy policy : policies) {
			double exponent = 1.0;
										
			while (true) {
				double maxValue = Double.NEGATIVE_INFINITY;
				for (List<Integer> action : policy.getActions()) {
					double value = value(policy, action, exponent);
					if (value >= maxValue) {
						maxValue = value;
						policy.getOptimalAction().clear();
						policy.getOptimalAction().addAll(action);
					}
				}
				if (Math.abs(maxValue - policy.getValue()) < EPSILON) break;
				policy.setValue(maxValue);
				exponent++;
			}
		}
	}
	
	private double value(Policy policy, List<Integer> action, double exponent) {
		double immediateReward = policy.getValue();
		double expectedReward = policy.reward(action);
		double transition = policy.transition(action);
		
		if (transition == 0.0) {
			return Double.NEGATIVE_INFINITY;
		} else {
			return immediateReward + (Math.pow(spec.getDiscountFactor(), exponent) * (transition * expectedReward));
		}
	}
	
	public List<Policy> getPolicies() {
		return policies;
	}
	
	public List<Integer> getOptimalPolicy(List<Integer> state) {
		for (Policy policy : policies) {
			if (state.equals(policy.getState())) {
				return new ArrayList<Integer>(policy.getOptimalAction());
			}
		}
		return new ArrayList<Integer>(Collections.nCopies(store.getMaxTypes(), 0));
	}
	
	public void generatePolicies() {
		List<List<Integer>> states = generateStates();
		List<List<Integer>> actions = generateActions();
		
		for (List<Integer> state : states) {
			policies.add(new Policy(spec, state, actions));
		}
	}
	
	private List<List<Integer>> generateStates() {
		List<List<Integer>> states = new ArrayList<List<Integer>>();
		
		for (int i = 0; i <= store.getCapacity(); i++) {
			List<Integer> state = new ArrayList<Integer>();
			state.add(i);
			states.add(state);
		}
		
		for (int i = 1; i < store.getMaxTypes(); i++) {
			List<List<Integer>> tempStates = new ArrayList<List<Integer>>();
			for (List<Integer> state : states) {
				int totalItems = state.stream().mapToInt(Integer::intValue).sum();
				for (int j = 0; j <= store.getCapacity() - totalItems; j++) {
					List<Integer> newState = new ArrayList<Integer>(state);
					newState.add(j);
					tempStates.add(newState);
				}
			}
			states.clear();
			states.addAll(tempStates);
			tempStates.clear();
		}
		
		return states;
	}
	
	private List<List<Integer>> generateActions() {
		List<List<Integer>> actions = new ArrayList<List<Integer>>();
		
		for (int i = 0; i <= store.getMaxPurchase(); i++) {
			List<Integer> action = new ArrayList<Integer>();
			action.add(i);
			actions.add(action);
		}
		
		for (int i = 1; i < store.getMaxTypes(); i++) {
			List<List<Integer>> tempActions = new ArrayList<List<Integer>>();
			for (List<Integer> action : actions) {
				int totalItems = action.stream().mapToInt(Integer::intValue).sum();
				for (int j = 0; j <= store.getMaxPurchase() - totalItems; j++) {
					List<Integer> newAction = new ArrayList<Integer>(action);
					newAction.add(j);
					tempActions.add(newAction);
				}
			}
			actions.clear();
			actions.addAll(tempActions);
			tempActions.clear();
		}
		
		return actions;
	}
}
