package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.FifteenStack;
import nl.hu.jorisvergeer.practica.OurOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTurnBehaviour extends CyclicBehaviour {
	public interface ReceiveTurnListener {
		boolean onTurn(FifteenStack stack);
	}

	public static class Message extends ACLMessage {
		private static final long serialVersionUID = 1L;

		public Message(FifteenStack stack) {
			super(INFORM);
			setOntology(OurOntology.TURN);
			setContent(stack.toString());
		}
	}

	private static final long serialVersionUID = 1L;

	private Agent agent;
	private ReceiveTurnListener receiver;

	public ReceiveTurnBehaviour(Agent agent, ReceiveTurnListener receiver) {
		this.agent = agent;
		this.receiver = receiver;
	}

	@Override
	public void action() {
		ACLMessage msg = agent.receive(MessageTemplate
				.MatchOntology(OurOntology.TURN));
		if (msg != null) {
			if(!receiver.onTurn(FifteenStack.fromString(msg.getContent()))){
				getAgent().removeBehaviour(this);
			}
		}
	}
}
