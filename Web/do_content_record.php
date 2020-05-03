<?php 
  session_id($_GET['id']);
  session_start();
  header('content-type:text/html charset:utf-8');
 ?>
 <?php 
  include_once './DB/connect.php';
  include_once './service/date.php';
  include_once './service/expression_show.php';
  include_once './service/expression_text.php';
  ?>
<!DOCTYPE html>
<html lang="zh-cn">
<head> 
  <meta charset="utf-8">
  <title>stare you</title>
   <script src="js/viewer.min.js"></script>
  <link rel="stylesheet" href="css/viewer.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body style="background-size: 100% 100%;background-image:url(image/qingdiao.jpg);background-repeat: no-repeat;">
   <?php 
   $id = $_GET['id'];
  if (!isset($id)||empty($id) ) {
     echo "<script>window.location.href='./index.html'</script>";
     return;
    }
  $username = $_SESSION['username'];
  $sql0 = "select userid from user where username='$username' ";
  $sel  = mysql_query($sql0,$conn);
  $res  = mysql_fetch_array($sel);
  $userid0 = $res[0];
    ?>
   <div id="container">
   <div id="user_title">
   <span id="welcome_text">欢迎来到<font color="red" face="楷体" size="6" style="margin-left: 5px"><?php echo $username ?></font>的个人中心</span>
   <span id="user_exit">
   <a href="my_page.php?id=<?php echo $id; ?>" style="text-decoration: none;"><input type="submit" value="返回好人圈" style="width:80px; height:30px;"></a>
   </span> 
    <span id="user_exit">
  <script type="text/javascript" language="javascript"> 
        function confirmAct() 
        { 
            if(confirm('您确定要离开?')) 
            { 
                return true; 
            } 
            return false; 
        } 
        </script>
   <a href="service/exit.php?id=<?php echo $id; ?>" style="text-decoration:none;"  onclick="return confirmAct();"><input type="submit" value="离开好人圈" style="width:80px; cursor:pointer; height:30px;"></a>
   </span> 
   </div>
   <div id="ask_content">
     <div id="ask_content_left">
   <div id="do_content_write">
    <span class="do_content_do">
    <a href="askcontent_list.php?id=<?php echo $id; ?>" style="text-decoration: none;">我要发表</a>
    </span>
    </div>
    <div id="do_content_voice">
    <span id="btn_new" onclick="">
     <?php 
    $new_sql  = "select sum(new_userid_number) from new where new_userid='$userid0' ";
    $new_sel2 = mysql_query($new_sql,$conn);
    $new_num = mysql_fetch_array($new_sel2);
      ?>
    <a  href="my_new.php?new_num=<?php echo $new_num[0];?>&&id=<?php echo $id; ?>"  style="text-decoration: none; ">我的消息
    </a>
    <b  id="btn_num" style="position:absolute;left:200px;color:red;">
    <?php 
    if ($new_num[0] != 0) {
      echo "+".$new_num[0];  
    }
    ?>
    </b>
    </span>
    </div>
    <div id="do_content_record">
    <span class="do_content_do">
    <a href="do_content_record.php?id=<?php echo $id; ?>" style="text-decoration: none;">我的相册</a>
    </span>
    </div>
    <div id="do_content_share">
    <span class="do_content_do">
    <a href="do_content_share.php?id=<?php echo $id; ?>" style="text-decoration: none;">我的视频</a>
    </span>
    </div>
    <div id="concern">
    <span id="number_set1">
    <?php 
      $fansql0 = "select attention_number from user where userid='$userid0' ";
      $fansel0 = mysql_query($fansql0,$conn);
      $fansres0 = mysql_fetch_array($fansel0);
      echo $fansres0['attention_number'];
    ?>
    </span>
    <span id="set_text"><b>我的关注</b></span>
    </div>
    <div id="collect">
       <span id="number_set2">
    <?php 
      $fansql1 = "select collection_number from user where userid='$userid0' ";
      $fansel1 = mysql_query($fansql1,$conn);
      $fansres1 = mysql_fetch_array($fansel1);
      echo $fansres1['collection_number'];
    ?>
    </span>
    <span id="set_text"><b>我的收藏</b></span>
    </div>
    <div id="fans">
    <span id="number_set3">
    <?php 
      $fansql2 = "select attentioned_number from user where userid='$userid0' ";
      $fansel2 = mysql_query($fansql2,$conn);
      $fansres2 = mysql_fetch_array($fansel2);
      echo $fansres2['attentioned_number'];
      ?> 
    </span>
    <span id="set_text"><b>我的粉丝</b></span>
    </div>
   </div>
   </div>
   <div id="ask_content_right">
   <div id="title_circle_set">
   <span class ="goodpersoncircle">这&nbsp;里&nbsp;有&nbsp;您&nbsp;的&nbsp;美&nbsp;好&nbsp;回&nbsp;忆:</span>
   </div>
   <div id="new_user_content" style="width: 66%;float: left;background-color: #F5F5F5;">
   <div id="imgs" style="background-image:url(image/bg.jpg);">
    <?php
     if (isset($_GET["page"])) {
     $page = $_GET["page"];
     }else{
       $page = 1;
     }
    $list_num = 20;
    $temp = ($page-1)*$list_num;
    $listsql0 = "select picture_content from send_picture,user where username='$username' and user.userid = send_picture.userid";
    $listsel0 = mysql_query($listsql0,$conn);
    $listnum = mysql_num_rows($listsel0);
   $sql = "select picture_content from send_picture,user where username='$username' and user.userid = send_picture.userid limit $temp,$list_num ";
   $sel = mysql_query($sql,$conn);
   $num = mysql_num_rows($sel);
   while ($res=mysql_fetch_array($sel)) {?>   
  <img src="content_image/<?php echo $res['picture_content']; ?>" alt="" width="120" height="120" style="margin:10px;border:4px white solid" data-original="content_image/<?php echo $res['picture_content']; ?>" >
   <?php
     }
    ?>
  </div>
