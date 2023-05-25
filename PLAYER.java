//y line X column 

package cyPath;

import java.util.List;

public class Player extends Pion  {
	
	String name;
	Pion pion;
	int color; // color is not in the Pion class because the gamer is the one who choose the
				// color
	int barriersLeft;
    private OccupiedCoordinates occupiedCoordinates;
    

	
	
	// constructor
	public Player(Pion pion,String name, int color, int barriersLeft, int boardSize, int numberOfPlayer, int indexOfPlayer) {
		 
		super(pion.getPositionX(),pion.getPositionY());
		occupiedCoordinates = new OccupiedCoordinates();
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

	public void makeMove(Coordinate destination,List<Barrier> listBarriers) {
	    // Vérifier si la destination est adjacente
		
	    if (Adjacent(destination)) {
	    	
	        // Vérifier si la destination est déjà occupée
	    	
	    	if (!occupiedCoordinates.isCoordinateOccupied(destination))  {
	    	       
	            // Vérifier si le déplacement est horizontal ou vertical
	    	        
	            if (respectedMove(pion.getPositionX(), pion.getPositionY(), destination.getPositionX(), destination.getPositionY())) {
	                pion = new Pion(destination.getPositionX(), destination.getPositionY());
	                occupiedCoordinates.addCoordinate(destination);
	                
	            } else {
	                // recuperer les coordonnees du case au mileu du case de pion et la case de destination(apres saut)
	                int posmidX = (pion.getPositionX() + destination.getPositionX()) / 2;
	                int posmidY = (pion.getPositionY() + destination.getPositionY()) / 2;
	                
	                if (validCoordinate(posmidX, posmidY)) {
	                	Coordinate middleCoordinate=new Coordinate(posmidX, posmidY);
	                	
	                   //verifier que la destination est vide la case du milieu est pleine et pas de barriere entre case du milieu et case d arrivee+
	                	 boolean barrierExists = Barrier.isBarrier(pion.position, destination, listBarriers);
	                    if (!occupiedCoordinates.isCoordinateOccupied(destination)&&(!barrierExists)&&occupiedCoordinates.isCoordinateOccupied(middleCoordinate)) {
	                        pion = new Pion(destination.getPositionX(), destination.getPositionY());
	                        occupiedCoordinates.addCoordinate(destination); // after verifing the whole conditions we have to mark that this cell is now occupied 
	                    } else {
	                        throw new IllegalArgumentException("La case d'arrivée est déjà occupée ou case a sauter est vide .");
	                    }
	                } else {
	                    throw new IllegalArgumentException("coordonnee invalide");
	                }
	            }
	        } else {
	            throw new IllegalArgumentException("Case occupeé.");
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
	 
	
	public boolean respectedMove(int startX, int startY, int destX, int destY) {
	   // star x and y are the current cell
		// Vérifier si le mouvement respecte les règles (horizontal ou vertical d'une case)

	    // Vérifier si le mouvement est horizontal (même ligne) 3ndhom same y
	    if (startY == destY) {
	        int diffX = Math.abs(destX - startX);
	        return diffX == 1; // Le mouvement est d'une case horizontalement
	    }
	    // Vérifier si le mouvement est vertical (même colonne) same X
	    else if (startX == destX) {
	        int diffY = Math.abs(destY - startY);
	        return diffY == 1; // Le mouvement est d'une case verticalement
	    }

	    return false; // Le mouvement n'est ni horizontal ni vertical
	}
	 public boolean validCoordinate(int ligne, int colonne) {
	        return ligne >= 0 && ligne < 9 && colonne >= 0 && colonne < 9;
	 }
	 
	 
	
}




   
