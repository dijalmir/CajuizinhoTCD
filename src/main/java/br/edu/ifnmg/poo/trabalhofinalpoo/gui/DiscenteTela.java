package br.edu.ifnmg.poo.trabalhofinalpoo.gui;

import br.edu.ifnmg.poo.trabalhofinalpoo.entity.Discente;
import br.edu.ifnmg.poo.trabalhofinalpoo.repository.DiscenteRepository;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DiscenteTela extends javax.swing.JDialog {

    private final DiscenteRepository discenteRepository;
    private Discente discenteEmEdicao;

    /**
     * Creates new form DiscenteTela
     */
    public DiscenteTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        discenteRepository = new DiscenteRepository();
        discenteEmEdicao = new Discente();

        initComponents();

        setLocationRelativeTo(null);

        carregarDiscentes();

        adicionarListeners();
    }

    private void carregarDiscentes() {
        List<Discente> discentes = discenteRepository.findAll();

        DefaultTableModel model = (DefaultTableModel) tblDiscentes.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Discente d : discentes) {
            Object[] row = {
                    d.getId(),
                    d.getNome(),
                    d.getCpf(),
                    d.getNascimento().format(formatter)
            };
            model.addRow(row);
        }
    }

    private void adicionarListeners() {
        btnNovo.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvarDiscente());
        btnExcluir.addActionListener(e -> excluirDiscente());

        tblDiscentes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblDiscentes.getSelectedRow() != -1) {
                int selectedRow = tblDiscentes.getSelectedRow();
                Long id = (Long) tblDiscentes.getValueAt(selectedRow, 0);

                Discente discenteSelecionado = discenteRepository.findById(id);
                if (discenteSelecionado != null) {
                    discenteEmEdicao = discenteSelecionado;
                    preencherFormulario();
                }
            }
        });

        btnLixeira.addActionListener(e -> {
            LixeiraTela lixeira = new LixeiraTela(null, true, discenteRepository);
            lixeira.setVisible(true);
            // Recarrega a tabela principal caso algum item tenha sido restaurado
            carregarDiscentes();
        });
    }

    private void limparFormulario() {
        discenteEmEdicao = new Discente();
        txtNome.setText("");
        txtCpf.setText("");
        dteNascimento.setDate(null); // Limpa o JDateChooser
        tblDiscentes.clearSelection();
        txtNome.requestFocus();
    }

    private void preencherFormulario() {
        txtNome.setText(discenteEmEdicao.getNome());
        txtCpf.setText(discenteEmEdicao.getCpf());

        // Converte LocalDate para Date para o JDateChooser
        Date dataNascimento = Date.from(discenteEmEdicao.getNascimento()
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        dteNascimento.setDate(dataNascimento);
    }

    private void salvarDiscente() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        Date dataSelecionada = dteNascimento.getDate(); // Pega a data do JDateChooser

        if (nome.isEmpty() || cpf.isEmpty() || dataSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Converte Date para LocalDate
            LocalDate nascimento = dataSelecionada.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            discenteEmEdicao.setNome(nome);
            discenteEmEdicao.setCpf(cpf);
            discenteEmEdicao.setNascimento(nascimento);

            discenteRepository.saveOrUpdate(discenteEmEdicao);

            JOptionPane.showMessageDialog(this, "Discente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparFormulario();
            carregarDiscentes();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar discente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirDiscente() {
        if (discenteEmEdicao.getId() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um discente na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja mover este discente para a lixeira?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            try {
                discenteRepository.deleteById(discenteEmEdicao.getId());
                JOptionPane.showMessageDialog(this, "Discente movido para a lixeira.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                carregarDiscentes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir discente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        tblDiscentes = new javax.swing.JTable();
        pnlFormulario = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblCpf = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        lblNascimento = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        dteNascimento = new com.toedter.calendar.JDateChooser();
        btnExcluir = new javax.swing.JButton();
        btnLixeira = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Discentes");

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Gerenciar Discentes (Alunos)");

        tblDiscentes.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Nome", "CPF", "Nascimento"
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
        jScrollPane1.setViewportView(tblDiscentes);

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Discente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        lblNome.setText("Nome completo");

        lblCpf.setText("CPF");

        lblNascimento.setText("Nascimento");

        btnSalvar.setText("Salvar");

        btnNovo.setText("Novo");

        dteNascimento.setDateFormatString("dd/MM/yyyy");

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNome)
                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNome)
                                                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblCpf))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblNascimento)
                                                                        .addComponent(dteNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        pnlFormularioLayout.setVerticalGroup(
                pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlFormularioLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCpf)
                                        .addComponent(lblNascimento))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dteNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSalvar)
                                        .addComponent(btnNovo))
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
                                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
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
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLixeira;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private com.toedter.calendar.JDateChooser dteNascimento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblNascimento;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTable tblDiscentes;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}