package nl.hu.jorisvergeer.practica;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class GameAgent extends Agent {
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				System.out.println("I'm still alive !");
			}
		});
	}
}
