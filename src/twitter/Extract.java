package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

	public static Timespan getTimespan(List<Tweet> tweets) {
	    if (tweets.isEmpty()) {
	        return new Timespan(null, null); 
	    }

	    Instant start = tweets.get(0).getTimestamp();
	    Instant end = tweets.get(0).getTimestamp();

	    for (Tweet tweet : tweets) {
	        if (tweet.getTimestamp().isBefore(start)) {
	            start = tweet.getTimestamp();
	        }
	        if (tweet.getTimestamp().isAfter(end)) {
	            end = tweet.getTimestamp();
	        }
	    }
	    return new Timespan(start, end);
	}


    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        Pattern mentionPattern = Pattern.compile("(?<!\\w)@(\\w+)(?!\\w)");

        for (Tweet tweet : tweets) {
            Matcher matcher = mentionPattern.matcher(tweet.getText()); // Use getText() here
            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase());
            }
        }
        return mentionedUsers;
    }
}
