package arvoreBinaria;

/**
 * Representação de um nó binario que possui nós ordenados.
 * @author Lucas de Oliveira Silva - 15182
 * @param <tipoC> Tipo da chave do nó binário.
 * @param <tipoD> Tipo do dado do nó binário.
 */
public class NoArvoreBinaria<tipoC extends Comparable<tipoC>, tipoD> implements Cloneable, Comparable<NoArvoreBinaria<tipoC, tipoD>> {

    /**
     * Chave do nó.
     */
    protected tipoC chave;

    /**
     * Dado do nó.
     */
    protected tipoD dado;

    /**
     * Nó da direita que possui uma chave maior.
     */
    protected NoArvoreBinaria<tipoC, tipoD> right;

    /**
     * Nó da esquerda que possui uma chave menor.
     */
    protected NoArvoreBinaria<tipoC, tipoD> left;
    
    /**
     * Inicializa o nó com a chave e dado passado.
     * @param chave Chave do nó.
     * @param dado Dado do nó.
     * @throws Exception Caso a chave seja nula.
     */
    public NoArvoreBinaria(tipoC chave, tipoD dado) throws Exception {
        if (chave == null)
            throw new Exception("Chave nula!!!");

        if (chave instanceof Cloneable)
            this.chave = (tipoC) chave.getClass().getMethod("clone", (Class<?>[]) null).invoke(chave, (Object[]) null);
        else
            this.chave = chave;

        if (dado != null && dado instanceof Cloneable)
            this.dado = (tipoD) dado.getClass().getMethod("clone", (Class<?>[]) null).invoke(dado, (Object[]) null);
        else
            this.dado = dado;

        this.right = null;
        this.left = null;
    }

    /**
     * Retorna o nó da direita.
     * @return Retorna o nó da direita.
     */
    public NoArvoreBinaria<tipoC, tipoD> getRight() {
        if (this.right != null)
            return (NoArvoreBinaria<tipoC, tipoD>) this.right.clone();

        return null;
    }

    /**
     * Altera o nó da  direita.
     * @param right Novo nó da direita. 
     */
    public void setRight(NoArvoreBinaria<tipoC, tipoD> right) {
        if (right != null)
            this.right = (NoArvoreBinaria<tipoC, tipoD>) right.clone();
        else
            this.right = null;
    }

    /**
     * Retorna o nó da esquerda.
     * @return Retorna o nó da esquerda.
     */
    public NoArvoreBinaria<tipoC, tipoD> getLeft() {
        if (this.left != null)
            return (NoArvoreBinaria<tipoC, tipoD>) this.left.clone();

        return null;
    }

    /**
     * Altera o nó da  esquerda.
     * @param left Novo nó da esquerda. 
     */
    public void setLeft(NoArvoreBinaria<tipoC, tipoD> left) {
        if (left != null)
            this.left = (NoArvoreBinaria<tipoC, tipoD>) left.clone();
        else
            this.left = null;
    }

    /**
     * Retorna o dado do nó.
     * @return O dado do nó.
     * @throws Exception
     */
    public tipoD getDado() throws Exception {
        if (this.dado != null && this.dado instanceof Cloneable)
            return (tipoD) this.dado.getClass().getMethod("clone", (Class<?>[]) null).invoke(this.dado, (Object[]) null);
        else
            return this.dado;
    }

    /**
     * Altera o dado do nó.
     * @param dado Novo dado do nó.
     * @throws Exception
     */
    public void setDado(tipoD dado) throws Exception {
        if (dado != null && dado instanceof Cloneable)
            this.dado = (tipoD) dado.getClass().getMethod("clone", (Class<?>[]) null).invoke(dado, (Object[]) null);
        else
            this.dado = dado;
    }

    /**
     * Retorna a chave do nó.
     * @return A chave do nó.
     * @throws Exception
     */
    public tipoC getChave() throws Exception {
        if (this.chave instanceof Cloneable)
            return (tipoC) this.chave.getClass().getMethod("clone", (Class<?>[]) null).invoke(this.chave, (Object[]) null);
        else
            return this.chave;
    }

    /**
     * Altera a chave do nó.
     * @param chave Nova chave do nó.
     * @throws Exception
     */
    public void setChave(tipoC chave) throws Exception {
        if (chave == null)
            throw new Exception("Chave nula!!!");

        if (chave instanceof Cloneable)
            this.chave = (tipoC) chave.getClass().getMethod("clone", (Class<?>[]) null).invoke(chave, (Object[]) null);
        else
            this.chave = chave;
    }

    /**
     * Copia a instância atual de nó.
     * @return Uma cópia da instância atual de nó.
     */
    @Override
    public Object clone() {
        try {
            return new NoArvoreBinaria<>(this);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * Inicializa o objeto de modo que ele seja igual ao modelo recebido.
     * @param no Modelo a ser seguido.
     * @throws Exception Caso o modelo de nó seja nulo.
     */
    public NoArvoreBinaria(NoArvoreBinaria<tipoC, tipoD> no) throws Exception {
        if (no == null)
            throw new Exception("Nó nulo!!!");

        if (this.chave instanceof Cloneable) {
            tipoC chaveNova = no.getChave();
            this.chave = (tipoC) chaveNova.getClass().getMethod("clone", (Class<?>[]) null).invoke(chaveNova, (Object[]) null);
        } else
            this.chave = no.getChave();

        tipoD dadoNovo = no.getDado();
        if (dadoNovo != null && dadoNovo instanceof Cloneable)
            this.dado = (tipoD) dadoNovo.getClass().getMethod("clone", (Class<?>[]) null).invoke(dadoNovo, (Object[]) null);
        else
            this.dado = dadoNovo;

        if (no.getRight() != null)
            this.right = (NoArvoreBinaria<tipoC, tipoD>) no.getRight().clone();
        else
            this.right = null;

        if (no.getLeft() != null)
            this.left = (NoArvoreBinaria<tipoC, tipoD>) no.getLeft().clone();
        else
            this.left = null;
    }

    /**
     * Retorna a posição relativa entre o parâmetro em caso de ordenação. 
     * @param no Nó a ser comparada.
     * @return 0 caso os dois nó sejam iguais, 1 caso o parâmetro seja menor e -1 caso ele seja maior.
     */
    @Override
    public int compareTo(NoArvoreBinaria<tipoC, tipoD> no) {
        try {
            if (no == null)
                return 1;

            return this.chave.compareTo(no.getChave());
        } catch (Exception ex) {
        }

        return 0;
    }
    
    /**
     * Verifica se o nó é uma folha de uma árvore.
     * @return Verdadeiro caso os dois sub nós sejam nulos e falso caso contrario.
     */
    public boolean isFolha() {
	return this.left == null && this.right == null;
    }
}
