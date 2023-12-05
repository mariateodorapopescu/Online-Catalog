import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fereastra3 extends JFrame {
            private JLabel a;
    private JTextArea textb;
    private JButton btn4;
   public Fereastra3(String text, String nume, Catalog catalog, ArrayList<Notification> notification) {
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
       Parent t = new Parent(first, last);
       ScoreVisitor sc = new ScoreVisitor();
       sc.setCatalog(catalog);
       catalog.addObserver(t);
       t.setCatalog(catalog);
       t.setObservator(catalog);
       a = new JLabel("Aveti urmatoarele notificari:");
       Box box = Box.createVerticalBox();
       add(box);
       a.setAlignmentX(JLabel.CENTER_ALIGNMENT);
       box.add(a);
       textb = new JTextArea(9, 40);
       textb.setEditable(false);
       box.add(textb);
       StringBuffer data1 = new StringBuffer("");
           for (int i = 0; i < notification.size(); i++)
           {
                   if((notification.get(i).getGrade().getStudent().getFather().toString().compareTo(t.toString())==0 ||
                           notification.get(i).getGrade().getStudent().getMother().toString().compareTo(t.toString())==0)&&(notification.get(i).getGrade()!=null))
                   {
                           t.update(notification.get(i));
                           if (notification.get(i).getGrade().getOk() == 1)
                           {
                               data1.append(notification.get(i).getGrade().getCourse().toString() + ": examen: " + notification.get(i).getGrade().getExamScore() + "\n");
                           }
                           else
                           {
                               if (notification.get(i).getGrade().getOk() == 2)
                               {
                                   data1.append(notification.get(i).getGrade().getCourse().toString() + ": parcurs: " + notification.get(i).getGrade().getPartialScore() + "\n");
                               }
                               else
                               {
                                   data1.append("Nicio notificare noua\n");
                               }
                           }
                   }
           }
            String data = new String(data1);
            textb.append(data);
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