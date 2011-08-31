<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko-KR" lang="ko-KR">
  <!--
  Nagle (Naver + Google)

  Version: 0.3.18
  Developer: Vincent (http://blog.superwtk.com)
  -->
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>Nagle Web Search</title>

    <style type="text/css" media="screen">
      @import url('style.css');
    </style>
  </head>
  <body onload="document.getElementById('query').focus()">
    <div id="search">
      <h1>Nagle</h1>
      <div id="search-title">Naver's Engine + Google's Style</div>
      <div id="form">
        <form id="searchform" method="get" action="result.jsp" >
          <fieldset class="search">
            <input id="query" type="text" name="query" class="query" />
            <input id="submit" type="submit" value="Search" class="submit" />
          </fieldset>
        </form>
      </div>
      <div id="search-copyright">
        Provided by <a href="http://openapi.naver.com">Naver Open APIs</a>
      </div>
    </div>
  </body>
</html>
