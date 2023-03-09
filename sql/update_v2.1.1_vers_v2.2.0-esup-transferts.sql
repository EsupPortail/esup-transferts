-- Executer ce script de migration uniquement si vous souhaitez passer de la version v2.1.0 vers la version 2.1.1 de l'application esup-transferts

SET DEFINE OFF; 

Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (13, '2014/2015');   

update wspub set mail_technique='a.definir@monmail.fr';   

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('choixDuVoeuParComposante', 1, 'Choix du voeu par composante si true ou diplome si false');

Insert into PARAMETRES
   (CODE, ETAT, COMMENTAIRE)
 Values
   ('maj_odf_auto', 0, 'Mise à jour automatique de l''offre de formation par rapport au scheduller');

COMMIT;
