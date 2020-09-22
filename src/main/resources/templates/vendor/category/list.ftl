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
                <div class="col-md-6 column">
                    <table class="table table-hover table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Category Name</th>
                            <th>Category Type</th>
                            <th>Operation </th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list categoryList as category>
                            <tr class="success">
                                <td>${category.categoryName}</td>
                                <td>${category.categoryType}</td>
                                <td><a href="/sell/vendor/category/index?categoryId=${category.categoryId}">Update</a></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>