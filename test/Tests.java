/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests as set out in the survey.
 *
 * @author josephyearsley
 */
public class Tests {

    //String array to split inputs in test methods
    private static String[] graphInputs = null;
    //an instantation of graph for use in tests
    private static HuddleGraph graph = new HuddleGraph();

    //No constructor
    public Tests() {
    }

    /**
     * Sets up the environment for tests, makes the graph from the input text.
     */
    @BeforeClass
    public static void setUpClass() {
        // one-time initialization code   
        System.out.println("@BeforeClass - oneTimeSetUp");
        String input = "AB5, BC4, CD7, DC8, DE6, AD5, CE2, EB3, AE7";
        graphInputs = input.split("\\s*,\\s*");
        for (int i = 0; i < graphInputs.length; i++) {
            Character x = graphInputs[i].charAt(0);
            Character y = graphInputs[i].charAt(1);
            Integer z = Character.getNumericValue(graphInputs[i].charAt(2));
            graph.add(x, y, z);
        }
        System.out.println(graph.toString());
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * set iteration to 0.
     */
    @Before
    public void setUp() {
        graph.iteration = 0;
    }

    /**
     * Test that the route ABC's distance is 9
     */
    @Test
    public void testABC() {
        assertTrue(graph.distance("ABC").equals("9"));
        System.out.println("@Test - 1");
    }

    /**
     * Test that the route AD's distance is 5
     */
    @Test
    public void testAD() {
        assertTrue(graph.distance("AD").equals("5"));
        System.out.println("@Test - 2");
    }

    /**
     * Test that the route ADC's distance is 13
     */
    @Test
    public void testADC() {
        assertTrue(graph.distance("ADC").equals("13"));
        System.out.println("@Test - 3");
    }

    /**
     * Test that the route AEBCD's distance is 21
     */
    @Test
    public void testAEBCD() {
        assertTrue(graph.distance("AEBCD").equals("21"));
        System.out.println("@Test - 4");
    }

    /**
     * Test that the route AED's distance is 5
     */
    @Test
    public void testAED() {
        assertTrue(graph.distance("AED").equals("NO SUCH ROUTE"));
        System.out.println("@Test - 5");
    }

    /**
     * Test that the Max number of routes from C to C with number of junctions
     * at a max of 3 to be 2.
     */
    @Test
    public void testMaxRoutes1() {
        assertTrue(graph.MaxNoR('C', 'C', 3, 'm') == 2);
        System.out.println("@Test - 6");
    }

    /**
     * Test that the Max number of routes from A to C with number of junctions
     * equal to 4 to be 3.
     */
    @Test
    public void testMaxRoutes2() {
        assertTrue(graph.MaxNoR('A', 'C', 4, 'e') == 3);
        System.out.println("@Test - 7");
    }

    /**
     * Test that the shortest route from A to C is of distance 9.
     */
    @Test
    public void testShortestDistance() {
        assertTrue(graph.dijkstraDistance('A', 'C') == 9);
        System.out.println("@Test - 8");
    }

    /**
     * Test that the shortest route from B to B is of distance 9.
     */
    @Test
    public void testShortestDistance2() {
        assertTrue(graph.dijkstraDistance('B', 'B') == 9);
        System.out.println("@Test - 9");
    }

    /**
     * Test that the number of routes from C to C with a max distance of 30 is
     * 9.
     */
    @Test
    public void testRoutesUnder30() {
        graph.iteration = 30;
        assertTrue(graph.NoSR('C', 'C', 0) == 9);
        System.out.println("@Test - 10");
    }

    /**
     * Reset everything to 0.
     */
    @After
    public void tearDown() {
        graph.total = 0;
        graph.iteration = 0;
    }
}
