public class Command {
    private Type type;
    private int n;
    private int m;
    private int q;

    public Command() {
        this.n = 0;
        this.m = 0;
        this.q = 0;
    }

    public Command(Type type, int n, int m, int q){
        this.type = type;
        this.n = n;
        this.m = m;
        this.q = q;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    @Override
    public String toString() {
        switch (type){
            case Z:  return "Z(" + n + ")";
            case S:  return "S(" + n + ")";
            case T:  return "T(" + n + "," + m + ")";
            default: return "J(" + n + "," + m + "," + q + ")";
        }
    }
}

