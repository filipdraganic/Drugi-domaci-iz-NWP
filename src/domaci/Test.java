package domaci;

import anotacije.definicije.Autowired;
import anotacije.definicije.Qualifier;
import domaci.fakultet.interfejsi.Student;
import domaci.fakultet.model.Brucos;
import domaci.fakultet.model.DrugaGodina;
import domaci.fakultet.model.Ucionica;

public class Test {

    @Autowired(verbose = true)
    Ucionica ucionica;

    @Autowired(verbose = true)
    Brucos student;

    @Autowired(verbose = true)
    DrugaGodina student2;


    @Autowired
    @Qualifier("Student")
    Student nesto;


    public Test(){

    }

    public void print(){
        ucionica.printSifra();
        System.out.println("");
        student.printBrojIndexa();
        student.printIme();
        System.out.println("");
        student2.printBrojIndexa();
        student2.printIme();
        System.out.println("");
        nesto.printIme();
        nesto.printBrojIndexa();
        System.out.println("");
    }
}
