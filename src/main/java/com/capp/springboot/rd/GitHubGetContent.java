package com.capp.springboot.rd;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class GitHubGetContent {
	public static String main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("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");
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/repos/mgireesha/contacts-app-sb-gh-tmp-repo/contents/100010.jpg?ref=main", HttpMethod.GET, request, String.class);
		System.out.println("response : "+response);
	return response.toString();
	}
}
