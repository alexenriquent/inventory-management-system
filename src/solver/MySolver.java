package solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import problem.Store;
import problem.Matrix;
import problem.ProblemSpec;

public class MySolver implements OrderingAgent {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
    private List<Matrix> probabilities;
    
    private Map<List<Integer>, Map<List<Integer>, Double>> policies;
	private Map<List<Integer>, List<Integer>> optimalPolicies;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
        
        policies = new LinkedHashMap<List<Integer>, Map<List<Integer>, Double>>();
        optimalPolicies = new LinkedHashMap<List<Integer>, List<Integer>>();
	}
	
	public void doOfflineComputation() {
	    generateStates();
	    
	    for (List<Integer> stockInventory : generateStates()) {
	    	System.out.println(stockInventory);
	    }
	    
	    System.out.println();
	    
	    for (List<Integer> order : generateActions()) {
	    	System.out.println(order);
	    }
	}
	
	public List<Integer> generateStockOrder(List<Integer> stockInventory, int numWeeksLeft) {
		RTDP RTDP = new RTDP(spec);
		List<Integer> itemOrders = RTDP.selectAction(stockInventory);
				
		return itemOrders;
	}
	
	public List<List<Integer>> generateStates() {
		List<List<Integer>> stockInventories = new ArrayList<List<Integer>>();
		
		for (int i = 0; i <= store.getCapacity(); i++) {
			List<Integer> stockInventory = new ArrayList<Integer>();
			stockInventory.add(i);
			stockInventories.add(stockInventory);
		}
		
		for (int i = 1; i < store.getMaxTypes(); i++) {
			List<List<Integer>> tempInventories = new ArrayList<List<Integer>>();
			for (List<Integer> stockInventory : stockInventories) {
				int totalItems = stockInventory.stream().mapToInt(Integer::intValue).sum();
				for (int j = 0; j <= store.getCapacity() - totalItems; j++) {
					List<Integer> inventory = new ArrayList<Integer>(stockInventory);
					inventory.add(j);
					tempInventories.add(inventory);
				}
			}
			stockInventories.clear();
			stockInventories.addAll(tempInventories);
			tempInventories.clear();
		}
		
		return stockInventories;
	}

	public List<List<Integer>> generateActions() {
		List<List<Integer>> orders = new ArrayList<List<Integer>>();
		
		for (int i = 0; i <= store.getMaxPurchase(); i++) {
			List<Integer> order = new ArrayList<Integer>();
			order.add(i);
			orders.add(order);
		}
		
		for (int i = 1; i < store.getMaxTypes(); i++) {
			List<List<Integer>> tempOrders = new ArrayList<List<Integer>>();
			for (List<Integer> order : orders) {
				int totalItems = order.stream().mapToInt(Integer::intValue).sum();
				for (int j = 0; j <= store.getMaxPurchase() - totalItems; j++) {
					List<Integer> itemOrders = new ArrayList<Integer>(order);
					itemOrders.add(j);
					tempOrders.add(itemOrders);
				}
			}
			orders.clear();
			orders.addAll(tempOrders);
			tempOrders.clear();
		}
		
		return orders;
	}
	
	public List<Integer> nextState(List<Integer> stockInventory, List<Integer> itemOrders) {
		List<Integer> updatedInventory = new ArrayList<Integer>();
		
		for (int i = 0; i < store.getMaxTypes(); i++) {
			updatedInventory.add(stockInventory.get(i) + itemOrders.get(i));
		}
		
		return updatedInventory;
	}
}
