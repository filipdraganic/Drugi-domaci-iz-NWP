package domaci;
import anotacije.definicije.Autowired;
import anotacije.definicije.ClassInfo;
import anotacije.definicije.Qualifier;
import anotacije.definicije.Service;
import domaci.fakultet.model.DrugaGodina;
import domaci.fakultet.model.Ucionica;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@ClassInfo(version = "1.0", author = "RAF")
public class DIEngine {


    private static DIEngine instance = null;
    private int test = 0;
    private HashMap<String, Object> servisi;
    private HashMap<String, String> DSupplier;

    private DIEngine(){}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void pumpIt(Test test) {
        this.DSupplier = new HashMap<>();
        DSupplier.put("Student", DrugaGodina.class.getTypeName());


        this.servisi = new HashMap<>();
        Set<String> files = new HashSet<>();
        listOfPackage("src/domaci/fakultet",files);

        System.out.println(files);

        try{
            Class cl = Class.forName("domaci.Test");


            Annotation ci[] = cl.getAnnotations();
			for(Annotation annotation: ci){
                System.out.println(annotation.annotationType().getSimpleName());

            }

			for(Field field: cl.getDeclaredFields()){
                field.setAccessible(true);

                Autowired nesto = field.getAnnotation(Autowired.class);
                if(nesto != null){

                    Class testAnnotation = Class.forName(field.getType().getName());
                    if (!Arrays.toString(testAnnotation.getAnnotations()).contains("@anotacije.definicije.Component()") )
                    {
                        if (!Arrays.toString(testAnnotation.getAnnotations()).contains("@anotacije.definicije.Service()")){
                            throw new Exception("Ne @Bean klasa je pokusana da se @Autowireuje");
                        }

                    }

                    //Atribut tipa Interface je anotiran sa @Autowired a nije sa @Qualifier
                    Annotation annotation = testAnnotation.getAnnotation(Qualifier.class);

                    if (annotation == null){
                        String testString = DSupplier.get(field.getType().getSimpleName());
                 
                        if (testString != null){
                            throw new Exception("Atribut tipa interfejs je anotiran sa @Autowire, ali ne i sa @Qualifier");
                        }

                    }


                    if (nesto.verbose()){
                        LocalDateTime now = LocalDateTime.now();

                        System.out.println("Initialized GetType:"+ field.getType() + " GetName:" + field.getName() + " SimpleName:" + cl.getSimpleName() + " now:"+ now + " Hash:" + field.hashCode());

                    }
                    Class newClass = Class.forName(field.getType().getTypeName());
                    Object obj = idemoUDubinu(field.getType().getTypeName());

                    field.set(test, obj);
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static DIEngine getInstance(Test test){
        if(instance == null){
            instance = new DIEngine();
        }
        return instance;
    }

    public Object idemoUDubinu(String putanja){
        Object obj = null;
        try{

            Class cl = Class.forName(putanja);


            Annotation[] annotations = cl.getAnnotations();


            if(cl.getAnnotation(Service.class) != null){
                Qualifier qualifier = (Qualifier) cl.getAnnotation(Qualifier.class);
                if (!servisi.containsKey(qualifier.value())) {
                     obj = cl.newInstance();
                    servisi.put(qualifier.value(), obj);
                }
                else{
                    obj = servisi.get(qualifier.value());
                }
            }else{

                if(DSupplier.containsKey(cl.getSimpleName())){
                    cl = Class.forName(DSupplier.get(cl.getSimpleName()));
                }

                obj = cl.newInstance();
            }


            for(Field field: cl.getDeclaredFields()){
                field.setAccessible(true);
                System.out.println(field.toString());
                Autowired nesto = field.getAnnotation(Autowired.class);
                if(nesto != null) {

                    //Bacanje izuzetaka ako se nesto lose desi iz specifikacije

                    //@Autowired na atributu koji nije @Bean (ili @Service ili @Component)
                    Class testAnnotation = Class.forName(field.getType().getName());
                    if (!Arrays.toString(testAnnotation.getAnnotations()).contains("@anotacije.definicije.Component()") )
                    {
                        if (!Arrays.toString(testAnnotation.getAnnotations()).contains("@anotacije.definicije.Service()")){
                            throw new Exception("Ne @Bean klasa je pokusana da se @Autowireuje");
                        }

                    }

                    //Atribut tipa Interface je anotiran sa @Autowired a nije sa @Qualifier
                    //Nije gotovo jos
                    Annotation annotation = testAnnotation.getAnnotation(Qualifier.class);
                    System.out.println(annotation);
                    if (annotation == null){
                        String testString = DSupplier.get(field.getType().getSimpleName());
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN");
                        System.out.println(field.getType().getSimpleName());
                        if (testString != null){
                            throw new Exception("Atribut tipa interfejs je anotiran sa @Autowire, ali ne i sa @Qualifier");
                        }

                    }


                    if (nesto.verbose()) {
                        LocalDateTime now = LocalDateTime.now();

                        System.out.println("Initialized GetType:" + field.getType() + " GetName:" + field.getName() +
                                " SimpleName:" + cl.getSimpleName() + " now:" + now + " Hash:" + field.hashCode());

                    }
                    Class newClass = Class.forName(field.getType().getTypeName());
                    Object objToSet = idemoUDubinu(field.getType().getTypeName());

                    field.set(obj, objToSet);
                }
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static void listOfPackage(String directoryName, Set<String> pack) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                String path=file.getPath();
                String packName=path.substring(path.indexOf("src")+4, path.lastIndexOf('\\'));
                pack.add(packName.replace('\\', '.'));
            } else if (file.isDirectory()) {

                listOfPackage(file.getAbsolutePath(), pack);
            }
        }
    }

}
