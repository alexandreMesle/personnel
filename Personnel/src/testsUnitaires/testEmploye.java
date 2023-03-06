package testsUnitaires;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testEmployer
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void test_date_arrive() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now);
		assertEquals(an_employer.getArrive(), now);
	}
	@Test
	void test_date_depart() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now);
		assertEquals(an_employer.getDepart(), now);
	}
}
