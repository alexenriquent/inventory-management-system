package mdp;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	private List<Integer> state;
	
	public State(List<Integer> state) {
		this.state = new ArrayList<Integer>(state);
	}
	
	public List<Integer> getState() {
		return state;
	}
	
	public void setState(List<Integer> state) {
		this.state = new ArrayList<>(state);
	}
}
