/* CVS $Id: $ */
package org.doremus.ontology; 
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
 
/**
 * Vocabulary definitions from http://www.w3.org/ns/prov 
 * @author Auto-generated by schemagen on 15 Jan 2018 10:57 
 */
public class PROV {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/ns/prov#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "Working Group Note version 2013-04-30";
    
    public static final ObjectProperty actedOnBehalfOf = m_model.createObjectProperty( "http://www.w3.org/ns/prov#actedOnBehalfOf" );
    
    public static final ObjectProperty activity = m_model.createObjectProperty( "http://www.w3.org/ns/prov#activity" );
    
    public static final ObjectProperty agent = m_model.createObjectProperty( "http://www.w3.org/ns/prov#agent" );
    
    public static final ObjectProperty alternateOf = m_model.createObjectProperty( "http://www.w3.org/ns/prov#alternateOf" );
    
    public static final ObjectProperty asInBundle = m_model.createObjectProperty( "http://www.w3.org/ns/prov#asInBundle" );
    
    public static final ObjectProperty atLocation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#atLocation" );
    
    public static final ObjectProperty derivedByInsertionFrom = m_model.createObjectProperty( "http://www.w3.org/ns/prov#derivedByInsertionFrom" );
    
    public static final ObjectProperty derivedByRemovalFrom = m_model.createObjectProperty( "http://www.w3.org/ns/prov#derivedByRemovalFrom" );
    
    public static final ObjectProperty describesService = m_model.createObjectProperty( "http://www.w3.org/ns/prov#describesService" );
    
    public static final ObjectProperty dictionary = m_model.createObjectProperty( "http://www.w3.org/ns/prov#dictionary" );
    
    public static final ObjectProperty entity = m_model.createObjectProperty( "http://www.w3.org/ns/prov#entity" );
    
    public static final ObjectProperty generated = m_model.createObjectProperty( "http://www.w3.org/ns/prov#generated" );
    
    public static final ObjectProperty hadActivity = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadActivity" );
    
    public static final ObjectProperty hadDictionaryMember = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadDictionaryMember" );
    
    public static final ObjectProperty hadGeneration = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadGeneration" );
    
    public static final ObjectProperty hadMember = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadMember" );
    
    public static final ObjectProperty hadPlan = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadPlan" );
    
    public static final ObjectProperty hadPrimarySource = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadPrimarySource" );
    
    public static final ObjectProperty hadRole = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadRole" );
    
    public static final ObjectProperty hadUsage = m_model.createObjectProperty( "http://www.w3.org/ns/prov#hadUsage" );
    
    public static final ObjectProperty has_anchor = m_model.createObjectProperty( "http://www.w3.org/ns/prov#has_anchor" );
    
    public static final ObjectProperty has_provenance = m_model.createObjectProperty( "http://www.w3.org/ns/prov#has_provenance" );
    
    public static final ObjectProperty has_query_service = m_model.createObjectProperty( "http://www.w3.org/ns/prov#has_query_service" );
    
    public static final ObjectProperty influenced = m_model.createObjectProperty( "http://www.w3.org/ns/prov#influenced" );
    
    public static final ObjectProperty influencer = m_model.createObjectProperty( "http://www.w3.org/ns/prov#influencer" );
    
    public static final ObjectProperty insertedKeyEntityPair = m_model.createObjectProperty( "http://www.w3.org/ns/prov#insertedKeyEntityPair" );
    
    public static final ObjectProperty invalidated = m_model.createObjectProperty( "http://www.w3.org/ns/prov#invalidated" );
    
    public static final ObjectProperty mentionOf = m_model.createObjectProperty( "http://www.w3.org/ns/prov#mentionOf" );
    
    public static final ObjectProperty pairEntity = m_model.createObjectProperty( "http://www.w3.org/ns/prov#pairEntity" );
    
    public static final ObjectProperty pingback = m_model.createObjectProperty( "http://www.w3.org/ns/prov#pingback" );
    
    public static final ObjectProperty qualifiedAssociation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedAssociation" );
    
