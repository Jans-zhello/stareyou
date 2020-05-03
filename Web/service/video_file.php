<?php 
  session_start();
  if (isset($_SESSION[video_true]) ||isset($_SESSION[video])) {
    unset($_SESSION[video]);
    unset($_SESSION[video_true]);
   } 
    $_SESSION[video] = $_FILES;
    $_SESSION[video_true] = $_SESSION[video];
 ?>
<?php
    include_once '../DB/connect.php';
	header('content-type:text/html charset:utf-8');
	$dir_base = "../content_movie/";
	if(empty($_FILES)) {
		echo "<textarea><img src='{$dir_base}error.mp4'/></textarea>";
		return false;
	}
	$output = "<textarea>";
	$index = 0;
	foreach($_FILES as $file){//循环遍历
		$upload_file_name = 'upload_file'.$index;
		//对应FomData中的文件命名
		$filename = $_FILES[$upload_file_name]['name'];
		$gb_filename = iconv('utf-8','gb2312//ignore',$filename);
		//名字转换成gb2312处理
		 if (!file_exists($dir_base.$gb_filename)) {  	
			$isMoved = false;  //默认上传失败
			$MAXIMUM_FILESIZE = 12*1024*1024; 
			$rEFileTypes = "/^\.(mp4|flv|3gp|3gp2|avi|mov|ogg|webm){1}$/i"; 
			if ($_FILES[$upload_file_name]['size'] <= $MAXIMUM_FILESIZE && 
				preg_match($rEFileTypes,strrchr($gb_filename, '.'))) {	
				$isMoved = @move_uploaded_file ( $_FILES[$upload_file_name]['tmp_name'], $dir_base.$gb_filename);		//上传文件
			}else{
			  echo "视频格式不正确";
			  return false;	
			}
	}else{
    $isMoved = true;
  }
		if($isMoved){   
			$output .= "<video src='./content_movie/{$filename}' title='{$filename}' alt='{$filename}' style='width:150px;height:150px;margin-left:2px;margin-top:2px;' controls='controls'/>";
		}else{
				echo "添加失败";
				return false;
			}
		$index++;
	}

   echo $output."</textarea>";
?>
	