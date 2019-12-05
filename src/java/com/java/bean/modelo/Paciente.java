package com.java.bean.modelo;

import java.io.Serializable;

//Getter y Setter de Paciente
public class Paciente implements Serializable{
    
    private int IdUsuario;
    private String NombrePaciente;
    private String ApellidoMaterno;
    private String ApellidoPaterno;
    private String Telefono;
    private int idpaciente;
    private String delegacion;
    private String sexo;
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    public int getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente) {
        this.idpaciente = idpaciente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public Paciente(){}
    
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public int getIdPaciente(){
        return idpaciente;
    }
    
    public void setIdPaciente(int idpaciente){
        this.idpaciente = idpaciente;
    }
    
    public String getNombrePaciente(){
        return NombrePaciente;
    }
    
    public void setNombrePaciente(String NombrePaciente){
        this.NombrePaciente = NombrePaciente;
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
    
    public String getTelefono(){
        return Telefono;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }
    
}
