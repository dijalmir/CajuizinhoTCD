package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.repository.AulaRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.AvaliacaoRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.DiscenteRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.DisciplinaRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.IRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.MatriculaRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProfessorRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProjectEntity;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.TurmaRepository;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LixeiraGeralTela extends javax.swing.JDialog {

    private final List<IRepository> repositorios;
    private final Map<Long, IRepository> mapaRepositorios;
    private Long idSelecionado;

    public LixeiraGeralTela(Frame parent, boolean modal) {
        super(parent, modal);

        repositorios = new ArrayList<>();
        repositorios.add(new DiscenteRepository());
        repositorios.add(new ProfessorRepository());
        repositorios.add(new DisciplinaRepository());
        repositorios.add(new TurmaRepository());
        repositorios.add(new MatriculaRepository());
        repositorios.add(new AulaRepository());
        repositorios.add(new AvaliacaoRepository());

        mapaRepositorios = new HashMap<>();

        initComponents();
        setLocationRelativeTo(null);
        carregarItensExcluidos();
        adicionarListeners();
    }

    private void carregarItensExcluidos() {
        DefaultTableModel model = (DefaultTableModel) tblItensExcluidos.getModel();
        model.setRowCount(0);
        mapaRepositorios.clear();

        for(IRepository repo : repositorios) {
            List<ProjectEntity> itens = repo.findAllInTrash();
            for(ProjectEntity item : itens) {
                Object[] row = {
                        item.getId(),
                        item.getClass().getSimpleName(), // Mostra o tipo do objeto (Ex: "Discente")
                        item.toString()
                };
                model.addRow(row);
                mapaRepositorios.put(item.getId(), repo);
            }
        }
    }

    private void adicionarListeners() {
        tblItensExcluidos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblItensExcluidos.getSelectedRow() != -1) {
                int selectedRow = tblItensExcluidos.getSelectedRow();
                idSelecionado = (Long) tblItensExcluidos.getValueAt(selectedRow, 0);
            } else {
                idSelecionado = null;
            }
        });

        btnRestaurar.addActionListener(e -> restaurarItem());
    }

    private void restaurarItem() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um item para restaurar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        IRepository repo = mapaRepositorios.get(idSelecionado);
        if (repo != null) {
            repo.recoverFromTrash(idSelecionado);
            JOptionPane.showMessageDialog(this, "Item restaurado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarItensExcluidos();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItensExcluidos = new javax.swing.JTable();
        btnRestaurar = new javax.swing.JButton();
        lblAviso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lixeira Geral");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Lixeira Geral do Sistema");

        tblItensExcluidos.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Tipo", "Descrição"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Long.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItensExcluidos);

        btnRestaurar.setText("Restaurar Item Selecionado");

        lblAviso.setForeground(new java.awt.Color(102, 102, 102));
        lblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAviso.setText("A exclusão definitiva e o esvaziamento devem ser feitos nas lixeiras de cada cadastro.");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnRestaurar)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(lblAviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addGap(18, 18, 18)
                                .addComponent(btnRestaurar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAviso)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblItensExcluidos;
    // End of variables declaration//GEN-END:variables
}