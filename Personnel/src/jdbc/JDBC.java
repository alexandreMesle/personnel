package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import personnel.*;

public class JDBC implements Passerelle {
	Connection connection;

	public JDBC() {
		try {
			Class.forName(CredentialsExample.getDriverClassName());
			connection = DriverManager.getConnection(CredentialsExample.getUrl(), CredentialsExample.getUser(),
					CredentialsExample.getPassword());
		} catch (ClassNotFoundException e) {
			System.out.println("Pilote JDBC non installé.");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Override
	public GestionPersonnel getGestionPersonnel() {
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try {
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		} catch (SQLException e) {
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
		close();
	}

	public void close() throws SauvegardeImpossible {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom_ligue) values(?)",
					Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}

	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement(
					"insert into employe (nom_employe, prenom_employe, mail_employe, password_employe, dateArrivé_employe, id_ligue) values(?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, java.sql.Date.valueOf(employe.getDateA()));
			instruction.setInt(6, employe.getLigue().getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}

	@Override
	public void delete(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("DELETE FROM employe WHERE id_employe = ?");
			listEmploye.setInt(1, employe.getId());
			listEmploye.executeUpdate();
			System.out.println("Employe " + employe.getNom() + " supprimer ");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}

	}

	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement listLigue;
			listLigue = connection.prepareStatement("DELETE FROM ligue WHERE id = ?");
			listLigue.setInt(1, ligue.getId());
			listLigue.executeUpdate();
			System.out.println("Ligue " + ligue.getNom() + " supprimer ");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom = ? WHERE id = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}

	}

	@Override
	public void update(Employe employe, String dataList) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET " + dataList + "= ? WHERE id_employe = ?");

			Map<String, String> map = new HashMap<>();
			map.put("nom_employe", employe.getNom());
			map.put("prenom_employe", employe.getPrenom());
			map.put("mail_employe", employe.getMail());
			map.put("password_employe", employe.getPassword());
			map.put("dateArrivee_employe",
					String.valueOf(employe.getDateA()).isEmpty() ? null : String.valueOf(employe.getDateA()));
			instruction.setString(1, map.get(dataList));
			instruction.setInt(2, employe.getId());
			instruction.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}

	}
}
