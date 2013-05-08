package nl.hu.jorisvergeer.practica;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class FifteenStackDescription extends DFAgentDescription{
	private static final long serialVersionUID = 1L;

	public FifteenStackDescription() {
		ServiceDescription serv = new ServiceDescription();
		serv.setName("15player");
		serv.setType("15player");
		addServices(serv);
	}
}
