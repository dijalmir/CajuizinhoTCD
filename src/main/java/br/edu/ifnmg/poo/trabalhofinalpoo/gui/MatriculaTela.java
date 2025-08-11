package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Discente;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Matricula;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Turma;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.DiscenteRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.MatriculaRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.TurmaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MatriculaTela extends javax.swing.JDialog {

    private final MatriculaRepository matriculaRepository;
    private final DiscenteRepository discenteRepository;
    private final TurmaRepository turmaRepository;
    private Matricula matriculaEmEdicao;

    public MatriculaTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        matriculaRepository = new MatriculaRepository();
        discenteRepository = new DiscenteRepository();
        turmaRepository = new TurmaRepository();

        initComponents();

        setLocationRelativeTo(null);

        carregarComboBoxes();
        limparFormulario();
        carregarMatriculas();
        adicionarListeners();
    }

    private void carregarComboBoxes() {
        List<Discente> discentes = discenteRepository.findAll();
        DefaultComboBoxModel<Discente> discenteModel = new DefaultComboBoxModel<>();
        discentes.forEach(discenteModel::addElement);
        cmbDiscente.setModel(discenteModel);

        List<Turma> turmas = turmaRepository.findAll();
        DefaultComboBoxModel<Turma> turmaModel = new DefaultComboBoxModel<>();
        turmas.forEach(turmaModel::addElement);
        cmbTurma.setModel(turmaModel);
    }

    private void carregarMatriculas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        DefaultTableModel model = (DefaultTableModel) tblMatriculas.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Matricula m : matriculas) {
            Object[] row = {
                    m.getId(),
                    m.getDiscente().getNome(),
                    m.getTurma().toString(),
                    m.getDataMatricula().format(formatter),
                    m.getStatus()
            };
            model.addRow(row);
        }
    }

    private void adicionarListeners() {
        btnLimpar.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvarMatricula());
        btnExcluir.addActionListener(e -> excluirMatricula());

        tblMatriculas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblMatriculas.getSelectedRow() != -1) {
                int selectedRow = tblMatriculas.getSelectedRow();
                Long id = (Long) tblMatriculas.getValueAt(selectedRow, 0);
                matriculaEmEdicao = matriculaRepository.findById(id);
                if (matriculaEmEdicao != null) {
                    preencherFormularioComDados();
                }
            }
        });

        btnLixeira.addActionListener(e -> {
            LixeiraTela lixeira = new LixeiraTela(null, true, matriculaRepository);
            lixeira.setVisible(true);
            carregarMatriculas();
        });
    }

    private void limparFormulario() {
        matriculaEmEdicao = null;
        cmbDiscente.setSelectedIndex(-1);
        cmbTurma.setSelectedIndex(-1);
        tblMatriculas.clearSelection();
        cmbDiscente.requestFocus();
        btnExcluir.setEnabled(false);
    }

    private void preencherFormularioComDados() {
        cmbDiscente.setSelectedItem(matriculaEmEdicao.getDiscente());
        cmbTurma.setSelectedItem(matriculaEmEdicao.getTurma());
        cmbDiscente.setEnabled(false);
        cmbTurma.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(true);
    }

    private void salvarMatricula() {
        Discente discenteSelecionado = (Discente) cmbDiscente.getSelectedItem();
        Turma turmaSelecionada = (Turma) cmbTurma.getSelectedItem();

        if (discenteSelecionado == null || turmaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione um discente e uma turma!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Matricula novaMatricula = new Matricula(discenteSelecionado, turmaSelecionada);

            matriculaRepository.saveOrUpdate(novaMatricula);

            JOptionPane.showMessageDialog(this, "Matrícula realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparFormularioAposSalvar();
            carregarMatriculas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirMatricula() {
        if (matriculaEmEdicao == null || matriculaEmEdicao.getId() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja mover esta matrícula para a lixeira?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            try {
                matriculaRepository.deleteById(matriculaEmEdicao.getId());
                JOptionPane.showMessageDialog(this, "Matrícula movida para a lixeira.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormularioAposSalvar();
                carregarMatriculas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir matrícula: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparFormularioAposSalvar() {
        limparFormulario();
        cmbDiscente.setEnabled(true);
        cmbTurma.setEnabled(true);
        btnSalvar.setEnabled(true);
        btnExcluir.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMatriculas = new javax.swing.JTable();
        pnlFormulario = new javax.swing.JPanel();
        lblDiscente = new javax.swing.JLabel();
        cmbDiscente = new javax.swing.JComboBox<>();
        lblTurma = new javax.swing.JLabel();
        cmbTurma = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLixeira = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Matrículas");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gerenciar Matrículas");

        tblMatriculas.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Aluno", "Turma", "Data", "Status"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblMatriculas);

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Matrícula", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        lblDiscente.setText("Discente (Aluno)");

        lblTurma.setText("Turma");

        btnSalvar.setText("Salvar");

        btnLimpar.setText("Limpar");

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbDiscente, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDiscente))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblTurma)
                                                        .addComponent(cmbTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 40, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pnlFormularioLayout.setVerticalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDiscente)
                                        .addComponent(lblTurma))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbDiscente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSalvar)
                                        .addComponent(btnLimpar))
                                .addContainerGap())
        );

        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setText("Excluir");

        btnLixeira.setText("Lixeira");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnLixeira, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addGap(18, 18, 18)
                                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnExcluir)
                                        .addComponent(btnLixeira))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
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
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLixeira;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Discente> cmbDiscente;
    private javax.swing.JComboBox<Turma> cmbTurma;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiscente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTurma;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblMatriculas;
    // End of variables declaration//GEN-END:variables
}