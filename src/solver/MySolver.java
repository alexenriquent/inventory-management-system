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
    private ValueIteration valueIteration;
    private RTDP RTDP;
    
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
        valueIteration = new ValueIteration(spec);
        RTDP = new RTDP(spec);
	}
	
	public void doOfflineComputation() {
		if (store.getName().equals("tiny") ||
			store.getName().equals("small") ||
			store.getName().equals("medium")) {
			valueIteration.generatePolicies();
		    valueIteration.valueIteration();
		}
			    
//	    for (Policy policy : valueIteration.getPolicies()) {
//	    	System.out.println("S: " + policy.getState());
//	    	System.out.println("A: " + policy.getActions());
//	    	System.out.println("A*: " + policy.getOptimalAction());
//	    	System.out.println("V(s): " + policy.getValue());
//	    	System.out.println();
//	    }
	}
	
	public List<Integer> generateStockOrder(List<Integer> state, int numWeeksLeft) {
		if (store.getName().equals("tiny") ||
			store.getName().equals("small") ||
			store.getName().equals("medium")) {
			System.out.println("Value Iteration");
			return valueIteration.getOptimalPolicy(state);
		} else {
			System.out.println("RTDP");
			return RTDP.selectAction(state);
		}
	}
}
