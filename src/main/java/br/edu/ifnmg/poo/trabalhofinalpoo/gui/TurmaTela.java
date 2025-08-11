package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Disciplina;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Professor;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Turma;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.DisciplinaRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.ProfessorRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.TurmaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TurmaTela extends javax.swing.JDialog {

    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private Turma turmaEmEdicao;

    public TurmaTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        turmaRepository = new TurmaRepository();
        disciplinaRepository = new DisciplinaRepository();
        professorRepository = new ProfessorRepository();

        initComponents();

        setLocationRelativeTo(null);

        carregarComboBoxes();
        limparFormulario();
        carregarTurmas();
        adicionarListeners();
    }

    private void carregarComboBoxes() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        DefaultComboBoxModel<Disciplina> disciplinaModel = new DefaultComboBoxModel<>();
        disciplinas.forEach(disciplinaModel::addElement);
        cmbDisciplina.setModel(disciplinaModel);

        List<Professor> professores = professorRepository.findAll();
        DefaultComboBoxModel<Professor> professorModel = new DefaultComboBoxModel<>();
        professores.forEach(professorModel::addElement);
        cmbProfessor.setModel(professorModel);
    }

    private void carregarTurmas() {
        List<Turma> turmas = turmaRepository.findAll();
        DefaultTableModel model = (DefaultTableModel) tblTurmas.getModel();
        model.setRowCount(0);

        for (Turma t : turmas) {
            Object[] row = {
                    t.getId(),
                    t.getPeriodo(),
                    t.getDisciplina().getNome(),
                    t.getProfessor().getNome()
            };
            model.addRow(row);
        }
    }

    private void adicionarListeners() {
        btnLimpar.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvarTurma());
        btnExcluir.addActionListener(e -> excluirTurma());

        btnDetalhes.addActionListener(e -> {
            if (turmaEmEdicao == null || turmaEmEdicao.getId() == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma turma na tabela para ver os detalhes.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            TurmaDetalheTela detalheTela = new TurmaDetalheTela(null, true, turmaEmEdicao);
            detalheTela.setVisible(true);

            carregarTurmas();
        });

        tblTurmas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblTurmas.getSelectedRow() != -1) {
                btnDetalhes.setEnabled(true);
                int selectedRow = tblTurmas.getSelectedRow();
                Long id = (Long) tblTurmas.getValueAt(selectedRow, 0);
                turmaEmEdicao = turmaRepository.findById(id);
                if (turmaEmEdicao != null) {
                    preencherFormularioComDados();
                }
            } else {
                btnDetalhes.setEnabled(false);
            }
        });

        btnLixeira.addActionListener(e -> {
            LixeiraTela lixeira = new LixeiraTela(null, true, turmaRepository);
            lixeira.setVisible(true);
            carregarTurmas();
        });
    }

    private void limparFormulario() {
        turmaEmEdicao = new Turma();
        cmbDisciplina.setSelectedIndex(-1);
        cmbProfessor.setSelectedIndex(-1);
        txtPeriodo.setText("");
        tblTurmas.clearSelection();
        cmbDisciplina.requestFocus();
        btnDetalhes.setEnabled(false);
    }

    private void preencherFormularioComDados() {
        cmbDisciplina.setSelectedItem(turmaEmEdicao.getDisciplina());
        cmbProfessor.setSelectedItem(turmaEmEdicao.getProfessor());
        txtPeriodo.setText(turmaEmEdicao.getPeriodo());
    }

    private void salvarTurma() {
        Disciplina disciplinaSelecionada = (Disciplina) cmbDisciplina.getSelectedItem();
        Professor professorSelecionado = (Professor) cmbProfessor.getSelectedItem();
        String periodo = txtPeriodo.getText().trim();

        if (disciplinaSelecionada == null || professorSelecionado == null || periodo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            turmaEmEdicao.setDisciplina(disciplinaSelecionada);
            turmaEmEdicao.setProfessor(professorSelecionado);
            turmaEmEdicao.setPeriodo(periodo);
            turmaRepository.saveOrUpdate(turmaEmEdicao);
            String acao = (turmaEmEdicao.getId() == null || turmaEmEdicao.getId() == 0) ? "criada" : "atualizada";
            JOptionPane.showMessageDialog(this, "Turma " + acao + " com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparFormulario();
            carregarTurmas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar turma: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirTurma() {
        if (turmaEmEdicao.getId() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma turma na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja mover esta turma para a lixeira?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                turmaRepository.deleteById(turmaEmEdicao.getId());
                JOptionPane.showMessageDialog(this, "Turma movida para a lixeira.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                carregarTurmas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir turma: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        tblTurmas = new javax.swing.JTable();
        pnlFormulario = new javax.swing.JPanel();
        lblDisciplina = new javax.swing.JLabel();
        cmbDisciplina = new javax.swing.JComboBox<>();
        lblProfessor = new javax.swing.JLabel();
        cmbProfessor = new javax.swing.JComboBox<>();
        lblPeriodo = new javax.swing.JLabel();
        txtPeriodo = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLixeira = new javax.swing.JButton();
        btnDetalhes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Turmas");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gerenciar Turmas");

        tblTurmas.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Período", "Disciplina", "Professor"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTurmas);

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da Turma", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        lblDisciplina.setText("Disciplina");

        lblProfessor.setText("Professor");

        lblPeriodo.setText("Período (Ex: 2025/1)");

        btnSalvar.setText("Salvar");

        btnLimpar.setText("Limpar");

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbDisciplina, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                                .addComponent(lblDisciplina)
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cmbProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblProfessor)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblPeriodo)
                                                        .addComponent(txtPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pnlFormularioLayout.setVerticalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDisciplina)
                                        .addComponent(lblProfessor))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbDisciplina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(lblPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSalvar)
                                        .addComponent(btnLimpar))
                                .addContainerGap())
        );

        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setText("Excluir");

        btnLixeira.setText("Lixeira");

        btnDetalhes.setText("Detalhes");
        btnDetalhes.setEnabled(false);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(btnLixeira)
                                        .addComponent(btnDetalhes))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
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
    private javax.swing.JButton btnDetalhes;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLixeira;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Disciplina> cmbDisciplina;
    private javax.swing.JComboBox<Professor> cmbProfessor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDisciplina;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblProfessor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblTurmas;
    private javax.swing.JTextField txtPeriodo;
    // End of variables declaration//GEN-END:variables
}