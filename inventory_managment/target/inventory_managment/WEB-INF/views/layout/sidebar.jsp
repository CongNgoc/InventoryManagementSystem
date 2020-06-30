<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
        <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                        <c:forEach items="${menuSession}" var="menu">
                                <li id="${menu.menuIdForHTML}"><a> ${menu.menuName}<span
                                class="fa fa-chevron-down"></span>
                                </a>
                                <ul class="nav child_menu">
                                <c:forEach items="${menu.childList}" var="child">
                                        <li id="${child.menuIdForHTML}"><a href="<c:url value="${child.url}"/>">${child.menuName}</a></li>
                                </c:forEach>
                                </ul>
                                </li>
                        </c:forEach>
                </ul>
        </div>
</div>