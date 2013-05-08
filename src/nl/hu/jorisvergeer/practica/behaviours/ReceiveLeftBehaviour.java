package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.OurOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveLeftBehaviour extends CyclicBehaviour {
	public interface ReceiveLeaveListener {
		void onPlayerLeft(String name);
	}

	public static class PlayerLeftMessage extends ACLMessage {
		private static final long serialVersionUID = 1L;

		public PlayerLeftMessage(Agent agent) {
			super(INFORM);
			setOntology(OurOntology.PLAYER_LEAVE);
			setContent(agent.getLocalName());
		}
	}

	private static final long serialVersionUID = 1L;

	private Agent agent;
	private ReceiveLeaveListener receiver;

	public ReceiveLeftBehaviour(Agent agent, ReceiveLeaveListener receiver) {
		this.agent = agent;
		this.receiver = receiver;
	}

	@Override
	public void action() {
		ACLMessage msg = agent.receive(MessageTemplate
				.MatchOntology(OurOntology.PLAYER_LEAVE));
		if (msg != null) {
			String name = msg.getContent();
			receiver.onPlayerLeft(name);
		}
	}
}
