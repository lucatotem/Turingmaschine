public class Regel {
    public Regel(int _q, char _a,int _qstrich, char _b, char _D ){
        m_q = _q;
        m_a = _a;
        m_qstrich = _qstrich;
        m_b= _b;
        m_D = _D;
    }
    public int getM_q() {
        return m_q;
    }
    public int getM_a() {
        return m_a;
    }
    public int getM_b() {
        return m_b;
    }
    public char getM_D() {
        return m_D;
    }
    public int getM_qstrich() {
        return m_qstrich;
    }
    private int m_q; // Zustand q
    private char m_a; // Symbol gerade
    private int m_qstrich; // Zustand in dem wir gehen sollen 
    private char m_b; // Symbol mit dem wir ueberschreiben
    private char m_D; //Richtung

}
