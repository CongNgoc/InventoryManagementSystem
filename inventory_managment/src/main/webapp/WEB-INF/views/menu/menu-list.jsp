<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Menu List</h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="container">
<%--                        <div class="col-md-3 col-sm-3 col-xs-12"><a href="<c:url value="/user/add"/>" class="h-43 btn btn-app"><i class="fa fa-plus"></i>Add</a></div>--%>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/menu/list/1" method="POST" accept-charset="UTF-8">
                                <div class="form-group col-md-9 col-sm-9 col-xs-6">
                                    <form:input path="menuName" placeholder="Enter menu name or url to search" cssClass="form-control col-md-7 col-xs-12" />
                                </div>

                                <div class="form-group col-md-3 col-sm-3 col-xs-6 col-md-offset-3">
                                    <button type="submit" class="btn btn-success">Search</button>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">#</th>
                                <th class="column-title">Id</th>
                                <th class="column-title">Menu name</th>
                                <th class="column-title">URL</th>
                                <th class="column-title">Parent Id</th>
                                <th class="column-title">Create date</th>
                                <th class="column-title">Update date</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${menuList}" var="menu" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${menu.menuId}</td>
                                <td class=" ">${menu.menuName}</td>
                                <td class=" ">${menu.url}</td>
                                <td class=" ">${menu.parentId}</td>
                                <td class=" ">${menu.createDate}</td>
                                <td class=" ">${menu.updateDate}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                        <jsp:include page="../layout/paging.jsp"></jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function gotoPage(page){
        $('#searchForm').attr('action','<c:url value="/menu/list/"/>'+page);
        $('#searchForm').submit();
    }
    $(document).ready(function(){
        processMessage();
    });
    function processMessage(){
        var msgSuccess = '${msgSuccess}';
        var msgError = '${msgError}';
        if(msgSuccess){
            new PNotify({
                title: ' Success',
                text: msgSuccess,
                type: 'success',
                styling: 'bootstrap3'
            });
        }
        if(msgError){
            new PNotify({
                title: ' Error',
                text: msgError,
                type: 'error',
                styling: 'bootstrap3'
            });
        }
    }
</script>