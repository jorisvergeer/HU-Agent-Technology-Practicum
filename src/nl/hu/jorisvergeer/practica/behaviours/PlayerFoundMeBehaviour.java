package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.OurOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PlayerFoundMeBehaviour extends CyclicBehaviour {
	public interface PlayerFoundMeListener {
		public void onPlayerFoundMe(String name);
	}
	
	public static class FoundYouMessage extends ACLMessage{
		private static final long serialVersionUID = 1L;

		public FoundYouMessage(Agent agent) {
			super(INFORM);
			setOntology(OurOntology.FOUND_YOU);
			setContent(agent.getLocalName());
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private PlayerFoundMeListener receiver;
	
	public PlayerFoundMeBehaviour(Agent agent, PlayerFoundMeListener receiver) {
		super(agent);
		this.receiver = receiver;
	}

	@Override
	public void action() {
		ACLMessage msg = getAgent().receive(MessageTemplate
				.MatchOntology(OurOntology.FOUND_YOU));
		if (msg != null) {
			String name = msg.getContent();
			receiver.onPlayerFoundMe(name);
		}		
	}

}
