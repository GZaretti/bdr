/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.domain;

import gza.transportPublic.idomain.IPersonnel;
import gza.transportPublic.idomain.IModel;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Personnel extends Entite<IPersonnel> implements IPersonnel, Serializable {

    protected String nom;
    protected String prenom;
    protected Date dateEmbauche;
    protected int pourcentage;

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
        return dateEmbauche;
    }

    @Override
    public void setDateEmbauche(Date date) {
        this.dateEmbauche = date;
    }

    @Override
    public void setPourcentage(int prctage) {
        this.pourcentage = prctage;
    }

    @Override
    public int getPourcentage() {
        return this.pourcentage;
    }

    protected Personnel(long id, long version, String nom, String prenom, Date dateEmbauche, int prctage) {
        super(id, version);
        this.nom = nom;
        this.prenom = prenom;
        this.dateEmbauche = dateEmbauche;
        this.pourcentage = prctage;

    }

    private Personnel(Personnel.Builder builder) {
        this(builder.id, builder.version,
                builder.nom, builder.prenom, builder.dateEmbauche, builder.pourcentage);
    }

    @Override
    public String toString() {
        return String.format("Personnel : { %s, nom : %s, }", super.toString(),
                this.getNom());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();
        hash = 47 * hash + Objects.hashCode(this.getNom());
        hash = 47 * hash + Objects.hashCode(this.getPrenom());
        hash = 47 * hash + Objects.hashCode(this.getDateEmbauche());
        hash = 47 * hash + Objects.hashCode(this.getPourcentage());

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IPersonnel)) {
            return false;
        }
        final IPersonnel other = (IPersonnel) obj;
        if (!Objects.equals(this.nom, other.getNom())) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.getPrenom())) {
            return false;
        }
        if (!Objects.equals(this.dateEmbauche, other.getDateEmbauche())) {
            return false;
        }
        if (!Objects.equals(this.pourcentage, other.getPourcentage())) {
            return false;
        }
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entite.Builder<IPersonnel> {

        protected String nom;
        protected String prenom;
        protected Date dateEmbauche;
        protected int pourcentage;

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

        public Builder IModel(IPersonnel model) {
            this.id = model.getId();
            this.version = model.getVersion();
            this.nom = model.getNom();
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
        
        
        public Builder dateEmbauche(Date dateEmbauche) {
            this.dateEmbauche = dateEmbauche;
            return this;
        }
          
        public Builder pourcentage(int pourcentage) {
            this.pourcentage = pourcentage;
            return this;
        }
        
        @Override
        public IPersonnel build() {
            return new Personnel(this);
        }

    }

}
