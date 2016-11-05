
public class Client extends Thread {
	
	private Site siteDepart;
	private Site siteArrivee;
	private int numClient;
	
	/**
	 * Construis l'objet avec un identifiant, un site de d�part et un site d'arriv�e
	 * @param numClient Identifiant du client
	 * @param depart Site de d�part
	 * @param arrivee Site d'arriv�e
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
			int distanceSites = Math.abs(siteArrivee.getNumSite() - siteDepart.getNumSite()) * 100; //Simule la distance entre les sites de d�part et d'arriv�e
			this.emprunter();
			Thread.sleep(distanceSites); //Simule le d�placement du client jusqu'au site d'arriv�e
			this.restituer();		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne le site de d�part du client
	 * @return Site de d�part
	 */
	public Site getDepart() {
		return siteDepart;
	}
	
	/**
	 * D�finit le site de d�part du client
	 * @param depart site de d�part
	 */
	public void setDepart(Site depart) {
		this.siteDepart = depart;
	}
	
	/**
	 * Retourne le site d'arriv�e du client
	 * @return Site d'arriv�e
	 */
	public Site getArrivee() {
		return siteArrivee;
	}
	
	/**
	 * D�finit le site d'arriv�e du client
	 * @param arrivee Site d'arriv�e
	 */
	public void setArrivee(Site arrivee) {
		this.siteArrivee = arrivee;
	}
	
	/**
	 * Emprunte un v�lo au site de d�part d�s que possible 
	 * et parcours la distance entre ce site et celui de l'arriv�e
	 * @throws InterruptedException
	 */
	public void emprunter() throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + "_Client sur le point d'emprunter sur le site " + siteDepart.getNumSite());
		siteDepart.deStocker();		
	}
	
	/**
	 * Restitue le v�lo emprunt� au site de d�part, au site d'arriv�e d�s que possible
	 * @throws InterruptedException
	 */
	public void restituer() throws InterruptedException{
		System.out.println(Thread.currentThread().getName() + "_Client sur le point de restituer au site " + siteArrivee.getNumSite());
		siteArrivee.stocker();
	}
	

}
