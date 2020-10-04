package gza.transportPublic.idomain;

import java.util.Date;

/**
 * @author ZEED
 */
public interface IMaintenance extends IEntite<IMaintenance> {

    IBus getBus();

    void setBus(IBus bus);

    IMecanicien getMecanicien();

    void setMecanicien(IMecanicien mecanicien);

    Date getDateDebut();

    void setDateDebut(Date date);

    Date getDateFin();

    void setDateFin(Date date);

    String getCommentaires();

    void setCommentaires(String commentaires);
}
