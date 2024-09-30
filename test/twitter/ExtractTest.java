package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "user3", "Hello @User2!", Instant.now());
    private static final Tweet tweet4 = new Tweet(4, "user4", "Check out @user3 and @User2!", Instant.now());
    private static final Tweet tweet5 = new Tweet(5, "user5", "No mentions here.", Instant.now());

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start and end for single tweet", d1, timespan.getStart());
        assertEquals("expected start and end for single tweet", d1, timespan.getEnd());
    }

    @Test
    public void testGetTimespanEmptyList() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertNull("expected start to be null", timespan.getStart());
        assertNull("expected end to be null", timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        Set<String> expectedUsers = new HashSet<>(Arrays.asList("user2", "user3"));
        assertEquals("expected mentioned users", expectedUsers, mentionedUsers);
    }

    @Test
    public void testGetMentionedUsersMixedCase() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        assertTrue("expected user2 in mentioned users", mentionedUsers.contains("user2"));
        assertTrue("expected user3 in mentioned users", mentionedUsers.contains("user3"));
        assertFalse("should not contain user4", mentionedUsers.contains("user4"));
    }
    
    

    
}
