import huffman.HuffmanEncoder

/**
 * Created by david on 3/1/16.
 */
import huffman.*;

fun main(args: Array<String>) {
    val filePath : String = "test.txt"
    val encoder: HuffmanEncoder = HuffmanEncoder();

    if(args[0].equals("-o")){
        val entity = HuffmanWriter.read(filePath);
        encoder.decode(entity);

        println(entity.decoded);

    }
    else{
        val entity = encoder.encode(args.joinToString(" "));

        println(entity.encoded);

        HuffmanWriter.write(entity, filePath);
    }
}