    public static final ObjectProperty qualifiedAttribution = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedAttribution" );
    
    public static final ObjectProperty qualifiedCommunication = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedCommunication" );
    
    public static final ObjectProperty qualifiedDelegation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedDelegation" );
    
    public static final ObjectProperty qualifiedDerivation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedDerivation" );
    
    public static final ObjectProperty qualifiedEnd = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedEnd" );
    
    public static final ObjectProperty qualifiedGeneration = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedGeneration" );
    
    public static final ObjectProperty qualifiedInfluence = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedInfluence" );
    
    public static final ObjectProperty qualifiedInsertion = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedInsertion" );
    
    public static final ObjectProperty qualifiedInvalidation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedInvalidation" );
    
    public static final ObjectProperty qualifiedPrimarySource = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedPrimarySource" );
    
    public static final ObjectProperty qualifiedQuotation = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedQuotation" );
    
    public static final ObjectProperty qualifiedRemoval = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedRemoval" );
    
    public static final ObjectProperty qualifiedRevision = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedRevision" );
    
    public static final ObjectProperty qualifiedStart = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedStart" );
    
    public static final ObjectProperty qualifiedUsage = m_model.createObjectProperty( "http://www.w3.org/ns/prov#qualifiedUsage" );
    
    public static final ObjectProperty specializationOf = m_model.createObjectProperty( "http://www.w3.org/ns/prov#specializationOf" );
    
    public static final ObjectProperty used = m_model.createObjectProperty( "http://www.w3.org/ns/prov#used" );
    
    public static final ObjectProperty wasAssociatedWith = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasAssociatedWith" );
    
    public static final ObjectProperty wasAttributedTo = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasAttributedTo" );
    
    public static final ObjectProperty wasDerivedFrom = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasDerivedFrom" );
    
    public static final ObjectProperty wasEndedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasEndedBy" );
    
    public static final ObjectProperty wasGeneratedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasGeneratedBy" );
    
    public static final ObjectProperty wasInfluencedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasInfluencedBy" );
    
    public static final ObjectProperty wasInformedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasInformedBy" );
    
    public static final ObjectProperty wasInvalidatedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasInvalidatedBy" );
    
    public static final ObjectProperty wasQuotedFrom = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasQuotedFrom" );
    
    public static final ObjectProperty wasRevisionOf = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasRevisionOf" );
    
    public static final ObjectProperty wasStartedBy = m_model.createObjectProperty( "http://www.w3.org/ns/prov#wasStartedBy" );
    
    public static final DatatypeProperty atTime = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#atTime" );
    
    public static final DatatypeProperty endedAtTime = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#endedAtTime" );
    
    public static final DatatypeProperty generatedAtTime = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#generatedAtTime" );
    
    public static final DatatypeProperty invalidatedAtTime = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#invalidatedAtTime" );
    
    public static final DatatypeProperty pairKey = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#pairKey" );
    
    public static final DatatypeProperty provenanceUriTemplate = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#provenanceUriTemplate" );
    
    public static final DatatypeProperty removedKey = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#removedKey" );
    
    public static final DatatypeProperty startedAtTime = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#startedAtTime" );
    
    public static final DatatypeProperty value = m_model.createDatatypeProperty( "http://www.w3.org/ns/prov#value" );
    
    public static final AnnotationProperty aq = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#aq" );
    
    public static final AnnotationProperty category = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#category" );
    
    public static final AnnotationProperty component = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#component" );
    
    public static final AnnotationProperty constraints = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#constraints" );
    
    public static final AnnotationProperty definition = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#definition" );
    
    public static final AnnotationProperty dm = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#dm" );
    
    public static final AnnotationProperty editorialNote = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#editorialNote" );
    
    public static final AnnotationProperty editorsDefinition = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#editorsDefinition" );
    
    public static final AnnotationProperty inverse = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#inverse" );
    
    public static final AnnotationProperty n = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#n" );
    
    public static final AnnotationProperty order = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#order" );
    
    public static final AnnotationProperty qualifiedForm = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#qualifiedForm" );
    
