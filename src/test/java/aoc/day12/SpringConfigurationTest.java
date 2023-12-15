package aoc.day12;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpringConfigurationTest {
    private final SpringConfiguration config = new SpringConfiguration();

    @Test
    void testEmptySizesList() {
        assertTrue(config.canMatch(" ", List.of()));
        assertFalse(config.canMatch(" # ", List.of()));
    }

    @Test
    void testSingleSizeMatching() {
        assertTrue(config.canMatch("#", List.of(1)));
        assertFalse(config.canMatch("", List.of(1)));
        assertFalse(config.canMatch("##", List.of(1)));
    }

    @Test
    void testMultipleSizesMatching() {
        assertTrue(config.canMatch("# ##", List.of(1, 2)));
        assertTrue(config.canMatch("#", List.of(1, 2))); // can be expanded
    }

    @Test
    void testMultipleSizesNonMatching() {
        assertFalse(config.canMatch("# ##", List.of(2, 1)));
        assertFalse(config.canMatch("###", List.of(1, 2)));
    }

    @Test
    void testLeadingWhitespaces() {
        assertTrue(config.canMatch("   #", List.of(1)));
        assertTrue(config.canMatch("   # ##", List.of(1, 2)));
        assertFalse(config.canMatch("   # ###", List.of(1, 2)));
    }

    @Test
    void testEdgeCases() {
        assertTrue(config.canMatch("", List.of()));
        assertTrue(config.canMatch("###########", List.of(15)));
    }

    @Test
    void testDoesMatch() {
        assertTrue(config.doesMatch(" ### ##    #", List.of(3,2,1)));
    }
}
