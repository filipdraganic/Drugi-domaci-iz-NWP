package domaci.fakultet.model;


import anotacije.definicije.Autowired;
import anotacije.definicije.ClassInfo;
import anotacije.definicije.Component;
import domaci.DIEngine;
import domaci.fakultet.interfejsi.Student;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@ClassInfo(author = "RAF", version = "1.0")
public class Ucionica {

    String sifra = "RAF3";

    ArrayList<Student> studenti = null;


    public Ucionica(){

    }

    public void printSifra(){
        System.out.println(sifra + " " + this.hashCode());
    }

    public void printStudente(){
        System.out.println(studenti.toString());
    }

    public void addStudent(Student student){
        if(studenti == null) studenti = new ArrayList<>();

        this.studenti.add(student);
    }

}
