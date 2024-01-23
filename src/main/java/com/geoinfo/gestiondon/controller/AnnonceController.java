package com.geoinfo.gestiondon.controller;


import com.geoinfo.gestiondon.model.Annonce;
import com.geoinfo.gestiondon.model.Categorie;
import com.geoinfo.gestiondon.model.Utilisateur;
import com.geoinfo.gestiondon.repository.AnnonceRepository;
import com.geoinfo.gestiondon.repository.CategorieRepository;
import com.geoinfo.gestiondon.repository.CommuneRepository;
import com.geoinfo.gestiondon.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/annonces")

public class AnnonceController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CommuneRepository communeRepository;

    @GetMapping("/{id}")
    public Annonce getAnnoceById(@PathVariable("id") int id){
        return annonceRepository.findById(id).get();

    }
    @PostMapping("/ajouter")
    public void ajouterAnnonce(@RequestBody Annonce annonce) {


            Utilisateur utilisateur = utilisateurRepository.findById(annonce.getUtilisateur().getId()).orElse(null);
            Categorie categorie = categorieRepository.findById(annonce.getCategorie().getId()).orElse(null);



                annonce.setUtilisateur(utilisateur);
                annonce.setCategorie(categorie);

                annonceRepository.save(annonce);


    }



    @GetMapping("/nombre")
    public Long getNombreAnnonces() {
        return annonceRepository.count();
    }
    @GetMapping("/annoncesGroupedByCommune")
    public Map<String, Object> getAnnoncesGroupedByCommune() {
        List<Object[]> communeDetails = communeRepository.getCommuneDetailsWithCount();

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> communesList = new ArrayList<>();

        for (Object[] communeDetail : communeDetails) {
            String communeName = (String) communeDetail[0];
            Double xCentroid = (Double) communeDetail[1];
            Double yCentroid = (Double) communeDetail[2];
            Long numberOfAnnouncements = (Long) communeDetail[3];

            Map<String, Object> communeMap = new HashMap<>();
            communeMap.put("nom", communeName);
            communeMap.put("longitude", xCentroid);
            communeMap.put("latitude", yCentroid);
            communeMap.put("nombreAnnonces", numberOfAnnouncements);

            // Récupérer les annonces pour cette commune
            List<Annonce> annonces = annonceRepository.findByCommune(communeName);
            List<Map<String, Object>> annoncesList = new ArrayList<>();

            for (Annonce annonce : annonces) {
                Map<String, Object> annonceMap = new HashMap<>();
                annonceMap.put("id", annonce.getId());
                annonceMap.put("utilisateur", annonce.getUtilisateur());
                annonceMap.put("titre", annonce.getTitre());
                annonceMap.put("description", annonce.getDescription());
                annonceMap.put("categorie", annonce.getCategorie());
                annonceMap.put("quantite", annonce.getQuantite());
                annonceMap.put("commune", annonce.getCommune());
                annonceMap.put("date", annonce.getDate());
                annonceMap.put("statut", annonce.getStatut());

                annoncesList.add(annonceMap);
            }

            communeMap.put("annonces", annoncesList);
            communesList.add(communeMap);
        }

        result.put("communes", communesList);
        return result;
    }
    

    @GetMapping("/all_annonces1")
    public List<Annonce> getAnnonces1() {
        return (List<Annonce>)annonceRepository.findByStatut("validé");

        
    }
    @GetMapping("/all_annonces")
    public List<Annonce> getAnnonces() {
        return (List<Annonce>)annonceRepository.findAll();
        
    }
    

    @GetMapping("/nombre-accepte")
    public Long getNombreAnnoncesAcceptees() {
        return annonceRepository.countByStatut("accepte");
    }
    @GetMapping("/nombre-rejete")
    public Long getNombreAnnoncesRejetees() {
        return annonceRepository.countByStatut("rejete");
    }

    @GetMapping("/par-categorie/{categorieId}")
    public List<Annonce> getAnnoncesParCategorie(@PathVariable Long categorieId) {
        return annonceRepository.findByCategorie_id(categorieId);
    }

    @GetMapping("/par-motcle/{motcle}")
    public List<Annonce> getAnnoncesParMotCle(@PathVariable String motcle) {
        return annonceRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(motcle,motcle);
    }

    @PutMapping("/{id}/editer-statut")
    public ResponseEntity<Annonce> editerStatutAnnonce(@PathVariable int id, @RequestParam String nouveauStatut) {
        Annonce annonceExistante = annonceRepository.findById(id).orElse(null);

        if (annonceExistante != null) {
            annonceExistante.setStatut(nouveauStatut);

            Annonce annonceMiseAJour = annonceRepository.save(annonceExistante);
            return new ResponseEntity<>(annonceMiseAJour, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/recent-annonces")
    public List<Annonce> getRecentAnnonces() {
        List<Annonce> recentAnnonces = annonceRepository.findAllByOrderByDateDesc();

        // Limit to 5 items
        return recentAnnonces.stream().limit(5).collect(Collectors.toList());
    }


    // Annonces triées par date (ascendant ou descendant)
    @GetMapping("/tri-par-date")
    public List<Annonce> getAnnoncesTrieesParDate(@RequestParam(defaultValue = "asc") String ordre) {
        if ("asc".equalsIgnoreCase(ordre)) {
            return annonceRepository.findAllByOrderByDateAsc();
        } else {
            return annonceRepository.findAllByOrderByDateDesc();
        }
    }

    // Annonces triées par quantité (ascendant ou descendant)
    @GetMapping("/tri-par-quantite")
    public List<Annonce> getAnnoncesTrieesParQuantite(@RequestParam(defaultValue = "asc") String ordre) {
        if ("asc".equalsIgnoreCase(ordre)) {
            return annonceRepository.findAllByOrderByQuantiteAsc();
        } else {
            return annonceRepository.findAllByOrderByQuantiteDesc();
        }
    }

    // Modifier une annonce
   
    // Supprimer une annonce
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerAnnonce(@PathVariable int id) {
        Annonce annonce = annonceRepository.findById(id).orElse(null);
        if (annonce != null) {
            annonceRepository.delete(annonce);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Annonces d'un utilisateur par son ID
    @GetMapping("/par-utilisateur/{utilisateurId}")
    public List<Annonce> getAnnoncesParUtilisateur(@PathVariable int utilisateurId) {
        return annonceRepository.findByUtilisateur_Id(utilisateurId);
    }

    // Annonces d'une commune
    @GetMapping("/par-commune/{commune}")
    public List<Annonce> getAnnoncesParCommune(@PathVariable String commune) {
        return annonceRepository.findByCommune(commune);
    }
   
        @PutMapping("/{id}/updateStatut")
        public ResponseEntity<Annonce> modifierStatutAnnonce(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
            Annonce annonceExistante = annonceRepository.findById(id).orElse(null);

            if (annonceExistante != null) {
                String statut = requestBody.get("statut");
                annonceExistante.setStatut(statut);

                Annonce annonceMiseAJour = annonceRepository.save(annonceExistante);
                return new ResponseEntity<>(annonceMiseAJour, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        @GetMapping("/category-count")
        public ResponseEntity<Map<String, Long>> getCategoryCounts() {
            List<Object[]> categoryCounts = annonceRepository.countAnnouncementsByCategory();
            Map<String, Long> result = new HashMap<>();

            for (Object[] obj : categoryCounts) {
                Categorie categorie = (Categorie) obj[0];
                Long count = (Long) obj[1];
                result.put(categorie.getNom(), count);
            }

            return ResponseEntity.ok(result);
        }

            @GetMapping("/status-count")
            public ResponseEntity<Map<String, Long>> getStatusCounts() {
                List<Annonce> annonces = annonceRepository.findAll();
                Map<String, Long> statusCounts = new HashMap<>();

                for (Annonce annonce : annonces) {
                    String statut = annonce.getStatut();
                    statusCounts.put(statut, statusCounts.getOrDefault(statut, 0L) + 1);
                }

                return ResponseEntity.ok(statusCounts);
            }
}



