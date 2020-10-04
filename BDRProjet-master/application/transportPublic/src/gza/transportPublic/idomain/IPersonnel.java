package gza.transportPublic.idomain;

import java.util.Date;

/**
 * @author ZEED
 */
public interface IPersonnel extends IEntite<IPersonnel> {

    String getNom();

    void setNom(String nom);

    String getPrenom();

    void setPrenom(String prenom);

    Date getDateEmbauche();

    void setDateEmbauche(Date date);

    int getPourcentage();

    void setPourcentage(int prctage);
}
