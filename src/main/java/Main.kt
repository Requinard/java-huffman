import huffman.HuffmanEncoder

/**
 * Created by david on 3/1/16.
 */
import huffman.*;
import org.apache.commons.io.FileUtils
import java.io.File

fun main(args: Array<String>) {
    val filePath: String = "test.txt"
    val encoder: HuffmanEncoder = HuffmanEncoder();

    if (args[0].equals("-o")) {
        // Output the text
        val entity = HuffmanWriter.read(filePath);
        encoder.decode(entity);

        println(entity.decoded);
    } else {
        val entity = encoder.encode(args.joinToString(" "));

        println(entity.encoded);

        HuffmanWriter.write(entity, filePath);
    }
}