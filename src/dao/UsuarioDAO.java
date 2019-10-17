/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import apoio.ConexaoBD;
import apoio.IDAO_T;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import tabelas.Usuario;

/**
 *
 * @author cristiano.sommer
 */
public class UsuarioDAO implements IDAO_T<Usuario> {
    ResultSet resultadoQ = null;

    @Override
    public String salvar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String atualizar(Usuario o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Usuario> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario consultarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean autenticar (String usuario, String senha) {
        boolean ok = false;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from usuario "
                    + "where  "
                    + "login = '" + usuario + "'and senha = md5('" + senha+"')";

            

            resultadoQ = st.executeQuery(sql);

            if (resultadoQ.next()) {
                ok = true;
            }

        } catch (Exception e) {
            System.out.println("Erro consultar produto = " + e);
        }
        return ok;
    }
    public int getPermicao(String usuario, String senha){
        int permissao = 0;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from usuario "
                    + "where  "
                    + "login = '" + usuario + "'and senha = md5('" + senha+"')";

            

            resultadoQ = st.executeQuery(sql);

            if (resultadoQ.next()) {
                permissao = resultadoQ.getInt("permissao");
            }

        } catch (Exception e) {
            System.out.println("Erro consultar permissao = " + e);
        }
        
        return permissao;
    }
}
