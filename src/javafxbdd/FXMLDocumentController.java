package javafxbdd;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import m2i.database.ConnexionBD;
import m2i.database.PaysDTO;
import m2i.database.PaysDbManager;

/**
 *
 * @author nico
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TextField paysTextField;

    @FXML
    private Label resultat;

    @FXML
    private ListView<PaysDTO> paysListView;

    @FXML
    private TextField idPaysSupprimer;

    //Déclaration du gestionnaire de Pays
    PaysDbManager manager;

    //PaysDTO est une classe
    PaysDTO paysSelectionne;

    @FXML//permet de faire appel depuis l'interface
    private void onValider(ActionEvent event) {

        //Récupération de la saisie de l'utilisateur
        String nomPays = paysTextField.getText();

        //Validation de la saisie
        if (nomPays.trim().equals("")) {
            Alert warning = new Alert(AlertType.WARNING);
            warning.setTitle("Attention");
            warning.setHeaderText("Veuillez saisir un pays");
            warning.show();
            return;
        }

        try {
            if (paysTextField != null) {
                //Exécution de la méthode d'insertion
                if (paysSelectionne == null) {
                    manager.insert(nomPays);
                    resultat.setText("Enregistrement ok");
                } else {
                    //Exécution de la méthode de modification
                    //modification de PaysDTO en fonction de la saisie de l'utilisateur
                    paysSelectionne.setNom(nomPays);
                    manager.modif(paysSelectionne);
                    resultat.setText("Modification effectué");
                    paysSelectionne = null;
                }
                afficheListePays();
                paysTextField.setText("");
            }
        } catch (SQLException ex) {
            //Message d'erreur en cas d'échec
            resultat.setText("Impossible d'insérer un nouveau pays");
        }
    }//fin de onValider

    private void afficheListePays() throws SQLException {
        //paysTextArea.setText(manager.ListDesPays());

        //Création d'une liste observable
        ObservableList<PaysDTO> paysObservable;

        //Affectation des données de la liste
        paysObservable = FXCollections.observableArrayList(manager.ListDesPaysPourListView());

        //Liaison entre ListeView et données permet aussi de générer une erreur
        paysListView.setItems(paysObservable);
    }//fin de afficheListePays

    //Méthode NID de suppression avec saisie de l'id
    @FXML
    private void onSupprimer(ActionEvent event) {

        int id = Integer.valueOf(idPaysSupprimer.getText());

        try {
            //Création d'une instance de PaysManager
            PaysDbManager manager = new PaysDbManager(ConnexionBD.getConnexion());

            //Exécution de la méthode d'insertion
            manager.Supprimer(id);

            afficheListePays();

            //Message de succes
            resultat.setText("suppression ok");
        } catch (SQLException ex) {
            //Message d'erreur en cas d'échec
            resultat.setText("Impossible de supprimer le pays");
        }
        idPaysSupprimer.setText("");
    }// fin de onSupprimer

    //Méthode Prof de suppression avec la sélection du pays
    @FXML
    private void onSuppression(ActionEvent event) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Attention opération délicate");
        confirmation.setHeaderText("Voulez-vous vraiment supprimer ce pays ?");
        confirmation.setContentText("Cela éliminera un con!");
        Optional<ButtonType> resultat = confirmation.showAndWait();
        if (resultat.get() == ButtonType.OK) {
            supprime();
        }
    }

    private void supprime() {
        try {
            if (paysSelectionne != null) {
                //Suppression du pays
                manager.supprimer(paysSelectionne);
                //Mise à jour de l'affichage des pays
                afficheListePays();
                //Mise à null du pays sélectionné
                paysSelectionne = null;
                //Message de succès
                resultat.setText("suppression ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin de méthode onSuppression

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Création d'une instance de PaysManager
            manager = new PaysDbManager(ConnexionBD.getConnexion());
            afficheListePays();

            //Récupération du modèle de sélection
            MultipleSelectionModel<PaysDTO> model = paysListView.getSelectionModel();

            //Définition d'un écouteur d'évènement
            model.selectedItemProperty().addListener(new ChangeListener<PaysDTO>() {
                @Override
                public void changed(ObservableValue<? extends PaysDTO> observable, PaysDTO oldValue, PaysDTO newValue) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                    if (newValue != null) {
                        System.out.println(newValue.getNom());
                        paysSelectionne = newValue;
                        paysTextField.setText(newValue.getNom());
                    }
                }
            });

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//fin de méthode initialize
}
