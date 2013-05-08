package nl.hu.jorisvergeer.practica.behaviours;

import nl.hu.jorisvergeer.practica.FifteenStackDescription;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class FindPlayerBehaviour extends TickerBehaviour {
	public interface PlayerFoundListener {
		public void onPlayerFound(String name);
	}

	private static final long serialVersionUID = 1L;
	private PlayerFoundListener receiver;

	public FindPlayerBehaviour(Agent agent, PlayerFoundListener receiver) {
		super(agent, 5000);
		this.receiver = receiver;
	}

	@Override
	protected void onTick() {
		try {
			DFAgentDescription[] agents = DFService.search(getAgent(),
					new FifteenStackDescription());
			for (DFAgentDescription agent : agents) {
				if (!agent.getName().getLocalName()
						.equals(getAgent().getLocalName())) {
					receiver.onPlayerFound(agent.getName().getLocalName());
					getAgent().removeBehaviour(this);
					break;
				}
			}
		} catch (FIPAException e) {
			e.printStackTrace();
			getAgent().doDelete();
		}
	}

}
