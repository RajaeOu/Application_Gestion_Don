package com.geoinfo.gestiondon.repository;


import com.geoinfo.gestiondon.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce,Integer> {

    Long countByStatut(String statut);


    List<Annonce> findByCategorie_id(Long categorieId);
    List<Annonce>findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titre, String description);

    List<Annonce>findByUtilisateur_Id(Integer utilisateurId);
    List<Annonce> findByCommune(String commune);
    List<Annonce> findAllByOrderByQuantiteDesc();
    List<Annonce> findAllByOrderByQuantiteAsc();
    List<Annonce> findAllByOrderByDateDesc();
    List<Annonce>findAllByOrderByDateAsc();
    @Query("SELECT a.categorie, COUNT(a) FROM Annonce a GROUP BY a.categorie ORDER BY COUNT(a) DESC")
    List<Object[]> countAnnouncementsByCategory();
    List<Annonce> findByStatut(String statut);

}



