package manipuladores;

import java.util.*;
import java.util.Map.*;
import arvoreBinaria.*;

/**
 * Gera as estruturas que representam os passos da compactação do arquivo.
 * @author Lucas de Oliveira Silva - 15182
 */
public class GeradorDeHuffman {

    private final ArrayList<NoArvoreBinaria<Long, Character>> ocorrencias;

    /**
     * Inicializa o objeto associando-o a uma tabela de ocorrências. 
     * @param tabelaDeOcorrencia Tabela de ocorrências do arquivo. 
     * @throws Exception Casso a tabela seja nula ou estava vazia.
     */
    public GeradorDeHuffman(HashMap<Character, Long> tabelaDeOcorrencia) throws Exception {
        if (tabelaDeOcorrencia == null)
            throw new Exception("Tabela nula!!!");
        else if (tabelaDeOcorrencia.isEmpty())
            throw new Exception("Tabela vazia!!!");

        this.ocorrencias = new ArrayList<>();

        Set<Entry<Character, Long>> entrys = tabelaDeOcorrencia.entrySet();
        for (Entry<Character, Long> entry : entrys)
            this.ocorrencias.add(new NoArvoreBinaria<>(entry.getValue(), entry.getKey()));
    }

    /**
     * Gera uma árvore de Huffman baseado na tabela de ocorrências.
     * @return Árvore de Huffman baseado na tabela.
     * @throws Exception Caso de algum erro na geração.
     */
    public ArvoreBinaria<Long, Character> gerarArvoreDeHuffman() throws Exception {
        int tam = this.ocorrencias.size();

        if (tam == 1)
            return new ArvoreBinaria<>(this.ocorrencias.get(0));

        NoArvoreBinaria<Long, Character> aux, ultimo, penultimo;

        for (; tam > 1; tam = this.ocorrencias.size()) {
            Collections.sort(this.ocorrencias, (x, y) -> {
                try {
                    return y.getChave().compareTo(x.getChave());
                } catch (Exception ex) {
                }

                return 0;
            });

            ultimo = this.ocorrencias.get(tam - 1);
            penultimo = this.ocorrencias.get(tam - 2);

            aux = new NoArvoreBinaria<>(ultimo.getChave() + penultimo.getChave(), '\0');

            if (ultimo.getChave() > penultimo.getChave()) {
                aux.setLeft(ultimo);
                aux.setRight(penultimo);
            } else {
                aux.setLeft(penultimo);
                aux.setRight(ultimo);
            }

            this.ocorrencias.remove(--tam);
            this.ocorrencias.remove(tam - 1);

            this.ocorrencias.add(aux);
        }

        return new ArvoreBinaria<>(this.ocorrencias.get(0));
    }

    /**
     * Gera uma tabela associando um código representado por um array de boolean e um character.
     * @return Tabela de códigos.
     * @throws Exception 
     */
    public HashMap<Character, ArrayList<Boolean>> gerarTabelaDeHuffman() throws Exception {
        return gerarCodigosDeHuffman(gerarArvoreDeHuffman());
    }

    private HashMap<Character, ArrayList<Boolean>> gerarCodigosDeHuffman(ArvoreBinaria<Long, Character> arvore) throws Exception {
        HashMap ret = new HashMap<>();

        if (arvore.getRaiz().getDado() != '\0') {
            ArrayList codAtual = new ArrayList<>();
            codAtual.add(false);

            ret.put(arvore.getRaiz().getDado(), codAtual);
        } else
            gerarCodigosDeHuffman(new ArrayList<>(), arvore.getRaiz(), ret);

        return ret;
    }

    private void gerarCodigosDeHuffman(ArrayList codAtual, NoArvoreBinaria<Long, Character> raiz, HashMap tabela) throws Exception {
        if (raiz.getDado() != '\0') {
            tabela.put(raiz.getDado(), codAtual.clone());
            return;
        }

        if (raiz.getLeft() != null) {
            ArrayList aux = (ArrayList) codAtual.clone();
            aux.add(false);

            gerarCodigosDeHuffman(aux, raiz.getLeft(), tabela);
        }

        if (raiz.getRight() != null) {
            ArrayList aux = (ArrayList) codAtual.clone();
            aux.add(true);

            gerarCodigosDeHuffman(aux, raiz.getRight(), tabela);
        }
    }
}

