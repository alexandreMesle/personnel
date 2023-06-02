package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee;
	private LocalDate dateDepart;
	private int id;
	
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
	}
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart, int id)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
		this.gestionPersonnel = gestionPersonnel;
		this.id = id;
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	 public int getId() {
		 return this.id;
	 }
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	public LocalDate getdateArrivee() 
	{
		return dateArrivee;
	}
	
	public void setDateArrivee(LocalDate dateArrivee) throws DateImpossible
	{
		if(dateArrivee != null && dateDepart != null && dateArrivee.isBefore(dateDepart))
			throw new DateImpossible();
		else 
		{
			this.dateArrivee = dateArrivee;
		}
			
	}
	
	
	public LocalDate getdateDepart() 
	{
		return dateDepart;
	}
	
	public void setDateDepart(LocalDate dateDepart) throws DateImpossible
	{
		if(dateArrivee != null && dateDepart != null && dateDepart.isAfter(dateArrivee))
			throw new DateImpossible();
		else 
		{
			this.dateDepart = dateDepart;
		}
			
	}
	public int getIdLigue() {
		return this.ligue.getId();
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws SauvegardeImpossible 
	 */
	
	public void setNom(String nom) 
	{
		this.nom = nom;
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setArrivee(String date, LocalDate dateArrivee)  {
		// TODO Auto-generated method stub
		 this.dateArrivee = dateArrivee ;
			try {
				gestionPersonnel.update(this);
			} catch (SauvegardeImpossible e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	public void setDepart(String date, LocalDate dateDepart) {
		// TODO Auto-generated method stub
		this.dateDepart = dateDepart ;
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}
	
	public int getType() {
		/* TODO if pour savoir le type */
		
		int typ = 0;
		
		if (this.estRoot()) 
		{
			typ = 2;
		}
		else if (this.estAdmin(ligue)) // admin
		{
			typ = 1;
		}
		else // employé simple
		{
			typ = 0;
		}
	
		return typ;
	}
	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}
	public void update() throws SQLException
	{
		try {
			gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			
			e.printStackTrace();
		}
	}
	
	public GestionPersonnel getGestion() {
		return gestionPersonnel;
	}
	
	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " " + dateArrivee + " " + dateDepart + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}

	
}
