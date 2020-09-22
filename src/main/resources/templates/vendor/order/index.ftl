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
                    <form role="form" action="/sell/vendor/product/save" method="post">
                        <div class="form-group">
                            <label for="ProductName">Product Name</label><input type="text" name="productName" class="form-control" value="${(productInfo.productName)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productPrice">Product Price</label><input type="text" name="productPrice" class="form-control" value="${(productInfo.productPrice)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productStock">Product Stock</label><input type="text" name="productStock" class="form-control" value="${(productInfo.productStock)!""}" />
                        </div>
                        <div class="form-group">
                            <label for="productDescription">Product Description</label><input type="text" name="productDescription" class="form-control" value="${(productInfo.productDescription)!""}" />
                        </div>
                        <div class="form-group">
                            <#if (productInfo.productIcon)??>
                                <img src="${productInfo.productIcon}" width="100" height="100"/>
                            </#if>
                        </div>
                        <div class="form-group">
                            <label for="productIcon">Icon URL</label><input type="text" name="productIcon" class="form-control" value="${(productInfo.productIcon)!""}" />
                        </div>
                        <div class="form-group">
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && category.categoryType == productInfo.categoryType>
                                                selected
                                            </#if>
                                            >
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input type="hidden" name="productId" value="${(productInfo.productId)!""}"/>
                         <button type="submit" class="btn btn-default btn-success">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>


</html>