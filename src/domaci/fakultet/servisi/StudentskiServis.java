package domaci.fakultet.servisi;

import anotacije.definicije.ClassInfo;
import anotacije.definicije.Qualifier;
import anotacije.definicije.Service;

@Service
@ClassInfo(author = "RAF", version = "1.0")
@Qualifier("StudentskiServis")
public class StudentskiServis {

    public StudentskiServis() {

    }

    public void printBrojIndeksa(int broj){
        System.out.println(broj);
    }
}
