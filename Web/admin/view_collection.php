<?php 
  session_start();
  header('content-type:text/html charset:utf-8');
 ?>
 <?php 
  include_once '../DB/connect.php';
  ?>
  <?php 
  if (isset($_GET["page"])) {
     $page = $_GET["page"];
     }else{
       $page = 1;
     }
    $list_num = 15;
    $temp = ($page-1)*$list_num;
    $listsql0 = "select * from collection";
    $listsel0 = mysql_query($listsql0,$conn);
    $listnum = mysql_num_rows($listsel0);
   $sql = "select * from collection limit $temp,$list_num";
   $sel = mysql_query($sql,$conn);?>
     <table border="2" style="font-size: 19px;">
     <tr>
        <td style="width: 20px;">ID</td>
        <td style="width: 80px;text-align: center;">用户名1</td>
        <td style="width: 50px;">行为</td>
        <td  style="width: 80px;text-align: center;">用户名2</td>
        <td  style="width: 20px;text-align: center;">文章ID</td>
        <td  style="width: 200px;text-align: center;">日期</td>
        <td  style="width:50px;text-align: center; ">操作</td>
     </tr>
  <?php while ($res = mysql_fetch_array($sel)) {
         $userid1 = $res['userid'];
         $userid2 = $res['collect_userid'];
    ?>  
    <tr>
    <td style="width: 20px;"><?php echo $res['collectionid'];?></td>
       <?php 
       $sql2 = "select username from user where userid = '$userid1' ";
       $sql3 = "select username from user where userid = '$userid2' ";
        $sel2 = mysql_query($sql2,$conn);
        $sel3 = mysql_query($sql3,$conn);
        $res2 = mysql_fetch_array($sel2);
        $res3 = mysql_fetch_array($sel3);
       ?>
  <td style="width: 80px;"><?php echo $res2[0];?></td>
  <td style="width: 50px;">收藏</td>
  <td style="width: 80px; text-align: center;"><?php echo $res3[0];?></td>
  <td style="width: 80px; text-align: center;"><?php echo $res['collect_textid'];?></td>
   <td style="width: 200px; text-align: center;"><?php echo $res['collection_date'];?></td>
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
   <td><a href="del_collection.php?&&collectionid=<?php echo $res['collectionid'];?>&&content=<?php echo $content; ?>"onclick="return confirmAct();"><input type="submit" value="删除"></a></td>
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
    echo "<a href='./view_collection.php?page=1'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './view_collection.php?page=$pre_page'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./view_collection.php?page=$next_page'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./view_collection.php?page=$p_count'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
    ?>