package manipuladores;

import java.io.*;
import java.util.*;

/**
 * Escreve em um arquivo dados recebidos.
 * @author Lucas de Oliveira Silva - 15182
 */
public class EscritorDeArquivo {

    protected BufferedOutputStream bops;

    /**
     * Escritor do arquivo.
     */
    protected DataOutputStream escritor;
    protected FileOutputStream arquivo;

    /**
     * Caminho absoluto do aquivo a ser escrito.
     */
    protected String nomeArq;

    /**
     * Inicializa o escritor.
     * @param nomeArq  Caminho absoluto do aquivo a ser escrito.
     * @throws Exception Caso o caminho absoluto do aquivo seja nulo.
     */
    public EscritorDeArquivo(String nomeArq) throws Exception {
        if (nomeArq == null)
            throw new Exception("Nome nulo!!!");

        this.nomeArq = nomeArq;

        init();
    }

    private void init() throws IOException {
        try {
            this.arquivo = new FileOutputStream(this.nomeArq);
            this.bops = new BufferedOutputStream(this.arquivo);
            this.escritor = new DataOutputStream(this.bops);
        } catch (IOException ex) {
            throw new IOException("Erro ao inicializar o arquivo!!!: " + ex.getMessage());
        }
    }

    /**
     * Escreve no arquivo os dados recebidos.
     * @param bytes Array de dados a ser escrito no arquivo.
     * @throws IOException Caso de algum erro na escrita no arquivo.
     * @throws Exception Caso o array de dados seja nulo.
     */
    public void escrever(ArrayList<Byte> bytes) throws IOException, Exception {
        if (bytes == null)
            throw new Exception("Dados nulos!!!");
        
        try {
            byte[] dados = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++)
                dados[i] = bytes.get(i);

            this.escritor.write(dados);
        } catch (IOException ex) {
            throw new IOException("Erro ao escrever no arquivo!!!: " + ex.getMessage());
        }
    }
    
    /**
     * Finaliza/fecha o escritor.
     * @throws IOException Caso der erro ao fechar o escritor.
     */
    public void fechar() throws IOException {
        this.escritor.close();
        this.bops.close();
        this.arquivo.close();
    }
}
