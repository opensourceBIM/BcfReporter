package nl.tue.ddss.bcf;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;

import nl.tue.buildingsmart.express.parser.SchemaLoader;

import org.bimserver.emf.ModelMetaData;
import org.bimserver.interfaces.objects.*;
import org.bimserver.ifc.IfcModel;
import org.bimserver.ifc.step.deserializer.*;
import org.bimserver.models.ifc2x3tc1.*;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.plugins.schema.SchemaDefinition;

public class IfcParserAdd {


		public static void main(String[] args) {
			@SuppressWarnings("unused")
			IfcParserAdd reader = new IfcParserAdd();		
		}

		public IfcParserAdd()
		{
			IfcStepDeserializer stepReader = new IfcStepDeserializer();
			try {
				SchemaDefinition schema = new SchemaLoader("Ifc2x3_TC1.exp").getSchema();
				stepReader.init(schema);
				IfcModel model = (IfcModel) stepReader.read(new File("simpel huisje.ifc"));
				MarkupWriter reportWriter = new MarkupWriter();
				
				ModelMetaData mmd = model.getModelMetaData();
				SIfcHeader header = mmd.getIfcHeader();
				reportWriter.setFilename(header.getFilename());
				reportWriter.setDate(header.getTimeStamp());
				
				List<IfcProject> projects = model.getAll(IfcProject.class);
				for (IfcProject project:projects){
					reportWriter.setIfcProject(project.getGlobalId());
					reportWriter.setReferenceLink("Place reference link here.");
					reportWriter.setTitle("Place exchange requirement here."); //title is the same as the first comment
					reportWriter.setComment("Place exchange requirement here."); //setting several comments should be possible
					reportWriter.setVerbalStatus("Open"); //enumeration of Assigned, Closed, Open and Resolved
					reportWriter.setCommentStatus("Error"); //enumeration of Error, Warning, Info and Unknown as default
					reportWriter.createXMLFromJava();
				}
				List<IfcRoot> wortels = model.getAll(IfcRoot.class);
				for (IfcRoot IfcRootImpl:wortels){	
					reportWriter.setIfcSpatialStructureElement(IfcRootImpl.getGlobalId());
				}
			} catch (DeserializeException | JAXBException e) {
				e.printStackTrace();
			}
			
		}
	}
