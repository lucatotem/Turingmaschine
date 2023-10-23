import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class inputTextBox {

    private JFrame frame;
    private JTextArea textArea;
    private JButton applyButton;

    public inputTextBox(DefaultListModel<String> _regelListModel) {
        frame = new JFrame("Regel input in Lines");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        String temp = "";
        for (int i =0;i<_regelListModel.size();i++){
            temp = temp + _regelListModel.get(i)+ "\n";
        }
        textArea.setText(temp);

        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                String[] lines = text.split("\n");
                _regelListModel.clear();
                for (String line : lines) {
                    _regelListModel.addElement(line);
                }
                MyFile.writeFile(_regelListModel);
                frame.dispose();
            }

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyButton);

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();

    }

    public void show() {
        frame.setVisible(true);
    }
}