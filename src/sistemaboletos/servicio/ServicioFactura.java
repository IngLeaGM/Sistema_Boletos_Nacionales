
package sistemaboletos.servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import sistemaboletos.modelo.Boleto;
import sistemaboletos.modelo.BoletoInformacion;
import sistemaboletos.modelo.Factura;
import sistemaboletos.modelo.ViajeInformacion;

public class ServicioFactura {
    public void generarFacturaPDF(Factura factura,ViajeInformacion viajeInformacion, double montoTotal, ArrayList<Boleto> listaBoletos) {
        
        int idFactura = factura.getId_factura();
        String f_fecha = factura.getFecha();
        
        int id_viaje = viajeInformacion.getId_viaje();
        String salida = viajeInformacion.getCiudad_salida();
        String destino = viajeInformacion.getCiudad_destino();
        Double precio_x_asiento = viajeInformacion.getPrecio_x_asiento();
        String v_fecha = viajeInformacion.getFecha();
        
        // Asegurar que la carpeta exista
        File folder = new File("reportes");
        if (!folder.exists()) folder.mkdir();

        String rutaPdf = "reportes/Factura_" + idFactura + ".pdf";
        Document documento = new Document(PageSize.A6); // Tamaño recibo compacto
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(rutaPdf));
            documento.open();

            // 1. Fuentes Estilo Ticket
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            Font fontBold = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

            // 2. Encabezado
            Paragraph titulo = new Paragraph("SISTEMA DE BOLETOS NACIONALES\n", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph datosFactura = new Paragraph(
                "Factura N°: " + idFactura + "\n" +
                "Fecha de emision: " + f_fecha + "\n" +
                "Origen: " + salida + "\n" +
                "Destino: " + destino + "\n" +
                "Fecha de salida: " + v_fecha + "\n" +
                "---------------------------------------------------------------------------\n", fontSubtitulo
            );
            documento.add(datosFactura);

            // Detalle de Boletos Comprados (Tabla iText)
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            
            // Cabeceras de la tabla
            tabla.addCell(new Phrase("Pasajero", fontBold));
            tabla.addCell(new Phrase("Cedula", fontBold));
            tabla.addCell(new Phrase("Asiento", fontBold));
            tabla.addCell(new Phrase("Precio", fontBold));

            // Agregar cada boleto comprado de la lista
            for (Boleto b : listaBoletos) {
                tabla.addCell(new Phrase(b.getNom_pasajero(), fontCuerpo));
                tabla.addCell(new Phrase(String.valueOf(b.getCedula()), fontCuerpo));
                tabla.addCell(new Phrase(b.getAsiento(), fontCuerpo));
                tabla.addCell(new Phrase(String.valueOf(precio_x_asiento) + "$", fontCuerpo));
            }
            documento.add(tabla);

            // Monto Total
            Paragraph total = new Paragraph("\nTOTAL PAGADO: " + montoTotal + "$", fontTitulo);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(5f);
            total.setSpacingAfter(5f);
            documento.add(total);
            
            documento.add(new Paragraph("---------------------------------------------------------------------------\n", fontSubtitulo));

            // CÓDIGO QR EN EL CIERRE
            // Texto oculto que leerá cualquier teléfono celular al escanear el QR:
            String datosParaQR = "Factura:" + idFactura + " | Ruta:" + salida + "-" + destino + " | Total:$" + montoTotal + " | Boletos:" + listaBoletos.size();
            String rutaQrImg = GeneradorQR.generarCodigoQR(datosParaQR, "QR_" + idFactura);

            if (rutaQrImg != null) {
                Image imgQr = Image.getInstance(rutaQrImg);
                imgQr.setAlignment(Element.ALIGN_CENTER);
                
                imgQr.scaleAbsolute(90f, 90f); 
                imgQr.setSpacingBefore(5f);
                
                documento.add(imgQr);
            }

            // Cierre e instrucciones de éxito
            documento.close();
            System.out.println("Factura PDF creada con éxito.");

        } catch (Exception e) {
            System.err.println("Error al estructurar el PDF: " + e.getMessage());
        }
    }
}
