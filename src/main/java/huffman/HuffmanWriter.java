package huffman;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by david on 3/1/16.
 */
public class HuffmanWriter {
    public static void write(HuffmanEntity entity, String filePath) throws IOException {
        Preconditions.checkArgument(entity.getEncoded() != null, "No encoded string is yet present, Have you compressed this object?");
        Preconditions.checkArgument(entity.getOccurenceMap() != null, "No occurence map has been found!");

        StringBuilder builder = new StringBuilder();

        // First we write the dictionary
        builder.append(
                entity.getOccurenceMap()
                        .keySet()
                        .stream()
                        .map((key) -> String.format("%s:%d,", key, entity.getOccurenceMap().get(key)))
                        .reduce((x, y) -> x + y)
                        .get()
        );

        // Then we write three newlines to indicate the end of the dict and the start of the string
        builder.append("\n\n\n");

        // then we write our string
        builder.append(entity.getEncoded());

        // Now we write to a file
        FileWriter writer = new FileWriter(filePath);

        writer.write(builder.toString());

        writer.close();
    }

    public static HuffmanEntity read(String filePath) throws IOException {
        // Prepare variables
        HuffmanEntity entity = new HuffmanEntity();
        Map<Character, Integer> map = new HashMap<>();

        String readFile = FileUtils.readFileToString(new File(filePath));

        // Split the string with the predefined values, 0 is dict, 1 is string
        String[] substrings = readFile.split("\n\n\n");

        // get the subvals
        for (String s : substrings[0].split(",")) {

            String[] keyvals = s.split(":");

            if (keyvals.length == 2) {
                if (keyvals[0].isEmpty()) {
                    map.put(',', Integer.parseInt(keyvals[1]));
                } else
                    map.put(keyvals[0].charAt(0), Integer.parseInt(keyvals[1]));
            }
        }

        entity.setOccurenceMap(map);
        entity.setEncoded(substrings[1]);

        return entity;
    }
}
