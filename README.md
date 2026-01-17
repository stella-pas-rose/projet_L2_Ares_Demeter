# l2s4-projet-2025



# Equipe

- Damya BELAL
- Laeticia CHALAH
- Fatima ALMOHAMED ALSADOU
- Stella Rose MILLE

# Sujet

[Le sujet 2025](https://www.fil.univ-lille.fr/~varre/portail/l2s4-projet/sujet2025.pdf)

# Livrables

Les paragraphes concernant les livrables doivent être rempli avant la date de rendu du livrable. A chaque fois on décrira l'état du projet par rapport aux objectifs du livrable. Il est attendu un texte de plusieurs lignes qui explique la modélisation choisie, et/ou les algorithmes choisis et/ou les modifications apportées à la modélisation du livrable précédent.

Un lien vers une image de l'UML doit être fourni (une photo d'un diagramme UML fait à la main est suffisant).

## Livrable 1

On a choisie de faire une classe abstraite Tuile car pour les types de tuiles de type mer et terrestre doivent etre de meme type , c'est a dire de type Tuile. De plus ca n'a pas de sens de instancier un objet de type Tuile.

Ensuite on a crée une classe Terrestre et Sea  qui héritent de Tuile car les tuiles peuvent etre soit de type terrestre soit type mer.Les tuiles de type Terrestre n'ont pas les memes capacités et ainsi pas les meme méthodes et attributs que ceux de type mer.

Les tuiles de type Terrestre peuvent de quatre type différents.Chaque type a un constructeur different et peuvent produire des ressources différentes. 
On a donc choisie de modéliser chaque  type de tuiles par une classe qui lui correspond.Une classe foret , paturage , montagne et champs.Ces quatres classes vont hériter de la classe Terrestre et des ses méthodes et attributs car pour chaque tuiles les méthodes sont les memes a part le constructeur.Cela évite la répétition des méthodes.

On a choisie de faire une classe Position car dans la classe Board , on a besoin de faire appelle a des coordonnées d'une case du plateau régulièrement ce qui  peut encombrer le code.Pour éviter cette gene on a décidé de remplacer les tuples par une instance de la classe position.


