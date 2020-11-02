package domaci.fakultet.model;

import anotacije.definicije.Autowired;
import anotacije.definicije.ClassInfo;
import anotacije.definicije.Component;
import anotacije.definicije.Qualifier;
import domaci.fakultet.interfejsi.Student;
import domaci.fakultet.servisi.StudentskiServis;

@ClassInfo(author = "RAF", version = "1.0")
@Component
@Qualifier("brucosStudent")
public class Brucos implements Student {

    @Autowired
    StudentskiServis servis;

    String ime = "Naivan Brucos";

    public Brucos(){

    }

    public void printIme(){
        System.out.println(this.ime);
    }

    public void printBrojIndexa(){
        servis.printBrojIndeksa(this.hashCode());
    }


}
