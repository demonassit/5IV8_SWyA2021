

var cesar = cesar || (function() {
    var proceso = function (txt, desp, action) {
        var replace = (function() {
            //en el cuerpo principal de la funcion del callback
            //debemos de empezar a definir los elementos necesarios para
            //el cifrado:
            //abc
            var abc = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];
            var l = abc.length;
            //debemos de retornar posicion del caracter
            return function(c) {
                //variable para iterar en mi arreglo del abc
                var i = abc.indexOf(c.toLowerCase());
                //asegurarse que este dentro de mi rango
                if (i != -1) {
                    var pos = i;
                    if (action) {
                        //cifrar (avanza)
                        pos += desp;
                        //nuestro limite es el tamaño del abc; por lo tanto
                        //tenemos que darle vueltas y vueltas y vueltas sobre
                        //el mismo tamaño
                        pos -= (pos >= l)?l:0;
                    } else {
                        //descifrar (retrocede)
                        pos -= desp;
                        pos += (pos < 0)?l:0;
                    }
                    return abc[pos];
                }
                 //retornar el caracter o la poscion del caracter
                return c;
            };
        })();
        //necesito una expresion regular para mi abc
        var re = (/([a-z])/ig);
        //alert(re);
        //esta es la funcion que se encarga del reemplazo
        //acorde a la poscion que esta obteniendo respecto del caracter
        //para recorrer el abc
        return String(txt).replace(re, function (match) {
            return replace(match);
        });
    };

    //hay que definir la accion a realizar en el algoritmo
    return {
            encode: function(txt, desp) {
            return proceso(txt, desp, true);
        },
            decode: function(txt, desp) {
            return proceso(txt, desp, false);
        }
    };
})();

//ahora vamos a realizar la correspondiente funcion

//codificar o cifrar
function cifrar(){
    document.getElementById("resultado").innerHTML = 
    cesar.encode(document.getElementById("cadena").value, 3);
}

//decodificar o descifrar
function descifrar(){
    document.getElementById("resultado").innerHTML = 
    cesar.decode(document.getElementById("cadena").value, 3);
}