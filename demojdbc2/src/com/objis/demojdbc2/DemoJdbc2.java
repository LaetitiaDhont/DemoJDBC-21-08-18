package com.objis.demojdbc2;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DemoJdbc2 {

	public static void main(String[] args) {

		// Chargement du driver
		// Connexion

		String url = "jdbc:mysql://localhost/formation";
		String user = "root";
		String pwd = "Artichaut";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = (Connection) DriverManager.getConnection(url, user, pwd);

			// Création d'un statement à partir de notre objet connection
			Statement st = (Statement) cn.createStatement();

			// Nos requêtes

			String sql = "SELECT * FROM formation.clients";
			String sql2 = "SELECT np,nomp FROM formation.produits WHERE coul = 'rouge' AND PDS>2000 ";
			String sql3 = "SELECT representants.nom FROM formation.representants\r\n"
					+ "INNER JOIN formation.ventes\r\n" + "ON representants.nr = ventes.qt\r\n" + "\r\n"
					+ "WHERE qt>1;";

			ResultSet result = st.executeQuery(sql);

			int ncVar;
			String nomcVar;
			String villeVar;

			while (result.next()) {

				// recupérer le "nc"

				ncVar = result.getInt("nc");

				// récupérer le "nomc"

				nomcVar = result.getString("nomc");

				// récupérer "ville"

				villeVar = result.getString("ville");

				System.out.println("Numero Client : " + ncVar + " , NomClient : " + nomcVar + ", Ville: " + villeVar);
			}

			ResultSet result2 = st.executeQuery(sql2);

			int npVar;
			String nompVar;

			while (result2.next()) {

				// récupérer le "np"

				npVar = result2.getInt("np");

				// récupérer "nomp"

				nompVar = result2.getString("nomp");

				System.out.println("Numéro produit : " + npVar + " , Nom produit : " + nompVar);

			}

			ResultSet result3 = st.executeQuery(sql3);

			String nom;

			while (result3.next()) {

				// récupérer "nom du réprésentant"

				nom = result3.getString("nom");

				System.out.println("Nom représentants : " + nom);

			}

			// Requête 4

			String sql4 = "SELECT DISTINCT clients.nomc FROM formation.clients\r\n" + "INNER  JOIN formation.ventes\r\n"
					+ "ON clients.nc = ventes.nc\r\n" + "WHERE clients.ville = 'Lyon'\r\n" + "AND qt > 180";

			ResultSet result4 = st.executeQuery(sql4);

			String nomClient;

			while (result4.next()) {

				// récupérer "nom du client qui réside à Lyon avec des achats supérieur à 180"

				nomClient = result4.getString("nomc");

				System.out.println("Nom du client avec des achats supérieur à 180 qui réside à Lyon : " + nomClient);

			}

			// 5. Les noms des représentants et des clients à qui ces représentants ont
			// vendu un produit de couleur rouge pour une quantité supérieure à 100.

			String sql5 = "SELECT DISTINCT representants.nom, clients.nomc FROM ((( formation.ventes\r\n"
					+ "INNER JOIN formation.produits ON ventes.np = produits.np)\r\n"
					+ "INNER JOIN formation.representants ON ventes.nr = representants.nr)\r\n"
					+ "INNER JOIN formation.clients ON ventes.nc = clients.nc)\r\n"
					+ "WHERE produits.coul = 'Rouge'\r\n" + "AND ventes.qt>100";

			ResultSet result5 = st.executeQuery(sql5);

			String nomRepresentants;
			String nomClient2;

			while (result5.next()) {

				// récupérer "nom du client "

				nomClient2 = result5.getString("nomc");

				// récupérer "nom rep représentants qui ont vendu un produit

				nomRepresentants = result5.getString("nom");

				System.out.println("Le nom du client : " + nomClient2
						+ " le nom du représentant qui a vendu un produit de couleur rouge pour une quantité supérieur à 100 : "
						+ nomRepresentants);

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
