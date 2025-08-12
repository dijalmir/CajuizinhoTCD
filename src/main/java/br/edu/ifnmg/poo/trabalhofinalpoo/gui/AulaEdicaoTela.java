package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Aula;
import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Turma;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Tela para criação e edição dos dados de uma Aula.
 *
 * @author  Dijalmir Barbosa de Oliveira Junior
 * @version 0.1
 * @since 0.1, 11/08/2025
 */

public class AulaEdicaoTela extends javax.swing.JDialog {

    private Aula aula;
    private Turma turma;
    private boolean confirmado;

    public AulaEdicaoTela(java.awt.Dialog parent, boolean modal, Aula aula, Turma turma) {
        super(parent, modal);
        this.aula = aula;
        this.turma = turma;
        this.confirmado = false;

        initComponents();

        DatePickerSettings dateSettings = new DatePickerSettings(new Locale("pt", "BR"));
        dateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
        dteDataHora.datePicker.setSettings(dateSettings);

        TimePickerSettings timeSettings = dteDataHora.timePicker.getSettings();
        //timeSettings.setLocale(new Locale("pt", "BR"));
        timeSettings.setFormatForDisplayTime("HH:mm");

        setLocationRelativeTo(null);

        preencherFormulario();

        adicionarListeners();
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    private void preencherFormulario() {
        if (aula.getId() != null) {
            txaConteudo.setText(aula.getConteudo());
            dteDataHora.setDateTimePermissive(aula.getDataHora());
        } else {
            dteDataHora.setDateTimePermissive(LocalDateTime.now());
        }
    }

    private void adicionarListeners() {
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar() {
        LocalDateTime dataSelecionada = dteDataHora.getDateTimePermissive();
        String conteudo = txaConteudo.getText().trim();

        if (dataSelecionada == null || conteudo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        aula.setConteudo(conteudo);
        aula.setDataHora(dataSelecionada);
        aula.setTurma(turma);

        this.confirmado = true;
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        lblDataHora = new javax.swing.JLabel();
        lblConteudo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaConteudo = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        dteDataHora = new com.github.lgooddatepicker.components.DateTimePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dados da Aula");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Dados da Aula");

        pnlFormulario.setBackground(new java.awt.Color(255, 255, 255));

        lblDataHora.setText("Data e Hora");

        lblConteudo.setText("Conteúdo Ministrado");

        txaConteudo.setColumns(20);
        txaConteudo.setLineWrap(true);
        txaConteudo.setRows(5);
        txaConteudo.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txaConteudo);

        btnCancelar.setText("Cancelar");

        btnSalvar.setText("Salvar");

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblDataHora)
                                                        .addComponent(lblConteudo)
                                                        .addComponent(dteDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        pnlFormularioLayout.setVerticalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblDataHora)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dteDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblConteudo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCancelar)
                                        .addComponent(btnSalvar))
                                .addContainerGap())
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnlPrincipalLayout.setVerticalGroup(
                pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private com.github.lgooddatepicker.components.DateTimePicker dteDataHora;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConteudo;
    private javax.swing.JLabel lblDataHora;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTextArea txaConteudo;
    // End of variables declaration//GEN-END:variables
}