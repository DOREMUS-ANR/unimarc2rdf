package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;
import org.doremus.marc2rdf.main.DoremusResource;
import org.doremus.marc2rdf.marcparser.Record;
import org.doremus.ontology.CIDOC;
import org.doremus.ontology.FRBROO;
import org.doremus.ontology.MUS;

import java.util.List;
import java.util.stream.Collectors;

public class PM46_SetOfTracks extends DoremusResource {
  private boolean recordAsATrack = false;

  public PM46_SetOfTracks(String identifier) {
    super(identifier);
    this.setClass(MUS.M46_Set_of_Tracks);
    this.addProperty(DC.identifier, this.identifier);
  }

  public PM46_SetOfTracks(Record record) {
    super(record);

    this.setClass(MUS.M46_Set_of_Tracks);
    this.addProperty(DC.identifier, this.identifier);
    this.addProperty(MUS.U227_has_content_type, "performed music", "en");

    switch (record.getType()) {
      case "UNI:4":
        this.addProperty(MUS.U227_has_content_type, "two-dimensional moving image", "en");
        break;
      case "UNI:2":
      case "UNI:42":
        this.addProperty(MUS.U227_has_content_type, "sounds", "en");
    }


    for (String trackCode : getTracks()) {
      DoremusResource track = record.isType("UNI:2") ?
        new PM46_SetOfTracks(trackCode) : new PM24_Track(trackCode);
      this.addProperty(FRBROO.R5_has_component, track);
    }

    for (String title : getTitles())
      this.addProperty(CIDOC.P102_has_title, title).addProperty(RDFS.label, title);

    for (String subtitle : record.getDatafieldsByCode(200, 'e'))
      this.addProperty(MUS.U67_has_subtitle, subtitle);

      this.addProperty(MUS.U53_has_duration, PM24_Track.getDuration(this.record), XSDDatatype.XSDdayTimeDuration);
  }

  private List<String> getTitles() {
    return record.getDatafieldsByCode(200, 'a').stream()
      .map(String::trim)
      .collect(Collectors.toList());
  }

  private List<String> getTracks() {
    List<String> tracks = record.getDatafieldsByCode(462, 3);
    if (record.isType("UNI:42") && tracks.isEmpty()) {
      recordAsATrack = true;
      tracks.add(this.identifier);
    }
    return tracks;
  }

  public PM46_SetOfTracks add(PM43_PerformedExpression exp) {
    this.addProperty(MUS.U51_is_partial_or_full_recording_of, exp);
    return this;
  }

  public boolean recordAsATrack() {
    return recordAsATrack;
  }
}