    public static final AnnotationProperty sharesDefinitionWith = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#sharesDefinitionWith" );
    
    public static final AnnotationProperty todo = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#todo" );
    
    public static final AnnotationProperty unqualifiedForm = m_model.createAnnotationProperty( "http://www.w3.org/ns/prov#unqualifiedForm" );
    
    public static final OntProperty activityOfInfluence = m_model.createOntProperty( "http://www.w3.org/ns/prov#activityOfInfluence" );
    
    public static final OntProperty agentOfInfluence = m_model.createOntProperty( "http://www.w3.org/ns/prov#agentOfInfluence" );
    
    public static final OntProperty contributed = m_model.createOntProperty( "http://www.w3.org/ns/prov#contributed" );
    
    public static final OntProperty ended = m_model.createOntProperty( "http://www.w3.org/ns/prov#ended" );
    
    public static final OntProperty entityOfInfluence = m_model.createOntProperty( "http://www.w3.org/ns/prov#entityOfInfluence" );
    
    public static final OntProperty generalizationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#generalizationOf" );
    
    public static final OntProperty generatedAsDerivation = m_model.createOntProperty( "http://www.w3.org/ns/prov#generatedAsDerivation" );
    
    public static final OntProperty hadDelegate = m_model.createOntProperty( "http://www.w3.org/ns/prov#hadDelegate" );
    
    public static final OntProperty hadDerivation = m_model.createOntProperty( "http://www.w3.org/ns/prov#hadDerivation" );
    
    public static final OntProperty hadInfluence = m_model.createOntProperty( "http://www.w3.org/ns/prov#hadInfluence" );
    
    public static final OntProperty hadRevision = m_model.createOntProperty( "http://www.w3.org/ns/prov#hadRevision" );
    
    public static final OntProperty informed = m_model.createOntProperty( "http://www.w3.org/ns/prov#informed" );
    
    public static final OntProperty locationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#locationOf" );
    
    public static final OntProperty qualifiedAssociationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedAssociationOf" );
    
    public static final OntProperty qualifiedAttributionOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedAttributionOf" );
    
    public static final OntProperty qualifiedCommunicationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedCommunicationOf" );
    
    public static final OntProperty qualifiedDelegationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedDelegationOf" );
    
    public static final OntProperty qualifiedDerivationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedDerivationOf" );
    
    public static final OntProperty qualifiedEndOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedEndOf" );
    
    public static final OntProperty qualifiedGenerationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedGenerationOf" );
    
    public static final OntProperty qualifiedInfluenceOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedInfluenceOf" );
    
    public static final OntProperty qualifiedInvalidationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedInvalidationOf" );
    
    public static final OntProperty qualifiedQuotationOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedQuotationOf" );
    
    public static final OntProperty qualifiedSourceOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedSourceOf" );
    
    public static final OntProperty qualifiedStartOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedStartOf" );
    
    public static final OntProperty qualifiedUsingActivity = m_model.createOntProperty( "http://www.w3.org/ns/prov#qualifiedUsingActivity" );
    
    public static final OntProperty quotedAs = m_model.createOntProperty( "http://www.w3.org/ns/prov#quotedAs" );
    
    public static final OntProperty revisedEntity = m_model.createOntProperty( "http://www.w3.org/ns/prov#revisedEntity" );
    
    public static final OntProperty started = m_model.createOntProperty( "http://www.w3.org/ns/prov#started" );
    
    public static final OntProperty wasActivityOfInfluence = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasActivityOfInfluence" );
    
    public static final OntProperty wasAssociateFor = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasAssociateFor" );
    
    public static final OntProperty wasMemberOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasMemberOf" );
    
    public static final OntProperty wasPlanOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasPlanOf" );
    
    public static final OntProperty wasPrimarySourceOf = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasPrimarySourceOf" );
    
    public static final OntProperty wasRoleIn = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasRoleIn" );
    
    public static final OntProperty wasUsedBy = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasUsedBy" );
    
