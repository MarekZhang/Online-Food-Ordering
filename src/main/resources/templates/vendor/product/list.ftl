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
                            <th>Product ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Description</th>
                            <th>Icon</th>
                            <th>Category</th>
                            <th>Operation</th>
                            <th>Change Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productList.getContent() as product>
                            <tr class="success">
                                <td>${product.productId}</td>
                                <td>${product.productName}</td>
                                <td>${product.productPrice}</td>
                                <td>${product.productStock}</td>
                                <td>${product.productDescription}</td>
                                <td><img width="50" height="50" src="${product.productIcon}"/></td>
                                <td>${product.categoryType}</td>
                                <td><a href="/sell/vendor/product/index?productId=${product.productId}">Update</a> </td>
                                <#if product.productStatus==0>
                                    <td><a href="/sell/vendor/product/offShelf?productId=${product.productId}">Off shelf</a> </td>
                                <#else>
                                    <td><a href="/sell/vendor/product/display?productId=${product.productId}">Display</a> </td>
                                </#if>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <ul class="pagination pull-right">
                        <#if page lte 1>
                            <li class="disabled"><a href="#">Prev</a></li>
                        <#else>
                            <li><a href="/sell/vendor/product/list?page=${page-1}&size=${size}">Prev</a></li>
                        </#if>
                        <#list 1..productList.getTotalPages() as idx>
                            <#if page == idx>
                            <#--                            /sell/vendor/order/list?page=${idx}&size=${size}-->
                                <li class="disabled"><a href="/sell/vendor/product/list?page=${idx}&size=${size}">${idx}</a></li>
                            <#else>
                                <li><a href="/sell/vendor/product/list?page=${idx}&size=${size}">${idx}</a></li>
                            </#if>
                        </#list>

                        <#if page gte productList.getTotalPages()>
                            <li class="disabled"><a href="#">Next</a></li>
                        <#else>
                            <li><a href="/sell/vendor/product/list?page=${page+1}&size=${size}">Next</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>