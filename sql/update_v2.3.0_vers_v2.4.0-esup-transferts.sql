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
   ('relance_depart_sva', 0, 'Envoi mail aux personnels concernés (loi SVA) pour les transferts départ');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('relance_accueil_sva', 0, 'Envoi mail aux personnels concernés (loi SVA) pour les transferts accueil');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('relance_resume_sva', 0, 'Envoi mail qui résume toutes les demndes de transferts soumis à la SVA');

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (15, '2016/2017');

Update WSPUB set choix_du_voeu_par_composante=0;

Update VERSIONS set ETAT=0 WHERE NUMERO = '2.3.0';

Insert into VERSIONS (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.4.0', '', 1);

update PERSONNEL_COMPOSANTE set ALERT_MAIL_DEMANDE_TRANSFERT='non', ALERT_MAIL_SVA='non';

update hibernate_sequences set SEQUENCE_NEXT_HI_VALUE=1 where SEQUENCE_NAME='SEQUENCE_OPI';
delete from SEQUENCE_OPI;

commit;