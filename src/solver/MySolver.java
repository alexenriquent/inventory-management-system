package solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problem.Store;
import problem.Matrix;
import problem.ProblemSpec;

public class MySolver implements OrderingAgent {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
    private List<Matrix> probabilities;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
	}
	
	public void doOfflineComputation() {
	    // TODO Write your own code here.
	}
	
	public List<Integer> generateStockOrder(List<Integer> stockInventory, int numWeeksLeft) {
		List<Integer> itemOrders = generateAction(stockInventory);
				
		for (int i = 0; i < 10; i++) {
			List<Integer> a = generateAction(stockInventory);
			System.out.println(a);
			System.out.println(rewardFunction(stockInventory, a));
		}
		
		return itemOrders;
	}
	
	public List<Integer> generateAction(List<Integer> stockInventory) {
		List<Integer> itemOrders = new ArrayList<Integer>();
		
		int totalItems = stockInventory.stream().mapToInt(Integer::intValue).sum();
		int totalOrders = 0;
		
		Random random = new Random();
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			if (totalItems >= store.getCapacity() || totalOrders >= store.getMaxPurchase()) {
				itemOrders.add(0);
			} else {
				int orders = random.nextInt((store.getMaxPurchase() + 1) - totalOrders);
				itemOrders.add(orders);
				totalOrders += orders;
				totalItems += orders;
			}
		}
		
		return itemOrders;
	}
	
	public double rewardFunction(List<Integer> stockInventory, List<Integer> itemOrders) {
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

}
