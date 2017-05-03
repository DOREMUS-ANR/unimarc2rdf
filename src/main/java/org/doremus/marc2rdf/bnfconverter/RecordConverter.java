package org.doremus.marc2rdf.bnfconverter;

import org.apache.jena.rdf.model.Model;
import org.doremus.marc2rdf.marcparser.Record;

import java.net.URISyntaxException;


public class RecordConverter {
  private Model model;
  private Record record;

  private F22_SelfContainedExpression f22;
  public F28_ExpressionCreation f28;
  private F14_IndividualWork f14;
  private F15_ComplexWork f15;


  public RecordConverter(Record record, Model model) throws URISyntaxException {
    this.record = record;
    this.model = model;

    // Instantiate work and expression
    f28 = new F28_ExpressionCreation(record);
    f22 = new F22_SelfContainedExpression(record, f28);
    f14 = new F14_IndividualWork(record);
    f15 = new F15_ComplexWork(record);
//    F40_IdentifierAssignment f40 = new F40_IdentifierAssignment(record);
    M45_DescriptiveExpressionAssignment f42 = new M45_DescriptiveExpressionAssignment(record);

    addPrincepsPublication();
    addPerformances();
    linkToMovements();

    f28.add(f22).add(f14);
    f15.add(f22).add(f14);
    f14.add(f22);
//    f40.add(f22).add(f14).add(f15);
    f42.add(f22).add(f15);

    model.add(f22.getModel());
    model.add(f28.getModel());
    model.add(f14.getModel());
    model.add(f15.getModel());
//    model.add(f40.getModel());
    model.add(f42.getModel());
  }

  private void addPerformances() throws URISyntaxException {
    int performanceCounter = 0;

    for (String performance : M42_PerformedExpressionCreation.getPerformances(record)) {
      M42_PerformedExpressionCreation m42 = new M42_PerformedExpressionCreation(performance, record, f28, ++performanceCounter);
      F25_PerformancePlan f25 = new F25_PerformancePlan(m42.getIdentifier());

      m42.add(f25);
      f25.add(f22);
      f15.add(m42);

      if (m42.isPremiere()) {
        f14.addPremiere(m42);
        f22.addPremiere(m42);
      }

      model.add(m42.getModel());
      model.add(f25.getModel());
    }
  }

  private void addPrincepsPublication() throws URISyntaxException {
    int pubCounter = 0;

    for (String edition : F30_PublicationEvent.getEditionPrinceps(record)) {

      F30_PublicationEvent f30 = new F30_PublicationEvent(edition.trim(), record, f28, ++pubCounter);
      F24_PublicationExpression f24 = new F24_PublicationExpression(f30.getIdentifier());
      F19_PublicationWork f19 = new F19_PublicationWork(f30.getIdentifier());

      f30.add(f24).add(f19);
      f19.add(f24);
      f24.add(f22);

      if (pubCounter > 1) {
        f14.add(f30);
        f22.add(f30);
      }

      model.add(f24.getModel());
      model.add(f30.getModel());
      model.add(f19.getModel());
    }
  }

  private void linkToMovements() {
    for (String code : record.getDatafieldsByCode("302", '3'))
      try {
        f14.addMovement(new F14_IndividualWork(code));
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
  }

  public Model getModel() {
    return model;
  }

}
