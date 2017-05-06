package manipuladores;

import java.util.*;

/**
 * Possui metodos para a compactação e decompactação de dados. 
 * @author Lucas de Oliveira Silva - 15182
 */
public class Compactador {
    
    /**
     * Adiciona um bit ao BitSet.
     * @param cod BitSet a ser alterado.
     * @param valor Novo bit a ser adicionado.
     * @return O novo BitSet com o novo valor adicionado.
     * @throws Exception Caso o codigo seja nulo.
     */
    public static BitSet adicionarBit(BitSet cod, boolean valor) throws Exception {
        if (cod == null)
            throw new Exception("Código nulo!!!");
           
        int i;
        BitSet ret = new BitSet(cod.size() + 1);
        for (i = 0; i < cod.size(); i++)
            ret.set(i, cod.get(i));
        
        ret.set(++i, valor);
        
        return  ret;
    }

    /**
     * Adiciona os valores de um array de boolean na frente de outro array.
     * @param destino Array a ser alterado.
     * @param novosBits Array fonte que sera adicionado ao original.
     * @throws Exception Caso um dos arrays sejam nulos.
     */
    public static void addBools(ArrayList<Boolean> destino, ArrayList<Boolean> novosBits) throws Exception {
        if (destino == null || novosBits == null)
            throw new Exception("Array(s) nulo(s)!!!");
        
        novosBits.stream().forEach(b -> destino.add(b));
    }

    /**
     * Gera um número a partir de um código binário representado por um array de boolean. 
     * @param cod Array de boolean que representa um número binário.
     * @return Um número decimal.
     * @throws Exception Caso o código seja nulo.
     */
    public static byte boolsToByte(ArrayList<Boolean> cod) throws Exception {
        if (cod == null)
            throw new Exception("Lista nula!!!");

        byte ret = 0;

        int max = cod.size() < 8 ? cod.size() : 8;
        for (int i = 0, val = 128; i < max; i++, val /= 2) {
            ret += val * (cod.get(0) ? 1 : 0);
            cod.remove(0);
        }

        return ret;
    }
    
    /**
     * Gera uma representação em array de boolean de um inteiro.
     * @param num Número a ser transformado em array de boolean.
     * @return Representação em array de boolean do número passado.
     * @throws Exception Caso o número seja menor que zero.
     */
    public static ArrayList<Boolean> intToBools(int num) throws Exception {
        if (num < 0)
            throw new Exception("Número menor que zero!!!");
        
        ArrayList<Boolean> ret = new ArrayList<>();
        
        for (int exp = 7; exp >= 0; exp--) {
            
            int valorCasa = (int) Math.pow(2, exp);
            if (num - valorCasa >= 0) {
                num -= valorCasa;
                ret.add(true);
            } else 
                ret.add(false);
        }
        
        return ret;
    }

    /**
     * Adiciona em um array de bytes uma representação em 4 bytes de um inteiro.
     * @param num Número a ser gravado.
     * @param cods Array de boolean onde o inteiro sera gravado
     * @throws Exception Caso o número seja menor que zero ou o array seja nulo.
     */
    public static void gravarInt(int num, ArrayList<Byte> cods) throws Exception {
        if (num < 0)
            throw new Exception("Número inválido!!!");
        else if (cods == null)
            throw new Exception("Lista nula!!!");

        for (int multi = (int) Math.pow(256, 3); multi >= 1; multi /= 256) {

            byte byteAtual = 0;
            for (int exp = 7; exp >= 0; exp--) {

                int valorCasa = (int) Math.pow(2, exp);
                if (num - multi * valorCasa >= 0) {
                    num -= multi * valorCasa;
                    byteAtual += valorCasa;
                }
            }

            cods.add(byteAtual);
        }
    }

