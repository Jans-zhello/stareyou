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
   <div id="ask_content_right">
   <div id="title_circle_set">
   <span class ="goodpersoncircle">这&nbsp;里&nbsp;有&nbsp;您&nbsp;的&nbsp;永&nbsp;久&nbsp;收&nbsp;藏:</span>
   </div>
   <div id="new_user_content" style="width: 66%;float: left;background-color: #F5F5F5;">
     <?php 
      $userid = $_GET['userid'];
      $textid = $_GET['textid'];
      $collection_date = $_GET['collection_date'];
      $time=strtotime($collection_date);
      $sql = "select * from send_text where textid = '$textid'";
      $sel = mysql_query($sql,$conn);
      while ($res=mysql_fetch_array($sel)) {
        $text_date = $res['text_date'];?>
        <div id="collection_desc_set" style="width: 100%;height: 30px;background-color:#DEB887;padding-left:10px;color: #3B3B3B;font-size: 20px;line-height: 30px;border-bottom: 1px black solid;">
        <?php echo "<b>您收藏于</b>&nbsp;&nbsp;&nbsp;&nbsp;".tranTime($time);?>
        </div>
        <div id="collection_desc_content" style="width: 100%;background-color: #DEB887">
        <div id="collection_desc_text" style="font-size:16px;padding-left: 10px;padding-top: 10px;word-break:break-all;width:99%;">
        <?php 
        $content = explode("/",$res['text_content']);
        echo tranExpression($content);
         ?>
         </div>
         <br>
        <?php 
        $sql2 = "select * from send_picture where picture_date = '$text_date'";
        $sel2 = mysql_query($sql2,$conn);?>
      <div id="imgs__show_collection_article_<?php echo $res['textid'] ?>" style="margin-left: 15px;">
<?php while ($res2 = mysql_fetch_array($sel2)) { ?>
  <img src="content_image/<?php echo $res2['picture_content']; ?>" alt="" style="width:120px;height:120px;border:3px black solid" data-original="content_image/<?php echo $res2['picture_content'];?>">  
        <?php 
          }
        ?>
        </div>
        <script language='javascript' charset="utf-8" type="text/javascript" >
     var viewer = new Viewer(document.getElementById('imgs__show_collection_article_<?php echo $res['textid'] ?>'), {
             url: 'data-original'
           });
    </script>
        <?php 
          echo "<br>";
        $sql3 = "select * from send_video where video_date = '$text_date'";
        $sel3 = mysql_query($sql3,$conn);
        while ($res3 = mysql_fetch_array($sel3)) {?>
        <span id="collection_desc_video" >
        <video src="content_movie/<?php echo $res3['video_content'];?>"  controls style="width:200px;height:200px;"></video>
        </span>
      <?php 
          }
        ?>
        </div>
       <?php 
        }
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