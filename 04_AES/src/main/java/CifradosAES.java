/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author demon
 */

import java.io.*;
import java.util.*;

public class CifradosAES {
    
    public static void main(String[] args) throws Exception{
        String mensaje = "Habia una vez un patito que decia miau miau y queria mimir todo el dia, y ademas jugaba halo y mataba gente de otros continentes llamados niños rata :3";
        String mensajeCifrado = AES.encrypt(mensaje);
        String mensajeDescifrado = AES.decrypt(mensajeCifrado);
        
        System.out.println("El mensaje ultrasecreto es: " + mensaje);
        System.out.println("Mensaje Cifrado AES 128: " + mensajeCifrado);
        System.out.println("Mensaje Descifrado AES 128: " + mensajeDescifrado);
    }
    
}
