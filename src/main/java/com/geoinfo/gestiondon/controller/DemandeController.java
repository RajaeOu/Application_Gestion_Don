package com.geoinfo.gestiondon.controller;

import com.geoinfo.gestiondon.model.Annonce;
import com.geoinfo.gestiondon.model.Demande;
import com.geoinfo.gestiondon.model.Utilisateur;
import com.geoinfo.gestiondon.repository.AnnonceRepository;
import com.geoinfo.gestiondon.repository.DemandeRepository;
import com.geoinfo.gestiondon.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demandes")
public class DemandeController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private DemandeRepository demandeRepository;

    @GetMapping
    public List<Demande> getDemandes() {
        return (List<Demande>) demandeRepository.findAll();
    }
    @GetMapping("/nombre")
    public Long getNombreDemande() {
        return demandeRepository.count();
    }

    @GetMapping("/{id}")
    public Demande getDemandeById(@PathVariable("id") int id) {
        return demandeRepository.findById(id).get();
    }

    @GetMapping("/demande/annonce/{idAnnonce}")
    public List<Demande> getDemandesByAnnonceId(@PathVariable("idAnnonce") int idAnnonce) {
        List<Demande> demandes = demandeRepository.findByAnnonceId(idAnnonce);
        return demandes;
    }

    @GetMapping("/demande/utilisateur/{idUtilisateur}")
    public List<Demande> getDemandesByUtilisateurId(@PathVariable("idUtilisateur") int idUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
        if (utilisateur != null) {
            return demandeRepository.findByUtilisateur(utilisateur);
        } else {
            return Collections.emptyList();
        }
    }

    @PostMapping("/traiterDemande")
    public ResponseEntity<String> traiterDemande(@RequestBody Demande demande) {
        // Récupérer l'ID de l'annonce depuis la demande
        int id_ann = demande.getAnnonce().getId(); // Assurez-vous que cette méthode existe dans votre modèle Demande pour récupérer l'ID de l'annonce

        // Récupérer l'annonce correspondante
        Optional<Annonce> annonceOpt = annonceRepository.findById(id_ann);

        if (annonceOpt.isPresent()) {
            Annonce annonce = annonceOpt.get();
            
            // Récupérer la quantité demandée
            int qteDemandee = demande.getQuantite();

            // Vérifier si la quantité demandée est inférieure ou égale à la quantité actuelle de l'annonce
            if (qteDemandee <= annonce.getQuantite()) {
                // Mettre à jour la quantité actuelle de l'annonce
                annonce.setQuantite(annonce.getQuantite() - qteDemandee);
                annonceRepository.save(annonce);

                // Enregistrer la demande dans la base de données
                demandeRepository.save(demande);

                return ResponseEntity.ok("Demande traitée avec succès.");
            } else {
                return ResponseEntity.badRequest().body("Quantité demandée supérieure à la quantité actuelle.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/ajouter")
    public void ajouterDemande(@RequestBody Demande demande) {
        demandeRepository.save(demande);
    }

    @PutMapping("/modifier/{id}")
    public void modifierDemande(@PathVariable("id") int id, @RequestBody Demande demande) {
        Demande existingDemande = demandeRepository.findById(id).orElse(null);
        if (existingDemande != null) {
            existingDemande.setUtilisateur(demande.getUtilisateur());
            existingDemande.setAnnonce(demande.getAnnonce());
            existingDemande.setQuantite(demande.getQuantite());
            existingDemande.setDate(demande.getDate());
            existingDemande.setStatut(demande.getStatut());

            // Enregistrez la demande mise à jour dans la base de données
            demandeRepository.save(existingDemande);
        }
    }

    @DeleteMapping("/supprimer/{id}")
    public void supprimerDemande(@PathVariable("id") int id) {
        demandeRepository.deleteById(id);
    }
}
