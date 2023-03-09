
Insert into ACCUEIL_ANNEE
   (ID_ACCUEIL_ANNEE, LIBELLE)
 Values
   (17, '2018/2019');

Update VERSIONS set ETAT=0 WHERE NUMERO = '2.5.0';

Insert into VERSIONS (NUMERO, COMMENTAIRE, ETAT)
 Values
   ('2.6.0', '', 1);

commit;
