package parser

import spock.lang.Specification
import tkom.module.parser.Parser

import java.nio.charset.StandardCharsets

/**
 * Created by wprzecho on 30.05.16.
 */
class AbstractParserTest extends Specification {
    Parser parser;

    def InputStream getInputStream(final String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }
}