package twitter;

import java.util.List;
import java.util.stream.Collectors;

public class Filter {

    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        return tweets.stream()
                     .filter(tweet -> tweet.getAuthor().equalsIgnoreCase(username))
                     .collect(Collectors.toList());
    }

    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        return tweets.stream()
                     .filter(tweet -> !tweet.getTimestamp().isBefore(timespan.getStart()) &&
                                      !tweet.getTimestamp().isAfter(timespan.getEnd()))
                     .collect(Collectors.toList());
    }

    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        return tweets.stream()
                     .filter(tweet -> words.stream()
                                           .anyMatch(word -> tweet.getText().toLowerCase().contains(word.toLowerCase())))
                     .collect(Collectors.toList());
    }
}
