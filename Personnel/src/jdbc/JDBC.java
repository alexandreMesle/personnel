package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * FROM ligue ORDER BY nom_ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			
			//2 boucles imbriquées
			while (ligues.next())
			{
				Ligue ligue = 	gestionPersonnel.addLigue(ligues.getInt("id_ligue"), ligues.getString("nom_ligue"));
			 PreparedStatement req = connection.prepareStatement("SELECT * FROM employe WHERE id_ligue = ?");
             req.setInt(1, ligue.getId());
             ResultSet employe = req.executeQuery();

             while (employe.next()) {
                 int id = employe.getInt("id_employee");
                 String nom = employe.getString("nom");
                 String prenom = employe.getString("prenom");
                 String mail = employe.getString("mail");
                 String password = employe.getString("password");
                 LocalDate date_arrivee = employe.getDate("date_arrivee") != null ? LocalDate.parse(employe.getString("date_arrivee")) : null;
                 LocalDate date_depart = employe.getDate("date_depart") != null ? LocalDate.parse(employe.getString("date_depart")) : null;    
                 int type = employe.getInt("habilitation");
                 Employe employee = ligue.addEmploye(nom, prenom, mail, password, date_arrivee, date_depart,id);
                 if (type == 1) {
                     ligue.setAdministrateur(employee);
                 }
                
             }
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	

	

	
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement(
					"INSERT INTO employe (id_employee, nom, prenom, mail, password, date_arrivee, date_depart,id_ligue, habilitation) VALUES (?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getdateArrivee() == null ? null : Date.valueOf(employe.getdateArrivee()));
			instruction.setDate(6, employe.getdateDepart() == null ? null : Date.valueOf(employe.getdateDepart()));
			instruction.setInt(7, employe.getType());
			instruction.setInt(8, employe.getLigue().getId());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();

			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
<<<<<<< Updated upstream
=======
	

	
	@Override
	public void update(Employe employe, String column) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement(
					"UPDATE employe SET nom = (?), prenom = (?), mail = (?), password = (?), date_arrivee = (?), date_depart = (?), habilitation(?) = (?) WHERE id_employe = (?)");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, employe.getdateArrivee() == null ? null : Date.valueOf(employe.getdateArrivee()));
			instruction.setDate(6, employe.getdateDepart() == null ? null : Date.valueOf(employe.getdateDepart()));
			instruction.setInt(7, employe.getType());
			instruction.setInt(8, employe.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("DELETE FROM ligue WHERE id_ligue = ?");
			instruction.setInt(1, ligue.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}
	
	@Override
	public void delete(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("DELETE FROM employe WHERE id_employe = ?");
			instruction.setInt(1, employe.getId());
			instruction.executeUpdate();

		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}

	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible {
		
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom_ligue = '"+ligue.getNom()+"' WHERE id_ligue = "+ligue.getId()+" ");
			instruction.executeUpdate();
		} catch (SQLException e) {
			
			throw new SauvegardeImpossible(e);
		}		
		
	}
>>>>>>> Stashed changes

	@Override
	public void update(Employe employe) throws SauvegardeImpossible {
		
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET nom = '"+ employe.getNom()+
					"', prénom = '"+employe.getPrenom()+"',  mail = '"+employe.getMail()+"',  password ='"+
					employe.getPassword()+"', habilitation = "+employe.getType() +" WHERE id_employee = "+ employe.getid() +" ", Statement.RETURN_GENERATED_KEYS);
			instruction.executeUpdate();
		} catch (SQLException e) {
			
			throw new SauvegardeImpossible(e);
		}
		
	
	}
	

<<<<<<< Updated upstream
	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible {
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("DELETE FROM employe WHERE id_employee = ?");
			instruction.setInt(1, employe.getid());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{

			throw new SauvegardeImpossible(e);
		}
		
	}
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom_ligue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible {
		try
		{
			PreparedStatement tableLigue;
			tableLigue = connection.prepareStatement("DELETE FROM ligue WHERE id_ligue = ?");
			tableLigue.setInt(1, ligue.getId());
			tableLigue.executeUpdate();;
		}
		catch (SQLException e) 
		{
			throw new SauvegardeImpossible(e);
		}
		
	}
=======
//	@Override
//	public void deleteEmploye(Employe employe) throws SauvegardeImpossible {
//		try
//		{
//			PreparedStatement instruction;
//			instruction = connection.prepareStatement("DELETE FROM employe WHERE id_employee = ?");
//			instruction.setInt(1, employe.getid());
//			instruction.executeUpdate();
//		}
//		catch (SQLException e) 
//		{
//
//			throw new SauvegardeImpossible(e);
//		}
//		
//	}
//
//	@Override
//	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible {
//		try
//		{
//			PreparedStatement tableLigue;
//			tableLigue = connection.prepareStatement("DELETE FROM ligue WHERE id_ligue = ?");
//			tableLigue.setInt(1, ligue.getId());
//			tableLigue.executeUpdate();;
//		}
//		catch (SQLException e) 
//		{
//			throw new SauvegardeImpossible(e);
//		}
//		
//	}
>>>>>>> Stashed changes

	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET nom_ligue = '"+ligue.getNom()+"' WHERE id_ligue = "+ligue.getId()+" ", Statement.RETURN_GENERATED_KEYS);
			instruction.executeUpdate();
		} catch (SQLException e) {
			
			throw new SauvegardeImpossible(e);
		}		
	}
}
