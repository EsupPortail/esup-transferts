-- Executer ce script de migration uniquement si vous souhaitez passer de la version v2.2.0 vers la version 2.3.0 de l'application esup-transferts

DROP SEQUENCE AVIS_SEQ;

DROP SEQUENCE DECISION_SEQ;

DROP SEQUENCE OPI_SEQ;


alter table 
   OFFRE_FORMATIONS
modify 
( 
   ACTIF    integer
);


alter table 
   IND_OPI
modify 
( 
   SYNCHRO    integer
);

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('planning_fermetures', 0, 'Choix de l''affichage et gestion manuelle ou automatique des périodes de fermeture');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('ajout_etablissement_manuellement', 0, '');

Update VERSIONS set ETAT=0;
Insert into VERSIONS (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.3.0', '', 1);

Update PERSONNEL_COMPOSANTE set DROIT_AVIS_DEFINITIF='oui';

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (14, '2015/2016');

insert into HIBERNATE_SEQUENCES values ('ACCUEIL_DECISION', 0);
update HIBERNATE_SEQUENCES set SEQUENCE_NEXT_HI_VALUE=(select max(id+1) from ACCUEIL_DECISION) where SEQUENCE_NAME='ACCUEIL_DECISION';

insert into HIBERNATE_SEQUENCES values ('AVIS', 0);
update HIBERNATE_SEQUENCES set SEQUENCE_NEXT_HI_VALUE=(select max(id+1) from AVIS) where SEQUENCE_NAME='AVIS';

COMMIT;