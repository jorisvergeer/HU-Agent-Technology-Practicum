package nl.hu.jorisvergeer.practica.behaviours;

import java.util.Set;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendHelloBehaviour extends TickerBehaviour {
	private static final long serialVersionUID = 1L;
	
	Set<String> players;

	public SendHelloBehaviour(Agent a, long period, Set<String> players) {
		super(a, period);
		this.players = players;
	}

	@Override
	protected void onTick() {
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		for (String player : players) {
			msg.addReceiver(new AID(player, AID.ISLOCALNAME));
		}
		msg.setPerformative(ACLMessage.PROPOSE);
		getAgent().send(msg);
	}

}