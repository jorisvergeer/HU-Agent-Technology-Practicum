package nl.hu.jorisvergeer.practica;

import jade.core.Agent;

public class LoggerAgent extends Agent {
	private static final long serialVersionUID = 1L;
	
	public LoggerAgent() {
		
	}
	
	public void log(String msg){
		System.out.println(getLocalName() + " : " + msg);
	}

}
