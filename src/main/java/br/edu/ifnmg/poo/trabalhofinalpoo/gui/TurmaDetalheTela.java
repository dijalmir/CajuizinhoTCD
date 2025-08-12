package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.*;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Frame;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TurmaDetalheTela extends javax.swing.JDialog {

    private Turma turma;
    private final TurmaRepository turmaRepository;
    private final AulaRepository aulaRepository;
    private Aula aulaSelecionada;

    public TurmaDetalheTela(Frame parent, boolean modal, Turma turma) {
        super(parent, modal);
        this.turmaRepository = new TurmaRepository();
        this.turma = turmaRepository.findById(turma.getId());
        this.aulaRepository = new AulaRepository();

        initComponents();

        setLocationRelativeTo(null);

        preencherInformacoes();

        adicionarListeners();
    }

    private void preencherInformacoes(){
        lblInfoDisciplina.setText("Disciplina: " + turma.getDisciplina().getNome());
        lblInfoProfessor.setText("Professor: " + turma.getProfessor().getNome());
        lblInfoPeriodo.setText("Período: " + turma.getPeriodo());

        carregarAlunosMatriculados();
        carregarAulas();
    }

    private void carregarAlunosMatriculados() {
        DefaultTableModel model = (DefaultTableModel) tblAlunosMatriculados.getModel();
        model.setRowCount(0);

        List<Matricula> matriculas = new ArrayList<>(turma.getMatriculas());

        for (Matricula m : matriculas) {
            if (!m.isExcluido()) {
                Object[] row = { m.getId(), m.getDiscente().getNome() };
                model.addRow(row);
            }
        }
    }

    private void carregarAulas() {
        this.turma = turmaRepository.findById(turma.getId());

        DefaultTableModel model = (DefaultTableModel) tblAulas.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Aula> aulas = new ArrayList<>(turma.getAulas());

        for (Aula a : aulas) {
            if (!a.isExcluido()) {
                Object[] row = { a.getId(), a.getDataHora().format(formatter), a.getConteudo() };
                model.addRow(row);
            }
        }
    }

    private void adicionarListeners(){
        btnFechar.addActionListener(e -> this.dispose());

        // Ações para o painel de Aulas
        btnNovaAula.addActionListener(e -> criarNovaAula());
        btnEditarAula.addActionListener(e -> editarAula());
        btnExcluirAula.addActionListener(e -> excluirAula());

        tblAulas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblAulas.getSelectedRow() != -1) {
                int selectedRow = tblAulas.getSelectedRow();
                Long id = (Long) tblAulas.getValueAt(selectedRow, 0);
                aulaSelecionada = aulaRepository.findById(id);
            } else {
                aulaSelecionada = null;
            }
        });

        // Ação para o painel de Alunos
        btnGerenciarAvaliacoes.addActionListener(e -> gerenciarAvaliacoes());
    }

    private void gerenciarAvaliacoes() {
        int selectedRow = tblAlunosMatriculados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na lista para gerenciar as avaliações.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long matriculaId = (Long) tblAlunosMatriculados.getValueAt(selectedRow, 0);
        Matricula matriculaSelecionada = new MatriculaRepository().findById(matriculaId);

        if (matriculaSelecionada != null) {
            AvaliacaoTela avaliacaoTela = new AvaliacaoTela(this, true, matriculaSelecionada);
            avaliacaoTela.setVisible(true);
        }
    }

    private void criarNovaAula() {
        Aula novaAula = new Aula();
        AulaEdicaoTela editor = new AulaEdicaoTela(this, true, novaAula, this.turma);
        editor.setVisible(true);

        if (editor.isConfirmado()) {
            aulaRepository.saveOrUpdate(novaAula);
            carregarAulas(); // Atualiza a tabela
        }
    }

    private void editarAula() {
        if (aulaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma aula para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AulaEdicaoTela editor = new AulaEdicaoTela(this, true, aulaSelecionada, this.turma);
        editor.setVisible(true);

        if (editor.isConfirmado()) {
            aulaRepository.saveOrUpdate(aulaSelecionada);
            carregarAulas();
        }
    }

    private void excluirAula() {
        if (aulaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma aula para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja mover esta aula para a lixeira?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            aulaRepository.deleteById(aulaSelecionada.getId());
            carregarAulas();
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
        pnlInfoTurma = new javax.swing.JPanel();
        lblInfoDisciplina = new javax.swing.JLabel();
        lblInfoProfessor = new javax.swing.JLabel();
        lblInfoPeriodo = new javax.swing.JLabel();
        pnlAlunos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlunosMatriculados = new javax.swing.JTable();
        btnGerenciarAvaliacoes = new javax.swing.JButton();
        pnlAulas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAulas = new javax.swing.JTable();
        btnNovaAula = new javax.swing.JButton();
        btnEditarAula = new javax.swing.JButton();
        btnExcluirAula = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detalhes da Turma");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Detalhes da Turma");

        pnlInfoTurma.setBackground(new java.awt.Color(245, 245, 245));
        pnlInfoTurma.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações"));

        lblInfoDisciplina.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInfoDisciplina.setText("Disciplina: Nome da Disciplina");

        lblInfoProfessor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoProfessor.setText("Professor: Nome do Professor");

        lblInfoPeriodo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfoPeriodo.setText("Período: 2025/1");

        javax.swing.GroupLayout pnlInfoTurmaLayout = new javax.swing.GroupLayout(pnlInfoTurma);
        pnlInfoTurma.setLayout(pnlInfoTurmaLayout);
        pnlInfoTurmaLayout.setHorizontalGroup(
                pnlInfoTurmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlInfoTurmaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblInfoDisciplina, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblInfoProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblInfoPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addContainerGap())
        );
        pnlInfoTurmaLayout.setVerticalGroup(
                pnlInfoTurmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlInfoTurmaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlInfoTurmaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblInfoDisciplina)
                                        .addComponent(lblInfoProfessor)
                                        .addComponent(lblInfoPeriodo))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAlunos.setBorder(javax.swing.BorderFactory.createTitledBorder("Alunos Matriculados"));

        tblAlunosMatriculados.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID Matrícula", "Aluno"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Long.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAlunosMatriculados);

        btnGerenciarAvaliacoes.setText("Gerenciar Avaliações do Aluno");

        javax.swing.GroupLayout pnlAlunosLayout = new javax.swing.GroupLayout(pnlAlunos);
        pnlAlunos.setLayout(pnlAlunosLayout);
        pnlAlunosLayout.setHorizontalGroup(
                pnlAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlAlunosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAlunosLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnGerenciarAvaliacoes)))
                                .addContainerGap())
        );
        pnlAlunosLayout.setVerticalGroup(
                pnlAlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAlunosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnGerenciarAvaliacoes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pnlAulas.setBorder(javax.swing.BorderFactory.createTitledBorder("Aulas Ministradas"));

        tblAulas.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Data e Hora", "Conteúdo"
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
        jScrollPane2.setViewportView(tblAulas);
        if (tblAulas.getColumnModel().getColumnCount() > 0) {
            tblAulas.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblAulas.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblAulas.getColumnModel().getColumn(2).setPreferredWidth(400);
        }

        btnNovaAula.setText("Nova Aula");

        btnEditarAula.setText("Editar Aula");

        btnExcluirAula.setText("Excluir Aula");

        javax.swing.GroupLayout pnlAulasLayout = new javax.swing.GroupLayout(pnlAulas);
        pnlAulas.setLayout(pnlAulasLayout);
        pnlAulasLayout.setHorizontalGroup(
                pnlAulasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlAulasLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlAulasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                        .addGroup(pnlAulasLayout.createSequentialGroup()
                                                .addComponent(btnNovaAula)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEditarAula)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnExcluirAula)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pnlAulasLayout.setVerticalGroup(
                pnlAulasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAulasLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlAulasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNovaAula)
                                        .addComponent(btnEditarAula)
                                        .addComponent(btnExcluirAula))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
        );

        btnFechar.setText("Fechar");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlInfoTurma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(pnlAlunos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlAulas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlInfoTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlAlunos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlAulas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnFechar)
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
    private javax.swing.JButton btnEditarAula;
    private javax.swing.JButton btnExcluirAula;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnGerenciarAvaliacoes;
    private javax.swing.JButton btnNovaAula;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblInfoDisciplina;
    private javax.swing.JLabel lblInfoPeriodo;
    private javax.swing.JLabel lblInfoProfessor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAlunos;
    private javax.swing.JPanel pnlAulas;
    private javax.swing.JPanel pnlInfoTurma;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblAlunosMatriculados;
    private javax.swing.JTable tblAulas;
    // End of variables declaration//GEN-END:variables
}