package pf22_SelfContainedExpression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import main.Converter;

import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pf14_IndividualWork.PF14_IndividualWork;
import pf25_AutrePerformancePlan.PF25_AutrePerformancePlan;
import pf28_ExpressionCreation.PF28_ExpressionCreation;
import pf40_IdentifierAssignment.PF40_IdentifierAssignment;
import pf50_ControlledAccessPoint.PF50_ControlledAccessPoint;
import ppConverter.ConstructURI;
import ppConverter.GenerateUUID;
import ppMarcXML.MarcXmlReader;
import ppMarcXML.Record;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import pf15_ComplexWork.PF15_ComplexWork;

public class PF22_SelfContainedExpression {
	
          /*** Correspond à la description développée de l'expression représentative ***/
	
	static Model modelF22 = ModelFactory.createDefaultModel();
	static URI uriF22=null;
	
	String mus = "http://data.doremus.org/ontology/";
    String cidoc = "http://www.cidoc-crm.org/cidoc-crm/";
    String frbroo = "http://erlangen-crm.org/efrbroo/";
    String xsd = "http://www.w3.org/2001/XMLSchema#";
    
	/********************************************************************************************/
    public URI getURIF22() throws URISyntaxException {
    	if (uriF22==null){
    	ConstructURI uri = new ConstructURI();
    	GenerateUUID uuid = new GenerateUUID();
    	uriF22 = uri.getUUID("Self_Contained_Expression","F22", uuid.get());
    	}
    	return uriF22;
    }
    
