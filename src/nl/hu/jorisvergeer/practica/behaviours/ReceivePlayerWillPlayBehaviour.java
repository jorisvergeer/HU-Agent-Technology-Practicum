package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.OurOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceivePlayerWillPlayBehaviour extends CyclicBehaviour {
	public interface ReceivePlayerWillPlayListener {
		void onPlayerWillPlay(String name);
	}

	public static class Message extends ACLMessage {
		private static final long serialVersionUID = 1L;

		public Message(Agent agent) {
			super(INFORM);
			setOntology(OurOntology.I_WILL_PLAY);
			setContent(agent.getLocalName());
		}
	}

	private static final long serialVersionUID = 1L;

	private Agent agent;
	private ReceivePlayerWillPlayListener receiver;

	public ReceivePlayerWillPlayBehaviour(Agent agent, ReceivePlayerWillPlayListener receiver) {
		this.agent = agent;
		this.receiver = receiver;
	}

	@Override
	public void action() {
		ACLMessage msg = agent.receive(MessageTemplate
				.MatchOntology(OurOntology.I_WILL_PLAY));
		if (msg != null) {
			receiver.onPlayerWillPlay(msg.getContent());
		}
	}
}
