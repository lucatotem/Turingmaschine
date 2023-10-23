import java.util.ArrayList;
import java.util.List;
public class TM {


    public TM(int _anfangszustandindex, int _endzustandinxed, int _anzahlZustand, String _gamma, String _sigma, ArrayList<String> _Regels){
        m_Regels = new ArrayList<>();
        m_anfangszustandindex = _anfangszustandindex;
        m_jetztq = m_anfangszustandindex;
        m_endzustandinxed = _endzustandinxed;
        m_anzahlZustand = _anzahlZustand;
        m_pointer = 1;
        m_gamma = _gamma;
        m_sigma = _sigma;
        String temp[];
        for (int i =0;i<_Regels.size();i++){
            temp = _Regels.get(i).split(" ");
            m_Regels.add(new Regel(Integer.parseInt(temp[0]), temp[1].charAt(0), Integer.parseInt(temp[2]),temp[3].charAt(0) , temp[4].charAt(0)));
        }
    }

    public boolean checkAlphabetandPosition(String _toberun){
        for (int i=0; i< _toberun.length();i++){
            if (m_sigma.indexOf(_toberun.charAt(i))==-1) return false;
        }
        for (Regel regel : m_Regels) {
            if("LRN".indexOf(regel.getM_D())==-1) return false;
        }
        return true;
    }

    //Method das unsere String durch CalculateStep() umformt mit der Position des Pointers

   public ArrayList<String> calculate(String _toberun){
        ArrayList<String> temp = new ArrayList<>();
        int indexcap = 0;
        String current = insertString(_toberun, m_pointer, " [" +m_jetztq+"] ")+"\n";// hinzufuegt pointer mit anfangzustand unsere jetztigen zustand zu string
        temp.add(current); // ersten String zu allen hinzufuegen
        while(m_jetztq != m_endzustandinxed && indexcap < 1000 && current != "error"){       
            current = calculateStep(current);
            temp.add(current);
        }
        temp.add("Result:\n"+current.replace("[" + m_jetztq +"] ", "")) ;
        return temp;
    }

    private String calculateStep(String _toberun){
        String stepString =  _toberun.replace(" [" + m_jetztq +"] ", "" );
        for(int i=0; i<m_Regels.size();i++){
            if(m_Regels.get(i).getM_q() == m_jetztq){
                if(stepString.charAt(m_pointer)==m_Regels.get(i).getM_a()){
                    stepString = stepString.substring(0, m_pointer) + stepString.substring(m_pointer+1); // loescht symbol am pointer

                    stepString = TM.insertString(stepString, m_pointer, (Character.toString(m_Regels.get(i).getM_b()))); //fuegt neues Symbol am pointer

                    if (m_Regels.get(i).getM_D()=='L')  //pointer bewegen wenn links dann 1 abziehen
                        if(m_pointer != 0) {
                            m_pointer--;
                        }else{
                            stepString = "B"+stepString;
                        }
                    else if (m_Regels.get(i).getM_D()=='R') 
                        if(m_pointer+2 >= stepString.length()){
                            m_pointer++;
                            stepString = insertString(stepString, stepString.length()-1, "B");
                        } 
                        else{
                            m_pointer++;
                        }//1 addieren
                    else ; // ist N weil Richtungen geprueft sind dann stehen bleiben

                    m_jetztq = m_Regels.get(i).getM_qstrich();// setzt  naechsten zustand
                    stepString = insertString(stepString, m_pointer,  " [" +m_jetztq+"] ");

                    return stepString;
                }
            }
        }
        return "error";
    }


    private int m_jetztq;
    private int m_pointer;
    private int m_anfangszustandindex;
    private int m_endzustandinxed;
    private int m_anzahlZustand;
    private String m_gamma;
    private String m_sigma;
    private ArrayList<Regel> m_Regels;


    public static String insertString(String insertee, int index, String inserted){

        StringBuilder builder = new StringBuilder(insertee);
        builder.insert(index, inserted);
        return builder.toString();
    }
}

