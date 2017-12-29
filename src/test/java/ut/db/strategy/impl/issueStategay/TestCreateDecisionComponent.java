package ut.db.strategy.impl.issueStategay;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.atlassian.DecisionDocumentation.rest.Decisions.model.DecisionRepresentation;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.MockApplicationUser;

public class TestCreateDecisionComponent extends TestIssueStartegySup {
	
	@Test
	(expected = NullPointerException.class)
	public void testCreateDecisionComponentRepresNullUserNull() {
		issueStrat.createDecisionComponent(null, null);
	}
	
	@Test
	(expected = NullPointerException.class)
	public void testCreateDecisionComponentRepresFilledUserNull() {
		DecisionRepresentation dec = new DecisionRepresentation();
		issueStrat.createDecisionComponent(dec, null);
	}
	
	@Test
	public void testCreateDecisionComponentRepresFilledUserFilledNoFails() {
		DecisionRepresentation dec = new DecisionRepresentation();
		dec.setProjectKey("TEST");
		dec.setType("Solution");
		ApplicationUser user = new MockApplicationUser("NoFails");
		assertNotNull(issueStrat.createDecisionComponent(dec, user));
		
	}
	@Test
	public void testCreateDecisionComponentRepresFilledUserFilledWithFails() {
		DecisionRepresentation dec = new DecisionRepresentation();
		dec.setProjectKey("TEST");
		dec.setType("Solution");
		ApplicationUser user = new MockApplicationUser("WithFails");
		assertNull(issueStrat.createDecisionComponent(dec, user));
		
	}
}