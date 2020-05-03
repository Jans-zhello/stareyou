<?php 
  session_start();
  header('content-type:text/html charset:utf-8');
 ?>
 <?php 
  include_once '../DB/connect.php';
  ?>
  <?php 
    $textid = $_GET['textid'];
    $content = $_GET['content'];
    $sql = "SET FOREIGN_KEY_CHECKS=0;";
    $sql2 = "delete from send_text where textid='$textid'";
    $del = mysql_query($sql,$conn);
    $del2 = mysql_query($sql2,$conn);
    if ($del and $del2 and empty($content)) {
     echo "<script> window.location.href='view_text.php';
           </script>;";
  }else if ($del and $del2 and isset($content)) {?>
  <div style="text-align:center;">
  <form action="" method="get">
  <span>
  请输入查询关键字
  <input type="text" id="content" name="content" style="width: 200px;height:25px;font-size: 18px;">
  <input type="submit" style="width: 50px;height: 30px;" value="查询">
  </span>
  </form>
  </div>
  <?php
     if (isset($_GET["page"])) {
     $page = $_GET["page"];
     }else{
       $page = 1;
     }
    $list_num = 8;
    $temp = ($page-1)*$list_num;
    $listsql0 = "select * from send_text where concat(textid,text_content,text_date,userid) like'%".$content."%' ";
    $listsel0 = mysql_query($listsql0,$conn); 
    $listnum = mysql_num_rows($listsel0);
 if (isset($_GET['content'])|| !empty($_GET['content']))  {  
   $content = $_GET['content'];
   $sql = "select * from send_text where concat(textid,text_content,text_date,userid) like'%".$content."%' limit $temp,$list_num ";
   $sel = mysql_query($sql,$conn);?>
   <table border="2" style="font-size: 19px;">
     <tr>
        <td style="width: 20px;">ID</td>
        <td style="width: 570px;text-align: center;">内容</td>
        <td  style="width: 170px;text-align: center;">日期</td>
        <td  style="width: 80px;text-align: center;">发送者</td>
        <td  style="width:50px;text-align: center; ">操作</td>
     </tr>
  <?php while ($res = mysql_fetch_array($sel)) {
      $search_id = $res['userid'];
    ?>  
    <tr>
    <td style="width: 20px;"><?php echo $res['textid'];?></td>
  <td style="width: 570px;"><?php  echo $res['text_content'];?></td>
    <td style="width: 170px; text-align: center;"><?php echo $res['text_date'];?></td>
    <?php  
      $sql2 = "select username from user where userid = '$search_id' ";
      $sel2 = mysql_query($sql2,$conn);
      $res2 = mysql_fetch_array($sel2);
    ?>
    <td style="width: 80px; text-align: center;"><?php echo $res2['username'];?></td>
          <script type="text/javascript" language="javascript"> 
        function confirmAct() 
        { 
            if(confirm('确定删除这条记录?')) 
            { 
                return true; 
            } 
            return false; 
        } 
        </script>
    <td><a href="del_text.php?&&textid=<?php echo $res['textid'];?>&&content=<?php echo $content; ?>" onclick="return confirmAct();">
          <input type="submit" value="删除"></a></td>
    </tr>
   <?php }?>
   </table>
   <?php 
    echo "<br>";
    echo "共"."<font color='red'>".$listnum."</font>"."条记录&nbsp;";
    $p_count = ceil($listnum/$list_num);
    $pre_page = $page  - 1;
    $next_page = $page + 1;
    if ($page<=1) {
    echo "首页 ";
    } 
    else{
    echo "<a href='./view_search_text.php?page=1&&content=$content'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './view_search_text.php?page=$pre_page&&content=$content'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./view_search_text.php?page=$next_page&&content=$content'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./view_search_text.php?page=$p_count&&content=$content'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
         }
    }else {
      echo "<script> alert('出错');
           </script>;";
    }
  ?>