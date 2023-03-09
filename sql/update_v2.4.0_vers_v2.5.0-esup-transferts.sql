
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (16, '2017/2018');

Update VERSIONS set ETAT=0 WHERE NUMERO = '2.4.0';

Insert into VERSIONS (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.5.0', '', 1);

commit;