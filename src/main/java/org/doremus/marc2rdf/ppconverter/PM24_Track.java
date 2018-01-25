package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.marc2rdf.main.DoremusResource;
import org.doremus.marc2rdf.main.Utils;
import org.doremus.marc2rdf.marcparser.DataField;
import org.doremus.marc2rdf.marcparser.Record;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.MUS;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class PM24_Track extends DoremusResource {

  public PM24_Track(DataField field) throws URISyntaxException {
    super(field.getSubfield(3).getData());

    this.resource.addProperty(RDF.type, MUS.M24_Track)
      .addProperty(DCTerms.identifier, this.identifier);


  }

  public PM24_Track(Record record) throws URISyntaxException {
    super(record);

    this.resource.addProperty(RDF.type, MUS.M24_Track)
      .addProperty(DCTerms.identifier, this.identifier)
      .addProperty(MUS.U227_has_content_type, "two-dimensional moving image", "en");

    List<String> funCodes = getFunctionCodes();
    if (funCodes.size() == 1 && funCodes.get(0).equals("800"))
      this.resource.addProperty(MUS.U227_has_content_type, "spoken word", "en");
    else if (funCodes.contains("195") || funCodes.contains("230") ||
      funCodes.contains("545") || funCodes.contains("721"))
      this.resource.addProperty(MUS.U227_has_content_type, "performed music", "en");

    for (String title : getTitles())
      this.resource.addProperty(CIDOC.P102_has_title, title)
        .addProperty(RDFS.label, title);

    String orderNum = getOrderNum();
    if (orderNum != null)
      this.resource.addProperty(MUS.U10_has_order_number, orderNum, XSDDatatype.XSDint);

    String duration = getDuration();
    if (duration != null)
      this.resource.addProperty(MUS.U53_has_duration, duration, XSDDatatype.XSDdayTimeDuration);
  }

  private List<String> getTitles() {
    return record.getDatafieldsByCode(200, 'a').stream()
      .map(String::trim)
      .collect(Collectors.toList());
  }


  public String getOrderNum() {
    for (String f : record.getDatafieldsByCode(856, 'x')) {
      if (f == null || f.isEmpty()) continue;
      int l = f.length();
      return String.valueOf(Integer.parseInt(f.substring(l - 2)));
    }
    return null;
  }

  private String getDuration() {
    for (String f : record.getDatafieldsByCode(215, 'a')) {
      if (f == null || f.isEmpty() || !f.matches(Utils.DURATION_REGEX)) continue;
      return Utils.duration2iso(f);
    }
    return null;
  }

  private List<String> getFunctionCodes() {
    List<String> f = record.getDatafieldByCode(700, 4);
    f.addAll(record.getDatafieldByCode(701, 4));
    f.addAll(record.getDatafieldByCode(702, 4));
    f.addAll(record.getDatafieldByCode(712, 4));
    return f;
  }

}
