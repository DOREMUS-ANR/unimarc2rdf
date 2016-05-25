package org.doremus.marc2rdf.ppconverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class PF42_representativeExpressionAssignment {

	static Model modelF42 = ModelFactory.createDefaultModel();
	static URI uriF42=null;

	String mus = "http://data.doremus.org/ontology/";
	String cidoc = "http://www.cidoc-crm.org/cidoc-crm/";
	String frbroo = "http://erlangen-crm.org/efrbroo/";
	String xsd = "http://www.w3.org/2001/XMLSchema#";

	/********************************************************************************************/
	public URI getURIF42() throws URISyntaxException {
		if (uriF42==null){
			ConstructURI uri = new ConstructURI();
			GenerateUUID uuid = new GenerateUUID();
			uriF42 = uri.getUUID("Representative_Expression_Assignment","F42", uuid.get());
		}
		return uriF42;
	}

	/********************************************************************************************/
	public Model getModel() throws URISyntaxException{
		uriF42 = getURIF42();
		Resource F42 = modelF42.createResource(uriF42.toString());

		/**************************** Responsable de l'attribution **********************************/
		F42.addProperty(modelF42.createProperty(frbroo+ "R44_carried_out_by"), modelF42.createResource("http://data.doremus.org/Philharmonie_de_Paris"));

		/**************************** Attribution à ***********************************************/
		PF15_ComplexWork F15= new PF15_ComplexWork();
		F42.addProperty(modelF42.createProperty(frbroo+ "R50_assigned_to"), modelF42.createResource(F15.getURIF15().toString()));

		/**************************** Attribution à ***********************************************/
		PF22_SelfContainedExpression F22= new PF22_SelfContainedExpression();
		F42.addProperty(modelF42.createProperty(frbroo+ "R51_assigned"), modelF42.createResource(F22.getURIF22().toString()));

		return modelF42;
	}
	/********************************************************************************************/

}
