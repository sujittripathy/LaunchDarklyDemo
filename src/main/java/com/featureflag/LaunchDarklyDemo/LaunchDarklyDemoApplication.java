package com.featureflag.LaunchDarklyDemo;

import com.google.gson.JsonElement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.launchdarkly.client.LDClient;
import com.launchdarkly.client.LDUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@SpringBootApplication
@RestController
public class LaunchDarklyDemoApplication implements CommandLineRunner{
	private static LDClient ldClient = new LDClient("sdk-a4f9b1d9-0203-4f93-b137-ef6ea10c0dc0");


	public static void main(String[] args) {
		SpringApplication.run(LaunchDarklyDemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		LDUser ldUser = new LDUser.Builder("sujit.tripathy@icloud.com")
				.firstName("Sujit")
				.lastName("Tripathy")
				.custom("groups", "beta_testers")
				.build();
		final Map<String, JsonElement> stringJsonElementMap = ldClient.allFlags(ldUser);
		System.out.println(stringJsonElementMap);
	}

	@GetMapping("/trending-news")
	public String trendingFeature() {
		String retString;
		LDUser ldUser = new LDUser.Builder("123ldd7")
				.firstName("Sujit")
				.lastName("Tripathy")
				.custom("groups", "beta_testers")
				.build();
		boolean showFeature = ldClient.boolVariation("trending-news",ldUser,false);
		if(showFeature) {
			retString = "Yay!! Trending Fetaure is enabled now";
			System.out.println(retString);
		} else {
			retString = "Don't worry. Trending-feature will enable in future";
			System.out.println(retString);
		}
		return retString;
	}
}
