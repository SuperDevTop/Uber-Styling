<?php
//error_reporting(0);
session_start();
class Activity
{    
	public function createActivity($user_id,$activity_id,$date,$duration) {
		$goalQry = mysql_query("INSERT into hm_activity_detail(user_id,activity_id,date,duration)values('".$user_id."', '".$activity_id."','".$date."','".$duration."')");
		$aid = mysql_insert_id();
		if($aid){
			$response['aid']=$aid;
			$response['responseMessage']='Your activity is set.';
			$response['responseCode']='200';
		}else{
			$response['responseMessage']='Any error occurred.';
			$response['responseCode']='000';
		}
       
        return json_encode($response);
    }
    public function listActivityLog($user_id) {
	$activityQry = mysql_query("select s.activity, t.activity_id, t.date, t.duration from hm_activity s join hm_activity_detail t on s.id=t.activity_id where t.user_id ='".$user_id."'");
		$rows = array();
		while($data = mysql_fetch_object($activityQry)){
			$row['activity_id'] = $data->activity_id;
			$row['myActivity'] = $data->activity;
			$row['date'] = $data->date;
			$row['duration'] = $data->duration;
			$rows[] = $row;
		}
		if(count($rows)>0){
			$response['activityList']=$rows;
			$response['responseMessage']='activity LOG list.';
			$response['responseCode']='200';
		}else{
			$response['responseMessage']='No data found.';
			$response['responseCode']='000';
		}
       
        return json_encode($response);
  }
  
  public function activityList() {
		$activityQry = mysql_query("SELECT `id`,`activity` FROM `hm_activity` group by `activity`");
		$rows = array();
		while($data = mysql_fetch_object($activityQry)){
			
			$row['activity_id'] = $data->id;
			$row['activity'] = $data->activity;
			$rows[] = $row;
		}
		if(count($rows)>0){
			$response['activityList']=$rows;
			$response['responseMessage']='activity list.';
			$response['responseCode']='200';
		}else{
			$response['responseMessage']='No data found.';
			$response['responseCode']='000';
		}
       
        return json_encode($response);
    }  
	public function specificMotionList($activity) {
		$activityQry = mysql_query("SELECT `id`,`specific_motion` FROM `hm_activity` WHERE `activity` ='".trim($activity)."' ");
		$rows = array();
		while($data = mysql_fetch_object($activityQry)){
			
			$row['id'] = $data->id;
			$row['specific_motion'] = $data->specific_motion;
			$rows[] = $row;
		}
		if(count($rows)>0){
			$response['specificMotionList']=$rows;
			$response['responseMessage']='specific motion list.';
			$response['responseCode']='200';
		}else{
			$response['responseMessage']='No data found.';
			$response['responseCode']='000';
		}
       
        return json_encode($response);
    }
	public function activityLevel($specific_motion_id) {
		$activityQry = mysql_query("SELECT `id`,`activity_level`,`activity`,`specific_motion`,`mets` FROM `hm_activity` WHERE id='".$specific_motion_id."'");
		$rows = array();
		while($data = mysql_fetch_object($activityQry)){
			$row['id'] = $data->id;
			$row['activity_level'] = $data->activity_level;
			$row['activity'] = $data->activity;
			$row['specific_motion'] = $data->specific_motion;
			$row['activity_mets'] = $data->mets;
		}
        $response['activityLevel']=$row;
		$response['responseMessage']='activity level get successfully.';
		$response['responseCode']='200';
        return json_encode($response);
    }	
	public function addAssessment($user_id,$target_weight,$target_time,$waist,$hip,$stress_level,$health_condition,$activity) {
		$sql=
		$activityQry = mysql_query("INSERT INTO `hm_user_assessment` (`user_id`, `target_weight`, `target_time`, `waist`, `hip`, `stress_level`, `health_condition`) VALUES ('".$user_id."', '".$target_weight."', '".$target_time."', '".$waist."', '".$hip."', '".mysql_real_escape_string(trim($stress_level))."', '".$health_condition."')");
		
		$assessment_id=mysql_insert_id();
		
		 if(count($activity)>0){
			foreach($activity as $k=>$v){
				$sql=mysql_query("INSERT INTO `hm_user_assessment_activity` (`assessment_id`,`user_activity_id`, `user_activity`, `user_specific_motion`, `user_activity_level`, `user_met`, `duration`, `pas`, `expected_calories_burnt`,`is_assessment`,`user_id`) VALUES ('".$assessment_id."', '".$v->activity_id."', '".mysql_real_escape_string(trim($v->activity_name))."', '".mysql_real_escape_string(trim($v->activity_motion))."', '".mysql_real_escape_string(trim($v->activity_level))."', '".$v->activity_met."', '".$v->activity_duration."', '".$v->activity_pas."', '".$v->expected_calories."','1','".$user_id."')");
				
			}
		} 
        $response['activity_id']=$assessment_id;
		$response['responseMessage']='Assessment created successfully.';
		$response['responseCode']='200';
		//$response['responseCodea']=$activity;
        return json_encode($response);
    }
	
	public function newActivity($activity_name,$user_id){
		$sql=mysql_query("INSERT INTO `hm_activity_request` (`activity_name`, `user_id`) VALUES ('".$activity_name."', '".$user_id."');");
		
		$response=array();
		if($sql){
			$response['responseMessage']='activity request added successfully.';
			$response['responseCode']='200';
		}else{	
			 $response['responseMessage']='Error in adding activity.';
			$response['responseCode']='000';
		}
        return json_encode($response);
	}
}			


