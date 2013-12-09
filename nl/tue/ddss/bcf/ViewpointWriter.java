package nl.tue.ddss.bcf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import nl.tue.ddss.bcf_visinfo.*;

public class ViewpointWriter {
	
	public void main(String[] Args) {
		try {
			createXMLFromJava();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void createXMLFromJava() throws JAXBException {
		ObjectFactory viewpointFactory = new ObjectFactory();
		
		VisualizationInfo visualizationInfo = viewpointFactory.createVisualizationInfo();
		ClippingPlane clippingPlane = viewpointFactory.createClippingPlane();
		Component component = viewpointFactory.createComponent();
		Direction direction = viewpointFactory.createDirection();
		Line line = viewpointFactory.createLine();
		OrthogonalCamera orthogonalCamera = viewpointFactory.createOrthogonalCamera();
		PerspectiveCamera perspectiveCamera = viewpointFactory.createPerspectiveCamera();
		Point point = viewpointFactory.createPoint();
		VisualizationInfo.ClippingPlanes visualizationInfoClippingPlanes = viewpointFactory.createVisualizationInfoClippingPlanes();
		VisualizationInfo.Components visualizationInfoComponents = viewpointFactory.createVisualizationInfoComponents();
		VisualizationInfo.Lines visualizationInfoLines = viewpointFactory.createVisualizationInfoLines();
		
		clippingPlane.setDirection(null);
		clippingPlane.setLocation(null);
		component.setAuthoringToolId(null);
		component.setIfcGuid(null);
		component.setOriginatingSystem(null);
		direction.setX(0);
		direction.setY(0);
		direction.setZ(0);
		line.setEndPoint(null);
		line.setStartPoint(null);
		orthogonalCamera.setCameraDirection(direction);
		orthogonalCamera.setCameraUpVector(direction);
		orthogonalCamera.setCameraViewPoint(point);
		orthogonalCamera.setViewToWorldScale(0);
		perspectiveCamera.setCameraDirection(direction);
		perspectiveCamera.setCameraUpVector(direction);
		perspectiveCamera.setCameraViewPoint(point);
		perspectiveCamera.setFieldOfView(0);
		point.setX(0);
		point.setY(0);
		point.setZ(0);
		visualizationInfoClippingPlanes.getClippingPlane();
		visualizationInfoLines.getLine();
		visualizationInfoComponents.getComponent();
		visualizationInfo.setClippingPlanes(null);
		visualizationInfo.setComponents(null);
		visualizationInfo.setLines(null);
		visualizationInfo.setOrthogonalCamera(null);
		visualizationInfo.setPerspectiveCamera(null);
		
		JAXBContext ctx = JAXBContext.newInstance(VisualizationInfo.class);
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(visualizationInfo, System.out);
		try {
			OutputStream os = new FileOutputStream(new File("viewpoint.bcfv"));
			marshaller.marshal(visualizationInfo, os);
		}	catch(Exception e) {
			e.printStackTrace();
		}
	}	
}
