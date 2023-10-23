import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUI {
    

    private JTextField m_qAnzahl;
    private JTextField m_moeglicheSymboleOhneB;
    private JTextField m_moeglicheSymbole;
    private JTextField m_qAnfang;
    private JTextField m_qEnde;
    private JTextField m_string;

    private JTextField m_qregel;
    private JTextField m_aregel;
    private JTextField m_qstrichregel;
    private JTextField m_bregel;
    private JTextField m_richtungRegel;

    private DefaultListModel<String> regelListModel;


    public GUI() {
        JFrame frame = new JFrame("TM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600); 
        frame.setLayout(new GridLayout(0, 2));

        // Input components (int, string, string2, int2, int3)
        m_qAnzahl = addLabelAndTextField(frame, "Q Anzahl:");
        m_moeglicheSymboleOhneB = addLabelAndTextField(frame, "E:");
        m_moeglicheSymbole = addLabelAndTextField(frame, "Gama:");
        m_qAnfang = addLabelAndTextField(frame, "Anfangq:");
        m_qEnde = addLabelAndTextField(frame, "Endq:");
        m_string = addLabelAndTextField(frame, "String:");
        
        JButton calculateButton = new JButton("Calculate");
        frame.add(new JLabel());  
        frame.add(calculateButton);

        m_qregel = addLabelAndTextField(frame, "q:");
        m_aregel = addLabelAndTextField(frame, "a:");
        m_qstrichregel = addLabelAndTextField(frame, "q strich:");
        m_bregel = addLabelAndTextField(frame, "b:");
        m_richtungRegel = addLabelAndTextField(frame, "Richtung");
    
        JButton addRegelButton = new JButton("Add Regel");
        JButton fastRegelButton = new JButton( "Add Regel with Text");
        frame.add(fastRegelButton);  
        frame.add(addRegelButton);
        // "Delete Regel" button
        JButton deleteRegelButton = new JButton("Delete Regel");
        JButton clearTMButton = new JButton("Clear TM");
        frame.add(clearTMButton);  
        frame.add(deleteRegelButton);

        // List for storing regels
        regelListModel = new DefaultListModel<>();
        JList<String> regelList = new JList<>(regelListModel);
        JScrollPane regelListScrollPane = new JScrollPane(regelList);
        frame.add(regelListScrollPane);

        String text = MyFile.readFile();
        String[] lines = text.split("\n");
        for (String line : lines) {
            regelListModel.addElement(line);
        }

        // Define the action when the "Add Regel" button is pressed
        addRegelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values for the object
                if(!m_qregel.getText().isEmpty() &&
                !m_aregel.getText().isEmpty() &&
                !m_qstrichregel.getText().isEmpty() &&
                !m_bregel.getText().isEmpty() &&
                !m_richtungRegel.getText().isEmpty()){
                    String objText = m_qregel.getText() + " " + m_aregel.getText() + " " +
                            m_qstrichregel.getText() + " " + m_bregel.getText() + " " +
                            m_richtungRegel.getText();
                    regelListModel.addElement(objText);
                    MyFile.writeFile(regelListModel);
                    m_qregel.setText("");
                    m_aregel.setText("");
                    m_bregel.setText("");
                    m_richtungRegel.setText("");
                    m_qstrichregel.setText("");
                }
            }
        });

        fastRegelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                ArrayList<String> regels = new ArrayList<>();
                for (int i = 0; i < regelListModel.size(); i++) {
                    regels.add(regelListModel.get(i));
                }
                
                new inputTextBox(regelListModel).show();
            }
        });

        clearTMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (youSureWindow()){
                    regelListModel.clear();
                    MyFile.writeFile(regelListModel);
                }
            }
        });
        //Addiert Regel in Textbox wenn sie geclickt 
        regelList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedIndex = regelList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        String selectedItem[] = regelListModel.getElementAt(selectedIndex).split(" ");
                        m_qregel.setText(selectedItem[0]);
                        m_aregel.setText(selectedItem[1]);
                        m_qstrichregel.setText(selectedItem[2]);
                        m_bregel.setText(selectedItem[3]);
                        m_richtungRegel.setText(selectedItem[4]);
                    }
                }
            }
        });
        deleteRegelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String objText = m_qregel.getText() + " " + m_aregel.getText() + " " +
                        m_qstrichregel.getText() + " " + m_bregel.getText() + " " +
                        m_richtungRegel.getText();

                for (int i = 0; i < regelListModel.size(); i++) {
                    if (regelListModel.get(i).equals(objText)) {
                        regelListModel.remove(i);
                        break;
                    }

                }
                MyFile.writeFile(regelListModel);
                m_qregel.setText("");
                m_aregel.setText("");
                m_bregel.setText("");
                m_richtungRegel.setText("");
                m_qstrichregel.setText("");

            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                int qAnzahl = Integer.parseInt(m_qAnzahl.getText());
                String sigma = m_moeglicheSymboleOhneB.getText();
                String gamma = m_moeglicheSymbole.getText();
                int qAnfang = Integer.parseInt(m_qAnfang.getText());
                int qEnde = Integer.parseInt(m_qEnde.getText());
                String stringValue = "B" +m_string.getText()+"B";
                
                // Process the list of regels
                ArrayList<String> regels = new ArrayList<>();
                for (int i = 0; i < regelListModel.size(); i++) {
                    regels.add(regelListModel.get(i));
                }

                

                TM unsereTM= new TM(qAnfang,qEnde,qAnfang,gamma,sigma,regels);
                
                String result = ""+ unsereTM.calculate(stringValue);
                new Dialogbox(result, 15);
            }
        });

        frame.setVisible(true);
    }

    // Helper method to add a label and a text field to the frame
    private JTextField addLabelAndTextField(Container container, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField("");
        container.add(label);
        container.add(textField);
        return textField;
    }
    public static boolean youSureWindow() {
        int choice = JOptionPane.showConfirmDialog(null, "Sicher?", "Bestaetigen", JOptionPane.YES_NO_OPTION);

        // If the user chooses "Yes", return true; otherwise, return false
        return choice == JOptionPane.YES_OPTION;
    }
}