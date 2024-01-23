package com.geoinfo.gestiondon.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.geoinfo.gestiondon.model.Annonce;
import com.geoinfo.gestiondon.model.Commune;

public interface CommuneRepository extends JpaRepository<Commune, Integer> {
	@Query("SELECT c.commune FROM Commune c")
    List<String> findAllCommuneNames();
	@Query("SELECT DISTINCT c.region FROM Commune c")
    List<String> findDistinctRegion();

    @Query("SELECT DISTINCT c.province FROM Commune c WHERE c.region = :region")
    List<String> findByRegion(@Param("region") String region);

    @Query("SELECT c.commune FROM Commune c WHERE c.province = :province")
    List<String> findByProvince(@Param("province") String province);
	List<String> findCommuneByCommuneContainingIgnoreCase(String searchTerm);

	@Query("SELECT DISTINCT c.x_centroid FROM Commune c INNER JOIN Annonce a ON c.commune = a.commune")
	 List<Double> findXCentroidsOfCommunesWithAnnonces();
	
	@Query("SELECT DISTINCT c.y_centroid FROM Commune c INNER JOIN Annonce a ON c.commune = a.commune")
	List<Double> findYCentroidsOfCommunesWithAnnonces();
	
	@Query("SELECT c.commune, c.x_centroid, c.y_centroid, COUNT(a.id) as numberOfAnnouncements FROM Commune c LEFT JOIN Annonce a ON c.commune = a.commune GROUP BY c.commune, c.x_centroid, c.y_centroid")
    List<Object[]> getCommuneDataWithAnnouncementsCount();
    
    @Query("SELECT c.commune, c.x_centroid, c.y_centroid, COUNT(a.id) AS nbr_annonce FROM Commune c LEFT JOIN Annonce a ON c.commune = a.commune GROUP BY c.commune, c.x_centroid, c.y_centroid HAVING COUNT(a) > 0 ORDER BY c.commune")
    List<Object[]> getCommuneDetailsWithCount();
    
    //List<Commune> findByCommuneStartingWithIgnoreCase(String debutNom);
    List<Object[]> findCommuneByCommuneStartingWithIgnoreCase(@Param("debutNom") String debutNom);
    
    @Query("SELECT DISTINCT c.commune FROM Commune c WHERE c.commune LIKE :prefixe% ORDER BY c.commune ASC")
    List<String> trouverCommunesParPrefixeIgnoreCase(@Param("prefixe") String prefixe);
    @Query(nativeQuery = true, value =
            "SELECT c.commune AS commune, COUNT(a.id) AS annonces " +
                    "FROM Commune c " +
                    "LEFT JOIN Annonce a ON c.commune = a.commune " +
                    "GROUP BY c.commune " +
                    "ORDER BY annonces DESC " +
                    "LIMIT 4")
    List<Object[]> getTopCommunesWithAnnonces();

    @Query(nativeQuery = true, value =
            "SELECT COUNT(a.id) FROM Annonce a WHERE a.commune = :communeName")
    long countByCommune(@Param("communeName") String communeName);
}