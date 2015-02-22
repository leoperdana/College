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
				"StudentController",
				function($scope, $http, $window)
				{     
					var arrStrStudentNumber = [];
					
					$scope.Validate = function()
					{
						var strStudentNumber = $scope.student.number;
						
						if(!strStudentNumber)
							$scope.frmStudent.txtStudentNumber.$error.hasbeenused = false;
						else if(arrStrStudentNumber.indexOf(strStudentNumber) != -1)
							$scope.frmStudent.txtStudentNumber.$error.hasbeenused = true;
						else
						{
							var mParam = {studentnumber : strStudentNumber};
							var strUrlCheckStudentNumberService = "${pageContext.request.contextPath}/checkstudentnumbersvc";

							$http.post(strUrlCheckStudentNumberService, mParam).success
							(
								function(mResponse)
								{
									if(!mResponse.status)
										return;
									
									if(!mResponse.result)
										$scope.frmStudent.txtStudentNumber.$error.hasbeenused = false
									else if(mResponse.result)
									{
										arrStrStudentNumber.push(strStudentNumber);
										$scope.frmStudent.txtStudentNumber.$error.hasbeenused = true;
									}
								}
							);
						}
					}
					
					$scope.Cancel = function()
					{
						$window.location = "${pageContext.request.contextPath}/index";
					}
					
					$scope.Submit = function()
					{
						if($scope.frmStudent.$invalid)
						{
							$scope.frmStudent.txtStudentNumber.$setTouched();
							$scope.frmStudent.txtStudentName.$setTouched();
							
							return;
						}
							
						var mParam = 
						{
							studentnumber : $scope.student.number,
							studentname : $scope.student.name
						};
						
						var strUrlDoAddStudentService = "${pageContext.request.contextPath}/doaddstudentsvc";

						$http.post(strUrlDoAddStudentService, mParam).success
						(
							function(mResponse)
							{
								if(!mResponse.status)
									return;
								
								alert("Student has been added");
								$window.location = "${pageContext.request.contextPath}/index";
							}
						);
					}
				}
			);
			
		</script>
	</head>
	<body ng-app="page">
		<div class="place" ng-controller="StudentController">
			<form name="frmStudent">
				<div class="title">Add Student</div>
				<div class="component">
					<label for="txtStudentNumber" class="label">Number</label>
					<input type="text" id="txtStudentNumber" name="txtStudentNumber" ng-model="student.number" ng-maxlength="10" ng-blur="Validate()" required />
					<div ng-show="frmStudent.txtStudentNumber.$touched || frmStudent.txtStudentNumber.$dirty">
						<div class="error" ng-show="frmStudent.txtStudentNumber.$error.required">This field is required</div>
						<div class="error" ng-show="frmStudent.txtStudentNumber.$error.maxlength">Wrong student number format</div>
						<div class="error" ng-show="frmStudent.txtStudentNumber.$error.hasbeenused">This student number has been used</div>
					</div>
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