import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialogbox {

    private JFrame frame;
    private JTextArea textArea;
    private String[] pages;
    private int currentPage;

    public Dialogbox(String message, int linesPerPage) {
        pages = message.split("\n");
        currentPage = 0;

        frame = new JFrame("Results :)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousPage(linesPerPage);
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextPage(linesPerPage);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        showCurrentPage(linesPerPage);
    }

    private void showCurrentPage(int linesPerPage) {
        StringBuilder pageContent = new StringBuilder();
        for (int i = currentPage; i < Math.min(currentPage + linesPerPage, pages.length); i++) {
            pageContent.append(pages[i]).append("\n");
        }
        textArea.setText(pageContent.toString());
    }

    private void showNextPage(int linesPerPage) {
        if (currentPage + linesPerPage <  pages.length-1) {
            currentPage+=linesPerPage;
            showCurrentPage(linesPerPage);
        }
    }

    private void showPreviousPage(int linesPerPage) {
        if (currentPage >=  linesPerPage) {
            currentPage-=linesPerPage;
            showCurrentPage(linesPerPage);
        }
    }
}