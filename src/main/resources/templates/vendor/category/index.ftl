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
                <div class="col-md-4 column">
                    <form role="form" action="/sell/vendor/category/save" method="post">
                        <div class="form-group">
                            <label for="CategoryName">Category Name</label>
                            <input type="text" name="categoryName" class="form-control" value="${(productCategory.categoryName)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="CategoryType">Category Type</label>
                            <input type="text" name="categoryType" class="form-control" value="${(productCategory.categoryType)!""}" />
                        </div>
                        <input type="hidden" name="categoryId" value="${(productCategory.categoryId)!""}"/>
                        <input type="hidden" name="prevCategoryType" value="${(prevCategoryType)!""}">
                        <button type="submit" class="btn btn-default btn-success">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>