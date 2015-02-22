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
			var mPage = angular.module("page", []);
			
			mPage.controller
			(
				"StudentController",
				function($scope, $http, $window)
				{     
					$scope.Cancel = function()
					{
						$window.location = "${pageContext.request.contextPath}/index";
					}
					
					$scope.Submit = function()
					{
						if($scope.frmStudent.$invalid)
						{
							$scope.frmStudent.txtStudentName.$setTouched();
							
							return;
						}
							
						var mParam = {studentname : $scope.student.name};
						var strUrlDoEditStudentService = "${pageContext.request.contextPath}/doeditstudentsvc/" + iStudentId;

						$http.post(strUrlDoEditStudentService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
								
								alert("Student has been updated");
								$window.location = "${pageContext.request.contextPath}/index";
							}
						);
					}
					
					var strUrlEditStudentService = "${pageContext.request.contextPath}/editstudentsvc/" + iStudentId;
					
					$http.get(strUrlEditStudentService).success
					(
						function(mResponse)
						{
							if(!mResponse.status)
								return;
							
							$scope.student = 
							{
								number : mResponse.result.studentnumber,
								name : mResponse.result.studentname
							};
						}
					);
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="place" ng-controller="StudentController">
			<form name="frmStudent">
				<div class="title">Edit Student</div>
				<div class="component">
					<label class="label">Number</label>
					<input type="text" ng-model="student.number" disabled />
				</div>
				<div class="component">
					<label for="txtStudentName" class="label">Name</label>
					<input type="text" id="txtStudentName" name="txtStudentName" ng-model="student.name" required  />
					<div ng-show="frmStudent.txtStudentName.$touched || frmStudent.txtStudentName.$dirty">
						<div class="error" ng-show="frmStudent.txtStudentName.$error.required">This field is required</div>
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