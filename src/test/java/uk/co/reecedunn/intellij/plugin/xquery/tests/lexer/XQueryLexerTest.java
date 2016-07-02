/*
 * Copyright (C) 2016 Reece H. Dunn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.reecedunn.intellij.plugin.xquery.tests.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import junit.framework.TestCase;
import uk.co.reecedunn.intellij.plugin.xquery.lexer.XQueryTokenType;
import uk.co.reecedunn.intellij.plugin.xquery.lexer.XQueryLexer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class XQueryLexerTest extends TestCase {
    private void matchToken(Lexer lexer, String text, int state, int start, int end, IElementType type) {
        assertThat(lexer.getTokenText(), is(text));
        assertThat(lexer.getState(), is(state));
        assertThat(lexer.getTokenStart(), is(start));
        assertThat(lexer.getTokenEnd(), is(end));
        assertThat(lexer.getTokenType(), is(type));

        if (lexer.getTokenType() == null) {
            assertThat(lexer.getBufferEnd(), is(start));
            assertThat(lexer.getBufferEnd(), is(end));
        }

        lexer.advance();
    }

    public void testEmptyBuffer() {
        Lexer lexer = new XQueryLexer();

        lexer.start("");
        matchToken(lexer, "", 0, 0, 0, null);
    }

    public void testS() {
        Lexer lexer = new XQueryLexer();

        lexer.start(" ");
        matchToken(lexer, " ", 0, 0, 1, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "",  0, 1, 1, null);

        lexer.start("\t");
        matchToken(lexer, "\t", 0, 0, 1, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "",   0, 1, 1, null);

        lexer.start("\r");
        matchToken(lexer, "\r", 0, 0, 1, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "",   0, 1, 1, null);

        lexer.start("\n");
        matchToken(lexer, "\n", 0, 0, 1, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "",   0, 1, 1, null);

        lexer.start("   \t  \r\n ");
        matchToken(lexer, "   \t  \r\n ", 0, 0, 9, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "",             0, 9, 9, null);
    }

    public void testIntegerLiteral() {
        Lexer lexer = new XQueryLexer();

        lexer.start("1234");
        matchToken(lexer, "1234", 0, 0, 4, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "",     0, 4, 4, null);
    }

    public void testDecimalLiteral() {
        Lexer lexer = new XQueryLexer();

        lexer.start("47.");
        matchToken(lexer, "47.", 0, 0, 3, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "",    0, 3, 3, null);

        lexer.start("1.234");
        matchToken(lexer, "1.234", 0, 0, 5, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "",      0, 5, 5, null);

        lexer.start(".25");
        matchToken(lexer, ".25", 0, 0, 3, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "",    0, 3, 3, null);

        lexer.start(".1.2");
        matchToken(lexer, ".1", 0, 0, 2, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, ".2", 0, 2, 4, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "",   0, 4, 4, null);
    }

    public void testDoubleLiteral() {
        Lexer lexer = new XQueryLexer();

        lexer.start("3e7 3e+7 3e-7");
        matchToken(lexer, "3e7",  0,  0,  3, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",    0,  3,  4, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "3e+7", 0,  4,  8, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",    0,  8,  9, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "3e-7", 0,  9, 13, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",     0, 13, 13, null);

        lexer.start("43E22 43E+22 43E-22");
        matchToken(lexer, "43E22",  0,  0,  5, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",      0,  5,  6, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "43E+22", 0,  6, 12, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",      0, 12, 13, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "43E-22", 0, 13, 19, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",       0, 19, 19, null);

        lexer.start("2.1e3 2.1e+3 2.1e-3");
        matchToken(lexer, "2.1e3",  0,  0,  5, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",      0,  5,  6, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "2.1e+3", 0,  6, 12, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",      0, 12, 13, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "2.1e-3", 0, 13, 19, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",       0, 19, 19, null);

        lexer.start("1.7E99 1.7E+99 1.7E-99");
        matchToken(lexer, "1.7E99",  0,  0,  6, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",       0,  6,  7, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1.7E+99", 0,  7, 14, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",       0, 14, 15, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1.7E-99", 0, 15, 22, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",        0, 22, 22, null);

        lexer.start(".22e42 .22e+42 .22e-42");
        matchToken(lexer, ".22e42",  0,  0,  6, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",       0,  6,  7, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".22e+42", 0,  7, 14, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",       0, 14, 15, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".22e-42", 0, 15, 22, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",        0, 22, 22, null);

        lexer.start(".8E2 .8E+2 .8E-2");
        matchToken(lexer, ".8E2",  0,  0,  4, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",     0,  4,  5, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".8E+2", 0,  5, 10, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, " ",     0, 10, 11, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".8E-2", 0, 11, 16, XQueryTokenType.DOUBLE_LITERAL);
        matchToken(lexer, "",      0, 16, 16, null);

        lexer.start("1e 1e+ 1e-");
        matchToken(lexer, "1", 0,  0,  1, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "e", 0,  1,  2, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ", 0,  2,  3, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1", 0,  3,  4, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "e", 0,  4,  5, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+", 0,  5,  6, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ", 0,  6,  7, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1", 0,  7,  8, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "e", 0,  8,  9, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-", 0,  9, 10, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",  0, 10, 10, null);

        lexer.start("1E 1E+ 1E-");
        matchToken(lexer, "1", 0,  0,  1, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "E", 0,  1,  2, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ", 0,  2,  3, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1", 0,  3,  4, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "E", 0,  4,  5, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+", 0,  5,  6, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ", 0,  6,  7, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "1", 0,  7,  8, XQueryTokenType.INTEGER_LITERAL);
        matchToken(lexer, "E", 0,  8,  9, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-", 0,  9, 10, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",  0, 10, 10, null);

        lexer.start("8.9e 8.9e+ 8.9e-");
        matchToken(lexer, "8.9", 0,  0,  3, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",   0,  3,  4, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",   0,  4,  5, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "8.9", 0,  5,  8, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",   0,  8,  9, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+",   0,  9, 10, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",   0, 10, 11, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "8.9", 0, 11, 14, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",   0, 14, 15, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-",   0, 15, 16, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",    0, 16, 16, null);

        lexer.start("8.9E 8.9E+ 8.9E-");
        matchToken(lexer, "8.9", 0,  0,  3, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",   0,  3,  4, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",   0,  4,  5, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "8.9", 0,  5,  8, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",   0,  8,  9, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+",   0,  9, 10, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",   0, 10, 11, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, "8.9", 0, 11, 14, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",   0, 14, 15, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-",   0, 15, 16, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",    0, 16, 16, null);

        lexer.start(".4e .4e+ .4e-");
        matchToken(lexer, ".4", 0,  0,  2, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",  0,  2,  3, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",  0,  3,  4, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".4", 0,  4,  6, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",  0,  6,  7, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+",  0,  7,  8, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",  0,  8,  9, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".4", 0,  9, 11, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "e",  0, 11, 12, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-",  0, 12, 13, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",   0, 13, 13, null);

        lexer.start(".4E .4E+ .4E-");
        matchToken(lexer, ".4", 0,  0,  2, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",  0,  2,  3, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",  0,  3,  4, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".4", 0,  4,  6, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",  0,  6,  7, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "+",  0,  7,  8, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, " ",  0,  8,  9, XQueryTokenType.WHITE_SPACE);
        matchToken(lexer, ".4", 0,  9, 11, XQueryTokenType.DECIMAL_LITERAL);
        matchToken(lexer, "E",  0, 11, 12, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "-",  0, 12, 13, XQueryTokenType.BAD_CHARACTER);
        matchToken(lexer, "",   0, 13, 13, null);
    }

    public void testStringLiteral() {
        Lexer lexer = new XQueryLexer();

        lexer.start("\"");
        matchToken(lexer, "\"", 0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "",   1, 1, 1, null);

        lexer.start("\"Hello World\"");
        matchToken(lexer, "\"",          0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "Hello World", 1,  1, 12, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"",          1, 12, 13, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",            0, 13, 13, null);

        lexer.start("'");
        matchToken(lexer, "'", 0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "",  2, 1, 1, null);

        lexer.start("'Hello World'");
        matchToken(lexer, "'",           0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "Hello World", 2,  1, 12, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "'",           2, 12, 13, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",            0, 13, 13, null);
    }

    public void testStringLiteral_EscapeQuot() {
        Lexer lexer = new XQueryLexer();

        lexer.start("\"One\"\"Two\"");
        matchToken(lexer, "\"",   0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "One",  1,  1,  4, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"\"", 1,  4,  6, XQueryTokenType.STRING_LITERAL_ESCAPED_CHARACTER);
        matchToken(lexer, "Two",  1,  6,  9, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"",   1,  9, 10, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",     0, 10, 10, null);
    }

    public void testStringLiteral_EscapeApos() {
        Lexer lexer = new XQueryLexer();

        lexer.start("'One''Two'");
        matchToken(lexer, "'",    0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "One",  2,  1,  4, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "''",   2,  4,  6, XQueryTokenType.STRING_LITERAL_ESCAPED_CHARACTER);
        matchToken(lexer, "Two",  2,  6,  9, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "'",    2,  9, 10, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",     0, 10, 10, null);
    }

    public void testStringLiteral_PredefinedEntityRef() {
        Lexer lexer = new XQueryLexer();

        // NOTE: The predefined entity reference names are not validated by the lexer, as some
        // XQuery processors support HTML predefined entities. Shifting the name validation to
        // the parser allows proper validation errors to be generated.

        lexer.start("\"One&abc;&aBc;&Abc;&ABC;&a4;&a;Two\"");
        matchToken(lexer, "\"",    0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "One",   1,  1,  4, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "&abc;", 1,  4,  9, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&aBc;", 1,  9, 14, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&Abc;", 1, 14, 19, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&ABC;", 1, 19, 24, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&a4;",  1, 24, 28, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&a;",   1, 28, 31, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "Two",   1, 31, 34, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"",    1, 34, 35, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",      0, 35, 35, null);

        lexer.start("'One&abc;&aBc;&Abc;&ABC;&a4;&a;Two'");
        matchToken(lexer, "'",     0,  0,  1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "One",   2,  1,  4, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "&abc;", 2,  4,  9, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&aBc;", 2,  9, 14, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&Abc;", 2, 14, 19, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&ABC;", 2, 19, 24, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&a4;",  2, 24, 28, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "&a;",   2, 28, 31, XQueryTokenType.PREDEFINED_ENTITY_REFERENCE);
        matchToken(lexer, "Two",   2, 31, 34, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "'",     2, 34, 35, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",      0, 35, 35, null);

        lexer.start("\"&\"");
        matchToken(lexer, "\"", 0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "&",  1, 1, 2, XQueryTokenType.PARTIAL_ENTITY_REFERENCE);
        matchToken(lexer, "\"", 1, 2, 3, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",   0, 3, 3, null);

        lexer.start("\"&abc!\"");
        matchToken(lexer, "\"",   0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "&abc", 1, 1, 5, XQueryTokenType.PARTIAL_ENTITY_REFERENCE);
        matchToken(lexer, "!",    1, 5, 6, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"",   1, 6, 7, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",     0, 7, 7, null);

        lexer.start("\"& \"");
        matchToken(lexer, "\"", 0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "&",  1, 1, 2, XQueryTokenType.PARTIAL_ENTITY_REFERENCE);
        matchToken(lexer, " ",  1, 2, 3, XQueryTokenType.STRING_LITERAL_CONTENTS);
        matchToken(lexer, "\"", 1, 3, 4, XQueryTokenType.STRING_LITERAL_END);
        matchToken(lexer, "",   0, 4, 4, null);

        lexer.start("\"&");
        matchToken(lexer, "\"", 0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "&",  1, 1, 2, XQueryTokenType.PARTIAL_ENTITY_REFERENCE);
        matchToken(lexer, "",   1, 2, 2, null);

        lexer.start("\"&abc");
        matchToken(lexer, "\"",   0, 0, 1, XQueryTokenType.STRING_LITERAL_START);
        matchToken(lexer, "&abc", 1, 1, 5, XQueryTokenType.PARTIAL_ENTITY_REFERENCE);
        matchToken(lexer, "",     1, 5, 5, null);
    }
}
