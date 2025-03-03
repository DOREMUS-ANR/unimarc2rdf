package org.doremus.marc2rdf.main;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.isnimatcher.ISNIRecord;
import org.doremus.marc2rdf.bnfconverter.FunctionMap;
import org.doremus.marc2rdf.marcparser.DataField;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.Schema;

import java.io.IOException;
import java.net.URISyntaxException;

public class Person extends Artist {
  private String fullName;
  private String firstName, lastName, birthDate, deathDate, lang;
  private String birthPlace, deathPlace;
  private TimeSpan timeSpan;

  public Person(String firstName, String lastName, String birthDate, String deathDate, String lang) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = safeDate(birthDate);
    this.deathDate = safeDate(deathDate);
    this.lang = lang;

    if (lastName == null) {
      throw new RuntimeException("Missing artist value: null | " + lastName + " | " + birthDate);
    }

    try {
      this.uri = ConstructURI.build("E21_Person", firstName, lastName, birthDate).toString();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    initResource();
  }

  private String safeDate(String date) {
    if (date == null || date.isEmpty() || date.contains("[")) return null;
    return date;
  }

  public Person(String firstName, String lastName, String birthDate) {
    this(firstName, lastName, birthDate, null, null);
  }

  public Person(String firstName, String lastName, String birthDate, String lang) {
    this(firstName, lastName, birthDate, null, lang);
  }

