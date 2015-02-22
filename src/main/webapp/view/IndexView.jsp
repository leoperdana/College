<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>College</title>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
		<script type="text/javascript">
		
			var mPage = angular.module("page", []);
			
			mPage.controller
			(
				"StudentController",
				function($scope, $http, $rootScope)
				{     
					var Retrieve = function()
					{
						var strUrlListStudentService = "${pageContext.request.contextPath}/liststudentsvc";
						
						$http.get(strUrlListStudentService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
									
								$scope.lStudent = mResponse.result;
							}
						);
					}					
					
					$scope.Delete = function(iStudentId)
					{
						var bConfirmation = confirm("Are you sure you want to delete this data?");
						if(!bConfirmation)
							return;
						
						var strUrlDoDeleteStudentService = "${pageContext.request.contextPath}/dodeletestudentsvc/" + iStudentId;
						
						$http.get(strUrlDoDeleteStudentService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									alert("Failed to delete student");
								else
								{
									alert("Student has been deleted");
									Retrieve();
									
									$rootScope.$broadcast("RetrieveScore");
								}
							}
						);
					}
					
					Retrieve();
				}
			);
			
			mPage.controller
			(
				"CoursesController",
				function($scope, $http)
				{     
					var Retrieve = function()
					{
						var strUrlListCoursesService = "${pageContext.request.contextPath}/listcoursessvc";
						
						$http.get(strUrlListCoursesService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
								
								$scope.lCourses = mResponse.result;
							}
						);
					}
					
					$scope.Delete = function(iCoursesId)
					{
						var bConfirmation = confirm("Are you sure you want to delete this data?");
						if(!bConfirmation)
							return;
						
						var strUrlDoDeleteCoursesService = "${pageContext.request.contextPath}/dodeletecoursessvc/" + iCoursesId;

						$http.get(strUrlDoDeleteCoursesService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									alert("Failed to delete courses");
								else
								{
									alert("Courses has been deleted");
									Retrieve();
									
									$rootScope.$broadcast("RetrieveScore");
								}
							}
						);
					}
					
					Retrieve();
				}
			);
			
			mPage.controller
			(
				"ScoreController",
				function($scope, $http)
				{     
					var Retrieve = function()
					{
						var strUrlListScoreService = "${pageContext.request.contextPath}/listscoresvc";
						
						$http.get(strUrlListScoreService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
									
								$scope.lScore = mResponse.result;
							}
						);
					}
					
					$scope.Delete = function(iStudentId, iCoursesId)
					{
						var bConfirmation = confirm("Are you sure you want to delete this data?");
						if(!bConfirmation)
							return;
						
						var strUrlDoDeleteScoreService = "${pageContext.request.contextPath}/dodeletescoresvc/" + iStudentId + "/" + iCoursesId;
						
						$http.get(strUrlDoDeleteScoreService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									alert("Failed to delete score");
								else
								{
									alert("Score has been deleted");
									Retrieve();
								}
							}
						);
					}
					
					$scope.$on
					(
						"RetrieveScore",
						function()
						{
							Retrieve();
						}
					);
					
					Retrieve();
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="container">
			<div class="place first" ng-controller="StudentController">
				<div class="header clear">
					<span class="left">Student</span>
					<a href="addstudent" class="button-link right">Add</a>
				</div>
				<table>
					<thead>
						<tr>
							<th>Number</th>
							<th>Name</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="mStudent in lStudent">
							<td>{{mStudent.student_number}}</td>
							<td>{{mStudent.student_name}}</td>
							<td><a href="editstudent/{{mStudent.student_id}}">edit</a> <a href="" ng-click="Delete(mStudent.student_id)">delete</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="place" ng-controller="CoursesController">
				<div class="header clear">
					<span class="left">Courses</span>
					<a href="addcourses" class="button-link right">Add</a>
				</div>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th>Credit</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="mCourses in lCourses">
							<td>{{mCourses.courses_name}}</td>
							<td>{{mCourses.courses_credit}}</td>
							<td><a href="editcourses/{{mCourses.courses_id}}">edit</a> <a href="" ng-click="Delete(mCourses.courses_id)">delete</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="place" ng-controller="ScoreController">
				<div class="header clear">
					<span class="left">Score</span>
					<a href="addscore" class="button-link right">Add</a>
				</div>
				<table>
					<thead>
						<tr>
							<th>Student Name</th>
							<th>Courses Name</th>
							<th>Score</th>
							<th>Exam Date</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="mScore in lScore">
							<td>{{mScore.student_name}}</td>
							<td>{{mScore.courses_name}}</td>
							<td>{{mScore.score_value}}</td>
							<td>{{mScore.score_exam_date}}</td>
							<td><a href="editscore/{{mScore.student_id}}/{{mScore.courses_id}}">edit</a> <a href="" ng-click="Delete(mScore.student_id, mScore.courses_id)">delete</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>