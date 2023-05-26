public class Board {
    private static final int TAILLE = 9;
    private static final int VIDE = 0;
    private static final int MUR = 1;
    private static final int PION = 2;
// Matrix that represents the game board
    private int[][] plateau; 

    public Board() {
        plateau = new int[TAILLE][TAILLE];
		// Initialise le plateau en le remplissant de cases vides
        initialiserPlateau(); 
    }

    public void initialiserPlateau() {
		// Fills all cells of the board with the value "vide"
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {

                plateau[i][j] = VIDE; 
            }
        }
    }
	 // Returns the size of the game board
    public int getTaille() {
        return TAILLE; 
    }
// Returns the matrix representing the game board
    public int[][] getPlateau() {
        return plateau; 
    }

	// Updates the matrix representing the game board with a new board
    public void setPlateau(int[][] plateau) {
        if (plateau.length != TAILLE || plateau[0].length != TAILLE) {
            throw new IllegalArgumentException("La taille du plateau est invalide.");
        }
        this.plateau = plateau; 
    }

 // Returns the value of the cell at the specified position
    public int getCellule(int ligne, int colonne) {
        if (positionValide(ligne, colonne)) {
            return plateau[ligne][colonne]; 
        } else {
            throw new IllegalArgumentException("Position de cellule invalide.");
        }
    }
// Modifies the value of the cell at the specified position
    public void setCellule(int ligne, int colonne, int valeur) {
        if (positionValide(ligne, colonne)) {
            plateau[ligne][colonne] = valeur; 
        } else {
            throw new IllegalArgumentException("Position de cellule invalide.");
        }
    }
   // Checks if the specified position is valid on the game board
    public boolean positionValide(int ligne, int colonne) {
        return ligne >= 0 && ligne < TAILLE && colonne >= 0 && colonne < TAILLE;

    }
// Checks if the specified cell is "vide"
    public boolean estCelluleVide(int ligne, int colonne) {
        return getCellule(ligne, colonne) == VIDE; 
    }
// Checks if the specified cell contains a "mur"
    public boolean estMur(int ligne, int colonne) {
        return getCellule(ligne, colonne) == MUR; 
    }
// Checks if the specified cell contains a pion
    public boolean estPion(int ligne, int colonne) {
        return getCellule(ligne, colonne) == PION; 

    }

    public boolean estPlateauVide() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (plateau[i][j] != VIDE) {
                    return false;
                }
            }
        }
       
