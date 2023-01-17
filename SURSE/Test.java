import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Test {
    public static void main(String args[]) throws MalformedURLException {

        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/data.txt"));
            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] splits = lines.get(0).split("\\s+");
        int nCourses = Integer.parseInt(splits[0]);
        int nPartial = Integer.parseInt(splits[1]);
        int nFull = Integer.parseInt(splits[2]);
        //primele nPartial cursuri din fisier sunt partiale, restul de nFull sunt full

        ArrayList<String> coursesNames = new ArrayList<>();
        ArrayList<Integer> nostudcourse = new ArrayList<>(); // number of students for each course
        ArrayList<String> fnt = new ArrayList<>(); //firstname teacher
        ArrayList<String> lnt = new ArrayList<>(); //lastname teacher
        ArrayList<String> sfn = new ArrayList<>(); //student first
        ArrayList<String> sln = new ArrayList<>(); //student second
        ArrayList<String> ffn = new ArrayList<>(); //father first
        ArrayList<String> fln = new ArrayList<>(); // father second
        ArrayList<String> mfn = new ArrayList<>(); // mother first
        ArrayList<String> mln = new ArrayList<>(); //mother second
        ArrayList<String> ids = new ArrayList<>(); //group ids
        ArrayList<String> afn = new ArrayList<>(); // assistant first
        ArrayList<String> aln = new ArrayList<>(); // assistant second
        ArrayList<Double> partial = new ArrayList<>(); // partial score
        ArrayList<Double> exam = new ArrayList<>(); // exam score

        int i = 1;
        while (i < lines.size())
        {
            String[] splited = lines.get(i).split("\\s+");
            coursesNames.add(splited[0]);
            fnt.add(splited[2]);
            lnt.add(splited[3]);
            nostudcourse.add(Integer.parseInt(splited[1]));
            i = i + Integer.parseInt(splited[1]) + 1;
        }
        i = 2;
        int num = 0;
        for (int nr = 0; nr < nCourses; nr++)
        //daca o persoana are mai multe nume ---> cratima intre nume a.i sa rezulte un first si un last
        {
            if (nr != 0)
            {
                i = i + num + 1;
            }
            num = 0;
            while (num < nostudcourse.get(nr))
            {
                String[] splited = lines.get(i + num).split("\\s+");
                sfn.add(splited[0]);
                sln.add(splited[1]);
                ffn.add(splited[2]); // stiu cate sunt, duh!
                fln.add(splited[3]);
                mfn.add(splited[4]);
                mln.add(splited[5]);
                ids.add(splited[6]);
                afn.add(splited[7]);
                aln.add(splited[8]); // e ok daca se repeta, abia o sa se dovedeasca eficienta treeset-urilor, basca asa vad care e inrolat la curs
                partial.add(Double.parseDouble(splited[9]));
                exam.add(Double.parseDouble(splited[10]));
                num++;
            }
        }
        UserFactory factory = new UserFactory();
        ArrayList<Parent> parents = new ArrayList<>();
        for (i = 0; i < mfn.size(); i++)
        {
            parents.add((Parent) factory.factory("Parent", ffn.get(i), fln.get(i))); //father
            parents.add((Parent) factory.factory("Parent", mfn.get(i), mln.get(i))); //mother
            //presupunem ca toti studentii au ambii parinti
        }
        // folosire userfactory

        ArrayList<Student> students = new ArrayList<>();
        for (i = 0; i < sfn.size(); i++)
        {
           students.add(new Student(sfn.get(i),sln.get(i)));
        }
        //creare user/student propriu-zis

        int nr = 0;
        for (i = 0; i < students.size() && nr < 2 * students.size(); i++)
        {

            students.get(i).setFather(parents.get(nr));
            students.get(i).setMother(parents.get(nr+1));
            nr = nr + 2;
        }
        // adaugare parinte pentru fiecare student

        ArrayList<Teacher> t = new ArrayList<>();
        for (i = 0; i < fnt.size(); i++)
        {
            t.add(new Teacher(fnt.get(i),lnt.get(i)));
        }
        //creare profesori pur si simplu

        ArrayList<Assistant> as = new ArrayList<>();
        for (i = 0; i < afn.size(); i++) {
            as.add(new Assistant(afn.get(i), aln.get(i) ));
        }
        // creare asistenti

        ArrayList<Grade> note = new ArrayList<>();
        for (i = 0; i < partial.size(); i++)
        {
            note.add(new Grade(partial.get(i), exam.get(i)," ",students.get(i)));
        }
        //creare note

        ArrayList<Group> mygroups = new ArrayList<>();
        for (i = 0; i < ids.size(); i++)
        {
            Group mygrupa = new Group(ids.get(i),as.get(i));
            for (int j = 0; j < sfn.size(); j++)
            {
                if (ids.get(j).compareTo(ids.get(i)) == 0)
                    mygrupa.add(students.get(i));
            }
            mygroups.add(mygrupa);
        }
        //creare grupe, chiar daca se repeta, cu studenti cu tot!

        HashMap<String, TreeSet<Assistant>> allassistantscourses = new HashMap<>();
        int numb = 0;
        for (i = 0; i < nCourses; i++)
        {
            TreeSet<Assistant> a = new TreeSet<>();
            nr = 0;
            for (int j = numb; nr < nostudcourse.get(i); j++){
                        a.add(as.get(j));
                        nr++;
                    }
            numb = numb + nr;
            allassistantscourses.put(coursesNames.get(i),a);
        }

        HashMap<String, TreeSet<Grade>> allgrades = new HashMap<>();
        numb = 0;
        for (i = 0; i < nCourses; i++)
        {
            nr = 0;
            TreeSet<Grade> grades = new TreeSet<>();
            for (int j = numb; nr < nostudcourse.get(i); j++)
            {
                grades.add(note.get(j));
                nr++;
            }
            numb = numb + nr;
                allgrades.put(coursesNames.get(i), grades);
        }
        //adaugare note pentru fiecare copil ptr fiecare materie in parte

        // deci nume_curs, profesor_curs, asistenti_curs, note_curs, grupa_curs
        // grupa_curs e HashMap<String, Group> unde String-ul e ID-ul grupei

        HashMap<String, HashMap<String,Group>> hsmp = new HashMap<>();
        numb = 0; // iau pe cursuri
        for (i = 0; i < nCourses; i++)
        {
            nr=0;
            HashMap<String, Group> grupelemele = new HashMap<>();
            for (int j = numb; nr < nostudcourse.get(i); j++)
            {
                Group mygrupa = new Group(ids.get(j),as.get(j));
                    if (ids.get(j).compareTo(mygrupa.getID())==0)
                    {
                        mygrupa.add(students.get(j));
                    }
                nr++;
                grupelemele.put(mygrupa.getID(), mygrupa);
            }
           numb = numb + nr;
            hsmp.put(coursesNames.get(i),grupelemele);
        }

        ArrayList<Course> cr = new ArrayList<>();
        ArrayList<Course.CourseBuilder> b = new ArrayList<>();
        for (i = 0; i < nPartial; i++)
        {
            b.add(new PartialCourse.PartialCourseBuilder(coursesNames.get(i), t.get(i), allassistantscourses.get(coursesNames.get(i)), allgrades.get(coursesNames.get(i)), hsmp.get(coursesNames.get(i))));
            cr.add(b.get(i).build());
        }

        for (i = nPartial; i < nCourses; i++)
        {
            b.add(new FullCourse.FullCourseBuilder(coursesNames.get(i), t.get(i), allassistantscourses.get(coursesNames.get(i)), allgrades.get(coursesNames.get(i)), hsmp.get(coursesNames.get(i))));
            cr.add(b.get(i).build());
        }
        TreeSet<Course> cursuri = new TreeSet<>();
        for (i = 0; i < nCourses; i++)
        {
            cursuri.add((Course)cr.get(i));
        }
        //adaugare cursuri in lista de cursuri

        Catalog c = Catalog.getInstance("Catalog", cursuri);
        // creare catalog
        System.out.println("<=====URMATOARELE DATE SUNT INVENTATE DE MINE, IN FUNCTIE DE CE DATE AM AVUT NEVOIE PENTRU TESTARE.=====>\n" +
                "<=====DATELE DE INTRARE SE POT VEDEA IN FISIERUL .TXT=====>");
        System.out.println("<=====IN CELE CE URMEAZA TESTEZ FUNCTIILE IMPLEMENTATE, PE ALOCURI EXPLICAND SI CARE FUNCTIE E=====>");
        System.out.println("Cursurile existente in catalog sunt: ");
        c.showCourses();
        System.out.println("");
        System.out.println("------------------");
        System.out.println("");

        // info general
        System.out.println("Info general:");
        System.out.println("<=====USERFACTORY, SINGLELTON, BUILDER (DESIGN PATTERNS) ADDSTUDENT(GROUP), SETMOTHER/FATHER, ETC. =====>");
        for (Course crs : c.getCourses()) {
            System.out.println("Cursul " + crs.getCourse() + "\n" + "Profesor titular: " + crs.getTeacher().toString());
            System.out.print("Lista asistenti/curs: ");
            for (Assistant ai : crs.getAssistants()) {
                System.out.print(ai.toString() + "; ");
            }
            System.out.println("");
            for (Group j : crs.getGroups().values()) {
                System.out.println("Asistentul grupei " + j.getID() + ": " + j.getAssistant().toString());
            }
            System.out.println("------------------");
        }
        System.out.println("Studentii per total din catalog");
        TreeSet<Student> studn = new TreeSet<>();
        for (Course ci : c.getCourses())
        {
            for (Student j : ci.getAllStudents())
            {
                studn.add(j);
            }
        }
        Course ceva = null;
        for (Student s: studn)
        {
            System.out.print("Studentul "+ s.toString() + " e inrolat la cursurile: ");
            for (Course ci : c.getCourses()) {
                System.out.print(ci.getCourse() + "; ");
            }
            System.out.print("si face oparte din grupa ");
            for (Course ci :c.getCourses()) {
                for (Group j : ci.getGroups().values()) {
                    for (Student k : j) {
                        ceva = ci;
                        break;
                    }
                    break;
                }
                break;
            }
            for (Group j : ceva.getGroups().values())
            {
                for (Student k : j)
                    if (k.toString().compareTo(s.toString()) == 0 && k.getFather().toString().compareTo(s.getFather().toString()) == 0)
                    {
                        System.out.print(j.getID());
                    }
            }
            System.out.println(" si are ca parinti pe " + s.getFather().toString() + " si " + s.getMother().toString());
        }
        System.out.println("------------------\n");

        System.out.println("<=====CUM FARA ANUMITE FUNCTII NU AM PUTUT CREA UN CATALOG CU TOATE DATELE AFISATE ANTERIOR, PARSATE DIN FISIER, ANTERIOR AM TESTAT MAJORTATEA=====>");

        // acum note parcurs si examen si total pe cursuri, cat si getbeststudent
        // getgraduetedstudents
        System.out.println("<=====TESTARE ALTE FUNCTIONALITATI=====>");

        HashSet<Teacher> profi = new HashSet<>();
        for (Course ci : c.getCourses())
        {
            profi.add(ci.getTeacher());
        }

        //aici am mai incercat sa testez functii, getteri si setteri, add-uri nefolositi(e) etc etc
        System.out.println("<=====ADAUGARE ASISTENT NOU, ATRIBUIREA SA LA O GRUPA====>");
        Assistant a32 = new Assistant(" ", " ");
        a32.setFirstName("Tranca");
        a32.setLastName("Cristian");
        cr.get(2).getAssistants().add(a32);
        cr.get(2).getGroups().get("321CC").setAssistant(a32);

        //noi vrem la DEEA care e al doilea
        System.out.println("Grupa 321CC are acum la DEEA ca asistent pe " + cr.get(2).getGroups().get("321CC").getAssistant());
        System.out.println("<=====ADAUGARE GRUPA NOUA LA UN CURS=====>");
        Group grr = new Group("325CC",a32);
        cr.get(2).addGroup(grr);
        grr.setID("325CC");
        System.out.println("<=====ADAUGARE STUDENT NOU LA O GRUPA=====>");
        Student s5 = new Student("Martinovici","Gina");
        s5.setFather(new Parent("Martinovici","Dorian"));
        s5.setMother(new Parent("Martinovici", "Dina"));
        cr.get(2).addStudent("325CC", s5);
        cr.get(2).addAssistant("325CC", a32);
        Grade g35 = new Grade(3.5,4.5,"DEEA",s5);
        cr.get(2).getGrades().add(g35);
        System.out.print("La cursul de DEEA s-a mai adaugat o grupa, anume pe ");
        if (cr.get(2).getGroups().containsKey("325CC"))
        {
            for (Group g :cr.get(2).getGroups().values())
            {
                if (g.getID().compareTo("325CC")==0)
                {
                    System.out.println("325CC");
                }
            }
        }
        System.out.println("Nota studentului nou adaugat la DEEA ce se numeste " + s5.toString() + " este: Parcurs: "
                + cr.get(2).getGrade(s5).getPartialScore() + "; Examen: " +
                cr.get(2).getGrade(s5).getExamScore() + "; Total: " + cr.get(2).getGrade(s5).getTotal());
        System.out.print("Studentul de mai sus se afla la grupa ");
        for (Group g: cr.get(2).getGroups().values())
        {
            if (g.contains(s5))
            {
                System.out.print(g.getID());
            }
        }
        System.out.print(" si are ca asistent pe ");
        for (Group g: cr.get(2).getGroups().values())
        {
            if (g.contains(s5))
            {
                System.out.println(g.getAssistant().toString());
            }
        }

        System.out.println("------------------\n");

        // testare getAllStudentsGrades()
        System.out.println("<=====TESTARE getAllStudentGrades()=====>");
        HashMap<Student, Grade> grds = new HashMap<>();
        grds = cr.get(2).getAllStudentGrades();
        System.out.println("Notele la cursul de DEEA sunt: ");
        for (Grade ii : grds.values())
        {
            System.out.println("Student: " + ii.getStudent().toString() + ": Parcurs: " + ii.getPartialScore() + "; Examen: " + ii.getExamScore() + "; Total: " + ii.getTotal());
        }
        System.out.println("------------------\n");

        ArrayList<Student> gradEA = ((FullCourse)cr.get(2)).getGratuatedStudents();
        ArrayList<Student> gradSO = ((PartialCourse)cr.get(1)).getGratuatedStudents();
        System.out.println("<=====TESTARE getGraduatedStudents()=====>");
        System.out.println("Studentii care au trecut materia DEEA (FullCourse) sunt:");
        for (Student ii : gradEA)
        {
            System.out.print(ii.toString() + "; ");
        }
        System.out.println("");
        System.out.println("Studentii care au trecut materia SO (PartialCourse) sunt:");
        for (Student ii : gradSO)
        {
            System.out.print(ii.toString() + "; ");
        }
        System.out.println("");
        System.out.println("------------------\n");
        System.out.println("<=====TESTARE STRATEGY DESIGN PATTERN=====>\n<=====A SE VERIFICA PE DATELE DE INTRARE!=====>");
        Strategy be = new BestExamScore(cr.get(1));
        Student stud1 = cr.get(1).getBestStudent(be);

        Strategy bp = new BestPartialScore(cr.get(3));
        Student stud2 = cr.get(3).getBestStudent(bp);

        Strategy bt = new BestTotalScore(cr.get(0));
        Student stud3 = cr.get(0).getBestStudent(bt);

        System.out.println("Cel mai bun student la examen la SO este: " + stud1.toString());
        System.out.println("Cel mai bun student pe parcurs la AA este: " + stud2.toString());
        System.out.println("Cel mai bun student per total la POO este: " + stud3.toString());

        System.out.println("------------------\n");

        System.out.println("<=====TESTARE OBSERVER DESIGN PATTERN=====>");
        //acum testez design pattern-urile de la 1.6, 1.8, 1.9 (pana acum am facut tot, fara acestea)

        //testare notify
        //System.out.println(cr.get(1).getAllStudents().get(2).getFather().toString());
        // sa zicem ca i se modifica o nota la poo
        System.out.println("Aici se va schimba nota la POO lui Dumitrescu Damian si se va notifica tatal");
        System.out.println("<<-----Pana sa se schimbe----->>");
        //initial
        Notification notif = new Notification(null);
        for (Course cc : c.getCourses())
        {
            if (cc.getCourse().compareTo("POO")==0)
            {
                for (Map.Entry<Student,Grade> e : cc.getAllStudentGrades().entrySet())
                {
                    if(e.getKey().toString().compareTo("Dumitrescu Damian")==0)
                    {
                        c.addObserver(cc.getAllStudents().get(2).getFather()); //tatal lui Damian Dumitrescu
                        cc.getAllStudents().get(2).getFather().setCatalog(c);
                        cc.getAllStudents().get(2).getFather().setObservator(c);
                        cr.get(1).getAllStudents().get(2).getFather().update(notif);
                    }
                }
            }
        }
        System.out.println("<<-----Dupa schimbare----->>");
        for (Course cc : c.getCourses())
        {
            if (cc.getCourse().compareTo("POO")==0)
            {
                for (Map.Entry<Student,Grade> e : cc.getAllStudentGrades().entrySet())
                {
                    if(e.getKey().toString().compareTo("Dumitrescu Damian")==0)
                    {
                        Grade ng = new Grade(4.3,4.6,cc.getCourse(),e.getKey());
                        e.setValue(ng);
                        notif.setGrade(ng);
                        e.getKey().getFather().update(notif);
                    }
                }
            }
        }
        for (Course cc : c.getCourses())
        {
            for (Student s:cc.getAllStudents())
            {
                if (s.toString().compareTo("Damian Dumitrescu")==0)
                {
                    s.getFather().setObservator(null);
                    s.getFather().setCatalog(null);
                    c.removeObserver(s.getFather());
                }
            }
        }
        notif = null;
        System.out.println("------------------\n");
        System.out.println("<=====TESTARE VISITOR DESIGN PATTERN=====>");

        // testare visitor
        System.out.println("Functionalitatea de la score visitor se va vedea in fereastra de validare a notelor");
        // hai ca testez intr-un final si aici, pentru profesorul de la cursul de SO
        ScoreVisitor sc = new ScoreVisitor();
        sc.setCatalog(c);
        //hai sa punem toti parintii ca observatori
        System.out.println("<<-----Pana la vizitare----->>");
        Notification notiff = new Notification(null);
        for (Course cc : c.getCourses())
        {
            if (cc.getCourse().compareTo("POO")==0)
            {
                for (Map.Entry<Student,Grade> e : cc.getAllStudentGrades().entrySet())
                {
                    for (Student s : cc.getAllStudents())
                    {
                        if(e.getKey().toString().compareTo(s.toString())==0&&e.getValue().getCourse().compareTo("POO")==0)
                        {
                            c.addObserver(s.getFather());
                            c.addObserver(s.getMother());
                            s.getFather().setCatalog(c);
                            s.getMother().setCatalog(c);
                            s.getMother().update(notiff);
                            s.getFather().update(notiff);
                        }
                    }
                }
            }
        }
        System.out.println("<<-----Dupa vizitare----->>");
        for (Course cc : c.getCourses())
        {
            if (cc.getCourse().compareTo("POO")==0)
            {
                if (cc.getTeacher().toString().compareTo("Odubasteanu Carmen") == 0) {
                    cc.getTeacher().accept(sc);
                }
                for (Map.Entry<Student, Grade> e : cc.getAllStudentGrades().entrySet())
                {
                    for (Student s : cc.getAllStudents())
                    {
                        if (e.getKey().toString().compareTo(s.toString())==0&&e.getValue().getCourse().compareTo("POO")==0)
                        {
                            notiff.setGrade(e.getValue());
                        }
                    }
                }
            }
        }
        System.out.println("------------------\n");
        //nu afiseaza nimic

        // testare memento
        System.out.println("<=====TESTARE MEMENTO DESIGN PATTERN=====>");
        System.out.println("<<-----Initial----->>");
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                for (Grade gdd : ccr.getGrades()) {
                System.out.println(gdd.getStudent().toString() + ", Nota finala: " + gdd.getTotal());
            }
            }
        }
        System.out.println("<<-----Dupa backup----->>");
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                ccr.hitSave();
            }
        }
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                for (Grade gdd : ccr.getGrades()) {
                    System.out.println(gdd.getStudent().toString() + ", Nota finala: " + gdd.getTotal());
                }
            }
        }
        System.out.println("<<-----Modific ceva----->>");
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                for (Grade gdd : ccr.getGrades()) {
                   //sa zicem ca modific nota lui Amariei
                    if (gdd.getStudent().toString().compareTo("Amariei Irina")==0)
                    {
                        gdd.setPartialScore(4.0);
                        //modific numai punctajul pe parcurs
                    }
                }
            }
        }
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                for (Grade gdd : ccr.getGrades()) {
                    System.out.println(gdd.getStudent().toString() + ", Nota finala: " + gdd.getTotal());
                }
            }
        }
        System.out.println("<<-----Dupa undo----->>");
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                ccr.undo();
            }
        }
        for (Course ccr : c.getCourses())
        {
            if (ccr.getCourse().compareTo("AA")==0)
            {
                for (Grade gdd : ccr.getGrades()) {
                    System.out.println(gdd.getStudent().toString() + ", Nota finala: " + gdd.getTotal());
                }
            }
        }
        System.out.println("------------------");
        System.out.println("<=====END TESTARE CLI=====>");

        ArrayList<Notification> nn = new ArrayList<>();
        Object[] options = {"Cautare student",
                "Validare note",
                "Vizualizare notificari"};
        final ImageIcon icon = new ImageIcon(new URL("https://icons.iconarchive.com/icons/paomedia/small-n-flat/48/sign-warning-icon.png"));
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
            Fereastra1 f1 = new Fereastra1("Cautare student",c, nn);
        }else if (n == JOptionPane.NO_OPTION){
            String result = (String)JOptionPane.showInputDialog(null,"Nume si prenume:","Autentificare",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
            Fereastra2 f2 = new Fereastra2("Validare note", result, c, nn);
        }else {
            String results = (String)JOptionPane.showInputDialog(null,"Nume si prenume:","Autentificare",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
            Fereastra3 f3 = new Fereastra3("Validare Notificari", results, c, nn);
        }
    }
}