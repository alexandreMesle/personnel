package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.Ligue;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "0", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateArrivee(employe));
			menu.add(changerDateDepart(employe));
			menu.add(supprimerEmploye(employe));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> 
		{
			employe.setNom(getString("Nouveau nom : "));
			}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> 
		{
			employe.setPrenom(getString("Nouveau prénom : "));
			});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> 
		{
			employe.setMail(getString("Nouveau mail : "));
			});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le mot de passe", "x", () -> 
		{
			employe.setPassword(getString("Nouveau mot de passe : "));
		});
	}

	private Option changerDateDepart(Employe employe) 
	{
		return new Option("Changer la date de départ", "d", () -> 
		{
			String date = getString("Nouvelle date de départ (format JJ/MM/AAAA) : ");
			employe.setDepart(date, employe.getDateArrivee());
			System.out.println("La date de départ a été modifiée avec succès.");
		});
	}

	private Option changerDateArrivee(Employe employe) 
	{
		return new Option("Changer la date d'arrivée", "a", () -> 
		{
			String date = getString("Nouvelle date d'arrivée (format JJ/MM/AAAA) : ");
			employe.setArrivee(date, employe.getDateDepart());
			System.out.println("La date d'arrivée a été modifiée avec succès.");
		});
	}
	
	private Option supprimerEmploye(final Employe employe) 
	{
		return new Option("supprimer", "r", () -> 
		{
			employe.remove();
		});
	}
	
}
