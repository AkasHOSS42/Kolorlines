Après compilation, les classes du projet (dossier Sources) à éxecuter sont les suivantes :

Gomoku, KolorLines et KolorLinesPlus (le KolorLines amélioré) avec comme argument la taille du plateau.

Pour faire jouer le robot stupide (tout seul sur KolorLines, ou en joueur contre environnement sur Gomoku),
il faut exécuter la classe Insanity avec deux arguments : le premier est la taille du plateau, le second
un entier entre 0 et 2 qui désigne le jeu auquel on veut jouer. 0 correspond au Gomoku, 1 au KolorLines et 2
à sa version améliorée.

Si on fait jouer le robot stupide au KolorLines, il joue une partie entière avant que quoi que ce soit ne s'affiche.
Il faut donc laisser au programme le temps de tourner un peu (et ne pas rentrer une taille de plateau trop grande).

Je recommande à l'examinateur de regarder d'abord ma modélisation(diagrammeDeClasses.pdf), 
et, si il lit le code source des classes,
de commencer par celles qui sont situés en haut dans le pdf de la modélisation.
L'explication objective du fonctionnement du code
est écrite directement en commentaire dans le code source. Les justifications subjectives de mes choix pour
ce projet sont données dans mon rapport.

Dans le KolorLines, la couleur arc-en-ciel est affichée en noir. Dans le KolorLines normal, on peut déplacer une case
colorée vers n'importe quelle case vide. Dans la version améliorée, je propose un KolorLines où le déplacement d'une
case peut être bloqué par des cases colorées (comme dans la version originale).

Quelques explications sur les règles d'attribution des points, que l'examinateur n'a pas besoin de lire pour jouer :

Pour le Gomoku, chaque alignement rapporte un point, un alignement de plus de 5
cases n'apporte pas de points supplémentaires. Ainsi les joueurs
sont amenés à chercher à faire de nouveaux alignements, plutôt
qu'à agrandir ceux qu'ils possèdent déjà. Cependant, un alignement
de 10 cases est considéré comme deux alignements de 5 cases (et ainsi
de suite pour tous les multiples de 5).
Remarque : si mes sources sont exactes, dans le Gomoku original,
réussir un alignement de 5 cases mets immédiatement fin à la partie.

Pour KolorLines, je décide qu'un alignement de 5 cases rapporte 5 points, qu'un
alignement de plus de 5 cases rapporte 2 points supplémentaires par
case au delà des 5 premières, et que chaque intersection d'alignement
rapporte 3 points supplémentaires. Comme une intersection de 3 alignements est
plus dure à faire, elle rapporte plus de points car on regarde les intersections
d'alignements deux à deux (donc pour 3 alignements, cela fait 3 intersections
et donc 9 points supplémentaires). On remarque qu'avec une telle attribution des points,
un alignement de 9 cases apporte le même nombre de points, qu'il soit considéré comme
un alignement de 9 cases ou deux alignements de 5 cases intersectés (le programme, lui,
le considère comme un alignement de 9 cases). Même chose pour les alignements de plus de 9 cases.