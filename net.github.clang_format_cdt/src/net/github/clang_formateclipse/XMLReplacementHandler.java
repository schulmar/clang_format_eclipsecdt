package net.github.clang_formateclipse;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.text.edits.ReplaceEdit;
import org.xml.sax.Attributes;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.SAXException;

public class XMLReplacementHandler extends DefaultHandler2 {

	// list of edits read since the document start
	private List<ReplaceEdit> edits;

	// offset of the currently opened replacement
	private int currentOffset;
	// length of the currently opened replacement
	private int currentLength;
	
	private final static String REPLACEMENT_TAG_NAME = "replacement";
	private final static String OFFSET_ATTRIBUTE_NAME = "offset";
	private final static String LENGTH_ATTRIBUTE_NAME = "length";
	
	private StringBuilder charactersBuilder;

	public List<ReplaceEdit> getEdits() {
		return edits;
	}
	
	@Override
	public void characters(char[] characters, int start, int length)
			throws SAXException {
		// only get the text of replacement elements
		if (charactersBuilder != null) {
			charactersBuilder.append(characters, start, length);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// reset the edits list
		edits = new ArrayList<ReplaceEdit>();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(charactersBuilder != null) {
			String replacedText = charactersBuilder.toString(); 
			edits.add(new ReplaceEdit(currentOffset, currentLength,
					replacedText));
			charactersBuilder = null;
		}
	}
	
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// just in case the parser wants to ignore something we want
		characters(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String qname,
			Attributes attributes) throws SAXException {
		// per default assume wrong element
		charactersBuilder = null;
		// only act on the replacement entries
		if (qname == REPLACEMENT_TAG_NAME) {
			charactersBuilder = new StringBuilder();
			{
				String offsetAttributeString = attributes
						.getValue(OFFSET_ATTRIBUTE_NAME);
				if (offsetAttributeString != null) {
					currentOffset = Integer.parseInt(offsetAttributeString);
				} else
					throw new SAXException("Missing " + OFFSET_ATTRIBUTE_NAME
							+ " attribute in " + REPLACEMENT_TAG_NAME
							+ " element");
			}
			{
				String lengthAttributeString = attributes
						.getValue(LENGTH_ATTRIBUTE_NAME);
				if (lengthAttributeString != null) {
					currentLength = Integer.parseInt(lengthAttributeString);
				} else {
					throw new SAXException("Missing " + LENGTH_ATTRIBUTE_NAME
							+ " attribute in " + REPLACEMENT_TAG_NAME
							+ " element");
				}
			}
		}
	}
}
