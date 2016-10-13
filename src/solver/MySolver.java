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
    private List<Policy> policies;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
        policies = new ArrayList<Policy>();
	}
	
	public void doOfflineComputation() {
	    generateStates();
	    
	    for (Policy policy : policies) {
	    	System.out.println(policy.getState());
	    }
	}
	
	public List<Integer> generateStockOrder(List<Integer> stockInventory, int numWeeksLeft) {
		RTDP RTDP = new RTDP(spec);
		List<Integer> itemOrders = RTDP.selectAction(stockInventory);
				
		return itemOrders;
	}
	
	public void generateStates() {
		List<Policy> policyList = new ArrayList<Policy>();
		
		for (int i = 0; i <= store.getCapacity(); i++) {
			policyList.add(new Policy(i));
		}
		
		for (int i = 1; i < store.getMaxTypes(); i++) {
			List<Policy> tempPolicyList = new ArrayList<Policy>();
			for (Policy policy : policyList) {
				for (int j = 0; j <= store.getCapacity() - policy.getTotalItems(); j++) {
					tempPolicyList.add(new Policy(policy, j));
				}
			}
			policyList.clear();
			policyList.addAll(tempPolicyList);
			tempPolicyList.clear();
		}
		
		policies.addAll(policyList);
	}
}
