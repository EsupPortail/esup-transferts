# ESUP Transferts

Ce dépôt contient les sources du projet Transferts, permettant de dématérialiser
les demandes de transfert de dossiers étudiants.

## Lancement de l'application

Pour compiler les sources, il vous faut créer les fichiers suivants (un fichier
`-exemple` existe dans chacun des cas) :

* `esup-transferts-web-jsf-servlet/src/main/resources/properties/config.properties` :
  Permet notamment de spécifier l'URL vers les webservices Apogée (nécessaire
  pour la compilation), les paramètres de connexion à la base de données ainsi
  que le RNE de l'établissement.
* `esup-transferts-web-jsf-servlet/src/main/resources/log4j.properties` : les
  paramètres liés à Log4j, notamment le fichier de destination des logs.

### Pour Apogée

Pour compiler les sources et générer le WAR :

```console
$ mvn clean install
```

### Pour Pégase

Si vous souhaitez utiliser la version faisant appel à Pégase, il faut en premier
lieu modifier le fichier `esup-transfert-web-jsf-servlet/src/main/properties/cache/cache.xml`
avec :

```diff
-<bean id="siscol" class="org.esupportail.transferts.domain.DomainServiceApogeeImpl"/>
+<bean id="siscol" class="org.esupportail.transferts.domain.DomainServicePegaseImpl"/>
```

Puis compiler les sources et générer le WAR :

```console
$ mvn clean install -Ppegase
```

Pour lancer l'application en local avec Jetty :

```console
$ mvn -pl esup-transferts-web-jsf-servlet jetty:run
```
