package org.doremus.marc2rdf.bnfconverter;

import java.net.URI;
import java.net.URISyntaxException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class F19_PublicationWork {

	static Model modelF19 = ModelFactory.createDefaultModel();
	static URI uriF19 = null;
	
	public F19_PublicationWork() throws URISyntaxException{
		this.modelF19 = ModelFactory.createDefaultModel();
		this.uriF19= getURIF19();
	}
	
	String mus = "http://data.doremus.org/ontology/";
    String cidoc = "http://www.cidoc-crm.org/cidoc-crm/";
    String frbroo = "http://erlangen-crm.org/efrbroo/";
    String xsd = "http://www.w3.org/2001/XMLSchema#";
    
	/********************************************************************************************/
    public URI getURIF19() throws URISyntaxException {
    	ConstructURI uri = new ConstructURI();
    	GenerateUUID uuid = new GenerateUUID();
    	uriF19 = uri.getUUID("Publication_Work","F19", uuid.get());
    	return uriF19;
    }
    
    /********************************************************************************************/
    public Model getModel() throws URISyntaxException{
    	
    	Resource F19 = modelF19.createResource(uriF19.toString());
    	
    	/**************************** réalisation d'une work ************************************/
    	F19.addProperty(modelF19.createProperty(frbroo+ "R3_is_realised_in"), modelF19.createResource(F24_PublicationExpression.uriF24.toString()));
    	
		return modelF19;
    }
    /********************************************************************************************/
}
