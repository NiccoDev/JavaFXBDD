package m2i.database;

/**
 *
 * @author nico
 */
public class PaysDTO {
    
    private String nom;
    
    private Integer id;

    //Constructeur vide
    public PaysDTO() {
    }

    //Constructeur avec un arguments
    public PaysDTO(String nom) {
        this.nom = nom;
    }

    //Constructeur avec 2 arguments
    public PaysDTO(String nom, Integer id) {
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " - " + nom;
    }
    
}
