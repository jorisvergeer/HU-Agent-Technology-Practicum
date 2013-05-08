package nl.hu.jorisvergeer.practica;

import java.util.Random;

import nl.hu.jorisvergeer.practica.behaviours.FindPlayerBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.FindPlayerBehaviour.PlayerFoundListener;
import nl.hu.jorisvergeer.practica.behaviours.PlayerFoundMeBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.ReceiveLeftBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.ReceivePlayerWillPlayBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.PlayerFoundMeBehaviour.PlayerFoundMeListener;
import nl.hu.jorisvergeer.practica.behaviours.ReceivePlayerWillPlayBehaviour.ReceivePlayerWillPlayListener;
import nl.hu.jorisvergeer.practica.behaviours.ReceivePlayerWontPlayBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.ReceivePlayerWontPlayBehaviour.Message;
import nl.hu.jorisvergeer.practica.behaviours.ReceivePlayerWontPlayBehaviour.ReceivePlayerWontPlayListener;
import nl.hu.jorisvergeer.practica.behaviours.ReceiveTurnBehaviour;
import nl.hu.jorisvergeer.practica.behaviours.ReceiveTurnBehaviour.ReceiveTurnListener;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class FifteenAgent extends LoggerAgent implements PlayerFoundListener,
		PlayerFoundMeListener, ReceivePlayerWontPlayListener,
		ReceivePlayerWillPlayListener, ReceiveTurnListener {
	private static final long serialVersionUID = 1L;

	enum State {
		WAITING_FOR_PLAYER, WAITING_FOR_CONFIRM, PLAYING,
	}

	private State state;
	private String player;
	private FindPlayerBehaviour findPlayer;

	@Override
	protected void setup() {
		state = State.WAITING_FOR_PLAYER;

		addBehaviour(findPlayer = new FindPlayerBehaviour(this, this));
		addBehaviour(new PlayerFoundMeBehaviour(this, this));

		try {
			DFService.register(this, new FifteenStackDescription());
		} catch (FIPAException e) {
			e.printStackTrace();
			doDelete();
		}

		log("I'm online");
	}

	@Override
	protected void takeDown() {
		if (player != null) {
			ACLMessage msg = new ReceiveLeftBehaviour.PlayerLeftMessage(this);
			msg.addReceiver(new AID(player, AID.ISLOCALNAME));
			send(msg);
		}
		log("I'm offline");
	}

	@Override
	public void onPlayerFound(String name) {
		if (state == State.WAITING_FOR_PLAYER) {
			try {
				log("Found player " + name + ", waiting for it to confirm");
				ACLMessage msg = new PlayerFoundMeBehaviour.FoundYouMessage(
						this);
				msg.addReceiver(new AID(name, AID.ISLOCALNAME));
				send(msg);

				DFService.deregister(this);

				addBehaviour(new ReceivePlayerWontPlayBehaviour(this, this));
				addBehaviour(new ReceivePlayerWillPlayBehaviour(this, this));

				state = State.WAITING_FOR_CONFIRM;
				player = name;
			} catch (FIPAException e) {
				e.printStackTrace();
				doDelete();
			}
		} else {
			log("Found player " + name
					+ " but i don't know why because I am not searching");
		}
	}

	@Override
	public void onPlayerFoundMe(String name) {
		if (state == State.WAITING_FOR_PLAYER) {
			log("Player " + name + " found me and i will play");
			state = State.PLAYING;
			removeBehaviour(findPlayer);
			addBehaviour(new ReceiveTurnBehaviour(this, this));
			ACLMessage msg = new ReceivePlayerWillPlayBehaviour.Message(this);
			msg.addReceiver(new AID(name, AID.ISLOCALNAME));
			send(msg);
			player = name;
			try {
				DFService.deregister(this);
			} catch (FIPAException e) {
				e.printStackTrace();
				doDelete();
			}
		} else {
			log("Player " + name + " found me but i won't play");
			ACLMessage msg = new ReceivePlayerWontPlayBehaviour.Message(this);
			msg.addReceiver(new AID(name, AID.ISLOCALNAME));
			send(msg);
		}
	}

	@Override
	public void onPlayerWontPlay(String name) {
		if (state == State.WAITING_FOR_CONFIRM && player.equals(name)) {
			log("Player " + player + " won't play with me");
			state = State.WAITING_FOR_PLAYER;
			player = null;
			addBehaviour(findPlayer = new FindPlayerBehaviour(this, this));
			try {
				DFService.register(this, new FifteenStackDescription());
			} catch (FIPAException e) {
				e.printStackTrace();
				doDelete();
			}
		} else {
			log("Some player does not want play with me. I don't care");
		}
	}

	@Override
	public void onPlayerWillPlay(String name) {
		if (state == State.WAITING_FOR_CONFIRM && player.equals(name)) {
			log("Start playing with " + player + "");
			state = State.PLAYING;
			addBehaviour(new ReceiveTurnBehaviour(this, this));
			
			ACLMessage msg = new ReceiveTurnBehaviour.Message(new FifteenStack());
			msg.addReceiver(new AID(name, AID.ISLOCALNAME));
			send(msg);
		}
	}

	@Override
	public boolean onTurn(FifteenStack stack) {
		if(stack.gameOver()){
			log("I won!!!");
			state = State.WAITING_FOR_PLAYER;
			player = null;
			addBehaviour(findPlayer = new FindPlayerBehaviour(this, this));
			try {
				DFService.register(this, new FifteenStackDescription());
			} catch (FIPAException e) {
				e.printStackTrace();
				doDelete();
			}
			log("Waiting for a new one");
			return false;
		} else {
			int myStack = -1;
			while(myStack < 0 || stack.look(myStack) == 0){
				myStack = (int) (Math.random() * 3.0);
			}
			
			int myValue = (int) (Math.random() * (stack.look(myStack) < 3 ? (double)stack.look(myStack) : 3.0) + 1);
			if(myValue > 3)
				myValue = 2;
			
			log("Get stack " + stack.toString() + " and take " + myValue + " from stack " + myStack);
			stack.take(myStack, myValue);
			log("Stack is now " + stack.toString());
			
			ACLMessage msg = new ReceiveTurnBehaviour.Message(stack);
			msg.addReceiver(new AID(player, AID.ISLOCALNAME));
			send(msg);
			
			if(stack.gameOver()){
				log("I lost!!!");
				state = State.WAITING_FOR_PLAYER;
				player = null;
				addBehaviour(findPlayer = new FindPlayerBehaviour(this, this));
				try {
					DFService.register(this, new FifteenStackDescription());
				} catch (FIPAException e) {
					e.printStackTrace();
					doDelete();
				}
				log("Waiting for a new one");
				
				return false;
			}
			return true;
		}
	}
}
