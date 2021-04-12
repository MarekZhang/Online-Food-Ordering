<html>
<#include "../../common/head.ftl">
    <body>
    <div id="wrapper" class="toggled">
<#--        sidebar-->
        <#include "../../common/nav.ftl">
<#--        main content-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-hover table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Buyer Name</th>
                                <th>Phone number</th>
                                <th>Buyer Address</th>
                                <th>Order Amount</th>
                                <th>Order Status</th>
                                <th>Payment Status</th>
                                <th>Created Time</th>
                                <th colspan="2">Operations </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderList.getContent() as orderList>
                                <tr class="success">
                                    <td>${orderList.orderId}</td>
                                    <td>${orderList.buyerName}</td>
                                    <td>${orderList.buyerPhone}</td>
                                    <td>${orderList.buyerAddress}</td>
                                    <td>${orderList.orderAmount}</td>
                                    <td>${orderList.getOrderStatusEnum().name()}</td>
                                    <td>${orderList.getPaymentStatusEnum().name()}</td>
                                    <td>${orderList.createTime}</td>
                                    <td><a href="/sell/vendor/order/detail?orderId=${orderList.orderId}">Detail</a> </td>
                                    <td>
                                        <#if orderList.getOrderStatusEnum().getCode()==0>
                                            <a href="/sell/vendor/order/cancel?orderId=${orderList.orderId}">Cancel</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                        <ul class="pagination pull-right">
                            <#if page lte 1>
                                <li class="disabled"><a href="#">Prev</a></li>
                            <#else>
                                <li><a href="/sell/vendor/order/list?page=${page-1}&size=${size}">Prev</a></li>
                            </#if>
                            <#list 1..orderList.getTotalPages() as idx>
                                <#if page == idx>
                                <#--                            /sell/vendor/order/list?page=${idx}&size=${size}-->
                                    <li class="disabled"><a href="/sell/vendor/order/list?page=${idx}&size=${size}">${idx}</a></li>
                                <#else>
                                    <li><a href="/sell/vendor/order/list?page=${idx}&size=${size}">${idx}</a></li>
                                </#if>
                            </#list>

                            <#if page gte orderList.getTotalPages()>
                                <li class="disabled"><a href="#">Next</a></li>
                            <#else>
                                <li><a href="/sell/vendor/order/list?page=${page+1}&size=${size}">Next</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#--pop up window-->
    <div class="modal fade" id="orderAlert" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        New order alert
                    </h4>
                </div>
                <div class="modal-body">
                    You received a new order
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="javascript:document.getElementById('altertAudio').pause()" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" id="orderDetail" class="btn btn-primary">Order details</button>
                </div>
            </div>
        </div>
    </div>
    <video id="altertAudio" loop muted autoplay>
        <source src="/sell/audio/alert.mp3" type="audio/mpeg">
    </video>
    </body>
</html>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    var webSocket = null;
    if('WebSocket' in window){
        webSocket = new WebSocket('ws://markzhang.natapp1.cc/sell/webSocket');
    }else{
        alert("current browser does not support web socket");
    }

    webSocket.onopen = function(event){
        console.log("connection set up");
    }

    webSocket.onclose = function(event){
        console.log("web socket disconnected");
    };

    webSocket.onerror = function(event){
        alert("web socket connection error");
    };

    webSocket.onmessage = function(event){
        console.log("new message received: " + event.data);
        $('#orderAlert').modal('show');
        document.getElementById('altertAudio').play();
        $('#orderDetail').on("click", function(){
            location.href="/sell/vendor/order/detail?orderId="+event.data;
        });
    };

    window.onbeforeunload = function(event){
        webSocket.close();
    };
</script>