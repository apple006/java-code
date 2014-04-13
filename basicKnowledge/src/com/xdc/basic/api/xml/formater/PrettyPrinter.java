package com.xdc.basic.api.xml.formater;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.xdc.basic.skills.GetCurPath;

public class PrettyPrinter
{
	public static void main(String[] args)
	{
		String curPath = GetCurPath.getCurPath();

		try
		{
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			Document doc = builder.build(new File(curPath + "users.xml"));
			// Output the document, use standard formatter
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(doc, System.out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}