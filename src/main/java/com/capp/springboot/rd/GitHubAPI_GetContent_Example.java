package com.capp.springboot.rd;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.client.RestTemplate;

public class GitHubAPI_GetContent_Example {

	public static void main(String[] args) throws IOException, URISyntaxException {

		/*
		 * Call GitHub REST API - https://developer.github.com/v3/repos/contents/
		 * 
		 * Using Spring's RestTemplate to simplify REST call. Any other REST client
		 * library can be used here.
		 */
		RestTemplate restTemplate = new RestTemplate();
		List<Map> response = restTemplate.getForObject(
				"https://api.github.com/repos/mgireesha/contacts-app-sb-gh-tmp-repo/contents?ref=main&access_token=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", List.class, "mgireesha",
				"test", "main");

		System.out.println(response);

		// Iterate through list of file metadata from response.
		for (Map fileMetaData : response) {

			// Get file name & raw file download URL from response.
			String fileName = (String) fileMetaData.get("name");
			String downloadUrl = (String) fileMetaData.get("download_url");
			System.out.println("File Name = " + fileName + " | Download URL = " + downloadUrl);

			// We will only fetch read me file for this example.
			if (downloadUrl != null && downloadUrl.contains("README")) {

				/*
				 * Get file content as string
				 * 
				 * Using Apache commons IO to read content from the remote URL. Any other HTTP
				 * client library can be used here.
				 */
				String fileContent = IOUtils.toString(new URI(downloadUrl), "utf-8");
				System.out.println("\nfileContent = <FILE CONTENT START>\n" + fileContent + "\n<FILE CONTENT END>\n");

				/*
				 * Download read me file to local.
				 * 
				 * Using Apache commons IO to create file from remote content. Any other library
				 * or code can be written to get content from URL & create file in local.
				 */
				File file = new File("github-api-downloaded-" + fileName);
				FileUtils.copyURLToFile(new URL(downloadUrl), file);
			}
		}
	}
}
