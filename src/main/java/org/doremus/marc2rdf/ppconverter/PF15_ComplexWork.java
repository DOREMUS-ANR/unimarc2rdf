package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.OWL;
import org.doremus.marc2rdf.main.DoremusResource;
import org.doremus.marc2rdf.marcparser.Record;
import org.doremus.ontology.FRBROO;
import org.doremus.ontology.MUS;

public class PF15_ComplexWork extends DoremusResource {

  private PF14_IndividualWork mainF14;
  private PF22_SelfContainedExpression mainF22;

  public PF15_ComplexWork(Record record, String identifier) {
    this(identifier);
    this.record = record;
    this.addProperty(DC.identifier, identifier);
    this.addProperty(OWL.sameAs, model.createResource("http://digital.philharmoniedeparis.fr/doc/CIMU/" + identifier));
  }

  public PF15_ComplexWork(String identifier) {
    super(identifier);
    this.setClass(FRBROO.F15_Complex_Work);
  }

  public PF15_ComplexWork add(PF22_SelfContainedExpression f22) {
    if (record.isType("UNI:100")) {
      this.addProperty(MUS.U38_has_descriptive_expression, f22);
      if (mainF22 == null) this.mainF22 = f22;
    }
    return this;
  }

  public PF15_ComplexWork add(PF14_IndividualWork f14) {
    this.addProperty(FRBROO.R10_has_member, f14);
    if (mainF14 == null) this.mainF14 = f14;
    return this;
  }

  public PF15_ComplexWork add(PM42_PerformedExpressionCreation performance) {
    this.addProperty(FRBROO.R10_has_member, performance.getWork())
      .addProperty(FRBROO.R13_is_realised_in, performance.getExpression());
    return this;
  }

  public PF14_IndividualWork getMainF14() {
    return mainF14;
  }

  public PF22_SelfContainedExpression getMainF22() {
    return mainF22;
  }
}
