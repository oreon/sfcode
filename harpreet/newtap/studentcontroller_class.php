<?php

 include_once 'student_class.php';

 $controller = new StudentController();
 $controller->save();

class StudentController {

	function save() {
		$s = new student();

		$fn = $_GET['firstName'];
		$ln = $_GET['lastName'];
		$db = $_GET['dob'];
		$gn = $_GET['gender'];
		
		$s->lastName = $ln;
		$s->firstName = $fn;
		$s->dob = $db;
		$s->gender = $gn;
		
		$qry = "insert into student(firstname, lastname, dob, gender) values ('$fn', '$ln', '$db', '$gn')";
		
		//print $qry;
		
		$this->connect();
		$result = mysql_query($qry);
		if(!$result)
			die( mysql_error() );
		$id = mysql_insert_id();
			
		session_start(); 
		$_SESSION['student'] = serialize($s); 
		header( 'Location: studentSaveSuccess.php?id='.$id ) ;
		
	}

	function connect(){
		$dbcnx = @mysql_connect("localhost", "root", "root");
		if (!$dbcnx)
		{
			echo( "<p>connection to database server failed!</p>");
			exit();
		}

		if (! @mysql_select_db("newtap") )
		{
			echo( "<p>Image Database Not Available!</p >" );
			exit();
		}
	}

}




?>