package arvoreBinaria;

/**
 * Representação de uma árvore binaria que possui nós ordenados.
 * @author Lucas de Oliveira Silva - 15182
 * @param <tipoC> Tipo da chave da árvore binária.
 * @param <tipoD> Tipo do dado da árvore binária.
 */
public class ArvoreBinaria<tipoC extends Comparable<tipoC>, tipoD> implements Cloneable, Comparable<ArvoreBinaria<tipoC, tipoD>> {

    /**
     * Representação da árvore. 
     */
    protected NoArvoreBinaria<tipoC, tipoD> raiz;

    /**
     * Gera uma árvore de raiz nula. 
     */
    public ArvoreBinaria() {
        this.raiz = null;
    }

    /**
     * Gera uma árvore de raiz igual a passada como parâmetro.
     * @param raiz Raiz da nova árvore.
     * @throws Exception Caso o novo nó seja nulo.
     */
    public ArvoreBinaria(NoArvoreBinaria<tipoC, tipoD> raiz) throws Exception {
        if (raiz == null)
            throw new Exception("Nó nulo!!!");
        
        this.raiz = (NoArvoreBinaria<tipoC, tipoD>) raiz.clone();
    }

    /**
     * Insere ordenadamente na árvore de modo que o nó a direita de um nó tenha chave maior do que a dele.
     * @param novo Novo nó a ser inserido.
     * @throws Exception Caso o novo nó seja nulo.
     */
    public void inserir(NoArvoreBinaria<tipoC, tipoD> novo) throws Exception {
        if (novo == null)
            throw new Exception("Nó nulo!!!");

        if (this.raiz == null)
            this.raiz = (NoArvoreBinaria<tipoC, tipoD>) novo.clone();
        else {
            NoArvoreBinaria<tipoC, tipoD> foco = this.raiz;
            tipoC novaChave = novo.getChave();

            while (true) {
                NoArvoreBinaria<tipoC, tipoD> focoL = foco.getLeft();
                NoArvoreBinaria<tipoC, tipoD> focoR = foco.getRight();

                //Percorre toda a árvore de modo a achar uma folha onde o novo nó sera inserido.
                if (novaChave.compareTo(foco.getChave()) >= 0) {
                    if (focoR == null) {
                        foco.setRight(novo);
                        break;
                    }

                    foco = foco.getRight();
                } else {
                    if (focoL == null) {
                        foco.setLeft(novo);
                        break;
                    }

                    foco = foco.getLeft();
                }
            }
        }
    }

    /**
     * Insere na raiz da árvore de modo que o novo nó seja a nova raiz.
     * @param novo Novo nó a ser inserido.
     * @throws Exception Caso o novo nó seja nulo ou a raiz da árvore seja nula.
     */
    public void inserirTopo(NoArvoreBinaria<tipoC, tipoD> novo) throws Exception {
        if (novo == null)
            throw new Exception("Nó nulo!!!");
        else if (this.raiz == null)
            throw new Exception("Raiz vazia!!!");

        if (this.raiz.getChave().compareTo(novo.getChave()) >= 0)
            novo.setRight(this.raiz);
        else
            novo.setLeft(this.raiz);

        this.raiz = (NoArvoreBinaria<tipoC, tipoD>) novo.clone();
    }

    /**
     * Percorre a árvore de modo a encontrar uma posição que tera seu dado alterado. 
     * @param pos Posição a ser alterada.
     * @param novoDado Dado a ser colocado na posição passada como parâmetro.
     * @throws Exception Caso a chave a ser encontrada seja nula ou a posição não exista na árvore.
     */
    public void alterarDadoEm(tipoC pos, tipoD novoDado) throws Exception {
        if (pos == null)
            throw new Exception("Chave nula!!!");
        else if (this.raiz == null)
            throw new Exception("Posição não encontrada!!!");

        NoArvoreBinaria<tipoC, tipoD> foco = this.raiz;
        tipoC chaveFoco;

        NoArvoreBinaria<tipoC, tipoD> focoL;
        NoArvoreBinaria<tipoC, tipoD> focoR;

        //Percorre toda a árvore de modo a achar a posição do dado a ser atualizado.
        while (true) {
            chaveFoco = foco.getChave();

            if (chaveFoco.equals(pos)) {
                foco.setDado(novoDado);
                break;
            }

            focoL = foco.getLeft();
            focoR = foco.getRight();

            if (pos.compareTo(chaveFoco) >= 0) {
                if (focoR == null)
                    throw new Exception("Posição não encontrada!!!");

                foco = foco.getRight();
            } else {
                if (focoL == null)
                    throw new Exception("Posição não encontrada!!!");

                foco = foco.getLeft();
            }
        }
    }

    /**
     * Percore a árvore até encontrar a posição e retorna ela. Caso a árvore não seja nula o método repassa o escopo para outra função.
     * @param pos Posição a ser retornada.
     * @return O nó da posição a ser encontrada.
     * @throws Exception Caso a chave a ser encontrada seja nula ou a posição não exista na árvore.
     */
    public NoArvoreBinaria<tipoC, tipoD> getNo(tipoC pos) throws Exception {
        if (pos == null)
            throw new Exception("Chave nula!!!");
        else if (this.raiz == null)
            throw new Exception("Posição não encontrada!!!");

        return getNo(pos, this.raiz);
    }

    /**
     * Percorre recursivamente a árvore até encontrar o nó desejado ou chegar a uma folha.
     * @param pos Posição a ser retornada.
     * @param raiz O foco atual na árvore. 
     * @return O nó da posição a ser encontrada.
     * @throws Exception A posição não exista na árvore.
     */
    protected NoArvoreBinaria<tipoC, tipoD> getNo(tipoC pos, NoArvoreBinaria<tipoC, tipoD> raiz) throws Exception {
        if (raiz.getChave().equals(pos))
            return (NoArvoreBinaria<tipoC, tipoD>) raiz.clone();

        if (pos.compareTo(raiz.getChave()) < 0) {
            if (raiz.getLeft() == null)
                throw new Exception("Posição não encontrada!!!");

            return getNo(pos, raiz.getLeft());
        } else {
            if (raiz.getRight() == null)
                throw new Exception("Posição não encontrada!!!");

            return getNo(pos, raiz.getRight());
        }
    }

    public NoArvoreBinaria<tipoC, tipoD> getRaiz() {
        return (NoArvoreBinaria<tipoC, tipoD>) this.raiz.clone();
    }

    /**
     * Copia a instância atual da árvore.
     * @return Uma cópia da instância atual da árvore.
     */
    @Override
    public Object clone() {
        try {
            return new ArvoreBinaria<>(this);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * Inicializa o objeto de modo que ele seja igual ao modelo recebido.
     * @param arvore Modelo a ser seguido.
     * @throws Exception Caso o modelo de árvore seja nulo.
     */
    public ArvoreBinaria(ArvoreBinaria<tipoC, tipoD> arvore) throws Exception {
        if (arvore == null)
            throw new Exception("Árvore nula!!!");

        this.raiz = (NoArvoreBinaria<tipoC, tipoD>) arvore.raiz.clone();
    }

    /**
     * Retorna a posição relativa entre o parâmetro em caso de ordenação. 
     * @param arvore Árvore a ser comparada.
     * @return 0 caso as duas árvores sejam iguais, 1 caso o parâmetro seja menor e -1 caso ele seja maior.
     */
    @Override
    public int compareTo(ArvoreBinaria<tipoC, tipoD> arvore) {
        try {
            if (arvore == null)
                return 1;

            return this.raiz.compareTo(arvore.getRaiz());
        } catch (Exception ex) {
        }

        return 0;
    }
}
