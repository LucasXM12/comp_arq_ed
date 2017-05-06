package manipuladores;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

/**
 * Lê um arquivo e gera um vetor com cada byte do arquivo, podendo também gerar uma tabela de ocorrencia dos bytes. 
 * @author Lucas de Oliveira Silva - 15182
 */
public class LeitorDeArquivo {

    protected BufferedInputStream bips;
    protected FileInputStream arquivo;

    /**
     * Leitor do arquivo.
     */
    protected DataInputStream leitor;

    /**
     * Vetor de bytes do aquivo.
     */
    protected byte[] bytesArq;

    /**
     * Caminho absoluto do aquivo a ser lido.
     */
    protected String nomeArq;

    /**
     * Inicializa e lê do arquivo todos os dados e armazena em um vetor. 
     * @param nomeArq Caminho absoluto do aquivo a ser lido.
     * @throws Exception Caso o caminho absoluto do aquivo seja nulo.
     */
    public LeitorDeArquivo(String nomeArq) throws Exception {
        if (nomeArq == null)
            throw new Exception("Nome nulo!!!");

        this.nomeArq = nomeArq;

        init();
    }

    private void init() throws IOException {
        try {
            this.arquivo = new FileInputStream(this.nomeArq);
            this.bips = new BufferedInputStream(this.arquivo);
            this.leitor = new DataInputStream(bips);

            this.ler();
        } catch (IOException ex) {
            throw new IOException("Erro ao inicializar o arquivo!!!: " + ex.getMessage());
        }
    }

    private void ler() throws IOException {
        try {
            this.bytesArq = new byte[this.leitor.available()];            
            this.leitor.mark(this.bytesArq.length + 1);
            this.leitor.read(this.bytesArq);
            this.leitor.reset();
            
            this.leitor.close();
            this.bips.close();
            this.arquivo.close();
        } catch (IOException ex) {
            throw new IOException("Erro ao ler o arquivo!!!: " + ex.getMessage());
        }
    }

    /**
     * Gera uma tabela de ocorrência associando um dado do arquivo a frequência com que ele aparence no mesmo.
     * @return Uma tabela de ocorrência.
     */
    public HashMap<Character, Long> gerarTabelaDeOcorrencia()  {
        ArrayList<Character> arrayChar = new ArrayList<>();
        HashMap<Character, Long> ret = new HashMap<>();

        for (Byte b : this.bytesArq)
            arrayChar.add((char) (b & 0xFF));

        for (Object c : arrayChar.toArray())
            if (!ret.containsKey((Character) c))
                ret.put((Character) c, (long) Collections.frequency(arrayChar, c));

        if (ret.isEmpty()) 
            ret.put((Character) '\0', (long) 1);     
        
        return ret.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).collect(
                Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> x, LinkedHashMap::new));
    }

    /**
     * Retorna os dados do aquivo na forma de um vetor de bytes. 
     * @return Vetor de bytes do arquivo.
     */
    public byte[] getBytesArq() {
        return this.bytesArq;
    }
}