On a décidé de modéliser le plateau , toutes ses methodes et tout ce qui le concerne dans une class Board. Pour ce qui est de l'algorithme de placement des tuiles on en as beaucoup discuté, notre première approche était de découpé le nombre total de tuiles terrestre sur le plateau (donc 1/3 des tuiles totales) afin de tout de suite les regroupé entre elle. On voulait formé les îles directement, en sachant par exemple qu'il y aurait 4 îles avec l'une de 2 tuiles, l'autres de 6, ect...
Cependant cette structure qui semblait efficace dans un premier temps est vite devenu compliqué en pensant au pseudo code. C'est pour cela que nous avons optées pour une méthode plus simple: 
on divise le nombre totale de tuile terrestre par deux et on les places de manière aléatoire. Ensuite pour s'assurer qu'elles aient toutes un voisins, on parcourt l'ensemble du plateau: 
si une tuile est seule, on lui ajoute un voisin.
De cette manière on respecte le caractère aléatoire mais aussi la consigne comme quoi **au moins** 2/3 des tuiles sont de types mer. Avec notre modélisation, 1/3 des tuiles totales ne sont pas obligatoirement placé (dans le cas où aléatoirement une tuile as été placé voisine d'une autre) permettant aussi de varier l'aspect du plateau.

On a choisie de modéliser les différents types de ressources dans une enum car les ressources peuvent etre uniquement de quatre types prédifinies( wood , sheep, wealth , ore).

On a choisie de faire une enum Directon pour pouvoir parcourir les 4 directions a partir d'une tuile et ainsi éviter les répétitions.


#### Description des méthodes utilisées dans Board.java  :


- **Constructeur Board(int width, int height)** :

   La méthode crée une grille Tuile[][] grid et y place des tuiles Sea pour chaque position. Le plateau est ensuite prêt a être modifié pour accueillir des tuiles terrestres

- **display()** :

  Affiche le plateau de manière lisible dans la console avec des symboles représentant chaque type de tuile. Une description est également fournie à la fin de l'affichage pour expliquer le choix des symboles


- **createBoard()** :

   Génère un plateau complet en exécutant deux étapes :

    1. **placeInitialeTiles()** : Place une première série de tuiles terrestres (moitié de 1/3 c'est a dire 1/6 ) de manière aléatoire

    2. **placeNeighboorEarthTiles()** : Cette méthode garantit que le plateau est bien rempli en respectant la règle selon laquelle 2/3 du plateau est constitué de mer et que les tuiles terrestres sont connectées entre elles ou proches les unes des autres


- **isEmpty(Position pos)** :

  La méthode retourne true si la case est vide (mer) et false si la case est occupée par une tuile terrestre cela permet de vérifier si une case peut accueillir une tuile terrestre

- **haveNeighbor(Position pos)** :

   La méthode examine les quatre directions (haut, bas, gauche, droite) autour de la position donnée pour voir si l'une des cases voisines est occupée par une tuile terrestre. Si c'est le cas, la méthode retourne true, sinon elle retourne false

- **put(Tuile t, Position pos)** :

   Cette méthode remplace la tuile existante à la position pos par la nouvelle tuile t, elle est utilisée pour ajouter des tuiles terrestres

- **randomPosition()** :

   La méthode génère des coordonnées aléatoires x et y, puis vérifie si la case à ces coordonnées est une mer si ce n'est pas le cas elle genere de nouvelles coordonnées jusqu'a trouver une case vide

- **tileNumber()** :

  La méthode utilise la formule (largeur * hauteur) / 3 pour déterminer le nombre de tuiles terrestres. Cela permet de respecter la contrainte selon laquelle 1/3 du plateau doit être composé de tuiles terrestres

- **randomTuile()** :

   La méthode utilise un générateur de nombres aléatoires pour choisir parmi les quatre types de tuiles terrestres qui sont stocké dans une hashmap 



#### Affichage du Plateau :

Au debut, on voulait afficher le plateau avec des symboles simples comme //\ pour montagne , ~ pour mer ... etc. on a commence a faire ca sur nos machines personnelles, et ca marchait bien pour les tests. mais apres on a vu que c’etait possible d’afficher des emojis dans le terminal, alors on a decide de changer les symboles par des emojis.

les emojis rendent le jeu plus beau a regarder et plus facile a comprendre. chaque type de tuile (mer, foret, paturage, montagne, champ) est represente par un emoji specifique comme suit :

Sea → 🌊
Foret → 🌳
Pasture → 🐑
Mountain → 🏔
Field → 🌸

### Voici un exemple d’affichage du plateau de dimension 8x8 :

![exemple d'affichage avec a =5 et b=5 :](/index/ExempleBoard.png "")




### Diagramme UML pour le Livrable1 :

![ l'uml complet du premier livrable](/index/UMLlivrable1.png "UML complet pour le premier livrable")

### Les commandes  : 

### 1.1 Compilation des sources du package game.tuile

javac -sourcepath src src/game/tuile/*.java -d classes

### 1.2 Compilation des sources du package game.util 

javac -sourcepath src src/game/util/*.java -d classes

### 1.2 Compilation des sources du package game

javac -sourcepath src src/game/*.java -d classes


### 2. Exécution de la classe principale

java -classpath classes game.Livrable1 a b 

ou a et b seront saisie par l'utilisateur (ils désignent les valeurs width et height du plateau)

### 3. Génération de la documentation Javadoc pour les packages game.tuile, game.util, game : 

javadoc -d docs -sourcepath src src/game/tuile/*.java

javadoc -d docs -sourcepath src src/game/util/*.java

javadoc -d docs -sourcepath src src/game/*.java


### 5.1 Compilation des tests du package game.tuile

javac -classpath junit-console.jar:classes test/game/tuile/*.java

### 5.2 Compilation des tests du package game.util

javac -classpath junit-console.jar:classes test/game/util/*.java

### 4. Execution des tests

java -jar junit-console.jar -classpath test:classes -scan-classpath

### 6.Créer les Archives JAR

jar cvfe livrable1.jar game.Livrable1 -C classes .

### 7.Exécuter les Archives JAR

java -jar livrable1.jar


### Atteinte des objectifs
On a réussi a générer un plateau de facon aléatoire qui respecte les règles suivantes :

- le plateau doit comporter au minimum deux tiers de tuiles de type mer.

- toutes les tuiles de type montagne, patûrage, champ ou forêt doivent au moins avoir une tuile adjacente qui n’est
pas de type mer.

### Difficultés restant à résoudre
On cherche encore en parallèle comment ajouter des couleurs au plateau, mais ce n'est pas forcément la priorité , c'est plus dans un but esthétique et pratique pour debugger plus facilement.  

## Livrable 2

On a choisie de modéliser les building avec une classe abstraite car ça n'a pas de sens de créer une instance Building.
Les objets héritant d'une classe Building ont un cout , donc on a crée un attribut cost qui est une hashmap qui associe tout les ressources que vas couter un Building a leur quantité.

On a crée ensuite une classe Port pour modéliser les ports , une classe Farm pour modéliser une ferme, une classe Army pour modéliser une armée.Cest trois classes héritent toutes de  la classe Building et ainsi de ses méthodes car elles ont toutes le meme comportement et ainsi nécessitent les memes méthodes.Army a quelque méthodes en plus que Port et Farm n'ont pas,donc on les a mis dans la classe Army. Parcontre Army a quelque méthodes en plus que Port et Farm n'ont pas.Donc on les a mis dans la classe Army.


Pour modéliser une Exploitation, on a crée une classe Exploitation qui héritent de Farm  car une ferme peut se tranformer en une exploitation si le joueur a le moyen ,que cela soit en ressource ou en nombre de guerriers,de le faire.

De meme , on a choisie de modéliser un camp en créant une classe Camp.Cette classe hérite de Army car une armée peut évoluer en un camp avec des ressources ou des guerriers. Ainsi Camp a les memes fonctionnaliés que Army c'est pourquoi elle hérite de ses méthodes.En plus des méthodes qu'elle hérite de Army , elle a des méthodes propre a elle.

Pour placer un port sur une tuile terrestre, on regarde si il y a au moins une tuile de type mer autour de la tuile considerée.Si oui on le place sinon on renvoie False.
Pour la classe Army ,comme le joueur ne peut pas avoir plus de 5 guerriers par armée , il fallait bien donc restreindre le nombre de guerrier quand le joueur veut on créer , pour cela on a crée une constante nbWarriorMax pour pourvoir le réustiliser plus tard dans le constructeur de Army.Si un joueur construit une armée avec plus de 5 guerriers alors on construit une armée avec 5 guerriers seulement.


On s'est rendu compte qu'on avait besoin de modéliser un joueur pour pouvoir écrire les méthodes de c'est classe ,on a donc créer la classe Player.Chaque player a un nom et a initiallement 30 guerriers et 0 ressources.

Comme il ya plusieurs fichier qui concerne des choses qu'on peut construire et qui hérite de la Classe Building .Ainsi pour avoir des fichiers mieux organiser , on a regroupé ces classes au sein d'un meme package Building.

Pour pourvoir gérer le fait qu'un joueur peut potentiellement vouloir acheter ou faire évoluer quelque chose alors que il n'a pas assez de ressources pour faire cela, on a crée une classe d'exception NoMoreRessourcesException.



### Les commandes  : 

### 1.1 Compilation des sources du package game.tuile

javac -sourcepath src src/game/tuile/*.java -d classes

### 1.2 Compilation des sources du package game.util 

javac -sourcepath src src/game/util/*.java -d classes

### 1.3 Compilation des sources du package game

javac -sourcepath src src/game/*.java -d classes

### 1.4 Compilation des sources du package game.tuile.building

javac -sourcepath src src/game/tuile/building*.java -d classes

### 2. Exécution de la classe principale

java -classpath classes game.Livrable2 a b 

ou a et b seront saisie par l'utilisateur (ils désignent les valeurs width et height du plateau et le minimum est 10)

### 3. Génération de la documentation Javadoc pour les packages game.tuile, game.util, game : 

javadoc -d docs -sourcepath src src/game/tuile/*.java

javadoc -d docs -sourcepath src src/game/util/*.java

javadoc -d docs -sourcepath src src/game/*.java

javadoc -d docs -sourcepath src src/game/tuile/building/*.java


### 5.1 Compilation des tests du package game.tuile

javac -classpath junit-console.jar:classes test/game/tuile/*.java

### 5.2 Compilation des tests du package game.util

javac -classpath junit-console.jar:classes test/game/util/*.java

### 5.2 Compilation des tests du package game.tuile.building

javac javac -classpath junit-console.jar:classes test/game/tuile/building*.java

### 4. Execution des tests

java -jar junit-console.jar -classpath test:classes -scan-classpath

### 6.Créer les Archives JAR

jar cvfe livrable2.jar game.Livrable2 -C classes .

### 7.Exécuter les Archives JAR

java -jar livrable2.jar


#### Affichage du Plateau :

 Légende des tuiles :

 S  : Sea

 F  : Forest

 P  : Pasture

 M  : Mountain

 C  : Field

 Légende des Batiments :

 a : Army

 c : Camp

 f : Farm

 e : Exploitation

 p : Port

### Voici un exemple d’affichage du plateau de dimension 10x10 :

![exemple d'affichage avec a =10 et b=10 :](/index/exempleBoard2.png "")




### Diagramme UML pour le Livrable2 :

![ l'uml complet du second livrable](/index/UMLLivrable2.png "UML complet pour le second livrable")
[mieux voir l'uml](https://lucid.app/lucidchart/38cc81c5-70a7-4213-a395-4649bfcfe868/edit?viewport_loc=546%2C-728%2C1467%2C646%2C0_0&invitationId=inv_a5bb74cc-7cd8-4e37-9fc7-9fcbdb7c63b1)

### Atteinte des objectifs
On peut maintenant placer des Building sur les tuiles Earth grâce à des ressources. Certains type de bâtiments peuvent évoluer en un autre: 
- une Army peut évoluer en Camp avec un certain nombre de ressources ou en ayant atteint son seuil de soldat maximum
- une Farm évolue en Exploitation en payant un certain nombre de ressource
De plus, le bâtiment Port ne peut être placer uniquement sur des tuiles voisines à a mer. 

### Difficultés restant à résoudre
Pour cause de problèmes technique les émojis ont disparuts de l'affichage. On aimerait les remettre avec aussi des émojis pour les bâtiments, cependant cela n'est pas la priorité. 

## Livrable 3

On a choisi de modéliser les actions en utilisant une interface Action qui dispose de la methode act() qui sera 
redéfini proprement à chaque action, cette méthode prend en paramètre un player de type paramétré <T> pour differencier les joueurs des actions associées au jeu Ares et au jeu Demeter 

De plus, on a choisi de rajouter une classe abstraite ActionManager qui est caracterisé par le coût de l'action et le type du joueur qui execute l'action. Cette classe dispose des methodes qui seront utiles dans la modélisation des actions qui nécessite un paiement (utilisation des ressources du joueur). Elle dispose des méthodes : 

hasEnoughRessources() qui verifie si le joueur a suffisament de ressources pour se permettre de payer le coût de l'action concernée

removeRessources() qui parcout le coût de l'action et et la soustrait des ressources du joueur

On a crée une classe Player qui sera commune pour PlayerAres et PlayerDemeter (héritage), un player est caractérisé par son nom, ses ressources, sa liste de ports, sa liste des tuiles sur les quelles il a construit ses batiments. Initialement ces listes sont vides, on jugeait que c'était pas nécessaire de rajouter un attribut liste des bâtiments pour le joueur car depuis sa liste de tuiles on peut récupérer ces bâtiments en question

PlayerAres hérite de Player, il dispose donc de ses attributs et méthodes de base. On a rajouté tout ce qui peut concerner un player du jeu Ares, c'est à dire son nombre de guerriers initialement à 30, son nombre d'armes secrète, sa liste d'armées , sa liste de camps , sa liste des actions..ect

PlayerDemeter hérite de Player, il dispose aussi de ses attributrs et méthodes de base. On a rajouté tout ce qui peut concerner un player du jeu Demeter, c'est à dire son nombre de points au cour du jeu, son nombre de voleurs, sa liste de fermes, sa lite d'exploitations , sa liste des actions.. ect

- Pour les actions qui nécessitent un paiement (utlisation des ressources du joueur) tel que BuySecretWeapon, BuyThief, BuyWarriors on vérifie si le joueur a suffisament de ressources pour l'action en question si c'est le cas on effectue l'action si ce n'est pas le cas on génère une erreur (throws NoMoreRessourcesException)

- Pour les actions qui nécessitent la construction des batiments (build) tel que BuildArmy, BuildFarm, BuildPort on vérifie également si le joueur a suffisament de ressources ainsi que d'autres conditions de construction (par exemple pour buildPort on verifie la condition de : le port ne peut être créé que sur une tuile voisine de la mer..ect )

- Pour les actions qui nécessitent les upgrades tel que UpgradeFarm et UpgradeArmy permettent respectivement d'améliorer une ferme en exploitation et une armée en camp. Elles héritent de ActionManager car elles nécessitent des ressources. UpgradeFarm consomme WOOD, WEALTH et SHEEP pour remplacer une ferme par une exploitation. UpgradeArmy offre deux options : payer en WOOD et ORE ou ajouter des guerriers. Dans les deux cas, le bâtiment initial est supprimé et remplacé par sa version améliorée. Si les conditions ne sont pas remplies, une exception est levée (NoMoreRessourcesException)

- Pour les actions qui nécessitent des échanges de ressources : ExchangeRessources permet à un joueur d'échanger 3 unités d'une ressource contre 1 d'une autre. ExchangeRessourcesPort, spécifique à PlayerDemeter, nécessite un port et échange 2 unités contre 1. Si les conditions ne sont pas remplies, des exceptions sont levées

 #### A propos de RandomListChooser :

 On a crée la classe RandomListChooser<T> qui permet de choisir un élément au hasard dans une liste, elle a deux méthodes principales :

choose(String msg, List<? extends T> list) : elle affiche un message et choisit aléatoirement un élément dans la liste si la liste est vide elle renvoie null

chooseCoordinate(String msg, Board board) : elle choisit une position aléatoire sur le plateau de jeu et vérifie si la position est valide et ou l'on peut construire

Ce choix de modélisation sert à simuler des choix automatiques pour le joueur comme choisir une ressource ou une position de bâtiment ou un nombre de guerriers à rajouter.. ect

### Les commandes  : 

### 1.1 Compilation des sources du package game.tuile

javac -sourcepath src src/game/tuile/*.java -d classes

### 1.2 Compilation des sources du package game.util 

javac -sourcepath src src/game/util/*.java -d classes

### 1.3 Compilation des sources du package game

javac -sourcepath src src/game/*.java -d classes

### 1.4 Compilation des sources du package game.tuile.building

javac -sourcepath src src/game/tuile/building/*.java -d classes

### 1.5 Compilation des sources du package game.action

javac -sourcepath src src/game/action/*.java -d classes

### 2.1 Exécution du livrable 3 Ares :

java -classpath classes game.Livrable3ares a b 

### 2.2 Exécution du livrable 3 Demeter :

java -classpath classes game.Livrable3demeter a b 

ou a et b seront saisie par l'utilisateur (ils désignent les valeurs width et height du plateau et le minimum est 10)

### 3. Génération de la documentation Javadoc pour les packages game.tuile, game.util, game, game.building, game.action: 

javadoc -d docs -sourcepath src src/game/tuile/*.java

javadoc -d docs -sourcepath src src/game/util/*.java

javadoc -d docs -sourcepath src src/game/*.java

javadoc -d docs -sourcepath src src/game/tuile/building/*.java

javadoc -d docs -sourcepath src src/game/action/*.java


### 5.1 Compilation des tests du package game.tuile

javac -classpath junit-console.jar:classes test/game/tuile/*.java

### 5.2 Compilation des tests du package game.util

javac -classpath junit-console.jar:classes test/game/util/*.java

### 5.2 Compilation des tests du package game.tuile.building

javac -classpath junit-console.jar:classes test/game/tuile/building*.java

### 5.3 Compilation des tests du package game.action

javac -classpath junit-console.jar:classes test/game/action/*.java

### 4. Execution des tests

java -jar junit-console.jar -classpath test:classes -scan-classpath

### 6.Créer les Archives JAR

jar cvfe livrable3ares.jar game.Livrable3ares -C classes .

jar cvfe livrable3demeter.jar game.Livrable3demeter -C classes .

### 7.Exécuter les Archives JAR

java -jar livrable3ares.jar

java -jar livrable3demeter.jar


### Diagramme UML pour le Livrable 3 :
![ l'uml des actions](/index/UMLAction2.png "UML des actions")

![ l'uml du listchooser](/index/UMLListchooser.png "UML du listchooser")

[l'uml des actions et du listchooser](https://lucid.app/lucidchart/5902e73f-4322-4aba-922a-fc3bb0dea9c7/edit?invitationId=inv_0bf26132-d019-489c-bff7-d16ef601bbf9)

l'uml des précédents livrable est toujours visible [ici](https://lucid.app/lucidchart/38cc81c5-70a7-4213-a395-4649bfcfe868/edit?invitationId=inv_a5bb74cc-7cd8-4e37-9fc7-9fcbdb7c63b1)


### Atteinte des objectifs

Les principaux objectifs du livrable 3 ont été réalisés, les actions de base comme l'échange de ressources, la construction de bâtiments et les améliorations de fermes et d'armées, l'achat de guerriers et de voleurs fonctionnent correctement pour les joueurs Ares et Demeter, la gestion des ressources et des conditions d'actions a été mise en place

### Difficultés restant à résoudre

- Il reste à améliorer l'action Attack Neighboor 

- Il reste à améliorer l'affichage des actions : il faudra plus tard afficher uniquement les actions que le joueur peut réellement effectuer

-  Le système de choix aléatoires pourrait être amélioré pour éviter les choix répétitifs

- L'affichage du plateau pourrait être amélioré en ajoutant des couleurs pour le rendre plus lisible

## Livrable 4

### Les commandes  : 

### 1 Compilation des sources 

make cls

### 2.1 Exécution du livrable 4 Ares :

java -classpath classes game.Ares a b c 

ou a,b et c seront saisie par l'utilisateur.
a et b représentent la hauteur et la largeur du plateau.
c représente le nombre de joueurs. 

### 2.1. Exécution du livrable 4 AresRandom :

java -classpath classes game.AresRandom a b c 

ou a,b et c seront saisie par l'utilisateur.
a et b représentent la hauteur et la largeur du plateau.
c représente le nombre de joueurs. 

### 2.2 Exécution du livrable 4 Demeter :

java -classpath classes game.Demeter a b c

ou a,b et c seront saisie par l'utilisateur.
a et b représentent la hauteur et la largeur du plateau.
c représente le nombre de joueurs. 


### 2.2 Exécution du livrable 4 DemeterRandom :
java -classpath classes game.DemeterRandom a b c

ou a,b et c seront saisie par l'utilisateur.
a et b représentent la hauteur et la largeur du plateau.
c représente le nombre de joueurs. 

### 3 Génération de la documentation Javadoc pour les packages game.tuile, game.util, game, game.building, game.action: 

make doc

### 4.1 Compilation des tests du package game.tuile

javac -classpath junit-console.jar:classes test/game/tuile/*.java

### 4.2 Compilation des tests du package game.util

javac -classpath junit-console.jar:classes test/game/util/*.java

### 4.3 Compilation des tests du package game.tuile.building

javac -classpath junit-console.jar:classes test/game/tuile/building*.java

### 4.4 Compilation des tests du package game.action

javac -classpath junit-console.jar:classes test/game/action/*.java

### 5 Execution des tests

java -jar junit-console.jar -classpath test:classes -scan-classpath

### 6 Créer les Archives JAR

make ares.jar

make demeter.jar

make aresRandom.jar

make demeterRandom.jar

### 7 Exécuter les Archives JAR

java -jar ares.jar

java -jar demeter.jar

java -jar aresRandom.jar

java -jar demeterRandom.jar

### 8 Nettoyer le dossier classes

make clean

### Diagramme UML pour le Livrable 4 :

![ l'uml ](/index/UMLFINAL1.PNG "UML")

![ l'uml ](/index/UMLFINAL2.PNG "UML")

![ l'uml ](/index/UMLFINAL3.PNG "UML")

![ l'uml ](/index/UMLFINAL4.PNG "UML")

![ l'uml ](/index/UMLFINAL5.PNG "UML")

![ l'uml ](/index/UMLFINAL6.PNG "UML")

![ l'uml ](/index/UMLFINAL7.PNG "UML")

![ l'uml ](/index/UMLFINAL8.PNG "UML")


### Affichage du plateau: 


#### exemple d’affichage du plateau de dimension 10x10 :

 Légende des tuiles :

 `rgb(12,18,88)` Sea 

 `rgb(13,88,12)` Forest

 `rgb(240, 179, 50)` Pasture

 `rgb(156,147,175)` Mountain

 `rgb(136,96,26)` Field

 Légende des Batiments :

 a : Army

 c : Camp

 f : Farm

 e : Exploitation

 p : Port

![exemple d'affichage avec a =10 et b=10 :](/index/Board.png "")


### Atteinte des objectifs
Les objectifs de ce livrable ont été atteint, la boucle de jeu est fonctionnel, on peut donc jouer à Ares et Demeter de manière interactive mais aussi en mode de jeu "random".
C'est à dire qu'après avoir donnée la dimension du plateau et le nombre de joueur, le jeu se déroule seul, à base d'actions aléatoire.
Lors de la partie, uniquement les actions possible à réaliser sont proposer au joueur, si un joueur commence une action et finalement décide de l'annuler en tapant "0" correspondant à none (par exemple su le choix de combien de guerriers placer ou sur quelle tuile construire...) on concidère qu'il soufaite passer son tour. 
De plus, dans toutes les actions de type "build" on proposeras les tuiles sur lesquelles on peut construire uniquement (avec l'ajout de la condition "près de la mer" pour la construction du port).
Si l'on veut ajouter des guerriers à une armées on ne pourras pas dépasser un effectif de 5. Une fois l'armée à 5 on pourras proposer de la faire évoluer en camps et enfin mettre plus de 5 guerriers. 
On as pris la décision de découper le choix de l'évolution d'une armée en camp en deux actions:
- UpgradeWithWarriors
- UpgradeWithRessources
afin de faciliter la prise de décission du joueur mais aussi de faire en sorte qu'on sache ce que l'on échange sans porter à confusion. 

### Difficultés restant à résoudre
Tout est fonctionnel cependant on rencontre des difficultés avec l'exécution de la doc. Effectivement les types du genre "List<List<int>>" semble poser des problèmes...
Cette section peut évoluer avant le rendu final selon ce que l'on trouve pour régler ce probléme. 

# Journal de bord

Le journal de bord doit être rempli à la fin de chaque séance encadrée, et avant de quitter la salle. 

Pour chaque semaine on y trouvera :
- ce qui a été réalisé, les difficultés rencontrées et comment elles ont été surmontées (on attend du contenu, pas uniquement une phrase du type "tous les objectifs ont été atteints")
- la liste des objectifs à réaliser d'ici à la prochaine séance encadrée

## Semaine 1

### Ce qui a été réalisé

- Début de la réfléxion sur la construction du plateau ainsi que la conception des tuiles
- Discussion autour de la mise en place de l'algorithme posant les tuiles aléatoirement
- Modélisation du diagramme UML pour la classe Plateau 

 ![ l'uml de la classe plateau et tuile](/index/uml_w1.png "UML semaine 1")

### Difficultés rencontrées
- La mise en place des tuiles sur le plateau de facon aléatoire 

### Objectifs pour la semaine

- Finalisation de l'algorithme permettant la mise en place des tuiles aléatoirement
- Modélisation du diagramme UML sur les classes Tuiles et Héritage sur les types (forêt , montagne , paturâge, champ)

## Semaine 2

### Ce qui a été réalisé
- Construction d'une énumeration Direction qui représentent les quatres directions possibles pour vérifier l'encadrement des tuiles.
- Creation d'une énumeration Ressource qui représente les 4 types de ressource
- Rajout de classes et amélioration du diagramme UML
- Réalisation du pseudo-code permettant la mise en place de la construction du plateau

 ![ ajout de enum et amélioration de l'héritage](/index/uml_w2.png "UML semaine 2")

  ![pseudo code construction plateau](/index/pseudoCode1.jpeg "pseudo-code semaine 2")


### Difficultés rencontrées
- Débat sur comment modéliser l'héritage des types de tuiles.

### Objectifs pour la semaine

- Création de la classe abstraite Tuile et la classe Sea et la classe Earth qui héritent de la classe Tuile.
- Création des classes Forest, Montain, Field, Pasture qui héritent de la classe Earth.

## Semaine 3

### Ce qui a été réalisé

- Organisation des packages
- Ecriture de la méthode randomTuile() qui permet de générer une Tuile aléatoirement
- Ecriture de la méthode placeHalfEarthTiles() qui permet de placer 1/6 (la moitie de 1/3) des tuiles terrestres
- Résolution des bugs concernant les commandes git (git stash git merge ect..)
- Discussion sur la répartition des taches pour la semaine pour la finalisation du plateau

### Difficultés rencontrées

- Synchronisation de Git
- Git Merge
- Configuration de vs code


### Objectifs pour la semaine
- Finalisation de la méthode placeNeighboorEarthTiles() qui permet de placer les 1/6 des tuiles terrestres restants en vérifiant le voisinage (Sera fait par : Mille Stella Rose)
- Création de la méthode creaeBoard() qui permet la creation du Plateau (Sera fait par : Fatima ALMOHAMED Alsadou)
- Création de la méthode display() qui permet l'affichage du plateau (Sera fait par : Laeticia Chalah)
- Création du fichier test BoardTest pour le fichier Board.java (Sera fait par : Belal Damya)

## Semaine 4

### Ce qui a été réalisé

- Modélisation du diagramme UML permettant la mise en place des classes Building, Army, Camp, Port, Farm, Exploitation 

- Discussion sur les héritages entre Army et Camp car il y'a une relation entre elles (Army peut évoluer en camp selon le nombre de guerriers, par exemple si ca dépasse 5 une armmée devient camp)

- Discussion sur les héritages entre Farm et Exploitation car il y'a une  entre elles (Un joueur peut faire évoluer les fermes en exploitations en utilisant des ressources)

- Réflexion sur le choix de constante pour la dimension des classes Farm et Exploitation car c'est des valeurs fixes 

### Difficultés rencontrées

- Remise en question sur la conception Building

### Objectifs pour la semaine

- Suite a la discussion avec le prof concernant le livrable1, on va procéder a quelques modifications 

- Finalisation du diagramme UML pour la modélisation des batiments

- Répartir les tâches entre les membres du groupe pour répartir la partie code 




![Building](/index/UML_Building.png "UML Building")

## Semaine 5

### Ce qui a été réalisé
Lors de cette séance, l'objectif était de mettre à plat l'UML afin de se mettre d'accord pour de bon sur la conception de Building. On as passé la séance au tableau à discuter et finalement compléter l'UML afin de pouvoir continuer le projets sans problèmes durant la pause pédagogique. 

### Difficultés rencontrées
- Lier la classe Tuile et Building afin de pouvoir récupérer les bâtiments facilement durant le display

- définition de la capacité (nottament sur ce qu'elle représente et si elle devait être une constante)

### Objectifs pour la semaine
- Avancer sur le code de la classe Building (+ correction de ce qui a déjà était fait)

- faire des modifications sur Tuile pour qu'elle prenne Building en attribut

![ l'uml BUILDING ](/index/Uml_building2.png "UML Buidling ")

## Semaine 6

### Ce qui a été réalisé
-Reflexion sur la modélisation de different action
-Reflexion sur les avantages d'utiliser une interface
-Ajout d'un attribut playerTiles pour pouvoir associer chaque joueur a ses tuiles pour faciliter l'utilisation de methodes 
-Création d'une interface et d'une classe pour chaque  d'action
-Reflexion sur la manière qu'on va utiliser pour relier une saisie de joueur a une action

### Difficultés rencontrées
-Association d'un saisie de joueur a une méthode
-Modélisation de differents actions
-discussion autour de l'impémentation/ représentation des îles
-élaboration de l'interface des actions



![ l'uml ACRTION ](/index/UMLAction.png "UML Action ")

### Objectifs pour la semaine
Commencer a coder les classes des actions.

## Semaine 7

### Ce qui a été réalisé
- entretien sur le livrable 2
- ajout de deux classes héritant de player pour pouvoir avoir des constructeurs et méthodes spécifiques au deux jeux
- ajout de la méthode canBuildArmy 


liste des réflexions et choses à modifier du livrable 2:
- ajouter tuile et player en paramètre de builduing
- modifier l'emplacement de la récupération des ressources
- pour chaque building, cost recrée une nouvelle hashmap, on doit supprimer les répétitions et juste l'initialiser dans buidling
- supprimer setBuilding et l'ajouter dans le constructeur
- ajouter canBeUpgrade dans building
- modifier le fonctionnement de create army (par exemple selon le nombre de soldat ajouté on pourrais levé une exception)

### Difficultés rencontrées
- On ne sait pas encore comment ne pas créer de conflit de type danc action avec la création des deux sous classes propre à chaque jeu, on doit donc se renseigner sur les types paramétrés (on as commencé à regarder la vidéo mais on as pas eu le temps de finir :()

### Objectifs pour la semaine
- ce concentreer sur le code pour rencontrer des problèmes et savoir ce qu'il faut revoir la semaine prochaine

## Semaine 8

### Ce qui a été réalisé
- écriture de différentes actions
- on essaye de faire les modifications demandé suite à l'entretien de la semaine dernière
- disccussion autour de explore island et de comment sont gérés les îles en général

### Difficultés rencontrées
- séparation de la modification et vérification de l'inventaire du joueur (ressourcesManager), on as eu du mal à séparer l'interface et la classe

### Objectifs pour la semaine
- Finir d'écrire les actions (même si ce n'est pas la forme final), pour se concentreer sur les $iles et les tests qui ne sont pas vraiment entamés pour le moment...


## Semaine 9

### Ce qui a été réalisé
- Réalisation de la classe RandomListChooser.java et implementation de celle-ci dans les actions : Par exemple dans BuildArmy on utilise celle-ci pour choisir une tuile aleatoirement pour construis l'armée car la saisie au clavier n'est pas autorisé pour le livrable 3

- Début de création des fichiers tests 

- Modification de PlayerAres et PlayerDemeter : ajout des methodes qui permettent de contruire la liste des actions pour chaque player et utilisation de celle-ci dans le constructeur

- Mise à jour de l'UML 

### Difficultés rencontrées

- Résolution de problèmes liées a ListChooser par rapport au typage

### Objectifs pour la semaine

- Finalisation d'écriture des fichiers tests 
- Finalisation d'écriture des livrables : Livrable3ares, Livrable3demeter 
- Completion de la documentation 
- Debut d'écriture du fichier readme pour le livrable3



![ l'uml ACRTION ](/index/uml_semaine9.png "UML Action ")
## Semaine 10

### Ce qui a été réalisé

- Résolution des problèmes qui conçernent les livrables (on avait pas réussi à afficher les upgades sur le plateau)
- Début de rédaction du readme de la section livrable 3
- Rédaction des tests unitaires de la classe BuildArmy 
- Amélioration de la javadoc pour le rendu du livrable 

### Difficultés rencontrées

- Réflexion sur cette condition de construction : pour construire une armée ou un port sur une île qu’on n’occupe pas encore, il faut disposer d’au moins un port sur une île qu’on occupe déjà

### Objectifs pour la semaine

- Completion des tests unitaires manquants 
- Completion du readme ainsi que la section des commandes qui permettent d'exécuter le livrable

## Semaine 11

### Ce qui a été réalisé
On as corriger quelques test et discuter sur la mise en forme du prochain livrable.
Entretien sur le livrable3, petit compte rendu ci dessous: 

- faire des sous-package actionDemeter et actionAres
- régler le display du plateau (il y a un petit décallage dans l'affichage)
- vérification sur les échange de ressources posible
- ne pas utiliser utiliser instanceof dans l'échange avec le port 
- division de upgrade Army en upgradeWithWarriors et upgradeWithRessources
- mettre un type paramétré pour le player dans ActionManager (correction dans AskWarrior)
- remplacé certaines exception par nos propres exception 
- suppression de la méthode chooseCoordinate de ListChooser
- ajouter listChooser en paramètre des actions (pour pouvoir choisir la manière interactive ou random)

### Difficultés rencontrées
Certains test ne passait plus dans building (ArmyTest), il sont maintenant de nouveau fonctionnels.


### Objectifs pour la semaine
On voudrais amélirorer l'affichage, faire les corrections necessaire suite à ce qui as été dit à l'entretien et commencé à écrire les boucles de jeu. 

## Semaine 12

### Ce qui a été réalisé
On as bien avancé sur le dernier livrable, les deux jeux sont globalement fonctionnel, dans l'ensemble la réflexion et la programmation s'est déroulé plutôt rapidement. Aujourd'hui il reste presque plus de bug. On as décidé de proposer aux joueurs uniquement les actions qu'ils pouvait effectué. Cela as permit de rendre les version random plus fluide, de cette manière les joueurs random ne peuvent pas s'entêter sur un choix qui ne passe pas. On a aussi décidé de mettre une limite de tour avant de déclarer une égalité (un game over). Pour le moment cette limite est fixé à 100. 

### Difficultés rencontrées
On as eu du mal à implémenter les objectifs pour les joueur d'Ares c'est pourquoi on as créer une classe à part entière, ca rend la vérification et l'attribution plus fluide. On as eu aussi un problème avec l'action d'attaque, un joueur pouvait s'attquer lui même et gagner un duel contre lui même. 

### Objectifs pour finaliser le projet
AUjourd'hui il nous reste à:
- vérifier que chaque action est bien fonctionnel et réalise ce qu'elle doit faire
- Que le jeu est gagnabla
- Faire l'UML complet
- Finir les Tests
- et faire des corrections dans la doc