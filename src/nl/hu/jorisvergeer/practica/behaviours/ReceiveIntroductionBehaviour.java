package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.OurOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveIntroductionBehaviour extends CyclicBehaviour{
	public interface ReceiveIntroductionListener {
		void onNewPlayerIntroduced(String name);
	}
	
	private static final long serialVersionUID = 1L;
	
	private Agent agent;
	private ReceiveIntroductionListener receiver;
	
	public ReceiveIntroductionBehaviour(Agent agent, ReceiveIntroductionListener receiver){
		this.agent = agent;
		this.receiver = receiver;
	}

	@Override
	public void action() {
		ACLMessage msg = agent.receive(MessageTemplate.MatchOntology(OurOntology.INTRODUCE_NEW_PLAYER));
		if(msg != null){
			String name = msg.getContent();
			receiver.onNewPlayerIntroduced(name);
		}
	}
}
