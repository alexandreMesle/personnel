package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
<<<<<<< Updated upstream
	public void update(Ligue ligue) throws SauvegardeImpossible;
	public int insert(Employe employe) throws SauvegardeImpossible;
	public void update(Employe employe) throws SauvegardeImpossible;
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible;
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible;
=======
	int insert(Employe employe) throws SauvegardeImpossible;
	int insert1(Ligue ligue) throws SauvegardeImpossible;
	void update(Ligue ligue) throws SauvegardeImpossible;
	void update(Employe employé) throws SauvegardeImpossible;
//	void deleteEmploye(Employe employe) throws SauvegardeImpossible;
//	void deleteLigue(Ligue ligue) throws SauvegardeImpossible;
	void delete(Employe employe) throws SauvegardeImpossible;
	void delete(Ligue ligue) throws SauvegardeImpossible;
	void update(Employe employe, String column) throws SauvegardeImpossible;
	void update1(Ligue ligue) throws SauvegardeImpossible;
>>>>>>> Stashed changes

}
