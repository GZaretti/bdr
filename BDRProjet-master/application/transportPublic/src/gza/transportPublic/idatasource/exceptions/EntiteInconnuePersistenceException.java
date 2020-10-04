/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.idatasource.exceptions;

/**
 *
 * @author ZEED
 */
public class EntiteInconnuePersistenceException extends PersistenceException  {
    public EntiteInconnuePersistenceException() {
    }

    public EntiteInconnuePersistenceException(String message) {
        super(message);
    }

    public EntiteInconnuePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntiteInconnuePersistenceException(Throwable cause) {
        super(cause);
    }

    public EntiteInconnuePersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
