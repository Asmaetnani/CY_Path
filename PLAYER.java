public class Player extends Pion  {
	String name;
	Pion pion;
	int color; // color is not in the Pion class because the gamer is the one who choose the
				// color
	int barriersLeft;

	// constructor
	public Player(Pion pion,String name, int color, int barriersLeft, int boardSize, int numberOfPlayer, int indexOfPlayer) {
	    super(pion.getPositionX(),pion.getPositionY());
		this.name = name;
		this.color = color; // when we will present the game we will indicate that each color is presented
							// by a number
		this.barriersLeft = barriersLeft;
		// Fixing the starting position based on the boardsize and the player index
		int initialPosition = (int) boardSize / 2;

		if (numberOfPlayer == 2) {
			// if there are 2 players their Pions will be placed on opposites sides.
			if (indexOfPlayer == 0) {
				this.pion = new Pion(initialPosition, 0);
				// X= initialPos and Y=ligne 0
			} else {
				// 2nd player
				this.pion = new Pion(initialPosition, boardSize - 1);
				// X=initialPos Y=last line

			}
		} else if (numberOfPlayer == 4) {
			int middlepos = (int) boardSize / 2;
			switch (indexOfPlayer) {
			case 0:
				this.pion = new Pion(0, middlepos);
				// line0 in the middle
				break;
			case 1:
				this.pion = new Pion(boardSize - 1, middlepos);
				// last line in the middle
				break;
			case 2:
				this.pion = new Pion(middlepos, 0);
				// column0 in the middle
				break;
			case 3:
				this.pion = new Pion(middlepos, boardSize - 1);
				// last column in the middle
				break;
			default:
				throw new IllegaLArgumentException("numner of players invalid" + indexOfPlayer);
			}

		}
	}

	// getters and setters
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getBarriere() {
		return this.barriersLeft;
	}

	public void setBarriere(int barriersLef) {
		this.barriersLeft = barriersLef;
	}

	public Pion getPion() {
		return this.pion;
	}

	public void setPion(Pion pion) {
		this.pion = pion;
	}

	// methods to control the game

	public void makeMove(Coordinate destination) {
	    // Vérifier si la destination est adjacente
	    if (Adjacent(destination)) {
	        // Vérifier si la destination est déjà occupée
	        if (!occupied(destination)) {
	            // Vérifier si le déplacement est horizontal ou vertical
	            if (respectedMove(pion.getPositionX(), pion.getPositionY(), destination.getPositionX(), destination.getPositionY())) {
	                pion = new Pion(destination.getPositionX(), destination.getPositionY());
	            } else {
	                // Vérifier si les coordonnées sont dans les limites du plateau
	                int posmidX = (pion.getPositionX() + destination.getPositionX()) / 2;
	                int posmidY = (pion.getPositionY() + destination.getPositionY()) / 2;
	                if (validCoordinate(posmidX, posmidY)) {
	                    // Vérifier si la case à sauter est vide
	                    if (!occupied(destination.getPositionX(), destination.getPositionY())) {
	                        pion = new Pion(destination.getPositionX(), destination.getPositionY());
	                    } else {
	                        throw new IllegalArgumentException("La case d'arrivée est déjà occupée.");
	                    }
	                } else {
	                    throw new IllegalArgumentException("La case à sauter est vide.");
	                }
	            }
	        } else {
	            throw new IllegalArgumentException("Coordonnées en dehors du plateau de jeu.");
	        }
	    } else {
	        throw new IllegalArgumentException("Coordonnées non adjacentes.");
	    }
	}
	public boolean Adjacent(Coordinate obj) {
		   int xdiff=Math.abs(this.position.getPositionX()-obj.getPositionX());
		   int ydiff=Math.abs(this.position.getPositionY()-obj.getPositionY());
		     return (xdiff==1 && ydiff==0)||(xdiff==0 && ydiff==1);
	    }
	 //need class Boar
	    public boolean occupied(Board board) {
	    	return board.occupied(this);
}
	    }




   
