# JAFFB
JAFFB
=========================

JAFFB parsing and mapping for the file flat with annotation imitating JAXB

Gestion de fichier plat

Description
===========

Cette librairie est là  pour transformer un fichier plat en objet.
Elle fonctionne par annotation. 


code exemple 
============
classe de description de ligne
```java

public class LigneSimple {
    //total 185 
    //format dix 0
    @PositionalMappingParse(offset = 1, length = 10)
    @PositionalJavaTypeAdapter(value=NumberConvertDix0.class)
    private Integer idLigne ;
    @PositionalMappingParse(offset = 11, length =10,padding=PositionalMappingParse.TOP,stripChaine=true)
    private String colume ;
    @PositionalMappingParse(offset = 21, length = 8,required=true)
    @PositionalControlRegex(expression="([0-9]{2})([0-9_A-Z]{6})")
    private String refference ;
    //il faut convertir en tableau
    @PositionalMappingParse(offset = 29, length = 97)
    @PositionalJavaTypeAdapter(value=ConvertStringTableauString.class)
    private String[] valueRef ;
    @PositionalMappingParse(offset = 126, length = 60)
    private String code ;
    
    getters and setter {
    ....
    
    }


````

class adapter extends de PositionalAdapter


```java 

public class DateConvertddMMyyyy extends PositionalAdapter<String, Date> {

    private static SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

    @Override
    public Date unparsing(String v) throws Exception {
        Date d = null;

        try {
            if (!StringUtils.isBlank(v)) {
                d = sdf.parse(v);
            }

        } catch (Exception e) {
            d = null;
            System.err.println(e + " est null");
        }

        return d;
    }

