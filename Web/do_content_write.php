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
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <script src="js/jquery-3.1.1.min.js" charset="utf-8"></script>
  <script type="text/javascript" charset="utf-8">
  $(document).ready(function(){
    $("#imgbtn").click(function(){
        $("#divName").toggle();
        $("#divName0").hide();
    }); 
});
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
<script language='javascript' charset="utf-8" >
 function insertHtmlAtCaret(html) {  
        var sel, range;  
        if (window.getSelection) {  
            sel = window.getSelection();  
            if (sel.getRangeAt && sel.rangeCount) {  
                range = sel.getRangeAt(0);  
                range.deleteContents();  
                var el = document.createElement("div");  
                el.innerHTML = html;  
                var frag = document.createDocumentFragment(), node, lastNode;  
                while ((node = el.firstChild)) {  
                    lastNode = frag.appendChild(node);  
                }  
                range.insertNode(frag);  
                if (lastNode) {  
                    range = range.cloneRange();  
                    range.setStartAfter(lastNode);  
                    range.collapse(true);  
                    sel.removeAllRanges();  
                    sel.addRange(range);  
                }  
            }  
        } else if (document.selection && document.selection.type != "Control") {  
            document.selection.createRange().pasteHTML(html);  
        }
      $("#divName").hide(); 
    }   
</script>
<script language='javascript' charset="utf-8"  >
$(document).ready(function(){
  //响应文件添加成功事件
  $("#pict_file").change(function(){
    //创建FormData对象
    var data = new FormData();
    var file = $("#pict_file").val();
    //为FormData对象添加数据
    $.each($("#pict_file")[0].files,function(i,file){
    var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
　　if(reg.test(file['name'])){     
       alert("不能用汉字命名！");  
       return false;      
　　} 
    if (file['name'].indexOf(".mp4") != -1 ||file['name'].indexOf(".3gp")!= -1 ||file['name'].indexOf(".3gp2")!= -1||file['name'].indexOf(".avi")!= -1||file['name'].indexOf(".mov")!= -1||file['name'].indexOf(".ogg")!= -1||file['name'].indexOf(".webm")!= -1||file['name'].indexOf(".flv")!= -1){
        alert('图片格式不正确!');
        return false;
      }
      if (Math.ceil(file['size']/1024/1024)>2) {
        alert('图片太大,请重新添加!');
        return false;
      }
      if (i>6) {
        alert('最多添加7张!');
        return false;
      }
      data.append('upload_file'+i, file);
    });
    $("#divName2").show();
    $("#divName0").hide();
    //发送图片数据
    $.ajax({
      url:'service/send_picture.php',
      type:'POST',
      data:data,
      cache: false,
      contentType: false,   //不可缺参数
      processData: false,   //不可缺参数
      success:function(data){
      if (data.indexOf("error.jpg")!=-1) {
          return;
      }
       if (data.indexOf('添加失败') != -1 || data.indexOf('图片格式不正确') != -1) {
          alert(data);
          return ;
      }
      data = $(data).html();
      if($("#feedback").children('img').length == 0) $("#feedback").append(data.replace(/&lt;/g,'<').replace(/&gt;/g,'>'));
        else $("#feedback").children('img').eq(-1).after(data.replace(/&lt;/g,'<').replace(/&gt;/g,'>'));       
          $(".loading").hide();
      },
      error:function(){
        alert('添加出错');
      }
    });
      
  });
});
</script>
<script language='javascript' charset="utf-8" >
function progressBar(){
  //初始化js进度条
  $("#bar").css("width","0px");
  var speed = 250;
  bar = setInterval(function(){
   nowWidth = parseInt($("#bar").width());
   //宽度要不能大于进度条的总宽度
   if(nowWidth<=200){
    barWidth = (nowWidth + 1)+"px";
    $("#bar").css("width",barWidth);
   }else{
    //进度条读满后，停止
    clearInterval(bar);
   } 
  },speed);
 }
