package org.doremus.marc2rdf.ppconverter;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateAction;
import org.doremus.marc2rdf.marcparser.MarcXmlHandler;
import org.doremus.marc2rdf.marcparser.MarcXmlReader;
import org.doremus.marc2rdf.marcparser.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.doremus.marc2rdf.main.Converter.properties;

public class PP2RDF {
  public static final MarcXmlHandler.MarcXmlHandlerBuilder ppXmlHandlerBuilder =
    new MarcXmlHandler.MarcXmlHandlerBuilder()
      .recordLabel("NOTICE")
      .datafieldLabel("champs")
      .subfieldLabel("SOUSCHAMP")
      .controlfieldLabel(false)
      .tagLabel("UnimarcTag")
      .codeLabel("UnimarcSubfield")
      .typeLabel("type")
      .idlabel("id");
  public static final String organizationURI = "http://data.doremus.org/organization/Philharmonie_de_Paris";
  public static final String doremusURI = "http://data.doremus.org/organization/DOREMUS";

  public static final String dotSeparator = "(?<![^A-Z][A-Z]|[oO]p|Hob|réf|[èeé]d|dir|Mus|[Ss]t|sept)\\.";
  public static Map<String, String> FUNCTION_MAP = null;

  public static Model convert(String file) throws FileNotFoundException, URISyntaxException {
    if (FUNCTION_MAP == null) {
      try {
        initFunctionMap();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Model model = ModelFactory.createDefaultModel();
    MarcXmlReader reader = new MarcXmlReader(file, PP2RDF.ppXmlHandlerBuilder);

    boolean found = false;
    for (Record r : reader.getRecords()) {
      // Skip TUMs (AIC:14). We will convert them contextually to UNI:100
      if (r.isType("AIC:14")) continue;

      // notice d'oeuvre: retrieve and convert TUM also
      if (r.isType("UNI:100")) {
        List<String> idTUMs = getIdTums(r);
        if (idTUMs.size() < 1) {
          System.out.println("Notice without TUM specified: " + file);
          continue;
        }

        PF15_ComplexWork first = null;
        for (String idTUM : idTUMs)
          try {
            RecordConverter current = convertTum(idTUM.trim(), r.getIdentifier(), model, first);
            first = current.getF15();
          } catch (FileNotFoundException fe) {
            System.out.println("TUM specified but not found: notice " + r.getIdentifier() + ", tum " + idTUM);
          }
      }

      RecordConverter mainRecord = new RecordConverter(r, model);
      if (mainRecord.isConverted()) found = true;
    }
    if (!found) return null;


    // Remove empty nodes
    String query = "delete where {?x ?p \"\" }";
    UpdateAction.parseExecute(query, model);

    return model;
  }

  private static RecordConverter convertTum(String idTUM, String upperIdentifier, Model model, PF15_ComplexWork first) throws FileNotFoundException, URISyntaxException {
    File tum = getTUM(properties.getProperty("TUMFolder"), idTUM);
    if (tum == null) throw new FileNotFoundException();

    MarcXmlReader tumReader = new MarcXmlReader(tum, PP2RDF.ppXmlHandlerBuilder);
    Record tumRecord = tumReader.getRecords().get(0);

    String id = (first == null) ? upperIdentifier : tumRecord.getIdentifier();
    return new RecordConverter(tumRecord, model, id, first);
  }

  private static List<String> getIdTums(Record r) {
    return r.getDatafieldsByCode("500", '3');
  }

  /**
   * Search TUM in a single folder
   **/
  private static File getTUM(final String folder, String idTUM) {
    return getTUM(new File(folder), idTUM);
  }

  private static File getTUM(final File folder, String idTUM) {
    for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
      if (fileEntry.isDirectory()) {
        File f = getTUM(fileEntry, idTUM);
        if (f != null) return f;
      } else if (fileEntry.getName().equals(idTUM + ".xml"))
        return fileEntry;
    }
    return null;
  }


  public static String guessType(Record record) {
    List<String> funCodes = record.getDatafieldsByCode(700, 4);
    funCodes.addAll(record.getDatafieldsByCode(701, 4));
    funCodes.addAll(record.getDatafieldsByCode(702, 4));
    funCodes.addAll(record.getDatafieldsByCode(712, 4));

    if (funCodes.size() == 1 && funCodes.get(0).equals("800"))
      return "spoken word";
    else if (funCodes.contains("195") || funCodes.contains("230") ||
      funCodes.contains("545") || funCodes.contains("721"))
      return "performed music";

    return null;
  }

  private static void initFunctionMap() throws IOException {
    String fmapPath = (new PP2RDF()).getClass().getClassLoader().getResource("ppFunctions.csv").getFile();

    Stream<String> lines = Files.lines(Paths.get(fmapPath));
    FUNCTION_MAP = lines.map(line -> line.split(","))
      .collect(Collectors.toMap(line -> line[0], line -> line[1]));

    lines.close();
  }


}
