import huffman.HuffmanEncoder;
import huffman.HuffmanEntity;
import huffman.HuffmanWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by david on 3/1/16.
 */
public class HuffmanTest {
    private HuffmanEncoder encoder;
    private final String filePath = "unittest.txt";
    private final String testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet vehicula lorem. Aenean pretium ipsum neque, et maximus turpis bibendum tincidunt. Fusce eros arcu, dictum sed pellentesque quis, dignissim ac massa. Etiam at sagittis odio. Suspendisse porta eros orci, non blandit nisl facilisis vel. Nullam accumsan iaculis metus, vel aliquam ipsum dictum non. Ut mattis purus ut elit efficitur convallis. Mauris ut vestibulum metus. In hac habitasse platea dictumst. Nullam sit amet eleifend magna. Suspendisse quis magna tortor. Vivamus commodo tempus vestibulum.";


    @Before
    public void setUp() {
        encoder = new HuffmanEncoder();
    }

    @Test
    public void testCompression() {
        HuffmanEntity encode = encoder.encode(testString);

        HuffmanEntity decode = new HuffmanEntity();
        decode.setEncoded(encode.getEncoded());
        decode.setOccurenceMap(encode.getOccurenceMap());

        encoder.decode(decode);

        assertTrue(testString.equals(decode.getDecoded()));
    }

    @Test
    public void testWriter() throws IOException {
        HuffmanEntity encode = encoder.encode(testString);
        HuffmanWriter.write(encode, filePath);

        HuffmanEntity decode = HuffmanWriter.read(filePath);

        encoder.decode(decode);

        assertTrue(testString.equals(decode.getDecoded()));
    }
}