$(document).ready(function(){
  //响应文件添加成功事件
  $("#video_file").change(function(){
    //创建FormData对象
    var data = new FormData();
    //为FormData对象添加数据
    $.each($('#video_file')[0].files, function(i, file) {
    var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
　　if(reg.test(file['name'])){     
       alert("不能用汉字命名！");  
       return ;      
　　} 
     if (file['name'].indexOf(".jpg") != -1 ||file['name'].indexOf(".jpeg")!= -1 ||file['name'].indexOf(".gif")!= -1||file['name'].indexOf(".png")!= -1||file['name'].indexOf(".PNG")!= -1){
        alert('视频格式不正确!');
        return false;
      }
      if (Math.ceil(file['size']/1024/1024)>12) {
        alert('视频太大,请重新添加');
        return false;
      }
      if (i>0) {
        alert('最多添加1部!');
        return false;
      }
      data.append('upload_file'+i, file);
      console.log(file['name']);
    });
    $("#divName3").show();
    $("#divName0").hide();
    progressBar();
    //发送图片数据
    $.ajax({
      url:'service/video_file.php',
      type:'POST',
      data:data,
      cache: false,
      contentType: false,   //不可缺参数
      processData: false,   //不可缺参数
      success:function(data){
      if (data.indexOf("error.mp4")!=-1) {
        $(".progressBar").hide();
        $("#bar").hide();
        return;
      }
      if (data.indexOf("添加失败") != -1 || data.indexOf('视频格式不正确') != -1) {
          alert(data);
          $(".progressBar").hide();
          $("#bar").hide();
          return;
      }
      data = $(data).html();
      if($("#feedback2").children('video').length == 0) $("#feedback2").append(data.replace(/&lt;/g,'<').replace(/&gt;/g,'>'));
        else $("#feedback2").children('video').eq(-1).after(data.replace(/&lt;/g,'<').replace(/&gt;/g,'>'));       
          $(".progressBar").hide();
          $("#bar").hide();
      },
      error:function(){
        alert('添加出错');
      }
    });
      
  });
});
</script>
<script language='javascript' charset="utf-8" type="text/javascript" >
  $(document).ready(function(){
    $("#ask_btn").click(function(){
      $content_name = $("#main_content").text();
      $content_number = $("#main_content").text().length;
      if ($content_number>400) {
        alert('内容已超出!');
        return;
      }    
      if ((!($("#main_content").text())||$content_name.trim()=="") && !($("#pict_file").val()) && !($("#video_file").val())){
        alert('请输入内容!');
        return;
        }
      //ajax获取div编辑区的内容传值到php后台
      $.ajax({
      url:'service/send.php',
      type:'POST',
      data:{content_name:$content_name},
      success:function(data){
         alert("发表成功");
       window.location.href='./my_page.php?id=<?php echo $id; ?> ';
      },
      error:function(){
         alert('网络错误,请重新发表！');
         window.location.href='./do_content_write.php?id=<?php echo $id; ?>';
      }
    }); 
    }); 
});
</script>
  <meta charset="utf-8">
  <title>stare you</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body style="background-size: 100% 100%;background-image:url(image/qingdiao.jpg);background-repeat: no-repeat;">
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
    <form action="" method="post" enctype="multipart/form-data" name="form1">
     <div id="will_ask_content">
     <div id="option_content_count">您最多输入400个字</div>
     <div id="main_content" name ="main_content" contenteditable="true" style="overflow-y: auto;overflow-x: hidden;"></div>
     <div id="more_kinds_content">
      <span id="gif" >
     <img id="imgbtn" src="image/mood_body.png" alt="添加表情" width="25" height="25" style="cursor:pointer;" >
      </span>&nbsp;&nbsp;
      <span id="pict" >
      <img src="image/pict_body.jpg" alt="添加图片" width="22" height="22" style="margin-top:1px;" onclick="document.getElementById('pict_file').click()">
      <span class="border"></span>
      <input type="file" id="pict_file" name="pict_file" multiple="multiple">
       </span>&nbsp;&nbsp;&nbsp;&nbsp;
       <span id="video">
       <img src="image/video_body.jpg" alt="添加视频" width="22" height="22" style="margin-top:1px;" onclick="document.getElementById('video_file').click()">
       <span class="border"></span>
       <input type="file" id="video_file" name="video_file" multiple="multiple">
      </span> 
      <span id="ask">
      <img id="ask_btn" src="image/ask_body.gif" alt="发表" width="70" height="30" style="float: right;margin-right:5px;cursor:pointer;">
      </span>
     </div>
     </div>
     </form>
     <div id="divName0" style="font-size: 22px;margin-top: 70px;margin-left: 270px;">
     <p style="text-align: left;">
    <h2 style="font-size: 28px;color: red;">注:</h2><br>
     &nbsp;&nbsp;&nbsp;1、文件请不要用汉字命名<br><br>
     &nbsp;&nbsp;&nbsp;2、最多添加7张图片,1部视频<br><br>
     &nbsp;&nbsp;&nbsp;3、若添加多张图片,请一次性选中全部添加
    </p>
     </div>
     <div id="divName" style="display:none;">  
       <?php 
    echo expressionTran("main_content","divName");
       ?>
      </div>
      <div id="divName2" style="display:none;">
      <span id="introduce">添加图片如下:(图片大小不超过2M)
      <img class="loading" src="image/loading.gif" alt="正在加载" >
      </span>
      <div id="feedback"></div>
      </div>
     <div id="divName3" style="display:none;">
      <div id="introduce2">添加视频如下:(视频大小不超过12M)
       <style>
     .progressBar{width:200px;height:8px;border:1px solid #98AFB7;border-radius:5px;margin-top:10px;}
      #bar{width:0px;height:8px;border-radius:5px;background:#5EC4EA;}
       </style>
      <div class="progressBar"><div id="bar"></div></div>
      </div>
      <div id="feedback2"></div>
      </div>
   </div>
   </div>
   </div>
   </body>
   </html>