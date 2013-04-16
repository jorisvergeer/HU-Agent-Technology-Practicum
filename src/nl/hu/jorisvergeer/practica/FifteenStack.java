package nl.hu.jorisvergeer.practica;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONValue;

public class FifteenStack {
	private List<Integer> stacks = Arrays.asList(3, 5, 7);

	public int look(int stack) {
		try {
			return stacks.get(stack);
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}

	public void take(int stack, int amount) {
		try {
			stacks.set(stack, stacks.get(stack) - amount);
		} catch (Exception e) {
		}
	}

	public boolean gameOver() {
		int total = stacks.get(0) + stacks.get(1) + stacks.get(2);
		if (total <= 0) {
			return true;
		}
		return false;
	}

	public String toString() {
		StringWriter out = new StringWriter();
		try {
			JSONValue.writeJSONString(stacks, out);
		} catch (IOException e) {
		}
		String jsonText = out.toString();
		return jsonText;
	}

	public static FifteenStack fromString(String string) {
		FifteenStack res = new FifteenStack();
		res.stacks = (List<Integer>) JSONValue.parse(string);
		return res;
	}
}
