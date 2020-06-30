<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Product In Stock List</h2>

                    <div class="clearfix"></div>
                </div>


                <div class="x_content">
                    <div class="container" style="padding: 10px 40px">
                        <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/product-in-stock/list/1" method="POST">
                            <div class="form-group col-md-9 col-sm-9 col-xs-12">
                                <form:input path="productInfo.code" placeholder="Enter code or name to search" cssClass="form-control col-md-7 col-xs-12" />
                            </div>

                            <div class="form-group col-md-3 col-sm-3 col-xs-12 col-md-offset-3">
                                <button type="submit" class="btn btn-success">Search</button>
                            </div>

                        </form:form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">#</th>
                                <th class="column-title">Category</th>
                                <th class="column-title">Code</th>
                                <th class="column-title">Name</th>
<%--                                <th class="column-title">Image</th>--%>
                                <th class="column-title">Qty</th>
                                <th class="column-title">Price</th>
                                <th class="column-title">Create date</th>
                                <th class="column-title">Update date</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${products}" var="product" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
<%--                                product is product in stock--%>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${product.productInfo.category.name }</td>
                                <td class=" ">${product.productInfo.code }</td>
                                <td class=" ">${product.productInfo.name }</td>
<%--                                <td class=" "><img src="<c:url value="${product.productInfo.imgUrl}"/>" width="100px" height="100px"/></td>--%>
                                <td class="">${product.quanity }</td>
                                <td class="">${product.productInfo.price }</td>
                                <td class=" ">${product.createDate }</td>
                                <td class=" ">${product.updateDate }</td>
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
        $('#searchForm').attr('action','<c:url value="/product-in-stock/list/"/>'+page);
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