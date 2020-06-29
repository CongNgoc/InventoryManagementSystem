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
                    <h2>${titlePage}</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="container">
                        <div class="form-group col-md-12 col-sm-12 col-xs-12">
                            <div class="form-group col-md-4 col-sm-4 col-xs12">
                                <label class="col-md-3 col-sm-3 col-xs-12 text-center" for="invoice.code">Invoice Code</label>
                                <div class="col-md-8 col-sm-8 col-xs-12" >
                                    <form:input path="invoice.code" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>

                            <div class="form-group col-md-4 col-sm-4 col-xs12">
                                <label class="col-md-3 col-sm-3 col-xs-12" for="invoice.price">Total Payment</label>
                                <div class="col-md-8 col-sm-8 col-xs-12" >
                                    <form:input path="invoice.price" disabled="true" cssClass="form-control col-md-7 col-xs-12" />
                                </div>
                            </div>

                            <div class="form-group col-md-4 col-sm-4 col-xs12">
                                <label class="col-md-3 col-sm-3 col-xs-12 text-center" for="invoice.createDate">Create date</label>
                                <div class="col-md-8 col-sm-8 col-xs-12" >
                                    <form:input path="invoice.createDate" disabled="true" cssClass="form-control col-md-7 col-xs-12" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">#</th>
                                <th class="column-title">Product Code</th>
                                <th class="column-title">Product Name</th>
                                <th class="column-title">Product Price</th>
                                <th class="column-title">Quantity</th>
                                <th class="column-title">Amount</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${invoiceDetails}" var="invoiceDetail" varStatus="loop">
                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${invoiceDetail.productInfo.code}</td>
                                <td class="price" style="font-size: 18px">${invoiceDetail.productInfo.name }</td>
                                <td class="price" style="font-size: 18px">${invoiceDetail.productInfo.price }</td>
                                <td class="">${invoiceDetail.quanity}</td>
                                <td class="price" style="font-size: 18px">${invoiceDetail.getAmount()}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td class="bg-warning" style="font-size: 15px;color:Red;font-weight:bold;">Total Payment</td>
                                <td class=" bg-warning price" style="font-size: 18px">
                                    <c:set var="total_payment" value="${0}"></c:set>
                                    <c:forEach items="${invoiceDetails}" var="invoiceDetail" varStatus="loop">
                                        <c:set var="total_payment" value="${total_payment + invoiceDetail.getAmount()}"></c:set>
                                    </c:forEach>
                                    ${total_payment}
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                        <%--                        <jsp:include page="../layout/paging.jsp"></jsp:include>--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#bnt_add_in_de').click(function () {
        var product_id = $("#productId").val();
        var quantity = $("quanity").val();
        console.log(product_id + " " + quantity);
    })
    function confirmDelete(id){
        if(confirm('Do you want delete this record?')){
            window.location.href = '<c:url value="/goods-issue/delete/"/>'+id;
        }
    }
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