    public static final OntProperty wasUsedInDerivation = m_model.createOntProperty( "http://www.w3.org/ns/prov#wasUsedInDerivation" );
    
    public static final OntClass Accept = m_model.createClass( "http://www.w3.org/ns/prov#Accept" );
    
    public static final OntClass Activity = m_model.createClass( "http://www.w3.org/ns/prov#Activity" );
    
    public static final OntClass ActivityInfluence = m_model.createClass( "http://www.w3.org/ns/prov#ActivityInfluence" );
    
    public static final OntClass Agent = m_model.createClass( "http://www.w3.org/ns/prov#Agent" );
    
    public static final OntClass AgentInfluence = m_model.createClass( "http://www.w3.org/ns/prov#AgentInfluence" );
    
    public static final OntClass Association = m_model.createClass( "http://www.w3.org/ns/prov#Association" );
    
    public static final OntClass Attribution = m_model.createClass( "http://www.w3.org/ns/prov#Attribution" );
    
    public static final OntClass Bundle = m_model.createClass( "http://www.w3.org/ns/prov#Bundle" );
    
    public static final OntClass Collection = m_model.createClass( "http://www.w3.org/ns/prov#Collection" );
    
    public static final OntClass Communication = m_model.createClass( "http://www.w3.org/ns/prov#Communication" );
    
    public static final OntClass Contribute = m_model.createClass( "http://www.w3.org/ns/prov#Contribute" );
    
    public static final OntClass Contributor = m_model.createClass( "http://www.w3.org/ns/prov#Contributor" );
    
    public static final OntClass Copyright = m_model.createClass( "http://www.w3.org/ns/prov#Copyright" );
    
    public static final OntClass Create = m_model.createClass( "http://www.w3.org/ns/prov#Create" );
    
    public static final OntClass Creator = m_model.createClass( "http://www.w3.org/ns/prov#Creator" );
    
    public static final OntClass Delegation = m_model.createClass( "http://www.w3.org/ns/prov#Delegation" );
    
    public static final OntClass Derivation = m_model.createClass( "http://www.w3.org/ns/prov#Derivation" );
    
    public static final OntClass Dictionary = m_model.createClass( "http://www.w3.org/ns/prov#Dictionary" );
    
    public static final OntClass DirectQueryService = m_model.createClass( "http://www.w3.org/ns/prov#DirectQueryService" );
    
    public static final OntClass EmptyCollection = m_model.createClass( "http://www.w3.org/ns/prov#EmptyCollection" );
    
    public static final OntClass EmptyDictionary = m_model.createClass( "http://www.w3.org/ns/prov#EmptyDictionary" );
    
    public static final OntClass End = m_model.createClass( "http://www.w3.org/ns/prov#End" );
    
    public static final OntClass Entity = m_model.createClass( "http://www.w3.org/ns/prov#Entity" );
    
    public static final OntClass EntityInfluence = m_model.createClass( "http://www.w3.org/ns/prov#EntityInfluence" );
    
    public static final OntClass Generation = m_model.createClass( "http://www.w3.org/ns/prov#Generation" );
    
    public static final OntClass Influence = m_model.createClass( "http://www.w3.org/ns/prov#Influence" );
    
    public static final OntClass Insertion = m_model.createClass( "http://www.w3.org/ns/prov#Insertion" );
    
    public static final OntClass InstantaneousEvent = m_model.createClass( "http://www.w3.org/ns/prov#InstantaneousEvent" );
    
    public static final OntClass Invalidation = m_model.createClass( "http://www.w3.org/ns/prov#Invalidation" );
    
    public static final OntClass KeyEntityPair = m_model.createClass( "http://www.w3.org/ns/prov#KeyEntityPair" );
    
    public static final OntClass Location = m_model.createClass( "http://www.w3.org/ns/prov#Location" );
    
    public static final OntClass Modify = m_model.createClass( "http://www.w3.org/ns/prov#Modify" );
    
    public static final OntClass Organization = m_model.createClass( "http://www.w3.org/ns/prov#Organization" );
    
