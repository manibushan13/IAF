package com.innominds.itaf.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The Class XMLUtils - reads, manupulate, create XML file/files.
 * 
 * @author Chaya Venkateswarlu
 */
public class XMLUtils {
	
	
	
	/**
	 * Pick xpath data.
	 * 
	 * @param data
	 *            the data
	 * @param xpath
	 *            the xpath
	 * @return the string
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	@SuppressWarnings("unused")
	public static String pickXpathData(String data, String xpath)
	{
		String retVal = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			ByteArrayInputStream input = new ByteArrayInputStream(data.toString().getBytes("UTF-8"));
			Document doc = builder.parse(input);
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = xpath;
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			for (int i = 0; i < nodeList.getLength(); i++) 
			{
				Node nNode = nodeList.item(i);
				Node firstChild = nNode.getFirstChild();
				retVal = firstChild.getNodeValue();
				break;
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to pick from XML Xpath data"+e.getMessage());
		}
		return retVal;
	}
	
	/**
	 * Read raw file.
	 * 
	 * @param path
	 *            the path
	 * @param encoding
	 *            the encoding
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readRawFile(String path, Charset encoding)
	{
		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to read Raw XML"+e.getMessage());
		}
	}
	
	
	/**
	 * Read XML Root element.
	 * 
	 * @param path
	 *  
	 */
	public static String readXMLRootElement(String path)
	{
		String rootElement = null;
		try
		{
				File fXmlFile = new File(path);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				rootElement = doc.getDocumentElement().getNodeName();
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to read Root Element of XML"+e.getMessage());
		}
		return rootElement;
	}
	

	/**
	 * Read XML Node values.
	 * 
	 * @param path
	 *            the path
	 * @param encoding
	 *            the encoding
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void readXMLByTag(String path)
	{
		try
		{
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			if(doc.hasChildNodes())
			{
				NodeList nodeList = doc.getChildNodes();
				nodeXMLData(nodeList);
			}
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to read XML by tag"+e.getMessage());
		}
	}
			
			
	/*
	 * XML  Node data
	 */
	public void nodeXMLData(NodeList nodeList)
	{
		try
		{
			for (int count = 0; count < nodeList.getLength(); count++) 
			{
				Node tempNode = nodeList.item(count);
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
					System.out.println("Node Value =" + tempNode.getTextContent());
					
					if (tempNode.hasAttributes()) 
					{
						NamedNodeMap nodeMap = tempNode.getAttributes();
						for (int i = 0; i < nodeMap.getLength(); i++) 
						{
							Node node = nodeMap.item(i);
							System.out.println("attr name : " + node.getNodeName());
							System.out.println("attr value : " + node.getNodeValue());
						}
					}
					if (tempNode.hasChildNodes()) 
					{
						nodeXMLData(tempNode.getChildNodes());
					}
					System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
				}
		}

			
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to read XML node data by tag"+e.getMessage());
		}
	}



}