    @Override
    public String parsing(Date v) throws Exception {
        if (v == null) {

            return "        ";

        }
        return sdf.format(v);
    }
    
````
classe de type root pour un fichier avec une entête et des lignes identiques par la suite

```java

@PositionalFormatFile(name = "FileRootForFFPMSimple")
public class FileRootForFFPMSimple {
    // no de l'attribut , le nombre de ligne conserner
    @PositionalMappingParseRootElem(name = "enteteSimple",numbersRowsIterator=1, startRowsIterationLigne = 1,valuLongueurChaine=56,theclass=EnteteSimple.class)
    private EnteteSimple enteteSimple ;
    
    @PositionalMappingParseRootElem(name = "ligneSimples",list=true,theclass=LigneSimple.class, startRowsIterationLigne = 2,valuLongueurChaine=185)
    private List<LigneSimple> ligneSimples ;


````
classe si les lignes sont à différencier avec une Regex

```java

@PositionalMappingParseRootElem(startRowsIterationLigne = 1,list = true, name = "enteteSimple", expression = "^((Test1Simple )(.*)|(Test1Complex)(.*))", theclass = EnteteComplex.class, charcatereRepli = ' ')
    private List<EnteteComplex> enteteComplex ;
    
    
    @PositionalMappingParseRootElem(startRowsIterationLigne = 2, list = true, name = "ligneA", expression =  "^(LIGNEA)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
    private List<LigneComplex> ligneA ;

    @PositionalMappingParseRootElem(startRowsIterationLigne = 3, list = true, name = "ligneB", expression = "^(LIGNEB)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
    private List<LigneComplex> ligneB ;
    
    @PositionalMappingParseRootElem(startRowsIterationLigne = 4, list = true, name = "ligneC", expression = "^(LIGNEC)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
    private List<LigneComplex> ligneC ;
    
    @PositionalMappingParseRootElem(startRowsIterationLigne = 5, list = true, name = "ligneD", expression = "^(LIGNED)(.*)", theclass = LigneComplex.class, charcatereRepli = ' ')
    private List<LigneComplex> ligneD ;
    
    // ligne qui ne correspond à aucune expression
    @PositionalMappingParseRootElem(startRowsIterationLigne = 6, list = true, name = "ligneE", expression =PositionalMappingParseRootElem.NOEXPRES , theclass = NonTraiter.class, charcatereRepli = ' ')
    private List<NonTraiter> ligneE ;
    
    

    getters and setter {
    ....
    
    }
````

utilisation lecture
    
```java
    
        /**
         * lecture et création d'un objet
         * 
         */
        JAFFBContext contex = JAFFBContext.newInstance(FileRootForFFPMSimple.class) ;
        Unmarshaller unmarshaller = contex.createUnmarshaller();
        
        FileRootForFFPMSimple ffpmSimple = (FileRootForFFPMSimple) unmarshaller.unmarshal(new File("RessourcesFiles/TestSimpleFileflat.txt"));

        System.out.println(ffpmSimple.toString());
              

````
utilisation écriture
    
```java
        
        /**
         * écriture d'un fichier à partir de l'objet
         */
        
        File fileReecrir = new File("RessourcesFiles/TestecrirNormanlFileflat.txt") ;
        
        Marshaller marshaller = JAFFBContext.newInstance().createMarshaller() ;
        
        marshaller.marshal(ffpmSimple, fileReecrir);
        
        /**
         * écriture d'un fichier à partir d'un objet
         * en utilisant la désactivation de certains attributs
         * 
         */
        
        File fileDesactivat = new File("RessourcesFiles/TestecrirDesactivatFileflat.txt") ;
        
        marshaller = JAFFBContext.newInstance().createMarshaller() ;
        
        Properties desactivate = new Properties();
        desactivate.put("refference", "refference");
        desactivate.put("valueRef", "valueRef");
        desactivate.put("idLigne", "idLigne");
        
        ffpmDesactivate.setDesactivat(desactivate);
        ffpmDesactivate.parsingPositional(ffpmSimple, fileDesactivat);
        
        marshaller.setDesactivat(desactivate);
        marshaller.marshal(ffpmSimple, fileDesactivat);
        
        Files.readAllLines(fileDesactivat.toPath()).forEach(s -> System.out.println(s));
        
        
    
````
    
ou juste pour une chaine string

```java
    String chaine = "002165224203        76B0A01BB0B0CB B0CWAB B0DAFB B0E0EB B0FBFB B0G0TB B0H04B B0JA0B B0K04B B0L00B B0MM0B B0NKUB B0P2LB B0RFXBDAB00CDDAD00CDDAE07CDDAF01CDDAG00CDDAI00CDDAJ01CDDAL33CDDAP0";
        /**
         * creation d'un simple objet à partir d'une chaine de caractères
         */
          JAFFBContext contex = JAFFBContext.newInstance() ;
        
        LigneSimple ligneSimple = (LigneSimple) contex.createUnmarshaller().convertStringChaineInObject(LigneSimple.class, chaine);
        
        System.out.println(ligneSimple.toString());
    
````
Pour le traitement CSV
============

code exemple la Class :

```java

public class ObjectForCsvMapping {

    @CsvMappingParse(offset = 1)
    private String nom ;
    @CsvMappingParse(offset = 2)
    private String prenom ;
    @CsvMappingParse(offset = 3)
    @PositionalJavaTypeAdapter(value=DateConvertddMMyyyy.class)
    private Date dateNaissanse ;
    @CsvMappingParse(offset = 4)
    private Integer age ; 
    


    getters and setter {
    ....

````

juste pour une chaine string en Objet et inverse


```java

        String data = "Nom;Prenom;05/10/2014;100;" ;

        JAFFBContext  jaffbContext = JAFFBContext.newInstance() ;
        Unmarshaller  unmarshaller = jaffbContext.createUnmarshaller();
        
        ObjectForCsvMapping csvMapping = (ObjectForCsvMapping)      unmarshaller.convertChaineCsvInObject(ObjectForCsvMapping.class, data, ";");
        
        System.out.println(csvMapping.toString());
        
        String result = jaffbContext.createMarshaller().convertObjectInChaineCsv(csvMapping, ";" );
        
        System.out.println(result);


````

juste pour un  Objet en chaine string et contenant une ou des lists annotées,
attention un object egale une chaine.

premier classe

```java

public class CorpCsvAnnalyse {
    
    @CsvMappingParse(offset = 1)
    private String famille ;
    @CsvMappingParse(offset = 2)
    private String ut;
    @CsvMappingParse(offset = 4)
    private String ligne;
    @CsvMappingParse(offset = 3)
    private List<PeriodeCsvAnnalyse> periodes ;
    getters and setter {
    ....
````

Classe de la liste qui est aussi annotées
```java

public class PeriodeCsvAnnalyse {
    
   @CsvMappingParse(offset = 1)
   private List<SemaineCsvAnalyse> semaines ;
    getters and setter {
    ....
    
````
Classe de la liste qui est aussi annotées

```java

public class SemaineCsvAnalyse {

    @CsvMappingParse(offset = 1)
    private Integer quantiteForSemaine  ;
    
   getters and setter {
    ....
    
````

```java

        CorpCsvAnnalyse corpCsvAnnalyse = new CorpCsvAnnalyse() ;
        
        // Ajout des valeurs
        ...
        //
        

      JAFFBContext  jaffbContext = JAFFBContext.newInstance() ;
      Marshaller parse = jaffbContext.createMarshaller();
      String chaine =  parse.convertObjectInChaineCsv(corpCsvAnnalyse, ";");
      System.out.println(chaine);


````
    
    
