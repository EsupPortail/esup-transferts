Insert into ETAT_DOSSIER
   (ID_ETAT_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (1, 'C', 'Complet');
Insert into ETAT_DOSSIER
   (ID_ETAT_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (2, 'I', 'Incomplet');

Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (1, 'AD', 'Avis défavorable');
Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (2, 'AF', 'Avis favorable');
Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (3, 'AUFR', 'En attente avis d''UFR');
Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (4, 'AS', 'En attente de signature');
Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (5, 'AV', 'En attente de validation');
Insert into DECISION_DOSSIER
   (ID_DECISION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (6, 'AP', 'En attente de pièces');

Insert into LOCALISATION_DOSSIER
   (ID_LOCALISATION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (1, 'E', 'Envoyé à l''étudiant');
Insert into LOCALISATION_DOSSIER
   (ID_LOCALISATION_DOSSIER, LIB_COURT, LIB_LONG)
 Values
   (2, 'Scol', 'Service Scolarité');
   
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('date_export_bu', 1, '12/09/2012 22:00:17');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ouvertureAccueil', 1, 'Bla bla bla accueil');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ouvertureDepart', 1, 'L''application Transfert sera ouverte du XX.XX.XXXX au XX.XX.XXXX <br/><br/>Merci de votre compréhension.<br/>');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('informationAccueil', 1, 'informationAccueil');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('informationDepart', 1, 'informationDepart'); 
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('choixDuVoeuParComposante', 1, 'Choix du voeu par composante si true ou diplome si false');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('maj_odf_auto', 0, 'Mise à jour automatique de l''offre de formation par rapport au scheduller');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('planning_fermetures', 0, 'Choix de l''affichage et gestion manuelle ou automatique des périodes de fermeture');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ajout_etablissement_manuellement', 0, 'RNE@Libellé,RNE@libellé');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ws_bu', 0, 'Acces au WebService de la BU');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ws_candidatures', 0, 'Acces au WebService candidatures');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ws_postbac', 0, 'Acces au WebService postbac');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('reload_demande_transferts_depart_echec_auto', 0, 'Relance automatique des demandes de transferts départ en echec');

   Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('reload_demande_transferts_accueil_echec_auto', 0, 'Relance automatique des demandes de transferts accueil en echec');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('time_out_connexion_ws', 1, '5000');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('super_gestionnaire', 0, '');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('relance_resume_sva', 0, 'Envoi mail qui résume toutes les demndes de transferts soumis à la SVA');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('relance_depart_sva', 0, 'Envoi mail aux personnels concernés (loi SVA) pour les transferts départ');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('relance_accueil_sva', 0, 'Envoi mail aux personnels concernés (loi SVA) pour les transferts accueil');

Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (0, 'null');   
Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (2, 'Résultat inconnu');
Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (3, 'AJAC / Chevauchement');
Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (4, 'Ajourné');
Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (1, 'Admis');

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (0, 'null');   
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (11, '2012/2013');   
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (2, '2003/2004');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (3, '2004/2005');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (4, '2005/2006');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (5, '2006/2007');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (6, '2007/2008');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (7, '2008/2009');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (8, '2009/2010');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (9, '2010/2011');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (10, '2011/2012');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (1, '2002/2003');   
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (12, '2013/2014');   
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (13, '2014/2015');      
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (14, '2015/2016');
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (15, '2016/2017');

Update WSPUB set choix_du_voeu_par_composante=0;

Insert into VERSIONS (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.4.0', '', 1);

COMMIT;