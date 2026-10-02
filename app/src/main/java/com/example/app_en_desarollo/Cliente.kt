package com.example.app_en_desarollo

class Cliente (
     var NombreCompleto: String,
     var Empresa: String,
     var Telefono: String,
     var Correo: String) {

     fun verificarCorreo(): Boolean {
         if (Correo.isNotEmpty() &&
             Correo.contains("@") &&
             Correo.substringAfter("@").contains(".")) {

             return true
         }
         else {
             return false
         }
     }
}