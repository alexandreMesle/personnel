package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "0");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "0", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "0", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "0", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
//		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
	    return new Option("ajouter un employé", "a",
	        () -> 
	        {
	            String nom, prenom, mail, password;
	            LocalDate Arrivee = null, Depart = null;

	            nom = getString("nom : ");
	            prenom = getString("prenom : ");
	            mail = getString("mail : ");
	            password = getString("mot de passe : ");
	            Arrivee = parseDate("Date Arrivee YYYY-MM-DD : ");
	            while (true) {
	                Depart = parseDate("Date Depart YYYY-MM-DD : ");
	                if (!Depart.isBefore(Arrivee)) {
	                    break;
	                }
	                System.out.println("La date de départ doit être postérieure ou égale à la date d'arrivée.");
	            }
	            ligue.addEmploye(nom, prenom, mail, password, Arrivee, Depart, 0);
	        }
	    );
	}
	  private LocalDate parseDate(String string){
	        while(true)
	            try {
	            return LocalDate.parse(getString(string));
	             }
	        catch(DateTimeParseException e) 
	        {
	             System.err.println("Erreur! Veuillez respecter le format YYYY-MM-DD");
	        }
	    
	    }
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}


	
//	private List<Employe> changerAdministrateur(final Ligue ligue)
//	{
//		return new List<>("Modifier l'administrateur de la ligue", "o", 
//                () -> new ArrayList<>(ligue.getEmployes()),
//                (index, element) -> {ligue.setAdministrateur(element);
//                }
//                );
//	}		

	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Selectionner un employé", "r", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}

	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {try {
			ligue.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
	}
	
}
