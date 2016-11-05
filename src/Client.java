
public class Client extends Thread {
	
	private Site siteDepart;
	private Site siteArrivee;
	private int numClient;
	
	/**
	 * Construis l'objet avec un identifiant, un site de départ et un site d'arrivée
	 * @param numClient Identifiant du client
	 * @param depart Site de départ
	 * @param arrivee Site d'arrivée
	 */
	public Client(int numClient, Site depart, Site arrivee){
		this.numClient = numClient;
		this.siteDepart = depart;
		this.siteArrivee = arrivee;
	}
	
	/**
	 * 
	 */
	public void run(){
		try {
			int distanceSites = Math.abs(siteArrivee.getNumSite() - siteDepart.getNumSite()) * 100; //Simule la distance entre les sites de départ et d'arrivée
			this.emprunter();
			Thread.sleep(distanceSites); //Simule le déplacement du client jusqu'au site d'arrivée
			this.restituer();		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne le site de départ du client
	 * @return Site de départ
	 */
	public Site getDepart() {
		return siteDepart;
	}
	
	/**
	 * Définit le site de départ du client
	 * @param depart site de départ
	 */
	public void setDepart(Site depart) {
		this.siteDepart = depart;
	}
	
	/**
	 * Retourne le site d'arrivée du client
	 * @return Site d'arrivée
	 */
	public Site getArrivee() {
		return siteArrivee;
	}
	
	/**
	 * Définit le site d'arrivée du client
	 * @param arrivee Site d'arrivée
	 */
	public void setArrivee(Site arrivee) {
		this.siteArrivee = arrivee;
	}
	
	/**
	 * Emprunte un vélo au site de départ dès que possible 
	 * et parcours la distance entre ce site et celui de l'arrivée
	 * @throws InterruptedException
	 */
	public void emprunter() throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + "_Client sur le point d'emprunter sur le site " + siteDepart.getNumSite());
		siteDepart.deStocker();		
	}
	
	/**
	 * Restitue le vélo emprunté au site de départ, au site d'arrivée dès que possible
	 * @throws InterruptedException
	 */
	public void restituer() throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + "_Client sur le point de restituer au site " + siteArrivee.getNumSite());
		siteArrivee.stocker();
	}
	

}
