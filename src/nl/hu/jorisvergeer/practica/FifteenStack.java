package nl.hu.jorisvergeer.practica;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONValue;

public class FifteenStack {
	private List<Long> stacks = Arrays.asList(3L, 5L, 7L);

	public long look(int stack) {
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
		if (stacks.get(0) == 0 && stacks.get(1) == 0 && stacks.get(2) == 0) {
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
		res.stacks = (List<Long>) JSONValue.parse(string);
		return res;
	}
}
