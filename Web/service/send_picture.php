<?php 
   session_start();
  if (isset($_SESSION[file_true]) || isset($_SESSION[file])) {
    unset($_SESSION[file]);
    unset($_SESSION[file_true]);
   } 
    $_SESSION[file] = $_FILES;
    $_SESSION[file_true] = $_SESSION[file];
 ?>
<?php
  include_once '../DB/connect.php';
  header('content-type:text/html charset:utf-8');
  $dir_base = "../content_image/";
  if(empty($_FILES)) {
    echo "<textarea><img src='./content_image/error.jpg'/></textarea>";
    return;
  }
  $output = "<textarea>";
  $index = 0;
  foreach($_FILES as $file) {
  $upload_file_name = 'upload_file'.$index;
    //对应FomData中的文件命名
  $filename = $_FILES[$upload_file_name]['name'];
  $gb_filename = iconv('utf-8','gb2312//ignore',$filename);
    //名字转换成gb2312处理
    if (!file_exists($dir_base.$gb_filename)) {  
      $isMoved = false;  //默认上传失败
      $MAXIMUM_FILESIZE = 2*1024*1024; 
      $rEFileTypes = "/^\.(jpg|jpeg|gif|png){1}$/i"; 
      if ($_FILES[$upload_file_name]['size'] <= $MAXIMUM_FILESIZE && 
        preg_match($rEFileTypes, strrchr($gb_filename, '.'))) { 
        $isMoved = @move_uploaded_file ( $_FILES[$upload_file_name]['tmp_name'], $dir_base.$gb_filename);   //上传文件
      }else{
        echo "图片格式不正确";
        return false;
      }
  } else{
    $isMoved = true;
  }
    if($isMoved){   
      $output .= "<img src='./content_image/{$filename}' title='{$filename}' alt='{$filename}' style='width:120px;height:120px;margin-left:2px;margin-top:2px;' />";
    }else{
        echo "添加失败";
        return false;
      }
      $index++;
} 

   echo $output."</textarea>";
?>