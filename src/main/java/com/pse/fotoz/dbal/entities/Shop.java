/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal.entities;

import com.pse.fotoz.dbal.HibernateEntityHelper;
import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.HibernateSession;
import com.pse.fotoz.helpers.encryption.PasswordHash;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.Session;

/**
 *
 * @author René
 */
@Entity
@Table(name = "shops")
public class Shop implements HibernateEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Photographer photographer;

    @Basic
    @Column(name = "login", unique = true)
    private String login;

    //TODO: hashen wellicht handig
    @Basic
    @Column(name = "passwordHash")
    private String passwordHash;

    @OneToMany(mappedBy = "shop")
    private Set<PictureSession> sessions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash value in the database
     *
     * @param passwordHash Hash value, do not store a plain password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Sets the password in the database as a hash
     *
     * @param password the plain password that will be stored as a hash
     */
    public void setPassword(String password) {
        try {
            this.passwordHash = PasswordHash.createHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Validates a password
     *
     * @param password the password to be verified
     * @return true if the password matches the stored password
     */
    public boolean validatePassword(String password) {
        boolean returnBool = false;
        try {
            if (!passwordHash.isEmpty()) {
                returnBool = PasswordHash.validatePassword(password, passwordHash);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnBool;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public Set<PictureSession> getSessions() {
        return sessions;
    }

    public static Shop getShopByID(int id) {
        Shop returnShop = null;
        try {
            Session session = HibernateSession.getInstance().newSession();
            returnShop = (Shop) session.load(Shop.class, id);
        } catch (HibernateException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnShop;
    }

    public static Shop getShopByLogin(String login) {
        Shop returnShop = null;
        
        returnShop = HibernateEntityHelper.all(Shop.class).
                stream().
                filter(s -> s.getLogin().equals(login)).
                findAny().
                get();

        return returnShop;
    }
}
