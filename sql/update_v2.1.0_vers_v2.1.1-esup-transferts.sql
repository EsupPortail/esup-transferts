-- Executer ce script de migration uniquement si vous souhaitez passer de la version v2.1.0 vers la version 2.1.1 de l'application esup-transferts

SET DEFINE OFF; 

-- Actually, according to the JPA specification it is forbidden to change a primary key:
-- The application must not change the value of the primary key[8]. The behavior is undefined if this occurs.[9]
-- (from E

/* ALTER TABLE offre_formations
DROP PRIMARY KEY;

ALTER TABLE offre_formations
ADD CONSTRAINT constraint_name PRIMARY KEY (COD_RNE,COD_ANU,COD_DIP,COD_VRS_VDI,COD_ETP,COD_VET,COD_CGE,COD_COMP);
*/

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('choixDuVoeuParComposante', 1, 'Choix du voeu par composante si true ou diplome si false');

Insert into VERSIONS
   (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.1.1', '- Ajout du libellé de la composante dans le récapitulatif de la demande de transfert coté départ
- Ajout d''un nouvel onglet administration afin d''éviter de créer de nouvelle propriété dans les fichiers properties
- Ajout d''un nouveau parametre dans la partie administration : "Choix de l''affichage du voeu par composante" qui conditionne l''affichage de la selection du veu coté transfert départ (etudiant et gestionnaire) soi par diplôme, soi par composante
- Ajout d''un nouveau bouton de suppression des données OPI', 1); 
   
COMMIT;
