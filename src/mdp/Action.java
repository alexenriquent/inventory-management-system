package mdp;

import java.util.ArrayList;
import java.util.List;

public class Action {

	private List<Integer> action;
	
	public Action(List<Integer> action) {
		this.action = new ArrayList<Integer>(action);
	}
	
	public List<Integer> getAction() {
		return action;
	}
	
	public void setAction(List<Integer> action) {
		this.action = new ArrayList<Integer>(action);
	}
}
