<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.naver.WebSearch"%>
<%@page import="com.naver.PageSplitter"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko-KR" lang="ko-KR">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Nagle Web Search</title>
		
    <style type="text/css" media="screen">
      @import url('style.css');
    </style>
  </head>
  <body>
    <%
      request.setCharacterEncoding("UTF-8");
      String query = request.getParameter("query");

      String target = request.getParameter("target");
      if(target == null || target.equals(""))
        target = "webkr";
      else if(!(target.equals("webkr") || target.equals("blog") || target.equals("kin") || target.equals("doc"))) {
        target = "webkr";
      }

      // Generate links
      String equery = java.net.URLEncoder.encode(query, "UTF-8");

      String targetWeb = "<a href=\"?target=webkr&amp;query=" + equery + "\">웹</a>";
      String targetBlog = "<a href=\"?target=blog&amp;query=" + equery + "\">블로그</a>";
      String targetKin = "<a href=\"?target=kin&amp;query=" + equery + "\">지식iN</a>";
      String targetDoc = "<a href=\"?target=doc&amp;query=" + equery + "\">전문자료</a>";

      if(target.equals("webkr"))
        targetWeb = "<strong>웹</strong>";
      else if(target.equals("blog"))
        targetBlog = "<strong>블로그</strong>";
      else if(target.equals("kin"))
        targetKin = "<strong>지식iN</strong>";
      else if(target.equals("doc"))
        targetDoc = "<strong>전문자료</strong>";

      int _page = 0;
      String spage = request.getParameter("page");
      if(spage == null)
        _page = 1;
      else
        _page = Integer.parseInt(spage);

      WebSearch parser = null;
      WebSearch.Record[] list = null;

      try {
        parser = new WebSearch(
            "c2c4edb8c7e2db5192141d5743a45fb8", target, query, 10, (_page-1)*10 + 1);

        parser.parse();
        list = parser.getRecords();
      } catch(Exception e) {
        e.printStackTrace();
      }

      int count = parser.getRecordCount();
    %>
    <div id="wrapper">
      <div id="search-top">
        <h1><a href="./">Nagle Search</a></h1>
        <form method="get" action="result.jsp">
          <fieldset class="search">
            <input type="hidden" name="target" value="<%=target%>" />
            <input type="text" name="query" class="query" value="<%=query%>" />
            <input type="submit" class="submit" value="Search" />
          </fieldset>
        </form>
      </div>
      <div id="toolbar-top">
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <%=targetWeb%> |
              <%=targetBlog%> |
              <%=targetKin%> |
              <%=targetDoc%>
            </td>
            <td align="right">
              <%
                if(count > 0) {
              %>
              Results <strong><%=parser.channel.getStart()%></strong> - <strong><%=(parser.channel.getStart() + parser.channel.getDisplay() - 1)%></strong>
              of about <strong><%=parser.channel.getTotal()%></strong> for <strong><%=query%></strong>
              <%
                }
              %>
            </td>
          </tr>
        </table>
      </div>
      <div id="result">
        <%
          if(count == 0) {
        %>
        <div class="item">
          No result for a keyword <b><%=query%></b>
        </div>
        <%
          } else {

        for (int i = 0; i < count; i++) {
          String title = list[i].getTitle();
          String link = list[i].getLink();
          String description = list[i].getDescription();
        %>
        <div class="item">
          <div class="link"><a href="<%=link%>" class="link"><%=title%></a></div>
          <div class="description"><%=description%></div>
          <div class="uri"><%=link%></div>
        </div>
        <%
          } // end of for loop
            } // end of if statement
        %>
        
        <div class="page-splitter">
          <%
            PageSplitter ps = new PageSplitter((int)parser.channel.getTotal(), 10, 9, _page);
            ps.previousPageLink1 = "&nbsp;<a href=\"?query=" + equery + "&amp;target=" + target + "&amp;page=" + (_page-1) + "\">&lt;</a>&nbsp;\n";
            ps.previousSegmentLink1 = "";
            ps.nextPageLink1 = "&nbsp;<a href=\"?query=" + equery + "&amp;target=" + target + "&amp;page=" + (_page+1) + "\">&gt;</a>&nbsp;\n";
            ps.nextSegmentLink1 = "";
            ps.pageLink = "&nbsp;<a href=\"?query=" + equery + "&amp;target=" + target + "&amp;page=$page\">$page</a>&nbsp;\n";
            ps.currentPageLink = "&nbsp;<strong>$page</strong>&nbsp;\n";
            
            out.println(ps);
          %>
        </div>
      </div>

      <div id="adsense">
        <script type="text/javascript"><!--
        google_ad_client = "pub-0602604487957721";
        google_ad_width = 120;
        google_ad_height = 600;
        google_ad_format = "120x600_as";
        google_ad_type = "text_image";
        google_ad_channel ="2594758769";
        google_color_border = "FFFFFF";
        google_color_link = "0000FF";
        google_color_bg = "FFFFFF";
        google_color_text = "000000";
        google_color_url = "008000";
        //--></script>
        <script type="text/javascript"
          src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
        </script>
      </div>
    
      <div id="search-bottom">
        <form method="get" action="result.jsp">
          <fieldset class="search">
            <input type="hidden" name="target" value="<%=target%>" />
            <input type="text" name="query" class="query" value="<%=query%>" />
            <input type="submit" class="submit" value="Search" />
          </fieldset>
        </form>
        
        <div id="search-copyright" style="margin-top: 10px;">
          Provided by <a href="http://openapi.naver.com">Naver Open APIs</a>
        </div>
      </div>
    </div>
  </body>
</html>
