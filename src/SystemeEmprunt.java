import java.util.Random;

/* Importation de la classe Lecture. */


class SystemeEmprunt {
	
	
	/* Constantes (final indique que la valeur ne peut pas changer) */
	static final int nbSites = 5;
	static final int maxClients = 20;
	
	/* Ces attributs sont statiques */	
	private Site[] sites = new Site[nbSites];
	private Client[] clients = new Client[maxClients];
	private int nbClients = 0;
	
	/**
	 * Crée un seul client à la fois (à la limite aucun).
	 * Elle renvoie vrai si et seulement si un client a été créé.
	 * Elle renvoie faux dès que la création des clients est terminée.
	 * @return
	 */
	private boolean nouveauClient() {
		Random rd = new Random();
		Site depart;
		Site arrivee;
	
		if(nbClients == maxClients) {
			System.out.println("Le nombre maximum de clients est atteint.");
			return false;
		}
		
		depart = sites[rd.nextInt(nbSites)];
		arrivee = sites[rd.nextInt(nbSites)];
	
		clients[nbClients] = new Client(nbClients, depart, arrivee);
		nbClients++;
	
		return true;	
	}
		
	/**
	 * Constructeur
	 */
	SystemeEmprunt() {
	
		int i;
		
		/* Instanciation des sites */
		for(i = 0; i < nbSites; i++)
			sites[i] = new Site(i);
				
		/* Instanciation des clients */
		for(i = 0; i < maxClients; i++){
			this.nouveauClient();
			clients[i].start();
		}
		
		/*Instanciation du camion*/
		CamionEquilibrage camion = new CamionEquilibrage(sites, 10);
		camion.start();
		
	}
	
	/**
	 * Point d'entrée du programme
	 * @param args
	 */
	public static void main(String[] args) {
	
		new SystemeEmprunt();
	
	}
	

} // class SystemeEmprunt
