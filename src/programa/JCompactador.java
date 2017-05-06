package programa;

import java.io.*;
import javax.swing.*;
import manipuladores.*;

/**
 * Formulário com opção de compactação e descompactação de arquivos.
 *
 * @author Lucas de Oliveira Silva - 15182
 */
public class JCompactador extends javax.swing.JFrame {

    /**
     * Cria e inicializa um novo formulario.
     */
    public JCompactador() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDescompactar = new javax.swing.JButton();
        btnCompactar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compactador de Arquivos Textuais");
        setResizable(false);

        btnDescompactar.setText("Descompactar");
        btnDescompactar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescompactarActionPerformed(evt);
            }
        });

        btnCompactar.setText("Compactar");
        btnCompactar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompactarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDescompactar, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnCompactar, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnDescompactar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(btnCompactar, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompactarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompactarActionPerformed
        JFileChooser escArq = new JFileChooser();

        if (escArq.showDialog(null, "Escolha o arquivo a ser compactado") == JFileChooser.APPROVE_OPTION
                && escArq.getSelectedFile() != null) {
            String nomeArq = escArq.getSelectedFile().getAbsolutePath();

            if ((new File(nomeArq)).exists()) {
                if (escArq.showDialog(null, "Escolha aonde salvar o arquivo compactado") == JFileChooser.APPROVE_OPTION
                        && escArq.getSelectedFile() != null) {
                    String nomeArqComp = escArq.getSelectedFile().getAbsolutePath();

                    //Compactar:
                    try {
                        EscritorDeArquivo compEsc = new EscritorDeArquivo(nomeArqComp);
                        LeitorDeArquivo origiLei = new LeitorDeArquivo(nomeArq);
                        GeradorDeHuffman gerador = new GeradorDeHuffman(origiLei.gerarTabelaDeOcorrencia());

                        compEsc.escrever(Compactador.gerarArquivoCompactado(origiLei.getBytesArq(), gerador.gerarTabelaDeHuffman()));
                        compEsc.fechar();

                        JOptionPane.showMessageDialog(null, "Compactado com sucesso!!!", "Finalizado", JOptionPane.OK_OPTION);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao compactar o arquivo: " + nomeArq, e.getMessage(), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Arquivo a ser compactado não existe!!!", "Erro ao compactar o arquivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCompactarActionPerformed

    private void btnDescompactarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescompactarActionPerformed
        JFileChooser escArq = new JFileChooser();

        if (escArq.showDialog(null, "Escolha o arquivo a ser descompactado") == JFileChooser.APPROVE_OPTION
                && escArq.getSelectedFile() != null) {
            String nomeArqComp = escArq.getSelectedFile().getAbsolutePath();

            if ((new File(nomeArqComp)).exists()) {
                if (escArq.showDialog(null, "Escolha aonde salvar o arquivo descompactado") == JFileChooser.APPROVE_OPTION
                        && escArq.getSelectedFile() != null) {
                    String nomeArqOrig = escArq.getSelectedFile().getAbsolutePath();

                    //Descompactar:
                    try {
                        EscritorDeArquivo descompEsc = new EscritorDeArquivo(nomeArqOrig);
                        LeitorDeArquivo compLei = new LeitorDeArquivo(nomeArqComp);

                        descompEsc.escrever(Compactador.gerarArquivoDescompactado(compLei.getBytesArq()));
                        descompEsc.fechar();

                        JOptionPane.showMessageDialog(null, "Descompactado com sucesso!!!", "Finalizado", JOptionPane.OK_OPTION);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao descompactar o arquivo: " + nomeArqComp, e.getMessage(), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Arquivo a ser descompactado não existe!!!", "Erro ao descompactar o arquivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDescompactarActionPerformed

    /**
     * Cria um novo formulário e inicializa os componentes exibindo-os.
     *
     * @param args Parâmetros da lina de comando.
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JCompactador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JCompactador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JCompactador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JCompactador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JCompactador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompactar;
    private javax.swing.JButton btnDescompactar;
    // End of variables declaration//GEN-END:variables
}
