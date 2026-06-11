package sistemaboletos.servicio;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
public class GeneradorQR {
    
    public static String generarCodigoQR(String textoData, String nombreArchivo) {
        String rutaDestino = "reportes/" + nombreArchivo + ".png";
        try {
            int ancho = 150;
            int alto = 150;
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(textoData, BarcodeFormat.QR_CODE, ancho, alto);
            
            Path path = FileSystems.getDefault().getPath(rutaDestino);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            
            return rutaDestino; // Retorna la ruta de la imagen generada
        } catch (Exception e) {
            System.err.println("Error al generar QR: " + e.getMessage());
            return null;
        }
    }
}

