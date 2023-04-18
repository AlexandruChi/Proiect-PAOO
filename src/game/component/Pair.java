package game.component;

public class Pair<T, T1> {
    private T left;
    private T1 right;

    public Pair(T left, T1 right) {
        this.left = left;
        this.right = right;
    }

    public Pair(Pair<T, T1> pair) {
        this.left = pair.left;
        this.right = pair.right;
    }

    public T getLeft() {
        return left;
    }

    public T1 getRight() {
        return right;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public void setRight(T1 right) {
        this.right = right;
    }
}
