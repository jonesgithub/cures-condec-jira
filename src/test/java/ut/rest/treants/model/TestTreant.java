package ut.rest.treants.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.DecisionDocumentation.rest.treants.model.Chart;
import com.atlassian.DecisionDocumentation.rest.treants.model.Node;
import com.atlassian.DecisionDocumentation.rest.treants.model.Treant;

public class TestTreant {
	
	private Chart chart;
	private Node nodeStructure;
	private Treant treant;
	
	@Before 
	public void setUp() {
		this.chart = new Chart();
		this.nodeStructure = new Node();
		this.treant = new Treant();
		this.treant.setChart(chart);
		this.treant.setNodeStructure(nodeStructure);
	}

	@Test
	public void testGetChart() {
		assertEquals(this.chart, this.treant.getChart());
	}
	
	@Test
	public void testGetNodeStructure() {
		assertEquals(this.nodeStructure, this.treant.getNodeStructure());
	}
	
	@Test
	public void testSetChart() {
		Chart newChart = new Chart();
		this.treant.setChart(newChart);
		assertEquals(newChart, this.treant.getChart());		
	}
	
	@Test
	public void testSetNodeStructure() {
		Node newNode = new Node();
		this.treant.setNodeStructure(newNode);
		assertEquals(newNode, this.treant.getNodeStructure());
	}
}