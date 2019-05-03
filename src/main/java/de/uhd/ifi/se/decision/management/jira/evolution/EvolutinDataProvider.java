package de.uhd.ifi.se.decision.management.jira.evolution;

import de.uhd.ifi.se.decision.management.jira.model.DecisionKnowledgeElement;
import de.uhd.ifi.se.decision.management.jira.persistence.AbstractPersistenceManager;

import javax.xml.bind.annotation.XmlElement;
import java.util.HashSet;
import java.util.List;

public class EvolutinDataProvider {
	private List<DecisionKnowledgeElement> elementList;

	@XmlElement
	private HashSet<EvolutionNode> dataSet;

	public EvolutinDataProvider(String projectKey){
		if(projectKey != null) {
			AbstractPersistenceManager strategy = AbstractPersistenceManager.getDefaultPersistenceStrategy(projectKey);
			elementList = strategy.getDecisionKnowledgeElements();
		}
		createDataSet();
	}

	public HashSet<EvolutionNode> getEvolutionData(){
		return dataSet;
	}

	private void createDataSet(){
		dataSet = new HashSet<>();
		dataSet.add(new EvolutionNode("Test Item 1", "2019-03-1"));
		dataSet.add(new EvolutionNode("Test Item 2", "2019-03-4"));
		dataSet.add(new EvolutionNode("Test Item 3", "2019-03-20"));
	}
}
