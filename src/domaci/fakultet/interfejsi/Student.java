package domaci.fakultet.interfejsi;

import anotacije.definicije.Component;
import anotacije.definicije.Qualifier;

@Component
@Qualifier("Student")
public interface Student {

    void printIme();
    void printBrojIndexa();

}
