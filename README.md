# GSB-Application

### L'entreprise

Le  laboratoire  Galaxy  SwissBourdin  (GSB)  est  issu  de  la  fusion  entre  le  géant  américain Galaxy  (spécialisé  dans  le  secteur des  maladies  virales  dont le  SIDA  et  les  hépatites) et  le conglomérat européen Swiss Bourdin (travaillant sur des médicaments plus conventionnels), lui même déjà union de trois petits laboratoires.En 2009, les deux géants pharmaceutiques ont uni leurs forces pour créer un leader de ce secteur  industriel.  L'entité  Galaxy  Swiss  Bourdin  Europe  a  établi  son  siège  administratif  à Paris. Le siège social de la multinationale est situé à Philadelphie, Pennsylvanie, aux Etats-Unis.

## Contexte  

Continuation du  PPE (Projet Personnel Encadré) 3 sous format d’une application Android.

Chaque employé visiteur médical est chargé de rendre visite à des praticiens.
Lors de chaque visite, il peut laisser des échantillons de médicaments, ce qui génère un coût de visite. 

De plus, il doit enregistrer un compte rendu de chaque visite, qui précise des demandes spécifiques du praticien, 
un suivi à réaliser. Chaque visiteur médical accèdera sur son mobile aux visites à réaliser dans la journée. Il devra
saisir les informations concernant ses visites, et renvoyer ces éléments pour mise à jour dans la base centrale.

## Principale(s) activité(s) concernée(s)

* A1.1.1 Analyse du cahier des charges d'un service à produire
* A1.1.2 Étude de l'impact de l'intégration d'un service sur le système informatique
* A1.1.3 Étude des exigences liées à la qualité attendue d'un service
* A1.2.4 Détermination des tests nécessaires à la validation d'un service
* A1.2.5 Définition des niveaux d'habilitation associés à un service
* A1.3.1 Test d'intégration et d'acceptation d'un service
* A1.3.4 Déploiement d'un service
* A1.4.1 Participation à un projet
* A1.4.3 Gestion des ressources
* A2.1.2 Évaluation et maintien de la qualité d'un service
* A4.1.1 Proposition d'une solution applicative
* A4.1.2 Conception ou adaptation de l'interface utilisateur d'une solution applicative
* A4.1.3 Conception ou adaptation d'une base de données
* A4.2.3 Réalisation des tests nécessaires à la mise en production d'éléments mis à jour

## Conditions de réalisation (ressources fournies, résultats attendus)

*Ressources fournies*:
* Cahier des charges de la mission

_Environnement_: Android  
_SGBD_: SQLite  
_IDE_: Android Studio  
_Dépendances_:  
+ DBFlow
+ Android Support Library

## Fonctionnalités attendue

+ Système d’authentification
+ Gestion des visites (médicaments laissés, compte rendu)
+ Création/Modification/Suppression d’un personnel (Réservé au role DELEGUE)
+ Création/Modification/Suppression d’un praticien (Réservé au role DELEGUE)
+ Création/Modification/Suppression d’un médicament en stock
+ Création/Modification/Suppression d’une activité complémentaire


## Diagramme des cas d’utilisation

![Diagram](https://i.imgur.com/Iutxxqo.png)