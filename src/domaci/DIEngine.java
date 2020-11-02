package domaci;
import anotacije.definicije.Autowired;
import anotacije.definicije.ClassInfo;
import anotacije.definicije.Qualifier;
import anotacije.definicije.Service;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import domaci.fakultet.model.DrugaGodina;
import domaci.fakultet.model.Ucionica;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


@ClassInfo(version = "1.0", author = "RAF")
public class DIEngine {


    private static DIEngine instance = null;
    private int test = 0;
    private HashMap<String, Object> servisi;
    private HashMap<String, String> DSupplier;
    private HashMap<String, String> qualifierRegistry;

    private DIEngine(){}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void pumpIt(Test test) {
        this.DSupplier = new HashMap<>();
        DSupplier.put("Student", DrugaGodina.class.getTypeName());


        this.servisi = new HashMap<>();
        Set<String> files = new HashSet<>();
        listOfPackage("src/domaci/fakultet",files);


        this.qualifierRegistry = new HashMap<>();

        pronadjiDuplikate();

        System.out.println(files);
        System.out.println("");
        try{
            Class cl = Class.forName("domaci.Test");


            Annotation ci[] = cl.getAnnotations();

			for(Field field: cl.getDeclaredFields()){
                field.setAccessible(true);

                Autowired nesto = field.getAnnotation(Autowired.class);
                if(nesto != null){

                    Class testAnnotations = Class.forName(field.getType().getName());
                    if (!Arrays.toString(testAnnotations.getAnnotations()).contains("@anotacije.definicije.Component()") )
                    {
                        if (!Arrays.toString(testAnnotations.getAnnotations()).contains("@anotacije.definicije.Service()")){
                            throw new Exception("Ne @Bean klasa je pokusana da se @Autowireuje");
                        }

                    }

                    //Atribut tipa Interface je anotiran sa @Autowired a nije sa @Qualifier
                    Qualifier annotation = field.getAnnotation(Qualifier.class);

                    if (annotation == null){
                        String testString = DSupplier.get(field.getType().getSimpleName());
                 
                        if (testString != null){
//                            System.out.println(field.getType().getSimpleName());
//                            System.out.println(DSupplier.get(field.getType().getSimpleName()));
                            throw new Exception("Atribut tipa interfejs je anotiran sa @Autowire, ali ne i sa @Qualifier");
                        }

                    }


                    if (nesto.verbose()){
                        LocalDateTime now = LocalDateTime.now();

                        System.out.println("Initialized "+ field.getType() + " " + field.getName() + " in " +
                                cl.getSimpleName() + " "+ now + " " + field.hashCode());
                        System.out.println("");

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
            System.exit(0);
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
//                System.out.println(field.toString());
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
//                    System.out.println(annotation);
                    if (annotation == null){
                        String testString = DSupplier.get(field.getType().getSimpleName());
//                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAN");
//                        System.out.println(field.getType().getSimpleName());
                        if (testString != null){
                            throw new Exception("Atribut tipa interfejs je anotiran sa @Autowire, ali ne i sa @Qualifier");
                        }

                    }


                    if (nesto.verbose()) {
                        LocalDateTime now = LocalDateTime.now();

                        System.out.println("Initialized " + field.getType() + " " + field.getName() +
                                " in " + cl.getSimpleName() + " " + now + " " + field.hashCode());
                        System.out.println("");
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
            System.exit(0);

        }
        return obj;
    }

    public void pronadjiDuplikate(){
        try {
            Set<String> files = new HashSet<>();
            listOfPackage("src/domaci/fakultet",files);

            ArrayList<Class[]> klase = new ArrayList<>();

            for(String putanja : files){

                for(Class c : getClassesForPackage(putanja)){
                    if (c.getAnnotation(Qualifier.class) == null) throw new Exception("Nije definisan Qualifier za @Bean ");
                    if(this.qualifierRegistry.containsKey(c.getSimpleName())){
                        throw new Exception("Postoji vise @Bean-ova sa @Qualifier-om iste vrednosti\n");
                    }else{
                        this.qualifierRegistry.put(c.getSimpleName(), c.getTypeName());
                    }
                }


            }
            System.out.println(this.qualifierRegistry.toString());
            System.out.println("");
        }catch (Exception e){
            e.printStackTrace();
        }


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

    public static ArrayList<Class<?>> getClassesForPackage(String pckgname)
            throws ClassNotFoundException {
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            final ClassLoader cld = Thread.currentThread()
                    .getContextClassLoader();

            if (cld == null)
                throw new ClassNotFoundException("Can't get class loader.");

            final Enumeration<URL> resources = cld.getResources(pckgname
                    .replace('.', '/'));
            URLConnection connection;

            for (URL url = null; resources.hasMoreElements()
                    && ((url = resources.nextElement()) != null);) {
                try {
                    connection = url.openConnection();

                    if (connection instanceof JarURLConnection) {
                        checkJarFile((JarURLConnection) connection, pckgname,
                                classes);
                    } else if (connection instanceof FileURLConnection) {
                        try {
                            checkDirectory(
                                    new File(URLDecoder.decode(url.getPath(),
                                            "UTF-8")), pckgname, classes);
                        } catch (final UnsupportedEncodingException ex) {
                            throw new ClassNotFoundException(
                                    pckgname
                                            + " does not appear to be a valid package (Unsupported encoding)",
                                    ex);
                        }
                    } else
                        throw new ClassNotFoundException(pckgname + " ("
                                + url.getPath()
                                + ") does not appear to be a valid package");
                } catch (final IOException ioex) {
                    throw new ClassNotFoundException(
                            "IOException was thrown when trying to get all resources for "
                                    + pckgname, ioex);
                }
            }
        } catch (final NullPointerException ex) {
            throw new ClassNotFoundException(
                    pckgname
                            + " does not appear to be a valid package (Null pointer exception)",
                    ex);
        } catch (final IOException ioex) {
            throw new ClassNotFoundException(
                    "IOException was thrown when trying to get all resources for "
                            + pckgname, ioex);
        }

        return classes;
    }

    private static void checkJarFile(JarURLConnection connection,
                                     String pckgname, ArrayList<Class<?>> classes)
            throws ClassNotFoundException, IOException {
        final JarFile jarFile = connection.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();
        String name;

        for (JarEntry jarEntry = null; entries.hasMoreElements()
                && ((jarEntry = entries.nextElement()) != null);) {
            name = jarEntry.getName();

            if (name.contains(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.contains(pckgname)) {
                    classes.add(Class.forName(name));
                }
            }
        }
    }

    private static void checkDirectory(File directory, String pckgname,
                                       ArrayList<Class<?>> classes) throws ClassNotFoundException {
        File tmpDirectory;

        if (directory.exists() && directory.isDirectory()) {
            final String[] files = directory.list();

            for (final String file : files) {
                if (file.endsWith(".class")) {
                    try {
                        classes.add(Class.forName(pckgname + '.'
                                + file.substring(0, file.length() - 6)));
                    } catch (final NoClassDefFoundError e) {
                        // do nothing. this class hasn't been found by the
                        // loader, and we don't care.
                    }
                } else if ((tmpDirectory = new File(directory, file))
                        .isDirectory()) {
                    checkDirectory(tmpDirectory, pckgname + "." + file, classes);
                }
            }
        }
    }



}
