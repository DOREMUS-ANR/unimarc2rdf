package org.doremus.marc2rdf.main;

import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.doremus.marc2rdf.ppconverter.PP2RDF;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.PROV;

public abstract class Artist extends DoremusResource {
  protected String function;

  protected Artist() {
    this.model = ModelFactory.createDefaultModel();
  }

  public abstract String getFullName();

  public void addProvenance(Resource intermarcRes, Resource provActivity) {
    this.asResource().addProperty(RDF.type, PROV.Entity)
      .addProperty(PROV.wasAttributedTo, PP2RDF.DOREMUS)
      .addProperty(PROV.wasDerivedFrom, intermarcRes)
      .addProperty(PROV.wasGeneratedBy, provActivity);
  }

  public String getUri() {
    return uri;
  }

  public void addResidence(String data) {
    if (data == null || data.isEmpty()) return;
    data = data.trim().replaceAll("^\\[(.+)]$", "$1").trim();
    E53_Place place = new E53_Place(data);
    this.resource.addProperty(CIDOC.P74_has_current_or_former_residence, place.asResource());
    this.model.add(place.getModel());
  }

  public abstract void addName(String name);

  public abstract boolean hasName(String value);

  protected abstract void interlink();

  public static Artist fromString(String txt) {
    return fromString(txt, false);
  }

  public static Artist fromString(String txt, boolean defaultGroup) {
    if (txt == null) return null;

    Artist artist;
    String _txt = txt.toLowerCase();
    if (defaultGroup || _txt.equals("l'eic") || _txt.startsWith("les ")
      || _txt.contains("philarmo") || _txt.contains("orchestr") ||
      _txt.contains("ensemble") || _txt.contains("membre") || _txt.contains("trio") ||
      _txt.contains("quartet") || _txt.contains("quatuor") || _txt.contains("choeur")) {
      txt = txt.replaceAll("^(l['+]|l[ae]s? )", "").trim();

      artist = new CorporateBody(txt);
    } else artist = new Person(txt);

    artist.interlink();
    return artist;

  }

  public void setFunction(String function) {
    this.function = function;
  }

  public String getFunction() {
    return function;
  }
}
