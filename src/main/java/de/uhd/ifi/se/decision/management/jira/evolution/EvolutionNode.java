package de.uhd.ifi.se.decision.management.jira.evolution;

import javax.xml.bind.annotation.XmlElement;

public class EvolutionNode {

	@XmlElement
	private String content;

	@XmlElement
	private String startTime;

	public EvolutionNode(String content, String startTime){
		this.content =content;
		this.startTime = startTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
