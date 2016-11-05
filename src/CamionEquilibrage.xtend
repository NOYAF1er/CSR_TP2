class CamionEquilibrage extends Thread {
	int stock

	// Stock de v�lo du camion
	/** 
	 * Constructeur
	 * D�finit ce thread comme �tant un deamon thread afin de ne l'arreter que lorsque 
	 * tous les thread autre que les deamons threads sont arr�t�s
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
	 * Se d�place de site en site en collectant le surplus de v�lo pour chaque site
	 * @param sites Ensemble des sites � parcourir
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
			System::out.println('''Camion au niveau du site �site.getNumSite()�'''.toString)
			synchronized (site) {
				if (stockSite > Site::borneSup) {
					var int surplus = stockSite - Site::stockInit
					this.stock = +surplus
					// Recup�re le surplus sur le camion
					site.reinitialiserStock() // Reinitialise le stock de v�lo du site
				} else if (stockSite < Site::borneInf) {
					var int manque = Site::stockInit - stockSite
					if (this.stock > manque) {
						site.reinitialiserStock();
						// Reinitialise le stock de v�lo du site
						this.stock = -manque // Met � jour le stock du camion
					} else {
						var int aDeposer = site.getStock() + this.stock
						site.setStock(aDeposer)
						// Compl�te le stock avec celui du camion
						this.stock = 0 // Met � jour le stock du camion
					}
				}
			}
			try {
				site.notifyAll()
				sleep(distanceSites) // Simule le d�placement entre le site courant et le suivant
			} catch (InterruptedException e) {
				e.printStackTrace()
			}

		} // End for		
	}
}
