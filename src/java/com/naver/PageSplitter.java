/*
 * PageSplitter.java
 *
 * Created on July 4, 2006, 12:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.naver;

/**
 *
 * @author superwtk
 */
public class PageSplitter {
	
	private int totalPosts;
	
	private int postsPerPage;
	
	private int pagesPerSegment;
	
	private int currentPage;
	
	private int totalPages;
	
	public String nextPageLink0; // in case of unavailable
	public String nextPageLink1; // in case of available
	public String nextSegmentLink0;
	public String nextSegmentLink1;
	public String previousPageLink0;
	public String previousPageLink1;
	public String previousSegmentLink0;
	public String previousSegmentLink1;
	public String pageLink;
	public String currentPageLink;
	
	public PageSplitter(int totalPosts, int postsPerPage, int pagesPerSegment,
			int currentPage) {
		this.totalPosts = totalPosts;
		this.postsPerPage = postsPerPage;
		this.pagesPerSegment = pagesPerSegment;
		this.currentPage = currentPage;
		
		this.nextPageLink0 = "";
		this.nextPageLink1 = " > ";
		this.nextSegmentLink0 = "";
		this.nextSegmentLink1 = " >> ";
		this.previousPageLink0 = "";
		this.previousPageLink1 = " < ";
		this.previousSegmentLink0 = "";
		this.previousSegmentLink1 = " << ";
		this.pageLink = " $page ";
	}
	
	public void setTotalPosts(int totalPosts) {
		this.totalPosts = totalPosts;
	}
	
	public int getTotalPosts() {
		return totalPosts;
	}
	
	public void setPostsPerPage(int postsPerPage) {
		this.postsPerPage = postsPerPage;
	}
	
	public int getPostsPerPage() {
		return postsPerPage;
	}
	
	public void setPagesPerSegment(int pagesPerSegment) {
		this.pagesPerSegment = pagesPerSegment;
	}
	
	public int getPagesPerSegment() {
		return pagesPerSegment;
	}
	
	public int getTotalPages() {
		if (totalPages == 0) {
			totalPages = totalPosts / postsPerPage;
			
			if (totalPosts % postsPerPage > 0)
				totalPages++;
		}
		return totalPages;
		
	}
	
	public int getStartPage() {
		int startPage = currentPage - (pagesPerSegment / 2);
		
		if (startPage < 1)
			startPage = 1;
		
		return startPage;
	}
	
	public int getEndPage() {
		int endPage = currentPage - (pagesPerSegment / 2) + pagesPerSegment - 1;
		int totalPages = getTotalPages();
		
		if (endPage > totalPages)
			endPage = totalPages;
		
		return endPage;
	}
	
	public String getNextPageLink(String arg0, String arg1) {
		if (currentPage < getTotalPages())
			return arg0;
		else
			return arg1;
	}
	
	public String getNextSegmentLink(String arg0, String arg1) {
		if((getTotalPages() - currentPage) > (pagesPerSegment / 2))
			return arg0;
		else
			return arg1;
	}
	
	public String getPreviousPageLink(String arg0, String arg1) {
		if (currentPage > 1)
			return arg0;
		else
			return arg1;
	}
	
	public String getPreviousSegmentLink(String arg0, String arg1) {
		if((currentPage - 1) > (pagesPerSegment / 2))
			return arg0;
		else
			return arg1;
	}
	
	public String getPageLink(String arg0, int page) {
		return arg0.replaceAll("[$]page", String.valueOf(page));
	}
	
	public String getCurrentPageLink(String arg0, int page) {
		return arg0.replaceAll("[$]page", String.valueOf(page));
	}
	
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append(getPreviousSegmentLink(previousSegmentLink1, previousSegmentLink0));
		buf.append(getPreviousPageLink(previousPageLink1, previousPageLink0));
		
		for (int i = getStartPage(); i <= getEndPage(); i++) {
			if(i == currentPage)
				buf.append(getCurrentPageLink(currentPageLink, i));
			else
				buf.append(getPageLink(pageLink, i));
		}
		
		buf.append(getNextPageLink(nextPageLink1, nextPageLink0));
		buf.append(getNextSegmentLink(nextSegmentLink1, nextSegmentLink0));
		
		return new String(buf);
	}
	
}
