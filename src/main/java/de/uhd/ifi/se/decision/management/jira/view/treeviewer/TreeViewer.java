package de.uhd.ifi.se.decision.management.jira.view.treeviewer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.google.common.collect.ImmutableMap;

import de.uhd.ifi.se.decision.management.jira.extraction.model.Sentence;
import de.uhd.ifi.se.decision.management.jira.extraction.persistence.ActiveObjectsManager;
import de.uhd.ifi.se.decision.management.jira.model.DecisionKnowledgeElement;
import de.uhd.ifi.se.decision.management.jira.model.DecisionKnowledgeElementImpl;
import de.uhd.ifi.se.decision.management.jira.model.DocumentationLocation;
import de.uhd.ifi.se.decision.management.jira.model.Graph;
import de.uhd.ifi.se.decision.management.jira.model.GraphImpl;
import de.uhd.ifi.se.decision.management.jira.model.KnowledgeType;
import de.uhd.ifi.se.decision.management.jira.model.Link;
import de.uhd.ifi.se.decision.management.jira.persistence.AbstractPersistenceStrategy;
import de.uhd.ifi.se.decision.management.jira.persistence.GenericLinkManager;
import de.uhd.ifi.se.decision.management.jira.persistence.StrategyProvider;

/**
 * Creates tree viewer content
 */
public class TreeViewer {

	@XmlElement
	private boolean multiple;

	@XmlElement(name = "check_callback")
	private boolean checkCallback;

	@XmlElement
	private Map<String, Boolean> themes;

	@XmlElement
	protected Set<Data> data;

	protected Graph graph;
	private List<String> ids;
	private long index;

	/** Effects (if true) that irrelevant sentences are added to Graph. */
	public static boolean isCalledFromIssueTabPanel = false;

	public TreeViewer() {
		this.multiple = false;
		this.checkCallback = true;
		this.themes = ImmutableMap.of("icons", true);

		this.ids = new ArrayList<String>();
		this.index = 1;
	}

	public TreeViewer(String projectKey, KnowledgeType rootElementType) {
		this();
		if (rootElementType != KnowledgeType.OTHER) {
			AbstractPersistenceStrategy strategy = StrategyProvider.getPersistenceStrategy(projectKey);
			List<DecisionKnowledgeElement> elements = strategy.getDecisionKnowledgeElements(rootElementType);

			Set<Data> dataSet = new HashSet<Data>();
			for (DecisionKnowledgeElement element : elements) {
				dataSet.add(this.getDataStructure(element));
			}
			for (DecisionKnowledgeElement sentenceElement : ActiveObjectsManager.getAllElementsFromAoByType(projectKey,
					rootElementType)) {
				dataSet.add(this.makeIdUnique(new Data(sentenceElement)));
			}
			this.data = dataSet;
		} else {

		}
	}

	public TreeViewer(String projectKey) {
		this(projectKey, KnowledgeType.DECISION);
	}

	/**
	 * Constructor for DecXtract TreeViewer in IssueTabPanel.
	 *
	 * @param issueKey
	 *            the issue id
	 * @param isCalledFromTabPanel
	 *            the show relevant (deprecated) currently used to distinguish
	 *            between Constructors
	 */
	public TreeViewer(String issueKey, boolean isCalledFromTabPanel) {
		this();
		TreeViewer.isCalledFromIssueTabPanel = true;
		if (issueKey == null) {
			return;
		}
		Issue issue = ComponentAccessor.getIssueManager().getIssueObject(issueKey);
		if (issue == null) {
			return;
		}
		Data issueNode = this.getDataStructure(new DecisionKnowledgeElementImpl(issue));
		// Match irrelevant sentences back to list
		String identifier = DocumentationLocation.getIdentifier(DocumentationLocation.JIRAISSUE);
		for (Link link : GenericLinkManager.getLinksForElement(identifier + issue.getId())) {
			DecisionKnowledgeElement opposite = link.getOppositeElement(identifier + issue.getId());
			if (opposite instanceof Sentence && isSentenceShown(opposite)) {
				issueNode.getChildren().add(new Data(opposite));
			}
		}

		this.data = new HashSet<Data>(issueNode.getChildren());
	}

	private boolean isSentenceShown(DecisionKnowledgeElement element) {
		return !((Sentence) element).isRelevant() && ((Sentence) element).getBody().length() > 0;
	}

	public Data getDataStructure(DecisionKnowledgeElement decisionKnowledgeElement) {
		if (decisionKnowledgeElement == null) {
			return new Data();
		}
		this.graph = new GraphImpl(decisionKnowledgeElement);
		Data data = new Data(decisionKnowledgeElement);
		data = this.makeIdUnique(data);
		List<Data> children = this.getChildren(decisionKnowledgeElement);
		data.setChildren(children);
		return data;
	}

	protected List<Data> getChildren(DecisionKnowledgeElement decisionKnowledgeElement) {
		List<Data> children = new ArrayList<>();
		Map<DecisionKnowledgeElement, Link> childrenAndLinks = this.graph
				.getLinkedElementsAndLinks(decisionKnowledgeElement);

		for (Map.Entry<DecisionKnowledgeElement, Link> childAndLink : childrenAndLinks.entrySet()) {
			Data dataChild = new Data(childAndLink.getKey(), childAndLink.getValue());
			if (dataChild.getNode().getProject().getProjectKey()
					.equals(decisionKnowledgeElement.getProject().getProjectKey())) {
				dataChild = this.makeIdUnique(dataChild);
				List<Data> childrenOfElement = this.getChildren(childAndLink.getKey());
				dataChild.setChildren(childrenOfElement);
				children.add(dataChild);
			}
		}
		return children;
	}

	protected Data makeIdUnique(Data data) {
		if (!ids.contains(data.getId())) {
			ids.add(data.getId());
		} else {
			data.setId(index + data.getId());
			index++;
		}
		return data;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isCheckCallback() {
		return checkCallback;
	}

	public void setCheckCallback(boolean checkCallback) {
		this.checkCallback = checkCallback;
	}

	public Map<String, Boolean> getThemes() {
		return themes;
	}

	public void setThemes(Map<String, Boolean> themes) {
		this.themes = themes;
	}

	public Set<Data> getData() {
		return data;
	}

	public void setData(Set<Data> data) {
		this.data = data;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
