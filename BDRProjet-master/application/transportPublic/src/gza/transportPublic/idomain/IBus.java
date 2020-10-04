package gza.transportPublic.idomain;

/**
 * @author ZEED
 */
public interface IBus extends IEntite<IBus> {

    ICategorie getCategorie();

    void setCategorie(ICategorie categorie);

    IModel getModel();

    void setModel(IModel model);

    String getNoMatricule();

    void setNoMatricule(String noMatricule);

    int getNbPassagerMax();

    void setNbPassagerMax(int nbPassager);

    boolean getHandicap();

    void setHandicap(boolean handicap);
}