    public static final OntClass Person = m_model.createClass( "http://www.w3.org/ns/prov#Person" );
    
    public static final OntClass Plan = m_model.createClass( "http://www.w3.org/ns/prov#Plan" );
    
    public static final OntClass PrimarySource = m_model.createClass( "http://www.w3.org/ns/prov#PrimarySource" );
    
    public static final OntClass Publish = m_model.createClass( "http://www.w3.org/ns/prov#Publish" );
    
    public static final OntClass Publisher = m_model.createClass( "http://www.w3.org/ns/prov#Publisher" );
    
    public static final OntClass Quotation = m_model.createClass( "http://www.w3.org/ns/prov#Quotation" );
    
    public static final OntClass Removal = m_model.createClass( "http://www.w3.org/ns/prov#Removal" );
    
    public static final OntClass Replace = m_model.createClass( "http://www.w3.org/ns/prov#Replace" );
    
    public static final OntClass Revision = m_model.createClass( "http://www.w3.org/ns/prov#Revision" );
    
    public static final OntClass RightsAssignment = m_model.createClass( "http://www.w3.org/ns/prov#RightsAssignment" );
    
    public static final OntClass RightsHolder = m_model.createClass( "http://www.w3.org/ns/prov#RightsHolder" );
    
    public static final OntClass Role = m_model.createClass( "http://www.w3.org/ns/prov#Role" );
    
    public static final OntClass ServiceDescription = m_model.createClass( "http://www.w3.org/ns/prov#ServiceDescription" );
    
    public static final OntClass SoftwareAgent = m_model.createClass( "http://www.w3.org/ns/prov#SoftwareAgent" );
    
    public static final OntClass Start = m_model.createClass( "http://www.w3.org/ns/prov#Start" );
    
    public static final OntClass Submit = m_model.createClass( "http://www.w3.org/ns/prov#Submit" );
    
    public static final OntClass Usage = m_model.createClass( "http://www.w3.org/ns/prov#Usage" );
    
    public static final Individual __ = m_model.createIndividual( "http://www.w3.org/ns/prov#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual prov_20130312 = m_model.createIndividual( "http://www.w3.org/ns/prov-20130312", Entity );
    
    public static final Individual ___INSTANCE = m_model.createIndividual( "http://www.w3.org/ns/prov-aq#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual ___INSTANCE1 = m_model.createIndividual( "http://www.w3.org/ns/prov-dc#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual ___INSTANCE2 = m_model.createIndividual( "http://www.w3.org/ns/prov-dictionary#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual prov_links = m_model.createIndividual( "http://www.w3.org/ns/prov-links", m_model.createClass( "http://www.w3.org/2002/07/owl#Thing" ) );
    
    public static final Individual ___INSTANCE3 = m_model.createIndividual( "http://www.w3.org/ns/prov-links#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual prov_o = m_model.createIndividual( "http://www.w3.org/ns/prov-o", Entity );
    
    public static final Individual ___INSTANCE4 = m_model.createIndividual( "http://www.w3.org/ns/prov-o#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual prov_o_20120312 = m_model.createIndividual( "http://www.w3.org/ns/prov-o-20120312", Entity );
    
    public static final Individual prov_o_20130430 = m_model.createIndividual( "http://www.w3.org/ns/prov-o-20130430", m_model.createClass( "http://www.w3.org/2002/07/owl#Thing" ) );
    
    public static final Individual prov_o_inverses = m_model.createIndividual( "http://www.w3.org/ns/prov-o-inverses", m_model.createClass( "http://www.w3.org/2002/07/owl#Thing" ) );
    
    public static final Individual ___INSTANCE5 = m_model.createIndividual( "http://www.w3.org/ns/prov-o-inverses#", m_model.createClass( "http://www.w3.org/2002/07/owl#Ontology" ) );
    
    public static final Individual prov_o_inverses_20120312 = m_model.createIndividual( "http://www.w3.org/ns/prov-o-inverses-20120312", m_model.createClass( "http://www.w3.org/2002/07/owl#Thing" ) );
    
}
