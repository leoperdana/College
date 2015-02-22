<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>College</title>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.js"></script>
		<script type="text/javascript">
		
			var mPage = angular.module("page", []);
			
			mPage.controller
			(
				"ScoreController",
				function($scope, $http, $window)
				{     
					$scope.lStudent = [];
					$scope.RetrieveStudent = function()
					{
						var strUrlGetStudentService = "${pageContext.request.contextPath}/getstudentsvc";
						
						$http.get(strUrlGetStudentService).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
									
								$scope.lStudent = mResponse.result;
							}
						);
					}
					
					$scope.lCourses = [];
					$scope.RetrieveCourses = function()
					{
						var mParam = {studentid : $scope.score.student};
						var strUrlGetCoursesService = "${pageContext.request.contextPath}/getcoursessvc";
						
						$http.post(strUrlGetCoursesService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
									
								$scope.lCourses = mResponse.result;
							}
						);
					}
					
					$scope.Cancel = function()
					{
						$window.location = "${pageContext.request.contextPath}/index";
					}
					
					$scope.Submit = function()
					{
						if($scope.frmScore.$invalid)
						{
							$scope.frmScore.selScoreStudent.$setTouched();
							$scope.frmScore.selScoreCourses.$setTouched();
							$scope.frmScore.txtScoreValue.$setTouched();
							$scope.frmScore.txtScoreExamDate.$setTouched();
							
							return;
						}
						
						var dExamDate = $scope.score.examdate;
						var strExamDate = dExamDate.getFullYear() + "-" + (dExamDate.getMonth() + 1) + "-" + dExamDate.getDate();
						var mParam = 
						{
							studentid : $scope.score.student,
							coursesid : $scope.score.courses,
							scorevalue : $scope.score.value,
							scoreexamdate : strExamDate
						};
						
						var strUrlDoAddScoreService = "${pageContext.request.contextPath}/doaddscoresvc";
						
						$http.post(strUrlDoAddScoreService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;

								alert("Score has been added");
								$window.location = "${pageContext.request.contextPath}/index";
							}
						);
					}
					
					$scope.RetrieveStudent();
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="place" ng-controller="ScoreController">
			<form name="frmScore">
				<div class="title">Add Score</div>
				<div class="component">
					<label for="selScoreStudent" class="label">Student</label>
					<select id="selScoreStudent" name="selScoreStudent" ng-model="score.student" ng-change="RetrieveCourses()" required>
						<option value=""> -- Choose Student -- </option>
						<option value="{{mStudent.student_id}}" ng-repeat="mStudent in lStudent">{{mStudent.student_name}}</option>
					</select>
					<div ng-show="frmScore.selScoreStudent.$touched || frmScore.selScoreStudent.$dirty">
						<div class="error" ng-show="frmScore.selScoreStudent.$error.required">This field is required</div>
					</div>
				</div>
				<div class="component">
					<label for="selScoreCourses" class="label">Courses</label>
					<select id="selScoreCourses" name="selScoreCourses"  ng-model="score.courses" required>
						<option value=""> -- Choose Courses -- </option>
						<option value="{{mCourses.courses_id}}" ng-repeat="mCourses in lCourses">{{mCourses.courses_name}}</option>
					</select>
					<div ng-show="frmScore.selScoreCourses.$touched || frmScore.selScoreCourses.$dirty">
						<div class="error" ng-show="frmScore.selScoreCourses.$error.required">This field is required</div>
					</div>
				</div>
				<div class="component">
					<label for="txtScoreValue" class="label">Score</label>
					<input type="number" id="txtScoreValue" name="txtScoreValue" ng-model="score.value" required  />
					<div ng-show="frmScore.txtScoreValue.$touched || frmScore.txtScoreValue.$dirty">
						<div class="error" ng-show="frmScore.txtScoreValue.$error.required">This field is required</div>
						<div class="error" ng-show="frmScore.txtScoreValue.$error.number">Wrong score format</div>
					</div>
				</div>
				<div class="component">
					<label for="txtScoreExamDate" class="label">Exam Date</label>
					<input type="date" id="txtScoreExamDate" name="txtScoreExamDate" ng-model="score.examdate" placeholder="yyyy-mm-dd" required  />
					<div ng-show="frmScore.txtScoreExamDate.$touched || frmScore.txtScoreExamDate.$dirty">
						<div class="error" ng-show="frmScore.txtScoreExamDate.$error.required">This field is required</div>
						<div class="error" ng-show="frmScore.txtScoreExamDate.$error.date">Wrong exam date format</div>
					</div>
				</div>
				<div class="action">
					<button type="button" class="button-link" ng-click="Cancel()">Cancel</button>
					<button type="button" class="button-Submit" ng-click="Submit()">Submit</button>
				</div>
			</form>
		</div>
	</body>
</html>