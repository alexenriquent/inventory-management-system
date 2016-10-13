package solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problem.Matrix;
import problem.ProblemSpec;
import problem.Store;

public class RTDP {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
    private List<Matrix> probabilities;
	
	public RTDP(ProblemSpec spec) {
		this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
	}
	
	public List<Integer> selectAction(List<Integer> stockInventory) {
		List<Integer> itemOrders = new ArrayList<Integer>();
		double qValue = Double.NEGATIVE_INFINITY;
		
		double startTime = System.currentTimeMillis();

		while (System.currentTimeMillis() - startTime < 1000) {
			List<Integer> orders = generateAction(stockInventory);
			double q = qValue(stockInventory, orders);
			if (q > qValue) {
				qValue = q;
				itemOrders.clear();
				itemOrders.addAll(orders);
			}
		}

		return itemOrders;
	}
	
	public double qValue(List<Integer> stockInventory, List<Integer> itemOrders) {
		double immediateReward = reward(stockInventory);
		double expectedReward = reward(stockInventory, itemOrders);
		double transition = transition(stockInventory, itemOrders);
		
		return immediateReward + (spec.getDiscountFactor() * (transition * expectedReward));
	}
	
	public List<Integer> generateAction(List<Integer> stockInventory) {
		List<Integer> itemOrders = new ArrayList<Integer>();
		List<Integer> itemReturns = new ArrayList<Integer>();
		
		int totalItems = stockInventory.stream().mapToInt(Integer::intValue).sum();
		int totalOrders = 0;
		int totalReturns = 0;
		
		Random random = new Random();
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			if (totalItems >= store.getCapacity() || totalOrders >= store.getMaxPurchase()) {
				itemOrders.add(0);
			} else {
				int orders = random.nextInt((store.getMaxPurchase() + 1) - totalOrders);
				while (totalItems + orders > store.getCapacity()) {
					orders = random.nextInt((store.getMaxPurchase() + 1) - totalOrders);
				}
				itemOrders.add(orders);
				totalOrders += orders;
				totalItems += orders;
			}
			if (totalReturns >= store.getMaxReturns()) {
				itemReturns.add(0);
			} else {
				int returns = random.nextInt((store.getMaxReturns() + 1) - totalReturns);
				itemReturns.add(returns);
				totalReturns += returns;
			}
		}
						
		List<Integer> order = new ArrayList<Integer>(itemOrders.size());
		for(int i = 0; i < itemOrders.size(); i++) {
			if (itemOrders.get(i) - itemReturns.get(i) < 0) {
				order.add(0);
			} else {
				order.add(itemOrders.get(i) - itemReturns.get(i));
			}
		}
		
		return order;
	}
	
	public double transition(List<Integer> stockInventory, List<Integer> itemOrders) {
		double totalTransitionProbability = 1.0;
		List<Integer> updatedInventory = nextState(stockInventory, itemOrders);
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double transitionProbability = 0.0;
			int totalItems = stockInventory.get(i) + itemOrders.get(i);
			if (updatedInventory.get(i) > totalItems) {
				transitionProbability = 0.0;
			} else if (updatedInventory.get(i) > 0 && updatedInventory.get(i) <= totalItems) {
				transitionProbability = probabilities.get(i).get(totalItems, totalItems - updatedInventory.get(i));
			} else if (updatedInventory.get(i) == 0) {
				for (int j = totalItems; j < store.getCapacity(); j++) {
					transitionProbability += probabilities.get(i).get(totalItems, j);
				}
			}
			totalTransitionProbability *= transitionProbability;
		}
		
		return totalTransitionProbability;
	}
	
	public double reward(List<Integer> stockInventory) {
		double totalReward = 0.0;
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double reward = 0.0;
			for (int j = stockInventory.get(i) + 1; j < store.getCapacity(); j++) {
				reward = j - stockInventory.get(i) * (probabilities.get(i).get(stockInventory.get(i), j));
			}
			totalReward += -1 * reward;
		}
		
		return totalReward;
	}
	
	public double reward(List<Integer> stockInventory, List<Integer> itemOrders) {
		double totalReward = 0.0;
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			double reward = 0.0;
			for (int j = stockInventory.get(i) + itemOrders.get(i) + 1; j < store.getCapacity(); j++) {
				reward = j - stockInventory.get(i) - itemOrders.get(i) 
					   * (probabilities.get(i).get(stockInventory.get(i) + itemOrders.get(i), j));
			}
			totalReward += -1 * reward;
		}
		
		return totalReward;
	}
	
	public List<Integer> nextState(List<Integer> stockInventory, List<Integer> itemOrders) {
		List<Integer> updatedInventory = new ArrayList<Integer>();
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			updatedInventory.add(stockInventory.get(i) + itemOrders.get(i));
		}
		
		return updatedInventory;
	}
}
