
public class CamionEquilibrage extends Thread {
	
	private int stock; //Stock de vélo du camion
	private Site[] sites;
	
	/**
	 * Constructeur
	 * Définit ce thread comme étant un deamon thread afin de ne l'arreter que lorsque 
	 * tous les thread autre que les deamons threads sont arrêtés
	 * Définit l'ensemble des sites à parcourir
	 * @param sites Ensemble des sites à parcourir
	 */
	public CamionEquilibrage(Site[] sites){
		this.setDaemon(true);
		this.sites = sites;
	}
	
	/**
	 * 
	 * @param sites
	 */
	public void run(){
		try {
			this.parcourir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Se déplace de site en site en collectant le surplus de vélo pour chaque site
	 * @throws InterruptedException 
	 */
	public void parcourir() throws InterruptedException{		
		for(int i = 0; i < sites.length; i = (i+1) % sites.length) {
			int siteSuivant = (i+1) % sites.length;
			long distanceSites = (long) (Math.abs(sites[siteSuivant].getNumSite() - sites[i].getNumSite()) * 150); //Calcul de la distance entre le site courant et le suivant
			Site site = sites[i];
			System.out.println("Camion au niveau du site "+ site.getNumSite());			
			site.equilibrage(this);; //Equilibre le stock de vélo du site (et du camion)

			try {
				sleep(distanceSites); //Simule le déplacement entre le site courant et le suivant
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} //End for		
	}
	
	/**
	 * @return Le stock du camion
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * @param stock Le nouveau stock
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Ajoute un certain nombre de velo au stock du camion
	 * @param nbVelo Nombre de velo à ajouter
	 */
	public void stocker(int nbVelo) {
		this.stock += nbVelo;		
	}
	
	/**
	 * Ratire un certain nombre de velo au stock du camion
	 * @param nbVelo Nombre de vélo à retirer
	 */
	public void destocker(int nbVelo) {
		this.stock -= nbVelo;
	}
	
	/**
	 * Vide le stock du camion
	 */
	public void vider() {
		this.stock = 0;
	}
	
	
}
