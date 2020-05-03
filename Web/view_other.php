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
<script src="js/jquery-3.1.1.min.js" charset="utf-8"></script>
</head>
<body style="background-size: 100% 100%;background-image:url(image/qingdiao.jpg);background-repeat: no-repeat;">
   <?php
     $userid0 = $_GET['userid0'];
    if (isset($_GET['userid'])) {
      $userid_view = $_GET['userid'];
      $sql0 = "select * from user where userid = '$userid_view' ";
      $sel0 = mysql_query($sql0,$conn);
      $res0 = mysql_fetch_array($sel0);
      $username0 = $res0['username'];
     }else{
        echo "<script>window.location.href='./index.html'</script>";
        return;
     }
    ?>
   <div id="container">
   <div id="user_title">
   <span id="welcome_text">欢迎访问<font color="red" face="楷体" size="6" style="margin-left: 5px"><?php echo $username0 ?></font>的个人中心</span> 
   </div>  
   <div id="view_other_left">
     <span id="other_article" style="display:inline-block;margin: 10px; font-size:19px;border-right: 1px black solid;">文章
      &nbsp;<br>
    <?php  
      $article_numsql = "select * from send_text where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_num = mysql_num_rows($article_numsel);
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5'>".$article_num."</font>";
      }
      ?>  
      </span>
     <span id="other_picture" style="display:inline-block;margin: 10px;font-size: 19px;border-right: 1px black solid;">图片
       &nbsp;<br>
          <?php  
      $article_numsql = "select * from send_picture where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_num = mysql_num_rows($article_numsel);
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5'>".$article_num."</font>";
      }
      ?> 
      </span>
     <span id="other_video" style="display:inline-block;margin: 10px;font-size: 19px;">视频&nbsp;<br>
     <?php  
      $article_numsql = "select * from send_video where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_num = mysql_num_rows($article_numsel);
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5'>".$article_num."</font>";
      }
      ?> 
     </span>
   </div>
   <div id="view_other_right">
   <div id="video_content_left">
    <table id="view_user_content" border="0" style="width: 100%;font-size: 20px;">
  <?php
    if (isset($_GET["page"])) {
     $page = $_GET["page"];
     }else{
       $page = 1;
     }
    $list_num = 15;
    $temp = ($page-1)*$list_num;
    $listsql0 = "select * from send_text where userid='$userid_view'";
    $listsel0 = mysql_query($listsql0,$conn);
    $listnum = mysql_num_rows($listsel0);
    $sql = "select * from send_text where userid='$userid_view' order by text_date desc limit $temp,$list_num ";
    $sel = mysql_query($sql,$conn);
    while ($res = mysql_fetch_array($sel)) {
            $userid = $res['userid']; 
            $textid = $res['textid'];      
  ?>
     <tr >
      <td id="first_td" height="30" style="position: relative;left:20px;top:5px;">
      <?php
       $sql2 = "select * from user,register where user.userid = '$userid' and  register.userid ='$userid'";
       $sel2 = mysql_query($sql2,$conn);
       $res2 = mysql_fetch_array($sel2);
       if ($res2['sex']=="男") {?>
       <img src="image/boy.jpg" alt="男" width="30" height="30">       
      <?php } ?>
      <?php if ($res2['sex']=="女") {?>
       <img src="image/girl.jpg" alt="女" width="30" height="30">       
      <?php } ?>
       <font color="#B03060" face="楷体" size="5" style="margin-left: 5px"><?php echo $res2['username'] ?></font>
        发表于&nbsp;&nbsp;
       <?php 
      $time = strtotime($res['text_date']);
      echo tranTime($time); 
       ?>        
       </td>
     </tr>
     <tr >
     <td id="second_td" style="position: relative;left:5px;top: 8px;font-size: 15px;">
     <?php
      $content = explode("/",$res['text_content']);
      ?>
      <div id="text_content_set" style="margin-left: 5px;word-break:break-all;width:99%;">
      <?php 
      echo tranExpression($content);
      ?>
      </div>
     <div id="imgs_mypage_<?php echo $res['textid'] ?>" style="margin-left: 5px;">
      <?php 
      $date = $res['text_date'];
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
     var viewer = new Viewer(document.getElementById('imgs_mypage_<?php echo $res['textid']; ?>'), {
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
     <span id="praise_action_<?php echo $res['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick="" ><b>点赞</b>&nbsp;&nbsp;</span>
     <span id="concern_action_<?php echo $res['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>关注</b>&nbsp;&nbsp;</span>
     <span id="collect_action_<?php echo $res['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>收藏</b>&nbsp;&nbsp;</span>
     <span id="review_action_<?php echo $res['textid'] ?>" style="cursor: pointer;color:#4D4D4D;" onclick=""><b>评论</b>&nbsp;&nbsp;</span><br>
     <span id="praise_user_list_<?php echo $textid;?>">
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
     <div id="looklist_<?php echo $textid;?>" style= "display: none;position: relative;left: 0px; background-color:#FFF8DC;">  
      <?php 
    echo expressionTran("review_content_".$textid,"looklist_".$textid);
       ?>
      </div>  
   <script src="js/jquery-3.1.1.min.js" charset="utf-8"></script>
   <script language='javascript' charset="utf-8" type="text/javascript" >
   $(document).ready(function(){
      $.ajax({
        url:'service/praise_list.php',
        type:'POST',
        data:{textid:<?php echo $textid; ?>},
        success:function(data){ 
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
     $("#praise_action_<?php echo $res['textid'] ?>").click(function(){
        if ($("#praise_action_<?php echo $res['textid'] ?>").html()=="<b>已赞&nbsp;&nbsp;</b>") {
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
          $("#praise_action_<?php echo $res['textid'] ?>").html("<b>已赞&nbsp;&nbsp;</b>");
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
    $("#review_action_<?php echo $res['textid'] ?>").click(function(){
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
    $("#concern_action_<?php echo $res['textid'] ?>").click(function(){
      if ($("#concern_action_<?php echo $res['textid'] ?>").html()=="<b>已关注&nbsp;&nbsp;</b>") {
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
         $("#concern_action_<?php echo $res['textid'] ?>").html("<b>已关注&nbsp;&nbsp;</b>");
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
    $("#collect_action_<?php echo $res['textid'] ?>").click(function(){
      if($("#collect_action_<?php echo $res['textid'] ?>").html()=="<b>已收藏&nbsp;&nbsp;</b>") {
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
         $("#collect_action_<?php echo $res['textid'] ?>").html("<b>已收藏&nbsp;&nbsp;</b>");
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
    echo "<a href='./view_other.php?page=1&&userid=$userid_view&&userid0=$userid0'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './view_other.php?page=$pre_page&&userid=$userid_view&&userid0=$userid0'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./view_other.php?page=$next_page&&userid=$userid_view&&userid0=$userid0'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./view_other.php?page=$p_count&&userid=$userid_view&&userid0=$userid0'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
    ?>   
   </div>
   <div id="video_content_right">
      <span id="other_fans" style="display:inline-block;margin: 8px; font-size: 20px;border-right: 1px black solid;">粉丝
      &nbsp;<br>
<?php  
      $article_numsql = "select * from user where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_numres = mysql_fetch_array($article_numsel);
      $article_num = $article_numres['attentioned_number'];
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5'>".$article_num."</font>";
      }
      ?>
      </span>
     <span id="other_concern" style="display:inline-block;margin: 8px;font-size: 20px;border-right: 1px black solid;">关注
       &nbsp;<br>
       <?php  
      $article_numsql = "select * from user where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_numres = mysql_fetch_array($article_numsel);
      $article_num = $article_numres['attention_number'];
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5'>".$article_num."</font>";
      }
      ?>
       </span>
     <span id="other_collection" style="display:inline-block;margin: 8px;font-size: 20px;">收藏&nbsp;<br>
            <?php  
      $article_numsql = "select * from user where userid = '$userid_view' " ;
      $article_numsel = mysql_query($article_numsql,$conn);
      $article_numres = mysql_fetch_array($article_numsel);
      $article_num = $article_numres['collection_number'];
      if ($article_num>100000000) {
       $article_num = ($article_num/100000000)*10;
echo "<font color='#A020F0' size='5'>".$article_num."亿</font>";
      }else if ($article_num>10000000) {
        $article_num = ($article_num/10000000);
echo "<font color='#A020F0' size='5'>".$article_num."千万</font>";
      }else if ($article_num>1000000) {
       $article_num = ($article_num/1000000)*100;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else if($article_num>100000){
        $article_num = ($article_num/100000)*10;
  echo "<font color='#A020F0' size='5'>".$article_num."万</font>";
      }else {
  echo "<font color='#A020F0' size='5' >".$article_num."</font>";
      }
      ?>
     </span>
   </div>
   </div>
   </div>
   </body>
   </html>