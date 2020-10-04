/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.domain;

import gza.transportPublic.idomain.IEntite;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public abstract class Entite<T> implements IEntite<T>, Serializable {
    protected long id ;
    protected long version;
    protected Entite() {
        this.version = 0L;
    }
    protected Entite(long id, long version) {
        this.id = id;
        this.version = version;
    }
    
    protected Entite(Builder<T> builder){
        this.id = builder.id;
        this.version = builder.version;
    }

    @Override
    public long getId() {
       return id;
    }

    @Override
    public String getDescription() {
        return String.format("id : %d", id);
    }

    @Override
    public long getVersion() {
        return version;
    }
    
     @Override
    public String toString() {
        return String.format("Entite : { id : %d, version : %d } ", this.id, this.version);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(id);
        hash = 97 * hash + Objects.hashCode(version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IEntite)) {
            return false;
        }

        final IEntite other = (IEntite) obj;
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }

        if (!Objects.equals(this.version, other.getVersion())) {
            return false;
        }
        return true;
    }
    
    
    public static abstract class Builder<T> {

        protected long id;
        protected long version;

        protected Builder() {
        }

        public Builder<T> id(long id) {
            this.id = id;
            return this;
        }

        public Builder<T> version(long version) {
            this.version = version;
            return this;
        }


        public abstract T build();

    }    
    
}
