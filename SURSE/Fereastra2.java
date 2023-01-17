import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fereastra2 extends JFrame {
    private JLabel texta;
    private JComboBox textb;
    private JButton btn2;
    private JTextArea b;
    private JButton buton;
    private JButton btn4;
    public Fereastra2(String text, String nume, Catalog catalog, ArrayList<Notification> notification) {
        super(text);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        getContentPane().setBackground(Color.lightGray);
        setLayout(new FlowLayout());

        String[] words = nume.split("\\s+");
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
        Teacher t = new Teacher(first, last);
        Assistant test = new Assistant(first, last);
        texta = new JLabel("Cursuri:");
        add(texta);
        textb = new JComboBox();
        add(textb);
        btn2 = new JButton("Vizualizare note");
        add(btn2);
        Dimension x2 = new Dimension(4,30);
        b = new JTextArea(35,30);
        b.setPreferredSize(x2);
        b.setEditable(false);
        add(b);
        buton = new JButton("Validare note");
        add(buton);
        for (Course i : catalog.getCourses()) {
            if (i.getTeacher().toString().compareTo(t.toString()) == 0)
            {
                textb.addItem(i.getCourse());
            }
            else
            {
                for (Assistant at : i.getAssistants())
                {
                    if (at.toString().compareTo(test.toString())== 0)
                    {
                        textb.addItem(i.getCourse());
                    }
                }
            }
        }
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuffer data1 = new StringBuffer();
                for (Course i : catalog.getCourses()) {
                    if (i.getTeacher().toString().compareTo(t.toString()) == 0)
                    {
                        if ((textb.getItemAt(textb.getSelectedIndex())).toString().compareTo(i.getCourse())==0)
                        {
                            for (Grade j : i.getGrades())
                            {
                                data1.append(j.getStudent().toString() + ": " + j.getExamScore() + "\n");
                            }
                        }
                    }
                    else
                    {
                        for (Assistant at : i.getAssistants())
                        {
                            if (at.toString().compareTo(test.toString())== 0)
                            {
                                if ((textb.getItemAt(textb.getSelectedIndex())).toString().compareTo(i.getCourse())==0)
                                {
                                    for (Grade j : i.getGrades())
                                    {
                                        for (Group grr : i.getGroups().values())
                                        {
                                            if (grr.contains(j.getStudent())&&grr.getAssistant().toString().compareTo(at.toString())==0)
                                            {
                                                data1.append(j.getStudent().toString() + ": " + j.getPartialScore() + "\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                String data = new String(data1);
                b.append(data);
            }
        });
        buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b.setText("Validarea este in curs de realizare...");
                String curs = textb.getItemAt(textb.getSelectedIndex()).toString();
                Course cursul = null;
                for (Course i : catalog.getCourses()) {
                    if (i.getCourse().compareTo(curs) == 0) {
                        cursul = i;
                    }
                }
                ScoreVisitor sc = new ScoreVisitor();
                sc.setCatalog(catalog);
                Notification f = new Notification(null);
                            for (Student ss : cursul.getAllStudents())
                            {
                                    catalog.addObserver(ss.getFather());
                                    catalog.addObserver(ss.getMother());
                                    ss.getFather().setCatalog(catalog);
                                    ss.getMother().setCatalog(catalog);
                                    ss.getMother().update(f);
                                    ss.getFather().update(f);
                            }
                        if (cursul.getTeacher().toString().compareTo(t.toString()) == 0) {
                                cursul.getTeacher().accept(sc);
                                    for (Student sss : cursul.getAllStudents())
                                    {
                                        f = new Notification(cursul.getAllStudentGrades().get(sss));
                                        f.getGrade().setOk(1);
                                        f.getGrade().setCourse(cursul.getCourse());
                                        notification.add(f);
                                    }
                            }
                            else
                            {
                            for (Assistant att : cursul.getAssistants()) {
                                if (att.toString().compareTo(test.toString()) == 0) {
                                        att.accept(sc);
                                                for (Group j : cursul.getGroups().values())
                                                {
                                                    if (j.getAssistant().toString().compareTo(att.toString())==0)
                                                    {
                                                        for (Student sst : j) {
                                                            for (Grade gg : cursul.getGrades()) {
                                                                if (gg.getStudent().toString().compareTo(sst.toString()) == 0) {
                                                                    f = new Notification(gg);
                                                                    f.getGrade().setOk(2);
                                                                    f.getGrade().setCourse(cursul.getCourse());
                                                                    notification.add(f);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                b.setText("Validare realizata cu succes!");
            }
        });
        btn4 = new JButton("Inapoi la optiuni");
        add(btn4);
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
                    Fereastra1 f1 = new Fereastra1("Cautare student",catalog,notification);
                }else if (n == JOptionPane.NO_OPTION){
                    String result = (String)JOptionPane.showInputDialog(null,"Nume si prenume:","Autentificare",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
                    Fereastra2 f2 = new Fereastra2("Validare note", result, catalog,notification);
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
