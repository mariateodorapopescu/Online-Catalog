import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static javax.swing.JOptionPane.OK_OPTION;

public class Fereastra1 extends JFrame {
    private JLabel texta;
    private JTextField textb;
    private JComboBox a;
    private JTextArea b;
    private JButton btn1, btn2;
    private JLabel label;
    private JButton btn3;
    private JButton btn4;

    public Fereastra1(String text, Catalog catalog,ArrayList<Notification> notification) {
        super(text);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.lightGray);
        setLayout(new FlowLayout());
        Box b1, b2;
        b1 = Box.createVerticalBox();
        b2 = Box.createHorizontalBox();
        label = new JLabel("Pentru a vedea notele aceluiasi student la o alta materie la care este inrolat, mai apasati o data pe butonul <<Cautare>>!");
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        b1.add(label);
        texta = new JLabel("Student:");
        b2.add(texta);
        textb = new JTextField(25);
        b2.add(textb);
        btn1 = new JButton("Cautare");
        b2.add(btn1);
        a = new JComboBox();
        Dimension x2 = new Dimension(4, 30);
        b = new JTextArea(4, 30);
        b.setPreferredSize(x2);
        b.setEditable(false);
        b2.add(a);
        btn2 = new JButton("Mai mult");
        b2.add(btn2);
        b1.add(b2);
        b1.add(b);
        add(b1);
        btn1.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       String text = textb.getText();
                                       String[] words = text.split("\\s+");
                                       for (int i = 0; i < words.length; i++) {
                                           words[i] = words[i].replaceAll("[^\\w]", "");
                                       }
                                       String first = words[0];
                                       StringBuffer sb = new StringBuffer();
                                       for (int i = 1; i < words.length; i++) {
                                           sb.append(words[i]);
                                           if (i < words.length - 1)
                                               sb.append(" ");
                                       }
                                       String last = new String(sb);
                                       Student s = new Student(first, last);
                    for (Course i : catalog.getCourses())
                    {
                        for (Group j : i.getGroups().values())
                        {
                            for (Student k : j)
                            {
                                if (s.toString().compareTo(k.toString()) == 0)
                                {
                                    a.addItem(i.getCourse());
                                }
                            }
                        }
                    }
                                   }
                               }
        );

        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.setText("");
                String text = textb.getText();
                String[] words = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    words[i] = words[i].replaceAll("[^\\w]", "");
                }
                String first = words[0];
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < words.length; i++) {
                    sb.append(words[i]);
                    if (i < words.length - 1)
                        sb.append(" ");
                }
                String last = new String(sb);
                Student s = new Student(first, last);
                StringBuffer data1 = new StringBuffer("Profesor: ");
                Course ceva = null;
                for (Course i : catalog.getCourses()) {
                    if (i.getCourse().compareTo(a.getItemAt(a.getSelectedIndex()).toString()) == 0) {
                        ceva = i;
                    }
                }
                for (Group j : ceva.getGroups().values()) {
                    for (Student k : j) {
                        if (s.toString().compareTo(k.toString()) == 0) {
                            data1.append(ceva.getTeacher().toString());
                        }
                    }
                }
                data1.append("\n");
                data1.append("Asistenti: ");
                for (Group j : ceva.getGroups().values()) {
                    for (Student k : j) {
                        if (s.toString().compareTo(k.toString()) == 0) {
                            for (Assistant at : ceva.getAssistants())
                            {
                                data1.append(at.toString() + "; ");
                            }
                        }
                    }
                }
                data1.append("\n");
                data1.append("Asistent: ");
                for (Group j : ceva.getGroups().values())
                {
                    for(Student k : j)
                    {
                        if (s.toString().compareTo(k.toString()) == 0)
                        {
                            data1.append(j.getAssistant().toString());
                        }
                    }
                }
                data1.append("\n");
                data1.append("Note:");
                for (Grade j : ceva.getGrades())
                {
                    if (j.getStudent().toString().compareTo(s.toString()) == 0)
                    {
                        data1.append(" Parcurs: ");
                        data1.append(j.getPartialScore());
                        data1.append("; Examen: ");
                        data1.append(j.getExamScore());
                        data1.append("; Total: ");
                        data1.append(j.getTotal());
                    }
                }
                String data = new String(data1);
                b.append(data);
                a.removeAllItems();
            }
        });
        btn4 = new JButton("Inapoi la optiuni");
        b1.add(btn4);
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Cautare student",
                        "Validare note",
                        "Vizualizare notificari"};
                final ImageIcon icon;
                try {
                    icon = new ImageIcon(new URL("https://icons.iconarchive.com/icons/paomedia/small-n-flat/48/sign-warning-icon.png"));
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                int n = JOptionPane.showOptionDialog(null,
                        "Bun venit la Catalogul virtual! "
                                + "Ce doriti sa faceti?\n----------------------------------------------------------------------------\n" +
                                "P.S. Cand cautati/va logati, va rugam sa scrieti numele corect, fara diacritice, <Nume Prenume>!",
                        "Catalog virtual",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon,
                        options,
                        options[2]);
                if(n == JOptionPane.YES_OPTION){
                    Fereastra1 f1 = new Fereastra1("Cautare student",catalog, notification);
                }else if (n == JOptionPane.NO_OPTION){
                    String result = (String)JOptionPane.showInputDialog(null,"Nume si prenume:","Autentificare",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
                    Fereastra2 f2 = new Fereastra2("Validare note", result, catalog, notification);
                }else {
                    String results = (String)JOptionPane.showInputDialog(null,"Nume si prenume:","Autentificare",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
                    Fereastra3 f3 = new Fereastra3("Validare Notificari", results, catalog, notification);
                }
            }
        });
        show();
        pack();
    }
}

