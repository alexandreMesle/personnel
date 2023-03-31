package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	int insert(Employe employe) throws SauvegardeImpossible;
	int insert1(Ligue ligue) throws SauvegardeImpossible;
	void update(Ligue ligue) throws SauvegardeImpossible;
	void update(Employe employé) throws SauvegardeImpossible;
	void deleteEmploye(Employe employe) throws SauvegardeImpossible;
	void deleteLigue(Ligue ligue) throws SauvegardeImpossible;
	void delete(Employe employe) throws SauvegardeImpossible;
	void delete(Ligue ligue) throws SauvegardeImpossible;
	void update(Employe employe, String column) throws SauvegardeImpossible;

}
