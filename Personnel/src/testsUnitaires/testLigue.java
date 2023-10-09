package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue {
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	@Test
	void createLigue() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null);
		assertEquals(employe, ligue.getEmployes().first());
	}

	@Test
	void testGetNom() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Foot");
		assertEquals("Foot", ligue.getNom());
	}

	@Test
	void testSetNom() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("Bowling");
		assertEquals("Bowling", ligue.getNom());
	}

	// test potentiellement fausse
	@Test
	void testGetAdministrateur() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.getAdministrateur();
		assertEquals(employe, ligue.getAdministrateur());
	}

	@Test
	void testSetAdministrateur() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null);
		ligue.setAdministrateur(employe);
		assertEquals(employe, ligue.getAdministrateur());
	}

	@Test
	void testSetExceptionAdministrateur() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Ligue ligue2 = gestionPersonnel.addLigue("bowling");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null);
		assertThrows(DroitsInsuffisants.class, () -> ligue2.setAdministrateur(employe));
	}

	@Test
	void testremoveEmploye() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
	}

	@Test
	void testremoveLigue() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.remove();
		assertFalse(gestionPersonnel.getLigues().contains(ligue));
	}

	@Test
	void testCompareTo() throws SauvegardeImpossible {
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Ligue ligue2 = gestionPersonnel.addLigue("Bowling");
		assertEquals(4, ligue.compareTo(ligue2));
	}

	@Test
	void testToString() throws SauvegardeImpossible {
		Ligue basketball = gestionPersonnel.addLigue("Basketball");
		assertEquals("Basketball", basketball.toString());
	}

}
