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
		RTDP RTDP = new RTDP(spec);
		List<Integer> itemOrders = RTDP.selectAction(stockInventory);
				
		return itemOrders;
	}
	
//	public void generateStates() {
//		List<List<Integer>> states = new ArrayList<List<Integer>>();
//		
//		for (int i = 0; i < store.getMaxTypes(); i++) {
//			
//		}
//	}
}
