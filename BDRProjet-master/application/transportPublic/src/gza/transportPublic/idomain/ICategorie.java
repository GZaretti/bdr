/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.idomain;

/**
 *
 * @author ZEED
 */
public interface ICategorie extends IEntite<ICategorie> {
    String getNom();
    void setNom(String nom);
}
