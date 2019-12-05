package com.java.controlador;

import com.java.bean.modelo.Administrador;
import com.java.bean.modelo.Hospitales;
import com.java.bean.modelo.ServiciosMedicos;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class prueba {
    public static void main(String[] args) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        //EncriptadorAES e = new EncriptadorAES();
        /*String clave = "yJgFLywTyhrqm1z+g3isiw==";
        String secreto = "secreto!";
        String desencriptado = e.desencriptar(clave, secreto);
        System.out.println(desencriptado);
        */  String identi = "IclQarP+ORGZIHmrLavxHA==";
            int idusuario = 0;
            System.out.println(identi.length());
            if (identi.length() > 5) {
                try {
                    final String claveEncriptacion = "sec";
                    EncriptadorAES encriptador = new EncriptadorAES();
                    String desencriptado = encriptador.desencriptar(identi, claveEncriptacion);
                    idusuario = Integer.parseInt(desencriptado);
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    //Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                idusuario = Integer.parseInt(identi);
            }

            //Datos del administrador
            AdministradorDAO admon = new AdministradorDAO();
            Administrador a = new Administrador();
            //Datos del Hospital
            HospitalCRUD crud = new HospitalCRUD();
            Hospitales h = new Hospitales();
            a.setIdUsuario(idusuario);
            a = admon.leer(a);
            h.setIdHospital(a.getIdHospital());
            h = crud.readOne(h);
            System.out.println(a.getNombreAdministrador());
            //Datos de los servicios médicos
            String tipo;
            int id;
            ServiciosMedicosCRUD dao1 = new ServiciosMedicosCRUD();
            ServiciosMedicos sm = new ServiciosMedicos();
            sm.setIdHospital(a.getIdHospital());
            List<ServiciosMedicos> lista1 = dao1.readAll(sm);
    }
}


