<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Successful Operation</title>
    <meta charset="utf-8">
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper" class="toggled">
    <div id="page-content-wrapper">
        <div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    ${msg}
                </h4> <strong>Success</strong> Redirecting in 3 seconds <a href="${url}" class="alert-link">RedirectURL</a>
            </div>
        </div>
    </div>
</div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${url}"', 3000);
</script>