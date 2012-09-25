CQ5 and CXF

This is an example OSGi Bundle for Adobe CQ5. It calls the webservicex.net Stock Quote service using CXF.

Built using Maven 3 on JDK1.6.
Developed against CQ5.4, works on CQ5.4 and CQ5.5.
Heavily based on the article by Sean Steimer 
http://shsteimer.com/blog/2012/03/integrating-a-soap-web-service-toolkit-with-day-cq/
which in turn was based on the session by Pro!vision at .adaptTo() Berlin 2011. 
http://www.pro-vision.de/cms/adaptto/rubrik/5/5481.adapttoberlin.htm

Unlike the version they produced, this bundle doesn't depend on the dOSGi or other CXF bundles, nor does it need hacking the sling.bootdelegation to work. All requirements are either present in a stock copy of CQ5 or are private internal to the bundle.


Example Usage:
1.Install bundle

2. Put this code in a component

<%
    cxf_example.api.CxfExampleService svc = sling.getService(cxf_example.api.CxfExampleService.class);
    %>
     svc = <%=svc.getSomething()%>

3. put the component on a page and load it. You should see a line with a stock quote for AAPL.
