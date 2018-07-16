package de.uhd.ifi.se.decision.management.jira.persistence.issuestrategy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.MockApplicationUser;

import de.uhd.ifi.se.decision.management.jira.model.DecisionKnowledgeElementImpl;
import de.uhd.ifi.se.decision.management.jira.model.KnowledgeType;

public class TestEditDecisionComponent extends TestIssueStrategySetUp {

	@Test
	(expected = NullPointerException.class)
	public void testDecisionRepresentationNullApplicUserNull() {
		issueStrategy.updateDecisionKnowledgeElement(null, null);
	}

	@Test
	(expected = NullPointerException.class)
	public void testDecisionRepresentationFilledApplicUserNull() {
		DecisionKnowledgeElementImpl dec = new DecisionKnowledgeElementImpl();
		issueStrategy.updateDecisionKnowledgeElement(dec, null);
	}

	@Test
	public void testDecisionRepresentationFilledApplicUserFilledRight() {
		DecisionKnowledgeElementImpl dec = new DecisionKnowledgeElementImpl();
		dec.setId(1);
		dec.setProject("TEST");
		dec.setType(KnowledgeType.SOLUTION);
		ApplicationUser user = new MockApplicationUser("NoFails");
		assertNotNull(issueStrategy.updateDecisionKnowledgeElement(dec, user));
	}

	@Test
	public void testDecisionRepresentationFilledApplicUserFilledWrong() {
		DecisionKnowledgeElementImpl dec = new DecisionKnowledgeElementImpl();
		dec.setId(1);
		dec.setProject("TEST");
		dec.setType(KnowledgeType.SOLUTION);
		ApplicationUser user = new MockApplicationUser("WithFails");
		assertFalse(issueStrategy.updateDecisionKnowledgeElement(dec, user));
	}
}
