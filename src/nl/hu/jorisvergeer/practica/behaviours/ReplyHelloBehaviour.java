package nl.hu.jorisvergeer.practica.behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReplyHelloBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	
	Agent agent;
	
	public ReplyHelloBehaviour(Agent agent) {
		this.agent = agent;
	}

	@Override
	public void action() {
		ACLMessage msg = agent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
		if (msg != null) {
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setContent("Hello");
			agent.send(reply);
		}
	}

}
