package gza.transportPublic.domain;

import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IModel;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Bus extends Entite<IBus> implements IBus, Serializable {

    protected String noMatricule;
    protected int nbPassagerMax;
    protected boolean handicap;

    protected IModel model;
    protected ICategorie categorie;

    private Bus(long id, long version, String noMatricule, int nbPassagerMax, boolean handicap,
            ICategorie categorie, IModel model) {
        super(id, version);
        this.nbPassagerMax = nbPassagerMax;
        this.handicap = handicap;
        this.categorie = categorie;
        this.model = model;
        this.noMatricule = noMatricule;
    }

    private Bus(Bus.Builder builder) {
        this(builder.id, builder.version, builder.noMatricule,
                builder.nbPassagerMax, builder.handicap, builder.categorie, builder.model);
    }

    @Override
    public int getNbPassagerMax() {
        return this.nbPassagerMax;
    }

    @Override
    public void setNbPassagerMax(int nbPassagerMax) {
        this.nbPassagerMax = nbPassagerMax;
    }

    @Override
    public boolean getHandicap() {
        return handicap;
    }

    @Override
    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }
        @Override
    public ICategorie getCategorie() {
        return categorie;
    }

    @Override
    public void setCategorie(ICategorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public IModel getModel() {
       return model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public String getNoMatricule() {
        return noMatricule;
    }

    @Override
    public void setNoMatricule(String noMatricule) {
        this.noMatricule = noMatricule; }

    @Override
    public String toString() {
        return String.format("Bus : { %s, "
                + "noMatricule : %s }", super.toString(), this.getNoMatricule() );
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();

        hash = 47 * hash + Objects.hashCode(this.nbPassagerMax);

        hash = 47 * hash + Objects.hashCode(this.handicap);
        hash = 47 * hash + Objects.hashCode(this.categorie.hashCode());
        hash = 47 * hash + Objects.hashCode(this.model.hashCode());
        hash = 47 * hash + Objects.hashCode(this.noMatricule.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IBus)) {
            return false;
        }
        final IBus other = (IBus) obj;
        if (!Objects.equals(this.nbPassagerMax, other.getNbPassagerMax())) {
            return false;
        }
        if (!Objects.equals(this.handicap, other.getHandicap())) {
            return false;
        }
        if (!Objects.equals(this.noMatricule, other.getNoMatricule())) {
            return false;
        }
        if (!Objects.equals(this.categorie, other.getCategorie())) {
            return false;
        }
        if (!Objects.equals(this.model, other.getModel())) {
            return false;
        }
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder extends Entite.Builder<IBus> {

        protected String noMatricule;
        private int nbPassagerMax;
        private boolean handicap;
        private IModel model;
        private ICategorie categorie;

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

        public Builder IBus(IBus bus) {
            this.id = bus.getId();
            this.version = bus.getVersion();
            this.nbPassagerMax = bus.getNbPassagerMax();
            this.handicap = bus.getHandicap();
            this.noMatricule = bus.getNoMatricule();
            this.model = bus.getModel();
            this.categorie = bus.getCategorie();
            return this;
        }

        public Builder noMatricule(String noMatricule) {
            this.noMatricule = noMatricule;
            return this;
        }

        public Builder model(IModel model) {
            this.model = model;
            return this;
        }

        public Builder categorie(ICategorie categorie) {
            this.categorie = categorie;
            return this;
        }

        public Builder nbPassagerMax(int nbPassagerMax) {
            this.nbPassagerMax = nbPassagerMax;
            return this;
        }

        public Builder handicap(boolean handicap) {
            this.handicap = handicap;
            return this;
        }

        @Override
        public IBus build() {
            return new Bus(this);
        }

    }
}
