package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Avaliacao;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Matricula;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.AvaliacaoRepository;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.MatriculaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela para gerenciar as avaliações de um aluno em uma determinada matrícula.
 *
 * @author  Dijalmir Barbosa de Oliveira Junior
 * @version 0.1
 * @since 0.1, 11/08/2025
 */

public class AvaliacaoTela extends javax.swing.JDialog {

    private Matricula matricula;
    private final AvaliacaoRepository avaliacaoRepository;
    private final MatriculaRepository matriculaRepository;
    private Avaliacao avaliacaoEmEdicao;

    public AvaliacaoTela(java.awt.Dialog parent, boolean modal, Matricula matricula) {
        super(parent, modal);
        this.matriculaRepository = new MatriculaRepository();
        this.matricula = matriculaRepository.findById(matricula.getId());
        this.avaliacaoRepository = new AvaliacaoRepository();

        initComponents();

        setLocationRelativeTo(null);

        lblInfoAluno.setText("Aluno: " + this.matricula.getDiscente().getNome());

        limparFormulario();
        carregarAvaliacoes();
        adicionarListeners();
    }

    private void carregarAvaliacoes() {
        this.matricula = matriculaRepository.findById(this.matricula.getId());

        DefaultTableModel model = (DefaultTableModel) tblAvaliacoes.getModel();
        model.setRowCount(0);

        List<Avaliacao> avaliacoes = new ArrayList<>(this.matricula.getAvaliacoes());

        for (Avaliacao a : avaliacoes) {
            if(!a.isExcluido()){
                Object[] row = { a.getId(), a.getDescricao(), a.getNota() };
                model.addRow(row);
            }
        }
    }

    private void adicionarListeners() {
        btnLimpar.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvarAvaliacao());
        btnExcluir.addActionListener(e -> excluirAvaliacao());

        tblAvaliacoes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblAvaliacoes.getSelectedRow() != -1) {
                int selectedRow = tblAvaliacoes.getSelectedRow();
                Long id = (Long) tblAvaliacoes.getValueAt(selectedRow, 0);
                avaliacaoEmEdicao = avaliacaoRepository.findById(id);
                if (avaliacaoEmEdicao != null) {
                    preencherFormulario();
                }
            }
        });

        btnLixeira.addActionListener(e -> {
            LixeiraTela lixeira = new LixeiraTela(null, true, avaliacaoRepository);
            lixeira.setVisible(true);
            carregarAvaliacoes();
        });
    }

    private void limparFormulario() {
        avaliacaoEmEdicao = new Avaliacao();
        txtDescricao.setText("");
        txtNota.setText("");
        tblAvaliacoes.clearSelection();
        txtDescricao.requestFocus();
    }

    private void preencherFormulario() {
        txtDescricao.setText(avaliacaoEmEdicao.getDescricao());
        txtNota.setText(String.valueOf(avaliacaoEmEdicao.getNota()));
    }

    private void salvarAvaliacao() {
        String descricao = txtDescricao.getText().trim();
        String notaStr = txtNota.getText().trim();

        if (descricao.isEmpty() || notaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Double nota = Double.parseDouble(notaStr.replace(",", "."));

            avaliacaoEmEdicao.setDescricao(descricao);
            avaliacaoEmEdicao.setNota(nota);
            avaliacaoEmEdicao.setMatricula(this.matricula);

            avaliacaoRepository.saveOrUpdate(avaliacaoEmEdicao);

            JOptionPane.showMessageDialog(this, "Avaliação salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparFormulario();
            carregarAvaliacoes();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O valor da nota é inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar avaliação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAvaliacao() {
        if (avaliacaoEmEdicao.getId() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma avaliação para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja mover esta avaliação para a lixeira?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            avaliacaoRepository.deleteById(avaliacaoEmEdicao.getId());
            limparFormulario();
            carregarAvaliacoes();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        lblDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        lblNota = new javax.swing.JLabel();
        txtNota = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAvaliacoes = new javax.swing.JTable();
        btnExcluir = new javax.swing.JButton();
        lblInfoAluno = new javax.swing.JLabel();
        btnLixeira = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciar Avaliações");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gerenciar Avaliações");

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da Avaliação"));

        lblDescricao.setText("Descrição (Ex: Prova 1)");

        lblNota.setText("Nota");

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
                                                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDescricao))
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNota)
                                                        .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pnlFormularioLayout.setVerticalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDescricao)
                                        .addComponent(lblNota))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSalvar)
                                        .addComponent(btnLimpar))
                                .addContainerGap())
        );

        tblAvaliacoes.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Descrição", "Nota"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Long.class, java.lang.String.class, java.lang.Double.class
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
        jScrollPane1.setViewportView(tblAvaliacoes);

        btnExcluir.setBackground(new java.awt.Color(255, 102, 102));
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setText("Excluir");

        lblInfoAluno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblInfoAluno.setText("Aluno: Nome do Aluno");

        btnLixeira.setText("Lixeira");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                                        .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1)
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnLixeira, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                                .addComponent(lblInfoAluno)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addGap(12, 12, 12)
                                .addComponent(lblInfoAluno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnExcluir)
                                        .addComponent(btnLixeira))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblInfoAluno;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblAvaliacoes;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtNota;
    // End of variables declaration//GEN-END:variables
}