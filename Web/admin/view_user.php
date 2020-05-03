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
    $listsql0 = "select * from user,register where user.userid = register.userid";
    $listsel0 = mysql_query($listsql0,$conn);
    $listnum = mysql_num_rows($listsel0);
   $sql = "select * from user,register where user.userid = register.userid limit $temp,$list_num";
   $sel = mysql_query($sql,$conn);?>
    <table border="2" style="font-size: 19px;">
     <tr >
        <td style="width: 20px;">ID</td>
        <td style="width: 50px;text-align: center;">用户名</td>
        <td style="width: 50px; text-align: center;">密码</td>
        <td style="width: 20px;">性别</td>
        <td style="width: 80px; text-align: center;">邮箱</td>
        <td style="width: 80px; text-align: center;">手机号</td>
        <td style="width: 150px; text-align: center;">地址</td>
        <td style="width: 20px;">粉丝</td>
        <td style="width: 20px;">关注</td>
        <td style="width: 20px;">收藏</td>
        <td style="width: 120px; text-align: center;">日期</td>
        <td  style="width:50px;text-align: center; ">操作</td>
   	 </tr>
  <?php while ($res = mysql_fetch_array($sel)) {?>  
     <tr >
        <td style="width: 20px;"><?php echo $res['userid'];?></td>
        <td style="width: 50px;"><?php echo $res['username'];?></td>
        <td style="width: 50px;"><?php echo $res['password'];?></td>
        <td style="width: 20px;"><?php echo $res['sex'];?></td>
        <td style="width: 80px;"><?php echo $res['mail'];?></td>
        <td style="width: 80px;"><?php echo $res['phone'];?></td>
        <td style="width: 150px;"><?php echo $res['address'];?></td>
        <td style="width: 20px;"><?php echo $res['attentioned_number'];?></td>
        <td style="width: 20px;"><?php echo $res['attention_number'];?></td>
        <td style="width: 20px;"><?php echo $res['collection_number'];?></td>
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
        <td style="width: 120px;"><?php echo $res['date'];?></td>
  <td><a href="del_user.php?&&userid=<?php echo $res['userid'];?>" onclick="return confirmAct();">
          <input type="submit" value="删除"></a></td>
   	 </tr>
   <?php 
   }?>
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
    echo "<a href='./view_user.php?page=1'>首页</a> | ";
    }
    if ($pre_page<1) {
    echo "上页 | ";
    } 
    else{
    echo "<a href = './view_user.php?page=$pre_page'>上页</a> | ";
     }
   if ($next_page > $p_count) {
    echo "下页 | ";
   }else{
    echo "<a href='./view_user.php?page=$next_page'>下页</a> | ";
  }
  if ($page>$p_count) {
     echo "尾页 </br>";
    }else{
    echo "<a href='./view_user.php?page=$p_count'>尾页</a></br>";
      }     
echo "当前显示第"."<font color='red' >".$page."</font>"."页&nbsp;&nbsp;";
    ?>