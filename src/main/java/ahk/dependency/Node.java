package ahk.dependency;

import java.util.HashSet;
import java.util.Set;

public class Node {

    private String nodeName;
    private Set<Node> connectsTo = new HashSet<>();
    private Set<Node> achievableFrom = new HashSet<>();

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }

    public static void linkNodes(Node fromNode, Node toNode) {
        fromNode.connectsTo.add(toNode);
        toNode.achievableFrom.add(fromNode);
    }

    public static Node createNewParentNode(Node toNode, String from) {
        Node oldParent = new Node(toNode.nodeName);
        oldParent.connectsTo = toNode.connectsTo;

        Node newParent = new Node(from);
        newParent.connectsTo.add(oldParent);
        oldParent.achievableFrom.add(newParent);
        return newParent;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Set<Node> getConnectsTo() {
        return connectsTo;
    }

    public Set<Node> getAchievableFrom() {
        return achievableFrom;
    }

    public void registerConnectsToNode(String connectsTo) {
        Node connectsToNode = new Node(connectsTo);
        connectsToNode.achievableFrom.add(this);
        this.connectsTo.add(connectsToNode);
    }

    public static Node createLinkedNodes(String from, String to) {
        Node parentNode = new Node(from);
        Node childNode = new Node(to);
        parentNode.getConnectsTo().add(childNode);
        childNode.getAchievableFrom().add(parentNode);
        return parentNode;
    }

}
