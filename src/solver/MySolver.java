package solver;

import java.io.IOException;
import java.util.List;

import problem.ProblemSpec;

public class MySolver implements OrderingAgent {
	
	private ProblemSpec spec;
    private ValueIteration valueIteration;
    private RTDP RTDP;
    
	public MySolver(ProblemSpec spec) throws IOException {
		this.spec = spec;
        valueIteration = new ValueIteration(spec);
        RTDP = new RTDP(spec);
	}
	
	public void doOfflineComputation() {
		if (spec.getStore().getName().equals("tiny") ||
			spec.getStore().getName().equals("small") ||
			spec.getStore().getName().equals("medium")) {
			valueIteration.generatePolicies();
		    valueIteration.valueIteration();
		}
		
		for (Policy policy : valueIteration.getPolicies()) {
			System.out.println(policy.getState() + " : " + policy.getOptimalAction());
		}
		System.out.println();
//		for (Policy policy : valueIteration.getPolicies()) {
//			System.out.print(policy.getState() + " : ");
//			System.out.println(RTDP.selectAction(policy.getState()));
//		}	
	}
	
	public List<Integer> generateStockOrder(List<Integer> state, int numWeeksLeft) {	
		if (spec.getStore().getName().equals("tiny") ||
			spec.getStore().getName().equals("small") ||
			spec.getStore().getName().equals("medium")) {
//			return valueIteration.getOptimalPolicy(state);
			return RTDP.selectAction(state);
		} else {
			return RTDP.selectAction(state);
		}
	}
}
