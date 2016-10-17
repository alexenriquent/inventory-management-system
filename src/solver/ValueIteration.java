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
			double gamma = spec.getDiscountFactor();
			double exponent = 1.0;
										
			while (true) {
				double maxValue = Double.NEGATIVE_INFINITY;
				for (List<Integer> action : policy.getActions()) {
					double immediateReward = policy.getValue();
					double expectedReward = policy.reward(action);
					double transition = policy.transition(action);
					double value = immediateReward + (Math.pow(gamma, exponent) * (transition * expectedReward));
					if (value > maxValue) {
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
	
//	public List<List<Integer>> generateActions() {
//		List<List<Integer>> orders = new ArrayList<List<Integer>>();
//		List<List<Integer>> returns = new ArrayList<List<Integer>>();
//		List<List<Integer>> actions = new ArrayList<List<Integer>>();
//		
//		for (int i = 0; i <= store.getMaxPurchase(); i++) {
//			List<Integer> orderItems = new ArrayList<Integer>();
//			orderItems.add(i);
//			orders.add(orderItems);
//		}
//		
//		for (int i = 0; i <= store.getMaxReturns(); i++) {
//			List<Integer> returnItems = new ArrayList<Integer>();
//			returnItems.add(i);
//			returns.add(returnItems);
//		}
//		
//		for (int i = 1; i < store.getMaxTypes(); i++) {
//			List<List<Integer>> tempOrders = new ArrayList<List<Integer>>();
//			for (List<Integer> orderItems : orders) {
//				int totalItems = orderItems.stream().mapToInt(Integer::intValue).sum();
//				for (int j = 0; j <= store.getMaxPurchase() - totalItems; j++) {
//					List<Integer> newOrderItems = new ArrayList<Integer>(orderItems);
//					newOrderItems.add(j);
//					tempOrders.add(newOrderItems);
//				}
//			}
//			orders.clear();
//			orders.addAll(tempOrders);
//			tempOrders.clear();
//		}
//		
//		for (int i = 1; i < store.getMaxTypes(); i++) {
//			List<List<Integer>> tempReturns = new ArrayList<List<Integer>>();
//			for (List<Integer> returnItems : returns) {
//				int totalItems = returnItems.stream().mapToInt(Integer::intValue).sum();
//				for (int j = 0; j <= store.getMaxReturns() - totalItems; j++) {
//					List<Integer> newReturnItems = new ArrayList<Integer>(returnItems);
//					newReturnItems.add(j);
//					tempReturns.add(newReturnItems);
//				}
//			}
//			returns.clear();
//			returns.addAll(tempReturns);
//			tempReturns.clear();
//		}
//		
//		actions.addAll(orders);
//		
//		for (List<Integer> orderItems : orders) {
//			for (List<Integer> returnItems : returns) {
//				List<Integer> returnList = new ArrayList<Integer>();
//				for (int i = 0; i < store.getMaxTypes(); i++) {
//					returnList.add(orderItems.get(i) - returnItems.get(i));
//				}
//				if (isValidAction(actions, returnList)) {
//					actions.add(returnList);
//				}
//			}
//		}
//		
//		return actions;
//	}
//	
//	private boolean isValidAction(List<List<Integer>> actions, List<Integer> newAction) {
//		for (int i = 0; i < actions.size(); i++) {
//			if (newAction.equals(actions.get(i))) {
//				return false;
//			}
//		}
//		return true;
//	}
}
