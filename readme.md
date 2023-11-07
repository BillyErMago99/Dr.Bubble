#####Come avviare il gioco

Per avviare il gioco basta eseguire il file Dr.Bubble.jar.  
Al primo avvio del gioco viene creato un file preferences all'interno della macchinina dove verranno salvate le impostazioni di default del gioco, i comandi e il livello del volume della musica e delle animazioni.  
L'utente, tramite la voce Personalize accessibile dal menù principale, potrà impostare sfondo, cannone e nome giocatore.

#####Comandi di default

Up:  ^
Down: v       
Fire: click mouse
Select: Enter
Pause: Esc  
Level start: 1 
Number of levels: 2
Volume:	80  
Effects: 80  

#####Come aggiungere ulteriori livelli

Per aggiungere un livello bisogna creare un file .java, nominarlo come nome livello e andare a modificare la classe GameStateManager
aggiungendo il nuovo stato.  
In seguito bisogna aggiungere i path delle immagini del mondo e del cannone in corrispondenza al numero del livello nell'array di stringhe presente nel GameStateManager, quindi :  
bgPath - cannonPath





