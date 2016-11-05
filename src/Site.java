class Site {

	/* Constantes associées au site */
	static final int stockInit = 6;
	static final int stockMax = 10;
	static final int borneSup = 8;
	static final int borneInf = 2;
	
	private int numSite;
	private int stock;
	
	public Site(int numSite){
		this.numSite = numSite;
		this.stock = stockInit;
	}
	
	/**
	 * Définit le stock de vélo
	 * @param stock Nouveau stock
	 */
	public void setStock(int stock){
		this.stock = stock;
	}
	
	/**
	 * Retourne le stock de velo disponible
	 * @return Stock de vélo
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * Retourne le numero du site	
	 * @return
	 */
	public int getNumSite() {
		return numSite;
	}
	
	/**
	 * Réinitialise le stock de vélo du site
	 */
	public void reinitialiserStock(){
		this.stock = stockInit;
        System.out.println(Thread.currentThread().getName() + "_Camion: Après action sur le site " + numSite + ", il contient " + stock + " velo(s).");
	}
	
    /**
     * Affiche l'etat du stock du site
     * @param acteur Indique le la classe solicitant l'affichage
     */
    public void afficher(String acteur) {
        System.out.println(Thread.currentThread().getName() + "_" + acteur + ": Après action sur le site " + numSite + ", il contient " + stock + " velo(s).");
    }
	
	/**
	 * Rétire un vélo à la fois du stock dès que cela est possible
	 * @throws InterruptedException
	 */
	public synchronized void deStocker() throws InterruptedException{
		
		while(stock == 0){
			this.wait();
		}
		this.stock--;
		this.notifyAll();
		this.afficher("Client");//Affiche le niveau de stock du site
	}
	
	/**
	 * Ajoute un vélo à la fois au stock dès que cela est possible
	 * @throws InterruptedException
	 */
	public synchronized void stocker() throws InterruptedException{
		
		while(stock >= stockMax){
			this.wait();
		}
		this.stock++;
		this.notifyAll();
		this.afficher("Client");//Affiche le niveau de stock du site
	}
	
	/**
	 * Equilibre le stock de velo du site en fonction des bornes (Sup et Inf) et du stock de velo du camion
	 * @param camion
	 */
	public synchronized void equilibrage(CamionEquilibrage camion){
		if(this.stock > borneSup){
			System.out.println("Cas de surplus");
			int surplus = this.stock - Site.stockInit;
			camion.stocker(surplus); //Recupère le surplus sur le camion
			this.reinitialiserStock(); //Reinitialise le stock de vélo du site
		}
		else if(this.stock < borneInf){
			System.out.println("Cas de manque");
			int manque = Site.stockInit - this.stock;
			if(this.stock > manque){
				this.reinitialiserStock();; //Reinitialise le stock de vélo du site
				camion.destocker(manque); //Met à jour le stock du camion
			}
			else{
				this.stock = this.stock + camion.getStock();
				camion.vider(); //Met à jour le stock du camion
			}
		}
		this.afficher("Camion");//Affiche le niveau de stock du site
        this.notifyAll();
	}
	
}