  public Person(String fullName) {
    super();
    this.fullName = fullName.trim();
    this.firstName = null;
    this.lastName = null;
    this.birthDate = null;
    this.deathDate = null;
    this.lang = null;

    try {
      this.uri = ConstructURI.build("E21_Person", null, fullName, null).toString();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    initResource();
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  private boolean hasBirthDate() {
    return this.getBirthDate() != null && !this.getBirthDate().isEmpty();
  }

  public String getBirthDate() {
    return birthDate;
  }

  public String getBirthYear() {
    if (!hasBirthDate()) return null;
    String year = birthDate;
    if (birthDate.length() > 4) year = year.substring(0, 4);
    return year.replaceAll("\\?", ".");
  }

  public String getDeathDate() {
    if (deathDate == null || deathDate.replaceAll("\\.", "").isEmpty())
      return null;
    return deathDate;
  }

  public void setBirthPlace(String place) {
    if (place == null || StringUtils.isBlank(place) ||
      place.replaceAll("\\.", "").equalsIgnoreCase("sl")) return;
    this.birthPlace = place;
    if (resource == null) return;

    this.addProperty(Schema.birthPlace, new E53_Place(place));
  }

  public void setDeathPlace(String place) {
    if (place == null) return;
    this.deathPlace = place;
    if (resource == null) return;

//    // if not country specified
//    String country = null;
//    if (this.birthPlace != null && !place.contains("(") && this.birthPlace.contains("(")) {
//      // add the one of the birth place only if it is 2 letters
//      // or a known country
//      String candidateCountry = this.birthPlace.split("[()]")[1];
//      if (candidateCountry.length() == 2)
//        country = candidateCountry;
//      else  if (candidateCountry.equalsIgnoreCase("italie"))
//        country = "it";
//    }

    this.addProperty(Schema.deathPlace, new E53_Place(place));
  }

  public String getLang() {
    return lang;
  }


  public String getFullName() {
    if (this.fullName != null) return this.fullName;
    String fullName = this.getLastName();
    if (this.getFirstName() != null) fullName = this.getFirstName() + " " + this.getLastName();
    return fullName;
  }

  @Override
  public void addName(String name) {
    this.resource.addProperty(FOAF.name, name);
  }

  @Override
  public boolean hasName(String value) {
    if (value == null || value.isEmpty()) return false;
    value = value.trim();

    if (value.contains(", ")) {
      String[] parts = value.split(", ");
      if (parts[0].equalsIgnoreCase(this.getLastName()) && parts[1].equalsIgnoreCase(this.getFirstName()))
        return true;
    }
    return value.equalsIgnoreCase(this.getFullName());
  }

  public String getIdentification() {
    String identification = this.getLastName();
    if (this.getFirstName() != null) identification += ", " + this.getFirstName();
    if (this.getBirthDate() != null) {
      identification += " (" + this.getBirthDate();
      if (this.getDeathDate() != null) identification += "-" + this.getDeathDate();
      identification += ")";
    }
    return identification;
  }

  private Resource initResource() {
    this.resource = model.createResource(this.getUri());
    resource.addProperty(RDF.type, CIDOC.E21_Person);

    addProperty(FOAF.firstName, this.getFirstName(), lang);
    addProperty(FOAF.surname, this.getLastName(), lang);
    addProperty(FOAF.name, this.getFullName(), lang);
    addProperty(RDFS.label, this.getFullName(), lang);
    addProperty(CIDOC.P131_is_identified_by, this.getIdentification(), lang);

    addDate(this.getBirthDate(), false);
    addDate(this.getDeathDate(), true);

    return resource;
  }

  public void addDate(String date, boolean isDeath) {
    this.timeSpan = cleanDate(date);
    if (timeSpan == null) return;

    String url = this.uri + (isDeath ? "/death" : "/birth");
    timeSpan.setUri(url + "/interval");
    addProperty(isDeath ? CIDOC.P100i_died_in : CIDOC.P98i_was_born,
      model.createResource(url)
        .addProperty(RDF.type, isDeath ? CIDOC.E69_Death : CIDOC.E67_Birth)
        .addProperty(CIDOC.P4_has_time_span, timeSpan.asResource())
    );

    if (timeSpan.getStart() != null)
      this.resource.addProperty(isDeath ? Schema.deathDate : Schema.birthDate, timeSpan.getStart());

    model.add(timeSpan.getModel());
  }

  public void addPropertyResource(Property property, String uri) {
    if (property == null || uri == null) return;
    resource.addProperty(property, model.createResource(uri));
  }

  private TimeSpan cleanDate(String d) {
    if (d == null || d.isEmpty() || d.startsWith(".") || d.equals("compositeur")) return null;
    if (d.equals("?")) return TimeSpan.emptyUncertain();
    TimeSpan ts;
    d = d.replaceFirst("(.{4}\\??) BC", "-$1");

    boolean uncertain = false;
    boolean after = false;
    String uncertainRegex = "(.{4}) ?\\?";
    if (d.matches(uncertainRegex)) {
      uncertain = true;
      d = d.replaceFirst(uncertainRegex, "$1");
    }
    if (d.startsWith("ca")) {
      uncertain = true;
      d = d.replaceFirst("^ca", "").trim();
    }
    if (d.startsWith("après")) {
      after = true;
      d = d.replaceFirst("^après", "").trim();
    }
    // "850?" is 850-uncertain, not 8500-precision at decade
    if (!d.startsWith("1") && d.length() > 3 && d.charAt(3) == '?') {
      uncertain = true;
      d = d.substring(0, 3);
    }
    if (d.replaceFirst("^-", "").length() > 4) {
      // I have the info on the end date!
      ts = new TimeSpan(d.substring(0, 4), d.substring(4));
    } else ts = new TimeSpan(d);

    if (uncertain) ts.setQuality(TimeSpan.Precision.UNCERTAINTY);
    if (after) ts.setQuality(TimeSpan.Precision.AFTER);

    return ts;
  }

  public static Person fromUnimarcField(DataField field) {
    // philharmonie
    if (field == null) return null;
    // for fields 700, 701, 721
    String firstName = null, lastName = null, birthDate = null, deathDate = null;
    if (field.isCode('a'))  // surname
      lastName = field.getString('a').trim();

    if (field.isCode('b'))  // name
      firstName = field.getString('b').trim();

    if (field.isCode('f')) { // birth - death dates
      String[] dates = field.getString('f').split("-");
      birthDate = dates[0].trim();
      if (dates.length > 1) deathDate = dates[1].trim();
    }

    return new Person(firstName, lastName, birthDate, deathDate, null);
  }

  public static Person fromIntermarcField(DataField field) {
    if (field == null) return null;

    String firstName = field.getString('m');
    String lastName = field.getString('a');
    if (lastName == null) return null;

    String birthDate = null;
    String deathDate = null;
    String lang = null;
    String function = null;

    if (field.isCode('d')) { // birth - death dates
      String d = field.getString('d');

      // av. J.-C.
      d = d.replaceAll("av\\. J\\.?-C\\.?", "BC");
      String[] dates = d.split("[-–]");
      birthDate = dates[0].trim();
      if (dates.length > 1) deathDate = dates[1].trim();
    }

    if (field.isCode('4')) { // function
      FunctionMap fm = FunctionMap.get(field.getString('4'));
      if (fm != null) function = fm.getFunction();
    }

    if (field.isCode('w')) { // lang
      String w = field.getString('w');
      lang = Utils.intermarcExtractLang(w);
    }

    Person p = new Person(firstName, lastName, birthDate, deathDate, lang);
    p.setFunction(function);
    return p;
  }

  @Override
  public void interlink() {
    // 1. search in doremus by name/date
    Resource match = getPersonFromDoremus();
    if (match != null) {
      this.setUri(match.getURI());
      return;
    }

    // 2. search in isni by name/date
    ISNIRecord isniMatch = null;
    try {
      isniMatch = ISNIWrapper.search(this.getFullName(), this.getBirthYear());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (isniMatch == null) return;

    // 3. search in doremus by isni
    match = getPersonFromDoremus(isniMatch.uri);
    if (match != null) {
      this.setUri(match.getURI());
      return;
    }

    // 4. add isni info
    this.isniEnrich(isniMatch);
  }

  private Resource getPersonFromDoremus() {
    return getFromDoremus(this.getFullName(), this.getBirthYear());
  }

  private static final String NAME_SPARQL = "PREFIX ecrm: <" + CIDOC.getURI() + ">\n" +
    "PREFIX foaf: <" + FOAF.getURI() + ">\n" +
    "PREFIX schema: <" + Schema.getURI() + ">\n" +
    "SELECT DISTINCT ?s " +
    "FROM <http://data.doremus.org/bnf> " +
    "WHERE { ?s a ecrm:E21_Person; foaf:name ?name. }";
  private static final String NAME_DATE_SPARQL = "PREFIX ecrm: <" + CIDOC.getURI() + ">\n" +
    "PREFIX foaf: <" + FOAF.getURI() + ">\n" +
    "PREFIX schema: <" + Schema.getURI() + ">\n" +
    "SELECT DISTINCT ?s " +
    "FROM <http://data.doremus.org/bnf> " +
    "WHERE { ?s a ecrm:E21_Person; foaf:name ?name. " +
    "?s schema:birthDate ?date. FILTER regex(str(?date), ?birthDate) }";

  public static Resource getFromDoremus(String name, String birthDate) {
    ParameterizedSparqlString pss = new ParameterizedSparqlString();
    pss.setCommandText(birthDate != null ? NAME_DATE_SPARQL : NAME_SPARQL);
    pss.setLiteral("name", name);
    if (birthDate != null) pss.setLiteral("birthDate", birthDate);

    return (Resource) Utils.queryDoremus(pss, "s");
  }

  private static final String ISNI_SPARQL = "PREFIX owl: <" + OWL.getURI() + ">\n" +
    "SELECT DISTINCT * WHERE { ?s owl:sameAs ?isni }";

  private static Resource getPersonFromDoremus(String isni) {
    ParameterizedSparqlString pss = new ParameterizedSparqlString();
    pss.setCommandText(ISNI_SPARQL);
    pss.setIri("isni", isni);
    return (Resource) Utils.queryDoremus(pss, "s");
  }

  public void isniEnrich(ISNIRecord isni) {
    this.addPropertyResource(OWL.sameAs, isni.uri);
    this.addPropertyResource(OWL.sameAs, isni.getViafURI());
    this.addPropertyResource(OWL.sameAs, isni.getMusicBrainzUri());
    this.addPropertyResource(OWL.sameAs, isni.getMuziekwebURI());
    this.addPropertyResource(OWL.sameAs, isni.getWikidataURI());

    String wp = isni.getWikipediaUri();
    String dp = isni.getDBpediaUri();

    if (wp == null) {
      wp = isni.getWikipediaUri("fr");
      dp = isni.getDBpediaUri("fr");
    }
    this.addPropertyResource(OWL.sameAs, dp);
    this.addPropertyResource(FOAF.isPrimaryTopicOf, wp);
  }
}
