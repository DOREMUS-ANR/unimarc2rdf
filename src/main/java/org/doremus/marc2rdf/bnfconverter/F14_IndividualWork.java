package org.doremus.marc2rdf.bnfconverter;

import org.apache.jena.vocabulary.RDF;
import org.doremus.marc2rdf.main.DoremusResource;
import org.doremus.marc2rdf.marcparser.Record;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.ontology.MUS;

public class F14_IndividualWork extends DoremusResource {
  public F14_IndividualWork(Record record) {
    super(record);
    this.resource.addProperty(RDF.type, FRBROO.F14_Individual_Work);
  }

  public F14_IndividualWork(String identifier) {
    super(identifier);
  }

  public F14_IndividualWork add(F22_SelfContainedExpression expression) {
    this.resource.addProperty(FRBROO.R9_is_realised_in, expression.asResource());
    return this;
  }

  public F14_IndividualWork addPremiere(M42_PerformedExpressionCreation premiere) {
    this.resource.addProperty(MUS.U5_had_premiere, premiere.getMainPerformance());
    return this;
  }

  public F14_IndividualWork add(F30_PublicationEvent publication) {
    this.resource.addProperty(MUS.U4_had_princeps_publication, publication.asResource());
    return this;
  }

  public void addMovement(F14_IndividualWork movement) {
    this.resource.addProperty(CIDOC.P148_has_component, movement.asResource());
  }
}
