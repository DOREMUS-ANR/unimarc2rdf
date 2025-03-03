package org.doremus.marc2rdf.bnfconverter;

import org.apache.jena.rdf.model.Resource;
import org.doremus.marc2rdf.main.DoremusResource;
import org.doremus.marc2rdf.marcparser.Record;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.ontology.MUS;

public class F14_IndividualWork extends DoremusResource {
  private F22_SelfContainedExpression expression;

  public F14_IndividualWork(Record record) {
    super(record);
    this.setClass(FRBROO.F14_Individual_Work);
    this.addProperty(MUS.U94_has_work_type, "musical work");
  }

  public F14_IndividualWork(String identifier) {
    super(identifier);
    this.setClass(FRBROO.F14_Individual_Work);
  }

  public F14_IndividualWork add(F22_SelfContainedExpression expression) {
    if (this.expression == null) this.expression = expression;
    this.addProperty(FRBROO.R9_is_realised_in, expression);
    return this;
  }

  public F14_IndividualWork addPremiere(M42_PerformedExpressionCreation premiere) {
    this.addProperty(MUS.U5_had_premiere, premiere.getMainPerformance());
    return this;
  }

  public F14_IndividualWork add(F30_PublicationEvent publication) {
    this.addProperty(MUS.U4_had_princeps_publication, publication);
    return this;
  }

  public void addMovement(F14_IndividualWork movement) {
    this.addProperty(CIDOC.P148_has_component, movement);
  }

  public F22_SelfContainedExpression getExpression() {
    return expression;
  }

  public void setDerivationType(String derivation) {
    this.addProperty(MUS.U47_has_derivation_type, derivation);
  }

  public void setDerivationType(Resource derivation) {
    this.addProperty(MUS.U47_has_derivation_type, derivation);
  }
}
