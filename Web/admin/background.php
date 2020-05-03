<?php 
  session_id($_GET['id']); 
  session_start();
  ?>
<!DOCTYPE html>
   <html lang="zh-cn">
   <title>stare you</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/style.css">
<script src="../js/jquery-3.1.1.min.js" charset="utf-8"></script>
<style>
html,body{
  height: 100%;
  overflow: hidden;
}
* { 
  margin:0px; 
  padding:0px;
}
html>body{    /*-- for !IE6.0 --*/
  width: auto;
  height: auto;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
}
body {
  border:8px solid #ffffff;
  background-color: #ffffff;min-width:230px;
}
#mainDiv {
  width: 100%;
  height: 100%;
    padding:60px 0px 25px 0px;;
}
#centerDiv{
  width: 100%;
  height: 100%;
  background-color:#00CCFF;
  padding-left:158px;
}
#mainDiv>#centerDiv{    /*-- for !IE6.0 --*/  
  width: auto;
  height: auto;
  position: absolute;
  top: 56px;
  left: 0px;
  right: 0px;
  bottom: 20px;
  
}
#left{
width:158px;
height:100%;
background:url(../image/slide.jpg) repeat-y;
position:absolute;
left:0px;
overflow-y:auto;
}
#lhead{
background:url(../image/left-head.jpg) left top no-repeat;
height:25px;
font-size:14px;
color:#FF9933;
text-align:center;
line-height:25px;
}
#right{
width:100%;
height:100%;
background:#ffffff;
position:absolute;
border:1px #003366 solid;
padding:20px 0 0 10px;
font-size:12px;
font-family:"宋体";
}
#centerDiv>#right{
width:auto;
height:auto;
position:absolute;
top:0px;
right:0px;
left:158px;
bottom:0px;
}
#topDiv{
width:100%;
height:56px;
background:url(../image/head-bg.jpg) repeat-x;
position:absolute;
top:0px;
overflow:hidden;
}
#current{
background:#ccc;
height:25px;
line-height:25px;
margin:-20px 0 0 -10;
overflow:hidden;
}
#bottomDiv{
width:100%;
height:20px;
background:url(../image/bottom.jpg) repeat-x;
position:absolute;
bottom:0px;
bottom:-1px;     /*-- for IE6.0 --*/
}
#left ul{
list-style:none;
font-size:16px;
margin:50px 0 0 8px;
}
#left ul li a{
display:block;
width:140px;
height:30px;
line-height:30px;
background:url(../image/menu-bg.jpg) repeat-x;
color:#FFFFFF;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
border:1px #06597D solid;
text-decoration:none;
}
#left ul li a:hover{
background:url(../image/menu-bg.jpg) 2px 30px;
color:#99FFCC;
direction:none;
text-align:center;
border-bottom:1px #000066 solid;
}
#form{
width:80%;
height:99%;
margin:0 auto;
_margin-left:20%;
background-color: #F0F0F0;
} 
</style>
</head>
<body>
<?php if ($_SESSION['username']=="zbcooper"||$_SESSION['username']=="mirages") {
  $id = $_GET['id'];
  ?>
<div id="mainDiv">
    <div id="topDiv">
	<div id="background_name" style="text-align: center;font-family: '隶书'; font-size:32px;padding-top: 12px;">
     stare&nbsp;you&nbsp;网&nbsp;站&nbsp;后&nbsp;台&nbsp;管&nbsp;理&nbsp;系&nbsp;统
	</div>
	</div>
	<div id="centerDiv">
	<div id="left">
	<div id="lhead" style="font-size: 18px;">管理菜单</div>
	<ul>
	<li ><a href="javascript:void(0);" id="test0"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">用户管理</a></li>
	<div id="action0" style="width:140px; height:60px; background:#CCFF66; display:none">
   <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_user.php" target="view">1用户列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_user.php" target="view">2查询用户</a>
   </div>
	</div>
	<li ><a href="javascript:void(0);" id="test1"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">文章管理</a></li>
	<div id="action1" style="width:140px; height:60px; background:#CCFF66; display:none">
    <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_text.php" target="view">1文章列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_text.php" target="view">2查询文章</a>
   </div>
	</div>
	<li ><a href="#" id="test2"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">图片管理</a></li>
	<div id="action2" style="width:140px; height:60px; background:#CCFF66; display:none">
    <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_picture.php" target="view">1图片列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_picture.php" target="view">2查询图片</a>
   </div>
	</div>
	<li ><a href="#" id="test3"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">视频管理</a></li>
	<div id="action3" style="width:140px; height:60px; background:#CCFF66; display:none">
  <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_video.php" target="view">1视频列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_video.php" target="view">2查询视频</a>
   </div>
	</div>
	<li ><a href="#" id="test4"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">消息管理</a></li>
	<div id="action4" style="width:140px; height:60px; background:#CCFF66; display:none">
      <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_new.php" target="view">1消息列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_new.php" target="view">2查询消息</a>
   </div>
	</div>
    <li ><a href="#" id="test5"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">点赞管理</a></li>
    <div id="action5" style="width:140px; height:60px; background:#CCFF66; display:none">
     <div style="text-align:center;font-size: 18px;padding-top: 5px;">
    <a href="view_praise.php" target="view">1点赞列表</a>
    </div>
    <div style="text-align:center;font-size: 18px;">
    <a href="view_search_praise.php" target="view">2查询点赞</a>
    </div>
    </div>
	<li ><a href="#" id="test6"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">收藏管理</a></li>
	<div id="action6" style="width:140px; height:60px; background:#CCFF66; display:none">
   <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_collection.php" target="view">1收藏列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_collection.php" target="view">2查询收藏</a>
   </div>
	</div>
	<li ><a href="#" id="test7"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">关注管理</a></li>
	<div id="action7" style="width:140px; height:60px; background:#CCFF66; display:none">
  <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_concern.php" target="view">1关注列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_concern.php" target="view">2查询关注</a>
   </div>
	</div>
	<li ><a href="#" id="test8"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">评论管理</a></li>
	<div id="action8" style="width:140px; height:60px; background:#CCFF66; display:none">
     <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_review.php" target="view">1评论列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_review.php" target="view">2查询评论</a>
   </div>
	</div>
	<li ><a href="#" id="test9"><img src="../image/stretch.png" alt="展开" width="25" height="25" style="position:relative;right: 5px;top: 2px;">反馈建议</a></li>
	<div id="action9" style="width:140px; height:60px; background:#CCFF66; display:none">
     <div style="text-align:center;font-size: 18px;padding-top: 5px;">
   <a href="view_suggestion.php" target="view">1反馈列表</a>
   </div>
   <div style="text-align:center;font-size: 18px;">
   <a href="view_search_suggestion.php" target="view">2查询反馈</a>
   </div>
	</div>
	</ul>
	</div>
	<div id="right"> 
	<div id="current">&nbsp;&nbsp;&nbsp;&nbsp;当前位置:
  <script type="text/javascript" language="javascript"> 
        function confirmAct() 
        { 
            if(confirm('您确定退出?')) 
            { 
                return true; 
            } 
            return false; 
        } 
        </script>
  <a href="../service/exit.php?id=<?php echo $id; ?>" onclick="return confirmAct();">
<button style="float: right;margin-right: 30px;height: 22px;width: 70px;font-size: 12px;margin-top: 2px;">退出后台</button>
  </a>
  </div>
<div id="form">
 <iframe  frameborder="0" name="view" width="100%" height="100%" scrolling="auto"></iframe>
</div>
</div>
</div>
	<div id="bottomDiv"></div>
	</div>
<script language="javascript">
   $(document).ready(function(){
    $("#test0").click(function(){
         $("#action0").toggle();
    });
    $("#test1").click(function(){
         $("#action1").toggle();
    });
    $("#test2").click(function(){
         $("#action2").toggle();
    });
    $("#test3").click(function(){
         $("#action3").toggle();
    });
    $("#test4").click(function(){
         $("#action4").toggle();
    });
    $("#test5").click(function(){
         $("#action5").toggle();
    });
    $("#test6").click(function(){
         $("#action6").toggle();
    });
    $("#test7").click(function(){
         $("#action7").toggle();
    });
    $("#test8").click(function(){
         $("#action8").toggle();
    });
    $("#test9").click(function(){
         $("#action9").toggle();
    }); 
});
</script>
  <?php
    }else{
  echo "<script>window.location.href='../index.html'</script>";
    }
   ?>
</body>
</html>