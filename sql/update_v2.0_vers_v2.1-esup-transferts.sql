-- Executer ce script de migration uniquement si vous souhaitez passer de la version v2.0 vers la version 2.1 de l'application esup-transferts

SET DEFINE OFF; 

update personnel_composante set annee=2013 where annee is null;

update offre_formations set depart='oui', arrivee='oui' where cod_anu=2013;

update ind_opi set source='D' where annee=2013;

update personnel_composante set droit_avis='oui', droit_decision='oui', droit_edition_pdf='oui', droit_suppression='oui', droit_deverrouiller='oui' where annee=2013;

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('informationAccueil', 1, 'informationAccueil');
Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('informationDepart', 1, 'informationDepart'); 

Insert into ACCUEIL_RESULTAT
   (ID_ACCUEIL_RESULTAT, LIBELLE)
 Values
   (0, 'null');  

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (0, 'null');      

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (12, '2013/2014');     
   
update infos_accueil ia set from_source='L';
   
-- Actually, according to the JPA specification it is forbidden to change a primary key:
-- The application must not change the value of the primary key[8]. The behavior is undefined if this occurs.[9]
-- (from E

ALTER TABLE personnel_composante
DROP PRIMARY KEY;

ALTER TABLE personnel_composante
ADD CONSTRAINT constraint_name PRIMARY KEY (cod_cmp, from_source, identifiant, annee);

DROP TABLE interdit_bu;

DROP TABLE etablissement;

COMMIT;
