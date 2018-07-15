package de.uhd.ifi.se.decision.management.jira.rest.decisionsrest;

import com.google.common.collect.ImmutableMap;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@RunWith(ActiveObjectsJUnitRunner.class)
public class TestPostUpdateDecisions extends TestDecisionSetUp {
    private final static String UPDATE_ERROR = "Update of decision knowledge element failed.";
    @Test
    public void testActionTypeNullReqNullDecNull() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement(null,null).getEntity());
    }

    @Test
    public void testActionTypeNullReqNullDecFilled() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement(null, dec).getEntity());
    }

    @Test
    public void testActionTypeNullReqFilledDecNull() {
        request.setAttribute("WithFails", false);
        request.setAttribute("NoFails", true);
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement( request, null).getEntity());
    }

    @Test
    public void testActionTypecreateReqNullDecNull() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement(null, null).getEntity());
    }

    @Test
    public void testActionTypecreateReqNullDecFilled() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement( null, dec).getEntity());
    }

    @Test
    public void testActionTypecreateReqFilledDecNull() {
        request.setAttribute("WithFails", false);
        request.setAttribute("NoFails", true);
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement( request, null).getEntity());
    }

    @Test
    public void testActionTypecreateReqFilledDecFilled() {
        request.setAttribute("WithFails", false);
        request.setAttribute("NoFails", true);
        assertEquals(Response.Status.OK.getStatusCode(), decisionsRest.updateDecisionKnowledgeElement( request, dec).getStatus());
    }



    @Test
    public void testActionTypeEditReqNullDecNull() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement(null, null).getEntity());
    }

    @Test
    public void testActionTypeEditReqNullDecFilled() {
        assertEquals(Response.status(Response.Status.BAD_REQUEST)
                .entity(ImmutableMap.of("error", UPDATE_ERROR)).build()
                .getEntity(), decisionsRest.updateDecisionKnowledgeElement(null, dec).getEntity());
    }
}
