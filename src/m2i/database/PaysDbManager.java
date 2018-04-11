package m2i.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nico
 */
public class PaysDbManager {

    private Connection cn;

    public PaysDbManager(Connection cn) {
        this.cn = cn;
    }//fin PaysDbManager

    /**
     * Méthode pour insérer des données dans la table pays INSERT
     *
     * @param nomPays
     * @throws SQLException
     */
    public void insert(String nomPays) throws SQLException {

        // --- INSERT
        String sql = "INSERT INTO pays (nomPays) VALUES (?)";

        // --- Creation du PreparedStatement "commande SQL"
        PreparedStatement statement = cn.prepareStatement(sql);

        // --- Valorisation du ou des parametre(s)
        // --- Les valeurs pourraient etre saisies au clavier
        statement.setString(1, nomPays);

        // --- Execution de la requete
        statement.executeUpdate();

    }// fin insert

    public String ListDesPays() throws SQLException {

        // --- SELECT
        String sql = "SELECT * FROM pays";

        // --- Creation de l'objet "commande SQL"
        Statement stm = cn.createStatement();

        // --- Execution de la requete
        ResultSet rs = stm.executeQuery(sql);

        StringBuilder sb = new StringBuilder();

        //Boucle tant que
        while (rs.next()) {
            sb.append(rs.getString("id"));
            sb.append(" : ");
            sb.append(rs.getString("nomPays"));
            sb.append("\n");
        }

        return sb.toString();

    }//fin ListDesPays

    public List<PaysDTO> ListDesPaysPourListView() throws SQLException {

        // --- SELECT
        String sql = "SELECT * FROM pays";

        // --- Creation de l'objet "commande SQL"
        Statement stm = cn.createStatement();

        // --- Execution de la requete
        ResultSet rs = stm.executeQuery(sql);

        //Création d'une liste vide
        List<PaysDTO> liste = new ArrayList<PaysDTO>();

        //Remplissage de la liste en fonction de la requête
        //Boucle tant que
        while (rs.next()) {
            //Instanciation du DTO
            PaysDTO pays = new PaysDTO();
            pays.setId(rs.getInt("id"));
            pays.setNom(rs.getString("nomPays"));

            //Ajout du pays à la liste
            liste.add(pays);

        }//fin de la boucle
        return liste;

    }//fin ListDesPays

    /**
     * Méthode DELETE NID
     *
     * @param id
     * @throws SQLException
     */
    public void Supprimer(int id) throws SQLException {

        try {

            // --- DELETE
            String lsSQL = "DELETE FROM pays WHERE id = ?";

            // --- Creation de l'objet "commande SQL"
            PreparedStatement stm = cn.prepareStatement(lsSQL);

            // --- Valorisation du ou des parametre(s)
            // --- Les valeurs pourraient etre saisies au clavier
            stm.setInt(1, id);

            // --- Execution de la requete
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }//fin méthode NID Supprimer

    /**
     * Méthode DELETE Prof
     *
     * @param pays
     * @throws SQLException
     */
    public void supprimer(PaysDTO pays) throws SQLException {

        String sql = "DELETE FROM pays WHERE id = ?";

        PreparedStatement stm = cn.prepareStatement(sql);
        stm.setInt(1, pays.getId());

        stm.executeUpdate();
    }//fin méthode Prof supprimer

    /**
     * Méthode Update
     *
     * @param pays
     * @throws SQLException
     */
    public void modif(PaysDTO pays) throws SQLException {

        String sql = "UPDATE pays SET nomPays = ? WHERE id = ?";

        PreparedStatement stm = cn.prepareStatement(sql);
        stm.setString(1, pays.getNom());
        stm.setInt(1, pays.getId());

        stm.executeUpdate();
    }//fin méthode modif

}//fin class PaysDbManager
