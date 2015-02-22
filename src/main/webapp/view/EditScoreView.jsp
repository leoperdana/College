<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>College</title>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/form.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.js"></script>
		<script type="text/javascript">
		
			var iStudentId = ${studentid};
			var iCoursesId = ${coursesid};
			var mPage = angular.module("page", []);
			
			mPage.controller
			(
				"ScoreController",
				function($scope, $http, $window)
				{     
					$scope.Cancel = function()
					{
						$window.location = "${pageContext.request.contextPath}/index";
					}
					
					$scope.Submit = function()
					{
						if($scope.frmScore.$invalid)
						{
							$scope.frmScore.txtScoreValue.$setTouched();
							$scope.frmScore.txtScoreExamDate.$setTouched();
							
							return;
						}
						
						var dExamDate = $scope.score.examdate;
						var strExamDate = dExamDate.getFullYear() + "-" + (dExamDate.getMonth() + 1) + "-" + dExamDate.getDate();
						var mParam = 
						{
							scorevalue : $scope.score.value,
							scoreexamdate : strExamDate
						};
						
						var strUrlDoEditScoreService = "${pageContext.request.contextPath}/doeditscoresvc/" + iStudentId + "/" + iCoursesId;

						$http.post(strUrlDoEditScoreService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
								
								alert("Score has been updated");
								$window.location = "${pageContext.request.contextPath}/index";
							}
						);
					}
					
					var strUrlEditScoreService = "${pageContext.request.contextPath}/editscoresvc/" + iStudentId + "/" + iCoursesId;
					
					$http.get(strUrlEditScoreService).success
					(
						function(mResponse)
						{
							if(!mResponse.status)
								return;
							
							$scope.score = 
							{
								student : mResponse.result.scorestudent,
								courses : mResponse.result.scorecourses,
								value : mResponse.result.scorevalue,
								examdate : new Date(mResponse.result.scoreexamdate)
							};
						}
					);
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="place" ng-controller="ScoreController">
			<form name="frmScore">
				<div class="title">Edit Score</div>
				<div class="component">
					<label class="label">Student</label>
					<select disabled>
						<option>{{score.student}}</option>
					</select>
				</div>
				<div class="component">
					<label class="label">Courses</label>
					<select disabled>
						<option>{{score.courses}}</option>
					</select>
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