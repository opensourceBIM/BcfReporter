package nl.tue.ddss.bcf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import nl.tue.ddss.bcf_markup.*;

public class MarkupWriter 
{
	private Date date;
	private String ifcProject;
	private String referenceLink;
	private String title;
	private String comment;
	private String filename;
	private String ifcSpatialStructureElement;
	private String commentStatus;
	private String verbalStatus;
	
	public void setDate(Date theDate) {
		this.date = theDate;
	}
	public void setIfcProject(String theIfcProject) {
		this.ifcProject = theIfcProject;
	}
	public void setReferenceLink(String theReferenceLink) {
		this.referenceLink = theReferenceLink;
	}
	public void setComment(String theComment) {
		this.comment = theComment;
	}
	public void setTitle(String theTitle) {
		this.title = theTitle;
	}
	public void setFilename(String theFilename) {
		this.filename = theFilename;
	}
	public void setIfcSpatialStructureElement(String theIfcSpatialStructureElement) {
		this.ifcSpatialStructureElement = theIfcSpatialStructureElement;
	}
	public void setCommentStatus(String theCommentStatus) {
		this.commentStatus = theCommentStatus;
	}
	public void setVerbalStatus(String theVerbalStatus) {
		this.verbalStatus = theVerbalStatus;
	}

	public void main(String[] Args) {
		try {
			createXMLFromJava();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createXMLFromJava() throws JAXBException {
		ObjectFactory markupFactory = new ObjectFactory();
	
		// create objects that can be used to create each type of complex element
		Markup markup = markupFactory.createMarkup();	
		
		Comment comments = markupFactory.createComment();
		try {
			comments.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String commentGuid = UUID.randomUUID().toString();
		comments.setGuid(commentGuid);
		comments.setVerbalStatus(verbalStatus);
		if(commentStatus == "Error") {
			comments.setStatus(CommentStatus.ERROR);
		}
		if(commentStatus == "Warning") {
			comments.setStatus(CommentStatus.WARNING);
		}
		if(commentStatus == "Info") {
			comments.setStatus(CommentStatus.INFO);
		}
		if(commentStatus == "Unknown") {
			comments.setStatus(CommentStatus.UNKNOWN);
		}
		String commentAuthor = System.getProperty("user.name");
		comments.setAuthor(commentAuthor);
		comments.setComment(comment);
		comments.setTopic(null);
		markup.getComment().add(comments);
	
		Topic topic = markupFactory.createTopic();
		String topicGuid = UUID.randomUUID().toString();
		topic.setGuid(topicGuid);
		topic.setReferenceLink(referenceLink);
		topic.setTitle(title);
		markup.setTopic(topic);
	
		Comment.Topic commentTopic = markupFactory.createCommentTopic();
		commentTopic.setGuid(topicGuid);
		comments.setTopic(commentTopic);
		
		Header.File headerFile = markupFactory.createHeaderFile();
		GregorianCalendar gregorianCalender = new GregorianCalendar();
		gregorianCalender.setTime(date);
		try {  //year-month-day+T+hour-minutes-seconds+timezone
			headerFile.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalender));
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		headerFile.setFilename(filename);
		headerFile.setIfcProject(ifcProject);
		headerFile.setIfcSpatialStructureElement(ifcSpatialStructureElement);
	
		Header header = markupFactory.createHeader();
		header.getFile().add(headerFile);
		markup.setHeader(header);

		JAXBContext ctx = JAXBContext.newInstance(Markup.class);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(markup, System.out);
		try {
			OutputStream os = new FileOutputStream(new File("markup.bcf"));
			m.marshal(markup, os);
		}	catch(Exception e) {
			e.printStackTrace();
		}
		File fileDirectory = new File(topicGuid);
		fileDirectory.mkdir();
	}
}