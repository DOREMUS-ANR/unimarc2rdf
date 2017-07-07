package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.marc2rdf.main.Converter;
import org.doremus.marc2rdf.main.StanfordLemmatizer;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.MUS;
import org.doremus.vocabulary.VocabularyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PM23_Casting_Detail {
  private final StanfordLemmatizer slem;
  String uri;

  String name;
  String note;
  int quantity;
  boolean solo;

  public PM23_Casting_Detail(String name, int quantity, boolean isSubSolo, String uri) {
    this.name = name;
    this.note = name;
    this.quantity = quantity;
    this.solo = isSubSolo;
    this.uri = uri;

    this.slem = Converter.stanfordLemmatizer;

    name = name
      .replaceAll("ou \\d", "") // 2 ou 4 bassoon
      .replaceAll("non spécifiée", "").trim();


    String gradeRegex = "(.+) (I+|\\d)";
    if (name.matches(gradeRegex)) {// Violon II, soprano 1, soprano 2
      Pattern grade = Pattern.compile(gradeRegex);
      Matcher matcher = grade.matcher(name);
      matcher.find();

      name = matcher.group(0);
    }

    this.name = instrumentToSingular(name);

  }

  private String instrumentToSingular(String r) {
    // punctual fix
    if (r.equals("flûtes")) r = "flûte";
    if (r.equals("contrebasses")) r = "contrebasse";
    if (r.equals("altos")) r = "alto";

    String[] parts = r.split(" ");
    if (parts.length == 1) return slem.lemmatize(parts[0]).get(0);

    // cornets à pistons --> cornet à pistons
    parts[0] = slem.lemmatize(parts[0]).get(0);
    return String.join(" ", parts);
  }

  public Resource asResource(Model model) {
    Resource M23CastingDetail = model.createResource(uri)
      .addProperty(RDF.type, MUS.M23_Casting_Detail)
      .addProperty(RDFS.label, (quantity > -1 ? quantity + " " : "") + name + (solo ? " solo" : ""))
      .addProperty(CIDOC.P3_has_note, note, "fr");

    if (solo)
      M23CastingDetail.addProperty(MUS.U36_foresees_responsibility_of_type, model.createLiteral("soloist", "fr"));

    if (quantity > -1)
      M23CastingDetail.addProperty(MUS.U30_foresees_quantity_of_mop, model.createTypedLiteral(quantity));

    Literal mopLiteral = model.createLiteral(name, "fr");

    Resource match = VocabularyManager.searchInCategory(name, "fr", "mop");

    if (match != null)
      M23CastingDetail.addProperty(MUS.U2_foresees_use_of_medium_of_performance_of_type, match);
    else M23CastingDetail.addProperty(MUS.U2_foresees_use_of_medium_of_performance_of_type, model.createResource()
      .addProperty(RDF.type, MUS.M14_Medium_Of_Performance)
      .addProperty(RDFS.label, mopLiteral)
      .addProperty(CIDOC.P1_is_identified_by, mopLiteral));


    return M23CastingDetail;
  }

  public String getLName() {
    return name.toLowerCase();
  }

  public void setAsVoice() {
    switch (this.name) {
      case "alto":
        this.name = "contralto";
        break;
      case "baryton":
        this.name = "baritono";
        break;
    }
  }
}
