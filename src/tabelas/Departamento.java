/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelas;

/**
 *
 * @author bruno.moraes
 */


public class Departamento {
    
    private int id;
    private int codigo;
    private String nome;
    private String periodo;
    private int status;
    private int ambiente_id;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAmbiente_id() {
        return ambiente_id;
    }

    public void setAmbiente_id(int ambiente_id) {
        this.ambiente_id = ambiente_id;
    }

    
    
    
    
    
    
}