<script language='javascript' charset="utf-8" type="text/javascript" >
     var viewer = new Viewer(document.getElementById('imgs'), {
             url: 'data-original'
           });
  </script>
   <?php
   echo "<br><br>"; 
   echo "共"."<font color='red'>".$listnum."</font>"."条记录&nbsp;";
    $p_count = ceil($listnum/$list_num);
    $pre_page = $page  - 1;
    $next_page = $page + 1;
    if ($page<=1) {
    echo "首页 ";
    } 
    else{
    echo "<a href='./do_content_record.php?page=1&&id=$id'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './do_content_record.php?page=$pre_page&&id=$id'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./do_content_record.php?page=$next_page&&id=$id'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./do_content_record.php?page=$p_count&&id=$id'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
      ?>  
  </div>
   <div id="user_person_info">
<script src="js/jquery-3.1.1.min.js" charset="utf-8"></script>
<script language='javascript' charset="utf-8" type="text/javascript" >
  $(document).ready(function(){
    $("#btn_new").click(function(){
    $.ajax({
        url:'service/remove_new_number.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>},
        success:function(data){ 
        },
        error:function(){
       }
    });
    }); 
});
    $(document).ready(function(){
    $("#imgbtn_fans").click(function(){
        $("#fans_user_list").toggle();
    });
  });
    $(document).ready(function(){
    $("#imgbtn_concern").click(function(){
        $("#concern_user_list").toggle();
    });
  });
    $(document).ready(function(){
    $("#imgbtn_collection").click(function(){
        $("#collection_user_list").toggle();
    });
  });
  $(document).ready(function(){
    $.ajax({
        url:'service/fans_userlist.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>},
        success:function(data){
         if (data=="") {
           $("#fans_user_list").html("<font size='5' style='margin-left:120px;'><b>暂无粉丝</b><font>");
           return;
         }
         $("#fans_user_list").html(data);
       },
       error:function(){
          alert('网络错误!');
       }
    });
  });
    $(document).ready(function(){
     $.ajax({
        url:'service/concern_userlist.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>},
        success:function(data){
         if (data=="") {
           $("#concern_user_list").html("<font size='5' style='margin-left:120px;'><b>暂无关注</b><font>");
           return;
         }
         $("#concern_user_list").html(data);
       },
       error:function(){
          alert('网络错误!');
        }
     });
   });
    $(document).ready(function(){
      $.ajax({
        url:'service/collection_userlist.php',
        type:'POST', 
        data:{userid0:<?php echo $userid0; ?>,id:'<?php echo $id; ?>'},
        success:function(data){
         if (data=="") {
           $("#collection_user_list").html("<font size='5' style='margin-left:120px;'><b>暂无收藏</b><font>");
           return;
         }
         $("#collection_user_list").html(data);
       },
       error:function(){
       }
    });
  }); 
   </script>
   <span  id="imgbtn_fans" style="display: block; color: #303030;text-align:center;font-size: 20px;">
   <img src="image/list.jpg" alt="显示列表" style="width: 15px;height: 15px;cursor: pointer;">
    <b>粉&nbsp;丝&nbsp;列&nbsp;表&nbsp;</b>
   </span>
   <div id="fans_user_list" style="width: 100%;height: 200px;overflow-y: auto;overflow-x: hidden;display: none;">
   </div>
   <span  id="imgbtn_concern"  style="display: block; color: #303030;text-align:center;font-size: 20px;">
   <img src="image/list.jpg" alt="显示列表" style="width: 15px;height: 15px;cursor: pointer;">
   <b>关&nbsp;注&nbsp;列&nbsp;表&nbsp;</b></span>
   <div id="concern_user_list" style="width: 100%;height: 200px;overflow-y: auto;overflow-x: hidden;display: none;" >
   </div>
   <span  id="imgbtn_collection" style="display: block; color: #303030;text-align:center;font-size: 20px;">
   <img src="image/list.jpg" alt="显示列表" style="width: 15px;height: 15px;cursor: pointer;">
   <b>收&nbsp;藏&nbsp;列&nbsp;表&nbsp;</b></span>
   <div id="collection_user_list" style="width: 100%;height:465px;overflow-y: auto;overflow-x: hidden;">
   </div>
   </div>

   </div>
   </div>
   </div>
   </body>
   </html>