<?php 
  session_start();
 ?>
<?php 
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $username = $_SESSION['username'];
  $content = $_POST['content_name'];
  echo $content;
  $date = date("Y-m-d H:i:s");
  $sql = " select * from user where username = '$username' ";
  $sel =  mysql_query($sql,$conn);
  $array = mysql_fetch_array($sel);
  if (isset($username)) {
        $sql2 = "insert into send_text(text_content,text_date,userid) values('$content','$date','$array[0]')";
        mysql_query($sql2,$conn);
        foreach ($_SESSION[file_true]  as $value) {
          $rEFileTypes1 = "/^\.(jpg|jpeg|gif|png|PNG){1}$/i"; 
          if (preg_match($rEFileTypes1, strrchr($value[name], '.'))) {
           $sql3 = "insert into send_picture(picture_content,picture_date,userid) values('$value[name]','$date','$array[0]')";
           mysql_query($sql3,$conn);
         }
        }
        foreach ($_SESSION[video_true]  as  $value2) {
          $rEFileTypes2 = "/^\.(mp4|flv|3gp|3gp2|avi|mov|ogg|webm){1}$/i"; 
          if (preg_match($rEFileTypes2,strrchr($value2[name], '.'))) {
           $sql4 = "insert into send_video(video_content,video_date,userid) values('$value2[name]','$date','$array[0]')";
           mysql_query($sql4,$conn);
          }
        }
         unset($_SESSION[video_true]);
         unset($_SESSION[file_true]);
         unset($_SESSION[video]);
         unset($_SESSION[file]); 
         mysql_close();           
    }
 ?>