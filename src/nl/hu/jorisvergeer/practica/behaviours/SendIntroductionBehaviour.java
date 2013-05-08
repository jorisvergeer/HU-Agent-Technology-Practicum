package nl.hu.jorisvergeer.practica.behaviours;

import java.util.Set;

import nl.hu.jorisvergeer.practica.OurOntology;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendIntroductionBehaviour extends OneShotBehaviour {
	public class IntroductionMessage extends ACLMessage {
		private static final long serialVersionUID = 1L;

		public IntroductionMessage(Agent agent) {
			super(INFORM);
			setOntology(OurOntology.INTRODUCE_NEW_PLAYER);
			setContent(agent.getLocalName());
		}
	}

	private static final long serialVersionUID = 1L;

	Set<String> players;

	public SendIntroductionBehaviour(Agent agent, Set<String> players) {
		super(agent);
		this.players = players;
	}

	@Override
	public void action() {
		ACLMessage msg = new IntroductionMessage(getAgent());
		for (String player : players) {
			msg.addReceiver(new AID(player, AID.ISLOCALNAME));
		}
		getAgent().send(msg);
	}

}
