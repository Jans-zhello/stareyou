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
       $sql0 = "select * from user where username ='$username'";
       $sel0 = mysql_query($sql0,$conn);
       $res0 = mysql_fetch_array($sel0);
       $userid0 = $res0[0];  
    ?>
   <div id="container">
   <div id="user_title">
   <span id="welcome_text">欢迎来到<font color="red" face="楷体" size="6" style="margin-left: 5px"><?php echo $username ?></font>的个人中心
   </span>
   <span id="user_exit">
   <a href="my_page.php?id=<?php echo $id; ?>" style="text-decoration: none;"><input type="submit" value="返回好人圈" style="width:80px; cursor: pointer; height:30px;"></a>
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
    <b  id="btn_num" style="color:red;">
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
   <span class ="goodpersoncircle">我&nbsp;的&nbsp;好&nbsp;人&nbsp;圈&nbsp;都&nbsp;发&nbsp;生&nbsp;着:</span>
   </div>
   <div id="table_user_content" style="width: 66%;float: left;background-color: #F5F5F5;">
     <?php 
    $new_userid = $_GET['new_userid'];
    $text_date = $_GET['text_date'];
    $new_date = $_GET['new_date'];
    $time = strtotime($new_date);
    $textid = $_GET['textid'];
    $userid = $_GET['userid'];
    $sql = "select * from user where user.userid = '$userid'";
    $sel = mysql_query($sql,$conn);
    $res = mysql_fetch_array($sel);
    ?>
  <span id="reviewdesc_header" style="margin-left: 10px;">  
  <?php echo "<font color='#BF3EFF' size='5'>".$res['username']."</font>"."评论："."&nbsp;&nbsp;".tranTime($time); ?>
  <input id="btn_review" type="submit" value="现在回复" style="position: relative;left: 240px;width: 60px;height: 25px;cursor: pointer;">
  <input id="btn_back" type="submit" value="返回消息列表" style="position: relative;left: 265px;width: 85px;height: 25px;cursor: pointer;">
   </span>
    <?php 
     $sql2 = "select * from review where review_date = '$new_date' ";
     $sel2 = mysql_query($sql2,$conn);
     $res2 = mysql_fetch_array($sel2);
     $review_content = explode("/",$res2['review_content']);
    ?>
   <br><br>
   <div id="reviewdesc_content" style="font-size: 18px;margin-left:2px;word-break:break-all;width:100%;"> 
   <?php 
   echo tranExpression($review_content)."<br>";
    ?>
   </div>
  <div id="reviewed_text" style="margin-left: 10px; width: 99%;height: 120px;border-top: 1px black solid;border-bottom: 1px black solid;">
   <div id="reviewed_text_left" style="float: left;width: 20%;height: 100%; border-right: 1px black solid;word-break:break-all;width:20%;">
   <?php 
   $sql3 = "select * from send_text where textid='$textid' ";
   $sel3 = mysql_query($sql3,$conn);
   $res3 = mysql_fetch_array($sel3);
   $sql5 = "select * from send_picture where picture_date='$text_date' ";
   $sel5 = mysql_query($sql5,$conn);
   $res5 = mysql_fetch_array($sel5);
   $sql6 = "select * from send_video where video_date='$text_date' ";
   $sel6 = mysql_query($sql6,$conn);
   $res6 = mysql_fetch_array($sel6);
   if (!empty($res3['text_content'])) {?>
    <?php 
    $char = iconv_strlen($res3['text_content'],"utf-8");
    if ($char>55) {
    $res3['text_content'] = iconv_substr($res3['text_content'], 0,55,"utf-8")."......";}
    $content = explode("/",$res3['text_content']); 
    echo tranExpression($content);
    }else if (!empty($res5['picture_content'])) {?>
    <img src="content_image/<?php  echo $res5['picture_content']; ?>" alt="" style="width:100%;height:100%;" >  
    <?php 
     }else if (!empty($res6['video_content'])) {?>
    <video src="content_movie/<?php echo $res6['video_content'];?>"  controls style="object-fit:fill;width:100%;height:100%;"></video>
    <?php 
     }
    ?>  
   </div>
   <div id="reviewed_text_right" style="float: right;width: 79%;height: 100%;text-align: center; line-height: 110px;font-size: 18px;">
    <?php
    $sql4 = "select * from user where userid = '$new_userid' ";
    $sel4 = mysql_query($sql4,$conn);
    $res4 = mysql_fetch_array($sel4); 
    $time2 = strtotime($res3['text_date']);
    echo $res4['username']."发表于&nbsp;".tranTime($time2);
    ?>
   </div> 
  </div>
  <div id="response" style="display: none;">
   <style>
    #response_content:empty:before{
     content:attr(placeholder);
     color:red;
}
    #response_content:focus:before{
     content:none;
} 
  </style>
   <div name="response_content" id="response_content" contenteditable= "true" placeholder="最多输入100字" style="margin-left: 25px;margin-top: 15px; width: 80%;height: 80px;background-color: #ffffff;font-size: 15px;" >
   </div>
   <img id="imgbtn" src="image/mood_body.png" title="添加表情" style="cursor:pointer;width: 25px;height: 25px;position:relative;top:5px;margin-left:25px;">
   <input type="submit" id="btnsubmit" value="回复" style="float: right;margin-right: 100px;position: relative; top: 5px;width: 50px;height: 25px;cursor: pointer;">
   </div>
   <div id="expression_list" style= "display: none;">  
    <?php 
    echo expressionTran("response_content","expression_list");
     ?>
   </div>
   <div id="show_review" style="margin-left:15px;"></div>
   <div id="show_review_all" style="display: none;margin-top: 5px;">
   <div id="show_review_all_titile" style="width: 100%;height: 20px;background-color: #9AC0CD;text-align: center;font-size: 18px;cursor: pointer;" onclick="">显示所有评论：
   </div>
   <div id="show_review_all_content" style="margin-left: 15px;"></div> 
   </div>
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
    $("#btn_review").click(function(){
        $("#response").toggle();
    });
  });
    $(document).ready(function(){
    $("#btn_back").click(function(){
      location.href = "my_new.php?id=<?php echo $id; ?>";
    });
  });
    $(document).ready(function(){
    $("#imgbtn").click(function(){
        $("#expression_list").toggle();
    });
  });
    $(document).ready(function(){
    $("#btnsubmit").click(function(){
  var $response_content = $("#response_content").text();  
  var $response_content_number = $("#response_content").text().length;
      if ($response_content_number>100) {
        alert('最多输入100字');
        return;
      }
      if ($response_content.trim()==""||$response_content=="") {
        alert('请输入内容！');
        return;
      }
      $.ajax({
        url:'service/new_response.php',
        type:'POST',
        data:{response_content:$response_content,new_userid:<?php echo $new_userid; ?>,userid:<?php echo $userid; ?>,textid:<?php echo $textid; ?>,new_date:<?php echo $time; ?>},
        success:function(data){
          $("#response_content").html("");
          $("#show_review").html(data);
          $("#response").toggle();
          $("#show_review_all").toggle();
       },
       error:function(){
          alert('网络错误!');
       }
    });
    });
  });
  $(document).ready(function(){
    $("#show_review_all_titile").click(function(){
        $("#show_review_all_content").toggle();
    });
  });
  $(document).ready(function(){
    $("#show_review_all_titile").click(function(){
      $.ajax({
        url:'service/show_response_all.php',
        type:'POST',
        data:{textid:<?php echo $textid; ?>},
        success:function(data){
           $("#show_review_all_content").html(data);
       },
       error:function(){
          alert('网络错误!');
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