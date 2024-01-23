package com.geoinfo.gestiondon.repository;


import com.geoinfo.gestiondon.model.Utilisateur;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur,Integer> {
    Utilisateur findByLogin(String login);
    Page<Utilisateur> findAll(Pageable pageable);
    List<Utilisateur> findByNom(String nom);
    Page<Utilisateur> findByNomContainingIgnoreCase(String nom, Pageable pageable);

}
