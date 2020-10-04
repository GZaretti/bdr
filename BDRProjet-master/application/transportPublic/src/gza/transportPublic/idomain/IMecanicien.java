package gza.transportPublic.idomain;

import java.util.Date;

/**
 * @author ZEED
 */
public interface IMecanicien extends IEntite<IMecanicien> {

    String getNoCertification();

    void setNoCertification(String noCertification);

    public String getNom();

    public void setNom(String nom);

    public String getPrenom();

    public void setPrenom(String prenom);

    public Date getDateEmbauche();

    public void setDateEmbauche(Date date);

    public int getPourcentage();

    public void setPourcentage(int prctage);
}
