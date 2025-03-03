package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.marc2rdf.main.StanfordLemmatizer;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.MUS;
import org.doremus.string2vocabulary.VocabularyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PM23_Casting_Detail {
  private final StanfordLemmatizer slem;
  private final String GRADE_REGEX = "(.+) (I+|\\d)";
  private final Pattern GRADE_PATTERN = Pattern.compile(GRADE_REGEX);
  String uri;
  String name;

  private String namePlural, nameComplete;
  private String note;
  private String lang;
  int quantity;
  private boolean solo;


  public PM23_Casting_Detail(String name, int quantity, boolean isSubSolo, String uri) {
    this.name = name;
    this.note = name;
    this.quantity = quantity;
    this.solo = isSubSolo;
    this.uri = uri;
    this.lang = "fr";

    this.slem = StanfordLemmatizer.getDefaultLemmatizer();

    name = name
      .replaceAll("ou \\d", "") // 2 ou 4 bassoon
      .replaceAll("non spécifiée?", "")
      .replaceAll("\\(\\)", "")
      .trim()
      .replaceAll("[.\\-/]$", "")
      .replaceAll("^[.\\-/]", "");

    if (name.matches(GRADE_REGEX)) {// Violon II, soprano 1, soprano 2
      Matcher matcher = GRADE_PATTERN.matcher(name);
      matcher.find();

      name = matcher.group(0);
    }
    name = name.replaceAll("(premieres?|seconds?) ", "");

    this.nameComplete = name;

    this.namePlural = name
      .replaceAll("[(\\[].+[)\\]]", "").trim()
      .replaceFirst("(?i)en (ut|ré|mi|fa|sol|la|si)( b(émol)?)?", "");
    this.name = instrumentToSingular(namePlural);
  }

  private String instrumentToSingular(String r) {
    if (r == null || r.isEmpty()) return null;

    // punctual fix
    if (r.equals("flûtes")) r = "flûte";
    if (r.equals("contrebasses")) r = "contrebasse";
    if (r.equals("altos")) r = "alto";
    if (r.equals("percussioniste") || r.equals("percussions")) return "percussions";

    String[] parts = r.split(" ");
    if (parts.length == 1) return slem.lemmatize(parts[0]).get(0);

    // cornets à pistons --> cornet à pistons
    parts[0] = slem.lemmatize(parts[0]).get(0);
    return String.join(" ", parts);
  }

  public Resource asResource(Model model) {
    Resource M23CastingDetail = model.createResource(uri)
      .addProperty(RDF.type, MUS.M23_Casting_Detail)
      .addProperty(RDFS.label, (quantity > -1 ? quantity + " " : "") + nameComplete + (solo ? " solo" : ""))
      .addProperty(RDFS.comment, note, lang)
      .addProperty(CIDOC.P3_has_note, note, lang);

    if (solo)
      M23CastingDetail.addProperty(MUS.U36_foresees_responsibility, model.createLiteral("soloist", lang));

    if (quantity > -1)
      M23CastingDetail.addProperty(MUS.U30_foresees_quantity_of_mop, model.createTypedLiteral(quantity));

    Literal mopLiteral = model.createLiteral(name, lang);


    Resource match = getMatch();

    if (match != null)
      M23CastingDetail.addProperty(MUS.U2_foresees_use_of_medium_of_performance, match);
    else M23CastingDetail.addProperty(MUS.U2_foresees_use_of_medium_of_performance,
      model.createResource(this.uri + "/mop")
        .addProperty(RDF.type, MUS.M14_Medium_Of_Performance)
        .addProperty(RDFS.label, mopLiteral)
        .addProperty(CIDOC.P1_is_identified_by, mopLiteral));


    return M23CastingDetail;
  }

  private Resource getMatch() {
    Resource match = VocabularyManager.searchInCategory(name, lang, "mop", false);

    if (match != null) return match;

    // workaround for choirs
    String _name = name.toLowerCase();
    if (_name.contains("choeur") || _name.contains("choristes") || name.equals("satb"))
      return VocabularyManager.searchInCategory("choir", "en", "mop", false);

    // workaround for voices
    if (_name.contains("voix")) {
      String voixClean = _name.replace("voix", "").trim()
        .replaceAll("^[^\\w]+", "").trim();
      String _is = toItalianSinger(voixClean);
      if (_is != null) {
        lang = "it";
        voixClean = _is;
      }
      match = VocabularyManager.searchInCategory(voixClean, lang, "mop", false);

      if (match != null) return match;
      return VocabularyManager.searchInCategory("voix", lang, "mop", false);
    }

    // 2nd attempt, pluralize the whole name (i.e. saxophones sopranos -> saxophone soprano)
    match = VocabularyManager.searchInCategory(namePlural, lang, "mop", true);
    return match;
  }

  public String getLName() {
    if (name == null) return null;
    return name.toLowerCase();
  }

  public void setAsVoice() {
    String _name = toItalianSinger(this.name);
    if (_name == null) return;

    this.lang = "it";
    this.name = _name;
  }

  static String toItalianSinger(String input) {
    return toItalianSinger(input, false);
  }

  static String toItalianSinger(String input, boolean safeMode) {
    switch (input) {
      case "alto":
        return "contralto";
      case "baryton":
        return "baritono";
      default:
        return safeMode ? input : null;
    }
  }

}
