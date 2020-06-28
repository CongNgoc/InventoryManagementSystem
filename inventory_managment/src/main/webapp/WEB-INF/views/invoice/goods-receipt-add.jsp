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
                    <h2>Goods Receipt</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="container">
                        <div>
                            <form:form modelAttribute="searchForm" cssClass="form-horizontal form-label-left" servletRelativeAction="/goods-receipt/add" method="POST">
                                <div class="col-md-9 col-sm-9 col-xs-6">
                                    <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="productInfo.code">Product Code</label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <form:input path="productInfo.code" placeholder="Enter product code" cssClass="form-control col-md-7 col-xs-12" servletRelativeAction="/goods-receipt/add" method="POST" />
                                            </div>
                                        </div>

                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="productId">Product Name</label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <form:select path="productId" cssClass="form-control">
                                                    <form:options items="${mapProduct}" />
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-12 col-sm-12 col-xs-12">
                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="productInfo.price">Product Price</label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <form:input path="productInfo.price"  disabled="true" cssClass="form-control col-md-7 col-xs-12" />
                                            </div>
                                        </div>
                                        <div class="form-group col-md-6 col-sm-6 col-xs12">
                                            <label class="col-md-3 col-sm-3 col-xs-12" for="quanity">Quantity</label>
                                            <div class="col-md-9 col-sm-9 col-xs-12" >
                                                <form:input path="quanity" cssClass="form-control col-md-7 col-xs-12" placeholder="Enter quantity"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group col-md-3 col-sm-3 col-xs-6">
                                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                        <button id="bnt_add_in_de" type="submit" class="btn btn-success">Add to Invoice</button>
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
                                <th class="column-title">Product Code</th>
                                <th class="column-title">Product Name</th>
                                <th class="column-title">Product Price</th>
                                <th class="column-title">Quantity</th>
                                <th class="column-title">Amount</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="entry" items="${mapQuantity}" varStatus="loop">

                                <c:choose>
                                    <c:when test="${loop.index%2==0 }">
                                        <tr class="even pointer">
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="odd pointer">
                                    </c:otherwise>
                                </c:choose>
                                <td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
                                <td class=" ">${entry.value.productInfo.code}</td>
                                <td class="price" style="font-size: 18px">${entry.value.productInfo.name }</td>
                                <td class="price" style="font-size: 18px">${entry.value.productInfo.price }</td>
                                <td class="">${entry.value.quanity}</td>
                                <td class="price" style="font-size: 18px">${entry.value.getAmount()}</td>
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
                                    <c:forEach var="entry" items="${mapQuantity}" varStatus="loop">
                                        <c:set var="total_payment" value="${total_payment + entry.value.getAmount()}"></c:set>
                                    </c:forEach>
                                    ${total_payment}
                                </td>
                            </tr>
                            </tfoot>
                        </table>
<%--                        <jsp:include page="../layout/paging.jsp"></jsp:include>--%>
                    </div>

                    <div class="add_invoice">
                        <form:form cssClass="form-horizontal form-label-left" servletRelativeAction="/goods-receipt/save" method="POST">
                            <div class="form-group col-md-3 col-sm-3 col-xs-6">
                                <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                    <button id="bnt_add_invoice" type="submit" class="btn btn-success">Add to Invoice</button>
                                </div>
                            </div>
                        </form:form>
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
            window.location.href = '<c:url value="/goods-receipt/delete/"/>'+id;
        }
    }
    function gotoPage(page){
        $('#searchForm').attr('action','<c:url value="/goods-receipt/list/"/>'+page);
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