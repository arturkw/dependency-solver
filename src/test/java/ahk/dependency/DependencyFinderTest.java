package ahk.dependency;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DependencyFinderTest {

    @Test
    public void testSimpleNode() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("A", "B");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(1, routes.size());
        assertEquals("AB", routes.get(0));
    }

    @Test
    public void testThreeSeparatedNodes() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("A", "B");
        dependencyFinder.registerConnection("C", "D");
        dependencyFinder.registerConnection("E", "F");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(3, routes.size());
        assertTrue(routes.contains("AB"));
        assertTrue(routes.contains("CD"));
        assertTrue(routes.contains("EF"));
    }

    @Test
    public void testThreeConnectedNodes() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("A", "B");
        dependencyFinder.registerConnection("B", "C");
        dependencyFinder.registerConnection("C", "D");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(1, routes.size());
        assertTrue(routes.contains("ABCD"));
    }

    @Test
    public void testThreeUnorderedConnectedNodes() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("A", "B");
        dependencyFinder.registerConnection("C", "D");
        dependencyFinder.registerConnection("B", "C");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(1, routes.size());
        assertTrue(routes.contains("ABCD"));
    }

    @Test
    public void testThreeUnorderedConnectedNodes2() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("B", "C");
        dependencyFinder.registerConnection("C", "D");
        dependencyFinder.registerConnection("A", "B");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(1, routes.size());
        assertTrue(routes.contains("ABCD"));
    }

    @Test
    public void testThreeUnorderedConnectedNodes3() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        //first route:
        dependencyFinder.registerConnection("B", "C");
        dependencyFinder.registerConnection("C", "D");
        dependencyFinder.registerConnection("A", "B");

        //second route:
        dependencyFinder.registerConnection("I", "J");
        dependencyFinder.registerConnection("H", "I");
        dependencyFinder.registerConnection("G", "H");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(2, routes.size());
        assertTrue(routes.contains("ABCD"));
        assertTrue(routes.contains("GHIJ"));
    }

    @Test
    public void testThreeUnorderedConnectedNodes4() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        //first route:
        dependencyFinder.registerConnection("B", "C");
        dependencyFinder.registerConnection("C", "D");
        dependencyFinder.registerConnection("A", "B");

        //second route:
        dependencyFinder.registerConnection("I", "J");
        dependencyFinder.registerConnection("H", "I");
        dependencyFinder.registerConnection("G", "H");

        //connector:
        dependencyFinder.registerConnection("D", "G");

        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(1, routes.size());
        assertTrue(routes.contains("ABCDGHIJ"));
    }

    @Test
    public void testSimpleTree() {
        DependencyFinder dependencyFinder = new DependencyFinder();
        dependencyFinder.registerConnection("A", "B");
        dependencyFinder.registerConnection("B", "C");
        dependencyFinder.registerConnection("B", "D");
        List<String> routes = dependencyFinder.findAllRoutes();
        assertEquals(2, routes.size());
        assertTrue(routes.contains("ABC"));
        assertTrue(routes.contains("ABD"));
    }

}
