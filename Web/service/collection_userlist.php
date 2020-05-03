<?php
   session_start();
?>
<?php
  include_once '../DB/connect.php';
  include_once 'date.php';
  header('content-type:text/html charset:utf-8');
  $id = $_POST['id'];
  $userid0 = $_POST['userid0']; 
 $listsql = "select collect_userid,collect_textid,collection_date from collection where userid = '$userid0' order by collection_date desc ";
  $listsel = mysql_query($listsql,$conn);
  while ($listres = mysql_fetch_array($listsel)) {
   $sql = "select username from user where userid='$listres[0]' ";
   $sel = mysql_query($sql,$conn);
   $res = mysql_fetch_array($sel);
   echo "&emsp;&emsp;&emsp;";
    ?>
<a href="./show_collection_article.php?textid=<?php echo $listres[1];?>&&userid=<?php echo $listres[0];?>&&collection_date=<?php 
echo $listres[2]; ?>&&id=<?php echo $id; ?> ">
<?php 
echo "您收藏了"."<font color='#473C8B' size='4'>".$res['username']."</font>"."<font color='#9400D3'>"."的文章"."</font>&nbsp;&nbsp;".tranTime(strtotime($listres[2]))."<br>";
?>
</a>
  <?php  }
      mysql_close();
?>