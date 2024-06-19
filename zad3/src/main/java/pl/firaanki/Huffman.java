package pl.firaanki;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Huffman implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Node root;
    private final String text;
    private Map<Character, Integer> charFrequencies;
    private final Map<Character, String> huffmanCodes;
    private String encoded;

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
        encoded = sb.toString();
        return encoded;
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    public String encode() {
        Queue<Node> queue = new PriorityQueue<>();
        charFrequencies.forEach(
                (character, frequency) -> queue.add(new Leaf(character, frequency))
        );
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), Objects.requireNonNull(queue.poll())));
        }
        root = queue.poll();
        generateHuffmanCodes(root, "");

        return getEncodedText();
    }

    public String decode(String encodedText) {
        if (encodedText == null) {
            encodedText = encoded;
        }
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

    public String getDictionary() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
            sb.append("char: ").append(entry.getKey()).append(" | ").append(entry.getValue()).append('\n');
        }

        return sb.toString();
    }
}