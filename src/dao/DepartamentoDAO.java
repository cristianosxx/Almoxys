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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import tabelas.Departamento;

/**
 *
 * @author bruno.moraes
 */
public class DepartamentoDAO implements IDAO_T<Departamento> { 
    ResultSet resultadoQ = null;
    @Override
    public String salvar(Departamento o) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "INSERT INTO departamento VALUES (DEFAULT,"
                         + "'"+o.getNome()+"',"
                         + "'"+o.getCodigo()+"',"
                         + " 1 ,"
                         + "'"+o.getPeriodo()+"',"
                         + "'"+o.getAmbiente_id()+"')";

            System.out.println("sql: " + sql);

            int resultado = st.executeUpdate(sql);
            return null;
        } catch (Exception e) {
            System.out.println("Erro salvar departamento = " + e);
            return e.toString();
        }
        
    }

    @Override
    public String atualizar(Departamento o) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "UPDATE departamento "
                    + "SET "
                    + "nome = '" + o.getNome() + "',"
                    + "codigo = '"+ o.getCodigo() + "',"
                    + "status = '" + o.getStatus()+ "',"
                    + "periodo = '" + o.getPeriodo()+ "'"
                    + "WHERE id = " + o.getId();

            System.out.println("sql: " + sql);

            int resultado = st.executeUpdate(sql);

            return null;
        } catch (Exception e) {
            System.out.println("Erro atualizar Departamento = " + e);
            return e.toString();
        }
        
    }

    @Override
    public String excluir(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Departamento> consultarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Departamento> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Departamento consultarId(int id) {
        Departamento g = null;
        
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = ""
                    + "select * from departamento "
                    + "where  "
                    + "id = " + id;

            System.out.println("sql: " + sql);
            
            resultadoQ = st.executeQuery(sql);

            if (resultadoQ.next()) {
                g = new Departamento();
                
                g.setId(id);
                g.setNome(resultadoQ.getString("nome"));
                g.setCodigo(resultadoQ.getInt("codigo"));
                g.setStatus(resultadoQ.getInt("status"));
                g.setPeriodo(resultadoQ.getString("periodo"));
            }

        } catch (Exception e) {
            System.out.println("Erro consultar Departamento = " + e);
        }
        return g;
    }
    
    public void popularTabela(JTable tabela, String criterio, String mostrarAtivo) {
        // dados da tabela
        Object[][] dadosTabela = null;

        // cabecalho da tabela
        Object[] cabecalho = new Object[5];
        cabecalho[0] = "ID";
        cabecalho[1] = "Código";
        cabecalho[2] = "Nome";
        cabecalho[3] = "Período";
        cabecalho[4] = "Status";
        
        
        
        // cria matriz de acordo com nº de registros da tabela
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT count(*) FROM departamento WHERE nome ILIKE '%" + criterio + "%' "+mostrarAtivo+" " );

            resultadoQ.next();

            dadosTabela = new Object[resultadoQ.getInt(1)][5];

        } catch (Exception e) {
            System.out.println("Erro ao consultar departamento: " + e);
        }

        int lin = 0;
        
        // efetua consulta na tabela
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT * FROM departamento WHERE NOME ILIKE '%" + criterio + "%' "+mostrarAtivo+" "
                    + "ORDER BY status desc,nome");
            
            while (resultadoQ.next()) {
                String stat;
                dadosTabela[lin][0] = resultadoQ.getInt("id");
                dadosTabela[lin][1] = resultadoQ.getInt("codigo");
                dadosTabela[lin][2] = resultadoQ.getString("nome");
                dadosTabela[lin][3] = resultadoQ.getString("periodo");
                if(resultadoQ.getString("status").equals("1")){
                    stat = "Ativo";
                }else {
                    stat = "Inativo";
                }
                dadosTabela[lin][4] = stat;
                
                
                
                
                 // caso a coluna precise exibir uma imagem
//                if (resultadoQ.getBoolean("Situacao")) {
//                    dadosTabela[lin][2] = new ImageIcon(getClass().getClassLoader().getResource("Interface/imagens/status_ativo.png"));
//                } else {
//                    dadosTabela[lin][2] = new ImageIcon(getClass().getClassLoader().getResource("Interface/imagens/status_inativo.png"));
//                }
                lin++;
            }
        } catch (Exception e) {
            System.out.println("problemas para popular tabela...");
            System.out.println(e);
        }
        
        // configuracoes adicionais no componente tabela
        tabela.setModel(new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            // quando retorno for FALSE, a tabela nao é editavel
            public boolean isCellEditable(int row, int column) {
                return false;
                /*  
                 if (column == 3) {  // apenas a coluna 3 sera editavel
                 return true;
                 } else {
                 return false;
                 }
                 */
            }

            // alteracao no metodo que determina a coluna em que o objeto ImageIcon devera aparecer
            @Override
            public Class getColumnClass(int column) {

                if (column == 2) {
//                    return ImageIcon.class;
                }
                return Object.class;
            }
        });

        // permite seleção de apenas uma linha da tabela
        tabela.setSelectionMode(0);

        // redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            column = tabela.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(17);
                    break;
                case 1:
                    column.setPreferredWidth(140);
                    break;
//                case 2:
//                    column.setPreferredWidth(14);
//                    break;
            }
        }
        // renderizacao das linhas da tabela = mudar a cor
//        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                    boolean isSelected, boolean hasFocus, int row, int column) {
//                super.getTableCellRendererComponent(table, value, isSelected,
//                        hasFocus, row, column);
//                if (row % 2 == 0) {
//                    setBackground(Color.GREEN);
//                } else {
//                    setBackground(Color.LIGHT_GRAY);
//                }
//                return this;
//            }
//        });
    }
    
    


        
    }
    
    
    

