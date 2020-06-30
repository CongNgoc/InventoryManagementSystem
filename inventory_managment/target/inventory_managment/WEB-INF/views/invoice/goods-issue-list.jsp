<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="//cdnjs.cloudflare.com/ajax/libs/numeral.js/2.0.6/numeral.min.js"></script>
<style>
</style>
<div class="right_col" role="main">
    <div class="">

        <div class="clearfix"></div>
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Goods Issue</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="container">
                        <div class="col-md-3 col-sm-3 col-xs-12"><a href="<c:url value="/goods-issue/add"/>" class="btn btn-app"><i class="fa fa-plus"></i>Add</a></div>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                            <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/goods-issue/list/1" method="POST">
                                <div class="col-md-9 col-sm-9 col-xs-6">
                                    <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                        <form:input path="code" placeholder="Enter code or name to search" cssClass="form-control col-md-7 col-xs-12" />
                                    </div>
                                    <div class="col-md-12 col-sm-12 col-xs12">
                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="fromDate">From Date</label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <div class="input-group date" id='fromDatePicker'>
                                                    <form:input path="fromDate" class="form-control" />
                                                    <span class="input-group-addon">
				                               <span class="glyphicon glyphicon-calendar"></span>
				                            </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="toDate">To Date </label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <div class="input-group date" id='toDatePicker'>
                                                    <form:input path="toDate" class="form-control" />
                                                    <span class="input-group-addon">
				                               <span class="glyphicon glyphicon-calendar"></span>
				                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group col-md-3 col-sm-3 col-xs-6">
                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                        <button type="submit" class="btn btn-success">Search</button>
                                    </div>
                                </div>

                            </form:form>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">#</th>
                                <th class="column-title">Code</th>
                                <th class="column-title">Total Payment</th>
                                <th class="column-title">Create Date</th>
                                <th class="column-title">Update Date</th>
                                <th class="column-title no-link last text-center"><span class="nobr">Action</span></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${invoices}" var="invoice" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${invoice.code }</td>
                                <td class="">${invoice.price }</td>
                                <td class="date">${invoice.createDate}</td>
                                <td class="date">${invoice.updateDate}</td>

                                <td class="text-center"><a href="<c:url value="/goods-issue/view/${invoice.invoiceId }"/>" class="btn btn-round btn-default">View</a></td>
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
        $('#searchForm').attr('action','<c:url value="/goods-issue/list/"/>'+page);
        $('#searchForm').submit();
    }
    $(document).ready(function(){
        processMessage();
        $('#fromDatePicker').datetimepicker({
            format : 'YYYY-MM-DD HH:mm:ss'
        });
        $('#toDatePicker').datetimepicker({
            format : 'YYYY-MM-DD HH:mm:ss'
        })
        $('.price').each(function(){
            $(this).text(numeral($(this).text()).format('0,0'));
        })
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