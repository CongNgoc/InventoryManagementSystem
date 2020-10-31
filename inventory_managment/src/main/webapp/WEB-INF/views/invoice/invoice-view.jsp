<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="//cdnjs.cloudflare.com/ajax/libs/numeral.js/2.0.6/numeral.min.js"></script>
<!-- jQuery -->
<script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
<!-- shieldui -->
<script src="<c:url value="/resources/vendors/shieldui/shieldui-all.min.js"/>"></script>
<!-- jszip -->
<script src="<c:url value="/resources/vendors/jszip/dist/jszip.min.js"/>"></script>

<link rel="stylesheet" type="text/css" src="../static/css/all.min.css" />
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
                            <div class="form-group col-md-3 col-sm-3 col-xs12">
                                <label class="col-md-3 col-sm-3 col-xs-12 text-center" for="invoice.code">Invoice Code</label>
                                <div class="col-md-8 col-sm-8 col-xs-12" >
                                    <form:input path="invoice.code" disabled="true" cssClass="form-control col-md-7 col-xs-12"/>
                                </div>
                            </div>

                            <div class="form-group col-md-3 col-sm-3 col-xs12">
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

                            <div class="form-group col-md-2 col-sm-2 col-xs12">
                                <button id="bnt_print_invoice" type="submit" class="btn btn-success"><i class="fa fa-print" aria-hidden="true"></i> Print Invoice</button>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped jambo_table bulk_action" id="exportTable">
                            <thead>
                            <tr class="headings">
                                <th class="column-title">Ind</th>
                                <th class="column-title">ProductCode</th>
                                <th class="column-title">ProductName</th>
                                <th class="column-title">ProductPrice</th>
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
<%--                                                <jsp:include page="../layout/paging.jsp"></jsp:include>--%>
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



<script type="text/javascript">
    jQuery(function ($) {
        $("#bnt_print_invoice").click(function () {
            console.log("click button!");
            // parse the HTML table element having an id=exportTable
            var dataSource = shield.DataSource.create({
                data: "#exportTable",
                schema: {
                    type: "table",
                    fields: {
                        Ind: {type: Number},
                        ProductCode: { type: String },
                        ProductName: { type: String },
                        ProductPrice: { type: Number },
                        Quantity: { type: Number },
                        Amount: { type: Number }
                }
            }
        });
            console.log("Run at here!")

            // when parsing is done, export the data to PDF
            dataSource.read().then(function (data) {
                var pdf = new shield.exp.PDFDocument({
                    author: "Admin",
                    created: new Date()
                });

                pdf.addPage("a4", "portrait");

                pdf.table(
                    50,
                    50,
                    data,
                    [
                        { field: "Ind", title: " ", width: 25 },
                        { field: "ProductCode", title: "Product Code", width: 80 },
                        { field: "ProductName", title: "Product Name", width: 170 },
                        { field: "ProductPrice", title: "Product Price", width: 80 },
                        { field: "Quantity", title: "Quantity", width: 50 },
                        { field: "Amount", title: "Amount", width: 100 }
                    ],
                    {
                        margins: {
                            top: 50,
                            left: 50
                        }
                    }
                );

                pdf.saveAs({
                    fileName: "invoice"
                });
                // pdf.exportPdf();
            });
        });
    });
</script>