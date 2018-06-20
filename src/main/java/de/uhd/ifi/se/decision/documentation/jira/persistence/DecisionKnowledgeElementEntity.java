package de.uhd.ifi.se.decision.documentation.jira.persistence;

import net.java.ao.RawEntity;
import net.java.ao.schema.AutoIncrement;
import net.java.ao.schema.PrimaryKey;
import net.java.ao.schema.Table;

/**
 * @description Interface for decision knowledge elements used in the
 *              active object persistence strategy
 */
@Table("DECISION")
public interface DecisionKnowledgeElementEntity extends RawEntity<Integer> {
	@AutoIncrement
	@PrimaryKey("ID")
	long getId();
	void setId(long id);

	String getSummary();
	void setSummary(String summary);

	String getDescription();
	void setDescription(String description);

	String getType();
	void setType(String type);

	String getProjectKey();
	void setProjectKey(String projectKey);

	String getKey();
	void setKey(String key);
}