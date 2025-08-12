package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UnsupportedLookAndFeelException;

public class TelaPrincipal extends javax.swing.JFrame {

    public TelaPrincipal() {
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Falha ao inicializar o tema escuro.");
        }

        initComponents();
        setLocationRelativeTo(null);
        adicionarListeners();
    }

    private void adicionarListeners() {
        itmDiscentes.addActionListener(e -> new DiscenteTela(this, true).setVisible(true));
        itmProfessores.addActionListener(e -> new ProfessorTela(this, true).setVisible(true));
        itmDisciplinas.addActionListener(e -> new DisciplinaTela(this, true).setVisible(true));
        itmMatriculas.addActionListener(e -> new MatriculaTela(this, true).setVisible(true));
        itmTurmas.addActionListener(e -> new TurmaTela(this, true).setVisible(true));
        itmLixeiraGeral.addActionListener(e -> new LixeiraGeralTela(this, true).setVisible(true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblSubtitulo = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuCadastros = new javax.swing.JMenu();
        itmDiscentes = new javax.swing.JMenuItem();
        itmProfessores = new javax.swing.JMenuItem();
        itmDisciplinas = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itmMatriculas = new javax.swing.JMenuItem();
        itmTurmas = new javax.swing.JMenuItem();
        mnuSistema = new javax.swing.JMenu();
        itmLixeiraGeral = new javax.swing.JMenuItem();
        itmSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Controle Acadêmico");

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(153, 153, 153));
        lblSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtitulo.setText("Selecione uma opção no menu \"Cadastros\" para começar.");

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Sistema de Controle Acadêmico");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblSubtitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        mnuCadastros.setText("Cadastros");

        itmDiscentes.setText("Discentes (Alunos)");
        mnuCadastros.add(itmDiscentes);

        itmProfessores.setText("Professores");
        mnuCadastros.add(itmProfessores);

        itmDisciplinas.setText("Disciplinas");
        mnuCadastros.add(itmDisciplinas);
        mnuCadastros.add(jSeparator1);

        itmMatriculas.setText("Matrículas");
        mnuCadastros.add(itmMatriculas);

        itmTurmas.setText("Turmas");
        mnuCadastros.add(itmTurmas);

        jMenuBar1.add(mnuCadastros);

        mnuSistema.setText("Sistema");

        itmLixeiraGeral.setText("Lixeira Geral");
        mnuSistema.add(itmLixeiraGeral);

        itmSair.setText("Sair");
        itmSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSairActionPerformed(evt);
            }
        });
        mnuSistema.add(itmSair);

        jMenuBar1.add(mnuSistema);

        setJMenuBar(jMenuBar1);

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

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void itmSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itmSairActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSair;
    private javax.swing.JMenuItem itmDiscentes;
    private javax.swing.JMenuItem itmDisciplinas;
    private javax.swing.JMenuItem itmLixeiraGeral;
    private javax.swing.JMenuItem itmMatriculas;
    private javax.swing.JMenuItem itmProfessores;
    private javax.swing.JMenuItem itmSair;
    private javax.swing.JMenuItem itmTurmas;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu mnuCadastros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables
}