/*
 * NaverParser.java
 *
 * Created on June 13, 2006, 10:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.naver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author superwtk
 */
public class WebSearch {
	
	private URL remoteUrl;
	private String remoteUrlString;
	
	public class Channel {
		private String title;
		private String link;
		private String description;
		private String lastBuildDate;
		private long total;
		private long start;
		private int display;
		
		public Channel(String title, String link, String description, String lastBuildDate,
				long total, long start, int display) {
			this.title = title;
			this.link = link;
			this.description = description;
			this.lastBuildDate = lastBuildDate;
			this.total = total;
			this.start = start;
			this.display = display;
		}

		public String getTitle() {
			return title;
		}

		public String getLink() {
			return link;
		}

		public String getDescription() {
			return description;
		}

		public String getLastBuildDate() {
			return lastBuildDate;
		}

		public long getTotal() {
			return total;
		}

		public long getStart() {
			return start;
		}

		public int getDisplay() {
			return display;
		}
	}
	
	public static Channel channel;
	
	public class Record {
		private String title;
		private String link;
		private String description;
		
		public Record(String title, String link, String description) {
			this.title = title;
			this.link = link;
			this.description = description;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getLink() {
			return link;
		}
		
		public String getDescription() {
			return description;
		}
	}
	
	private Record[] records;
	private int recordCount;
	
	public WebSearch() {
	}
	
	public WebSearch(String key, String target, String query, int display, int start) {
		try {
			query = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		
		try {
			remoteUrlString = String
					.format(
					"http://openapi.naver.com/search?key=%s&query=%s&display=%d&start=%d&target=%s",
					key, query, display, start, target);
			remoteUrl = new URL(remoteUrlString);
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		records = new Record[display];
	}
	
	public void parse() throws SAXParseException, SAXException, IOException {
		InputStream is = remoteUrl.openStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SAXException(e.getMessage());
		}
		Document doc = docBuilder.parse(bis);
		
		//
		// Parse chennel information
		//
		NodeList channelList = doc.getElementsByTagName("channel").item(0).getChildNodes();
		Node nodeChannelTitle = channelList.item(0);
		Node nodeChannelLink = channelList.item(1);
		Node nodeChannelDescription = channelList.item(2);
		Node nodeChannelLastBuildDate = channelList.item(3);
		Node nodeChannelTotal = channelList.item(4);
		Node nodeChannelStart = channelList.item(5);
		Node nodeChannelDisplay = channelList.item(6);
		
		String channelTitle = nodeChannelTitle.getTextContent();
		String channelLink = nodeChannelLink.getTextContent();
		String channelDescription = nodeChannelDescription.getTextContent();
		String channelLastBuildDate = nodeChannelLastBuildDate.getTextContent();
		long channelTotal = Long.valueOf(nodeChannelTotal.getTextContent());
		long channelStart = Long.valueOf(nodeChannelStart.getTextContent());
		int channelDisplay = Integer.valueOf(nodeChannelDisplay.getTextContent());
		
		channel = new Channel(channelTitle, channelLink, channelDescription,
				channelLastBuildDate, channelTotal, channelStart, channelDisplay);
		
		//
		// Parse items
		//
		NodeList itemList = doc.getElementsByTagName("item");
		recordCount = itemList.getLength();
		
		for (int i = 0; i < recordCount; i++) {
			Node item = itemList.item(i);
			NodeList childrenOfItem = item.getChildNodes();
			
			Node nodeItemTitle = childrenOfItem.item(0);
			Node nodeItemLink = childrenOfItem.item(1);
			Node nodeItemDescription = childrenOfItem.item(2);
			
			String itemTitle = nodeItemTitle.getTextContent();
			String itemLink = nodeItemLink.getTextContent();
			String itemDescription = nodeItemDescription.getTextContent();
			
			records[i] = new Record(itemTitle, itemLink, itemDescription);
		}
	}
	
	public Record[] getRecords() {
		return records;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
}
