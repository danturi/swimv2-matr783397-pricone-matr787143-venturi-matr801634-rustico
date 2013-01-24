

// faccio il preload dell'immagine utilizzata per l'effetto rollover
var staron = new Image(); staron.src = "star-on.gif";

// Definisco la funzione per la votazione che verrà lanciata
// all'evento onclick su una delle 5 stelle
function star_vota(QT)
{
  // Creo una variabile con l'output da restituire al momento del voto
  //var star_output = '<span class="output">Hai votato ' + QT + ' stelle!</span>';
  //location.href=QT
  
  // Cambio dinamicamente il contenuto del DIV contenitore con il messaggio di
  // conferma di votazione avvenuta
  //document.getElementById("vote").value = QT;
  
  
  //document.getElementById('STAR_RATING').innerHTML = star_output;
  
  //window.location.href="/SWIMv2-WebClient/secure/makeFeed.jsp?vote="+QT
  window.location.href= window.location.href+"&vote="+QT

  
}

// Definisco la funzione per "accendere" dinamicamente le stelle
// unico argomento è il numero di stelle da accendere
function star_accendi(QT)
{
  // verifico che esistano i DIV delle stelle
  // se il DIV non esiste significa che si è già votato
  if (document.getElementById('star_1'))
  {
    // Ciclo tutte e 5 i DIV contenenti le stelle
    for (i=1; i<=5; i++)
    {
      // se il div è minore o uguale del numero di stelle da accendere
      // imposto dinamicamente la classe su "on"
      if (i<=QT) document.getElementById('star_' + i).className = 'on';
      // in caso contrario spengo la stella...
      else document.getElementById('star_' + i).className = '';
    }
  }
}

// Questa è la funzione che produce l'output.
// richiede come unico argomento il numero di stelle che si vuole accendere
// di default (possiamo in questo, ad esempio, modo mostrare il voto ottenuto
// nelle precedenti votazioni)
function star(QT)
{
  // stampo il codice HTML che produce le stelle
  document.write('<div id="STAR_RATING" onmouseout="star_accendi(' + QT + ')""><ul>');
  document.write('<li id="star_1" onclick="star_vota(1)" onmouseover="star_accendi(0); star_accendi(1)"></li>');
  document.write('<li id="star_2" onclick="star_vota(2)" onmouseover="star_accendi(0); star_accendi(2)"></li>');
  document.write('<li id="star_3" onclick="star_vota(3)" onmouseover="star_accendi(0); star_accendi(3)"></li>');
  document.write('<li id="star_4" onclick="star_vota(4)" onmouseover="star_accendi(0); star_accendi(4)"></li>');
  document.write('<li id="star_5" onclick="star_vota(5)" onmouseover="star_accendi(0); star_accendi(5)"></li>');
  document.write('</ul></div>');
  // accendo le stelle definite in argomento
  star_accendi(QT);
}