    public Model getModel() throws URISyntaxException, IOException{
    	uriF22 = getURIF22();
    	Resource F22 = modelF22.createResource(uriF22.toString());
    	
    	/**************************** Expression: was created by ********************************/
    	PF28_ExpressionCreation F28= new PF28_ExpressionCreation();
    	F22.addProperty(modelF22.createProperty(frbroo+ "R17i_was_created_by"), modelF22.createResource(F28.getURIF28().toString()));
    	
    	/**************************** Expression: is representative expression of (Work) ********/
    	if (getTypeNotice(Converter.getFile()).equals("UNI:100")){ //Si c'est une notice d'oeuvre
    	PF15_ComplexWork F15= new PF15_ComplexWork();
    	F22.addProperty(modelF22.createProperty(frbroo+ "R40i_is_representative_expression_of"), modelF22.createResource(F15.getURIF15().toString()));
    	}
    	/**************************** Expression: Title *****************************************/
    	if (getTypeNotice(Converter.getFile()).equals("UNI:100")){ // Si c'est une notice d'oeuvre
    		F22.addProperty(modelF22.createProperty(cidoc+ "P102_has_title"), getTitle200(Converter.getFile()));
    	}
    	
    	if (getTypeNotice(Converter.getFile()).equals("AIC:14")) { // Si c'est une notice TUM
    		F22.addProperty(modelF22.createProperty(cidoc+ "P102_has_title"), getTitle(Converter.getFile(), "144"));
    		F22.addProperty(modelF22.createProperty(cidoc+ "P102_has_title"), getTitle(Converter.getFile(), "444"));
    	}
    	/**************************** Expression: Catalogue *************************************/
    	if (!(getCatalog(Converter.getFile(), "144").equals(""))){
    	if (getTypeNotice(Converter.getFile()).equals("AIC:14")) { // Si c'est une notice TUM
    	F22.addProperty(modelF22.createProperty(mus+ "U16_has_catalogue_statement"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getCatalog(Converter.getFile(), "144"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getCatalogName(Converter.getFile(), "144"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getCatalogNumber(Converter.getFile(), "144"))
				);
    	if (!(getCatalog(Converter.getFile(), "144").equals(getCatalog(Converter.getFile(), "444")))) { //Si le contenu de 144 est différent de 444
    	F22.addProperty(modelF22.createProperty(mus+ "U16_has_catalogue_statement"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getCatalog(Converter.getFile(), "444"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getCatalogName(Converter.getFile(), "444"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getCatalogNumber(Converter.getFile(), "444"))
				);}
    	}}
    	/**************************** Expression: Opus ******************************************/
    	if (!(getOpus(Converter.getFile(), "144").equals(""))){
    	F22.addProperty(modelF22.createProperty(mus+ "U17_has_opus_statement"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getOpus(Converter.getFile(), "144"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getOpusNumber(Converter.getFile(), "144"))
				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getOpusSubNumber(Converter.getFile(), "144"))
				);
    	if (!(getOpus(Converter.getFile(), "144").equals(getOpus(Converter.getFile(), "444")))) { //Si le contenu de 144 est différent de 444
    		F22.addProperty(modelF22.createProperty(mus+ "U17_has_opus_statement"), modelF22.createResource()
        			.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getOpus(Converter.getFile(), "444"))
    				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getOpusNumber(Converter.getFile(), "444"))
    				.addProperty(modelF22.createProperty(cidoc+ "P106_is_composed_of"), getOpusSubNumber(Converter.getFile(), "444"))
    				);
    	}}
    	/**************************** Expression: ***********************************************/
    	F22.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getNote(Converter.getFile()));
    	
    	/**************************** Expression: key *******************************************/
    	if (!(getKey(Converter.getFile()).equals(""))){
    	F22.addProperty(modelF22.createProperty(mus+ "U11_has_key"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P1_is_identified_by"), modelF22.createLiteral(getKey(Converter.getFile()),"fr")) // Le nom du genre est toujours en français
				);
    	}
    	/**************************** Expression: Genre *****************************************/
    	if (!(getGenre(Converter.getFile()).equals(""))){
    	F22.addProperty(modelF22.createProperty(mus+ "U12_has_genre"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P1_is_identified_by"), modelF22.createLiteral(getGenre(Converter.getFile()),"fr")) // Le nom du genre est toujours en français
				);
    	}
    	/**************************** Expression: Order Number **********************************/
    	F22.addProperty(modelF22.createProperty(mus+ "U10_has_order_number"), getOrderNumber(Converter.getFile(), "144"));
    	
    	if (!(getOrderNumber(Converter.getFile(), "144").equals(getOrderNumber(Converter.getFile(), "444")))) { //Si le contenu de 144 est différent de 444
    		F22.addProperty(modelF22.createProperty(mus+ "U10_has_order_number"), getOrderNumber(Converter.getFile(), "444"));
    	}
    	/**************************** Expression: Casting ***************************************/
    	if (!(getCasting(Converter.getFile()).equals(""))){
    	F22.addProperty(modelF22.createProperty(mus+ "U13_has_casting"), modelF22.createResource()
    			.addProperty(modelF22.createProperty(cidoc+ "P3_has_note"), getCasting(Converter.getFile())) 
				);
    	}
    	/**************************** Expression: Assignation d'identifiant ***********************/
    	PF40_IdentifierAssignment F40= new PF40_IdentifierAssignment();
    	F22.addProperty(modelF22.createProperty(frbroo+ "R45i_was_assigned_by"), modelF22.createResource(F40.getURIF40().toString()));
    	
    	/**************************** Expression: Point d'Accès ********************************/
    	PF50_ControlledAccessPoint F50= new PF50_ControlledAccessPoint();
    	F22.addProperty(modelF22.createProperty(cidoc+ "P1_is_identified_by"), modelF22.createResource(F50.getURIF50().toString()));
    	
    	/**************************** Expression: 1ère exécution********************************/
    	PF25_AutrePerformancePlan FA25= new PF25_AutrePerformancePlan();
    	F22.addProperty(modelF22.createProperty(cidoc+ "P165i_is_incorporated_in"), modelF22.createResource(FA25.getURIF25().toString()));
    	
		return modelF22;
    }
    
    /************************** Type du titre  ************************************************/
	 public static String getTypeNotice (String xmlFile) throws FileNotFoundException {
	    	StringBuilder buffer = new StringBuilder();
	        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
	        MarcXmlReader reader = new MarcXmlReader(file);
	        String typeNotice="";
	        while (reader.hasNext()) { // Parcourir le fichier MARCXML
	        	 Record s = reader.next();
	        	 typeNotice = s.getType();
	        }
	        buffer.append(typeNotice);
	        return buffer.toString();
	    }
	
	 /*********************************** Le titre **************************************/
	    public static String getTitle200(String xmlFile) throws IOException {
	    	
	    	StringBuilder buffer = new StringBuilder(); 
	    	InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
	        MarcXmlReader reader = new MarcXmlReader(file);
	        String title = "";
	        while (reader.hasNext()) { // Parcourir le fichier MARCXML
	          	 Record s = reader.next();
	           	 for (int i=0; i<s.dataFields.size(); i++) {
	           		 if (s.dataFields.get(i).getEtiq().equals("200")) {
	        			 if (s.dataFields.get(i).isCode('a'))
	          				 title = s.dataFields.get(i).getSubfield('a').getData();
	           		 }
	           	 }
	        }
	        buffer.append(title);
	    	return buffer.toString();
	    }
    /*********************************** Le titre **************************************/
    public static String getTitle(String xmlFile, String etiq) throws IOException {
    	
    	StringBuilder buffer = new StringBuilder(); 
    	InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
    	MarcXmlReader reader = new MarcXmlReader(file);
    	String title= ""; 
    		
    	while (reader.hasNext()) { // Parcourir le fichier MARCXML
    		Record s = reader.next();
    		for (int i=0; i<s.dataFields.size(); i++) {
    			if (s.dataFields.get(i).getEtiq().equals(etiq)) {
    				if (s.dataFields.get(i).isCode('a'))
    					title = s.dataFields.get(i).getSubfield('a').getData();
    			}
    		}
    	}
        
    		/*******************************************************************/
    		
    	Boolean trouve = false ; // "trouve=true" si "codeGenre" a �t� trouv� dans le fichier
    	File fichier = new File("Data\\GenresBNF.xlsx");
    	FileInputStream fis = new FileInputStream(fichier);

    	XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); // Trouver l'instance workbook du fichier XLSX
    	XSSFSheet mySheet = myWorkBook.getSheetAt(0); // Retourne la 1ere feuille du workbook XLSX
    	Iterator<Row> iter = mySheet.iterator(); //It�rateur de toutes les lignes de la feuille courante
       
    	while ((iter.hasNext())&&(trouve==false)) { // Traverser chaque ligne du fichier XLSX
    		Boolean correspondance = false ; // correspondance entre 144 $a et le genre courant parcouru
    		Row row = iter.next();
    		Iterator<Cell> cellIterator = row.cellIterator(); 
    		int numColonne = 0; 
    		
    		while ((cellIterator.hasNext())&&(correspondance==false)) { // Pour chaque ligne, it�rer chaque colonne
    			Cell cell = cellIterator.next();
    			if (title.equals(cell.getStringCellValue()) ) trouve = true ; //On a trouv� le code du genre dans le fichier
    			if (trouve == true) {
           	 		correspondance = true ;
    			} 
    			numColonne ++;
    		}
    	}
    	if (trouve==false){
    		buffer.append(title);
    	}
    	else buffer.append("");
    		/*******************************************************************/
        return buffer.toString();
    	}
    
    /*********************************** Le catalogue ***********************************/
    public static String getCatalog(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('k'))
        				 buffer.append(s.dataFields.get(i).getSubfield('k').getData());
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Le nom du catalogue ***********************************/
    public static String getCatalogName(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('k')){
        			 String catalogName= (s.dataFields.get(i).getSubfield('k').getData());
        			 String[] caracter = catalogName.split("");
        				int x = 0 ;
        				catalogName="";
        				while (!(caracter[x].equals(" "))) {
        					catalogName = catalogName + caracter[x];
        					x++;
        				}
        			  buffer.append(catalogName);
        		 }
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Le num du catalogue ***********************************/
    public static String getCatalogNumber(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('k')){
        			 String catalogNumber= (s.dataFields.get(i).getSubfield('k').getData());
        			 String[] caracter = catalogNumber.split("");
        				boolean t = false ;
        				catalogNumber="";
        				for (int x=0; x<caracter.length; x++){
        					if ((caracter[x].equals(" "))) t = true ;
        					else if (t==true) catalogNumber = catalogNumber + caracter[x];
        				}
        			  buffer.append(catalogNumber);
        		 }
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** L'opus ***********************************/
    public static String getOpus(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('p'))
        			 		buffer.append(s.dataFields.get(i).getSubfield('p').getData());
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Le numero d'opus ***********************************/
    public static String getOpusNumber(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('p')){
        			 String opusNumber= (s.dataFields.get(i).getSubfield('p').getData());
        			 String[] caracter = opusNumber.split("");
        			 boolean t = false ;
        			 opusNumber="";
     				 for (int x=0; x<caracter.length; x++){
     					if ((caracter[x].equals(","))) t = true ;
     					else if ((t==false)&&(!(caracter[x].equals("O"))&&!(caracter[x].equals("p"))&&!(caracter[x].equals("."))&&!(caracter[x].equals(" ")))) 
     						opusNumber = opusNumber + caracter[x];
     				}
        			  buffer.append(opusNumber);
        		 }
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Le sous-numero d'opus ***********************************/
    public static String getOpusSubNumber(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('p')){
        			 String opusSubNumber= (s.dataFields.get(i).getSubfield('p').getData());
        			 String[] caracter = opusSubNumber.split("");
        			 boolean t = false ;
        			 opusSubNumber = "";
        			 for (int x=0; x<caracter.length; x++){
        					if ((caracter[x].equals(","))) t = true ;
        					else if ((t==true)&&!(caracter[x].equals("n"))&&!(caracter[x].equals("o"))&&!(caracter[x].equals(" ")))
        						opusSubNumber = opusSubNumber + caracter[x];
        			 }
        			  buffer.append(opusSubNumber);
        		 }
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Note Libre ***********************************/
    public static String getNote(String xmlFile) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        String note909="", note919="";
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals("909")) {
        			 if (s.dataFields.get(i).isCode('a')){
        			 note909 = s.dataFields.get(i).getSubfield('a').getData();
        			 }
        		 }
        		 if (s.dataFields.get(i).getEtiq().equals("919")) {
        			 if (s.dataFields.get(i).isCode('a')){
        			 note919 = s.dataFields.get(i).getSubfield('a').getData();
        			 }
        		 }
        	 }
        }
        if (note909.endsWith(".")) {
        	buffer.append(note909); 
        	buffer.append(" ");
        	buffer.append(note919);
        	}
        else {
        	buffer.append(note909); 
        	buffer.append(".");
        	buffer.append(" ");
        	buffer.append(note919);
        	}
        return buffer.toString();
    	}
    /*********************************** Key (Tonalite) ***********************************/
    public static String getKey(String xmlFile) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals("909")) {
        			 if (s.dataFields.get(i).isCode('d'))
        				 buffer.append(s.dataFields.get(i).getSubfield('d').getData());
        		 }
        	 }
        }
        return buffer.toString();
    	}
    /*********************************** Le numero d'ordre ***********************************/
    public static String getOrderNumber(String xmlFile, String etiq) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        String orderNumber="", orderNumber444="";
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals(etiq)) {
        			 if (s.dataFields.get(i).isCode('n')){
        				 orderNumber= (s.dataFields.get(i).getSubfield('n').getData());
        			 String[] caracter = orderNumber.split("");
        			 orderNumber="";
     				 for (int x=0; x<caracter.length; x++){
     					  if (!(caracter[x].equals("N"))&&!(caracter[x].equals("o"))&&!(caracter[x].equals(" ")))
     						 orderNumber = orderNumber + caracter[x];
     				 }
        			 }
        		 }
        	 }
        }
        buffer.append(orderNumber);
        return buffer.toString();
    	}
    /***************************************** Le genre *************************************/
    public static String getGenre(String xmlFile) throws IOException {
    	StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        String genreA="", genreB="";
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals("610")) {
        			 if (s.dataFields.get(i).isCode('a')){
        				 genreA = s.dataFields.get(i).getSubfield('a').getData();
        			 }
        			 if (s.dataFields.get(i).isCode('b')){
        				 genreB = s.dataFields.get(i).getSubfield('b').getData();
        			 }
        		 }
        	 }
        }
        if (genreB.equals("04")) buffer.append(genreA); //$b=04 veut dire "genre"
        return buffer.toString();
    }
    
    /*********************************** Casting ***********************************/
    public static String getCasting(String xmlFile) throws FileNotFoundException {
        StringBuilder buffer = new StringBuilder();
        InputStream file = new FileInputStream(xmlFile); //Charger le fichier MARCXML a parser
        MarcXmlReader reader = new MarcXmlReader(file);
        while (reader.hasNext()) { // Parcourir le fichier MARCXML
        	 Record s = reader.next();
        	 for (int i=0; i<s.dataFields.size(); i++) {
        		 if (s.dataFields.get(i).getEtiq().equals("909")) {
        			 if (s.dataFields.get(i).isCode('c')){
        			 String casting = s.dataFields.get(i).getSubfield('c').getData();
    				 buffer.append(casting);
        		 }
        		 }
        	 }
        }
        return buffer.toString();
    	}
}
