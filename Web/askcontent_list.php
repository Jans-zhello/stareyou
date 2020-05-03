<?php
  session_id($_GET['id']); 
  session_start();
  header('content-type:text/html charset:utf-8'); 
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
       $sql0 = "select * from user,register where username ='$username' and user.userid = register.userid";
       $sel0 = mysql_query($sql0,$conn);
       $res0 = mysql_fetch_array($sel0);
       $userid0 = $res0[0];
       $sex = $res0['sex'];
    ?>
   <div id="container">
   <div id="user_title">
   <span id="welcome_text">欢迎来到<font color="red" face="楷体" size="6" style="margin-left: 5px"><?php echo $username ?></font>的个人中心</span>
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
   <span class ="goodpersoncircle">这里面记载着您的好人好事:</span>
   </div>
   <div id="content_describe">
   <div id="do_write_left">
     <?php
     if (isset($_GET["page"])) {
     $page = $_GET["page"];
     }else{
       $page = 1;
     }
    $list_num = 15;
    $temp = ($page-1)*$list_num;
    $listsql0 = "select * from send_text where userid='$userid0'";
    $listsel0 = mysql_query($listsql0,$conn);
    $listnum = mysql_num_rows($listsel0);
     $sql = "select * from send_text where userid='$userid0' order by text_date desc limit $temp,$list_num ";
     $sel = mysql_query($sql,$conn);
     while ($res = mysql_fetch_array($sel)) {
      $textid = $res['textid'];
      $text_date = $res['text_date'];
      $time = strtotime($res['text_date']);
      $text_content = explode("/",$res['text_content']);
      if ($sex=="男"){?>
      <span id="asked_title">
      <img src="image/boy.jpg" alt="男" width="30" height="30"> 
      <?php }else{ ?>
      <img src="image/girl.jpg" alt="女" width="30" height="30"> 
     <?php }?>
      <?php echo "<font color='red' size='4'>".$username."</font>&nbsp;发表于&nbsp;&nbsp;&nbsp;".tranTime($time)." &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";?>
      <script type="text/javascript" language="javascript"> 

        function confirmAct1() 
        { 
            if(confirm('确定删除这条记录?')) 
            { 
                return true; 
            } 
            return false; 
        } 

        </script> 
    <a href="service/del.php?textid=<?php echo $textid;?>&&text_date=<?php echo $text_date; ?>&&id=<?php echo $id; ?>"onclick="return confirmAct1();"><input 
     type="submit" value="删除" style="width: 50px;height: 30px;"></a>
      </span>
      <div id="asked_content" style="margin-left: 2px;margin-top:5px;font-size:15px;word-break:break-all;width:100%;">
      <?php  echo tranExpression($text_content)."<br>";
      ?>
      </div>
<?php 
      $sql2 = "select * from send_picture where 
      picture_date = '$text_date' ";
      $sql3 = "select * from send_video where 
      video_date = '$text_date' ";
      $sel2 = mysql_query($sql2,$conn);
      $sel3 = mysql_query($sql3,$conn);
      echo "<br>";?>
    <div id="imgs_askcontent_list_<?php echo $res['textid']; ?>" style="margin-left: 25px;">
    <?php while ($res2 = mysql_fetch_array($sel2)){ ?> 
      <img src="content_image/<?php echo $res2['picture_content']; ?>" alt="" width="120" height="120" data-original="content_image/<?php echo $res2['picture_content']; ?>">
      <?php 
        }
        ?>
      </div>
  <script language='javascript' charset="utf-8" type="text/javascript" >
     var viewer = new Viewer(document.getElementById('imgs_askcontent_list_<?php echo $res['textid']; ?>'), {
             url: 'data-original'
           });
    </script>
      <?php
      echo "<br>";
      while ($res3 = mysql_fetch_array($sel3)) {?>
      <span id="asked_video" style="margin-left: 25px;">
      <video src="content_movie/<?php echo $res3['video_content'];?>"  controls style="height:220px;width:280px"></video>
      </span>
      <?php 
        }
       ?>
      <br>
       <?php 
       $sql6 = "select * from praise where 
      textid = '$textid' "; 
       $sel6 = mysql_query($sql6,$conn);
       $num = mysql_num_rows($sel6);
       $array = array();?>
    <span id="asked_praise" style="padding-left:15px;">
      <?php 
        while ($res6 = mysql_fetch_array($sel6)) {
         $praise_id = $res6['userid'];
         $sql7 = "select * from user where userid = '$praise_id' ";
         $sel7 = mysql_query($sql7,$conn);
         $res7 = mysql_fetch_array($sel7);
         if ($num<12) {?>
        <?php  
          echo $res7['username']."、"; 
          array_push($array,$res7['username']);
          }else{
           for ($i=0; $i<11 ; $i++) { 
         echo $array[$i]."<font color='#698B22' size='2.5'>等</font>";    
          }
        }
      }
       echo $num."<font color='#698B22' size='2.5'>人为你</font>"."<font color='#698B22' size='2.5'>点赞</font>";
       ?>
       </span>
       <?php 
       echo "<br>";
       ?>
     <?php
      $sql4 = "select * from review where 
      textid = '$textid' ";
      $sel4 = mysql_query($sql4,$conn);
     while($res4 = mysql_fetch_array($sel4)) {
      $review_id = $res4['userid'];
      $sql5 = "select * from user where userid = '$review_id' ";
      $sel5 = mysql_query($sql5,$conn);
      $res5 = mysql_fetch_array($sel5);
      $content2 = explode("/",$res4['review_content']);
      $time2 = strtotime($res4['review_date']);
      ?>
      <span id="asked_reivew" style="padding-left:5px;font-size: 15px;color:#912CEE;word-break:break-all;width:99%;"> 
      <?php 
      echo  "<font size='3'>".$res5['username']."</font>"."评论：";
      echo  tranExpression($content2)."&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
      echo  tranTime($time2)."<br>";
      ?> 
      </span>
      <?php 
      }
      echo "<br>";
    }
      echo "<br>";
      echo "共"."<font color='red'>".$listnum."</font>"."条记录&nbsp;";
    $p_count = ceil($listnum/$list_num);
    $pre_page = $page  - 1;
    $next_page = $page + 1;
    if ($page<=1) {
    echo "首页 ";
    } 
    else{
    echo "<a href='./askcontent_list.php?page=1&&id=$id'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './askcontent_list.php?page=$pre_page&&id=$id'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./askcontent_list.php?page=$next_page&&id=$id'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./askcontent_list.php?page=$p_count&&id=$id'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
      ?>
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

      </script>
   </div>
   <div id="do_write_right">
   <img src="image/write.jpeg" alt="发表" width="40" height="40" style="margin-top: 5px;margin-left: 5px;">
   <a href="do_content_write.php?id=<?php echo $id; ?>" >
    <span id="do_write_right_text">现在发表</span>
    </a>
   </div>
   </div>
   </div>
   </div>
   </div>
   </body>
   </html>