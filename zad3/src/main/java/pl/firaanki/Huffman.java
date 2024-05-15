package pl.firaanki;

import java.io.Serializable;
import java.util.*;

class Huffman implements Serializable {

    private transient Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private final Map<Character, String> huffmanCodes;


    public Huffman(String text) {
        this.text = text;
        fillCharFrequencies();
        huffmanCodes = new HashMap<>();
    }

    private void fillCharFrequencies() {
        charFrequencies = new HashMap<>();
        for (char c : text.toCharArray()) {
            charFrequencies.compute(c, (k, currentCount) -> currentCount != null ? currentCount + 1 : 1);
        }
    }

    private void generateHuffmanCodes(Node node, String code) {
        if (node instanceof Leaf leaf) {
            huffmanCodes.put(leaf.getCharacter(), code);
            return;
        }
        generateHuffmanCodes(node.getLeftNode(), code.concat("0"));
        generateHuffmanCodes(node.getRightNode(), code.concat("1"));
    }

    private String getEncodedText() {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(huffmanCodes.get(c));
        }
        return sb.toString();
    }

    public String encode() {
        Queue<Node> queue = new PriorityQueue<>();
        charFrequencies.forEach(
                (character, frequency) -> queue.add(new Leaf(character, frequency))
        );
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), queue.poll()));
        }
        root = queue.poll();
        generateHuffmanCodes(root, "");

        return getEncodedText();
    }

    public String decode(String encodedText) {
        StringBuilder sb = new StringBuilder();
        Node current = root;
        for (char c : encodedText.toCharArray()) {
            current = (c == '0') ? current.getLeftNode() : current.getRightNode();
            if (current instanceof Leaf leaf) {
                sb.append(leaf.getCharacter());
                current = root;
            }
        }
        return sb.toString();
    }

}