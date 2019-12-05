package com.java.bean.modelo;

import java.io.Serializable;

public class AdministradorSistema implements Serializable{
    
    private int IdAdmonSistema;
    private int IdUsuario;
    private String LlaveAdmon;

    public AdministradorSistema(){}
    
    public int getIdAdmonSistema() {
        return IdAdmonSistema;
    }

    public void setIdAdmonSistema(int IdAdmonSistema) {
        this.IdAdmonSistema = IdAdmonSistema;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getLlaveAdmon() {
        return LlaveAdmon;
    }

    public void setLlaveAdmon(String LlaveAdmon) {
        this.LlaveAdmon = LlaveAdmon;
    }    
}
