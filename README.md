Object Oriented Project 4 
Student : Caroline Jubran 
ID : 318696150

This is the final project of our 0-4 project series at Ariel uni.
In the 2-3 project I made a basic pacman game in Ariel uni area,
and in this project we were required to imporve it by adding addiotional 
things such as : 
1) A player : he controls his game character using arrow keys
2) Ghosts : that hunt the pacman's 
3) Blockers : that are located in different sites on the map 
and can make a pacman lose points by stepping on it. 
4)Automatic mode : this is a no ghost mode , where the algorithm 
changes so that the player avoids the blockers because there are no ghost's 
so his path is clearer .

/////ADDITIONAL CLASSES////// 

*In Algorithms package* 
1)AutoNode = this class provides another path tp the fruits without taking ghosts nor blockers 
a threat.

2)AutoPlayer = this class is for the player who wants to avoid losing point from the blockers 

3)Play=responsible for starting the actual game , the movements of the charactesr ,
the point that each character gains and the final score.

*In Components package*
1)Blockers=for the blockers character that are spread on the feild 
to prevent the pacman from gaining extra points.

*In GUI package*
1)Board = for the players to move their game-character around the board 
to set the initial location of the game-character.

2)Characters =it represents a moving character that has a certain velocity.
each character can be:
a)a pacman 
b)a ghost
c)a player

-side note : I may have made some changes in classes that already existed 
so they can do they work according to the new requirements . 


////HOW TO PLAY/////
*Option 1*
1)load the game from a given file that has a list of 9 games.
2)use the mouse to set the initial location of your player.
3)press tab key 
4)the game starts when pressing any arrow key 

*Option 2*
1)choose a game 
2)press on the Automatic mode 



-side note : you may find some missing things due to the lack of time . 
