package nl.hu.jorisvergeer.practica;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class GameAgent extends Agent {
	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				System.out.println("I'm still alive !");
			}
		});
	}
}
