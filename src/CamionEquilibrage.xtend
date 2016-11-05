class CamionEquilibrage extends Thread {
	int stock

	// Stock de vélo du camion
	/** 
	 * Constructeur
	 * Définit ce thread comme étant un deamon thread afin de ne l'arreter que lorsque 
	 * tous les thread autre que les deamons threads sont arrêtés
	 */
	new() {
		this.setDaemon(true)
	}

	/** 
	 * @param sites
	 */
	def void run(Site[] sites) {
		this.parcourir(sites)
	}

	/** 
	 * Se déplace de site en site en collectant le surplus de vélo pour chaque site
	 * @param sites Ensemble des sites à parcourir
	 */
	def void parcourir(Site[] sites) {
		for (var int i = 0; i < sites.length; i = (i++) % sites.length) {
			var long distanceSites = ((Math::abs({
				val _rdIndx_sites = i + 1
				sites.get(_rdIndx_sites)
			}.getNumSite() - {
				val _rdIndx_sites = i
				sites.get(_rdIndx_sites)
			}.getNumSite()) * 100) as long)
			// Calcul de la distance entre le site courant et le suivant
			var Site site = {
				val _rdIndx_sites = i
				sites.get(_rdIndx_sites)
			}
			var int stockSite = site.getStock()
			System::out.println('''Camion au niveau du site «site.getNumSite()»'''.toString)
			synchronized (site) {
				if (stockSite > Site::borneSup) {
					var int surplus = stockSite - Site::stockInit
					this.stock = +surplus
					// Recupère le surplus sur le camion
					site.reinitialiserStock() // Reinitialise le stock de vélo du site
				} else if (stockSite < Site::borneInf) {
					var int manque = Site::stockInit - stockSite
					if (this.stock > manque) {
						site.reinitialiserStock();
						// Reinitialise le stock de vélo du site
						this.stock = -manque // Met à jour le stock du camion
					} else {
						var int aDeposer = site.getStock() + this.stock
						site.setStock(aDeposer)
						// Complète le stock avec celui du camion
						this.stock = 0 // Met à jour le stock du camion
					}
				}
			}
			try {
				site.notifyAll()
				sleep(distanceSites) // Simule le déplacement entre le site courant et le suivant
			} catch (InterruptedException e) {
				e.printStackTrace()
			}

		} // End for		
	}
}
