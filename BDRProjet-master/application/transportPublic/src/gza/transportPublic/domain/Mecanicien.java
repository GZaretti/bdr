package gza.transportPublic.domain;

import gza.transportPublic.idomain.IMecanicien;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IModel;
import gza.transportPublic.idomain.IPersonnel;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Mecanicien extends Entite<IMecanicien> implements IMecanicien, Serializable {

    protected String noCertification;
    protected String nom;
    protected String prenom;
    protected Date dateEmbauche;
    protected int pourcentage;

    private Mecanicien(long id, long version, String nom, String prenom, Date dateEmbauche, int prctage, String noCertification) {
        super(id, version);
        this.nom = nom;
        this.prenom =  prenom;
        this.dateEmbauche = dateEmbauche;
        this.pourcentage = prctage;
        this.noCertification = noCertification;
    }

    private Mecanicien(Mecanicien.Builder builder) {
        this(builder.id, builder.version, builder.nom, builder.prenom, builder.dateEmbauche, builder.pourcentage, builder.noCertification);
    }

    @Override
    public String getNoCertification() {
        return noCertification;
    }

    @Override
    public void setNoCertification(String noCertification) {
        this.noCertification = noCertification;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public Date getDateEmbauche() {
        return this.dateEmbauche;
    }

    @Override
    public void setDateEmbauche(Date date) {
        this.dateEmbauche = date;
    }

    @Override
    public int getPourcentage() {
        return this.pourcentage;
    }

    @Override
    public void setPourcentage(int prctage) {
        this.pourcentage = prctage;
    }

    @Override
    public String toString() {
        return String.format("Mecanicien : { %s, "
                + "noCertification : %s }", super.toString(), this.getNoCertification());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();
        hash = 47 * hash + Objects.hashCode(this.noCertification.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IMecanicien)) {
            return false;
        }
        final IMecanicien other = (IMecanicien) obj;

        if (!Objects.equals(this.noCertification, other.getNoCertification())) {
            return false;
        }
        return true;
    }

    public static Mecanicien.Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entite.Builder<IMecanicien> {

        protected String noCertification;
        protected String nom;
        protected String prenom;
        protected Date dateEmbauche;
        protected int pourcentage;

        public Builder IMecanicien(IMecanicien mecanicien) {
            this.id = mecanicien.getId();
            this.version = mecanicien.getVersion();
            this.noCertification = mecanicien.getNoCertification();
            this.nom = mecanicien.getNom();
            this.prenom = mecanicien.getPrenom();
            this.dateEmbauche = mecanicien.getDateEmbauche();
            this.pourcentage = mecanicien.getPourcentage();
            return this;
        }

         protected Builder() {
            super();
        }

        @Override
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        @Override
        public Builder version(long version) {
            this.version = version;
            return this;
        }
        
        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public Builder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public Builder dateEmbauche(Date date) {
            this.dateEmbauche = date;
            return this;
        }

        public Builder pourcentage(int prctage) {
            this.pourcentage = prctage;
            return this;
        }

        public Builder noCertification(String noCertification) {
            this.noCertification = noCertification;
            return this;
        }

        @Override
        public IMecanicien build() {
            return new Mecanicien(this);
        }

    }
}
