package ahk.dependency;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyFinder {
    private Set<Node> nodes = new HashSet<Node>();

    public void registerConnection(final String from, String to) {
        Node fromNode = findNode(nodes, from);
        Node toNode = findNode(nodes, to);
        if (fromNode == null && toNode == null) {
            Node parentNode = Node.createLinkedNodes(from, to);
            nodes.add(parentNode);
        } else if (fromNode == null) {
            nodes.add(Node.createNewParentNode(toNode, from));
            nodes.remove(toNode);
        } else if (toNode == null) {
            fromNode.registerConnectsToNode(to);
        } else {
            Node.linkNodes(fromNode, toNode);
        }
    }

    private Node findNode(Set<Node> nodes, String nodeName) {
        for (Node node : nodes) {
            if (node.getNodeName().equals(nodeName)) {
                return node;
            }
            Node connectToNode = findNode(node.getConnectsTo(), nodeName);
            if (connectToNode != null) {
                return connectToNode;
            }
            Node achievableFromNode = findNode(node.getConnectsTo(), nodeName);
            if (achievableFromNode != null) {
                return achievableFromNode;
            }
        }
        return null;
    }

    public List<String> findAllRoutes() {
        Set<Node> edgeNodes = nodes.stream().filter(n -> n.getAchievableFrom().isEmpty()).collect(Collectors.toSet());
        List<String> routes = new LinkedList<>();
        for (Node node : edgeNodes) {
            routes.addAll(findRoutes(node, ""));
        }
        return routes;
    }

    private List<String> findRoutes(Node node, String parentName) {
        List<String> routes = new LinkedList<>();
        if (node.getConnectsTo().isEmpty()) {
            routes.add(parentName + node.getNodeName());
        } else {
            for (Node childNode : node.getConnectsTo()) {
                routes.addAll(findRoutes(childNode, parentName + node.getNodeName()));
            }
        }
        return routes;
    }
}
