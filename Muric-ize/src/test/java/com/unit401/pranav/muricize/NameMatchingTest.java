package com.unit401.pranav.muricize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.unit401.pranav.muricize.beans.Name;
import com.unit401.pranav.muricize.beans.PossibleNames;
import com.unit401.pranav.muricize.util.Utilities;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/** Locks the 2017 matching heuristic and the bundled Social Security name lists. */
public class NameMatchingTest {

    @Test
    public void normalizeKeepsTheFirstTokenAndTitleCase() {
        assertEquals("Pranav", Name.normalize("  pranav prem "));
        assertEquals("Pranav", Name.normalize("PRANAV"));
        assertEquals("A", Name.normalize("a"));
        assertEquals("", Name.normalize("   "));
        assertEquals("", Name.normalize(null));
    }

    @Test
    public void splitRequestUsesTheLastComma() {
        assertEquals("Pranav", Utilities.splitRequest("Pranav,Male")[0]);
        assertEquals("Male", Utilities.splitRequest("Pranav,Male")[1]);
        assertEquals("Mary,Ann", Utilities.splitRequest("Mary,Ann,Female")[0]);
        assertEquals("Female", Utilities.splitRequest("Mary,Ann,Female")[1]);
        assertEquals("", Utilities.splitRequest(null)[0]);
        assertEquals("", Utilities.splitRequest("Pranav")[1]);
    }

    @Test
    public void vowelsAreLowercaseOnly() {
        assertEquals(Arrays.asList('a', 'a'), Utilities.getVowels("Pranav"));
        assertTrue(Utilities.getVowels("Amy").isEmpty());
        assertEquals(Arrays.asList('a', 'o'), Utilities.getVowels("Jacob"));
        assertTrue(Utilities.getVowels(null).isEmpty());
    }

    @Test
    public void vowelScoreMatchesTheOriginalCounter() {
        assertEquals(5, Utilities.vowelMatcher("Pranav", "Jacob"));
        assertEquals(2, Utilities.vowelMatcher("Pranav", "Michael"));
        assertEquals(0, Utilities.vowelMatcher("Amy", "Ava"));
    }

    @Test
    public void differentInitialsAreExcluded() {
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("Michael");
        candidates.add("Jacob");
        candidates.add("Mason");

        ArrayList<PossibleNames> ranked = new Name("Pranav", "Male").getAmerican(candidates);

        assertEquals(0, ranked.size());
    }

    @Test
    public void pranavRanksSameInitialNamesAndSkipsBlanks() {
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("");
        candidates.add("Preston");
        candidates.add("Patrick");
        candidates.add(null);

        ArrayList<PossibleNames> ranked = new Name("Pranav", "Male").getAmerican(candidates);

        assertEquals(2, ranked.size());
        assertEquals("Patrick", ranked.get(0).getPossibility());
        assertEquals(6, ranked.get(0).getMatch());
        assertEquals("Patrick  (Score= 6)", ranked.get(0).toString());
        assertEquals("Preston", ranked.get(1).getPossibility());
        assertEquals(1, ranked.get(1).getMatch());
    }

    @Test
    public void equalScoresStayInListOrder() {
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("Abigail");
        candidates.add("Aaron");

        ArrayList<PossibleNames> ranked = new Name("Amy", "Female").getAmerican(candidates);

        assertEquals("Abigail", ranked.get(0).getPossibility());
        assertEquals("Aaron", ranked.get(1).getPossibility());
        assertEquals(1, ranked.get(0).getMatch());
        assertEquals(1, ranked.get(1).getMatch());
    }

    @Test
    public void emptyInputDoesNotThrow() {
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add("Jacob");
        assertTrue(new Name("", "Male").getAmerican(candidates).isEmpty());
        assertTrue(new Name("Pranav", "Male").getAmerican(null).isEmpty());
    }

    @Test
    public void bundledListsAreThe2011SocialSecurityTop1000() throws IOException {
        List<String> male = asset("Male");
        List<String> female = asset("Female");

        assertEquals(1000, male.size());
        assertEquals(1000, female.size());
        assertEquals("Jacob", male.get(0));
        assertEquals("Mason", male.get(1));
        assertEquals("William", male.get(2));
        assertEquals("Elvis", male.get(903));
        assertEquals("Ethen", male.get(999));
        assertEquals("Sophia", female.get(0));
        assertEquals("Isabella", female.get(1));
        assertEquals("Damaris", female.get(999));
        assertNoBlankLines(male);
        assertNoBlankLines(female);
    }

    @Test
    public void pranavAgainstTheRealListKeepsTheOriginalTopMatches() throws IOException {
        ArrayList<PossibleNames> ranked =
                new Name("Pranav", "Male").getAmerican(new ArrayList<String>(asset("Male")));

        assertEquals(18, ranked.size());
        assertEquals("Parker", ranked.get(0).getPossibility());
        assertEquals("Patrick", ranked.get(1).getPossibility());
        assertEquals("Paul", ranked.get(2).getPossibility());
        assertEquals(6, ranked.get(0).getMatch());
        assertScoresDoNotIncrease(ranked);
        for (PossibleNames possible : ranked) {
            assertTrue(possible.getPossibility().startsWith("P"));
        }
    }

    @Test
    public void amyMatchesEveryANameAtTheSameScore() throws IOException {
        ArrayList<PossibleNames> ranked =
                new Name("Amy", "Female").getAmerican(new ArrayList<String>(asset("Female")));

        assertEquals("Ava", ranked.get(0).getPossibility());
        assertEquals(1, ranked.get(0).getMatch());
        assertEquals(167, ranked.size());
        for (PossibleNames possible : ranked) {
            assertEquals(1, possible.getMatch());
        }
    }

    private static void assertScoresDoNotIncrease(List<PossibleNames> ranked) {
        for (int i = 1; i < ranked.size(); i++) {
            assertTrue(ranked.get(i - 1).getMatch() >= ranked.get(i).getMatch());
        }
    }

    private static void assertNoBlankLines(List<String> names) {
        for (String name : names) {
            assertTrue(name != null && !name.isEmpty());
        }
    }

    private static List<String> asset(String fileName) throws IOException {
        Path path = Paths.get("src/main/assets", fileName);
        assertTrue("Missing " + path.toAbsolutePath(), Files.exists(path));
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}
