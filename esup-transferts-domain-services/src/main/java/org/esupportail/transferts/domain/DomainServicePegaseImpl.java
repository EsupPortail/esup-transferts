package org.esupportail.transferts.domain;

import com.googlecode.ehcache.annotations.Cacheable;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.uphf.pegase.dto.*;
import fr.uphf.pegase.dto.fragments.Bac;
import fr.uphf.pegase.services.*;
import org.esupportail.transferts.domain.beans.*;
import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DomainServicePegaseImpl implements DomainServiceScolarite {

    private final PegaseInsApiService pegaseInsApiService;

    private final PegaseAuthApiService pegaseAuthApiService;

    private final PegaseRefApiService pegaseRefApiService;

    private final PegaseCocApiService pegaseCocApiService;

    private final PegaseMofApiService pegaseMofApiService;

    private String token;
    private LocalDateTime tokenExpirationDate;

    @Value("${pegase.api.username}")
    private String apiUsername;

    @Value("${pegase.api.password}")
    private String apiPassword;

    @Value("${pegase.api.structure}")
    private String apiStructure;

    @Value("${pegase.api.environment}")
    private String apiEnvironment;

    @Value("${pegase.api.periode}")
    private String apiPeriode;

    public DomainServicePegaseImpl() {
        this.pegaseInsApiService = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(PegaseInsApiService.class, String.format("https://ins.%s.pc-scol.fr/api/v5/ins", this.apiEnvironment));

        this.pegaseAuthApiService = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(PegaseAuthApiService.class, String.format("https://authn-app.%s.pc-scol.fr", this.apiEnvironment));

        this.pegaseRefApiService = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(PegaseRefApiService.class, String.format("https://ref.%s.pc-scol.fr/api/v1/ref", this.apiEnvironment));

        this.pegaseCocApiService = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(PegaseCocApiService.class, String.format("https://coc.%s.pc-scol.fr/api/coc/publication/v1", this.apiEnvironment));

        this.pegaseMofApiService = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(PegaseMofApiService.class, String.format("https://mof.%s.pc-scol.fr/api/v1/mof", this.apiEnvironment));
    }

    @Override
    public EtudiantRef getCurrentEtudiant(String supannEtuId) {
        EtudiantRef etudiant = new EtudiantRef();

        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenant(this.getToken(), this.apiStructure, supannEtuId);

        this.copyAttributes(etudiant, apprenant);

        // TODO: Blocage

        return etudiant;
    }

    @Override
    public EtudiantRef getCurrentEtudiantIne(String ine, Date dateNaissance) {
        EtudiantRef etudiant = new EtudiantRef();

        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenantByIne(this.getToken(), this.apiStructure, ine);

        if (apprenant.getNaissance().getDateDeNaissance() == null)
            return null;

        LocalDate birthDate = LocalDate.parse(apprenant.getNaissance().getDateDeNaissance());

        if (!birthDate.isEqual(dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
            return null;

        this.copyAttributes(etudiant, apprenant);

        // TODO: Blocage

        return etudiant;
    }

    @Override
    public List<TrCommuneDTO> getCommunes(String codePostal) {
        return this.pegaseRefApiService
                .getCommunes(this.getToken(), codePostal)
                .stream()
                .map(commune ->
                        new TrCommuneDTO(commune.getCodeInsee(), commune.getLibelleAffichage()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrPaysDTO> getListePays() {
        return this.pegaseRefApiService
                .getCountries(this.getToken())
                .stream()
                .map(pays ->
                        new TrPaysDTO(pays.getCodeIso3611(), pays.getLibelleAffichage(), pays.getLibelleNationalite()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrDepartementDTO> getListeDepartements() {
        return this.pegaseRefApiService
                .getDepartements(this.getToken())
                .stream()
                .map(departement ->
                        new TrDepartementDTO(departement.getCodeDepartementInsee2(), departement.getLibelleAffichage()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrEtablissementDTO> getListeEtablissements(String typeEtablissement, String dept) {
        String pegaseTypeEtablissement = this.getMappingTypeEtablissements().get(typeEtablissement);

        Stream<PegaseEtablissementDto> etablissements = this.getEtablissements()
                .stream()
                .filter(e -> e.getDepartement().getCode().equals(dept))
                .filter(e -> e.getTypeUai().getTypeUai().equals(pegaseTypeEtablissement));

        return etablissements
                .map(e -> new TrEtablissementDTO(e.getNumeroUai(), e.getLibelleAffichage()))
                .collect(Collectors.toList());
    }

    @Override
    public TrEtablissementDTO getEtablissementByRne(String rne) {
        List<PegaseEtablissementDto> etablissements = this.pegaseRefApiService
                .getEtablissementsByUai(this.getToken(), rne);

        if (etablissements.size() == 0)
            return null;

        PegaseEtablissementDto etablissement = etablissements.get(0);

        return new TrEtablissementDTO(
                rne,
                etablissement.getLibelleAffichage(),
                etablissement.getDepartement().getCode(),
                etablissement.getDepartement().getLibelleAffichage(),
                etablissement.getAcademieLibe() != null ? etablissement.getAcademieLibe() : etablissement.getAcademie(),
                etablissement.getLibelleAffichage(), // TODO: Est-ce que ça convient ? (getLibOffEtb côté Apogée)
                etablissement.getAdresseUai(),
                null,
                null,
                etablissement.getCodePostalUai(),
                etablissement.getAdresseUai() // TODO: Est-ce que ça convient ? (getLibAchAdrEtb côté Apogée)
        );
    }

    @Override
    public TrEtablissementDTO getEtablissementByDepartement(String dep) {
        return this.getEtablissements()
                .stream()
                .filter(e -> e.getDepartement().getCode().equals(dep))
                .findFirst()
                .map(e -> new TrEtablissementDTO(
                        e.getNumeroUai(),
                        e.getLibelleAffichage(),
                        e.getDepartement().getCode(),
                        e.getDepartement().getLibelleAffichage(),
                        e.getAcademie(),
                        e.getLibelleAffichage(), // TODO: Est-ce que ça convient ? (getLibOffEtb côté Apogée)
                        e.getAdresseUai(),
                        null,
                        null,
                        e.getCodePostalUai(),
                        e.getAdresseUai())) // TODO: Est-ce que ça convient ? (getLibAchAdrEtb côté Apogée)
                .orElse(null);
    }

    @Override
    public TrBac getBaccalaureat(String supannEtuId) {
        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenant(this.getToken(), this.apiStructure, supannEtuId);

        Bac bac = apprenant.getBac();

        String etablissement;

        if (bac.getPays().equals("100")) {
            etablissement = this.getEtablissementByDepartement(bac.getDepartement()).getLibAcademie();
        } else {
            etablissement = "ETRANGER";
        }

        return new TrBac(bac.getSerie(),
                bac.getLibelleSerie(),
                bac.getLibelleDepartement(),
                bac.getEtablissement(),
                bac.getAnneeObtention(),
                etablissement);
    }

    @Override
    public TrResultatVdiVetDTO getSessionsResultats(String supannEtuId, String source) {
        PegaseDossierDto dossier = this.pegaseInsApiService
                .getDossier(this.getToken(), this.apiStructure, supannEtuId);

        List<PegaseInscriptionDto> inscriptions = dossier.getInscriptions();

        TrResultatVdiVetDTO trResultatVdiVetDTO = new TrResultatVdiVetDTO();
        List<ResultatEtape> resultatsEtapes = new ArrayList<>();

        for (PegaseInscriptionDto inscription : inscriptions) {
            String chemin = inscription.getCible().getCode();

            List<PegaseResultatDto> resultats = this.pegaseCocApiService
                    .getResultats(this.getToken(), this.apiStructure, inscription.getCible().getPeriode().getCode(), supannEtuId, chemin);

            int max = "A".equals(source)
                    ? MAX_SESSIONS_RESULTAT_ACCUEIL
                    : MAX_SESSIONS_RESULTAT_DEPART;

            int start = Math.max(resultats.size() - max, 0);
            List<PegaseResultatDto> subset = resultats.subList(start, resultats.size());

            for (PegaseResultatDto resultat : subset) {
                List<ResultatSession> resultatsSessions = new ArrayList<>();

                ResultatSession session1 = new ResultatSession();

                session1.setLibSession("Session 1");
                session1.setResultat(resultat.getResultatSession1().getLibelleAffichage());
                session1.setMention(resultat.getMentionHonorifique().getLibelleAffichage());

                ResultatSession session2 = new ResultatSession();

                session2.setLibSession("Session 2");
                session2.setResultat(resultat.getResultatSession2().getLibelleAffichage());
                session2.setMention(resultat.getMentionHonorifique().getLibelleAffichage());

                resultatsSessions.add(session1);
                resultatsSessions.add(session2);

                ResultatEtape resultatEtape = new ResultatEtape();
                resultatEtape.setAnnee(inscription.getCible().getPeriode().getLibelleCourt().replace('-', '/'));
                resultatEtape.setLibEtape(inscription.getCible().getLibelleLong());
                resultatEtape.setSession(resultatsSessions);

                resultatsEtapes.add(resultatEtape);
            }
        }

        trResultatVdiVetDTO.setEtapes(resultatsEtapes);

        return trResultatVdiVetDTO;
    }

    @Override
    public IndOpi getInfosOpi(String numeroEtudiant) {
        PegasePistesDto pistes = this.pegaseInsApiService
                .getPistes(this.getToken(), this.apiStructure, numeroEtudiant);

        if (pistes.getResultats().size() == 0) {
            return null;
        }

        PegasePistesDto.Resultat resultat = pistes.getResultats().get(0);

        VoeuxIns voeuxIns = new VoeuxIns();
        IndOpi indOpi = new IndOpi();

        String codePaysNaissance = resultat.getNaissance().getNationalite();

        indOpi.setCodPayNat(codePaysNaissance);
        indOpi.setLibVilNaiEtuOpi(resultat.getNaissance().getLibelleCommuneDeNaissance());

        indOpi.setCodNneIndOpi(resultat.getBac().getIne());
        indOpi.setCodCleNneIndOpi("");
        indOpi.setDateNaiIndOpi(Date.from(LocalDate.parse(resultat.getNaissance().getDateDeNaissance())
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()));

        PegasePremieresInscriptionsDto premieresInscriptions = this.pegaseInsApiService
                        .getPremieresInscriptions(this.getToken(), resultat.getUuid());

        indOpi.setDaaEnsSupOpi(premieresInscriptions.getAnneeEnseignementSuperieur());
        indOpi.setDaaEntEtbOpi(premieresInscriptions.getAnneeEtablissement());

        // Données inexistantes dans Pégase ?
        // indOpi.setCodEtb(infoAdmEtuDTO.getEtbPremiereInscUniv().getCodeEtb());
        // indOpi.setDaaEntEtbOpi(infoAdmEtuDTO.getAnneePremiereInscUniv());
        // indOpi.setDaaEtrSup(infoAdmEtuDTO.getAnneePremiereInscEtr());

        indOpi.setLibNomPatIndOpi(resultat.getEtatCivil().getNomDeNaissance());
        indOpi.setLibNomUsuIndOpi(resultat.getEtatCivil().getNomUsuel());
        indOpi.setLibPr1IndOpi(resultat.getEtatCivil().getPrenom());
        indOpi.setLibPr2IndOpi(resultat.getEtatCivil().getDeuxiemePrenom());
        indOpi.setLibPr3IndOpi(resultat.getEtatCivil().getTroisiemePrenom());
        indOpi.setCodSexEtuOpi(resultat.getEtatCivil().getGenre());

        if (codePaysNaissance != null && !"100".equals(codePaysNaissance)) {
            indOpi.setCodDepPayNai(codePaysNaissance);
            indOpi.setCodTypDepPayNai("P");

        } else if (resultat.getNaissance().getCommuneDeNaissance() != null) {
            Integer departement = Integer.parseInt(resultat.getNaissance().getCommuneDeNaissance().substring(0, 2));

            indOpi.setCodDepPayNai(String.format("%03d", departement));
            indOpi.setCodTypDepPayNai("D");

        } else {
            indOpi.setCodDepPayNai("");
            indOpi.setCodTypDepPayNai("");
        }

        indOpi.setCodBac(resultat.getBac().getSerie());
        indOpi.setCodEtbBac(resultat.getBac().getEtablissement());
        indOpi.setCodDep(resultat.getBac().getDepartement());
        indOpi.setCodMnb(resultat.getBac().getMention());
        indOpi.setDaabacObtOba(resultat.getBac().getAnneeObtention());
        indOpi.setCodTpe(resultat.getBac().getTypeEtablissement());
        indOpi.setVoeux(voeuxIns);

        return indOpi;
    }

    @Override
    public TrPaysDTO getPaysByCodePays(String codePays) {
        return this.pegaseRefApiService
                .getCountries(this.getToken())
                .stream()
                .filter(p -> p.getCodeIso3611().equals(codePays))
                .findFirst()
                .map(p -> new TrPaysDTO(p.getCodeIso3611(), p.getLibelleAffichage(), p.getLibelleNationalite()))
                .orElse(null);
    }

    @Override
    public String getComposante(String supannEtuId) {
        PegaseDossierDto dossier = this.pegaseInsApiService
                .getDossier(this.getToken(), this.apiStructure, supannEtuId);

        Optional<PegaseInscriptionDto> inscription = dossier.getInscriptions()
                .stream()
                .filter(PegaseInscriptionDto::isPrincipale)
                .findFirst();

        if (!inscription.isPresent())
            return null;

        String code = inscription.get().getCible().getCode();

        return this.composanteFromCode(code);
    }

    @Override
    @Cacheable(cacheName = "getOffreDeFormation")
    public List<OffreDeFormationsDTO> getOffreDeFormation(String rne, Integer annee) {
        List<PegaseMinimalFormationDto> formations = this.pegaseMofApiService
                .getFormations(this.getToken(), this.apiStructure, this.apiPeriode);

        return formations
                .stream()
                .map(f -> {
                    Optional<PegaseTypeDiplome> typeDiplome = this.getTypesDiplomes()
                            .stream()
                            .filter(t -> t.getCode().equals(f.getCodeTypeDiplome()))
                            .findFirst();

                    PegaseFormationDto formation = this.pegaseMofApiService
                            .getFormation(this.getToken(), this.apiStructure, f.getCode(), f.getPeriode().getCode());

                    Integer niveau = Integer.valueOf(formation.getNiveauSise());
                    String libelleNiveau = niveau == 1 ? "1ère année" : String.format("%dème année", niveau);

                    return new OffreDeFormationsDTO(
                            rne,
                            annee,
                            typeDiplome.map(PegaseTypeDiplome::getCode).orElse(null),
                            typeDiplome.map(PegaseTypeDiplome::getLibelleAffichage).orElse(null),
                            f.getCode(),
                            f.getVersion(),
                            f.getCode(),
                            String.valueOf(f.getVersion()), // TODO: Est-ce correct ? (ve.getCodVrsVet() côté Apogée)
                            f.getLibelleLong(),
                            f.getLibelleLong(),
                            f.getStructureBudgetaire().getCodeUai(),
                            f.getStructureBudgetaire().getDenominationPrincipale(),
                            formation.getCodeStructurePrincipale(),
                            formation.getCodeStructurePrincipale(),
                            niveau,
                            libelleNiveau,
                            "oui",
                            "oui"
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<IndOpi> synchroOpi(List<IndOpi> listeSynchroScolarite) {
        return Collections.emptyList();
    }

    @Override
    public List<TrBac> recupererBacOuEquWS(String codeBac) {
        List<PegaseBacDto> bacs = this.pegaseRefApiService
                .getBacByCodeBcn(this.getToken(), codeBac);

        if (bacs.size() == 0)
            return null;

        List<TrBac> trBacs = new ArrayList<>();

        for (PegaseBacDto bac : bacs) {
            trBacs.add(new TrBac(bac.getCodeBcn(), bac.getLibelleAffichage()));
        }

        return trBacs;
    }

    @Override
    public List<PersonnelComposante> recupererComposante(String uid, String displayName, String mail, String source, Integer annee) {
        List<PegaseStructureDto> structures = this.pegaseRefApiService
                .getStructures(this.getToken());

        if (structures.size() == 0)
            return null;

        List<PersonnelComposante> composantes = new ArrayList<>();

        composantes.add(new PersonnelComposante(
                uid,
                "N.A",
                source,
                annee,
                displayName,
                mail,
                "Iconnue",
                0,
                "oui",
                "oui",
                "oui",
                "oui",
                "oui",
                "oui",
                "non",
                "non"
        ));

        for (PegaseStructureDto structure : structures) {
            composantes.add(new PersonnelComposante(
                    uid,
                    structure.getCode(),
                    source,
                    annee,
                    displayName,
                    mail,
                    structure.getAppellationOfficielle(),
                    0,
                    "oui",
                    "oui",
                    "oui",
                    "oui",
                    "oui",
                    "oui",
                    "non",
                    "non"
            ));
        }

        return composantes;
    }

    @Override
    public Integer getAuthEtu(String ine, Date dateNaissanceApogee) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenantByIne(this.getToken(), this.apiStructure, ine);

        return apprenant.getNaissance().getDateDeNaissance().equals(dateFormat.format(dateNaissanceApogee))
                ? 0
                : 1;
    }

    @Override
    public List<Composante> recupererListeComposantes(Integer annee, String source) {
        List<PegaseStructureDto> structures = this.pegaseRefApiService
                .getStructures(this.getToken());

        if (structures.size() == 0)
            return null;

        List<Composante> composantes = new ArrayList<>();

        composantes.add(new Composante("N.A", source, annee, "Inconnue", "non"));

        for (PegaseStructureDto structure : structures) {
            composantes.add(new Composante(
                    structure.getCode(),
                    source,
                    annee,
                    structure.getAppellationOfficielle(),
                    "non"
            ));
        }

        return composantes;
    }

    @Override
    public List<CGE> recupererListeCGE(Integer annee, String source) {
        // TODO: L'équivalent des centres de gestion ne semble pas exister dans Pégase
        List<CGE> list = new ArrayList<>();

        list.add(new CGE("N.A", source, annee, "Iconnue", "non"));

        list.addAll(this.pegaseRefApiService
                .getStructures(this.getToken())
                .stream()
                .map(s -> new CGE(s.getCode(), source, annee, s.getAppellationOfficielle(), "non"))
                .collect(Collectors.toList()));

        return list;
    }

    @Override
    public Map<String, String> getEtapePremiereAndCodeCgeAndLibCge(String supannEtuId) {
        PegaseDossierDto dossier = this.pegaseInsApiService
                .getDossier(this.getToken(), this.apiStructure, supannEtuId);

        Map<String, String> result = new HashMap<>();

        if (dossier.getInscriptions().size() > 0) {
            PegaseInscriptionDto inscription = dossier.getInscriptions().get(0);

            String codeComposante = inscription.getCible().getCode().substring(0, 1);
            String libelleComposante = this.composanteFromCode(codeComposante);

            result.put("libWebVet", inscription.getCible().getLibelleLong());
            result.put("codeCGE", codeComposante);
            result.put("libCGE", libelleComposante);
            result.put("codeComposante", codeComposante);
            result.put("libComposante", libelleComposante);

        } else {
            result.put("libWebVet", "Inconnue");
            result.put("codeCGE", "Inconnue");
            result.put("libCGE", "Inconnue");
            result.put("codeComposante", "Inconnue");
            result.put("libComposante", "Inconnue");
        }

        return result;
    }

    @Override
    public IdentifiantEtudiant getIdentifiantEtudiantByIne(String codNneIndOpi, String codCleNneIndOpi) {
        String ine = codNneIndOpi + codCleNneIndOpi;

        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenantByIne(this.getToken(), this.apiStructure, ine);

        IdentifiantEtudiant identifiantEtudiant = new IdentifiantEtudiant();

        identifiantEtudiant.setCodEtu(Integer.parseInt(apprenant.getCode()));
        identifiantEtudiant.setNumeroIne(ine);

        // TODO: Vérifier si ça convient ; il ne semble pas y avoir d'équivalent Pégase
        identifiantEtudiant.setCodInd(Integer.parseInt(apprenant.getCode()));

        return identifiantEtudiant;
    }

    @Override
    public TrInfosAdmEtu getInfosAdmEtu(String supannEtuId) {
        PegaseApprenantDto apprenant = this.pegaseInsApiService
                .getApprenant(this.getToken(), this.apiStructure, supannEtuId);

        return new TrInfosAdmEtu(
                apprenant.getNaissance().getNationalite(),
                apprenant.getNaissance().getLibelleNationalite());
    }

    @Override
    public List<EtudiantRef> recupererListeEtudiants(String annee, String codeDiplome, String versionDiplome, String codeEtape, String versionEtape) {
        String periode = String.format("PER-%s", annee);

        PegaseInscriptionsDto inscriptions = this.pegaseInsApiService
                .getInscriptions(this.getToken(), this.apiStructure, periode, codeEtape);

        return inscriptions.getResultats()
                .stream()
                .map(r -> {
                    EtudiantRef etudiantRef = new EtudiantRef();
                    etudiantRef.setNumeroEtudiant(r.getMeta().getCodeApprenant());

                    return etudiantRef;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(cacheName = "etablissements")
    public List<PegaseEtablissementDto> getEtablissements() {
        return this.pegaseRefApiService
                .getEtablissements(this.getToken());
    }

    @Cacheable(cacheName = "typesEtablissements")
    public Map<String, String> getMappingTypeEtablissements() {
        Map<String, String> mapping = new HashMap<>();

        mapping.put("04", "ING");  // École d'ingénieur
        mapping.put("05", "PRSU"); // Etablissements privés d'enseignement universitaire
        mapping.put("16", "UNIV"); // Université
        mapping.put("15", "PBAC"); // Autres écoles post-bac non universitaires
        mapping.put("17", "CNED"); // Centres d'enseignement à distance

        return mapping;
    }

    @Cacheable(cacheName = "typesDiplomes")
    public List<PegaseTypeDiplome> getTypesDiplomes() {
        return this.pegaseRefApiService
                .getTypesDiplomes(this.getToken());
    }

    private String composanteFromCode(String code) {
        // TODO: Mettre à jour quand Pégase offrira une interface pour récupérer
        // la composante.
        if (code.startsWith("A"))
            return "INSA HdF";
        if (code.startsWith("B"))
            return "INSA";
        if (code.startsWith("E"))
            return "Pôle formations";
        if (code.startsWith("H"))
            return "ISH";
        if (code.startsWith("I"))
            return "IFSI";
        if (code.startsWith("M"))
            return "INSPE";
        if (code.startsWith("O"))
            return "École doctorale";
        if (code.startsWith("T"))
            return "IUT";

        return null;
    }

    private void copyAttributes(EtudiantRef etudiant, PegaseApprenantDto apprenant) {
        AdresseRef adresse = new AdresseRef();

        InfosAccueil infosAccueil = new InfosAccueil();

        Transferts transfert = new Transferts();

        etudiant.setFrom("PEGASE");
        etudiant.setTransferts(transfert);
        etudiant.setNumeroEtudiant(apprenant.getCode());

        transfert.setNumeroEtudiant(etudiant.getNumeroEtudiant());

        // Etat civil
        etudiant.setNumeroIne(apprenant.getBac().getIne());
        etudiant.setNomPatronymique(apprenant.getEtatCivil().getNomDeNaissance());
        etudiant.setNomUsuel(apprenant.getEtatCivil().getNomUsuel());
        etudiant.setPrenom1(apprenant.getEtatCivil().getPrenom());
        etudiant.setPrenom2(apprenant.getEtatCivil().getDeuxiemePrenom());

        if (apprenant.getNaissance().getDateDeNaissance() != null)
            etudiant.setDateNaissance(Date.from(LocalDate.parse(apprenant.getNaissance().getDateDeNaissance())
                    .atStartOfDay(ZoneOffset.UTC)
                    .toInstant()));

        etudiant.setLibNationalite(apprenant.getNaissance().getLibelleNationalite());

        PegaseApprenantDto.Contact pegaseAdresse = apprenant.getContacts().stream()
                .filter(c -> c.getDemandeDeContact().getCode().equals("ADR-001"))
                .findFirst()
                .orElse(null);

        PegaseApprenantDto.Contact pegaseTelephone = apprenant.getContacts().stream()
                .filter(c -> c.getDemandeDeContact().getCode().equals("TEL-002"))
                .findFirst()
                .orElse(null);

        PegaseApprenantDto.Contact pegaseMail = apprenant.getContacts().stream()
                .filter(c -> c.getDemandeDeContact().getCode().equals("MEL-001"))
                .findFirst()
                .orElse(null);

        // Adresse
        if (pegaseAdresse != null) {
            adresse.setNumeroEtudiant(etudiant.getNumeroEtudiant());
            adresse.setLibAd1(pegaseAdresse.getLigne1OuEtage());
            adresse.setLibAd2(pegaseAdresse.getLigne2OuBatiment());
            adresse.setLibAd3(pegaseAdresse.getLigne3OuVoie());
            adresse.setNumTelPortable(pegaseAdresse.getTelephone());

            adresse.setCodPay(pegaseAdresse.getPays().toString());
            adresse.setLibPay(pegaseAdresse.getLibellePays());

            if (pegaseAdresse.getPays().equals(100)) {
                adresse.setCodePostal(pegaseAdresse.getCodePostal());
                adresse.setCodeCommune(pegaseAdresse.getCommune());
            } else {
                adresse.setCodePostal("");
                adresse.setCodeCommune("");
            }
        }

        // Téléphone
        if (pegaseTelephone != null) {
            adresse.setNumTel(pegaseTelephone.getTelephone());
        }

        // Courriel
        if (pegaseMail != null) {
            adresse.setEmail(pegaseMail.getMail());
        }

        infosAccueil.setAnneeBac(apprenant.getBac().getAnneeObtention());
        infosAccueil.setCodeBac(apprenant.getBac().getSerie());
        infosAccueil.setCodePaysNat(apprenant.getNaissance().getNationalite());

        etudiant.setAdresse(adresse);
        etudiant.setTransferts(transfert);
        etudiant.setAccueil(infosAccueil);
    }

    /*
     * Sachant qu'un token a une durée de vie de 8 heures et qu'il peut être
     * chronophage d'en demander un, on retient la valeur pour une durée de
     * 7h30 afin d'éviter d'arriver à expiration au beau milieu d'une action
     * qui s'effectuerait en plusieurs requêtes (et qui serait
     * longue, elle aussi).
     */
    private String getToken() {
        if (this.token != null && LocalDateTime.now().minusHours(1).isBefore(tokenExpirationDate))
            return this.token;

        PegaseCredentialsDto credentials = new PegaseCredentialsDto(
                this.apiUsername,
                this.apiPassword,
                true
        );

        String token = this.pegaseAuthApiService.getToken(credentials);

        this.token = token;
        this.tokenExpirationDate = LocalDateTime
                .now()
                .plusHours(7)
                .plusMinutes(30);

        return token;
    }
}
