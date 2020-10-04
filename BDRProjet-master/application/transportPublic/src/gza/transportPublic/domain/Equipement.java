/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.domain;

import gza.transportPublic.idomain.IEquipement;
import gza.transportPublic.idomain.IModel;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Equipement extends Entite<IEquipement> implements IEquipement, Serializable {

    protected String nom;
    protected String information;

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public void setInformation(String information) {
        this.information = information;
    }

    private Equipement(long id, long version, String nom, String information) {
        super(id, version);
        this.nom = nom;
        this.information = information;

    }

    private Equipement(Equipement.Builder builder) {
        this(builder.id, builder.version,
                builder.nom, builder.information);
    }

    @Override
    public String toString() {
        return String.format("Equipement : { %s, nom : %s, information : %s }", super.toString(),
                this.getNom(), this.getInformation());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();

        hash = 47 * hash + Objects.hashCode(this.getNom());
        hash = 47 * hash + Objects.hashCode(this.getDescription());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IEquipement)) {
            return false;
        }
        final IEquipement other = (IEquipement) obj;
        if (!Objects.equals(this.nom, other.getNom())) {
            return false;
        }
        if (!Objects.equals(this.information, other.getInformation())) {
            return false;
        }
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entite.Builder<IEquipement> {

        private String nom;
        private String information;

        private Builder() {
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

        public Builder IEquipement(IEquipement equipement) {
            this.id = equipement.getId();
            this.version = equipement.getVersion();
            this.nom = equipement.getNom();
            this.information = equipement.getDescription();
            return this;
        }

        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public Builder information(String information) {
            this.information = information;
            return this;
        }

        @Override
        public IEquipement build() {
            return new Equipement(this);
        }

    }
}
