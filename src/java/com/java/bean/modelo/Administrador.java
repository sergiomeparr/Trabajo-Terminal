package com.java.bean.modelo;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

//Getters y Setter del Administrador
public class Administrador implements Serializable{
    
    private int Idadmon;
    private String NombreAdministrador;
    private String ApellidoMaterno;
    private String ApellidoPaterno;
    private String Telefono;
    private String foto;
    private int idUsuario;
    private int idHospital;
    private String sexo;

    public int getIdadmon() {
        return Idadmon;
    }

    public void setIdadmon(int Idadmon) {
        this.Idadmon = Idadmon;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public Administrador(){}
    
    public int getIdAdmon(){
        return Idadmon;
    }
    
    public void setIdAdmon(int Idadmon){
        this.Idadmon = Idadmon;
    }
    
    public int getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public int getIdHospital(){
        return idHospital;
    }
    
    public void setIdHospital(int idHospital){
        this.idHospital = idHospital;
    }
    
    public String getNombreAdministrador(){
        return NombreAdministrador;
    }
    
    public void setNombreAdministrador(String NombreAdministrador){
        this.NombreAdministrador = NombreAdministrador;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getTelefono(){
        return Telefono;
    }
    
    public void setFoto(String foto){
        this.foto = foto;
    }
    
    public String getFoto(){
        return foto;
    }
}
