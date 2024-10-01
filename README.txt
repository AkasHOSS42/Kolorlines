Apr�s compilation, les classes du projet (dossier Sources) � �xecuter sont les suivantes :

Gomoku, KolorLines et KolorLinesPlus (le KolorLines am�lior�) avec comme argument la taille du plateau.

Pour faire jouer le robot stupide (tout seul sur KolorLines, ou en joueur contre environnement sur Gomoku),
il faut ex�cuter la classe Insanity avec deux arguments : le premier est la taille du plateau, le second
un entier entre 0 et 2 qui d�signe le jeu auquel on veut jouer. 0 correspond au Gomoku, 1 au KolorLines et 2
� sa version am�lior�e.

Si on fait jouer le robot stupide au KolorLines, il joue une partie enti�re avant que quoi que ce soit ne s'affiche.
Il faut donc laisser au programme le temps de tourner un peu (et ne pas rentrer une taille de plateau trop grande).

Je recommande � l'examinateur de regarder d'abord ma mod�lisation(diagrammeDeClasses.pdf), 
et, si il lit le code source des classes,
de commencer par celles qui sont situ�s en haut dans le pdf de la mod�lisation.
L'explication objective du fonctionnement du code
est �crite directement en commentaire dans le code source. Les justifications subjectives de mes choix pour
ce projet sont donn�es dans mon rapport.

Dans le KolorLines, la couleur arc-en-ciel est affich�e en noir. Dans le KolorLines normal, on peut d�placer une case
color�e vers n'importe quelle case vide. Dans la version am�lior�e, je propose un KolorLines o� le d�placement d'une
case peut �tre bloqu� par des cases color�es (comme dans la version originale).

Quelques explications sur les r�gles d'attribution des points, que l'examinateur n'a pas besoin de lire pour jouer :

Pour le Gomoku, chaque alignement rapporte un point, un alignement de plus de 5
cases n'apporte pas de points suppl�mentaires. Ainsi les joueurs
sont amen�s � chercher � faire de nouveaux alignements, plut�t
qu'� agrandir ceux qu'ils poss�dent d�j�. Cependant, un alignement
de 10 cases est consid�r� comme deux alignements de 5 cases (et ainsi
de suite pour tous les multiples de 5).
Remarque : si mes sources sont exactes, dans le Gomoku original,
r�ussir un alignement de 5 cases mets imm�diatement fin � la partie.

Pour KolorLines, je d�cide qu'un alignement de 5 cases rapporte 5 points, qu'un
alignement de plus de 5 cases rapporte 2 points suppl�mentaires par
case au del� des 5 premi�res, et que chaque intersection d'alignement
rapporte 3 points suppl�mentaires. Comme une intersection de 3 alignements est
plus dure � faire, elle rapporte plus de points car on regarde les intersections
d'alignements deux � deux (donc pour 3 alignements, cela fait 3 intersections
et donc 9 points suppl�mentaires). On remarque qu'avec une telle attribution des points,
un alignement de 9 cases apporte le m�me nombre de points, qu'il soit consid�r� comme
un alignement de 9 cases ou deux alignements de 5 cases intersect�s (le programme, lui,
le consid�re comme un alignement de 9 cases). M�me chose pour les alignements de plus de 9 cases.