package com.java.bean.modelo;

import java.io.Serializable;

//Getters y Setter de Usuario
public class Usuario implements Serializable{
    
    private int IdUsuario;
    private String Usuarioo;
    private String Contrasena;
    
    public Usuario(){}
    
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public void setIdUsuario(int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    
    public String getUsuario(){
        return Usuarioo;
    }
    
    public void setUsuario(String Usuarioo){
        this.Usuarioo = Usuarioo;
    }
    
    public String getContrasena(){
        return Contrasena;
    }
    
    public void setContrasena(String Contrasena){
        this.Contrasena = Contrasena;
    }
}
