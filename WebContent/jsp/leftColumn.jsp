<%@ page import="java.util.List, java.util.Iterator, booksApp.books.model.Category" %>
<li><div><span class="label" style="margin-left: 15px;">Categories</span></div>
<ul>
<%
  List<Category> categoryList1 = (List<Category>) application.getAttribute("categoryList");
  if (categoryList1 != null) {
      Iterator<Category> iterator1 = categoryList1.iterator();
      while (iterator1.hasNext()) {
          Category category1 = (Category) iterator1.next();
%>
<li>
  <a class="label" href="<%=request.getContextPath()%>/Books?action=category&categoryId=<%=category1.getId()%>&category=<%=category1.getCategoryDescription()%>">
    <span class="label" style="margin-left: 30px;"><%=category1.getCategoryDescription()%></span>
  </a>
</li>
<%      }
  }
%>
</ul></li>