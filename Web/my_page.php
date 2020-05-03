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
   <span class ="goodpersoncircle">我&nbsp;的&nbsp;好&nbsp;人&nbsp;圈&nbsp;都&nbsp;发&nbsp;生&nbsp;着:</span>
   </div>
   <div id="table_user_content" style="width: 66%;float: left;background-color: #F5F5F5;">
   <table id="view_user_content" border="0" style="width: 100%; font-size: 20px;">
  <?php
        if (isset($_GET["page"])) {
           $page = $_GET["page"];
             }else{
               $page = 1;
            }
           $list_num = 15;
           $temp = ($page-1)*$list_num;
         $listsql0 = "select * from send_text ";
         $listsel0 = mysql_query($listsql0,$conn);
         $listnum = mysql_num_rows($listsel0);
         $sql2 = "select * from send_text,user where send_text.userid = user.userid ORDER BY text_date desc limit $temp,$list_num";
        $sel2 = mysql_query($sql2,$conn);
        while ($res2 = mysql_fetch_array($sel2)) {
                $userid = $res2['userid']; 
                $textid = $res2['textid'];      
  ?>
     <tr >
      <td id="first_td" height="30" style="position: relative;left:20px;top:5px;">
      <?php
       $sql = "select * from user,register where user.userid = '$userid' and  register.userid ='$userid'";
       $sel = mysql_query($sql);
       $res = mysql_fetch_array($sel);
       if ($res['sex']=="男") {?>
       <img src="image/boy.jpg" alt="男" width="30" height="30">       
      <?php } ?>
      <?php if ($res['sex']=="女") {?>
       <img src="image/girl.jpg" alt="女" width="30" height="30">       
      <?php } ?>
       <a href="view_other.php?userid=<?php echo $userid; ?>&&userid0=<?php echo $userid0; ?>" target="_blank">
       <font color="#B03060" face="楷体" size="5" style="margin-left: 5px"><?php echo $res['username'] ?></font></a>
        发表于&nbsp;&nbsp;
       <?php 
      $time = strtotime($res2['text_date']);
      echo tranTime($time); 
       ?>        
       </td>
     </tr>
     <tr >
     <td id="second_td" style="position: relative;left:5px;top: 8px;font-size: 15px;">
     <?php
      $content = explode("/",$res2['text_content']);
      ?>
      <div id="text_content_set" style="margin-left: 5px;word-break:break-all;width:99%;">
      <?php 
      echo tranExpression($content);
      ?>
      </div>
     <div id="imgs_mypage_<?php echo $res2['textid'] ?>" style="margin-left: 5px;">
      <?php 
      $date = $res2['text_date'];
      $sql3 = "select * from send_picture where userid = '$userid' and picture_date='$date' ";
      $sel3 = mysql_query($sql3,$conn);
      while ($res3 = mysql_fetch_array($sel3)) {
            ?>
      <img src="content_image/<?php  echo $res3['picture_content']; ?>" alt="" width="120" height="120" data-original="content_image/<?php  echo $res3['picture_content'];?>">
      <?php 
       }
       ?>
       </div>
 <script language='javascript' charset="utf-8" type="text/javascript" >
     var viewer = new Viewer(document.getElementById('imgs_mypage_<?php echo $res2['textid']; ?>'), {
             url: 'data-original'
           });
    </script>
    <span id="video_content_set" style="margin-left:5px;">
      <?php  
      $sql4 = "select * from send_video where userid = '$userid' and video_date='$date' ";
      $sel4 = mysql_query($sql4,$conn);
      while ($res4 = mysql_fetch_array($sel4)) {
        ?>
     <video src="content_movie/<?php echo $res4['video_content'];?>"  controls style="height:220px;width:280px"></video>
     <?php 
      } 
     ?>
    </span>
     <br><br>   
     <span id="praise_action_<?php echo $res2['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick="" ><b>点赞</b>&nbsp;&nbsp;</span>
     <span id="concern_action_<?php echo $res2['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>关注</b>&nbsp;&nbsp;</span>
     <span id="collect_action_<?php echo $res2['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>收藏</b>&nbsp;&nbsp;</span>
     <span id="review_action_<?php echo $res2['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>评论</b>&nbsp;&nbsp;</span><br>
     <span id="praise_user_list_<?php echo $textid;?>" style="word-break:break-all;width:99%;">
     </span><br>
     <span id="review_user_list_<?php echo $textid;?>" style="word-break:break-all;width:99%;">
     </span>
     <div id="review_area_<?php echo $textid;?>" style="display: none;"> 
     <style>
    /*为空时内容显示 element attribute content*/
     #review_content_<?php echo $textid;?>:empty:before{
        content: attr(placeholder);
        color:red;   
    }
     /*焦点时内容为空*/
     #review_content_<?php echo $textid;?>:focus:before{
         content:none;
    }
     </style>
     <div name="review_content_<?php echo $textid;?>" id="review_content_<?php echo $textid;?>" contenteditable="true"  placeholder="最多输入100字" style="width:430px;height: 80px;background-color:#ffffff; overflow-y: auto;overflow-x: hidden;">
     </div>
     <img id="imgbtn_<?php echo $textid;?>" src="image/mood_body.png" title="添加表情" style="cursor:pointer;width: 25px;height: 25px;position:relative;top:5px;">
     <button id="review_launch_<?php echo $textid;?>" style="width:40px;height:25px;margin-left: 350px;">评论</button>
     </div>
     <div id="looklist_<?php echo $textid;?>" style= "display: none;padding-left: 5px;margin-top: 2px; background-color:#FFF8DC;">  
      <?php 
    echo expressionTran("review_content_".$textid,"looklist_".$textid);
       ?>
      </div>  
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
      $.ajax({
        url:'service/praise_list.php',
        type:'POST',
        data:{textid:<?php echo $textid; ?>},
        success:function(data){
        console.log(data); 
        <?php 
         $show_praise_sql = "select textid from praise where userid = '$userid0' ";
         $show_praise_sel = mysql_query($show_praise_sql,$conn);
         while ($show_praise_res = mysql_fetch_array($show_praise_sel)) {?>
      $("#praise_action_<?php echo $show_praise_res[0] ?>").html("<b>已赞&nbsp;&nbsp;</b>");         
         <?php 
          }
         ?> 
        if (data != "0<font color='#698B22' size='2.5'>人为</font><font color='#698B22' size='2.5'>点赞</font>") {
        $("#praise_user_list_<?php echo $textid; ?>").html(data); 
        }
      },
       error:function(){
       }
    });
     $("#praise_action_<?php echo $res2['textid'] ?>").click(function(){
        if ($("#praise_action_<?php echo $res2['textid'] ?>").html()=="<b>已赞&nbsp;&nbsp;</b>") {
        alert('您已赞过');
        return false;
      }
      $.ajax({
        url:'service/praise.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>,userid:<?php echo $userid; ?>,textid:<?php echo $textid; ?>},
        success:function(data){
          if (data.indexOf("您已赞过") != -1) {
            alert('您已赞过');
            return;
          }
           var array = data.split("&&");
           var array_username = new Array();
           var array_number = 0;
           for(var e1 in array){
            array2 = array[e1].split("##"); 
             for(var e2 in array2){
                if (e2%2==0) {
                  array_number=array_username.push(array2[e2]);
                } 
             }
          }
          $("#praise_user_list_<?php echo $textid;?>").html(array_username+"<font color='#698B22' size='2.5'>等</font>"+(array_number-1)+"<font color='#698B22' size='2.5'>人点赞</font>");
          $("#praise_action_<?php echo $res2['textid'] ?>").html("<b>已赞&nbsp;&nbsp;</b>");
       },
       error:function(){
       }
    }); 
    });
});
  $(document).ready(function(){
    $("#imgbtn_<?php echo $textid;?>").click(function(){
        $("#looklist_<?php echo $textid;?>").toggle();
    }); 
});
  $(document).ready(function(){
    $("#review_action_<?php echo $res2['textid'] ?>").click(function(){
        $("#review_area_<?php echo $textid;?>").toggle();
        $("#looklist_<?php echo $textid;?>").hide();
    }); 
 });
    $(document).ready(function(){
     $.ajax({
        url:'service/review_list.php',
        type:'POST',
        data:{textid:<?php echo $textid; ?>},
        success:function(data){
         $("#review_content_<?php echo $textid;?>").html("");
         if (data.trim()!="评论: ") {
         $("#review_user_list_<?php echo $textid;?>").html(data);
         }
       },
       error:function(){
       }
    });
    $("#review_launch_<?php echo $textid;?>").click(function(){
        $review_content = $("#review_content_<?php echo $textid;?>").html();
        $content_number = $("#review_content_<?php echo $textid;?>").text().length;
        if ($content_number>100) {
          alert('内容已超出!');
          return;
        }    
        if(!($("#review_content_<?php echo $textid;?>").html())){
         alert('请输入内容!');
         return;
        }
        $.ajax({
        url:'service/review.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>,userid:<?php echo $userid; ?>,textid:<?php echo $textid; ?>,review_content:$review_content},
        success:function(data){
         $("#review_content_<?php echo $textid;?>").html("");
         $("#review_user_list_<?php echo $textid;?>").html(data);
       },
       error:function(){
       }
    });
       $("#review_area_<?php echo $textid;?>").toggle();
       $("#looklist_<?php echo $textid;?>").hide();
    }); 
});
  $(document).ready(function(){
    $.ajax({
        url:'service/concern_list.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>},
        success:function(data){
        $("#number_set1").html(data);
        if (data != "0" || data != 0) {
        <?php  
        $concernsql = "select attention_id from concern where userid = '$userid0' ";
        $concernsel = mysql_query($concernsql,$conn);
        while ($concernres = mysql_fetch_array($concernsel)) {
        $concernsql2 = "select textid from send_text where userid = '$concernres[0]' ";
         $concernsel2 = mysql_query($concernsql2,$conn);
         while ($concernres2 = mysql_fetch_array($concernsel2)) {?>
        $("#concern_action_<?php echo $concernres2['textid'] ?>").html("<b>已关注&nbsp;&nbsp;</b>");    
        <?php }
         } 
        ?>
        }
       },
       error:function(){
       }
    });
    $("#concern_action_<?php echo $res2['textid'] ?>").click(function(){
      if ($("#concern_action_<?php echo $res2['textid'] ?>").html()=="<b>已关注&nbsp;&nbsp;</b>") {
        alert('您已关注!请尝试刷新在列表中查看');
        return false;
      }
      $.ajax({
        url:'service/concern.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>,userid:<?php echo $userid; ?>,textid:<?php echo $textid; ?>},
        success:function(data){
        if(data.indexOf("您已关注!请尝试刷新在列表中查看") != -1) {
            alert('您已关注!请尝试刷新在列表中查看');
            return;
          }
         $("#concern_action_<?php echo $res2['textid'] ?>").html("<b>已关注&nbsp;&nbsp;</b>");
         $("#number_set1").html(data);
       },
       error:function(){
       }
    });
  });
});
  $(document).ready(function(){
    $.ajax({
        url:'service/collection_list.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>},
        success:function(data){
        $("#number_set2").html(data);
        if (data != "0" || data != 0) {
        <?php  
        $collectionsql = "select collect_textid from collection where userid = '$userid0' ";
        $collectionsel = mysql_query($collectionsql,$conn);
        while ($collectionres = mysql_fetch_array($collectionsel)) {?>
        $("#collect_action_<?php echo $collectionres['collect_textid'] ?>").html("<b>已收藏&nbsp;&nbsp;</b>");    
        <?php 
         }
        ?>
        }
       },
       error:function(){
       }
    });
    $("#collect_action_<?php echo $res2['textid'] ?>").click(function(){
      if($("#collect_action_<?php echo $res2['textid'] ?>").html()=="<b>已收藏&nbsp;&nbsp;</b>") {
        alert('您已收藏!请尝试刷新在列表中查看');
        return false;
      }
      $.ajax({
        url:'service/collection.php',
        type:'POST',
        data:{userid0:<?php echo $userid0; ?>,userid:<?php echo $userid; ?>,textid:<?php echo $textid; ?>},
        success:function(data){
        if(data.indexOf("您已收藏!请尝试刷新在列表中查看") != -1) {
            alert('您已收藏!请尝试刷新在列表中查看');
            return;
          }
         $("#collect_action_<?php echo $res2['textid'] ?>").html("<b>已收藏&nbsp;&nbsp;</b>");
         $("#number_set2").html(data);
       },
       error:function(){
       }
    });
  });
});
 </script>
     <?php 
     echo "<br>";
     continue;
     } 
     ?>
     </td>
     </tr>
   </table>
   <br><br><br>
   <?php 
    echo "共"."<font color='red'>".$listnum."</font>"."条记录&nbsp;";
    $p_count = ceil($listnum/$list_num);
    $pre_page = $page  - 1;
    $next_page = $page + 1;
    if ($page<=1) {
    echo "首页 ";
    } 
    else{
    echo "<a href='./my_page.php?page=1'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './my_page.php?page=$pre_page&&id=$id'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./my_page.php?page=$next_page&&id=$id'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./my_page.php?page=$p_count&&id=$id'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
    ?> 
   </div>
 <div id="user_person_info">
  <script src="js/jquery-3.1.1.min.js" charset="utf-8"></script>
<script language='javascript' charset="utf-8" type="text/javascript" >
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