    /**
     * Gera um array de bytes representando o arquivo compactado a partir de uma tabela de códigos e um arquivo original.
     * @param bytesArq Arquivo a ser compactado.
     * @param tabelaCods Tabela de referência para gerar o código do arquivo compactado. 
     * @return Array de bytes representando o arquivo compactado.
     * @throws Exception Caso o vetor de dados seja nulo, ou a tabela de códigos esteja inválida ou der erro ao compactar. 
     */
    public static ArrayList<Byte> gerarArquivoCompactado(byte[] bytesArq, HashMap<Character, ArrayList<Boolean>> tabelaCods) throws Exception {
        if (bytesArq == null)
            throw new Exception("Dado a compactar nulo!!!");
        else if (tabelaCods == null)
            throw new Exception("Tabela de códigos nula!!!");
        else if (bytesArq.length > 0 && tabelaCods.isEmpty())
            throw new Exception("Impossível de compactar pois não há um lista de códigos válida!!!");
        
        BitSet textoCompactado = new BitSet(8 * bytesArq.length); 
               
        ArrayList<Byte> valores = new ArrayList<>();       

        gravarInt(bytesArq.length, valores);

        tabelaCods.entrySet().stream().map(entry -> {
            valores.add((byte) (char) entry.getKey());
            return entry;
        }).forEach(entry -> entry.getValue().stream().forEach(b -> valores.add((byte) (b ? 1 : 0))));

        valores.add((byte) 3);
        
        ArrayList<Boolean> byteNovo = new ArrayList<>();
        for (byte b : bytesArq) {
            addBools(byteNovo, tabelaCods.get((char) (b & 0xFF)));
            
            if (byteNovo.size() >= 8)
                valores.add(boolsToByte(byteNovo));           
        }
        
        for (int pos = 0; pos < textoCompactado.length(); pos++) 
            textoCompactado.set(valores.get(pos));           

        while (!byteNovo.isEmpty())
            valores.add(boolsToByte(byteNovo));

        return valores;
    }

    /**
     * Gera um array de bytes representando o arquivo descompactado (original) a partir de um vetor de bytes representando o arquivo compactado.
     * @param bytesArq Vetor de bytes representando o arquivo compactado.
     * @return Array de bytes representando o arquivo descompactado ou seja o original.
     * @throws Exception Caso o vetor de dados seja nulo ou com tamanho inválido.
     */
    public static ArrayList<Byte> gerarArquivoDescompactado(byte[] bytesArq) throws Exception {
        if (bytesArq == null)
            throw new Exception("Vetor nulo!!!");
        else if (bytesArq.length < 4)
            throw new Exception("Dados insuficientes no vetor!!!");
        
        BitSet bitsArq = new BitSet(0); 
                                
        ArrayList<Byte> ret = new ArrayList<>();      
        try {      
            HashMap<ArrayList<Boolean>, Character> tabelaCods = new HashMap<>();       
            ArrayList<Boolean> byteNovo = new ArrayList<>();
                  
            int foco, numCharsOrig = lerInt(0, bytesArq);                  
            
            for (foco = 4; bytesArq[foco] != 3; ) {
                char charCod = (char) bytesArq[foco];                         
                                
                for (++foco; bytesArq[foco] == 0 || bytesArq[foco] == 1; foco++)
                    byteNovo.add(bytesArq[foco] != 0);
                
                tabelaCods.put((ArrayList<Boolean>) byteNovo.clone(), charCod);
                byteNovo.clear();
            }
            
            ArrayList<Boolean> bitsParaDec = new ArrayList<>();            
            for (++foco; foco < bytesArq.length; foco++) 
                addBools(bitsParaDec, intToBools(bytesArq[foco] & 0xFF));            
            
            for (Boolean b : bitsParaDec) 
                adicionarBit(bitsArq, b);              
                    
            while (bitsParaDec.size() > 0 && numCharsOrig > 0) {
                byteNovo.add(bitsParaDec.get(0));
                bitsParaDec.remove(0);
                
                if (tabelaCods.containsKey(byteNovo)) {
                    ret.add((byte) (char) tabelaCods.get(byteNovo));
                    byteNovo.clear();
                    numCharsOrig--;                 
                }
            }  
        } catch (Exception ex) {
            throw new Exception("Erro ao descompactar!!!");
        }

        return ret;
    }
    
    /**
     * Obtem um inteiro gravado em um vetor de bytes.
     * @param indice Indice do começo do inteiro.
     * @param bytesArq Vetor de bytes daonde o inteiro sera obtido. 
     * @return O valor a partir da posição passsada representada como inteiro.
     * @throws Exception
     */
    protected static int lerInt(int indice, byte[] bytesArq) throws Exception {
        if (indice < 0)
            throw new Exception("Indice inválido!!!");
        else if (indice + 4 >= bytesArq.length - 1)
            throw new Exception("Bytes insuficientes para a leitura!!!");
        
        int ret = 0;
        for (int exp = 3; exp >= 0; exp--, indice++)
            ret += (bytesArq[indice] & 0xFF) * Math.pow(256, exp);  
        
        return ret;
    }
}
