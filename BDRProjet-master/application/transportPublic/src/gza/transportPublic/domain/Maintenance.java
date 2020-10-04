package gza.transportPublic.domain;

import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.IMaintenance;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IMecanicien;
import gza.transportPublic.idomain.IModel;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ZEED
 */
public class Maintenance extends Entite<IMaintenance> implements IMaintenance, Serializable {

    protected IMecanicien mecanicien;
    protected IBus bus;
    protected Date dateDebut;
    protected Date dateFin;
    protected String commentaires;

    private Maintenance(long id, long version, IMecanicien mecanicien,
            IBus bus, Date dateDebut, Date dateFin, String commentaires) {
        super(id, version);
        this.mecanicien = mecanicien;
        this.bus = bus;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaires = commentaires;
    }

    private Maintenance(Maintenance.Builder builder) {
        this(builder.id, builder.version, builder.mecanicien,
                builder.bus, builder.dateDebut, builder.dateFin, builder.commentaires);
    }

    @Override
    public IBus getBus() {
        return bus;
    }

    @Override
    public void setBus(IBus bus) {
        this.bus = bus;
    }

    @Override
    public IMecanicien getMecanicien() {
        return mecanicien;
    }

    @Override
    public void setMecanicien(IMecanicien mecanicien) {
        this.mecanicien = mecanicien;
    }

    @Override
    public Date getDateDebut() {
        return dateDebut;
    }

    @Override
    public void setDateDebut(Date date) {
        this.dateDebut = date;
    }

    @Override
    public Date getDateFin() {
        return dateFin;
    }

    @Override
    public void setDateFin(Date date) {
        this.dateFin = date;
    }
    @Override
    public String getCommentaires() {
        return commentaires;
    }

    @Override
    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }


    @Override
    public String toString() {
        return String.format("Maintenance : { %s, }", super.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + super.hashCode();

        hash = 47 * hash + this.bus.hashCode();
        hash = 47 * hash + this.mecanicien.hashCode();
        hash = 47 * hash + Objects.hashCode(this.dateDebut);
        hash = 47 * hash + Objects.hashCode(this.dateFin);
        hash = 47 * hash + Objects.hashCode(this.commentaires);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof IMaintenance)) {
            return false;
        }
        final IMaintenance other = (IMaintenance) obj;
        if (!Objects.equals(this.bus, other.getBus())) {
            return false;
        }
        if (!Objects.equals(this.mecanicien, other.getMecanicien())) {
            return false;
        }
        if (!Objects.equals(this.dateDebut, other.getDateDebut())) {
            return false;
        }
        if (!Objects.equals(this.dateFin, other.getDateFin())) {
            return false;
        }
        if (!Objects.equals(this.commentaires, other.getCommentaires())) {
            return false;
        }
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder extends Entite.Builder<IMaintenance> {

        private IMecanicien mecanicien;
        private IBus bus;
        private Date dateDebut;
        private Date dateFin;
        private String commentaires;

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

        public Builder IMaintenance(IMaintenance maintenance) {
            this.id = maintenance.getId();
            this.version = maintenance.getVersion();
            
        this.mecanicien = maintenance.getMecanicien();
        this.bus = maintenance.getBus();
        this.mecanicien = maintenance.getMecanicien();
        this.dateDebut = maintenance.getDateDebut();
        this.dateFin = maintenance.getDateFin();
        this.commentaires = maintenance.getCommentaires();
            
            return this;
        }

        public Builder mecanicien(IMecanicien mecanicien) {
            this.mecanicien = mecanicien;
            return this;
        }

        public Builder bus(IBus bus) {
            this.bus = bus;
            return this;
        }

        public Builder dateDebut(Date dateDebut) {
            this.dateDebut = dateDebut;
            return this;
        }

        public Builder dateFin(Date dateFin) {
            this.dateFin = dateFin;
            return this;
        }

        public Builder commentaires(String commentaires) {
            this.commentaires = commentaires;
            return this;
        }

        @Override
        public IMaintenance build() {
            return new Maintenance(this);
        }

    }
}
