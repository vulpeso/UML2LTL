package pl.edu.agh.umldiagrams.parser;

import java.io.File;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.controllers.ParsedDiagramController;
import pl.edu.agh.umldiagrams.exceptions.ParseException;

public class Parser {

	private ParsedDiagramController parsedDiagramController;
	private Config config;
	public Parser(Config config) {
		parsedDiagramController = new ParsedDiagramController();
		this.config = config;
	}
	/**
	 * Metoda parsuje podany plik.
	 * 
	 * @param string
	 *           sciezka do pliku xml
	 */
	public void parseFile(String string) throws ParseException {
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(string);
		try {
			Document document = (Document) builder.build(xmlFile);
			// node description:
			Element rootNode = document.getRootElement();
			if(rootNode.getName() != "Project") {
				throw new ParseException("Parse exception: root node should be named \"Project\".");
			}
			List rootChildren = rootNode.getChildren();
			if(((Element)rootChildren.get(0)).getName() != "ProjectInfo") {
				throw new ParseException("Parse exception: first child of root node should be named \"ProjectInfo\".");
			}
			if(((Element)rootChildren.get(1)).getName() != "Models") {
				throw new ParseException("Parse exception: second child of root node should be named \"Models\".");
			}
			if(((Element)rootChildren.get(2)).getName() != "Diagrams") {
				throw new ParseException("Parse exception: third child of root node should be named \"Diagrams\".");
			}
			new ModelNodeParser(parsedDiagramController, config).parse((Element)rootChildren.get(1));
			new DiagramsParser(parsedDiagramController).parse((Element)rootChildren.get(2));

			parsedDiagramController.findRelationships();
//			ParsedDiagramController.getInstance().print();
		} catch (Exception ex) {
			throw new ParseException(ex.getMessage());
		}

	}
	public ParsedDiagramController getParsedDiagramController() {
		return parsedDiagramController;
	}
}
