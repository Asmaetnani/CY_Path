package Abstraction;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import Control.GameController;
import Control.GameTurn;
import Abstraction.*;
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Player> players;
	Board board;
	int currentPlayer;
	int[] gameFinished = new int[2]; // first value equal 0 if game not finished, 1 if game finished and second value
										// is the number of the player who won, or -1 if game not finished
	List<Barrier> listBarriers;

	

	public List<Barrier> getListBarriers() {
		return listBarriers;
	}

	public void setListBarriers(List<Barrier> listBarriers) {
		this.listBarriers = listBarriers;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayersPositions(List<Player> players) {
		this.players= players;
	}

	public int[] getGameFinished() {
		return gameFinished;
	}

	public void setGameFinished(int[] gameFinished) {
		this.gameFinished = gameFinished;
	}



	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	
	public Game(int nbPlayers, int currentPlayer, int[] gameFinished, List<Coordinate> playersPositions,
			List<Barrier> listBarriers) {
		super();
		this.currentPlayer = currentPlayer;
		this.gameFinished = gameFinished;
		this.players = players;
		this.listBarriers = listBarriers;
	}

	public void startGame(int PlayersCount) {

		board.initialiserPlateau();
		initializePlayers(PlayersCount);
		List<Barrier> listBarriers = new ArrayList<Barrier>();
		// select random first player
		setCurrentPlayer((int) (Math.random() * (this.players.size())));
		int[] a = { 0, -1 };
		setGameFinished(a);

	}
	
	

	private void initializePlayers(int nbPlayers2) {
		// TODO Auto-generated method stub
		
	}

	void endGame(Player player, int nbPlayers) {
		// Determine the winner
		if (player.getIndexOfPlayer() == 0 && player.pion.getPositionY() == 8) {
			this.gameFinished[0] = 1;
			this.gameFinished[1] = 0;
		} else if (player.getIndexOfPlayer() == 1 && player.pion.getPositionX() == 0 && nbPlayers == 4) {
			this.gameFinished[0] = 1;
			this.gameFinished[1] = 1;
		} else if (player.getIndexOfPlayer() == 1 && player.pion.getPositionY() == 0 && nbPlayers == 2) {
			this.gameFinished[0] = 1;
			this.gameFinished[1] = 1;
		} else if (player.getIndexOfPlayer() == 2 && player.pion.getPositionY() == 0) {
			this.gameFinished[0] = 1;
			this.gameFinished[1] = 2;
		} else if (player.getIndexOfPlayer() == 3 && player.pion.getPositionX() == 8) {
			this.gameFinished[0] = 1;
			this.gameFinished[1] = 3;
		}

	}
	
	void switchPlayer() {
		// Switch to the next player
		this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
	}
	
	
	public void placeBarrier(Barrier b) {
		List<Coordinate> playersPositions = new ArrayList<Coordinate>();
		for (Player p : players) {
			playersPositions.add(p.pion.position);
		}
		if (b.isBarrierPlacementValid(playersPositions, listBarriers)) {
			listBarriers.add(b);
			switchPlayer();
			// GameController.displayBarrier(this);
		}
		else
			//Control.GameController.DisplayInvalidActionMessage();
		startTurn();
	}
	
	public void movePawn(int direction) {
		// 0 up 1 right 2 down 3 left

		Coordinate pos = players.get(getCurrentPlayer()).position;
		switch(direction) {
		case 0:
			pos.setPositionY(pos.getPositionY()-1);
			break;
		case 1:
			pos.setPositionX(pos.getPositionX()+1);
			break;
		case 2:
			pos.setPositionY(pos.getPositionY()+1);
			break;
		case 3:
			pos.setPositionX(pos.getPositionX()-1);
			break;
			default :
				System.err.println("wrong movement");
				startTurn();
				break;
		};
		
		players.get(getCurrentPlayer()).makeMove(pos, listBarriers);	
		switchPlayer();
		startTurn();
		}
		

	public void startTurn() {
		GameTurn gameTurn = new GameTurn(this);
		gameTurn.show();

	}


}
