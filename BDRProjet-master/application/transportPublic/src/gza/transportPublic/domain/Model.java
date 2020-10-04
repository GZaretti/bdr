/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.domain;

import gza.transportPublic.idomain.IModel;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Model extends Entite<IModel> implements IModel, Serializable {

    protected String nom;

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    private Model(long id, long version, String nom) {
        super(id, version);
        this.nom = nom;
       
    }

    private Model(Model.Builder builder) {
        this(builder.id, builder.version,
                builder.nom);
    }
    
    @Override
    public String toString() {
        return String.format("Model : { %s, nom : %s, }", super.toString(),
                this.getNom());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();

        hash = 47 * hash + Objects.hashCode(this.getNom());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IModel)) {
            return false;
        }
        final IModel other = (IModel) obj;
        if (!Objects.equals(this.nom, other.getNom())) {
            return false;
        }
        return true;
    }
     public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Entite.Builder<IModel> {

        private String nom;

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

        public Builder IModel(IModel model) {
            this.id = model.getId();
            this.version = model.getVersion();
            this.nom = model.getNom();
            return this;
        }

        public Builder nom(String nom) {
            this.nom = nom;
            return this;
        }

        @Override
        public IModel build() {
            return new Model(this);
        }

    }
    

}
