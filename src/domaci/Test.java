package domaci;

import anotacije.definicije.Autowired;
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
    Student nesto;


    public Test(){

    }

    public void print(){
        ucionica.printSifra();
        student.printBrojIndexa();
        student.printIme();

        student2.printBrojIndexa();
        student2.printIme();

        nesto.printIme();
        nesto.printBrojIndexa();
    }
}
