<html>
<#include "../../common/head.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--        sidebar-->
    <#include "../../common/nav.ftl">
    <#--        main content-->
    <div id="page-content-wrapper">
        <div class="container">
    <div class="row clearfix">
        <div class="col-md-6 column">
            <table class="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>Order Id</th>
                    <th>Order Amount</th>
                    <th>Buyer Phone Number</th>
                    <th>Delivery Address </th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.orderId}</td>
                    <td>${orderDTO.orderAmount}</td>
                    <td>${orderDTO.buyerPhone}</td>
                    <td>${orderDTO.buyerAddress}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="col-md-9 column">
            <table class="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>Product Id</th>
                    <th>Product Name</th>
                    <th>Product Quantity</th>
                    <th>Product Price</th>
                    <th>Total Price</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.getOrderDetailList() as orderDetail>
                    <tr>
                        <td>${orderDetail.productId}</td>
                        <td>${orderDetail.productName}</td>
                        <td>${orderDetail.productQuantity}</td>
                        <td>${orderDetail.productPrice}</td>
                        <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#if orderDTO.getOrderStatus() == 0>
                <a href="/sell/vendor/order/complete?orderId=${orderDTO.orderId}" type="button" class="btn btn-success btn-default">Order Delivered</a>
                <a href="/sell/vendor/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-danger btn-default">Cancel Order</a>
            </#if>
        </div>
    </div>

</div>
    </div>
</div>
</body>

</html>