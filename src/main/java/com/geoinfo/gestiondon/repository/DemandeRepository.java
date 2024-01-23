package com.geoinfo.gestiondon.repository;

import com.geoinfo.gestiondon.model.Demande;
import com.geoinfo.gestiondon.model.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DemandeRepository extends CrudRepository<Demande,Integer> {
    List<Demande> findByAnnonceId(int idAnnonce);

    List<Demande> findByUtilisateur(Utilisateur utilisateur);
}
