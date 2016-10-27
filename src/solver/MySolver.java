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
	}
	
	public List<Integer> generateStockOrder(List<Integer> state, int numWeeksLeft) {	
		if (spec.getStore().getName().equals("tiny")) {
			return RTDP.selectAction(state, 300);
		} else if (spec.getStore().getName().equals("small")) {
			return RTDP.selectAction(state, 500);
		} else if (spec.getStore().getName().equals("medium")) {
			return RTDP.selectAction(state, 2000);
		} else if (spec.getStore().getName().equals("large")) {
			return RTDP.selectLargeAction(state, 5000);
		} else {
			return RTDP.selectLargeAction(state, 10000);
		}
	}
}
