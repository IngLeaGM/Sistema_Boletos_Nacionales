/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaboletos.servicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServicioTasaActual {
    
    public static double obtenerTasaBCV() {
        try {
            // URL de la API pública para la tasa de Venezuela (BCV)
            URL url = new URL("[https://ve.dolarapi.com/v1/dolares/oficial](https://ve.dolarapi.com/v1/dolares/oficial)");
            
            // Abrimos la conexión a internet
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(5000); // Espera máximo 5 segundos para conectar
            conexion.setReadTimeout(5000);    // Espera máximo 5 segundos para leer los datos
            
            // Verificamos si la conexión fue exitosa
            if (conexion.getResponseCode() == 200) {
                BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder respuesta = new StringBuilder();
                String linea;
                
                while ((linea = lector.readLine()) != null) {
                    respuesta.append(linea);
                }
                lector.close();
                
                // Extraemos el precio de la respuesta JSON
                String json = respuesta.toString(); 
                // El JSON se ve así: {"moneda":"USD","casa":"oficial","nombre":"BCV","promedio":36.42,...}
                
                String[] partes = json.split("\"promedio\":");
                if (partes.length > 1) {
                    // Limpiamos el texto para quedarnos solo con el número Double
                    String valorString = partes[1].split(",")[0].trim();
                    return Double.parseDouble(valorString);
                }
            }
        } catch (Exception e) {
            System.err.println("Sin internet o error en la API. Usando tasa de respaldo. Detalle: " + e.getMessage());
        }
        
        // TASA DE RESPALDO (Fallback): 
        // Si el cliente no tiene internet en la terminal, no podemos detener el sistema.
        // Retornamos esta tasa manual por defecto. ¡Asegúrate de actualizar este número a mano de vez en cuando!
        return 622.21;
    }
}
