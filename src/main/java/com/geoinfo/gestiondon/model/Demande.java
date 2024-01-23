package com.geoinfo.gestiondon.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_annonce", referencedColumnName = "id")
    private Annonce annonce;

    private int quantite;
    private Date date;
    private String statut;

    // Constructeur par défaut
    public Demande() {
    }

    // Constructeur avec tous les champs
    public Demande(int id, Utilisateur utilisateur, Annonce annonce, int quantite, Date date, String statut) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.annonce = annonce;
        this.quantite = quantite;
        this.date = date;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", annonce=" + annonce +
                ", quantite=" + quantite +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                '}';
    }
}
