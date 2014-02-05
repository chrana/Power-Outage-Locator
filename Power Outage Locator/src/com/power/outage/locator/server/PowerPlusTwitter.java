package com.power.outage.locator.server;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class PowerPlusTwitter {
	
	public static void updateStatus(String latestStatus) throws TwitterException{
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("WMlsAmzsJILmmjdpUuSPfg")
				.setOAuthConsumerSecret(
						"OsxCvaYnihLMmVoDKyQmt5uWEbapR1pZhcOlqR28iA")
				.setOAuthAccessToken(
						"2320890167-KgcsYn5mFSxGSAeypEcQeLGoG72sVUYyStqUpPW")
				.setOAuthAccessTokenSecret(
						"MFSLKy8fODUmB79noHZy3D1GjJkf9Wvl80iod3ZVUtm58");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		twitter.updateStatus(latestStatus);
	}

}
