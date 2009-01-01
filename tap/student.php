<?include_once 'entity.php';?>


<?php

include 'grade.php';

class Student extends Entity {
	var $firstName;
	var $lastName;
	var $grade;


	function __construct($fn = null, $ln = null){
		$this->firstName = $fn;
		$this->lastName = $ln;
		$this->grade = new Grade();
	}

	function toString(){
		return $this->firstName ." ". $this->lastName." ".$this->grade->id;
	}

	function getLoadQuery(){
		return "select s.id as id, s.firstName as firstName, s.lastName as lastName, g.id as grade___id, g.name as grade___name  from 
			student s, grade g where s.gradeId = g.id and s.id = $this->id";
	}

	function getPersistQuery(){
		return "Insert into student(firstName, lastName) values('$this->firstName', '$this->lastName')";
	}

	function getUpdateQuery(){
		return "Update student set firstName='$this->firstName', lastName='$this->lastName',
		 gradeId =". $this->grade->id." where id=$this->id";
	}

}


?>